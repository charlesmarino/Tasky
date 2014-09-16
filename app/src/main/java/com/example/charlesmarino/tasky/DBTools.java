package com.example.charlesmarino.tasky;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by charlesmarino on 9/15/14.
 */
public class DBTools extends SQLiteOpenHelper{

    public DBTools(Context applicationContext) {
        super(applicationContext, "tasksDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE tasks (TaskID INTEGER PRIMARY KEY, taskName TEXT, taskDescription TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS tasks";
        db.execSQL(query);
        onCreate(db);
    }

    public void insertTask(Task task)  {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("taskName", task.name);
        values.put("taskDescription", task.description);
        db.insert("tasks", null, values);
        db.close();
    }

    public void deleteTask(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM tasks where taskID='" + id + "'";
        db.execSQL(deleteQuery);
    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> taskList = new ArrayList<Task>();
        String selectQuery = "SELECT * FROM tasks";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor.moveToFirst() ) {
            do {
                Task task = new Task(cursor.getString(0), cursor.getString(1), cursor.getString(2));
                taskList.add(task);
            } while (cursor.moveToNext());
        }
        return taskList;
    }

    public Task getTaskInfo(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM tasks where TASKID='"+id+"'";

        Cursor cursor = db.rawQuery(selectQuery, null);
        Task task = null;
        if( cursor.moveToFirst() ) {
            do {
                task = new Task(cursor.getString(0), cursor.getString(1), cursor.getString(2));
            } while (cursor.moveToNext());
        }
        return task;
    }


}
