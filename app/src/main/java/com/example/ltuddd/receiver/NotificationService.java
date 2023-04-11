package com.example.ltuddd.receiver;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaActionSound;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.ltuddd.LoginActivity;
import com.example.ltuddd.R;
import com.example.ltuddd.Utils.Contain;
import com.example.ltuddd.Utils.MediaSeriable;

import java.util.Calendar;
import java.util.Date;

public class NotificationService extends Service {
    MediaPlayer mediaPlayer;
    private final String CHANEL_ID = "chanel_id";
    public void createNotification(Intent intent) {
        String title = intent.getStringExtra(Contain.title);
        String text = intent.getStringExtra(Contain.text);
        System.out.println("titile: "+title );
        System.out.println("text: "+text);
        System.out.println("thoi gian thong bao thuc: "+ new Date(Calendar.getInstance().getTimeInMillis()).getMinutes());
        int id = 1;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANEL_ID)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT).setSilent(true);

        // Hiển thị thông báo
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(id, builder.build());
        int resID= R.raw.abc;
        Contain.mediaPlayer = MediaPlayer.create(getApplicationContext(),resID);
        Contain.mediaPlayer.start();
        CountDownTimer timer = new CountDownTimer(10000, 1000) {
            int i =1;
            @Override
            public void onTick(long millisUntilFinished) {
                System.out.println(i++);
            }

            @Override
            public void onFinish() {
                Contain.stopMediaPlayer();
            }
        };
        timer.start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.createNotification(intent);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        if(mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        super.onDestroy();
    }
}
