package com.example.ltuddd.receiver;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.ltuddd.R;
import com.example.ltuddd.Utils.Contain;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent ) {
        Intent i = new Intent(context, NotificationService.class);
        i.putExtra(Contain.title,intent.getStringExtra(Contain.title));
        i.putExtra(Contain.text, intent.getStringExtra(Contain.text));
        context.startService(i);
    }
}
