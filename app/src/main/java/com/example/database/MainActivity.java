package com.example.database;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private ListView list_users;
    private EditText line_search;
    private Button search, filter, open_db, create_db;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        line_search = findViewById(R.id.line_search);
        search = findViewById(R.id.search);
        list_users = findViewById(R.id.list_users);
        filter = findViewById(R.id.filter);
        open_db = findViewById(R.id.open_db);
        create_db = findViewById(R.id.create_db);
        db = new Database(this);

        // Действие при нажатии на item
        list_users.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name_item = list_users.getItemAtPosition(position).toString();
                Toast.makeText(MainActivity.this, "pos: " + position + " id: " + id + " name: " + name_item, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent("com.example.database.DataUser");
                startActivity(intent);
            }
        });

        filter.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent("com.example.database.Filter");
                    startActivity(intent);
                }
            }
        );

        open_db.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "Open DB", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        create_db.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "Create DB", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        db.open_db();

        // Довавление в список элементов
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, db.get_db());
        list_users.setAdapter(adapter);
    }

    // Действие при нажатии на кнопку поиска
    public void onClickSearch(View view) {
        if (line_search.getText().toString().trim().equals(""))
            Toast.makeText(MainActivity.this, R.string.empty_line, Toast.LENGTH_LONG).show();
        else {
            String text_search = line_search.getText().toString();
            Toast.makeText(MainActivity.this, text_search, Toast.LENGTH_SHORT).show();

            db.insert_db(text_search, "Last name");

            list_users.setAdapter(null);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, db.get_db());
            list_users.setAdapter(adapter);
        }

        line_search.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close_db();
    }
}