package connectors;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import models.Account;

public class AccountConnector {

    private SQLiteDatabase db;

    public AccountConnector(SQLiteDatabase database) {
        this.db = database;
    }

    public Account checkLogin(String username, String password, int typeOfAccount) {
        Cursor cursor = db.rawQuery(
                "SELECT * FROM Account WHERE Username = ? AND Password = ? AND TypeOfAccount = ?",
                new String[]{username, password, String.valueOf(typeOfAccount)}
        );

        if (cursor.moveToFirst()) {
            Account account = new Account(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3)
            );
            cursor.close();
            return account;
        }

        cursor.close();
        return null;
    }
}

