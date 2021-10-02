package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import android.app.AlertDialog;


public class DataUser extends AppCompatActivity {

	private EditText field_first_name, field_last_name, field_patronymic, field_age, field_birth, field_country, field_address, field_index, field_number_phone, field_phone, field_passport, field_snils, field_car, field_education, field_place_work, field_email, field_vk, field_instagram, field_telegram, field_other_social, field_relative, field_hobby, field_other;
	private TextView field_title_data_user, field_user_id, field_name_doc_1, field_name_doc_2, field_name_doc_3;
	private Button back, add_doc, add_photo, delete, save_create_user;
	private ImageView photo_1, photo_2, photo_3, photo_4;
	private Database database;
	private String user_id, name_doc_1, name_doc_2, name_doc_3;
	private byte[] byte_photo_1 = null, byte_photo_2 = null, byte_photo_3 = null, byte_photo_4 = null;
	private static final int ACTIVITY_CHOOSE_DOC = 1;
	private static final int ACTIVITY_CHOOSE_PHOTO = 2;
	private String MODE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_data_user);

		// поля
		field_title_data_user = findViewById(R.id.title_data_user);
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

		// документы и фотографии
		field_name_doc_1 = findViewById(R.id.name_doc_1);
		field_name_doc_2 = findViewById(R.id.name_doc_2);
		field_name_doc_3 = findViewById(R.id.name_doc_3);
		photo_1 = findViewById(R.id.photo_1);
		photo_2 = findViewById(R.id.photo_2);
		photo_3 = findViewById(R.id.photo_3);
		photo_4 = findViewById(R.id.photo_4);

		// кнопки
		add_doc = findViewById(R.id.add_doc);
		add_photo = findViewById(R.id.add_photo);
		back = findViewById(R.id.back_from_data_user);
		delete = findViewById(R.id.delete_user);
		save_create_user = findViewById(R.id.save_create_user);

		database = new Database(DataUser.this);

		// действия для кнопок при нажатии
		back.setOnClickListener(
			new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent = new Intent(DataUser.this, MainActivity.class);
					startActivity(intent);
				}
			}
		);

		add_doc.setOnClickListener(
			new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Toast.makeText(DataUser.this, "Выберите файл", Toast.LENGTH_SHORT).show();
						choice_file("document");
					}
				}
		);

		add_photo.setOnClickListener(
			new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Toast.makeText(DataUser.this, "Выберите фотографию", Toast.LENGTH_SHORT).show();
						choice_file("photo");
					}
				}
		);

		photo_1.setOnClickListener(
			new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					on_click_photo(1);
				}
			}
		);

		photo_2.setOnClickListener(
			new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					on_click_photo(2);
				}
			}
		);

		photo_3.setOnClickListener(
			new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					on_click_photo(3);
				}
				}
		);

		photo_4.setOnClickListener(
			new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					on_click_photo(4);
				}
			}
		);
	}

	// добавление информации в поля и добавление нажваний кнопкам и заголовкам
	@Override
	protected void onResume() {
		super.onResume();
		database.open_db();
		Intent arguments = getIntent();
		MODE = arguments.getStringExtra("mode");

		if (MODE.equals("data")) {
			field_title_data_user.setText(R.string.title_data_user);
			back.setText(R.string.button_back);
			save_create_user.setText(R.string.button_save);

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

			ArrayList<byte[]> list_bytes_photo = database.get_bytes_photo(user_id);
			byte_photo_1 = list_bytes_photo.get(0);
			byte_photo_2 = list_bytes_photo.get(1);
			byte_photo_3 = list_bytes_photo.get(2);
			byte_photo_4 = list_bytes_photo.get(3);

			if (byte_photo_1 != null)
				photo_1.setImageBitmap(convertToBitmap(byte_photo_1));

			if (byte_photo_2 != null)
				photo_2.setImageBitmap(convertToBitmap(byte_photo_2));

			if (byte_photo_3 != null)
				photo_3.setImageBitmap(convertToBitmap(byte_photo_3));

			if (byte_photo_4 != null)
				photo_4.setImageBitmap(convertToBitmap(byte_photo_4));
		}
		else if (MODE.equals("create")) {
			field_title_data_user.setText(R.string.title_create_user);
			back.setText(R.string.button_cancel);
			save_create_user.setText(R.string.button_add_user);
			user_id = String.valueOf(database.generate_id());
			field_user_id.setText("#" + user_id);
			delete.setVisibility(View.INVISIBLE);
		}
	}

	// добавление или сохранение изменений записи
	public void save_or_create_user(View view) {
		String first_name = field_first_name.getText().toString();
		if (first_name == "")
			Toast.makeText(DataUser.this, R.string.empty_first_name, Toast.LENGTH_SHORT).show();
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

//			Bitmap bitmap_1 = ((BitmapDrawable) photo_1.getDrawable()).getBitmap();
//			byte_photo_1 = convertToByte(bitmap_1);
//
//			Bitmap bitmap_2 = ((BitmapDrawable) photo_2.getDrawable()).getBitmap();
//			byte_photo_2 = convertToByte(bitmap_2);
//
//			Bitmap bitmap_3 = ((BitmapDrawable) photo_3.getDrawable()).getBitmap();
//			byte_photo_3 = convertToByte(bitmap_3);
//
//			Bitmap bitmap_4 = ((BitmapDrawable) photo_4.getDrawable()).getBitmap();
//			byte_photo_4 = convertToByte(bitmap_4);

			if (MODE.equals("data")) {
				database.update_user(data_user, user_id, byte_photo_1, byte_photo_2, byte_photo_3, byte_photo_4);
				Toast.makeText(DataUser.this, R.string.success_update_data, Toast.LENGTH_SHORT).show();
			}
			else if (MODE.equals("create")) {
				database.add_user(data_user, user_id, byte_photo_1, byte_photo_2, byte_photo_3, byte_photo_4);
				Toast.makeText(DataUser.this, R.string.success_create_data, Toast.LENGTH_SHORT).show();
			}

			Intent intent = new Intent(DataUser.this, MainActivity.class);
			startActivity(intent);
		}
	}

	// удаление записи
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

	// выбор фото или документа
	public void choice_file(String type_file) {
		Intent chooseFile;
		Intent intent;
		chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
		chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
		chooseFile.setType("*/*");

		if (type_file.equals("document")) {
			intent = Intent.createChooser(chooseFile, "Choose a file");
			startActivityForResult(intent, ACTIVITY_CHOOSE_DOC);
		}
		else if (type_file.equals("photo")) {
			intent = Intent.createChooser(chooseFile, "Choose a photo");
			startActivityForResult(intent, ACTIVITY_CHOOSE_PHOTO);
		}
	}

	// работа с выбранным документом или фото
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ACTIVITY_CHOOSE_DOC) {
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

		}
		else if (requestCode == ACTIVITY_CHOOSE_PHOTO) {
			if (resultCode != RESULT_OK || data == null) {
				super.onActivityResult(requestCode, resultCode, data);
				return;
			}

			Uri uri = data.getData();
			if (uri == null) {
				return;
			}

			Bitmap bitmap = null;

			try {
				bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (photo_1.getDrawable() == null || photo_4.getDrawable() != null) {
				byte_photo_1 = convertToByte(bitmap);
				photo_1.setImageBitmap(bitmap);
			}
			else if (photo_2.getDrawable() == null) {
				byte_photo_2 = convertToByte(bitmap);
				photo_2.setImageBitmap(bitmap);
			}
			else if (photo_3.getDrawable() == null) {
				byte_photo_3 = convertToByte(bitmap);
				photo_3.setImageBitmap(bitmap);
			}
			else if (photo_4.getDrawable() == null) {
				byte_photo_4 = convertToByte(bitmap);
				photo_4.setImageBitmap(bitmap);
			}

			//Drawable drawable = photo_1.getDrawable();
			//photo_2.setImageDrawable(drawable);
		}
		else {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	public byte[] convertToByte(Bitmap bitmap) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG,100, os);
		byte[] byteArray = os.toByteArray();
		return byteArray;
	}

	public Bitmap convertToBitmap(byte[] byte_photo) {
		Bitmap bitmapResult = BitmapFactory.decodeByteArray(byte_photo, 0, byte_photo.length);
		return bitmapResult;
	}

	public void on_click_photo(int count_photo) {
		Dialog dialog = new Dialog(DataUser.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.activity_action_photo);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog.setCancelable(false);

		TextView close_dialog = (TextView) dialog.findViewById(R.id.close_action_photo);
		Button open_photo = (Button) dialog.findViewById(R.id.open_photo);
		Button download_photo = (Button) dialog.findViewById(R.id.download_photo);
		Button delete_photo = (Button) dialog.findViewById(R.id.delete_photo);

		open_photo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dialog.dismiss();
			}
		});

		download_photo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dialog.dismiss();
			}
		});

		delete_photo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dialog.dismiss();
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

	@Override
	protected void onDestroy() {
		super.onDestroy();
		database.close_db();
	}
}