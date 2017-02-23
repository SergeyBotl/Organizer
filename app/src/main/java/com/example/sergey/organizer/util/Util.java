package com.example.sergey.organizer.util;

import com.example.sergey.organizer.constants.Const;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
    private static SimpleDateFormat dmy = new SimpleDateFormat(Const.DATE_D_M_Y);
    private static SimpleDateFormat my = new SimpleDateFormat("MMMM yyyy");
    private static SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
    private static SimpleDateFormat sdfDayEE = new SimpleDateFormat("dd EE");
    private static SimpleDateFormat sdfDateTime = new SimpleDateFormat(Const.DATE_D_M_Y_H_M);
    private static SimpleDateFormat sdfEEDayMonth = new SimpleDateFormat("EE, dd MMM yyyy");
    public static String getDateDMY(long date) {
        return date == 0 ? null : dmy.format(new Date(date));
    }

    public static String getDateMY(long date) {
        return date == 0 ? null : my.format(new Date(date));
    }

    public static String getTime(long date) {
        return date == 0 ? null : sdfTime.format(new Date(date));
    }

    public static String getDayEE(long date) {
        return date == 0 ? null : sdfDayEE.format(new Date(date));
    }
    public static String getEEDayMonth(long date) {
        return date == 0 ? null : sdfEEDayMonth.format(new Date(date));
    }
}
