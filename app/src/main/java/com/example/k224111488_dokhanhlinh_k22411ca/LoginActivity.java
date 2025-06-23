package com.example.k224111488_dokhanhlinh_k22411ca;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import connectors.AccountConnector;
import connectors.SQLiteConnector;
import models.Account;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    RadioGroup rgAccountType;
    RadioButton rbAdmin, rbEmployee;
    Button btnLogin;

    SQLiteDatabase db;
    AccountConnector accountConnector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Ánh xạ View
        etUsername = findViewById(R.id.edtUsername);
        etPassword = findViewById(R.id.edtPassword);
        rgAccountType = findViewById(R.id.rgAccountType);
        rbAdmin = findViewById(R.id.rbAdmin);
        rbEmployee = findViewById(R.id.rbEmployee);
        btnLogin = findViewById(R.id.btnLogin);

        // Mở DB từ assets
        SQLiteConnector sqliteConnector = new SQLiteConnector(this);
        db = sqliteConnector.openDatabase();
        accountConnector = new AccountConnector(db);

        // Bắt sự kiện nút Login
        btnLogin.setOnClickListener(v -> handleLogin());
    }

    private void handleLogin() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int typeOfAccount = rbAdmin.isChecked() ? 1 : 2;

        Account account = accountConnector.checkLogin(username, password, typeOfAccount);
        if (account != null) {
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, MainActivity.class);  // Chuyển đến MainActivity
            intent.putExtra("accountId", account.getId());
            intent.putExtra("typeOfAccount", account.getTypeOfAccount());
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Invalid credentials or role", Toast.LENGTH_SHORT).show();
        }
    }

    // Thêm menu "About"
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
