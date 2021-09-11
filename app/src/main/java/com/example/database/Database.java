package com.example.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Database extends SQLiteOpenHelper {
	private Context context;
	public static final String DB_NAME = "server.db";
	public static final String TABLE_NAME = "users";
	public static final int DB_VERSION = 1;
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_FIRST_NAME = "first_name";
	public static final String COLUMN_LAST_NAME = "last_name";

	private static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
													TABLE_NAME + " (" +
													COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
													COLUMN_FIRST_NAME + " VARCHAR(255)," +
													COLUMN_LAST_NAME + " VARCHAR(255))";
	private static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

	public Database(Context context) {
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
