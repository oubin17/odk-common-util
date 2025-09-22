package com.odk.base.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * ExcelUtil
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/9/22
 */
public class ExcelReadUtil {

    private ExcelReadUtil() {}

    /**
     * 读取Excel文件中的数据,自动处理表头
     *
     * @param inputStream Excel文件输入流
     * @param clazz      数据模型类
     * @param sheetNo    工作表编号（从0开始）
     * @param <T>        数据模型类型
     * @return 数据列表
     */
    public static <T> List<T> readExcel(InputStream inputStream, Class<T> clazz, int sheetNo) {
        return EasyExcel.read(inputStream)
                .head(clazz)
                .sheet(sheetNo)
                .doReadSync();
    }

    /**
     * 读取Excel文件中的数据（默认读取第一个工作表）,自动处理表头
     *
     * @param inputStream Excel文件输入流
     * @param clazz      数据模型类
     * @param <T>        数据模型类型
     * @return 数据列表
     */
    public static <T> List<T> readExcel(InputStream inputStream, Class<T> clazz) {
        return readExcel(inputStream, clazz, 0);
    }

    /**
     * 读取Excel文件中的数据（忽略表头，按列索引读取）
     *
     * @param inputStream Excel文件输入流
     * @param clazz      数据模型类
     * @param sheetNo    工作表编号（从0开始）
     * @param headRowNum 表头行数
     * @param <T>        数据模型类型
     * @return 数据列表
     */
    public static <T> List<T> readExcelIgnoreHead(InputStream inputStream, Class<T> clazz, int sheetNo, int headRowNum) {
        return EasyExcel.read(inputStream)
                .head(clazz)
                .sheet(sheetNo)
                .headRowNumber(headRowNum)  // 指定表头行数
                .doReadSync();
    }


    /**
     * 读取Excel文件并逐行处理数据
     *
     * @param inputStream Excel文件输入流
     * @param clazz      数据模型类
     * @param sheetNo    工作表编号（从0开始）
     * @param consumer   数据处理函数
     * @param <T>        数据模型类型
     */
    public static <T> void readExcelSingleDeal(InputStream inputStream, Class<T> clazz, int sheetNo, Consumer<T> consumer) {
        EasyExcel.read(inputStream, clazz, new ExcelSingleDataListener<>(consumer))
                .sheet(sheetNo)
                .doRead();
    }

    /**
     * 读取Excel文件并批量处理数据
     *
     * @param inputStream Excel文件输入流
     * @param clazz      数据模型类
     * @param sheetNo    工作表编号（从0开始）
     * @param consumer   数据处理函数
     * @param <T>        数据模型类型
     */
    public static <T> void readExcelBatchDeal(InputStream inputStream, Class<T> clazz, int sheetNo, Consumer<List<T>> consumer) {
        EasyExcel.read(inputStream, clazz, new ExcelBatchDataListener<>(consumer))
                .sheet(sheetNo)
                .doRead();
    }

    /**
     * 读取Excel文件并批量处理数据
     *
     * @param inputStream Excel文件输入流
     * @param clazz      数据模型类
     * @param sheetNo    工作表编号（从0开始）
     * @param batchCount 批量处理数据行数（默认100行）
     * @param consumer   数据处理函数
     * @param <T>        数据模型类型
     */
    public static <T> void readExcelBatchDeal(InputStream inputStream, Class<T> clazz, int sheetNo, int batchCount, Consumer<List<T>> consumer) {
        EasyExcel.read(inputStream, clazz, new ExcelBatchDataListener<>(consumer, batchCount))
                .sheet(sheetNo)
                .doRead();
    }




    /**
     * 读取Excel多个工作表的数据
     *
     * @param inputStream Excel文件输入流
     * @param sheetConfigs 各工作表配置（key: 工作表编号, value: 数据模型类）
     * @return 各工作表数据映射
     */
    public static Map<Integer, List<?>> readMultipleSheets(InputStream inputStream, Map<Integer, Class<?>> sheetConfigs) {
        Map<Integer, List<?>> result = new HashMap<>();

        // 对于每个工作表配置，分别读取数据
        for (Map.Entry<Integer, Class<?>> entry : sheetConfigs.entrySet()) {
            Integer sheetNo = entry.getKey();
            Class<?> clazz = entry.getValue();

            // 读取指定工作表的数据
            List<?> sheetData = EasyExcel.read(inputStream)
                    .head(clazz)
                    .sheet(sheetNo)
                    .doReadSync();

            result.put(sheetNo, sheetData);
        }

        return result;
    }


    /**
     * 获取Excel文件中的sheet数量
     *
     * @param inputStream Excel文件输入流
     * @return sheet数量
     */
    public static int getSheetCount(InputStream inputStream) {
        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcel.read(inputStream).build();
            return excelReader.excelExecutor().sheetList().size();
        } finally {
            if (excelReader != null) {
                excelReader.finish();
            }
        }
    }


    /**
     * 获取Excel文件中所有sheet的信息
     *
     * @param inputStream Excel文件输入流
     * @return sheet信息列表，包含sheet名称和索引
     */
    public static List<SheetInfo> getAllSheetInfo(InputStream inputStream) {
        ExcelReader excelReader = null;
        List<SheetInfo> sheetInfoList = new ArrayList<>();
        try {
            excelReader = EasyExcel.read(inputStream).build();
            List<ReadSheet> sheets = excelReader.excelExecutor().sheetList();

            for (ReadSheet sheet : sheets) {
                SheetInfo sheetInfo = new SheetInfo();
                sheetInfo.setSheetNo(sheet.getSheetNo());
                sheetInfo.setSheetName(sheet.getSheetName());
                sheetInfoList.add(sheetInfo);
            }
        } finally {
            if (excelReader != null) {
                excelReader.finish();
            }
        }
        return sheetInfoList;
    }

    /**
     * 读取Excel所有工作表的数据
     *
     * @param inputStream Excel文件输入流
     * @param clazz       数据模型类（所有sheet使用相同的数据模型）
     * @param <T>         数据模型类型
     * @return 所有工作表数据映射（key: sheet索引, value: 数据列表）
     */
    public static <T> Map<Integer, List<T>> readAllSheets(InputStream inputStream, Class<T> clazz) {
        Map<Integer, List<T>> result = new HashMap<>();
        ExcelReader excelReader = null;

        try {
            excelReader = EasyExcel.read(inputStream).build();
            List<ReadSheet> sheets = excelReader.excelExecutor().sheetList();

            for (ReadSheet sheet : sheets) {
                List<T> sheetData = EasyExcel.read(inputStream)
                        .head(clazz)
                        .sheet(sheet.getSheetNo())
                        .doReadSync();
                result.put(sheet.getSheetNo(), sheetData);
            }
        } finally {
            if (excelReader != null) {
                excelReader.finish();
            }
        }

        return result;
    }


    /**
     * Sheet信息类
     */
    public static class SheetInfo {
        private Integer sheetNo;
        private String sheetName;

        public Integer getSheetNo() {
            return sheetNo;
        }

        public void setSheetNo(Integer sheetNo) {
            this.sheetNo = sheetNo;
        }

        public String getSheetName() {
            return sheetName;
        }

        public void setSheetName(String sheetName) {
            this.sheetName = sheetName;
        }

        @Override
        public String toString() {
            return "SheetInfo{" +
                    "sheetNo=" + sheetNo +
                    ", sheetName='" + sheetName + '\'' +
                    '}';
        }
    }

}
