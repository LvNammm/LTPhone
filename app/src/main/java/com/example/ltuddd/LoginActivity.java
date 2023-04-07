package com.example.ltuddd;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
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

import okhttp3.Request;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                Request.Builder builder = new Request.Builder();
                OkHttpHandler handler = new OkHttpHandler(getApplicationContext());
                byte[] image;

                try {
                    image = handler.execute(URLRequest.test).get();

                    if (image != null && image.length > 0) {

                        String testV=new String(image);
                        editTextUsername.setText(testV);
                        System.out.println("data: "+testV);
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Ket noi thanh cong", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
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
}