package com.frame.fast.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String getNowFormateShortYMDhmsDateString() {
        String nowDate = "";
        try {
            java.sql.Date date = null;
            date = new java.sql.Date(new Date().getTime());
            nowDate = new SimpleDateFormat("yyMMddHHmmss").format(date);
            return nowDate;
        } catch (Exception e) {
            return null;
        }
    }
}
