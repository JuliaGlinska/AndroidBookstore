package com.example.bookbase.database;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookbase.MainActivity;
import com.example.bookbase.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AddBooks extends AppCompatActivity {
    FloatingActionButton addButton;

    String title;
    String author;
    String price;
    String genre;
    String year;



    // -----------------------ON CREATE----------------------------
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_books);


        addButton = findViewById(R.id.AddButton);


        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(AddBooks.this, "Dodaj książkę", Toast.LENGTH_SHORT);
                toast.show();

                EditText t = findViewById(R.id.title1);
                title = t.getText().toString();
                EditText a = findViewById(R.id.author1);
                author = a.getText().toString();
                EditText p = findViewById(R.id.price1);
                price = p.getText().toString();
                EditText g = findViewById(R.id.genre1);
                genre = g.getText().toString();
                EditText y = findViewById(R.id.year1);
                year = y.getText().toString();

                ContentValues values = new ContentValues();
                values.put(DBHelper.books.TITLE, title);
                values.put(DBHelper.books.AUTHOR, author);
                values.put(DBHelper.books.PRICE, price);
                values.put(DBHelper.books.GENRE, genre);
                values.put(DBHelper.books.YEAR, year);

                if (title.isEmpty() || title == null) {
                    values.put(DBHelper.books.TITLE, "Unknown");
                }
                if (author.isEmpty() || author == null) {
                    values.put(DBHelper.books.AUTHOR, "Unknown");
                }
                if (price.isEmpty() || price == null) {
                    values.put(DBHelper.books.PRICE, "Unknown");
                }
                if (genre.isEmpty() || genre == null) {
                    values.put(DBHelper.books.GENRE, "Unknown");
                }
                if (year.isEmpty() || year == null) {
                    values.put(DBHelper.books.YEAR, "Unknown");
                }

                database.insert(DBHelper.books.TABLE_NAME, null, values);

                t.setText("");
                a.setText("");
                p.setText("");
                g.setText("");
                y.setText("");

            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



    //    ------------------MENU OPTIONS-------------------
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
        }

        return super.onOptionsItemSelected(item);
    }

}