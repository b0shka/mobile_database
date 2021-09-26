package com.example.database;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class CreateUser extends AppCompatActivity {

	private EditText field_first_name, field_last_name, field_patronymic, field_age, field_birth;
	private TextView title_activity;
	private Button create_user, back;
	private Database database;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_user);

		title_activity = findViewById(R.id.title_data_user);
		field_first_name = findViewById(R.id.firts_name_create);
		field_last_name = findViewById(R.id.last_name_create);
		field_patronymic = findViewById(R.id.patronymic_create);
		field_age = findViewById(R.id.age_create);
		field_birth = findViewById(R.id.birth_create);
		create_user = findViewById(R.id.save_user);
		back = findViewById(R.id.back_from_create);

		database = new Database(CreateUser.this);

		back.setOnClickListener(
			new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent = new Intent(CreateUser.this, MainActivity.class);
					startActivity(intent);
				}
			}
		);
	}

	@Override
	protected void onResume() {
		super.onResume();
		database.open_db();
	}

	public void create_user(View view) {
		String first_name = field_first_name.getText().toString();
		if (first_name == "")
			Toast.makeText(CreateUser.this, "Вы не написали имя", Toast.LENGTH_SHORT).show();
		else {
			String last_name = field_last_name.getText().toString();
			String patronymic = field_patronymic.getText().toString();
			String age = field_age.getText().toString();
			String birth = field_birth.getText().toString();

			database.add_user(first_name, last_name, patronymic, age, birth);
			Toast.makeText(CreateUser.this, "Запись успешно создана", Toast.LENGTH_SHORT).show();

			try {
				Intent intent = new Intent(CreateUser.this, MainActivity.class);
				startActivity(intent);
			} catch (Exception e) {
				Toast.makeText(CreateUser.this, e.toString(), Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		database.close_db();
	}
}