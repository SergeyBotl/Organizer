package com.example.sergey.organizer.constants;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
    private static SimpleDateFormat dmy = new SimpleDateFormat(Constants.DATE_D_M_Y);
    private static SimpleDateFormat my = new SimpleDateFormat("MMMM yyyy");

    public static String getDateDMY(long date) {
        return date == 0 ? null : dmy.format(new Date(date));
    }

    public static String getDateMY(long date) {
        return date == 0 ? null : my.format(new Date(date));
    }
}
