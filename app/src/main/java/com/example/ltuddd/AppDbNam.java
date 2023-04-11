package com.example.ltuddd;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.ltuddd.dao.RingStoneDao;
import com.example.ltuddd.domain.GroupTask;
import com.example.ltuddd.domain.RingStone;
import com.example.ltuddd.domain.Task;

@Database(entities = {RingStone.class}, version = 1)
public abstract class AppDbNam  extends RoomDatabase {
    public abstract RingStoneDao ringStoneDao();
}
