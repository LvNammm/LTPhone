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
import okhttp3.MediaType;
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
    private RequestBody bodyJson;
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private boolean sendWithBody = false;
    public void addBody(String body){
        bodyJson = RequestBody.create(JSON,body);
        sendWithBody = true;
    }
    @Override
    protected byte[] doInBackground(String... strings) {
        Request.Builder builder = new Request.Builder();
        builder.url(strings[0]);
        if(sendWithBody && bodyJson != null){
            builder.post(bodyJson);
            sendWithBody = false;
            bodyJson = null;
        }
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().bytes();
        } catch (Exception e) {
            MakeToast.make("Keets noi khong thanh cong", c);
        }
        return null;
    }

}
