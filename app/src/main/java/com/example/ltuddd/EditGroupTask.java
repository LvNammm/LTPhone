package com.example.ltuddd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ltuddd.adapter.GroupTaskAdapter;

public class EditGroupTask extends AppCompatActivity implements View.OnClickListener{

    AppDatabase db;
    EditText nameGroupTask;
    Button btnAdd, btnEdit, btnDelete;
    RecyclerView rvEmployee;
    private int id;
    GroupTaskAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group_task);
        initView();
    }
    private void initView() {
        nameGroupTask = findViewById(R.id.ed_name);


        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnEdit =findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(this);

        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }
}