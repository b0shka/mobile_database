package com.example.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

import java.sql.Array;
import java.sql.Blob;
import java.util.ArrayList;


public class Database extends SQLiteOpenHelper {
	private static final int DB_VERSION = 1;
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
	private static final String COLUMN_PHOTO_1 = "photo_1";
	private static final String COLUMN_PHOTO_2 = "photo_2";
	private static final String COLUMN_PHOTO_3 = "photo_3";
	private static final String COLUMN_PHOTO_4 = "photo_4";
	private static final String COLUMN_NAME_DOC_1 = "name_doc_1";
	private static final String COLUMN_DOC_1 = "doc_1";
	private static final String COLUMN_NAME_DOC_2 = "name_doc_2";
	private static final String COLUMN_DOC_2 = "doc_2";
	private static final String COLUMN_NAME_DOC_3 = "name_doc_3";
	private static final String COLUMN_DOC_3 = "doc_3";

	private Context context;
	private SQLiteDatabase db;

	public Database(Context context) {
		super(context, Variable.g_db_name, null, DB_VERSION);
		this.context = context;

		Variable.g_db_path_app = context.getApplicationInfo().dataDir + "/databases/";
		Variable.g_status_db = 1;
	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
		sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " +
				Variable.g_table_name + " (" +
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
				COLUMN_OTHER + " TEXT," +
				COLUMN_PHOTO_1 + " BLOB," +
				COLUMN_PHOTO_2 + " BLOB," +
				COLUMN_PHOTO_3 + " BLOB," +
				COLUMN_PHOTO_4 + " BLOB," +
				COLUMN_NAME_DOC_1 + " TEXT," +
				COLUMN_DOC_1 + " BLOB," +
				COLUMN_NAME_DOC_2 + " TEXT," +
				COLUMN_DOC_2 + " BLOB," +
				COLUMN_NAME_DOC_3 + " TEXT," +
				COLUMN_DOC_3 + " BLOB);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
		sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Variable.g_table_name + ";");
		onCreate(sqLiteDatabase);
	}

	public void open_db() {
		db = this.getWritableDatabase();
		if (Variable.g_status_first_start == 0) {
			get_global_table();
			Variable.g_status_first_start = 1;
		}
	}

	public ContentValues completion_content(ArrayList<String> list_data, String user_id, byte[] byte_photo_1, byte[] byte_photo_2, byte[] byte_photo_3, byte[] byte_photo_4) {
		ContentValues content = new ContentValues();
		content.put(COLUMN_ID, user_id);
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
		content.put(COLUMN_PHOTO_1, byte_photo_1);
		content.put(COLUMN_PHOTO_2, byte_photo_2);
		content.put(COLUMN_PHOTO_3, byte_photo_3);
		content.put(COLUMN_PHOTO_4, byte_photo_4);

		return content;
	}

	public void add_user(ArrayList<String> data_user, String user_id, byte[] byte_photo_1, byte[] byte_photo_2, byte[] byte_photo_3, byte[] byte_photo_4) {
		ContentValues content = completion_content(data_user, user_id, byte_photo_1, byte_photo_2, byte_photo_3, byte_photo_4);
		db.insert(Variable.g_table_name, null, content);
	}

	public void update_user(ArrayList<String> new_data_user, String user_id, byte[] byte_photo_1, byte[] byte_photo_2, byte[] byte_photo_3, byte[] byte_photo_4){
		ContentValues content = completion_content(new_data_user, user_id, byte_photo_1, byte_photo_2, byte_photo_3, byte_photo_4);
		db.update(Variable.g_table_name,
				content,
				COLUMN_ID + "= ?",
				new String[] {user_id});
	}

	public void delete_user(String user_id) {
		db.delete(Variable.g_table_name,
				COLUMN_ID + "= ?",
				new String[] {user_id});
	}

	public ArrayList<String> get_users() {
		ArrayList<String> list_data_user = new ArrayList();
		String[] get_columns = {COLUMN_ID, COLUMN_FIRST_NAME, COLUMN_LAST_NAME, COLUMN_PATRONYMIC, COLUMN_AGE, COLUMN_COUNTRY};
		Cursor cursor = db.query(Variable.g_table_name, get_columns, null, null, null, null, null);

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
			else
				result_data += "; ";

			if (!patronymic.equals(""))
				result_data += ";" + patronymic + "//";
			else
				result_data += "; //";

			result_data += "id: " + user_id;

			if (!age.equals(""))
				result_data += " age: " + age;

			if (!country_city.equals(""))
				result_data += " город: " + country_city;

			list_data_user.add(result_data);
		}

		cursor.close();
		return list_data_user;
	}

	public int generate_id() {
		int new_id = 1;

		Cursor cursor = db.query(Variable.g_table_name, new String[] {COLUMN_ID}, null, null, null, null, null);

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
			Cursor cursor = db.query(Variable.g_table_name,
					get_columns,
					COLUMN_ID + " = ?",
					new String[] {user_id},
					null, null, null);

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

	public ArrayList<byte[]> get_bytes_photo(String user_id) {
		ArrayList<byte[]> list_bytes_photo = new ArrayList<>();
		Blob blob;

		try {
			String[] get_columns = {COLUMN_PHOTO_1, COLUMN_PHOTO_2, COLUMN_PHOTO_3, COLUMN_PHOTO_4};
			Cursor cursor = db.query(Variable.g_table_name,
					get_columns,
					COLUMN_ID + " = ?",
					new String[] {user_id},
					null, null, null);

			while (cursor.moveToNext()) {
				byte[] bytes_photo_1 = cursor.getBlob(0);
				byte[] bytes_photo_2 = cursor.getBlob(1);
				byte[] bytes_photo_3 = cursor.getBlob(2);
				byte[] bytes_photo_4 = cursor.getBlob(3);

				list_bytes_photo.add(bytes_photo_1);
				list_bytes_photo.add(bytes_photo_2);
				list_bytes_photo.add(bytes_photo_3);
				list_bytes_photo.add(bytes_photo_4);
			}

			cursor.close();

		} catch (Exception e) {
			list_bytes_photo.add(null);
		}

		return list_bytes_photo;
	}

	@SuppressLint("Range")
	public ArrayList<String> get_tables() {
		ArrayList<String> list_tables = new ArrayList<>();
		Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type = 'table';", null);

		while (cursor.moveToNext()) {
			@SuppressLint("Range") String table = cursor.getString(cursor.getColumnIndex("name"));
			if (!table.equals("android_metadata"))
				list_tables.add(table);
		}

		cursor.close();
		return list_tables;
	}

	public void create_new_table(String name_table) {
		db.execSQL("CREATE TABLE IF NOT EXISTS " +
				name_table + " (" +
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
				COLUMN_OTHER + " TEXT," +
				COLUMN_PHOTO_1 + " BLOB," +
				COLUMN_PHOTO_2 + " BLOB," +
				COLUMN_PHOTO_3 + " BLOB," +
				COLUMN_PHOTO_4 + " BLOB," +
				COLUMN_NAME_DOC_1 + " TEXT," +
				COLUMN_DOC_1 + " BLOB," +
				COLUMN_NAME_DOC_2 + " TEXT," +
				COLUMN_DOC_2 + " BLOB," +
				COLUMN_NAME_DOC_3 + " TEXT," +
				COLUMN_DOC_3 + " BLOB);");
	}

	public void delete_table(String name_table) {
		db.execSQL("DROP TABLE IF EXISTS " + name_table + ";");
	}

	public void change_name_table(String new_name) {
		db.execSQL("ALTER TABLE " + Variable.g_table_name + " RENAME TO " + new_name + ";");
	}

	public ArrayList<String> get_city() {
		ArrayList<String> list_city = new ArrayList();
		Cursor cursor = db.query(Variable.g_table_name, new String[] {COLUMN_COUNTRY}, null, null, null, null, null);

		while (cursor.moveToNext()) {
			@SuppressLint("Range") String city = cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY));
			list_city.add(city);
		}

		cursor.close();
		return list_city;
	}

	public void get_global_table() {
		ArrayList<String> list_table = get_tables();
		if (list_table.size() != 0)
			Variable.g_table_name = list_table.get(0);
		else
			Variable.g_table_name = "users";
	}

	public void get_data_filter() {
		get_global_table();

		ArrayList<String> list_age = new ArrayList<>();
		Cursor cursor = db.query(Variable.g_table_name, new String[] {COLUMN_AGE}, null, null, null, null, null);

		while (cursor.moveToNext()) {
			@SuppressLint("Range") String age = cursor.getString(cursor.getColumnIndex(COLUMN_AGE));
			list_age.add(age);
		}

		if (list_age.size() > 0) {
			Variable.g_age_min = 0;
			for (int i = 0; i < list_age.size(); i++) {
				if (!list_age.get(i).equals("")) {
					Variable.g_age_min = Integer.parseInt(list_age.get(i));
					break;
				}
			}

			Variable.g_age_max = 0;

			for (int i = 0; i < list_age.size(); i++) {
				if (!list_age.get(i).equals("")) {
					if (Integer.parseInt(list_age.get(i)) < Variable.g_age_min)
						Variable.g_age_min = Integer.parseInt(list_age.get(i));

					if (Integer.parseInt(list_age.get(i)) > Variable.g_age_max)
						Variable.g_age_max = Integer.parseInt(list_age.get(i));
				}
			}
		}
		else {
			Variable.g_age_min = 0;
			Variable.g_age_max = 0;
		}

		cursor.close();
	}

	public void close_db() {
		this.close();
		Variable.g_db_name = "";
		Variable.g_status_db = 0;
	}
}
