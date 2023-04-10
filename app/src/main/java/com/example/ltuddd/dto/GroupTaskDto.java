package com.example.ltuddd.dto;


import com.example.ltuddd.domain.GroupTask;



public class GroupTaskDto {
    public int id;
    public String name;
    public Boolean status;

    public GroupTaskDto() {
    }

    public GroupTaskDto(GroupTask entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.status = entity.getStatus();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
