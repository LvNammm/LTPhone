package com.example.ltuddd.domain;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_ring_stone")
public class RingStone {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int uri;

    private int idRes;

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

    public int getUri() {
        return uri;
    }

    public void setUri(int uri) {
        this.uri = uri;
    }

    public int getIdRes() {
        return idRes;
    }

    public void setIdRes(int idRes) {
        this.idRes = idRes;
    }

    public RingStone() {
    }

    public RingStone(String name, int uri, int idRes) {
        this.name = name;
        this.uri = uri;
        this.idRes = idRes;
    }
}
