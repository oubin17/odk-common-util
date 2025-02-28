package com.odk.base.util;

import com.odk.base.exception.BizErrorCode;
import com.odk.base.exception.BizException;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.ooxml.extractor.POIXMLTextExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * FileUtil
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/12/26
 */
public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 删除本地文件
     *
     * @param filePath
     * @return
     */
    public static void deleteFile(String filePath) {
        //根据路径创建文件对象
        File file = new File(filePath);
        try {
            //路径是个文件且不为空时删除文件
            if (file.isFile() && file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            logger.error("删除本地文件失败，文件地址：{}", filePath);
        }
    }

    /**
     * 创建文件夹
     *
     * @param filePath
     * @return
     */
    public static boolean checkAndCreateFilePath(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            throw new BizException(BizErrorCode.PARAM_ILLEGAL, "文件路径不存在");
        }
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return true;
    }

    /**
     * 保存图片到服务器
     *
     * @param
     * @param input
     * @return
     * @throws IOException
     */
    public static String saveFile(String filePath, InputStream input) throws IOException {

        OutputStream out = Files.newOutputStream(Paths.get(filePath));
        byte[] data = new byte[2048];
        int len = 0;
        while ((len = input.read(data)) != -1) {
            out.write(data, 0, len);
        }
        input.close();
        out.close();
        return filePath;
    }

    /**
     * 获取文件内容
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static String getFileContents(String filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            if (filePath.endsWith(".docx")) {
                return getDocxContent(fis);
            } else if (filePath.endsWith(".doc")) {
               return getDocContent(fis);
            } else if (filePath.endsWith(".pdf")) {
                return getPdfContent(filePath);
            }
        } catch (IOException e) {
            logger.error("获取文件内容发生异常：", e);
        }
        return "";
    }

    /**
     * 获取docx文件内容
     *
     * @param fis
     * @return
     * @throws IOException
     */
    private static String getDocxContent(FileInputStream fis) throws IOException {
        XWPFDocument document = new XWPFDocument(fis);
        XWPFWordExtractor extractor = new XWPFWordExtractor(document);
        String text = String.valueOf(extractor.getText());
        extractor.close();
        document.close();
        return text;
    }

    /**
     * 获取doc文件内容
     *
     * @param fis
     * @return
     * @throws IOException
     */
    private static String getDocContent(FileInputStream fis) throws IOException {
        HWPFDocument document = new HWPFDocument(fis);
        WordExtractor extractor = new WordExtractor(document);
        String text = String.valueOf(extractor.getText());
        document.close();
        return text;
    }

    /**
     * 获取pdf文件内容
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    private static String getPdfContent(String filePath) throws IOException {
        PDDocument document = PDDocument.load(new File(filePath));
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String text = String.valueOf(pdfStripper.getText(document));
        document.close();
        return text;
    }


    /**
     * 文件名
     *
     * @param uniqueFileId
     * @param fileName
     * @return
     */
    public static String generateFullFileName(String baseFilePath, String uniqueFileId, String fileName) {
        return baseFilePath + generateFileName(uniqueFileId, fileName);
    }

    /**
     * 文件名
     *
     * @param uniqueFileId
     * @param fileName
     * @return
     */
    public static String generateFileName(String uniqueFileId, String fileName) {
        return "_" + uniqueFileId + "_" + fileName;
    }
}
