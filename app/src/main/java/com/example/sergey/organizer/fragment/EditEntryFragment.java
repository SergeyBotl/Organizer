package com.example.sergey.organizer.fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sergey.organizer.Controller;
import com.example.sergey.organizer.R;
import com.example.sergey.organizer.alarm.AlarmReceiver;
import com.example.sergey.organizer.entity.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class EditEntryFragment extends Fragment {
    final String TAG = "tag";
    final String LOG_TAG = "tag";

    private SimpleDateFormat sdfDateTime = new SimpleDateFormat("dd MMMM  EEEE HH:mm");
    private SimpleDateFormat sdfDate = new SimpleDateFormat("dd MMMM  EEEE ");
    private SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
    private static List<Event> list;
    private Controller contr = new Controller();
    private static Event event;
    private static int index;
    private TextView msgText, textDate, textTime;
    private ImageView ivDate, ivTime, ivClearDate, ivClearTime;
    private CheckBox checkBoxDone;
    private OnClickListenerEEF mOnClickListenerEEF;
    private static long keeperDate;
    private static long keeperTime;
    private LinearLayout layoutTime;
    private AlarmManager am;

    public interface OnClickListenerEEF {
        void startCalendarDialog(String nameDialog);
    }

    public static EditEntryFragment newInstance(int index, List<Event> event) {
        EditEntryFragment eef = new EditEntryFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("event", (ArrayList<? extends Parcelable>) event);
        args.putInt("index", index);
        eef.setArguments(args);
        return eef;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_entry, container, false);
        mOnClickListenerEEF = (OnClickListenerEEF) getActivity();
        am = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        checkBoxDone = (CheckBox) view.findViewById(R.id.checkboxDone);
        ivDate = (ImageView) view.findViewById(R.id.image_date);
        ivTime = (ImageView) view.findViewById(R.id.image_time);
        ivClearDate = (ImageView) view.findViewById(R.id.image_clear_date);
        ivClearTime = (ImageView) view.findViewById(R.id.image_clear_time);
        msgText = (TextView) view.findViewById(R.id.editTextMsg);
        textDate = (TextView) view.findViewById(R.id.editTextDate_eef);
        textTime = (TextView) view.findViewById(R.id.editTextTime_eef);
        layoutTime = (LinearLayout) view.findViewById(R.id.layout_time);

        textDate.setFocusable(false);

        if (getArguments() != null) {
            index = getArguments().getInt("index", 0);
            list = getArguments().getParcelableArrayList("event");
            event = list.get(index);
            msgText.setText(event.getMsgEvent());
            keeperDate = event.getDate();
            keeperTime = event.getTime();
            checkBoxDone.setChecked(event.isCheckDone());
        } else {
            event = null;
            keeperDate = 0;
            keeperTime = 0;
        }
        setDate(keeperDate);
        setTime(keeperTime);

        textDate.setOnClickListener(allBtnDate);
        ivClearDate.setOnClickListener(allBtnDate);
        ivDate.setOnClickListener(allBtnDate);

        ivClearTime.setOnClickListener(allBtnTime);
        ivTime.setOnClickListener(allBtnTime);
        textTime.setOnClickListener(allBtnTime);
        return view;
    }

    public void setDate(long date) {
        keeperDate = date;
        if (date != 0) {
            textDate.setText(sdfDate.format(new Date(date)));
            ivClearDate.setVisibility(View.VISIBLE);
            layoutTime.setVisibility(View.VISIBLE);
        } else {
            textDate.setText("");
            ivClearDate.setVisibility(View.GONE);
            setTime(0);
            layoutTime.setVisibility(View.GONE);
        }
    }

    public void setTime(long time) {
        keeperTime = time;
        if (time != 0) {
            textTime.setText(sdfTime.format(new Date(time)));
            ivClearTime.setVisibility(View.VISIBLE);
            keeperTime = prepareTimeAlarm(keeperDate, time);
        } else {
            textTime.setText("");
            ivClearTime.setVisibility(View.GONE);
        }
    }

    View.OnClickListener allBtnDate = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.image_clear_date:
                    setDate(0);
                    return;
                case R.id.image_date:
                    mOnClickListenerEEF.startCalendarDialog("date");
                    return;
                case R.id.editTextDate_eef:
                    mOnClickListenerEEF.startCalendarDialog("date");
                    return;
                 default:
                    break;
            }
        }
    };

    View.OnClickListener allBtnTime = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.image_clear_time:
                    setTime(0);
                    return;
                case R.id.image_time:
                    mOnClickListenerEEF.startCalendarDialog("time");
                    return;
                case R.id.editTextTime_eef:
                    mOnClickListenerEEF.startCalendarDialog("time");
                    return;
                default:
                    break;
            }
        }
    };

    public void saveEvent() {
        keeperDate = prepareTimeAlarm(keeperDate, keeperTime);
        Event eventNew = new Event(msgText.getText().toString(), keeperDate, keeperTime,checkBoxDone.isChecked());
        if (event == null) {
            if (msgText.getText().length() > 0 || textDate.getText().length() > 0) {
                contr.saveNewEvent(eventNew);
            }
        } else {
            if (event.getTime() != eventNew.getTime()) {
                if (keeperTime != 0) {
                    cancelAlarm(event.getTime());
                    setAlarm(keeperTime);
                } else {
                    if (event.getTime() != 0) {
                        cancelAlarm(event.getTime());
                    }
                }
            }

            contr.updateItemEvent(eventNew, index);
        }
    }

    private long prepareTimeAlarm(long date, long time) {
        if (date == 0) {
            keeperTime = 0;
            return 0;
        }
        Calendar dateNew = new GregorianCalendar();
        dateNew.setTime(new Date(date));
        Calendar timeNew = new GregorianCalendar();
        timeNew.setTime(new Date(time));

        Calendar calendar = new GregorianCalendar();
        calendar.set(dateNew.get(Calendar.YEAR)
                , dateNew.get(Calendar.MONTH)
                , dateNew.get(Calendar.DAY_OF_MONTH)
                , timeNew.get(Calendar.HOUR_OF_DAY)
                , timeNew.get(Calendar.MINUTE));
        if (time==0){
            calendar.set(Calendar.HOUR_OF_DAY,23);
            calendar.set(Calendar.MINUTE,59);
            calendar.set(Calendar.SECOND,59);
     }

        Log.d("tag", "" + sdfDate.format(new Date(calendar.getTimeInMillis()))
                + "\n" + sdfTime.format(new Date(calendar.getTimeInMillis()))
                + "\n" + sdfDateTime.format(new Date(calendar.getTimeInMillis())));
        return calendar.getTimeInMillis();
    }

    public void setAlarm(long timeMillis) {
        Intent intent = createIntent(String.valueOf(timeMillis), event.getMsgEvent(), sdfTime.format(new Date(timeMillis)));
        PendingIntent pIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, 0);
         am.set(AlarmManager.RTC_WAKEUP, timeMillis, pIntent);

    }

    public void cancelAlarm(long timeMillis) {
         Intent intent = createIntent(String.valueOf(timeMillis), null, null);
        PendingIntent pIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

    }

    private Intent createIntent(String action, String extraMsg, String extraTime) {
        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
        intent.setAction(action);
        intent.putExtra("time", extraTime);
        intent.putExtra("msg", extraMsg);
        return intent;
    }


}
