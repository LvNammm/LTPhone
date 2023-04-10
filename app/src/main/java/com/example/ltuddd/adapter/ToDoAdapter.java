package com.example.ltuddd.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ltuddd.AppDatabase;
import com.example.ltuddd.ListTask;
import com.example.ltuddd.R;
import com.example.ltuddd.domain.Task;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    private List<Task> todoList;
    private ListTask activity;
    private AppDatabase db;

    public ToDoAdapter(List<Task> todoList, ListTask activity, AppDatabase db) {
        this.todoList = todoList;
        this.activity = activity;
        this.db = db;
    }

    @NonNull
    @Override
    public ToDoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoAdapter.ViewHolder holder, int position) {
        final Task item = todoList.get(position);

        holder.task.setText(item.getTask());
        holder.task.setChecked(item.getStatus());
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    item.setStatus(true);
                    db.taskDao().updateTask(item);
                } else {
                    item.setStatus(false);
                    db.taskDao().updateTask(item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public void setTasks(List<Task> todoList) {
        this.todoList = todoList;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        Task item = todoList.get(position);
        db.taskDao().deleteTask(item);
        todoList.remove(position);
        notifyItemRemoved(position);
    }

    public void editItem(int position, Task entity) {
        Task item = todoList.get(position);
        item.setStatus(entity.getStatus());
        item.setTask(entity.getTask());
        item.setDate(entity.getDate());
        item.setIsRepeat(entity.getIsRepeat());
        item.setGroupTaskId(entity.getGroupTaskId());
        db.taskDao().updateTask(item);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox task;

        ViewHolder(View view) {
            super(view);
            task = view.findViewById(R.id.todoCheckBox);
        }
    }
    public Context getContext() {
        return activity;
    }
}
