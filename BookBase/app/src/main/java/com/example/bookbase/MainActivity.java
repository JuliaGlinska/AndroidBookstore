package com.example.bookbase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.bookbase.database.AddBooks;
import com.example.bookbase.database.DBHelper;

public class MainActivity extends AppCompatActivity {

    Button addBookToDB;
    Button orderBook;
    Button myOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addBookToDB = findViewById(R.id.button);
        orderBook = findViewById(R.id.button2);
        myOrders = findViewById(R.id.button3);


        addBookToDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), AddBooks.class);
                startActivity(intent);
            }
        });
        orderBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), OrderBooks.class);
                startActivity(intent);
            }
        });
        myOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), MyOrders.class);
                startActivity(intent);
            }
        });

        Context context = getApplicationContext();

        new DBHelper(context);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
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