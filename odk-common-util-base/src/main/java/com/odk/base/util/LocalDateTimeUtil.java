package com.odk.base.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * LocalDateTimeUtil
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/20
 */
public class LocalDateTimeUtil {


    // 获取当前日期时间
    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }

    // 创建指定日期时间
    public static LocalDateTime createSpecificDateTime(int year, int month, int day, int hour, int minute, int second) {
        return LocalDateTime.of(year, month, day, hour, minute, second);
    }

    // 格式化日期时间
    public static String formatDateTime(LocalDateTime dateTime, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(formatter);
    }

    // 解析字符串为LocalDateTime
    public static LocalDateTime parseStringToDateTime(String dateTimeString, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(dateTimeString, formatter);
    }

    // 获取日期时间的年、月、日、时、分、秒
    public static int getYear(LocalDateTime dateTime) {
        return dateTime.getYear();
    }

    public static int getMonth(LocalDateTime dateTime) {
        return dateTime.getMonthValue();
    }

    public static int getDay(LocalDateTime dateTime) {
        return dateTime.getDayOfMonth();
    }

    public static int getHour(LocalDateTime dateTime) {
        return dateTime.getHour();
    }

    public static int getMinute(LocalDateTime dateTime) {
        return dateTime.getMinute();
    }

    public static int getSecond(LocalDateTime dateTime) {
        return dateTime.getSecond();
    }

    // 日期时间比较
    public static boolean isDateTimeBefore(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        return dateTime1.isBefore(dateTime2);
    }

    public static boolean isDateTimeAfter(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        return dateTime1.isAfter(dateTime2);
    }

    // 计算日期时间差距
    public static long calculateDateTimeDifference(LocalDateTime dateTime1, LocalDateTime dateTime2, ChronoUnit unit) {
        return unit.between(dateTime1, dateTime2);
    }

    // LocalDateTime 转换为时间戳
    public static long convertLocalDateTimeToTimestamp(LocalDateTime dateTime) {
        return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    // 时间戳 转换为 LocalDateTime
    public static LocalDateTime convertTimestampToLocalDateTime(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
    }
}
