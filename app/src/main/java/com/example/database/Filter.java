package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class Filter extends AppCompatActivity {

	private Spinner list_tables, list_city;
	private Button save_filter, back, drop_filter_to_start;
	private EditText name_new_table, new_change_name_table, age_min, age_max;
	private CheckBox use_filter_age, use_filter_city;
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
		use_filter_age = findViewById(R.id.use_filter_age);
		use_filter_city = findViewById(R.id.use_filter_city);
		save_filter = findViewById(R.id.save_filter);
		drop_filter_to_start = findViewById(R.id.drop_filter_to_start);
		back = findViewById(R.id.back_from_filter);

		database = new Database(Filter.this);
		database.open_db();

		add_table();
		add_age();
		add_city();
		add_status_checkbox();

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

	public void add_age() {
		age_min.setText(String.valueOf(Variable.g_age_min));
		age_max.setText(String.valueOf(Variable.g_age_max));
	}

	public void add_city() {
		ArrayList<String> array_users_city = database.get_city();

		Set<String> array_data_set = new LinkedHashSet<>(array_users_city);
		array_users_city.clear();
		array_users_city.addAll(array_data_set);

		String[] list_users_city = new String[array_users_city.size()];

		for (int i = 0; i < array_users_city.size(); i++) {
			list_users_city[i] = array_users_city.get(i);
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list_users_city);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		list_city.setAdapter(adapter);

		if (!Variable.g_city.equals(""))
			list_city.setSelection(adapter.getPosition(Variable.g_city));
	}

	public void add_status_checkbox() {
		if (Variable.g_status_apply_filter_age == 1)
			use_filter_age.setChecked(true);
		else
			use_filter_age.setChecked(false);

		if (Variable.g_status_apply_filter_city == 1)
			use_filter_city.setChecked(true);
		else
			use_filter_city.setChecked(false);
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

	public void drop_filter(View view) {
		add_table();
		database.get_data_filter();
		add_age();

		Variable.g_city = "";
		add_city();
		Variable.g_status_apply_filter_age = 0;
		Variable.g_status_apply_filter_city = 0;
		add_status_checkbox();
	}

	public void apply_filter(View view) {
		String name_table = list_tables.getSelectedItem().toString();
		Variable.g_table_name = name_table;

		if (use_filter_age.isChecked()) {
			Variable.g_status_apply_filter_age = 1;
			String number_age_min = String.valueOf(age_min.getText().toString());
			String number_age_max = String.valueOf(age_max.getText().toString());
			if (!number_age_min.equals(""))
				Variable.g_age_min = Integer.parseInt(number_age_min);
			if (!number_age_max.equals(""))
				Variable.g_age_max = Integer.parseInt(number_age_max);
		}
		else {
			Variable.g_status_apply_filter_age = 0;
			database.get_data_filter();
		}
		if (use_filter_city.isChecked()) {
			Variable.g_status_apply_filter_city = 1;
			if (!list_city.getSelectedItem().toString().equals("")) {
				String city = list_city.getSelectedItem().toString();
				Variable.g_city = city;
			}
		}
		else {
			Variable.g_status_apply_filter_city = 0;
			Variable.g_city = "";
		}

		Intent intent = new Intent(Filter.this, MainActivity.class);
		startActivity(intent);
	}
}