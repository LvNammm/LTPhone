package com.example.ltuddd;



import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ltuddd.adapter.GroupTaskAdapter;
import com.example.ltuddd.domain.GroupTask;
import com.example.ltuddd.domain.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class AddNewTask extends AppCompatActivity implements View.OnClickListener {

    AppDatabase db;
    EditText name;
    TimePicker time;
    DatePicker date;
    Button btnAdd;
    private int id;
    CheckBox isRepeat;

    Calendar calendar = Calendar.getInstance();

    Task task = new Task();

    public static boolean isUpdate = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_task);
        db = AppDatabase.getAppDatabase(this);

        addOne();
        initView();

        if(isUpdate) {
            if(-999 == getIntent().getIntExtra("id",-999)) return;
            task.setId(getIntent().getIntExtra("id", 0));

            if(-999 == getIntent().getIntExtra("groupTaskId",-999)) return;
            task.setId(getIntent().getIntExtra("groupTaskId", 0));


            name.setText(getIntent().getStringExtra("task"));
            if(-999L != getIntent().getLongExtra("date",-999L)){
                Long timeInMillis = getIntent().getLongExtra("date", -1L);
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(timeInMillis);

                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                time.setHour(hour);
                time.setMinute(minute);

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        (view, year1, monthOfYear, dayOfMonth1) -> {
                        }, year, month, dayOfMonth);
            }

            isRepeat.setChecked(getIntent().getBooleanExtra("isRepeat", false));

        }

    }
    public void addOne(){
        GroupTask groupTask = new GroupTask("phong dep trai", false);
        db.groupTaskDao().insertGroupTask(groupTask);
    }
    private void initView() {
        name = findViewById(R.id.newTaskText);
        date = findViewById(R.id.datePicker);
        time = findViewById(R.id.simpleTimePicker);
        isRepeat = findViewById(R.id.isRepeat);

        btnAdd = findViewById(R.id.newTaskButton);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    saveUser();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    private void saveUser() throws ParseException {
        calendar.set(date.getYear(), date.getMonth()+1, date.getDayOfMonth(), time.getHour(), time.getMinute());
        String nameTask = name.getText().toString();
        Boolean isRepeatTask = isRepeat.isChecked();
        //Validate
        if(!isUpdate){
            task = new Task(nameTask, false, calendar.getTimeInMillis(), isRepeatTask, 1);
        }
        db.taskDao().insertTask(task);
        Toast.makeText(this, "Add new user successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(AddNewTask.this, AddNewTask.class));
        getAllDB();
    }
    void getAllDB() {
        List<Task> list = db.taskDao().getAllTask();
        for (Task task : list) {
            Log.d("TAG", "id: "+task.id + " - Name: " +task.task +"Status: "+ task.status );
        }
    }
    public static AddNewTask newInstance(){
        return new AddNewTask();
    }
    @Override
    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.btnAdd:
//                try {
//                    saveUser();
//                } catch (ParseException e) {
//                    throw new RuntimeException(e);
//                }
//                break;
//            default:
//                break;
//        }
    }

}
