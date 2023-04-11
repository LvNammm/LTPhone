package com.example.ltuddd.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.ltuddd.domain.GroupTask;
import com.example.ltuddd.domain.RingStone;

import java.util.List;

@Dao
public interface RingStoneDao {
    @Insert(entity = RingStone.class)
    void insertRingStone(RingStone ringStone);

    @Query("SELECT * FROM tbl_ring_stone where name = :name")
    List<RingStone> findByName(String name);
    @Query("SELECT * FROM tbl_ring_stone")
    List<RingStone> getAll();

}
