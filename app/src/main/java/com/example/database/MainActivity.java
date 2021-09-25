package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends AppCompatActivity {

	private ListView list_users;
	private EditText line_search;
	private Button search, filter, open_db, create_db, settings, add_user;
	//private Database db;
	private String name_new_db;

	private static final int ACTIVITY_CHOOSE_FILE = 123;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		line_search = findViewById(R.id.line_search);
		search = findViewById(R.id.search);
		list_users = findViewById(R.id.list_users);
		filter = findViewById(R.id.filter);
		open_db = findViewById(R.id.open_db);
		create_db = findViewById(R.id.create_db);
		settings = findViewById(R.id.settings);
		add_user = findViewById(R.id.add_user);

		int g_status_db = 0;

		//db = new Database(MainActivity.this, "server.db");

		// Действие при нажатии на item
		list_users.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String name_item = list_users.getItemAtPosition(position).toString();
				Intent intent = new Intent(MainActivity.this, DataUser.class);
				intent.putExtra("id", id);
				intent.putExtra("name", name_item);
				startActivity(intent);
			}
		});

		filter.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if (g_status_db == 0)
							Toast.makeText(MainActivity.this, "Для начала откройте или создайте БД", Toast.LENGTH_SHORT).show();
						else {
							Intent intent = new Intent(MainActivity.this, Filter.class);
							startActivity(intent);
						}
					}
				}
		);

		open_db.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Toast.makeText(MainActivity.this, "Выберите файл БД", Toast.LENGTH_SHORT).show();
						onBrowse();
					}
				}
		);

		settings.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if (g_status_db == 0)
							Toast.makeText(MainActivity.this, "Для начала откройте или создайте БД", Toast.LENGTH_SHORT).show();
						else {
							Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
						}
					}
				}
		);

		add_user.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if (g_status_db == 0)
							Toast.makeText(MainActivity.this, "Для начала откройте или создайте БД", Toast.LENGTH_SHORT).show();
						else {
							Intent intent = new Intent(MainActivity.this, CreateUser.class);
							startActivity(intent);
						}
					}
				}
		);
	}

/*
	@Override
	protected void onResume() {
		super.onResume();
		//db.open_db();

		// Довавление в список элементов
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//				android.R.layout.simple_list_item_1, db.get_users());
//		list_users.setAdapter(adapter);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//db.close_db();
	}
*/

	public void onBrowse() {
		Intent chooseFile;
		Intent intent;
		chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
		chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
		chooseFile.setType("*/*");
		intent = Intent.createChooser(chooseFile, "Choose a file DB");
		startActivityForResult(intent, ACTIVITY_CHOOSE_FILE);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ACTIVITY_CHOOSE_FILE) {
			if (resultCode != RESULT_OK || data == null) {
				super.onActivityResult(requestCode, resultCode, data);
				return;
			}
			Uri uri = data.getData();
			if (uri == null) {
				return;
			}
			String fileName = getFileName(uri);
			String fileContent = getFileContent(uri);
			Log.e("File content: ", fileContent); // Содержимое файла
			Log.e("File name: ", fileName); // Имя файла
		} else {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	public String getFileContent(Uri contentUri) {
		try {
			InputStream in = getContentResolver().openInputStream(contentUri);
			if (in != null) {
				BufferedReader r = new BufferedReader(new InputStreamReader(in));
				StringBuilder total = new StringBuilder();
				for (String line; (line = r.readLine()) != null; ) {
					total.append(line).append('\n');
				}
				return total.toString();
			} else {
				Log.e("TAG", "Input stream is null");
			}
		} catch (Exception e) {
			Log.e("TAG", "Error while reading file by uri", e);
		}
		return "Could not read content!";
	}

	@SuppressLint("Range")
	public String getFileName(Uri contentUri) {
		String result = null;
		if (contentUri.getScheme() != null && contentUri.getScheme().equals("content")) {
			try (Cursor cursor = getContentResolver().query(contentUri, null, null, null, null)) {
				if (cursor != null && cursor.moveToFirst()) {
					result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
				}
			}
		}
		if (result == null) {
			result = contentUri.getPath();
			if (result == null) {
				return null;
			}
			int cut = result.lastIndexOf('/');
			if (cut != -1) {
				result = result.substring(cut + 1);
			}
		}
		return result;
	}

	// Действие при нажатии на кнопку поиска
	public void on_search(View view) {
		if (line_search.getText().toString().trim().equals(""))
			Toast.makeText(MainActivity.this, R.string.empty_line, Toast.LENGTH_LONG).show();
		else {
			String text_search = line_search.getText().toString();
			Toast.makeText(MainActivity.this, text_search, Toast.LENGTH_SHORT).show();
		}

		line_search.setText("");
	}

	public void on_create_db(View view) {
		Dialog dialog = new Dialog(MainActivity.this);
		dialog.setCancelable(false);
		dialog.setTitle("Создание БД");
		dialog.setContentView(R.layout.activity_create_db);
		Button create_new_db = (Button) dialog.findViewById(R.id.create_new_db);
		Button cancel_create_db = (Button) dialog.findViewById(R.id.cancel_create);
		EditText name_db = (EditText) dialog.findViewById(R.id.name_db);

		create_new_db.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				name_new_db = name_db.getText().toString();
				FileOutputStream file_db = null;

				try {
					file_db = openFileOutput(name_new_db + ".db", MODE_PRIVATE);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

				//db = new Database(MainActivity.this, name_new_db + ".db");
				//db.open_db();
				//db.create_db_file();

				Toast.makeText(MainActivity.this, name_new_db, Toast.LENGTH_SHORT).show();
				dialog.dismiss();
			}
		});

		cancel_create_db.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dialog.dismiss();
			}
		});

		dialog.show();
	}
}