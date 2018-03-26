package com.blueotter.hainguyenminh.tasktracker.utils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by HaiNM on 26/03/2018.
 */

public class DateUtils {

    public static String formatDateTime() {
        Format formatter = new SimpleDateFormat("MM/dd/yy, hh:mm a");
        String today = formatter.format(new Date());
        return today;
    }
}
