package com.example.ltuddd.Utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.ltuddd.domain.Task;
import com.example.ltuddd.receiver.NotificationReceiver;

import java.util.Calendar;
import java.util.Date;

public class Notification  {
    public static void create(Context c,int code, String title, String text, Object systemService, boolean repeat,Calendar calendar){
        Intent intent = new Intent(c, NotificationReceiver.class);
        intent.putExtra(Contain.title,title);
        intent.putExtra(Contain.text, text);
        intent.putExtra("id",code);
        System.out.println("Time du dinh thong bao:"+calendar.getTime());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(c, code, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) systemService;
        if(!repeat){
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
        else {
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }
    public static void cancel(int id, Context c, Object systemService){
        AlarmManager alarmManager = (AlarmManager) systemService;
        Intent intent = new Intent(c, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(c, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }
}
