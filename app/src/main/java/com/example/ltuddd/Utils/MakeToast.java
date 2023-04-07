package com.example.ltuddd.Utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

public class MakeToast {
    public static void make(String mess, Context c){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(c, mess, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
