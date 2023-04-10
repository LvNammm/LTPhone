package com.example.ltuddd.domain;

import androidx.room.Embedded;
import androidx.room.Relation;
import com.example.ltuddd.domain.GroupTask;


import java.util.List;

public class GroupTaskWithTask {
    @Embedded
    protected GroupTask groupTask;
    @Relation(
            parentColumn = "id",
            entityColumn = "id"
    )
    protected List<Task> taskList;

    public GroupTaskWithTask(GroupTask groupTask, List<Task> taskList) {
        this.groupTask = groupTask;
        this.taskList = taskList;
    }


}
