package com.example.ltuddd.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.ltuddd.R;
import com.example.ltuddd.Utils.Contain;
import com.example.ltuddd.domain.RingStone;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RingStoneAdapter extends BaseAdapter {

    private List<RingStone> mData;
    private LayoutInflater mInflater;
    private List<CheckBox> checkBoxList = new ArrayList<>();

    public RingStoneAdapter(Context context, List<RingStone> data) {
        mData = data;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.list_view, parent, false);
            holder = new ViewHolder();
            holder.textView = view.findViewById(R.id.item_text);
            holder.checkBox = view.findViewById(R.id.item_checkbox);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        String item = ((RingStone) getItem(position)).getName();
        holder.textView.setText(item);
        holder.checkBox.setChecked(false);
        checkBoxList.add(holder.checkBox);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation animation = AnimationUtils.loadAnimation(mInflater.getContext(), R.anim.animation_hint_login);
                view.startAnimation(animation);
                if(Contain.mediaPlayer != null && Contain.mediaPlayer.isPlaying()){
                    Contain.mediaPlayer.stop();
                }
                String filePath = mData.get(position).getUrl();
                Contain.mediaPlayer = new MediaPlayer();
                try {
                    Contain.mediaPlayer.setDataSource(filePath);
                    Contain.mediaPlayer.prepare();
                    Contain.mediaPlayer.start();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<checkBoxList.size();i++){
                    if(i != position){
                        checkBoxList.get(i).setChecked(false);
                    }
                }
            }
        });
        return view;
    }
    public RingStone getRingStoneisCheck(){
        for(int i=0;i<checkBoxList.size();i++){
            if(checkBoxList.get(i).isChecked()){
                return mData.get(i);
            }
        }
        return null;
    }

    private static class ViewHolder {
        TextView textView;
        CheckBox checkBox;
    }
}
