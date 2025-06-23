package com.example.k224111488_dokhanhlinh_k22411ca;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import connectors.SQLiteConnector;
import connectors.TaskConnector;

public class CreateTaskActivity extends AppCompatActivity {
    EditText etTaskTitle;
    Spinner spinnerEmployee;
    Button btnCreate;

    HashMap<String, Integer> employeeMap = new HashMap<>();
    TaskConnector taskConnector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        etTaskTitle = findViewById(R.id.etTaskTitle);
        spinnerEmployee = findViewById(R.id.spinnerEmployee);
        btnCreate = findViewById(R.id.btnCreate);

        SQLiteDatabase db = new SQLiteConnector(this).openDatabase();
        taskConnector = new TaskConnector(db);

        loadEmployees(db);

        btnCreate.setOnClickListener(v -> {
            String title = etTaskTitle.getText().toString().trim();
            String name = spinnerEmployee.getSelectedItem().toString();
            int empId = employeeMap.get(name);
            taskConnector.insertTask(title, empId);
            Toast.makeText(this, "Created", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void loadEmployees(SQLiteDatabase db) {
        List<String> names = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT ID, Username FROM Account WHERE TypeOfAccount = 2", null);
        while (c.moveToNext()) {
            employeeMap.put(c.getString(1), c.getInt(0));
            names.add(c.getString(1));
        }
        spinnerEmployee.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, names));
    }
}