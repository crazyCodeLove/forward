package com.huitong.server.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * </p>
 * author pczhao <br/>
 * date  2021/5/23 18:58
 */

public class CalendarUtil {

    public static String DATE_TIME_PATTERN_STANDARD = "yyyy-MM-dd HH:mm:ss";
    public static String DATE_TIME_PATTERN_SIMPLE = "yyyyMMddHHmmss";

    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static Date parseDate(String dateTime, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            Date date = sdf.parse(dateTime);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
