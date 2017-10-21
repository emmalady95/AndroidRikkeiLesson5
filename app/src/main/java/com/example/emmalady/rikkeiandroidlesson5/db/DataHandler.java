package com.example.emmalady.rikkeiandroidlesson5.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.emmalady.rikkeiandroidlesson5.model.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liz Nguyen on 21/10/2017.
 */

public class DataHandler extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "ContactManager1";
    public static final String DATABASE_TABLE_NAME = "Contact";
    public static final String CONTACT_COLUMN_ID = "id";
    public static final String CONTACT_COLUMN_NAME = "contactName";
    public static final String CONTACT_COLUMN_NUMBER = "contactNumber";

    public static final String CREATE_TABLE = "CREATE TABLE" + DATABASE_TABLE_NAME +"("
            + CONTACT_COLUMN_ID +"INTEGER PRIMARY KEY AUTO INCREMENT NOT NULL,"
            + CONTACT_COLUMN_NAME +" text,"
            + CONTACT_COLUMN_NUMBER +"integer)";

    public DataHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_NAME);

        // Create tables again
        onCreate(db);
    }
    public void addData(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        //db = ConnectDatabase.sqLiteDatabase(MainActivity.this, DATABASE_NAME);
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACT_COLUMN_NAME, contact.getContactName());
        contentValues.put(CONTACT_COLUMN_NUMBER, contact.getContactNumber());

        db.insert(DATABASE_TABLE_NAME, null, contentValues);
        db.close();
    }

    public List<Contact> getAllData(){
        List<Contact> contactList = new ArrayList<>();
        String query = "SELECT * FROM " + DATABASE_TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        //db = ConnectDatabase.sqLiteDatabase(MainActivity.this, DATABASE_NAME);
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false){
            Contact contact = new Contact(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
            contactList.add(contact);
            cursor.moveToNext();
        }

        return contactList;
    }
}
