package com.example.database;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class DataUser extends AppCompatActivity {

	private EditText field_first_name, field_last_name, field_patronymic, field_age, field_birth;
	private TextView title_activity;
	private Button add_save_user;
	private String mode_activity;
	private Database db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_data_user);

		title_activity = findViewById(R.id.title_activity);
		field_first_name = findViewById(R.id.firts_name);
		field_last_name = findViewById(R.id.last_name);
		field_patronymic = findViewById(R.id.patronymic);
		field_age = findViewById(R.id.age);
		field_birth = findViewById(R.id.birth);
		add_save_user = findViewById(R.id.add_save_user);

		db = new Database(this);

		Intent arguments = getIntent();
		mode_activity = arguments.getStringExtra("mode").toLowerCase();

		if (mode_activity == "read") {
			String[] name = arguments.getStringExtra("name").split(" ");

			title_activity.setText(R.string.title_data_user + " " + name[0] + " " + name[1]);
			add_save_user.setText(R.string.button_save_user);

			field_first_name.setText(name[0]);
			field_last_name.setText(name[1]);
		}
		else if (mode_activity == "add") {
			title_activity.setText(R.string.title_create_user);
			add_save_user.setText(R.string.button_add_user);
		}
	}

	public void add_user(View view) {
		if (mode_activity == "add") {
			String first_name = field_first_name.getText().toString();
			String last_name = field_last_name.getText().toString();
			String patronymic = field_patronymic.getText().toString();
			String age = field_age.getText().toString();
			String birth = field_birth.getText().toString();

			db.insert_db(first_name, last_name, patronymic, age, birth);
		}

		Intent intent = new Intent(DataUser.this, MainActivity.class);
		startActivity(intent);
	}
}