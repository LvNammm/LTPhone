package com.example.ltuddd;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = AppDatabase.getAppDatabase(this);
        groupTasks = db.groupTaskDao().getAllGroupTask();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.group_task, menu);

        MenuItem menuItem = menu.findItem(R.id.action_overflow);

        SubMenu subMenu = menuItem.getSubMenu();


        subMenu.add(0, 0, Menu.NONE, "All lists");

        int i = 1;
        if(groupTasks != null){
            for (GroupTask groupTask : groupTasks){
                subMenu.add(i, groupTask.getId(), Menu.NONE, groupTask.getName());
                i ++;
            }
        }
        subMenu.add(i, -1, Menu.NONE, "Finished");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        System.out.println(id);
        return super.onOptionsItemSelected(item);
    }
}
