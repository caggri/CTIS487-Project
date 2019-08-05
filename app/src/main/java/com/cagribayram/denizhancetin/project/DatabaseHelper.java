package com.cagribayram.denizhancetin.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.time.LocalDate;

import static javax.xml.datatype.DatatypeConstants.DATETIME;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "person_db";
    public static final int DATABASE_VERSION = 1;

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("Database Operation", "Database created");
    }

    // Create query
    public static final String CREATE_TABLE = "create table " + TemperatureArchive.Temperature.TABLE_NAME +
            "(" + TemperatureArchive.Temperature.ID + " integer primary key autoincrement," +
            TemperatureArchive.Temperature.TIMESTAMP + " DATETIME,"+
            TemperatureArchive.Temperature.TEMP + " numeric);";


    // Delete query
    public static final String DROP_TABLE = "drop table if exists " + TemperatureArchive.Temperature.TABLE_NAME;


    @Override
    public void onCreate(SQLiteDatabase db) {
        // onCreate() is only called by the framework, if the database does not exist
        Log.d("Database Operation", "Table created");

        try {
            db.execSQL(CREATE_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // onUpgrade() is only called by the framework, if one changes the database version number

        // Sends a Warn log message
        Log.w("Database Operation", "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");

        // Method to execute an SQL statement directly
        db.execSQL(DROP_TABLE);

        // Create the Database again
        onCreate(db);

    }

    // Insert a contact into the database
    public void addContact(String name, Integer temp, SQLiteDatabase db) {
        // The class ContentValues allows to define key/value pairs. The "key" represents the
        // table column identifier and the "value" represents the content for the table
        // record in this column. ContentValues can be used for insert and update of database entries.
        ContentValues mContentValues = new ContentValues();
        mContentValues.put(TemperatureArchive.Temperature.TIMESTAMP, "sad");
        mContentValues.put(TemperatureArchive.Temperature.TEMP, temp);
        db.insert(TemperatureArchive.Temperature.TABLE_NAME, null, mContentValues);

        Log.d("Database Operation", "Row inserted");
    }


}