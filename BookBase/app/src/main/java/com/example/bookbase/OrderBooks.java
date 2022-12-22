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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookbase.database.AddBooks;
import com.example.bookbase.database.DBHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class OrderBooks extends AppCompatActivity {
    private Spinner spinner;
    private ArrayList<Book> bookList;
    private BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_books);

        spinner = findViewById(R.id.spinner);

        bookList = new ArrayList<>();
        adapter = new BookAdapter(this, bookList);
        spinner.setAdapter(adapter);

        // Retrieve book data from SQLite database and add to bookList
        SQLiteDatabase db = openOrCreateDatabase(DBHelper.DATABASE_NAME, MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("SELECT * FROM "+DBHelper.books.TABLE_NAME, null);
        while (cursor.moveToNext()) {
            int titleIndex = cursor.getColumnIndex("title");
            String title = null;
            if (titleIndex >= 0) {
                title = cursor.getString(titleIndex);
            }
            int authorIndex = cursor.getColumnIndex("author");
            String author = null;
            if (authorIndex >= 0) {
                author = cursor.getString(authorIndex);
            }
            int priceIndex = cursor.getColumnIndex("price");
            String price = null;
            if (priceIndex >= 0) {
                price = cursor.getString(priceIndex);
            }
            int genreIndex = cursor.getColumnIndex("genre");
            String genre = null;
            if (genreIndex >= 0) {
                genre = cursor.getString(genreIndex);
            }
            int yearIndex = cursor.getColumnIndex("year");
            String year = null;
            if (yearIndex >= 0) {
                year = cursor.getString(yearIndex);
            }
            int idIndex = cursor.getColumnIndex("_id");
            int id = 0;
            if (idIndex >= 0) {
                id = cursor.getInt(idIndex);
            }
            bookList.add(new Book(id,title, author, price, genre, year));
        }
        cursor.close();
        db.close();

        // Set OnItemSelectedListener for spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Book book = adapter.getItem(position);

                View view1 = LayoutInflater.from(OrderBooks.this).inflate(R.layout.recycler_view_item, null);
                final EditText nameEditText = view1.findViewById(R.id.name);
                final EditText surnameEditText = view1.findViewById(R.id.surname);
                final EditText phoneEditText = view1.findViewById(R.id.phone);
                Button submitButton = view1.findViewById(R.id.submit_button);
                Button button = view1.findViewById(R.id.orderbutton);

                RelativeLayout container = findViewById(R.id.container);
                container.addView(view1);

                // Set OnClickListener for submit button
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        View view1 = LayoutInflater.from(OrderBooks.this).inflate(R.layout.recycler_view_item, null,false);
                        final EditText nameEditText = view1.findViewById(R.id.name);
                        final EditText surnameEditText = view1.findViewById(R.id.surname);
                        final EditText phoneEditText = view1.findViewById(R.id.phone);
                        Button submitButton = view1.findViewById(R.id.submit_button);

                        submitButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Retrieve text from EditTexts
                                String name = nameEditText.getText().toString();
                                String surname = surnameEditText.getText().toString();
                                String phone = phoneEditText.getText().toString();

                                // Insert data into orders database
                                SQLiteDatabase db = openOrCreateDatabase(DBHelper.DATABASE_NAME, MODE_PRIVATE, null);
                                ContentValues values = new ContentValues();
                                values.put("bookid", book.getId());
                                values.put("name", name);
                                values.put("surname", surname);
                                values.put("phone", phone);
                                values.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));
                                db.insert(DBHelper.orders.TABLE_NAME, null, values);
                                db.close();

                                nameEditText.setText("");
                                surnameEditText.setText("");
                                phoneEditText.setText("");
                            }
                        });
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    private class BookAdapter extends ArrayAdapter<Book> {

        public BookAdapter(@NonNull Context context, ArrayList<Book> books) {
            super(context, 0, books);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Book book = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.recycler_view_item_1, parent, false);
            }
            TextView titleView = convertView.findViewById(R.id.texttitle);
            TextView authorView = convertView.findViewById(R.id.textauthor);
            titleView.setText(book.getTitle());
            authorView.setText(book.getAuthor());
            return convertView;
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Book book = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.recycler_view_item_1, parent, false);
                return convertView;
            }
            TextView titleView = convertView.findViewById(R.id.texttitle);
            TextView authorView = convertView.findViewById(R.id.textauthor);
            TextView priceView = convertView.findViewById(R.id.textprice);
            TextView genreView = convertView.findViewById(R.id.textgenre);
            TextView yearView = convertView.findViewById(R.id.textyear);
            ImageView imageView = convertView.findViewById(R.id.imgview);
            Button button = convertView.findViewById(R.id.orderbutton);
            titleView.setText(book.getTitle());
            authorView.setText(book.getAuthor());
            priceView.setText(book.getPrice()+" zł");
            genreView.setText(book.getGenre());
            yearView.setText(book.getYear());
            imageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.nocover));
        return convertView;
        }
    }


    public class Book {
        private int id;
        private String title;
        private String author;
        private String price;
        private String genre;
        private String year;

        public Book(int id, String title, String author, String price, String genre, String year) {
            this.id = id;
            this.title = title;
            this.author = author;
            this.price = price;
            this.genre = genre;
            this.year = year;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public String getPrice() {
            return price;
        }

        public String getGenre() {
            return genre;
        }

        public String getYear() {
            return year;
        }
        public int getId(){
            return id;
        }

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