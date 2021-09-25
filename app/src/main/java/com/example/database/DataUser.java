package com.example.database;

import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import android.app.AlertDialog;


public class DataUser extends AppCompatActivity {

	private EditText field_first_name, field_last_name, field_patronymic, field_age, field_birth;
	private TextView title_activity;
	private Button save_user, back;
	private Database db;
	String[] name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_data_user);

		title_activity = findViewById(R.id.title_data_user);
		field_first_name = findViewById(R.id.firts_name);
		field_last_name = findViewById(R.id.last_name);
		field_patronymic = findViewById(R.id.patronymic);
		field_age = findViewById(R.id.age);
		field_birth = findViewById(R.id.birth);
		save_user = findViewById(R.id.save_user);
		back = findViewById(R.id.back_from_data_user);

		db = new Database(DataUser.this, "server.db");

		back.setOnClickListener(
			new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent = new Intent(DataUser.this, MainActivity.class);
					startActivity(intent);
				}
			}
		);
	}

	@Override
	protected void onResume() {
		super.onResume();
		db.open_db();

		Intent arguments = getIntent();
		name = arguments.getStringExtra("name").split(" ");
		ArrayList<String> list_data_user = db.get_data_user(name[0], name[1]);

		field_first_name.setText(name[0]);
		field_last_name.setText(name[1]);
		field_patronymic.setText(list_data_user.get(0));
		field_age.setText(list_data_user.get(1));
		field_birth.setText(list_data_user.get(2));
	}

	public void save_user(View view) {
		String first_name = field_first_name.getText().toString();
		String last_name = field_last_name.getText().toString();
		String patronymic = field_patronymic.getText().toString();
		String age = field_age.getText().toString();
		String birth = field_birth.getText().toString();

		ArrayList<String> new_data_user = new ArrayList<>();
		new_data_user.add(first_name);
		new_data_user.add(last_name);
		new_data_user.add(patronymic);
		new_data_user.add(age);
		new_data_user.add(birth);

		db.update_user(new_data_user, name[0], name[1]);

		Toast.makeText(DataUser.this, "Данные обновлены", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(DataUser.this, MainActivity.class);
		startActivity(intent);
	}

	public void delete(View view) {
		AlertDialog.Builder accept_window = new AlertDialog.Builder(DataUser.this);
		accept_window.setMessage("Вы уверевы?").setCancelable(false)
				.setPositiveButton("Да", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						String first_name = field_first_name.getText().toString();
						String last_name = field_last_name.getText().toString();
						String patronymic = field_patronymic.getText().toString();

						ArrayList<String> delete_data_user = new ArrayList<>();
						delete_data_user.add(first_name);
						delete_data_user.add(last_name);
						delete_data_user.add(patronymic);

						db.delete_user(delete_data_user);

						Toast.makeText(DataUser.this, "Данные удалены", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(DataUser.this, MainActivity.class);
						startActivity(intent);
					}
				})
				.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						dialogInterface.cancel();
					}
				});

		AlertDialog alert = accept_window.create();
		alert.setTitle("Удаление записи");
		alert.show();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		db.close_db();
	}
}