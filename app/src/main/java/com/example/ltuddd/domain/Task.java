package com.example.ltuddd.domain;

import java.io.Serializable;
import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Relation;
import androidx.room.ForeignKey;
import androidx.room.TypeConverters;


@Entity(tableName = "task", foreignKeys = {
        @ForeignKey(
                entity = GroupTask.class,
                parentColumns = {"id"},
                childColumns = {"id"},
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        )})
public class Task implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "task")
    public String task;
    @ColumnInfo(name = "status")
    public Boolean status;

    @ColumnInfo(name = "date")
    public Long date;
    @ColumnInfo(name = "isrepeat")
    public Boolean isRepeat;

    @ColumnInfo(name = "group_task_id", index = true)
    int GroupTaskId;

    public Task() {
    }
    @Ignore
    public Task(String task, Boolean status, Long date, Boolean isRepeat, int groupTaskId) {
        this.task = task;
        this.status = status;
        this.date = date;
        this.isRepeat = isRepeat;
        GroupTaskId = groupTaskId;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Boolean getIsRepeat() {
        return isRepeat;
    }

    public void setIsRepeat(Boolean repeat) {
        isRepeat = repeat;
    }

    public int getGroupTaskId() {
        return GroupTaskId;
    }

    public void setGroupTaskId(int groupTaskId) {
        GroupTaskId = groupTaskId;
    }
}
