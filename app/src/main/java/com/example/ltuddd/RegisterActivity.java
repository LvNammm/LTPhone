package com.example.ltuddd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ltuddd.Utils.OkHttpHandler;
import com.example.ltuddd.Utils.URLRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Request;

public class RegisterActivity extends AppCompatActivity {
    private String validateMessenge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button btnRegister = findViewById(R.id.registerBtn);
        Button btnLogin = findViewById(R.id.loginBtn);
        EditText editTextFN = findViewById(R.id.fullname);
        EditText editTextEM = findViewById(R.id.email);
        EditText editTextPA = findViewById(R.id.password);
        EditText editTextRP = findViewById(R.id.repassword);
        Intent loginActivity = new Intent(this, LoginActivity.class);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(loginActivity);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullname = String.valueOf(editTextFN.getText());
                String email = String.valueOf(editTextEM.getText());
                String pasword = String.valueOf(editTextPA.getText());
                String rePassword = String.valueOf(editTextRP.getText());
                if(!validate(fullname,email,pasword,rePassword)){
                    Toast.makeText(getApplicationContext(), validateMessenge, Toast.LENGTH_SHORT).show();
                }
                else {
                    Request.Builder builder = new Request.Builder();
                    OkHttpHandler handler = new OkHttpHandler(getApplicationContext());
                    byte[] reponse;

                    try {
                        reponse = handler.execute(URLRequest.register).get();

                        if (reponse != null && reponse.length > 0) {

                            String testV=new String(reponse);
                            System.out.println("data: "+testV);
                            Toast.makeText(getApplicationContext(), "Kết nối thành công ", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    private boolean validate(String fullname, String email, String password, String rePass){
        if(!validateName(fullname)||!validateEmail(email)||!validatePass(password, rePass)){
            return false;
        }
        return true;
    }
    private boolean validateName(String fullname){
        if(fullname == null || fullname.isEmpty()){
            validateMessenge = "Tên không được bỏ trống";
            return false;
        }
        String number = "0123456789";
        for(int i =0;i<fullname.length();i++){
            if(number.contains(String.valueOf(fullname.charAt(i)))){
                validateMessenge = "Tên không được có số";
                return false;
            };
        }
        return true;
    }
    private boolean validateEmail(String email){
        if(email == null || email.isEmpty()){
            validateMessenge = "Email không được bỏ trống";
            return false;
        }
        String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);;
        Matcher matcher = pattern.matcher(email);
        boolean result = matcher.matches();
        if(!result){
            validateMessenge = "Email không hợp lệ";
            return result;
        }
        return result;
    }
    private boolean validatePass(String pass, String rePass){
        if(pass == null || pass.isEmpty()){
            validateMessenge = "Mật khẩu không được bỏ trống";
            return false;
        }
        if(pass.length()<6){
            validateMessenge = "Mật khẩu phải lớn hơn 6 ký tự";
            return false;
        }
        if(!pass.equals(rePass)){
            validateMessenge = "mật khẩu không khớp";
            return false;
        }
        return true;
    }
}