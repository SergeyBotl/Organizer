package com.example.sergey.organizer.calendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.sergey.organizer.MainActivity;
import com.example.sergey.organizer.R;
import com.example.sergey.organizer.constants.Util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CalendarFragment extends Fragment {

    private TextView textMonday, ivPrevious, ivNext;
    private OnClickListenerCF onClickListenerCF;
    private static Calendar calendar = new GregorianCalendar();
    private GridView gridView;
    private GridCustomAdapter customAdapter;
    private static int month;

    public CalendarFragment() {
    }

    public interface OnClickListenerCF {
        void onClickCF(Calendar c);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        onClickListenerCF = (OnClickListenerCF) getActivity();
        calendar.setTime(new Date());
        textMonday = (TextView) view.findViewById(R.id.textMonth);
        ivPrevious = (TextView) view.findViewById(R.id.textPrevious);
        ivNext = (TextView) view.findViewById(R.id.textNext);
        gridView = (GridView) view.findViewById(R.id.gridView);

        ivNext.setOnClickListener(onClickNext);
        ivPrevious.setOnClickListener(onClickPrevious);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, (int) customAdapter.getItemId(i));
                ((MainActivity) getActivity()).createListByFilter(calendar.getTimeInMillis(), 1, 0);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        month = calendar.get(Calendar.MONTH);
        onCreateCalendar();
    }

    View.OnClickListener onClickNext = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            month++;
            calendar.set(Calendar.MONTH, month);
            onCreateCalendar();
        }
    };

    View.OnClickListener onClickPrevious = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            month--;
            calendar.set(Calendar.MONTH, month);
            onCreateCalendar();
        }
    };

    void onCreateCalendar() {
        int i[] = new int[49];
        textMonday.setText(Util.getDateMY(calendar.getTimeInMillis()));
        customAdapter = new GridCustomAdapter(getContext(), i, calendar);
        gridView.setAdapter(customAdapter);
    }

}
