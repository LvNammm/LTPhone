package com.example.ltuddd;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.ltuddd.adapter.RingStoneAdapter;
import com.example.ltuddd.Utils.Contain;
import com.example.ltuddd.Utils.MakeToast;
import com.example.ltuddd.domain.RingStone;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChooseRingStone extends AppCompatActivity {
    MediaPlayer mediaPlayer;

    AppDbNam db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_ring_stone);
        db = Room.databaseBuilder(getApplicationContext(),
                AppDbNam.class, "tbl_ring_stone").allowMainThreadQueries().build();
        List<RingStone> ringStoneList = db.ringStoneDao().getAll();
        ListView listView = findViewById(R.id.listviewRt);
        if(ringStoneList == null || ringStoneList.size() ==0 ){
            try {
                addDefault("Dậy đê ông cháu ơi", R.raw.abc);
                addDefault("Flowers", R.raw.flowers);
                addDefault("Nhịp điệu cha cha", R.raw.nhip_dieu_cha_cha_cha);
                addDefault("Ồ má xê", R.raw.o_ma_xe);
                addDefault("Vì anh remix", R.raw.vi_anh_remix);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        ringStoneList = db.ringStoneDao().getAll();
        RingStoneAdapter adapter = new RingStoneAdapter(this,ringStoneList);
        listView.setAdapter(adapter);



        FloatingActionButton btnAdd = findViewById(R.id.floatingActionButton);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseFile(view);
            }
        });
        Button btnChoose = findViewById(R.id.buttonChoose);
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RingStone ringStone = adapter.getRingStoneisCheck();
                if(ringStone != null){
                    SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Contain.keySavePathRingStone, ringStone.getUrl());
                    editor.apply();
                    MakeToast.make("Thay đổi nhạc thành công",getApplicationContext());
                }
                else {
                    MakeToast.make("Thay đổi nhạc không thành công",getApplicationContext());
                }
                Contain.stopMediaPlayer();
                startActivity(new Intent(getApplicationContext(),ListTask.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        stopMedia();
        super.onBackPressed();
    }
    private void stopMedia(){
        if(Contain.mediaPlayer != null && Contain.mediaPlayer.isPlaying()){
            Contain.mediaPlayer.stop();
        }
    }
    public void chooseFile(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    1);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            MakeToast.make("Lỗi chọn file", getApplicationContext());
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String fileName = new Date().getTime() +".mp3";
            Uri uri = data.getData();

            // Lấy đường dẫn thư mục lưu trữ bên ngoài của thiết bị
            File externalDir = Environment.getExternalStorageDirectory();

// Lấy đường dẫn thư mục music trong thư mục lưu trữ bên ngoài
            File musicDir = new File(externalDir, "Music");


            if (!musicDir.exists()) {
                musicDir.mkdirs();
            }


            InputStream in = null;
            try {
                in = getContentResolver().openInputStream(uri);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            File outFile = new File(musicDir, fileName);
            OutputStream out = null;
            try {
                out = new FileOutputStream(outFile);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            byte[] buffer = new byte[1024];
            int length;
            while (true) {
                try {
                    if (!((length = in.read(buffer)) > 0)) break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    out.write(buffer, 0, length);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            String name = getFileName(uri).split("\\.")[0];
            db.ringStoneDao().insertRingStone(new RingStone(name, outFile.getAbsolutePath(), -1));
            startActivity(new Intent(getApplicationContext(),ChooseRingStone.class));
        }
    }
    private void addDefault(String name, int i) throws IOException {
        File externalDir = Environment.getExternalStorageDirectory();


        File musicDir = new File(externalDir, "Music");
        InputStream inputStream = getResources().openRawResource(i);
        if (!musicDir.exists()) {
            musicDir.mkdirs();
        }

        // Tạo một luồng ghi ra đến tập tin mới trong thư mục music
        String fileName = new Date().getTime() +".mp3";
        File outFile = new File(musicDir, fileName);
        OutputStream out = Files.newOutputStream(outFile.toPath());


        byte[] buffer = new byte[1024];
        int length;
        while (true) {
            try {
                if (!((length = inputStream.read(buffer)) > 0)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            out.write(buffer, 0, length);
        }
        db.ringStoneDao().insertRingStone(new RingStone(name, outFile.getAbsolutePath(), R.raw.abc));
        inputStream.close();
        out.close();
    }
    private String getFileName(Uri uri){
        String filename = null;

        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            if (nameIndex >= 0) {
                filename = cursor.getString(nameIndex);
            }
        }
        cursor.close();

        if (filename == null) {
            // if filename is null, use the Uri to get the last segment of the path as the filename
            filename = uri.getLastPathSegment();
        }

        File file = new File(filename);
        return file.getName();
    }
}