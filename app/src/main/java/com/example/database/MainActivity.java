package com.example.database;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

	private ListView list_users;
	private EditText line_search;
	private Button search, filter, update, open_db, create_db, save_db, close_db, settings;
	private FloatingActionButton add_user;
	private Database database;
	private ItemAdapter itemAdapter;
	private static final int ACTIVITY_CHOOSE_FILE = 123;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		line_search = findViewById(R.id.line_search);
		search = findViewById(R.id.search);
		list_users = findViewById(R.id.list_users);
		filter = findViewById(R.id.filter);
		update = findViewById(R.id.update);
		open_db = findViewById(R.id.open_db);
		create_db = findViewById(R.id.create_db);
		save_db = findViewById(R.id.save_db);
		close_db = findViewById(R.id.close_db);
		settings = findViewById(R.id.settings);
		add_user = findViewById(R.id.add_user);

		if (Variable.g_db_name != "")
			database = new Database(MainActivity.this);

		list_users.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String addition_data_user = list_users.getItemAtPosition(position).toString();
				String[] list_addition_data_user = addition_data_user.split(" ");
				String user_id = list_addition_data_user[1].replaceAll(",", "");

				Intent intent = new Intent(MainActivity.this, DataUser.class);
				intent.putExtra("user_id", user_id);
				startActivity(intent);
			}
		});

		filter.setOnClickListener(
			new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					if (Variable.g_status_db == 0)
						Toast.makeText(MainActivity.this, R.string.first_open_db, Toast.LENGTH_SHORT).show();
					else {
						Intent intent = new Intent(MainActivity.this, Filter.class);
						startActivity(intent);
					}
				}
			}
		);

		update.setOnClickListener(
			new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if (Variable.g_status_db == 0)
							Toast.makeText(MainActivity.this, R.string.first_open_db, Toast.LENGTH_SHORT).show();
						else
							get_users_from_db();
					}
				}
		);

		open_db.setOnClickListener(
			new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Toast.makeText(MainActivity.this, R.string.choice_file, Toast.LENGTH_SHORT).show();
					onBrowse();
				}
			}
		);

		save_db.setOnClickListener(
			new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					if (Variable.g_status_db == 0)
						Toast.makeText(MainActivity.this, R.string.first_open_db, Toast.LENGTH_SHORT).show();
					else {
						try {
							copy_db("from_data");
							Toast.makeText(MainActivity.this, R.string.success_copy, Toast.LENGTH_SHORT).show();
						} catch (IOException e) {
							e.printStackTrace();
							Toast.makeText(MainActivity.this, R.string.error_copy, Toast.LENGTH_SHORT).show();
						}
					}
				}
			}
		);

		close_db.setOnClickListener(
			new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					if (Variable.g_status_db == 0)
						Toast.makeText(MainActivity.this, R.string.first_open_db, Toast.LENGTH_SHORT).show();
					else {
						try {
							copy_db("from_data");
						} catch (IOException e) {
							e.printStackTrace();
						}
						database.close_db();
						list_users.setAdapter(null);
						Variable.g_status_db = 0;

					}
				}
			}
		);

		settings.setOnClickListener(
			new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					if (Variable.g_status_db == 0)
						Toast.makeText(MainActivity.this, R.string.first_open_db, Toast.LENGTH_SHORT).show();
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
						Toast.makeText(MainActivity.this, R.string.first_open_db, Toast.LENGTH_SHORT).show();
					else {
						Intent intent = new Intent(MainActivity.this, CreateUser.class);
						startActivity(intent);
					}
				}
			}
		);
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (Variable.g_status_db == 1) {
			database.open_db();
			get_users_from_db();
		}
	}

	public void get_users_from_db() {
		ArrayList<String> list_data_users = database.get_users();
		String[] list_data_user;
		ArrayList<String> title_item = new ArrayList<>();
		ArrayList<String> description_item = new ArrayList<>();

		for (String i : list_data_users) {
			list_data_user = i.split("//");
			title_item.add(list_data_user[0].replaceAll(";", " "));
			description_item.add(list_data_user[1]);
		}

		add_users_to_list(title_item, description_item);
	}

	public void add_users_to_list(ArrayList<String> main_data_users, ArrayList<String> addition_data_users) {
		Toast.makeText(MainActivity.this, main_data_users.get(0), Toast.LENGTH_SHORT).show();
		itemAdapter = new ItemAdapter(this, main_data_users, addition_data_users);
		list_users.setAdapter(itemAdapter);
	}

	public void on_search(View view) {
		if (Variable.g_status_db == 0)
			Toast.makeText(MainActivity.this, R.string.first_open_db, Toast.LENGTH_SHORT).show();
		else {
			String text_search = line_search.getText().toString();

			if (text_search.equals(""))
				get_users_from_db();
			else {
				ArrayList<String> list_data_users = database.get_users();
				ArrayList<String> list_result_search = new ArrayList<>();

				for (String i : list_data_users) {
					String[] name_user = i.split(" ");

					if (check_error(text_search.toLowerCase(), name_user[0].toLowerCase()) == 1 || check_error(text_search.toLowerCase(), name_user[1].toLowerCase()) == 1)
						list_result_search.add(name_user[0] + " " + name_user[1]);

					else if (name_user[0].equals(text_search) || name_user[1].equals(text_search))
						list_result_search.add(name_user[0] + " " + name_user[1]);
				}

				get_users_from_db();
			}

			line_search.setText("");
		}
	}

	public int check_error(String search, String text_main) {
		if (search.length() > 4)
		{
			int count = 0;
			for (int j = 0; j < search.length(); j++)
			{
				if (text_main.charAt(j) != search.charAt(j))
					count++;
			}
			if (count <= 1)
				return 1;
			else if (count <= 2 && (text_main.length() - search.length()) < 5)
				return 1;
			else
				return 0;
		}
		return 0;
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
			Variable.g_db_path_user = file_path.replaceFirst(file_name, "");

			try {
				copy_db("to_data");
			} catch (IOException e) {
				e.printStackTrace();
			}

			database = new Database(MainActivity.this);
			database.open_db();
			get_users_from_db();
		} else {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	public void on_create_db(View view) {
		Dialog dialog = new Dialog(MainActivity.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.activity_create_db);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog.setCancelable(false);

		TextView close_dialog = (TextView) dialog.findViewById(R.id.close_dialog);
		Button create_new_db = (Button) dialog.findViewById(R.id.create_new_db);
		EditText name_db = (EditText) dialog.findViewById(R.id.name_db);

		create_new_db.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String text_name_db = name_db.getText().toString() + ".db";
				if (text_name_db == "")
					Toast.makeText(MainActivity.this, "Вы ничего не ввели", Toast.LENGTH_SHORT).show();
				else {
					dialog.dismiss();

					Variable.g_db_name = text_name_db;
					database = new Database(MainActivity.this);
					database.open_db();
					list_users.setAdapter(null);

					Toast.makeText(MainActivity.this, "База данных успешно создана", Toast.LENGTH_SHORT).show();
					//create_file();
				}
			}
		});

		close_dialog.setOnClickListener(new View.OnClickListener() {
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

	public void copy_db(String mode) throws IOException {
		File sourceFile = null;
		File destFile = null;
		File sourceFile_journal = null;

		if (mode == "to_data") {
			sourceFile = new File(Variable.g_db_path_user + Variable.g_db_name);
			destFile = new File(Variable.g_db_path_app + Variable.g_db_name);
		} else if (mode == "from_data") {
			sourceFile = new File(Variable.g_db_path_app + Variable.g_db_name);
			sourceFile_journal = new File(Variable.g_db_path_app + Variable.g_db_name + "-journal");
			destFile = new File(Variable.g_db_path_user + Variable.g_db_name);
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

	@Override
	protected void onDestroy() {
		super.onDestroy();

		if (Variable.g_status_db == 1) {
			try {
				copy_db("from_data");
			} catch (IOException e) {
				e.printStackTrace();
			}

			database.close_db();
		}
	}
}