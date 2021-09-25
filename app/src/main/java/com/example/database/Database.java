package com.example.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;


public class Database extends SQLiteOpenHelper {
	private static final int DB_VERSION = 1;
	private static String DB_PATH = "";
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

	private static final String TAG = "APP_LOGS";
	private Context context;
	private SQLiteDatabase db;

	public Database(Context context) {
		super(context, Variable.g_db_name, null, DB_VERSION);
		this.context = context;
		DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";

		Variable.g_status_db = 1;
	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
		sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
		sqLiteDatabase.execSQL(SQL_DELETE_TABLE);
		onCreate(sqLiteDatabase);
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

	public void update_user(ArrayList<String> new_data_user, String old_first_name, String old_last_name){
		ContentValues content = new ContentValues();
		content.put(COLUMN_FIRST_NAME, new_data_user.get(0));
		content.put(COLUMN_LAST_NAME, new_data_user.get(1));
		content.put(COLUMN_PATRONYMIC, new_data_user.get(2));
		content.put(COLUMN_AGE, new_data_user.get(3));
		content.put(COLUMN_BIRTH, new_data_user.get(4));

		db.update(TABLE_NAME,
				content,
				COLUMN_FIRST_NAME + "= ? AND " + COLUMN_LAST_NAME + "= ?",
				new String[] {old_first_name, old_last_name});
	}

	public void delete_user(ArrayList<String> delete_data_user) {
		db.delete(TABLE_NAME,
				COLUMN_FIRST_NAME + "= ? AND " + COLUMN_LAST_NAME + "= ? AND " + COLUMN_PATRONYMIC + "= ?",
				new String[] {delete_data_user.get(0), delete_data_user.get(1), delete_data_user.get(2)});
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

		try {
			String[] get_columns = {COLUMN_PATRONYMIC, COLUMN_AGE, COLUMN_BIRTH};
			Cursor cursor = db.query(TABLE_NAME,
					get_columns,
					COLUMN_FIRST_NAME + " = ? AND " + COLUMN_LAST_NAME + " = ?",
					new String[] {first_name, last_name},
					null, null, null);

			//Cursor cursor = db.rawQuery("SELECT patronymic, age, birth FROM users WHERE first_name = ? AND last_name = ?;", new String[] {first_name, last_name});

			while (cursor.moveToNext()) {
				@SuppressLint("Range") String patronymic = cursor.getString(cursor.getColumnIndex(COLUMN_PATRONYMIC));
				@SuppressLint("Range") String age = cursor.getString(cursor.getColumnIndex(COLUMN_AGE));
				@SuppressLint("Range") String birth = cursor.getString(cursor.getColumnIndex(COLUMN_BIRTH));

				list_data_user.add(patronymic);
				list_data_user.add(age);
				list_data_user.add(birth);
			}

			cursor.close();

		} catch (Exception e) {
			String error_get_data_bd = "Ошибка при получении данных из БД";
			Log.d(TAG, error_get_data_bd, e);
			list_data_user.add("ERROR");
		}

		return list_data_user;
	}

	public void close_db() {
		this.close();
	}
}
