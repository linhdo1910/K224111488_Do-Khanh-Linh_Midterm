package com.example.k224111488_dokhanhlinh_k22411ca;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import adapters.TaskAdapter;
import connectors.SQLiteConnector;
import connectors.TaskConnector;
import models.TaskForTeleSales;

public class AdminTaskListActivity extends AppCompatActivity {
    ListView listTasks;
    Button btnAddTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_task_list);

        listTasks = findViewById(R.id.listTasks);
        btnAddTask = findViewById(R.id.btnAddTask);

        SQLiteDatabase db = new SQLiteConnector(this).openDatabase();
        TaskConnector taskConnector = new TaskConnector(db);
        List<TaskForTeleSales> tasks = taskConnector.getAllTasks();

        listTasks.setAdapter(new TaskAdapter(this, tasks));

        btnAddTask.setOnClickListener(v -> {
            startActivity(new Intent(this, CreateTaskActivity.class));
        });
    }
}
