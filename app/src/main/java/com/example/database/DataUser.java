package com.example.database;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class DataUser extends AppCompatActivity {

	private EditText field_first_name, field_last_name, field_patronymic, field_age, field_birth;
	private TextView title_activity;
	private Button save_user, back;
	private Database db;

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

		db = new Database(DataUser.this);

		Intent arguments = getIntent();
		String[] name = arguments.getStringExtra("name").split(" ");
		ArrayList<String> list_data_user = db.get_data_user(name[0], name[1]);

		field_first_name.setText(name[0]);
		field_last_name.setText(name[1]);
		field_patronymic.setText(list_data_user.get(0));
		field_age.setText(list_data_user.get(1));
		field_birth.setText(list_data_user.get(2));

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
	}

	public void save_user(View view) {
		String first_name = field_first_name.getText().toString();
		String last_name = field_last_name.getText().toString();
		String patronymic = field_patronymic.getText().toString();
		String age = field_age.getText().toString();
		String birth = field_birth.getText().toString();

		//db.add_user(first_name, last_name, patronymic, age, birth);

		Intent intent = new Intent(DataUser.this, MainActivity.class);
		startActivity(intent);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		db.close_db();
	}
}