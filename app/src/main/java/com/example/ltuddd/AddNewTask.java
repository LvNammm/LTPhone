package com.example.ltuddd;



import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ltuddd.Utils.Notification;
import com.example.ltuddd.adapter.GroupTaskAdapter;
import com.example.ltuddd.domain.GroupTask;
import com.example.ltuddd.domain.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class AddNewTask extends AppCompatActivity implements View.OnClickListener {

    private final String CHANEL_ID = "chanel_id";
    AppDatabase db;
    EditText name;
    TimePicker time;
    DatePicker date;
    Button btnAdd;
    CheckBox isRepeat;

    Calendar calendar = Calendar.getInstance();

    Task task = new Task();

    private List<GroupTask> groupTasks;
    TextView selection;

    int idGroupTask;

    public static boolean isUpdate = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        createNotificationChannel();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_task);
        db = AppDatabase.getAppDatabase(this);


        initView();

        if(isUpdate) {
            if(-999 == getIntent().getIntExtra("id",-999)) return;
            task.setId(getIntent().getIntExtra("id", 0));

//            if(-999 == getIntent().getIntExtra("groupTaskId",-999)) return;
//            task.setGroupTaskId(getIntent().getIntExtra("groupTaskId", 0));


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

        groupTasks = db.groupTaskDao().getAllGroupTask();

        selection = (TextView) findViewById(R.id.selection);


        Spinner spin = (Spinner) findViewById(R.id.spinner1);

        ArrayAdapter<GroupTask> adapter = new ArrayAdapter<GroupTask>
                (
                        this,
                        android.R.layout.simple_spinner_item,
                        groupTasks
                );

        adapter.setDropDownViewResource
                (android.R.layout.simple_list_item_single_choice);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(new MyProcessEvent());
    }
    public class MyProcessEvent implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            GroupTask selectedGroupTask = groupTasks.get(arg2);
            idGroupTask = selectedGroupTask.getId();
            selection.setText(selectedGroupTask.getName());
        }

        public void onNothingSelected(AdapterView<?> arg0) {
            selection.setText("");
        }
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
        calendar.set(date.getYear(), date.getMonth(), date.getDayOfMonth(), time.getHour(), time.getMinute());
        String nameTask = name.getText().toString();
        Boolean isRepeatTask = isRepeat.isChecked();
        System.out.println(calendar.getTime());
        //Validate
        if(!isUpdate){
            task = new Task(nameTask, false, calendar.getTimeInMillis(), isRepeatTask, idGroupTask);
            System.out.println("trước lưu: "+task);
            db.taskDao().insertTask(task);
            System.out.println("insert");
        }
        else {
            task.task = nameTask;
            task.date = calendar.getTimeInMillis();
            task.setGroupTaskId(idGroupTask);
            task.setIsRepeat(isRepeatTask);
            task.status = false;
            System.out.println("update");
            db.taskDao().updateTask(task);
        }
        getAllDB();

        System.out.println("task:"+task);
            task = db.taskDao().getTaskByTime(task.date).get(0);
            GroupTask groupTask = db.groupTaskDao().findGroupTask(task.getGroupTaskId());
            System.out.println(task);
            calendar.setTimeInMillis(task.getDate());
            Notification.create(getApplicationContext(),task.getId(),groupTask.getName(),task.getTask(),getSystemService(Context.ALARM_SERVICE),task.isRepeat,calendar);
        Toast.makeText(this, "Add new user successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), ListTask.class));
    }
    void getAllDB() {
        List<Task> list = db.taskDao().getAllTask();
        for (Task task : list) {
            Log.d("TAG", task.toString() );
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
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "testApp";
            String description = "decrep testApp";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void onBackPressed() {
        Intent intent = new Intent(AddNewTask.this, ListTask.class);
        startActivity(intent);
    }

}
