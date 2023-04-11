package com.example.ltuddd.receiver;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.ltuddd.Utils.Contain;

public class StopNotifi extends BroadcastReceiver {
    @Nullable

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm");
        Contain.stopMediaPlayer();
    }
}
