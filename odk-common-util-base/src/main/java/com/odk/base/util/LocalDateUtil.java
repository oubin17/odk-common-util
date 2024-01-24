package com.odk.base.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * LocalDateUtil
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/20
 */
public class LocalDateUtil {

    private LocalDateUtil() {}

    // 获取当前日期
    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    // 创建指定日期
    public static LocalDate createSpecificDate(int year, int month, int day) {
        return LocalDate.of(year, month, day);
    }

    // 获取年、月、日
    public static void printDateComponents(LocalDate date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        System.out.println("年: " + year + ", 月: " + month + ", 日: " + day);
    }

    // 日期比较
    public static boolean isDateBefore(LocalDate date1, LocalDate date2) {
        return date1.isBefore(date2);
    }

    public static boolean isDateAfter(LocalDate date1, LocalDate date2) {
        return date1.isAfter(date2);
    }

    // 日期操作
    public static LocalDate addDays(LocalDate date, long daysToAdd) {
        return date.plusDays(daysToAdd);
    }

    public static LocalDate subtractDays(LocalDate date, long daysToSubtract) {
        return date.minusDays(daysToSubtract);
    }

    // 格式化日期
    public static String formatDate(LocalDate date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return date.format(formatter);
    }

    // 解析字符串为LocalDate
    public static LocalDate parseStringToDate(String dateString, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(dateString, formatter);
    }

    // 计算日期之间的差距
    public static long calculateDaysDifference(LocalDate date1, LocalDate date2) {
        return ChronoUnit.DAYS.between(date1, date2);
    }
}
