package com.example.sergey.organizer.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;

import com.example.sergey.organizer.MainActivity;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    Handler handler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                // обновляем TextView
                Log.d("tag", " " + msg.what);
            }
        };*/
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        Log.d("tag", "onCreateDialog");
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user

        final int i = day;
        Calendar calendar=new GregorianCalendar(year,month,day);
        ((MainActivity)getActivity()).sendDate(calendar.getTimeInMillis());

        Log.d("tag", "onDateSet " + day);
    }
}