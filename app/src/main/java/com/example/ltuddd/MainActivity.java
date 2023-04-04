package com.example.ltuddd;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.ltuddd.Adapter.TodoAdapter;
import com.example.ltuddd.Model.ToDoModel;
import com.example.ltuddd.Utils.DatabaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import androidx.appcompat.app.AppCompatActivity;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogCloseListener{


    // Đoạn này Phong viết đừng xoá nha
    private RecyclerView taskRecyclerView;
    private TodoAdapter taskAdapter;
    private List<ToDoModel> taskList;

    private DatabaseHandler db;
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tasks);

//        getSupportActionBar().hide();

        db = new DatabaseHandler(this);
        db.openDatabase();
        taskList = new ArrayList<>();

        taskRecyclerView = findViewById(R.id.taskRecyclerView);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new TodoAdapter(db, this);
        taskRecyclerView.setAdapter(taskAdapter);

        fab = findViewById(R.id.fab);

        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        taskAdapter.setTask(taskList);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });
    }


    @Override
    public void handleDiglogClose(DialogInterface dialog) {
        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        taskAdapter.setTask(taskList);
        taskAdapter.notifyDataSetChanged();

    }
}