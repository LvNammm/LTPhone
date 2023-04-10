package com.example.ltuddd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.ltuddd.adapter.ToDoAdapter;
import com.example.ltuddd.domain.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ListTask extends AppCompatActivity {

    private AppDatabase db;
    private RecyclerView tasksRecyclerView;
    private ToDoAdapter tasksAdapter;
    private List<Task> taskList;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_task);

        Objects.requireNonNull(getSupportActionBar()).hide();
        db = AppDatabase.getAppDatabase(this);

        tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new ToDoAdapter(taskList,ListTask.this, db);
        tasksRecyclerView.setAdapter(tasksAdapter);


        fab = findViewById(R.id.fab);

        taskList = db.taskDao().getAllTask();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);

//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
//            }
//        });
    }
}