package com.example.database;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

	private ListView list_users;
	private EditText line_search;
	private Button search, filter, open_db, create_db, settings;
	private FloatingActionButton add_user;
	private Database database;
	private static final int ACTIVITY_CHOOSE_FILE = 123;

	private RecyclerView menu_recycler;
	private MenuAdapter menu_adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		List<Menu> menuList = new ArrayList<>();
		menuList.add(new Menu(1, "Фильтры"));
		menuList.add(new Menu(2, "Открыть"));
		menuList.add(new Menu(3, "Создать"));
		menuList.add(new Menu(4, "Закрыть"));
		menuList.add(new Menu(5, "Настройки"));

		setMenuRecycler(menuList);

		line_search = findViewById(R.id.line_search);
		search = findViewById(R.id.search);
		list_users = findViewById(R.id.list_users);
		filter = findViewById(R.id.filter);
		open_db = findViewById(R.id.open_db);
		create_db = findViewById(R.id.create_db);
		settings = findViewById(R.id.settings);
		add_user = findViewById(R.id.add_user);

		if (Variable.g_db_name != "")
			database = new Database(MainActivity.this);

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
						if (Variable.g_status_db == 1) {
							try {
								database.copy_db("from_data");
								Toast.makeText(MainActivity.this, "Copy DB complete", Toast.LENGTH_SHORT).show();
							} catch (IOException e) {
								e.printStackTrace();
								Toast.makeText(MainActivity.this, "Error copy db", Toast.LENGTH_SHORT).show();
							}
						}
						//Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
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
					}
				}
			}
		);
	}

	private void setMenuRecycler(List<Menu> menuList) {
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
		menu_recycler = findViewById(R.id.menu);
		menu_recycler.setLayoutManager(layoutManager);

		menu_adapter = new MenuAdapter(this, menuList);
		menu_recycler.setAdapter(menu_adapter);
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (Variable.g_status_db == 1) {
			database.open_db();
			add_users_to_list();
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
				android.R.layout.simple_list_item_1, database.get_users());
		list_users.setAdapter(adapter);
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

			String file_path = uri.getPath().replace(':', '/').replaceFirst("document", "storage/self");
			String[] list_file_path = file_path.split("/");
			String file_name = list_file_path[list_file_path.length - 1];
			Variable.g_db_name = file_name;
			Variable.g_db_path = file_path;

			database = new Database(MainActivity.this);
			database.open_db();

			try {
				database.copy_db("to_data");
			} catch (IOException e) {
				e.printStackTrace();
			}

			add_users_to_list();
			Toast.makeText(MainActivity.this, "База данных открыта", Toast.LENGTH_SHORT).show();

		} else {
			super.onActivityResult(requestCode, resultCode, data);
		}
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
				Variable.g_db_name = name_db.getText().toString() + ".db";
				dialog.dismiss();

				database = new Database(MainActivity.this);
				database.open_db();
				add_users_to_list();

				Toast.makeText(MainActivity.this, "База данных успешно создана", Toast.LENGTH_SHORT).show();
				//create_file();
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

		if (Variable.g_status_db == 1) {
			database.close_db();
		}
	}
}