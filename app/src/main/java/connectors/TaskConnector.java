package connectors;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import models.TaskForTeleSales;

public class TaskConnector {
    SQLiteDatabase db;

    public TaskConnector(SQLiteDatabase db) {
        this.db = db;
    }

    public List<TaskForTeleSales> getAllTasks() {
        List<TaskForTeleSales> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM TaskForTeleSales", null);
        while (c.moveToNext()) {
            list.add(new TaskForTeleSales(
                    c.getInt(0), c.getInt(1), c.getString(2),
                    c.getString(3), c.getInt(4)
            ));
        }
        return list;
    }

    public List<Integer> getAllCustomerIDs() {
        List<Integer> ids = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT ID FROM Customer", null);
        while (c.moveToNext()) ids.add(c.getInt(0));
        return ids;
    }

    public void insertTask(String title, int employeeId) {
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        ContentValues taskValues = new ContentValues();
        taskValues.put("AccountID", employeeId);
        taskValues.put("TaskTitle", title);
        taskValues.put("DateAssigned", date);
        taskValues.put("IsCompleted", 0);

        long taskId = db.insert("TaskForTeleSales", null, taskValues);

        List<Integer> allCustomers = getAllCustomerIDs();
        Collections.shuffle(allCustomers);
        for (int i = 0; i < 5 && i < allCustomers.size(); i++) {
            ContentValues detailValues = new ContentValues();
            detailValues.put("TaskForTeleSalesID", taskId);
            detailValues.put("CustomerID", allCustomers.get(i));
            detailValues.put("IsCalled", 0);
            db.insert("TaskForTeleSalesDetails", null, detailValues);
        }
    }
}
