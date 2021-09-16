package com.example.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;


public class Database extends SQLiteOpenHelper {
	private static final String DB_NAME = "server.db";
	private static final int DB_VERSION = 1;
	private static final String TABLE_NAME = "users";
	private static final String COLUMN_ID = "_id";
	private static final String COLUMN_FIRST_NAME = "first_name";
	private static final String COLUMN_LAST_NAME = "last_name";
	private static final String COLUMN_PATRONYMIC = "patronymic";
	private static final String COLUMN_AGE = "age";
	private static final String COLUMN_BIRTH = "birth";

	private static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
			TABLE_NAME + " (" +
			COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
			COLUMN_FIRST_NAME + " VARCHAR(255)," +
			COLUMN_LAST_NAME + " VARCHAR(255)," +
			COLUMN_PATRONYMIC + " VARCHAR(255)," +
			COLUMN_AGE + " VARCHAR(255)," +
			COLUMN_BIRTH + " TEXT)";
	private static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

	private final String TAG = this.getClass().getSimpleName();
	private Context context;
	private SQLiteDatabase db;

	public Database(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
		db.execSQL(SQL_CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
		db.execSQL(SQL_DELETE_TABLE);
		onCreate(db);
	}

	public void open_db() {
		db = this.getWritableDatabase();
	}

	public void add_user(String first_name, String last_name, String patronymic, String age, String birth) {
		ContentValues content = new ContentValues();
		content.put(COLUMN_FIRST_NAME, first_name);
		content.put(COLUMN_LAST_NAME, last_name);
		content.put(COLUMN_PATRONYMIC, patronymic);
		content.put(COLUMN_AGE, age);
		content.put(COLUMN_BIRTH, birth);

		db.insert(TABLE_NAME, null, content);
	}

	public ArrayList<String> get_users() {
		ArrayList<String> data_user = new ArrayList<>();
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

		while (cursor.moveToNext()) {
			@SuppressLint("Range") String first_name = cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME));
			@SuppressLint("Range") String last_name = cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME));

			data_user.add(first_name + " " + last_name);
		}

		cursor.close();
		return data_user;
	}

	public ArrayList<String> get_data_user(String first_name, String last_name) {
		ArrayList<String> list_data_user = new ArrayList<>();
		/*Cursor cursor = db.query(TABLE_NAME,
				new String[] {COLUMN_PATRONYMIC, COLUMN_AGE, COLUMN_BIRTH},
				"COLUMN_FIRST_NAME = ? AND COLUMN_LAST_NAME = ?",
				new String[] {first_name, last_name},
				null,null,null);*/

		Cursor cursor = db.rawQuery("SELECT patronymic, age, birth FROM users WHERE first_name = ? AND last_name = ?;", new String[] {first_name, last_name});

		/*while (cursor.moveToNext()) {
			String patronymic = cursor.getString(0);
			String age = cursor.getString(1);
			String birth = cursor.getString(2);
			//@SuppressLint("Range") String patronymic = cursor.getString(cursor.getColumnIndex(COLUMN_PATRONYMIC));
			//@SuppressLint("Range") String age = cursor.getString(cursor.getColumnIndex(COLUMN_AGE));
			//@SuppressLint("Range") String birth = cursor.getString(cursor.getColumnIndex(COLUMN_BIRTH));

			list_data_user.add(patronymic);
			list_data_user.add(age);
			list_data_user.add(birth);
		}*/
		list_data_user.add("patronymic");
		cursor.close();
		return list_data_user;
	}

	public void close_db() {
		this.close();
	}
}
