package com.example.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;


class DatabaseConst extends SQLiteOpenHelper {
	private Context context;
	public static final String DB_NAME = "server.db";
	public static final int DB_VERSION = 1;
	public static final String TABLE_NAME = "users";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_FIRST_NAME = "first_name";
	public static final String COLUMN_LAST_NAME = "last_name";
	public static final String COLUMN_PATRONYMIC = "patronymic";
	public static final String COLUMN_AGE = "age";
	public static final String COLUMN_BIRTH = "birth";

	private static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
			TABLE_NAME + " (" +
			COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
			COLUMN_FIRST_NAME + " VARCHAR(255)," +
			COLUMN_LAST_NAME + " VARCHAR(255)," +
			COLUMN_PATRONYMIC + " VARCHAR(255)," +
			COLUMN_AGE + "INTEGER," +
			COLUMN_BIRTH + "TEXT)";
	private static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

	public DatabaseConst(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int i, int i1) {
		db.execSQL(SQL_DELETE_TABLE);
		onCreate(db);
	}
}

public class Database {
	private Context context;
	private DatabaseConst database;
	private SQLiteDatabase db;

	public Database(Context context) {
		this.context = context;
		database = new DatabaseConst(context);
	}

	public void open_db() {
		db = database.getWritableDatabase();
	}

	public void insert_db(String first_name, String last_name, String patronymic, String age, String birth) {
		ContentValues content = new ContentValues();
		content.put(database.COLUMN_FIRST_NAME, first_name);
		content.put(database.COLUMN_LAST_NAME, last_name);
		content.put(database.COLUMN_PATRONYMIC, patronymic);
		content.put(database.COLUMN_AGE, age);
		content.put(database.COLUMN_BIRTH, birth);

		db.insert(database.TABLE_NAME, null, content);
	}

	public List<String> get_db() {
		List<String> data_user = new ArrayList<>();
		Cursor cursor = db.query(database.TABLE_NAME, null, null, null, null, null, null);

		while (cursor.moveToNext()) {
			@SuppressLint("Range") String first_name = cursor.getString(cursor.getColumnIndex(database.COLUMN_FIRST_NAME));
			@SuppressLint("Range") String last_name = cursor.getString(cursor.getColumnIndex(database.COLUMN_LAST_NAME));

			data_user.add(first_name + " " + last_name);
		}

		cursor.close();
		return data_user;
	}

	public void close_db() {
		database.close();
	}
}
