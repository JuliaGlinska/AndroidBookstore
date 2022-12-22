package com.example.bookbase.database;


import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.provider.BaseColumns;


import androidx.annotation.Nullable;



public final class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Database.db";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public class orders implements BaseColumns{
        public static final String TABLE_NAME = "Orders";
        public static final String ORDER_ID = "orderId";
        public static final String NAME = "name";
        public static final String SURNAME = "surname";
        public static final String PHONE = "phone";
        public static final String BOOK_ID = "bookId";
        public static final String DATE = "date";
    }
    public class books implements BaseColumns{
        public static final String TABLE_NAME = "Books";
        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String AUTHOR = "author";
        public static final String PRICE = "price";
        public static final String GENRE = "genre";
        public static final String YEAR = "year";
    }


    private final String SQL_CREATE_ORDERS =
            "CREATE TABLE " + orders.TABLE_NAME +
                    " ( " + orders.ORDER_ID + " INTEGER PRIMARY KEY, "
                    + orders.NAME + " TEXT,"
                    + orders.SURNAME + " TEXT,"
                    + orders.PHONE + " NUMBER,"
                    + orders.BOOK_ID + " TEXT,"
                    + orders.DATE + " DATE)";
    private final String  SQL_CREATE_BOOKS =
            "CREATE TABLE " + books.TABLE_NAME
                    + " ( " + books._ID + " INTEGER PRIMARY KEY, "
                    + books.ID + " NUMBER, "
                    + books.TITLE + " TEXT,"
                    + books.AUTHOR + " TEXT, "
                    + books.GENRE + " TEXT, "
                    + books.YEAR + " TEXT, "
                    + books.PRICE + " NUMBER)";

    private final String SQL_DELETE_ENTRIES1 = "DROP TABLE IF EXISTS " + orders.TABLE_NAME;
    private final String SQL_DELETE_ENTRIES2 = "DROP TABLE IF EXISTS " + books.TABLE_NAME;

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ORDERS);
        sqLiteDatabase.execSQL(SQL_CREATE_BOOKS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES1);
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES2);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
