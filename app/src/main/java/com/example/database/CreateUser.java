package com.example.database;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class CreateUser extends AppCompatActivity {

	private EditText field_first_name, field_last_name, field_patronymic, field_age, field_birth, field_country, field_address, field_index, field_number_phone, field_phone, field_passport, field_snils, field_car, field_education, field_place_work, field_email, field_vk, field_instagram, field_telegram, field_other_social, field_relative, field_hobby, field_other;
	private Button back;
	private Database database;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_user);

		field_first_name = findViewById(R.id.firts_name);
		field_last_name = findViewById(R.id.last_name);
		field_patronymic = findViewById(R.id.patronymic);
		field_age = findViewById(R.id.age);
		field_birth = findViewById(R.id.birth);
		field_country = findViewById(R.id.country);
		field_address = findViewById(R.id.address);
		field_index = findViewById(R.id.index);
		field_number_phone = findViewById(R.id.number_phone);
		field_phone = findViewById(R.id.phone);
		field_passport = findViewById(R.id.passport);
		field_snils = findViewById(R.id.snils);
		field_car = findViewById(R.id.car);
		field_education = findViewById(R.id.education);
		field_place_work = findViewById(R.id.place_work);
		field_email = findViewById(R.id.email);
		field_vk = findViewById(R.id.vk);
		field_instagram = findViewById(R.id.instagram);
		field_telegram = findViewById(R.id.telegram);
		field_other_social = findViewById(R.id.other_social);
		field_relative = findViewById(R.id.relative);
		field_hobby = findViewById(R.id.hobby);
		field_other = findViewById(R.id.other);

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
			String country = field_country.getText().toString();
			String address = field_address.getText().toString();
			String index = field_index.getText().toString();
			String number_phone = field_number_phone.getText().toString();
			String phone = field_phone.getText().toString();
			String passport = field_passport.getText().toString();
			String snils = field_snils.getText().toString();
			String car = field_car.getText().toString();
			String education = field_education.getText().toString();
			String place_work = field_place_work.getText().toString();
			String email = field_email.getText().toString();
			String vk = field_vk.getText().toString();
			String instagram = field_instagram.getText().toString();
			String telegram = field_telegram.getText().toString();
			String other_social = field_other_social.getText().toString();
			String relative = field_relative.getText().toString();
			String hobby = field_hobby.getText().toString();
			String other = field_other.getText().toString();

			ArrayList<String> data_user = new ArrayList<>();
			data_user.add(first_name);
			data_user.add(last_name);
			data_user.add(patronymic);
			data_user.add(age);
			data_user.add(birth);
			data_user.add(country);
			data_user.add(address);
			data_user.add(index);
			data_user.add(number_phone);
			data_user.add(phone);
			data_user.add(passport);
			data_user.add(snils);
			data_user.add(car);
			data_user.add(education);
			data_user.add(place_work);
			data_user.add(email);
			data_user.add(vk);
			data_user.add(instagram);
			data_user.add(telegram);
			data_user.add(other_social);
			data_user.add(relative);
			data_user.add(hobby);
			data_user.add(other);

			database.add_user(data_user);
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