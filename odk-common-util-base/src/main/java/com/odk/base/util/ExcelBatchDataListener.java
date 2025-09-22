package com.odk.base.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Consumer;

/**
 * ExcelBatchDataListener
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/9/22
 */
public class ExcelBatchDataListener<T> implements ReadListener<T> {


    private static final Logger logger = LoggerFactory.getLogger(ExcelSingleDataListener.class);


    /**
     * 每隔5条存储一次，实际使用中可以100条，然后清理list，方便内存回收
     */
    private int BATCH_COUNT = 100;
    private List<T> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    private final Consumer<List<T>> consumer;

    public ExcelBatchDataListener(Consumer<List<T>> consumer) {
        if (consumer == null) {
            throw new IllegalArgumentException("consumer cannot be null");
        }
        this.consumer = consumer;
    }

    public ExcelBatchDataListener(Consumer<List<T>> consumer, int batchCount) {
        if (consumer == null) {
            throw new IllegalArgumentException("consumer cannot be null");
        }
        if (batchCount < 1 || batchCount > 999) {
            BATCH_COUNT = 100;
        } else {
            BATCH_COUNT = batchCount;
        }
        this.consumer = consumer;
    }

    @Override
    public void invoke(T data, AnalysisContext context) {
        cachedDataList.add(data);
        if (cachedDataList.size() >= BATCH_COUNT) {
            consumer.accept(cachedDataList);
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        consumer.accept(cachedDataList);
    }

}
