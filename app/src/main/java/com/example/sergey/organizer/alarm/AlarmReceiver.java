package com.example.sergey.organizer.alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.example.sergey.organizer.MainActivity;
import com.example.sergey.organizer.R;

public class AlarmReceiver extends WakefulBroadcastReceiver {
    final String TAG = "tag";

    @Override
    public void onReceive(final Context context, Intent intent) {

        Log.d(TAG, "onReceiver");
        Log.d(TAG, "action " + intent.getAction());
        Log.d(TAG, "msg" + intent.getStringExtra("msg"));
        Log.d(TAG, "time" + intent.getStringExtra("time"));

        Uri alarmUri = null;//= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
        ringtone.play();
        // String s = intent.getStringExtra("extra");
        Intent intentN = new Intent(context, MainActivity.class);
       // intentN.putExtra("msg", intent.getStringExtra("extra"));
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intentN, 0);
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification builder = new Notification.Builder(context)
                //.addAction(R.mipmap.ic_launcher, "Notif "+ id, pIntent)

                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(intent.getStringExtra("time"))
                .setContentText(intent.getStringExtra("msg"))
                //.setContentIntent(pIntent)
                .build();

         // убираем уведомление, когда его выбрали
        builder.flags |= Notification.FLAG_AUTO_CANCEL;

        nm.notify(0, builder);
     }
}