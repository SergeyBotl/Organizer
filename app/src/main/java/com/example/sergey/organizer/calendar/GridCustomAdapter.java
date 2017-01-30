package com.example.sergey.organizer.calendar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sergey.organizer.Controller;
import com.example.sergey.organizer.R;
import com.example.sergey.organizer.entity.Event;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class GridCustomAdapter extends BaseAdapter {
    private SimpleDateFormat sdfEE = new SimpleDateFormat("EE");
    private SimpleDateFormat sdfDay = new SimpleDateFormat("dd MM yy");
    private String deyW[] = {"пн", "вт", "ср", "чт", "пт", "сб", "вс",};
    private Calendar calendar;
    private Context context;
    private static int day[];
    private static int month, year;
    private int colorBlu, colorWhith, colorAccent, countStart, colorBluDark, colorBlack;
    private static List<Event> eventList;
    private Controller cont = new Controller();

    public GridCustomAdapter(Context context, int[] day, Calendar calendar) {
        this.context = context;
        this.day = day;
        this.calendar = calendar;
        Log.d("Tag", "Constructor");
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        colorBlu = context.getResources().getColor(R.color.colorBlue);
        colorBluDark = context.getResources().getColor(R.color.colorPrimaryDark);
        colorWhith = context.getResources().getColor(R.color.colorWhite);
        colorAccent = context.getResources().getColor(R.color.colorAccent);
        colorBlack = context.getResources().getColor(R.color.colorBlack);
     eventList = cont.getSortedList();
       // eventList = cont.listWithoutExecuted();
    }

    @Override
    public int getCount() {
        return day.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return day[position];
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        TextView textView;
        if (view == null) {
            textView = new TextView(context);
        } else {
            textView = (TextView) view;
        }

        if (position == 1) {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            countStart = 5 + dayOfWeek(calendar);
        }
        if (position < 7) {
            textView.setText("" + deyW[position]);
            textView.setPadding(2, 2, 2, 2);
            textView.setTextColor(position < 5 ? colorWhith : colorAccent);
            textView.setBackgroundColor(colorBlu);
        } else {
            textView.setPadding(2, 25, 2, 25);
        }

        if (position > countStart && currentMonth(calendar)) {
            textView.setText("" + calendar.get(Calendar.DAY_OF_MONTH));
            textView.setTextColor(today(calendar) ? colorBluDark : colorBlack);
            // textView.setBackground(context.getResources().getDrawable(R.drawable.bottom_normal));

            int eventCount = isEvent(calendar);
            if (eventCount > 0) {
                Drawable image = context.getResources().getDrawable(R.drawable.ic_line1_or);
                int h = image.getIntrinsicHeight();
                int w = image.getIntrinsicWidth();
                image.setBounds(0, 0, 25,5);

                textView.setCompoundDrawables(null, null, null, image);
            }
            day[position] = calendar.get(Calendar.DAY_OF_MONTH);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        textView.setTextSize(15.0f);
        textView.setGravity(View.TEXT_ALIGNMENT_GRAVITY);

        return textView;
    }

    boolean today(Calendar calendar) {
        Calendar today = new GregorianCalendar();
        today.setTime(new Date());

        if (calendar.get(Calendar.MONTH) == today.get(Calendar.MONTH)) { //
            return today.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH);
        }
        return false;
    }

    int dayOfWeek(Calendar calendar) {
        int i = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return i == 0 ? 7 : i;
    }

    boolean currentMonth(Calendar calendar) {
        return month == calendar.get(Calendar.MONTH);
    }

    int isEvent(Calendar calendar) {
        int i = 0;
        String c1 = sdfDay.format(new Date(calendar.getTimeInMillis()));

        for (Event e :eventList) {
            String c2 = sdfDay.format(new Date(e.getDate()));
            if (c1.equals(c2)&&!e.isCheckDone()) {
                i++;
            }
        }
        return i;
    }

}
