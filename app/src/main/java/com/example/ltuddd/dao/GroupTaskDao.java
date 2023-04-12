package com.example.ltuddd.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ltuddd.domain.GroupTask;
import com.example.ltuddd.dto.GroupTaskDto;

import java.util.List;

@Dao
public interface GroupTaskDao {
    @Insert
    void insertGroupTask(GroupTask groupTask);

    @Update
    void updateGroupTask(GroupTask groupTask);

    @Query("SELECT * FROM group_task where id=:id")
    GroupTask findGroupTask(int id);

    @Query("SELECT * FROM group_task where status = false or status is null")
    List<GroupTask> getAllGroupTask();

    @Delete
    void deleteGroupTask(GroupTask groupTask);
}
