package com.example.ltuddd.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.ltuddd.domain.Task;

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
    @Query("SELECT * FROM task where task.group_task_id =:groupId and (status = false or status is null)")
    public List<Task> getUsersWithPlaylists(int groupId);

    @Query("SELECT * FROM task where status = false or status is null")
    List<Task> getAllTask();

    @Query("SELECT * FROM task where (status = false or status is null) and date = :time")
    List<Task> getTaskByTime(Long time);

    @Delete
    void deleteTask(Task task);
}
