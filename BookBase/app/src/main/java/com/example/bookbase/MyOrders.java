package com.example.bookbase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bookbase.database.DBHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MyOrders extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders2);

        // Query to join data from books and orders tables
        String query = "SELECT b.title, b.author, b.price, o.name, o.surname, o.date, o.phone FROM books b INNER JOIN orders o ON b._id = o.bookid";

// Retrieve data from database using the query
        SQLiteDatabase db = openOrCreateDatabase(DBHelper.DATABASE_NAME, MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery(query, null);

// Create a list of strings to hold the data
        ArrayList<String> dataList = new ArrayList<>();

// Iterate over the cursor and add the data to the list
        while (cursor.moveToNext()) {
            int titleIndex = cursor.getColumnIndex("title");
            String title = cursor.getString(titleIndex);

            int authorIndex = cursor.getColumnIndex("author");
            String author = cursor.getString(authorIndex);

            int priceIndex = cursor.getColumnIndex("price");
            int price = cursor.getInt(priceIndex);

            int nameIndex = cursor.getColumnIndex("name");
            String name = cursor.getString(nameIndex);

            int surnameIndex = cursor.getColumnIndex("surname");
            String surname = cursor.getString(surnameIndex);

            int dateIndex = cursor.getColumnIndex("date");
            String date = cursor.getString(dateIndex);

            int phoneIndex = cursor.getColumnIndex("phone");
            String phone = cursor.getString(phoneIndex);

            // Add the data to the list as a formatted string
            dataList.add(String.format("''%s' by %s, %d zł,\n Imię: %s, Nazwisko: %s, Data: %s, Nr: %s", title, author, price, name, surname, date, phone));
        }
        Spinner spinner = findViewById(R.id.my_orders_spinner);

// Create an ArrayAdapter using the data from the ArrayList
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dataList);

// Set the layout for the dropdown menu
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Set the adapter for the spinner
        spinner.setAdapter(adapter);


// Close the cursor and database
        cursor.close();
        db.close();
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