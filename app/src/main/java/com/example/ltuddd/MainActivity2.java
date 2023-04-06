package com.example.ltuddd;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity2 extends AppCompatActivity {

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
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // connnect to database 'testdb'
                    String url = "jdbc:mysql://localhost:3307/education";
                    String username = "root";
                    String pass = "123456";
//                    String host = "s88d81.cloudnetwork.vn:3306";
//                    username = "kin82682_group4Edu";
//                    String dbName = "kin82682_Education";
//                    String password = "nam689nam986";
//                    url = "jdbc:mysql://"+host+"/"+dbName+"?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowPublicKeyRetrieval=true&useSSL=false&sessionVariables=sql_mode='STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION'";
                    Connection conn = getConnection(url, username, pass);
                    // crate statement
                    Statement stmt = conn.createStatement();
                    // get data from table 'student'
                    ResultSet rs = stmt.executeQuery("select * from tbl_user");
                    // show data
                    while (rs.next()) {
                        System.out.println(rs.getInt(1) + "  " + rs.getString(2)
                                + "  " + rs.getString(3));
                    }
                    // close connection
                    conn.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
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