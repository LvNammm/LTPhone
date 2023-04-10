package com.example.ltuddd.dto;


import com.example.ltuddd.domain.Task;

public class TaskDto{
    public int id;
    public String task;
    public Boolean status;
    public GroupTaskDto groupTask;

    public TaskDto() {
    }

    public TaskDto(Task entity) {
        this.id = entity.getId();
        this.task = entity.getTask();
        this.status = entity.getStatus();
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


}
