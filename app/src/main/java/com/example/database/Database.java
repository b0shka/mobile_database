package com.example.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;


public class Database extends SQLiteOpenHelper {
	private static final int DB_VERSION = 1;
	private static String DB_PATH = "/data/data/com.example.database/databases/";
	private static final String TABLE_NAME = "users";
	private static final String COLUMN_ID = "_id";
	private static final String COLUMN_FIRST_NAME = "first_name";
	private static final String COLUMN_LAST_NAME = "last_name";
	private static final String COLUMN_PATRONYMIC = "patronymic";
	private static final String COLUMN_AGE = "age";
	private static final String COLUMN_BIRTH = "birth";

	private Context context;
	private SQLiteDatabase db;

	public Database(Context context) {
		super(context, Variable.g_db_name, null, DB_VERSION);
		this.context = context;

		Variable.g_status_db = 1;
	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
		sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " +
				TABLE_NAME + " (" +
				COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
				COLUMN_FIRST_NAME + " VARCHAR(255)," +
				COLUMN_LAST_NAME + " VARCHAR(255)," +
				COLUMN_PATRONYMIC + " VARCHAR(255)," +
				COLUMN_AGE + " VARCHAR(255)," +
				COLUMN_BIRTH + " TEXT)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
		sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(sqLiteDatabase);
	}

	public void open_db() {
		db = this.getWritableDatabase();
	}

	public void copy_db(String mode) throws IOException {
		File sourceFile = null;
		File destFile = null;

		if (mode == "to_data") {
			sourceFile = new File(Variable.g_db_path + Variable.g_db_path);
			destFile = new File(DB_PATH + Variable.g_db_name);
		} else if (mode == "from_data") {
			sourceFile = new File(DB_PATH + Variable.g_db_name);
			destFile = new File(Variable.g_db_path + Variable.g_db_name);
		}

		InputStream in = new FileInputStream(sourceFile);
		OutputStream out = new FileOutputStream(destFile);

		byte[] buffer = new byte[1024];
		int len;
		while ((len = in.read(buffer)) > 0){
			out.write(buffer, 0, len);
		}

		out.flush();
		out.close();
		in.close();
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
			Log.d(Variable.TAG, error_get_data_bd, e);
			list_data_user.add("ERROR");
		}

		return list_data_user;
	}

	public void close_db() {
		this.close();
	}
}
