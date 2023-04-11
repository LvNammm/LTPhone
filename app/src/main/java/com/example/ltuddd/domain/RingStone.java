package com.example.ltuddd.domain;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_ring_stone")
public class RingStone {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String url;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String uri) {
        this.url = uri;
    }

    public int getIdRes() {
        return idRes;
    }

    public void setIdRes(int idRes) {
        this.idRes = idRes;
    }

    public RingStone() {
    }

    public RingStone(String name, String uri, int idRes) {
        this.name = name;
        this.url = uri;
        this.idRes = idRes;
    }
}
