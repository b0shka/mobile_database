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

    //private Database database;
    //private SQLiteDatabase db;
    //private Cursor cursor;

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
        //database = new Database(this);

        // Довавление в список элементов
        String[] users = {"Alex", "Vanya", "Nastya"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, users);
        list_users.setAdapter(adapter);

        // Действие при нажатии на item
        list_users.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name_item = list_users.getItemAtPosition(position).toString();
                Toast.makeText(MainActivity.this, "pos: " + position + " id: " + id + " name: " + name_item, Toast.LENGTH_SHORT).show();
            }
        });

        filter.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent("com.example.database.DataUsers");
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

    // Действие при нажатии на кнопку поиска
    public void onClickSearch(View view) {
        if (line_search.getText().toString().trim().equals(""))
            Toast.makeText(MainActivity.this, R.string.empty_line, Toast.LENGTH_LONG).show();
        else {
            String text_search = line_search.getText().toString();
            Toast.makeText(MainActivity.this, text_search, Toast.LENGTH_SHORT).show();

            /*db = database.getWritableDatabase();
            ContentValues content = new ContentValues();

            content.put(database.COLUMN_FIRST_NAME, text_search);
            db.insert(database.TABLE_NAME, null, content);

            cursor = db.query(database.TABLE_NAME, null, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                int user_id = cursor.getColumnIndex(database.COLUMN_ID);
                int first_name = cursor.getColumnIndex(database.COLUMN_FIRST_NAME);
                int last_name = cursor.getColumnIndex(database.COLUMN_LAST_NAME);

                do {
                    Toast.makeText(MainActivity.this, "ID = " + cursor.getInt(user_id) + " first_name = " + cursor.getInt(first_name) + " last_name = " + cursor.getInt(last_name), Toast.LENGTH_LONG).show();
                } while (cursor.moveToNext());
            }
            else
                Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_LONG).show();
            cursor.close();*/
        }

        line_search.setText("");
    }
}