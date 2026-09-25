package com.myprojects.assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.util.Log;
import androidx.annotation.Nullable;
import com.myprojects.assignment.features.CardData;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String databaseName = "data_assignment.db";
    private static final String DATABASE_NAME = "data_assignment.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Pharmaceuticals";
    private static final String COLUMN_ID = "pharmaceuticals_id";
    private static final String COLUMN_LABEL = "pharmaceuticals_name";

    //main constructor
    public DataBaseHelper(@Nullable Context context) {super(context, databaseName , null, 1);}

    //creating database datatable if not exist
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createUserTable = "CREATE TABLE User (" +
                "username TEXT PRIMARY KEY," +
                "password TEXT," +
                "mail TEXT" +
                ");";
        String createPharmaceuticalsTable = "CREATE TABLE Pharmaceuticals (" +
                "pharmaceuticals_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "pharmaceuticals_name TEXT," +
                "pharmaceuticals_type_medication TEXT," +
                "pharmaceuticals_type TEXT," +
                "pharmaceuticals_company TEXT" +
                ");";
        sqLiteDatabase.execSQL(createUserTable);
        sqLiteDatabase.execSQL(createPharmaceuticalsTable);


    }

    // upgrade datatable
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE if exists User");
        sqLiteDatabase.execSQL("DROP TABLE if exists Pharmaceuticals");
    }

    // insert into users
    Boolean insertData(String username,  String mail, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        values.put("mail", mail);

        long newRowId = db.insert("User", null, values);

        if (newRowId == -1) return false;
        else return true;
    }

    // check the availability of username
    public Boolean chkUsername(String username) {
        SQLiteDatabase sqlLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqlLiteDatabase.rawQuery("SELECT * FROM User WHERE username = ? ", new String[]{username});
        if (cursor.getCount() > 0) return true;
        else return false;
    }

    // check login credentials
    public Boolean chkUserPassword(String  username, String password) {
        SQLiteDatabase sqlLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqlLiteDatabase.rawQuery("SELECT * FROM User WHERE mail = ? and password = ? ", new String[]{username, password});
        if (cursor.getCount() > 0) return true;
        else return false;
    }
    public boolean InsertData(String pharmaceuticals_name, String pharmaceuticals_type_medication, String pharmaceuticals_type, String pharmaceuticals_company) {
        // Assuming you have a SQLiteOpenHelper instance called dbHelper
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("pharmaceuticals_name", pharmaceuticals_name);
        values.put("pharmaceuticals_type_medication", pharmaceuticals_type_medication);
        values.put("pharmaceuticals_type", pharmaceuticals_type);
        values.put("pharmaceuticals_company", pharmaceuticals_company);
        long newRowId = db.insert("Pharmaceuticals", null, values);
        if (newRowId == -1)
            return false;
        else
            return true;
    }
    public void updatePharmaceuticalsName(int pharmaceuticalsId, String newPharmaceuticalsName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("pharmaceuticals_name", newPharmaceuticalsName);

        int rowsUpdated = db.update("Pharmaceuticals", values, "pharmaceuticals_id" + " = ?",
                new String[]{String.valueOf(pharmaceuticalsId)});

        if (rowsUpdated > 0) {
            Log.d("DatabaseHelper", "Pharmaceuticals name updated successfully");
        } else {
            Log.d("DatabaseHelper", "Failed to update pharmaceuticals name");
        }

        db.close();
    }

    public void deletePharmaceuticals(int pharmaceuticalsId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete("Pharmaceuticals", "pharmaceuticals_id" + " = ?",
                new String[]{String.valueOf(pharmaceuticalsId)});

        if (rowsDeleted > 0) {
            Log.d("DatabaseHelper", "Pharmaceuticals deleted successfully");
        } else {
            Log.d("DatabaseHelper", "Failed to delete pharmaceuticals");
        }

        db.close();
    }
    public int getPharmaceuticalsId(String pharmaceuticalsName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("Pharmaceuticals", new String[]{"pharmaceuticals_id"},
                "pharmaceuticals_name" + " = ?", new String[]{pharmaceuticalsName},
                null, null, null);

        int pharmaceuticalsId = -1;
        if (cursor.moveToFirst()) {
            pharmaceuticalsId = cursor.getInt(cursor.getColumnIndexOrThrow("pharmaceuticals_id"));
        }
        cursor.close();
        db.close();
        return pharmaceuticalsId;
    }
}