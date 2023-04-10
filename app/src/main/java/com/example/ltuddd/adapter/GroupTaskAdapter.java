package com.example.ltuddd.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ltuddd.EditGroupTask;
import com.example.ltuddd.R;
import com.example.ltuddd.domain.GroupTask;

import java.util.List;

public class GroupTaskAdapter extends RecyclerView.Adapter {
    Activity activity;
    List<GroupTask> listGroupTask;
    Context mContext;

    public GroupTaskAdapter(Activity activity, List<GroupTask> listGroupTask, Context mContext) {
        this.activity = activity;
        this.listGroupTask = listGroupTask;
        this.mContext = mContext;
    }

    public void ReloadData(List<GroupTask> list) {
        this.listGroupTask = list;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = activity.getLayoutInflater().inflate(R.layout.activitty_group_task_item, parent, false);
        GroupTaskHoldeer holder = new GroupTaskHoldeer(viewItem);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GroupTaskHoldeer vh = (GroupTaskHoldeer) holder;

        GroupTask model = listGroupTask.get(position);

        vh.tv_name.setText(model.name + "");
        ((GroupTaskHoldeer) holder).relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickGoToDetail(model);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listGroupTask.size();
    }

    public class GroupTaskHoldeer extends RecyclerView.ViewHolder {
        TextView tv_name;
        LinearLayout relativeLayout;

        public GroupTaskHoldeer(View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.layout_item);
            tv_name = itemView.findViewById(R.id.tv_name);
        }
    }

    private void onClickGoToDetail(GroupTask groupTask) {
        Intent intent = new Intent(mContext, EditGroupTask.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("groupTask", groupTask);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }
}
