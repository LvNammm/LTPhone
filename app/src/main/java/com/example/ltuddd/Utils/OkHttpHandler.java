package com.example.ltuddd.Utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpHandler extends AsyncTask<String, Void, byte[]> {
    OkHttpClient client = new OkHttpClient();
    private Context c;
    public OkHttpHandler(Context context){
        c = context;
    }
    private Map<String, String> bodyMapping;
    private boolean sendWithBody = false;
    public void addBody(Map<String, String> body){
        bodyMapping = body;
        sendWithBody = true;
    }
    @Override
    protected byte[] doInBackground(String... strings) {
        Request.Builder builder = new Request.Builder();
        builder.url(strings[0]);
        if(sendWithBody && bodyMapping != null){
            FormBody.Builder formBodyBuider = new FormBody.Builder();
            for(String a: bodyMapping.keySet()){
                formBodyBuider.add(a,bodyMapping.get(a));
            }
            RequestBody formBody = formBodyBuider.build();
            builder.post(formBody);
        }
        Request request = builder.build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().bytes();
        } catch (Exception e) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(c, "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
                }
            });
        }
        return null;
    }

}
