package com.smritivas.todoapp.yesplus;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_NAME = "taskManager";
	private static final String TABLE = "task_list";
	private static final String KEY_ID = "id";
	private static final String KEY_PRIORITY = "priority";
	private static final String KEY_DATE = "date";
	private static final String KEY_TASK = "task";
	static String KEY_DONE = "DONE";

	// private static final String[] COLUMNS = { KEY_ID, KEY_PRIORITY, KEY_DATE,
	// KEY_TASK };

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String CREATE_TABLE = "CREATE TABLE " + TABLE + " (" + KEY_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_PRIORITY
				+ " INTEGER, " + KEY_DATE + " TEXT NOT NULL, " + KEY_TASK
				+ " TEXT NOT NULL);";
		db.execSQL(CREATE_TABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS" + TABLE);
		this.onCreate(db);
	}

	public void addTask(Task task) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_PRIORITY, task.getPriority());
		values.put(KEY_DATE, task.getDate());
		values.put(KEY_TASK, task.getTaskName());
		db.insert(TABLE, null, values);
		String name = task.getTaskName();
		Log.i("task", "task " + name);
		db.close();
		// code to sort the database in order of priority
		// db.query(TABLE,
		// new String[] { KEY_ID, KEY_PRIORITY, KEY_DATE, KEY_TASK },
		// null, null, null, null, KEY_PRIORITY + " DESC");

	}

	public ArrayList<Task> getAllTasks() {
		SQLiteDatabase db = this.getWritableDatabase();
		String[] columns = new String[] { KEY_PRIORITY, KEY_DATE, KEY_TASK };
		Cursor c = db.query(TABLE, columns, null, null, null, null, null);
		ArrayList<Task> tasks = new ArrayList<Task>();
		int iNum = c.getColumnIndex(KEY_PRIORITY);
		int iDate = c.getColumnIndex(KEY_DATE);
		int iTask = c.getColumnIndex(KEY_TASK);
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			tasks.add(new Task(iNum, c.getString(iDate), c.getString(iTask)));
			String name = c.getString(iTask);
			Log.i("task", "task name " + name);
		}
		db.close();
		return tasks;

	}

	public void deleteTask(Task task) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE, KEY_ID, null);
		db.close();
		Log.i("delete", task.getTaskName());

	}

	public void deleteTask(int row) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE, KEY_ID + "=" + row, null);
		db.close();
	}

	public int updateTask(Task task) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_PRIORITY, task.getPriority());
		values.put(KEY_DATE, task.getDate());
		values.put(KEY_TASK, task.getTaskName());
		int i = db.update(TABLE, values, KEY_ID,
				new String[] { String.valueOf(task.getId()) });

		Log.i("update", task.getTaskName());
		db.close();
		return i;

	}

	// is something like this helpful in setting that alert dialog to delete the
	// selected task
	public Task getTask(int id) {
		Task task = new Task();
		task.setID(id);
		return task;

	}

}
