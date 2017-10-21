package com.example.emmalady.rikkeiandroidlesson5.activity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.emmalady.rikkeiandroidlesson5.R;
import com.example.emmalady.rikkeiandroidlesson5.adapter.ContactAdapter;
import com.example.emmalady.rikkeiandroidlesson5.db.ConnectDatabase;
import com.example.emmalady.rikkeiandroidlesson5.model.Contact;
import com.example.emmalady.rikkeiandroidlesson5.model.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String DATABASE_NAME = "ContactManager1.sqlite";
    public static final String DATABASE_TABLE_NAME = "Contact";
    public static final String CONTACT_COLUMN_ID = "id";
    public static final String CONTACT_COLUMN_NAME = "contactName";
    public static final String CONTACT_COLUMN_NUMBER = "contactNumber";

    List<Contact> contactList = new ArrayList<>();
    private Dialog dialog;
    Button btAdd;
    Button btSwitch;
    Button btAdSave;
    Button btClose;
    Button btSdSave;
    Button btDelete;

    EditText etContactName;
    EditText etContactNumber;

    EditText etContactNameShow;
    EditText etContactNumberShow;

    TextView tvID;

    RecyclerView mRcContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewByIdActivity();

        //addMoreContact();
        contactList = getAllData();

        ContactAdapter contactAdapter = new ContactAdapter(contactList);
        mRcContact.setAdapter(contactAdapter);
        // mRcContact.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL,false));
        mRcContact.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //Create Dialog
            dialog = new Dialog(MainActivity.this);
            //Set layout for Dialog
            dialog.setContentView(R.layout.item_add_dialog);
            //Set title for Dialog
            dialog.setTitle("Add Contact");
            //Show Dialog
            dialog.show();

            //Set on click listener for button of Dialog
            findViewByIdAddDialog();
            btAdSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = etContactName.getText().toString();
                    int number = Integer.parseInt(etContactNumber.getText().toString());
                    addMoreContact(name, number);
                    Intent i = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(i);
                }
            });
            btClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.hide();
                }
            });
            }
        });

        btSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            ContactAdapter contactAdapter = new ContactAdapter(contactList);
            mRcContact.setAdapter(contactAdapter);
            mRcContact.setLayoutManager(new GridLayoutManager(MainActivity.this, 3, LinearLayoutManager.VERTICAL,false));
            }
        });

        mRcContact.addOnItemTouchListener(new RecyclerItemClickListener(MainActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Create Dialog
                dialog = new Dialog(MainActivity.this);
                //Set layout for Dialog
                dialog.setContentView(R.layout.item_show_dialog);
                //Set title for Dialog
                dialog.setTitle("Show Contact");
                //Show Dialog
                dialog.show();

                //Set on click listener for button of Dialog
                findViewByIdShowDialog();

                //Set text for EditText of Show Diaglog
                int id = getID(contactList.get(position));
                tvID = (TextView) findViewById(R.id.tvID);
                tvID.setText(String.valueOf(id));

                String query = "SELECT contactName, contactNumber FROM " + DATABASE_TABLE_NAME + "WHERE id = "+ id;
                SQLiteDatabase db;
                db = ConnectDatabase.sqLiteDatabase(MainActivity.this, DATABASE_NAME);
                Cursor cursor = db.rawQuery(query, null);
                cursor.moveToFirst();
                String sName = cursor.getString(0);
                int sNumber = cursor.getInt(1);

                etContactNameShow.setText(sName);
                etContactNumberShow.setText(String.valueOf(sNumber));

                btSdSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int id = Integer.parseInt(tvID.getText().toString());
                        String uName = etContactNameShow.getText().toString();
                        int uNumber = Integer.parseInt(etContactNumber.getText().toString());
                        String sql= "UPDATE " + DATABASE_TABLE_NAME + " SET id = " + id +
                                ", contactName = "+ uName +
                                ", contactNumber = " + uNumber +
                                " WHERE id = "+ id;
                        SQLiteDatabase db;
                        db = ConnectDatabase.sqLiteDatabase(MainActivity.this, DATABASE_NAME);
                        db.execSQL(sql);
                        Intent i = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                });
                btDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int id = Integer.parseInt(tvID.getText().toString());
                        deleteData(id);
                        Intent i = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                });
            }
        }));
    }

    public void findViewByIdActivity(){
        btAdd = (Button) findViewById(R.id.btAdd);
        btSwitch = (Button) findViewById(R.id.btSwitch);
        mRcContact = (RecyclerView) findViewById(R.id.rc_Content);
    }
    public void findViewByIdAddDialog(){
        btAdSave = (Button) dialog.findViewById(R.id.btSave);
        btClose = (Button) dialog.findViewById(R.id.btClose);
        etContactName = (EditText) findViewById(R.id.etContactName);
        etContactNumber = (EditText) findViewById(R.id.etContactNumber);

    }
    public void findViewByIdShowDialog(){
        btSdSave = (Button) dialog.findViewById(R.id.btSave);
        btDelete = (Button) dialog.findViewById(R.id.btDelete);
        etContactNameShow = (EditText) findViewById(R.id.etContactName);
        etContactNumberShow = (EditText) findViewById(R.id.etContactNumber);
    }

    //DATABASE
    public List<Contact> getAllData(){
        List<Contact> contactList = new ArrayList<>();
        String query = "SELECT * FROM " + DATABASE_TABLE_NAME;

        SQLiteDatabase db;
        db = ConnectDatabase.sqLiteDatabase(MainActivity.this, DATABASE_NAME);
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false){
            Contact contact = new Contact(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
            contactList.add(contact);
            cursor.moveToNext();
        }

        return contactList;
    }
    public void addContact(Contact contact){
        SQLiteDatabase db;
        db = ConnectDatabase.sqLiteDatabase(MainActivity.this, DATABASE_NAME);
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACT_COLUMN_NAME, contact.getContactName());
        contentValues.put(CONTACT_COLUMN_NUMBER, contact.getContactNumber());

        db.insert(DATABASE_TABLE_NAME, null, contentValues);
        db.close();
    }

//    public void addMoreContact(){
//        List<Contact> contactList = new ArrayList<>();
//        contactList.add(new Contact("Nguyen Thanh Quy", 1665028014));
//
//        for (int i = 0; i < contactList.size(); i++){
//            addContact(contactList.get(i));
//        }
//    }
    public int getID(Contact contact){
        int id = contact.getId();
        String query = "SELECT id FROM " + DATABASE_TABLE_NAME + "WHERE id = "+ id;
        SQLiteDatabase db;
        db = ConnectDatabase.sqLiteDatabase(MainActivity.this, DATABASE_NAME);
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public void addMoreContact(String name, int number){
        List<Contact> contactList = new ArrayList<>();
        contactList.add(new Contact(name, number));
        addContact(contactList.get(0));
    }
    public void deleteData(int contactId){
        SQLiteDatabase db;
        db = ConnectDatabase.sqLiteDatabase(MainActivity.this, DATABASE_NAME);
        db.delete(DATABASE_TABLE_NAME, CONTACT_COLUMN_ID + " = ?",new String[]{ String.valueOf(contactId)} );
        db.close();
    }
}
