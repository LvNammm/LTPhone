package com.example.ltuddd.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.ltuddd.domain.GroupTask;
import com.example.ltuddd.domain.GroupTaskWithTask;
import com.example.ltuddd.domain.Task;
import com.example.ltuddd.dto.GroupTaskDto;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    void insertTask(Task task);

    @Update
    void updateTask(Task task);

    @Query("SELECT * FROM task where id=:id")
    Task findTask(int id);

    @Transaction
    @Query("SELECT * FROM group_task where id=:groupId")
    public List<GroupTaskWithTask> getUsersWithPlaylists(int groupId);

    @Query("SELECT * FROM task")
    List<Task> getAllTask();

    @Delete
    void deleteTask(Task task);
}
