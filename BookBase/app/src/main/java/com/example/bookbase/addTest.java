package com.example.bookbase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.bookbase.database.DBHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class addTest extends AppCompatActivity {
    EditText name, surname, phone, id;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_test);

        submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = findViewById(R.id.idd);
                name = findViewById(R.id.nam);
                surname = findViewById(R.id.sur);
                phone = findViewById(R.id.ph);

                int idSend = Integer.parseInt(id.getText().toString());
                String nameSend = name.getText().toString();
                String surnameSend = surname.getText().toString();
                String phoneSend = phone.getText().toString();

                SQLiteDatabase db = openOrCreateDatabase(DBHelper.DATABASE_NAME, MODE_PRIVATE, null);
                ContentValues values = new ContentValues();
                values.put("bookid", idSend);
                values.put("name", nameSend);
                values.put("surname", surnameSend);
                values.put("phone", phoneSend);
                values.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));
                db.insert(DBHelper.orders.TABLE_NAME, null, values);
                db.close();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_author:
                Intent intent = new Intent(getBaseContext(), Author.class);
                startActivity(intent);
                return true;
            case R.id.menu_orders:
                Intent intent1 = new Intent(getBaseContext(), addTest.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}