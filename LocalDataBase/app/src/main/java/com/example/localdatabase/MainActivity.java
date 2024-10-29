package com.example.localdatabase;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText campoId, campoName, campoPhone;
    Button insert, search, edit, delete, seeMore;
    Connect connect;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setClickListeners();

        connect = new Connect(this, Variables.NAME_DB, null, 1);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.insert) {
            insert();
        } else if (id == R.id.search) {
            search();
        } else if (id == R.id.edit) {
            edit();
        } else if (id == R.id.delete) {
            delete();
        } else if (id == R.id.seeMore) {
            i = new Intent(MainActivity.this, ListActivity.class);
            startActivity(i);
        }
    }

    protected void insert() {
        SQLiteDatabase db = connect.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Variables.CAMPO_NAME, campoName.getText().toString().trim());
        values.put(Variables.CAMPO_PHONE, campoPhone.getText().toString().trim());
        long id = db.insert(Variables.NAME_TABLE, Variables.CAMPO_ID, values);

        Toast.makeText(this, "id:"+id, Toast.LENGTH_SHORT).show();

        campoName.setText("");
        campoPhone.setText("");
        db.close();
    }

    private void search() {
        SQLiteDatabase db = connect.getReadableDatabase();
        String[] params = {campoId.getText().toString()};
        String[] campos = {Variables.CAMPO_NAME, Variables.CAMPO_PHONE};

        try {
            Cursor cursor = db.query(Variables.NAME_TABLE, campos, Variables.CAMPO_ID+"=?", params, null, null, null);
            cursor.moveToFirst();
            campoName.setText(cursor.getString(0));
            campoPhone.setText(cursor.getString(1));
            cursor.close();
        } catch (Exception e) {
            Toast.makeText(this, "The user not found", Toast.LENGTH_SHORT).show();
            campoName.setText("");
            campoPhone.setText("");
            e.printStackTrace();
        }

        db.close();
    }

    private void edit() {
        SQLiteDatabase db = connect.getWritableDatabase();
        String[] params = {campoId.getText().toString()};
        ContentValues values = new ContentValues();

        values.put(Variables.CAMPO_NAME, campoName.getText().toString());
        values.put(Variables.CAMPO_PHONE, campoPhone.getText().toString());

        db.update(Variables.NAME_TABLE, values, Variables.CAMPO_ID+"=?", params);

        Toast.makeText(this, "User updated successfully", Toast.LENGTH_SHORT).show();

        db.close();
    }

    private void delete() {
        SQLiteDatabase db = connect.getWritableDatabase();
        String[] params = {campoId.getText().toString()};

        int n = db.delete(Variables.NAME_TABLE, Variables.CAMPO_ID+"=?", params);

        Toast.makeText(this, "User deleted successfully"+n, Toast.LENGTH_SHORT).show();

        campoId.setText("");
        campoName.setText("");
        campoPhone.setText("");

        db.close();
    }

    private void initializeViews() {
        // EDIT TEXT
        campoId = findViewById(R.id.campoId);
        campoName = findViewById(R.id.campoName);
        campoPhone = findViewById(R.id.campoPhone);

        // BUTTON
        insert = findViewById(R.id.insert);
        search =  findViewById(R.id.search);
        edit = findViewById(R.id.edit);
        delete = findViewById(R.id.delete);
        seeMore = findViewById(R.id.seeMore);
    }

    private void setClickListeners() {
        insert.setOnClickListener(this);
        search.setOnClickListener(this);
        edit.setOnClickListener(this);
        delete.setOnClickListener(this);
        seeMore.setOnClickListener(this);
    }
}
