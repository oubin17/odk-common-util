package com.odk.base.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * ExcelWriteUtil
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/9/28
 */
public class ExcelWriteUtil {

    private ExcelWriteUtil() {}

    /**
     * 写入Excel数据（包含表头）
     *
     * @param data 数据列表
     * @param clazz 数据类型（用于自动提取表头）
     * @param outputStream 输出流
     * @param sheetName sheet名称
     * @param <T> 数据类型
     */
    public static <T> void writeExcel(List<T> data, Class<T> clazz, OutputStream outputStream, String sheetName) {
        EasyExcel.write(outputStream, clazz)
                .sheet(sheetName)
                .doWrite(data);
    }

    /**
     * 写入Excel数据（包含表头，默认sheet名称）
     *
     * @param data 数据列表
     * @param clazz 数据类型（用于自动提取表头）
     * @param outputStream 输出流
     * @param <T> 数据类型
     */
    public static <T> void writeExcel(List<T> data, Class<T> clazz, OutputStream outputStream) {
        writeExcel(data, clazz, outputStream, "Sheet1");
    }

    /**
     * 流式写入Excel数据（适用于大数据量场景）
     *
     * @param clazz 数据类型（用于自动提取表头）
     * @param outputStream 输出流
     * @param sheetName sheet名称
     * @param <T> 数据类型
     * @return ExcelWriter对象，用于后续写入数据
     */
    public static <T> ExcelWriter createExcelWriter(Class<T> clazz, OutputStream outputStream, String sheetName) {
        return EasyExcel.write(outputStream, clazz).build();
    }

    /**
     * 创建写入sheet对象
     *
     * @param excelWriter Excel写入器
     * @param sheetName sheet名称
     * @param sheetIndex sheet索引
     * @return WriteSheet对象
     */
    public static WriteSheet createWriteSheet(ExcelWriter excelWriter, String sheetName, int sheetIndex) {
        return EasyExcel.writerSheet(sheetIndex, sheetName).build();
    }

    /**
     * 创建写入sheet对象（默认索引0）
     *
     * @param excelWriter Excel写入器
     * @param sheetName sheet名称
     * @return WriteSheet对象
     */
    public static WriteSheet createWriteSheet(ExcelWriter excelWriter, String sheetName) {
        return createWriteSheet(excelWriter, sheetName, 0);
    }

    /**
     * 写入数据到指定sheet
     *
     * @param excelWriter Excel写入器
     * @param writeSheet sheet对象
     * @param data 数据列表
     * @param <T> 数据类型
     */
    public static <T> void writeDataToSheet(ExcelWriter excelWriter, WriteSheet writeSheet, List<T> data) {
        excelWriter.write(data, writeSheet);
    }

    /**
     * 完成Excel写入并关闭资源
     *
     * @param excelWriter Excel写入器
     */
    public static void finishExcelWriter(ExcelWriter excelWriter) {
        if (excelWriter != null) {
            excelWriter.finish();
        }
    }

    /**
     * 写入多个sheet的Excel数据
     *
     * @param outputStream 输出流
     * @param sheetDataMap sheet数据映射，key为sheet名称，value为数据列表
     * @param clazz 数据类型（用于自动提取表头）
     * @param <T> 数据类型
     */
    public static <T> void writeMultipleSheetExcel(OutputStream outputStream, Map<String, List<T>> sheetDataMap, Class<T> clazz) {
        ExcelWriter excelWriter = null;
        try {
            excelWriter = EasyExcel.write(outputStream, clazz).build();
            int sheetIndex = 0;
            for (Map.Entry<String, List<T>> entry : sheetDataMap.entrySet()) {
                WriteSheet writeSheet = EasyExcel.writerSheet(sheetIndex++, entry.getKey()).build();
                excelWriter.write(entry.getValue(), writeSheet);
            }
        } finally {
            finishExcelWriter(excelWriter);
        }
    }

    /**
     * 写入简单数据（无对象模型，需手动指定表头）
     *
     * @param data 数据列表
     * @param head 表头
     * @param outputStream 输出流
     * @param sheetName sheet名称
     */
    public static void writeSimpleExcel(List<List<Object>> data, List<List<String>> head,
                                        OutputStream outputStream, String sheetName) {
        EasyExcel.write(outputStream)
                .head(head)
                .sheet(sheetName)
                .doWrite(data);
    }

    /**
     * 写入简单数据（无对象模型，无表头）
     *
     * @param data 数据列表
     * @param outputStream 输出流
     * @param sheetName sheet名称
     */
    public static void writeSimpleExcel(List<List<Object>> data, OutputStream outputStream, String sheetName) {
        EasyExcel.write(outputStream)
                .sheet(sheetName)
                .doWrite(data);
    }
}
