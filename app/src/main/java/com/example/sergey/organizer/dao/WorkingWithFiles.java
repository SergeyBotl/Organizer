package com.example.sergey.organizer.dao;

import android.content.Context;
import android.util.Log;

import com.example.sergey.organizer.entity.Event;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class WorkingWithFiles {

    // private Context context;
    // private static File filePath;
    private String TAG = "tag";
    private static File filePathDir;
    private static List<Event> list = new ArrayList<>();

    public WorkingWithFiles() {
    }

    public WorkingWithFiles(Context context) {
        // this.context = context;
        //filePath = new File(Environment.getExternalStoragePublicDirectory("Organizer"), "file.txt");
        // path = Environment.DIRECTORY_DOWNLOADS;

        String fileName = "fileInner.txt";
        filePathDir = new File(context.getFilesDir(), fileName);

        long date = new Date().getTime();
        long time = new Date().getTime();

        list.add(new Event("Работа над книгой", date, time, false));
        list.add(new Event("Повесить давно купленную картину", date, time, false));
        list.add(new Event("Позвонить старому приятелю", date, time, false));
        list.add(new Event("Работа над книгой.", date, time, false));
        list.add(new Event("Конференция NAPO", date, time, false));
        list.add(new Event("Подготовка к съезду IMRA Пресс-релиз", date, time, false));
        list.add(new Event("Рейс 1610 вылет в нешвиль", date, time, false));
        list.add(new Event("Сезд IMRA в Нешвиле", date, time, false));
        list.add(new Event("Работа над книгой", date, time, false));
        list.add(new Event("Письмо с благодарностью: Линда", date, time, false));

    }

    public void addData() {
        writeFile(list);
    }

    public void writeFile(List<Event> list) {
        try {
            FileOutputStream fos = new FileOutputStream(filePathDir);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            for (Event e : list) {
                // Log.d(TAG, e.toString());
                oos.writeObject(e);
            }

            oos.close();
            Log.d(TAG, "Файл записан");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Event> readFile() {

        List<Event> list = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(filePathDir);
            ObjectInputStream ois = new ObjectInputStream(fis);

            if (ois != null) {
                Event event = (Event) ois.readObject();
                while (event != null) {
                    list.add(event);
                    event = (Event) ois.readObject();
                    // Log.d(TAG, event.toString());
                }
            }
        } catch (FileNotFoundException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }


}
