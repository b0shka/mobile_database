package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class Filter extends AppCompatActivity {

	private Spinner list_tables, list_city;
	private Button save_filter, back;
	private EditText name_new_table, new_change_name_table, age_min, age_max;
	private Database database;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter);

		list_tables = findViewById(R.id.list_table);
		list_city = findViewById(R.id.list_city);
		name_new_table = findViewById(R.id.name_new_table);
		new_change_name_table = findViewById(R.id.new_change_name_table);
		age_min = findViewById(R.id.age_min);
		age_max = findViewById(R.id.age_max);
		save_filter = findViewById(R.id.save_filter);
		back = findViewById(R.id.back_from_filter);

		database = new Database(Filter.this);
		database.open_db();

		add_table();
		add_city();

		new_change_name_table.setText(Variable.g_table_name);

		back.setOnClickListener(
			new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent = new Intent(Filter.this, MainActivity.class);
					startActivity(intent);
				}
			}
		);
	}

	public void add_table() {
		ArrayList<String> array_name_tables = database.get_tables();
		String[] list_name_table = new String[array_name_tables.size()];

		for (int i = 0; i < array_name_tables.size(); i++) {
			list_name_table[i] = array_name_tables.get(i);
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list_name_table);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		list_tables.setAdapter(adapter);

		list_tables.setSelection(adapter.getPosition(Variable.g_table_name));
	}

	public void add_city() {
		ArrayList<String> array_users_city = database.get_city();
		String[] list_users_city = new String[array_users_city.size()];

		for (int i = 0; i < array_users_city.size(); i++) {
			list_users_city[i] = array_users_city.get(i);
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list_users_city);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		list_city.setAdapter(adapter);
	}

	public void delete_table(View view) {
		String name_table = list_tables.getSelectedItem().toString();
		database.delete_table(name_table);
		Toast.makeText(Filter.this, R.string.success_delete_table, Toast.LENGTH_SHORT).show();

		if (Variable.g_table_name.equals(name_table))
			database.get_global_table();

		add_table();
	}

	public void new_table(View view) {
		String name_table = name_new_table.getText().toString();
		database.create_new_table(name_table);
		Toast.makeText(Filter.this, R.string.success_create_table, Toast.LENGTH_SHORT).show();
		add_table();
		name_new_table.setText("");
	}

	public void change_name(View view) {
		String name_table = new_change_name_table.getText().toString();
		database.change_name_table(name_table);
		Variable.g_table_name = name_table;
		add_table();
	}

	public void apply_filter(View view) {
		String name_table = list_tables.getSelectedItem().toString();
		Variable.g_table_name = name_table;

		String number_age_min = String.valueOf(age_min.getText().toString());
		String number_age_max = String.valueOf(age_max.getText().toString());
		if (!number_age_min.equals(""))
			Variable.g_age_min = Integer.parseInt(number_age_min);
		if (!number_age_max.equals(""))
			Variable.g_age_max = Integer.parseInt(number_age_max);

		String city = list_city.getSelectedItem().toString();
		Variable.g_city = city;

		Intent intent = new Intent(Filter.this, MainActivity.class);
		startActivity(intent);
	}
}