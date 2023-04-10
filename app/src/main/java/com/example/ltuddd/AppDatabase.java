package com.example.ltuddd;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.ltuddd.dao.GroupTaskDao;
import com.example.ltuddd.dao.TaskDao;
import com.example.ltuddd.domain.Task;
import com.example.ltuddd.domain.GroupTask;

@Database(entities = {Task.class, GroupTask.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase appDatabase;
    public abstract TaskDao taskDao();
    public abstract GroupTaskDao groupTaskDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(context,
                    AppDatabase.class, "appdatabase.db").allowMainThreadQueries().build();
        }
        return appDatabase;
    }
}