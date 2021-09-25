package com.example.database;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {

	private ListView list_users;
	private EditText line_search;
	private Button search, filter, open_db, create_db, settings;
	private FloatingActionButton add_user;
	private Database db;

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

		Variable.g_db_name = "server.db";
		db = new Database(MainActivity.this);

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
					if (Variable.g_status_db == 0)
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
					if (Variable.g_status_db == 0)
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
					if (Variable.g_status_db == 0)
						Toast.makeText(MainActivity.this, "Для начала откройте или создайте БД", Toast.LENGTH_SHORT).show();
					else {
						Intent intent = new Intent(MainActivity.this, CreateUser.class);
						startActivity(intent);
						add_users_to_list();
					}
				}
			}
		);
	}

	@Override
	protected void onResume() {
		super.onResume();
		db.open_db();
		add_users_to_list();
	}

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

			String file_path = uri.getPath();
			String[] list_file_path = file_path.split("/");
			String file_name = list_file_path[list_file_path.length - 1];
			Variable.g_db_name = file_name;
			Variable.g_db_path = file_path;
			Toast.makeText(MainActivity.this, file_name, Toast.LENGTH_SHORT).show();

		} else {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	// Действие при нажатии на кнопку поиска
	public void on_search(View view) {
		if (Variable.g_status_db == 0)
			Toast.makeText(MainActivity.this, "Для начала откройте или создайте БД", Toast.LENGTH_SHORT).show();
		else {
			if (line_search.getText().toString().trim().equals(""))
				Toast.makeText(MainActivity.this, R.string.empty_line, Toast.LENGTH_LONG).show();
			else {
				String text_search = line_search.getText().toString();
				Toast.makeText(MainActivity.this, text_search, Toast.LENGTH_SHORT).show();
			}

			line_search.setText("");
		}
	}

	public void add_users_to_list() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, db.get_users());
		list_users.setAdapter(adapter);
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
				String name_new_db = name_db.getText().toString() + ".db";
				dialog.dismiss();

				Variable.g_db_name = name_new_db;
				//db = new Database(MainActivity.this);
				//db.open_db();

				Toast.makeText(MainActivity.this, "База данных успешно создана", Toast.LENGTH_SHORT).show();
				add_users_to_list();
				create_file();
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

	public void create_file() {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(getExternalFilesDir(null), Variable.g_db_name));
			Toast.makeText(this, "Файл сохранен", Toast.LENGTH_SHORT).show();
		}
		catch(IOException ex) {
			Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
		}
		finally{
			try{
				if(fos!=null)
					fos.close();
			}
			catch(IOException ex){
				Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		db.close_db();
	}
}