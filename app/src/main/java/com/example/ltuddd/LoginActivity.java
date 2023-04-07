package com.example.ltuddd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.animation.ObjectAnimator;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ltuddd.Utils.OkHttpHandler;
import com.example.ltuddd.Utils.URLRequest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Request;

public class LoginActivity extends AppCompatActivity {

    private final String CHANEL_ID = "loginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNotificationChannel();
        setContentView(R.layout.activity_login);

        TextView hint_username = findViewById(R.id.text_hint_username);
        TextView hint_password = findViewById(R.id.text_hint_password);
        ObjectAnimator animator_user = ObjectAnimator.ofFloat(hint_username, "translationY", 0f, -100f);
        ObjectAnimator animator_pass = ObjectAnimator.ofFloat(hint_password, "translationY", 0f, -100f);
        EditText editTextUsername = findViewById(R.id.username);
        EditText editTextPassword = findViewById(R.id.password);
        Button btnLogin = findViewById(R.id.button);
        Button btnResiter = findViewById(R.id.register);
        Intent registerActivity = new Intent(this, RegisterActivity.class);

        btnResiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(registerActivity);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANEL_ID)
                        .setSmallIcon(R.drawable.icon)
                        .setContentTitle("thông báo")
                        .setContentText("Dậy đê ông cháu ơi")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

// notificationId is a unique int for each notification that you must define
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                notificationManager.notify(1, builder.build());
//                OkHttpHandler handler = new OkHttpHandler(getApplicationContext());
//                byte[] image;
//
//                try {
//                    image = handler.execute(URLRequest.test).get();
//
//                    if (image != null && image.length > 0) {
//
//                        String testV=new String(image);
//                        editTextUsername.setText(testV);
//                        System.out.println("data: "+testV);
//                        new Handler(Looper.getMainLooper()).post(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(getApplicationContext(), "Ket noi thanh cong", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                } catch (Exception e) {
//                    new Handler(Looper.getMainLooper()).post(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(getApplicationContext(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
            }
        });
        setOnfocusEditTextListener(editTextUsername,animator_user);
        setOnfocusEditTextListener(editTextPassword,animator_pass);

    }

    private void setOnfocusEditTextListener(EditText editText, ObjectAnimator animator) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                changeAnimatorForcusEdittext(editText,hasFocus,animator);
            }
        });
    }

    private void changeAnimatorForcusEdittext(EditText editText, boolean focus, ObjectAnimator animator) {
        String text = String.valueOf(editText.getText());
        if (focus && text.equals("")) {
            animator.start();
        } else {
            if (text.equals(""))
                animator.reverse();
        }
    }

    private  Connection getConnection(String dbURL, String userName,
                                                   String password) throws ClassNotFoundException, SQLException {
        Connection conn = null;
            Class.forName("com.mysql.jdbc.Driver");
            try {
                conn = DriverManager.getConnection("jdbc:mysql://" + "s88d81.cloudnetwork.vn:3306/kin82682_Education","kin82682_group4Edu","nam689nam986");
                System.out.println("connect successfully!");
            }
            catch (Exception e){
                e.printStackTrace();
            }
        return conn;
    }


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "testApp";
            String description = "decrep testApp";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}