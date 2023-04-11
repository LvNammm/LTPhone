package com.example.ltuddd;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import com.example.ltuddd.domain.GroupTask;
import com.example.ltuddd.domain.Task;

import java.util.List;

public class Home extends AppCompatActivity {
    private AppDatabase db;
    private List<GroupTask> groupTasks;

    static String nameGroupTask = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = AppDatabase.getAppDatabase(this);
        groupTasks = db.groupTaskDao().getAllGroupTask();
        startActivity(new Intent(getApplicationContext(),ListTask.class));
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
        System.out.println(id);
        System.out.println(getListTasksByGroup(id));
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
            Intent intent = new Intent(Home.this, EditGroupTask.class);
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
