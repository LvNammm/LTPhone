package com.example.ltuddd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ltuddd.dao.GroupTaskDao;
import com.example.ltuddd.dao.RingStoneDao;
import com.example.ltuddd.domain.RingStone;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ChooseRingStone extends AppCompatActivity {
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_ring_stone);
        AppDbNam db = Room.databaseBuilder(getApplicationContext(),
                AppDbNam.class, "tbl_ring_stone").allowMainThreadQueries().build();
        List<RingStone> ringStoneList = db.ringStoneDao().getAll();
        ListView listView = findViewById(R.id.listviewRt);
        if(ringStoneList == null || ringStoneList.size() ==0 ){
            db.ringStoneDao().insertRingStone(new RingStone("bài nhạc 1", R.raw.abc, R.raw.abc));
        }
        ringStoneList = db.ringStoneDao().getAll();
        List<String> list = new ArrayList<>();
        for(RingStone r: ringStoneList){
            list.add(r.getName());
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                list);
        listView.setAdapter(adapter);

        List<RingStone> finalRingStoneList = ringStoneList;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(mediaPlayer != null && mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                int resID= finalRingStoneList.get(i).getIdRes();
                mediaPlayer=MediaPlayer.create(getApplicationContext(),resID);
                mediaPlayer.start();
            }
        });
    }

    @Override
    public void onBackPressed() {
        stopMedia();
        super.onBackPressed();
    }
    private void stopMedia(){
        if(mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
    }
}