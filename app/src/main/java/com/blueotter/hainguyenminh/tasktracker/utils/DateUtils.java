package com.blueotter.hainguyenminh.tasktracker.utils;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by HaiNM on 26/03/2018.
 */

public class DateUtils {

    public static final String DATE_FORMAT_STANDARD = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_YMD = "yyyy-MM-dd";
    public static final String DATE_FORMAT_DMY = "dd-MM-yyyy";
    public static final String DATE_FORMAT_MDY = "MM/dd/yy, hh:mm a";
    public static final String DATE_FORMAT_MMM_DD_YYYY = "MMM dd, yyyy";


    public static String formatDateTime(String format) {
        Format formatter = new SimpleDateFormat(format);
        String today = formatter.format(new Date());
        return today;
    }

    public static String formatDateTime(String format, Date date) {
        Format formatter = new SimpleDateFormat(format);
        String today = formatter.format(date);
        return today;
    }

    public static Date formatDateWithFormat(String date, String formatDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatDate);
        Date d = new Date();

        try {
            d = sdf.parse(date);
        } catch (ParseException var5) {
            var5.printStackTrace();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return d;
    }
}
