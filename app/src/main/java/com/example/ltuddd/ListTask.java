package com.example.ltuddd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ltuddd.adapter.ToDoAdapter;
import com.example.ltuddd.domain.GroupTask;
import com.example.ltuddd.domain.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ListTask extends AppCompatActivity implements DialogCloseListener{

    private AppDatabase db;
    private RecyclerView tasksRecyclerView;
    private ToDoAdapter tasksAdapter;
    private List<Task> taskList;
    private FloatingActionButton fab;

    private List<GroupTask> groupTasks;

    String nameGroupTask = "";
    int idGroupTask ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_group_task);

//        Objects.requireNonNull(getSupportActionBar()).hide();
        db = AppDatabase.getAppDatabase(this);
        groupTasks = db.groupTaskDao().getAllGroupTask();
        tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new ToDoAdapter(taskList,ListTask.this, db);
        tasksAdapter.ReloadData(taskList);
        tasksRecyclerView.setAdapter(tasksAdapter);

        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new RecyclerItemTouchHelper(tasksAdapter, getApplicationContext()));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);

        fab = findViewById(R.id.fab);

        taskList = db.taskDao().getAllTask();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListTask.this, AddNewTask.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void handleDiglogClose(DialogInterface dialog) {
        taskList = db.taskDao().getAllTask();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);
        tasksAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.group_task, menu);

        MenuItem menuItem = menu.findItem(R.id.action_overflow);

        SubMenu subMenu = menuItem.getSubMenu();


        subMenu.add(0, 0, Menu.NONE, "All lists").setIcon(android.R.drawable.ic_menu_edit);

        int i = 1;
        if(groupTasks != null){
            for (GroupTask groupTask : groupTasks){
                subMenu.add(i, groupTask.getId(), Menu.NONE, groupTask.getName()).setIcon(android.R.drawable.arrow_up_float);
                i ++;
            }
        }
        subMenu.add(i, -1, Menu.NONE, "Finished").setIcon(android.R.drawable.ic_menu_delete);
        subMenu.add(i+1, -2, Menu.NONE, "Add Group Task").setIcon(android.R.drawable.ic_input_add);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        taskList = getListTasksByGroup(id);
        System.out.println(taskList);
        if(taskList != null){
            Collections.reverse(taskList);
            tasksAdapter.setTasks(taskList);
        }


        TextView textView = findViewById(R.id.tasksText);
        if (!"".equals(nameGroupTask)) {
            textView.setText(nameGroupTask);
        }
        return super.onOptionsItemSelected(item);
    }

    public List<Task> getListTasksByGroup (int id) {
        if(id == 0){
            nameGroupTask = "All lists";
            return db.taskDao().getAllTask();
        } else if ((id == -1)) {
            nameGroupTask = "Finished";
            return db.taskDao().getTaskFinished();
        }else if ((id == -2)){
            Intent intent = new Intent(ListTask.this, EditGroupTask.class);
            startActivity(intent);
            return null;
        }else {
            for(GroupTask groupTask : db.groupTaskDao().getAllGroupTask()){
                if(id == groupTask.getId()){
                    nameGroupTask = groupTask.getName();
                    return db.taskDao().getTaskByGroupId(id);
                }
            }
        }
        return null;
    }
}