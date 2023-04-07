package com.example.ltuddd.Utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.ltuddd.receiver.NotificationReceiver;

import java.util.Calendar;
import java.util.Date;

public class Notification {
    public static void create(Context c,int id, String title, String text, Object systemService, boolean repeat, Date date, Integer hour, Integer minute, Integer second){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);

        Intent intent = new Intent(c, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(c, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        intent.putExtra("title",title);
        intent.putExtra("text", text);
        intent.putExtra("id",id);
        AlarmManager alarmManager = (AlarmManager) systemService;
        if(!repeat){
            Long time = date.getTime();
            alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
        }
        else {
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }
}
