package com.example.sergey.organizer;

import android.annotation.SuppressLint;

import com.example.sergey.organizer.constants.Const;
import com.example.sergey.organizer.dao.WorkingWithFiles;
import com.example.sergey.organizer.entity.Event;

import java.text.SimpleDateFormat;
import java.util.List;


public class Controller {
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdfD = new SimpleDateFormat(Const.DATE_D_M_Y);
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdfT = new SimpleDateFormat(Const.DATE_H_M);

    private WorkingWithFiles wwf = new WorkingWithFiles();

    public Controller() {
    }

   // public List<Event> getList() {
//        return wwf.readFile();
//    }


    public List<Event> getSortedList() {
        List<Event> list = wwf.readFile();

      /*  Collections.sort(list, new Comparator<Event>() {
            @Override
            public int compare(Event event, Event t1) {
                return (int) event.getDate() - (int) t1.getDate();
            }
        });
        wwf.writeFile(list);*/

        return list;
    }

    public void saveList(List<Event> list) {
        wwf.writeFile(list);
    }

    public void saveNewEvent(Event event) {
        List<Event> list = wwf.readFile();
        list.add(event);
        wwf.writeFile(list);
    }

    public void updateItemEvent(Event event, int index) {
        List<Event> list = wwf.readFile();
        try {
            list.set(index, event);
        } catch (IndexOutOfBoundsException e) {

        }
        wwf.writeFile(list);
    }


}
