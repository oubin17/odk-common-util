package com.odk.base.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

/**
 * ExcelDataListener
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/9/22
 */
public class ExcelSingleDataListener<T> implements ReadListener<T> {


    private static final Logger logger = LoggerFactory.getLogger(ExcelBatchDataListener.class);
    private final Consumer<T> consumer;

    public ExcelSingleDataListener(Consumer<T> consumer) {
        if (null ==  consumer) {
            throw new IllegalArgumentException("consumer can not be null");
        }
        this.consumer = consumer;
    }

    @Override
    public void invoke(T data, AnalysisContext context) {
        consumer.accept(data);

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
