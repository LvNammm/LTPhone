package com.example.ltuddd.adapter;

import static com.example.ltuddd.AddNewTask.isUpdate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ltuddd.AddNewTask;
import com.example.ltuddd.AppDatabase;
import com.example.ltuddd.ListTask;
import com.example.ltuddd.R;
import com.example.ltuddd.domain.GroupTask;
import com.example.ltuddd.domain.Task;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    private static List<Task> todoList;
    private ListTask activity;
    private AppDatabase db;

    private static AppDatabase db2;

    private Context context;

    public ToDoAdapter(List<Task> todoList, ListTask activity, AppDatabase db) {
        this.todoList = todoList;
        this.activity = activity;
        this.db = db;
        this.db2 = db;
    }
    public void ReloadData(List<Task> list) {
        todoList = list;
        notifyDataSetChanged();
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


    public void editItem(int position) {
        Task item = todoList.get(position-1);

        isUpdate = true;
        Intent intent = new Intent(context, AddNewTask.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);;
        intent.putExtra("id", item.getId());
        intent.putExtra("groupTaskId", item.getGroupTaskId());
        intent.putExtra("task", item.getTask());
        intent.putExtra("status", item.getStatus());
        intent.putExtra("isRepeat", item.getIsRepeat());
        intent.putExtra("date", item.getDate());
        context.startActivity(intent);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox task;

        ViewHolder(View view) {
            super(view);
            task = view.findViewById(R.id.todoCheckBox);
            task.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        boolean isChecked = task.isChecked();
                        Task t = todoList.get(position);
                        t.setStatus(isChecked);
                        db2.taskDao().updateTask(t);
                    }
                }
            });

        }

    }
    public Context getContext() {
        return activity;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
