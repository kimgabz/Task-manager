package com.intellij.assessment.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.intellij.assessment.model.Task;

import java.util.ArrayList;
import java.util.List;


public class DataSource {

    private static final String TAG = DataSource.class.getSimpleName();

    private DBHelper dBHelper;
    private SQLiteDatabase database;

    public DataSource(Context context) {
        dBHelper = new DBHelper(context);
    }

    public void open() {
        Log.i(TAG, "Database opened");
        database = dBHelper.getWritableDatabase();
    }

    public void close() {
        Log.i(TAG, "Database closed");
        if (database.isOpen()) {
            database.close();
        }
    }

    public boolean isOpen() {
        return database.isOpen();
    }

    public void insertTasks(List<Task> dataList) {
        if (!database.isOpen()) {
            return;
        }
        for (int x = 0; x < dataList.size(); x++) {
            createTaskData(dataList.get(x));
        }
    }

    public Task createTaskData(Task task) {
        ContentValues values = new ContentValues();

        values.put(DBConstants.COLUMN_ID,           task.getId());
        values.put(DBConstants.COLUMN_TITLE,        task.getTitle());
        values.put(DBConstants.COLUMN_DETAILS,      task.getDetails());

        long insertId = database.insert(DBConstants.TABLE_TASKS, null, values);
        task.setDbid(insertId);
        return task;

    }

    public void editItem(Task task) {
        ContentValues values = new ContentValues();

        values.put(DBConstants.COLUMN_ID,           task.getId());
        values.put(DBConstants.COLUMN_TITLE,        task.getTitle());
        values.put(DBConstants.COLUMN_DETAILS,      task.getDetails());

        database.update(DBConstants.TABLE_TASKS, values, "columnId = ?", new String[]{task.getId()+""});
    }

    public List<Task> findAllTasks() {
        Cursor cursor = database.query(DBConstants.TABLE_TASKS, DBConstants.allTaskColumns,
                null, null, null, null, null);

        Log.i(TAG, "Returned " + cursor.getCount() + " rows");
        return cursorToTaskList(cursor);
    }

    private List<Task> cursorToTaskList(Cursor cursor) {
        List<Task> tasks = new ArrayList<>();
        if (cursor.getCount() > 0) while (cursor.moveToNext()) {
            Task task = new Task();

            task.setId(cursor.getInt(cursor.getColumnIndex(DBConstants.COLUMN_TABLE_ID)));
            task.setTitle(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_TITLE)));
            task.setDetails(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_DETAILS)));

            tasks.add(task);
        }
        return tasks;
    }

    public boolean deleteAllTable(String TABLE_NAME) {
        int doneDelete = 0;
        doneDelete = database.delete(TABLE_NAME, null, null);
        Log.w(TAG, Integer.toString(doneDelete));
        return doneDelete > 0;
    }

    public void removeSingleContact(String title) {
        database.execSQL("DELETE FROM " + DBConstants.TABLE_TASKS + " WHERE " + DBConstants.COLUMN_TITLE + "= '" + title + "'");

    }

    public void removeSingleContact(int id) {
        database.execSQL("DELETE FROM " + DBConstants.TABLE_TASKS + " WHERE " + DBConstants.COLUMN_TABLE_ID + "= '" + id + "'");
    }
}
