package com.example.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class Database extends SQLiteOpenHelper {
	private static final int DB_VERSION = 1;
	private static String DB_PATH = "/data/data/com.example.database/databases/";
	private static final String TABLE_NAME = Variable.g_table_name;
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_FIRST_NAME = "first_name";
	private static final String COLUMN_LAST_NAME = "last_name";
	private static final String COLUMN_PATRONYMIC = "patronymic";
	private static final String COLUMN_AGE = "age";
	private static final String COLUMN_BIRTH = "birth";
	private static final String COLUMN_COUNTRY = "country_city";
	private static final String COLUMN_ADDRESS = "address";
	private static final String COLUMN_INDEX = "index_";
	private static final String COLUMN_NUMBER_PHONE = "number_phone";
	private static final String COLUMN_PHONE = "phone";
	private static final String COLUMN_PASSPORT = "passport";
	private static final String COLUMN_SNILS = "snils";
	private static final String COLUMN_CAR = "car";
	private static final String COLUMN_EDUCATION = "education";
	private static final String COLUMN_PLACE_WORK = "place_work";
	private static final String COLUMN_EMAIL = "email";
	private static final String COLUMN_VK = "vk";
	private static final String COLUMN_INSTAGRAM = "instagram";
	private static final String COLUMN_TELEGRAM = "telegram";
	private static final String COLUMN_OTHER_SOCIAL = "other_social";
	private static final String COLUMN_HOBBY = "hobby";
	private static final String COLUMN_RELATIVE = "relatives";
	private static final String COLUMN_OTHER = "other";

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
				COLUMN_ID + " INTEGER PRIMARY KEY," +
				COLUMN_FIRST_NAME + " VARCHAR(255)," +
				COLUMN_LAST_NAME + " VARCHAR(255)," +
				COLUMN_PATRONYMIC + " VARCHAR(255)," +
				COLUMN_AGE + " VARCHAR(255)," +
				COLUMN_BIRTH + " VARCHAR(255)," +
				COLUMN_COUNTRY + " VARCHAR(255)," +
				COLUMN_ADDRESS + " VARCHAR(255)," +
				COLUMN_INDEX + " VARCHAR(255)," +
				COLUMN_NUMBER_PHONE + " VARCHAR(255)," +
				COLUMN_PHONE + " VARCHAR(255)," +
				COLUMN_PASSPORT + " TEXT," +
				COLUMN_SNILS + " VARCHAR(255)," +
				COLUMN_CAR + " VARCHAR(255)," +
				COLUMN_EDUCATION + " VARCHAR(255)," +
				COLUMN_PLACE_WORK + " VARCHAR(255)," +
				COLUMN_EMAIL + " VARCHAR(255)," +
				COLUMN_VK + " VARCHAR(255)," +
				COLUMN_INSTAGRAM + " VARCHAR(255)," +
				COLUMN_TELEGRAM + " VARCHAR(255)," +
				COLUMN_OTHER_SOCIAL + " TEXT," +
				COLUMN_RELATIVE + " TEXT," +
				COLUMN_HOBBY + " TEXT," +
				COLUMN_OTHER + " TEXT);");
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
		File sourceFile_journal = null;

		if (mode == "to_data") {
			sourceFile = new File(Variable.g_db_path + Variable.g_db_name);
			destFile = new File(DB_PATH + Variable.g_db_name);
		} else if (mode == "from_data") {
			sourceFile = new File(DB_PATH + Variable.g_db_name);
			sourceFile_journal = new File(DB_PATH + Variable.g_db_name + "-journal");
			destFile = new File(Variable.g_db_path + Variable.g_db_name);
		}

		if (destFile.exists())
			destFile.delete();

		InputStream in = new FileInputStream(sourceFile);
		OutputStream out = new FileOutputStream(destFile);

		byte[] buffer = new byte[1024];
		int len;
		while ((len = in.read(buffer)) > 0){
			out.write(buffer, 0, len);
		}

		if (mode == "from_data") {
			sourceFile.delete();
			if (sourceFile_journal.exists())
				sourceFile_journal.delete();
		}

		out.flush();
		out.close();
		in.close();
	}

	public ContentValues completion_content(ArrayList<String> list_data) {
		ContentValues content = new ContentValues();
		content.put(COLUMN_ID, generate_id());
		content.put(COLUMN_FIRST_NAME, list_data.get(0));
		content.put(COLUMN_LAST_NAME, list_data.get(1));
		content.put(COLUMN_PATRONYMIC, list_data.get(2));
		content.put(COLUMN_AGE, list_data.get(3));
		content.put(COLUMN_BIRTH, list_data.get(4));
		content.put(COLUMN_COUNTRY, list_data.get(5));
		content.put(COLUMN_ADDRESS, list_data.get(6));
		content.put(COLUMN_INDEX, list_data.get(7));
		content.put(COLUMN_NUMBER_PHONE, list_data.get(8));
		content.put(COLUMN_PHONE, list_data.get(9));
		content.put(COLUMN_PASSPORT, list_data.get(10));
		content.put(COLUMN_SNILS, list_data.get(11));
		content.put(COLUMN_CAR, list_data.get(12));
		content.put(COLUMN_EDUCATION, list_data.get(13));
		content.put(COLUMN_PLACE_WORK, list_data.get(14));
		content.put(COLUMN_EMAIL, list_data.get(15));
		content.put(COLUMN_VK, list_data.get(16));
		content.put(COLUMN_INSTAGRAM, list_data.get(17));
		content.put(COLUMN_TELEGRAM, list_data.get(18));
		content.put(COLUMN_OTHER_SOCIAL, list_data.get(19));
		content.put(COLUMN_RELATIVE, list_data.get(20));
		content.put(COLUMN_HOBBY, list_data.get(21));
		content.put(COLUMN_OTHER, list_data.get(22));

		return content;
	}

	public void add_user(ArrayList<String> data_user) {
		ContentValues content = completion_content(data_user);
		db.insert(TABLE_NAME, null, content);
	}

	public void update_user(ArrayList<String> new_data_user, String user_id){
		ContentValues content = completion_content(new_data_user);
		db.update(TABLE_NAME,
				content,
				COLUMN_ID + "= ?",
				new String[] {user_id});
	}

	public void delete_user(String user_id) {
		db.delete(TABLE_NAME,
				COLUMN_ID + "= ?",
				new String[] {user_id});
	}

	public ArrayList<String> get_users() {
		ArrayList<String> list_data_user = new ArrayList();
		String[] get_columns = {COLUMN_ID, COLUMN_FIRST_NAME, COLUMN_LAST_NAME, COLUMN_PATRONYMIC, COLUMN_AGE, COLUMN_COUNTRY};
		Cursor cursor = db.query(TABLE_NAME, get_columns, null, null, null, null, null);

		while (cursor.moveToNext()) {
			@SuppressLint("Range") String user_id = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
			@SuppressLint("Range") String first_name = cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME));
			@SuppressLint("Range") String last_name = cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME));
			@SuppressLint("Range") String patronymic = cursor.getString(cursor.getColumnIndex(COLUMN_PATRONYMIC));
			@SuppressLint("Range") String age = cursor.getString(cursor.getColumnIndex(COLUMN_AGE));
			@SuppressLint("Range") String country_city = cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY));

			String result_data = first_name;

			if (!last_name.equals(""))
				result_data += ";" + last_name;

			if (!patronymic.equals(""))
				result_data += ";" + patronymic + "//";
			else
				result_data += "//";

			result_data += "id: " + user_id;

			if (!age.equals(""))
				result_data += ", " + age + " лет";

			if (!country_city.equals(""))
				result_data += ", город: " + country_city;

			list_data_user.add(result_data);
		}

		cursor.close();
		return list_data_user;
	}

	public int generate_id() {
		int new_id = 1;

		Cursor cursor = db.query(Variable.g_table_name, new String[]{COLUMN_ID}, null, null, null, null, null);

		while (cursor.moveToNext()) {
			@SuppressLint("Range") int check_id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
			if (new_id == check_id)
				new_id++;
			else
				break;
		}

		cursor.close();
		return new_id;
	}

	public ArrayList<String> get_data_user(String user_id) {
		ArrayList<String> list_data_user = new ArrayList<>();

		try {
			String[] get_columns = {COLUMN_FIRST_NAME, COLUMN_LAST_NAME, COLUMN_PATRONYMIC, COLUMN_AGE, COLUMN_BIRTH, COLUMN_COUNTRY, COLUMN_ADDRESS, COLUMN_INDEX, COLUMN_NUMBER_PHONE, COLUMN_PHONE, COLUMN_PASSPORT, COLUMN_SNILS, COLUMN_CAR, COLUMN_EDUCATION, COLUMN_PLACE_WORK, COLUMN_EMAIL, COLUMN_VK, COLUMN_INSTAGRAM, COLUMN_TELEGRAM, COLUMN_OTHER_SOCIAL, COLUMN_RELATIVE, COLUMN_HOBBY, COLUMN_OTHER};
			Cursor cursor = db.query(TABLE_NAME,
					get_columns,
					COLUMN_ID + " = ?",
					new String[] {user_id},
					null, null, null);

			//Cursor cursor = db.rawQuery("SELECT patronymic, age, birth FROM users WHERE first_name = ? AND last_name = ?;", new String[] {first_name, last_name});

			while (cursor.moveToNext()) {
				@SuppressLint("Range") String first_name = cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME));
				@SuppressLint("Range") String last_name = cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME));
				@SuppressLint("Range") String patronymic = cursor.getString(cursor.getColumnIndex(COLUMN_PATRONYMIC));
				@SuppressLint("Range") String age = cursor.getString(cursor.getColumnIndex(COLUMN_AGE));
				@SuppressLint("Range") String birth = cursor.getString(cursor.getColumnIndex(COLUMN_BIRTH));
				@SuppressLint("Range") String country = cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY));
				@SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS));
				@SuppressLint("Range") String index = cursor.getString(cursor.getColumnIndex(COLUMN_INDEX));
				@SuppressLint("Range") String number_phone = cursor.getString(cursor.getColumnIndex(COLUMN_NUMBER_PHONE));
				@SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE));
				@SuppressLint("Range") String passport = cursor.getString(cursor.getColumnIndex(COLUMN_PASSPORT));
				@SuppressLint("Range") String snils = cursor.getString(cursor.getColumnIndex(COLUMN_SNILS));
				@SuppressLint("Range") String car = cursor.getString(cursor.getColumnIndex(COLUMN_CAR));
				@SuppressLint("Range") String education = cursor.getString(cursor.getColumnIndex(COLUMN_EDUCATION));
				@SuppressLint("Range") String place_work = cursor.getString(cursor.getColumnIndex(COLUMN_PLACE_WORK));
				@SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
				@SuppressLint("Range") String vk = cursor.getString(cursor.getColumnIndex(COLUMN_VK));
				@SuppressLint("Range") String instagram = cursor.getString(cursor.getColumnIndex(COLUMN_INSTAGRAM));
				@SuppressLint("Range") String telegram = cursor.getString(cursor.getColumnIndex(COLUMN_TELEGRAM));
				@SuppressLint("Range") String other_social = cursor.getString(cursor.getColumnIndex(COLUMN_OTHER_SOCIAL));
				@SuppressLint("Range") String relative = cursor.getString(cursor.getColumnIndex(COLUMN_RELATIVE));
				@SuppressLint("Range") String hobby = cursor.getString(cursor.getColumnIndex(COLUMN_HOBBY));
				@SuppressLint("Range") String other = cursor.getString(cursor.getColumnIndex(COLUMN_OTHER));

				list_data_user.add(first_name);
				list_data_user.add(last_name);
				list_data_user.add(patronymic);
				list_data_user.add(age);
				list_data_user.add(birth);
				list_data_user.add(country);
				list_data_user.add(address);
				list_data_user.add(index);
				list_data_user.add(number_phone);
				list_data_user.add(phone);
				list_data_user.add(passport);
				list_data_user.add(snils);
				list_data_user.add(car);
				list_data_user.add(education);
				list_data_user.add(place_work);
				list_data_user.add(email);
				list_data_user.add(vk);
				list_data_user.add(instagram);
				list_data_user.add(telegram);
				list_data_user.add(other_social);
				list_data_user.add(relative);
				list_data_user.add(hobby);
				list_data_user.add(other);
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
