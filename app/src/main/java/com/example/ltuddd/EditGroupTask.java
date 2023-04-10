package com.example.ltuddd;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ltuddd.adapter.GroupTaskAdapter;
import com.example.ltuddd.domain.GroupTask;

import java.util.List;

public class EditGroupTask extends AppCompatActivity implements View.OnClickListener{

    AppDatabase db;
    EditText nameGroupTask;
    Button btnAdd, btnEdit, btnDelete;
    RecyclerView ryGroupTask;
    private int id;
    GroupTaskAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group_task);
        initView();
        db = AppDatabase.getAppDatabase(this);
        List<GroupTask> list = db.groupTaskDao().getAllGroupTask();

        adapter = new GroupTaskAdapter(this,list, this);
        adapter.ReloadData(list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        ryGroupTask = findViewById(R.id.ryGroupTask);
        ryGroupTask.setLayoutManager(layoutManager);
        ryGroupTask.setAdapter(adapter);

        //get
        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return;
        }
        GroupTask groupTask = (GroupTask) bundle.get("groupTask");
        nameGroupTask.setText(groupTask.getName().toString());

        id = groupTask.getId();
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

    private void saveUser() {
        String name = nameGroupTask.getText().toString();
        //Validate
        GroupTask groupTask = new GroupTask(name, false);
        db.groupTaskDao().insertGroupTask(groupTask);
        Toast.makeText(this, "Add new user successfully hehee", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditGroupTask.this, EditGroupTask.class));
        getAllDB();
    }
    void getAllDB() {
        List<GroupTask> list = db.groupTaskDao().getAllGroupTask();
        for (GroupTask groupTask : list) {
            Log.d("TAG", "id: "+groupTask.id + " - Name: " +groupTask.name +"Status: "+ groupTask.status );
        }
    }

    void updateDB() {
        GroupTask groupTaskU = db.groupTaskDao().findGroupTask(id);
        groupTaskU.setName(nameGroupTask.getText().toString());
        if(groupTaskU != null){
            db.groupTaskDao().updateGroupTask(groupTaskU);
            Toast.makeText(this, "Update new user successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(EditGroupTask.this, EditGroupTask.class));
        }
        getAllDB();

    }

    void deleteDB() {
        GroupTask groupTaskUpdate = db.groupTaskDao().findGroupTask(id);
        if(groupTaskUpdate != null){

            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Delete user")
                    .setMessage("Are you sure you want to delete this employee?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            groupTaskUpdate.setStatus(true);
                            db.groupTaskDao().updateGroupTask(groupTaskUpdate);
                            startActivity(new Intent(EditGroupTask.this, EditGroupTask.class));
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }

        getAllDB();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAdd:
                saveUser();
                break;
            case R.id.btnEdit:
                updateDB();
                break;
            case R.id.btnDelete:
                deleteDB();
                break;
            default:
                break;
        }
    }
}