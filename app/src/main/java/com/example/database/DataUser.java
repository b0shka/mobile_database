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

	private EditText field_first_name, field_last_name, field_patronymic, field_age, field_birth, field_country, field_address, field_index, field_number_phone, field_phone, field_passport, field_snils, field_car, field_education, field_place_work, field_email, field_vk, field_instagram, field_telegram, field_other_social, field_relative, field_hobby, field_other;
	private TextView field_user_id;
	private Button back;
	private Database database;
	String user_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_data_user);

		field_user_id = findViewById(R.id.user_id);
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

		back = findViewById(R.id.back_from_data_user);

		database = new Database(DataUser.this);

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
		database.open_db();

		Intent arguments = getIntent();
		user_id = arguments.getStringExtra("user_id");
		field_user_id.setText("#" + user_id);
		ArrayList<String> list_data_user = database.get_data_user(user_id);

		field_first_name.setText(list_data_user.get(0));
		field_last_name.setText(list_data_user.get(1));
		field_patronymic.setText(list_data_user.get(2));
		field_age.setText(list_data_user.get(3));
		field_birth.setText(list_data_user.get(4));
		field_country.setText(list_data_user.get(5));
		field_address.setText(list_data_user.get(6));
		field_index.setText(list_data_user.get(7));
		field_number_phone.setText(list_data_user.get(8));
		field_phone.setText(list_data_user.get(9));
		field_passport.setText(list_data_user.get(10));
		field_snils.setText(list_data_user.get(11));
		field_car.setText(list_data_user.get(12));
		field_education.setText(list_data_user.get(13));
		field_place_work.setText(list_data_user.get(14));
		field_email.setText(list_data_user.get(15));
		field_vk.setText(list_data_user.get(16));
		field_instagram.setText(list_data_user.get(17));
		field_telegram.setText(list_data_user.get(18));
		field_other_social.setText(list_data_user.get(19));
		field_relative.setText(list_data_user.get(20));
		field_hobby.setText(list_data_user.get(21));
		field_other.setText(list_data_user.get(22));
	}

	public void save_user(View view) {
		String first_name = field_first_name.getText().toString();
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

		ArrayList<String> new_data_user = new ArrayList<>();
		new_data_user.add(first_name);
		new_data_user.add(last_name);
		new_data_user.add(patronymic);
		new_data_user.add(age);
		new_data_user.add(birth);
		new_data_user.add(country);
		new_data_user.add(address);
		new_data_user.add(index);
		new_data_user.add(number_phone);
		new_data_user.add(phone);
		new_data_user.add(passport);
		new_data_user.add(snils);
		new_data_user.add(car);
		new_data_user.add(education);
		new_data_user.add(place_work);
		new_data_user.add(email);
		new_data_user.add(vk);
		new_data_user.add(instagram);
		new_data_user.add(telegram);
		new_data_user.add(other_social);
		new_data_user.add(relative);
		new_data_user.add(hobby);
		new_data_user.add(other);

		database.update_user(new_data_user, user_id);
		Toast.makeText(DataUser.this, R.string.success_update_data, Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(DataUser.this, MainActivity.class);
		startActivity(intent);
	}

	public void delete(View view) {
		AlertDialog.Builder accept_window = new AlertDialog.Builder(DataUser.this);
		accept_window.setMessage(R.string.you_sure).setCancelable(false)
				.setPositiveButton("Да", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						database.delete_user(user_id);
						Toast.makeText(DataUser.this, R.string.success_delete_data, Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(DataUser.this, MainActivity.class);
						startActivity(intent);
					}
				})
				.setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						dialogInterface.cancel();
					}
				});

		AlertDialog alert = accept_window.create();
		alert.setTitle(R.string.title_delete_data);
		alert.show();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		database.close_db();
	}
}