package connectors;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class SQLiteConnector {

    private static final String DATABASE_NAME = "Data.db";
    private static final String DB_PATH_SUFFIX = "/databases/";
    private SQLiteDatabase database = null;
    private final Activity context;

    public SQLiteConnector(Activity context) {
        this.context = context;
        copyDatabaseFromAssets(); // Ensure DB exists when created
    }

    public SQLiteDatabase openDatabase() {
        String dbPath = getDatabasePath();
        database = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
        return database;
    }

    private String getDatabasePath() {
        return context.getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }

    private void copyDatabaseFromAssets() {
        try {
            File dbFile = new File(getDatabasePath());
            if (dbFile.exists()) {
                return; // already copied
            }

            File dbDir = new File(context.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!dbDir.exists()) {
                dbDir.mkdir();
            }

            InputStream inputStream = context.getAssets().open(DATABASE_NAME);
            OutputStream outputStream = new FileOutputStream(dbFile);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();

            Log.d("SQLiteConnector", "Database copied from assets.");

        } catch (Exception e) {
            Log.e("SQLiteConnector", "Error copying database", e);
        }
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public void closeDatabase() {
        if (database != null && database.isOpen()) {
            database.close();
        }
    }
}
