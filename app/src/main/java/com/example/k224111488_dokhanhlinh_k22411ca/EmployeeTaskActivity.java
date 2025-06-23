package com.example.k224111488_dokhanhlinh_k22411ca;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import adapters.TaskDetailAdapter;
import connectors.SQLiteConnector;
import models.Customer;

public class EmployeeTaskActivity extends AppCompatActivity {

    ListView lvCustomers;
    ArrayList<Customer> customers;
    TaskDetailAdapter adapter;
    SQLiteDatabase db;
    int accountId, taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_task);

        lvCustomers = findViewById(R.id.lvCustomers);
        customers = new ArrayList<>();
        adapter = new TaskDetailAdapter(this, customers);
        lvCustomers.setAdapter(adapter);

        SQLiteConnector connector = new SQLiteConnector(this);
        db = connector.openDatabase();

        // Nhận accountId từ LoginActivity
        accountId = getIntent().getIntExtra("accountId", -1);

        loadTodayTask();

        lvCustomers.setOnItemClickListener((parent, view, position, id) -> {
            Customer customer = customers.get(position);
            markAsCalled(customer.getId());

            Toast.makeText(this, "Called: " + customer.getName(), Toast.LENGTH_SHORT).show();
            loadTodayTask(); // Reload data

            if (isTaskCompleted()) {
                updateTaskCompleted();
                Toast.makeText(this, "Task completed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadTodayTask() {
        customers.clear();
        taskId = -1;

        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        Cursor cursor = db.rawQuery("SELECT ID FROM TaskForTeleSales WHERE AccountID = ? AND DateAssigned = ?",
                new String[]{String.valueOf(accountId), today});
        if (cursor.moveToFirst()) {
            taskId = cursor.getInt(0);
        }
        cursor.close();

        if (taskId == -1) return;

        Cursor c = db.rawQuery("SELECT c.ID, c.Name, c.Phone, d.IsCalled FROM TaskForTeleSalesDetails d " +
                "JOIN Customer c ON c.ID = d.CustomerID WHERE d.TaskForTeleSalesID = ?", new String[]{String.valueOf(taskId)});
        while (c.moveToNext()) {
            Customer cust = new Customer();
            cust.setId(c.getInt(0));
            cust.setName(c.getString(1));
            cust.setPhone(c.getString(2));
            cust.setIsCalled(c.getInt(3));
            customers.add(cust);
        }
        c.close();
        adapter.notifyDataSetChanged();
    }

    private void markAsCalled(int customerId) {
        db.execSQL("UPDATE TaskForTeleSalesDetails SET IsCalled = 1 WHERE TaskForTeleSalesID = ? AND CustomerID = ?",
                new Object[]{taskId, customerId});
    }

    private boolean isTaskCompleted() {
        Cursor c = db.rawQuery("SELECT COUNT(*) FROM TaskForTeleSalesDetails WHERE TaskForTeleSalesID = ? AND IsCalled = 0",
                new String[]{String.valueOf(taskId)});
        boolean done = false;
        if (c.moveToFirst()) {
            done = c.getInt(0) == 0;
        }
        c.close();
        return done;
    }

    private void updateTaskCompleted() {
        db.execSQL("UPDATE TaskForTeleSales SET IsCompleted = 1 WHERE ID = ?", new Object[]{taskId});
    }
}
