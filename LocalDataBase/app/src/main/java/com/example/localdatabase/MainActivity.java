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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText campoId, campoName, campoLastName, campoPhone, campoAge, campoSex, campoBirthday, campoHeight;
    Button insert, search, edit, delete, seeMore, seeMoreSorted, reset;
    Connect connect;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setClickListeners();

        connect = new Connect(this, Variables.NAME_DB, null, 1);

        setTitle(String.format("Local DB  |  LastId %d  |  Total %d", connect.getLastInsertedId(), connect.getRowCount(Variables.NAME_TABLE)));
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
            i.putExtra("sorted", false);
            startActivity(i);

            resetEditText();
        } else if (id == R.id.seeMoreSorted) {
            i = new Intent(MainActivity.this, ListActivity.class);
            i.putExtra("sorted", true);
            startActivity(i);

            resetEditText();
        } else if (id == R.id.reset) {
            resetDB();
        }
    }

    protected void insert() {
        if ( validInput() ) {
            return;
        }

        SQLiteDatabase db = connect.getWritableDatabase();
        ContentValues values = setContentValues();

        long id = db.insert(Variables.NAME_TABLE, Variables.CAMPO_ID, values);

        Toast.makeText(this, String.format("User #%s inserted successfully", id), Toast.LENGTH_SHORT).show();

        resetEditText();

        db.close();
    }

    private void search() {
        SQLiteDatabase db = connect.getReadableDatabase();
        StringBuilder whereClause = new StringBuilder();
        ArrayList<String> whereArgs = new ArrayList<>();

        if (!campoName.getText().toString().trim().isEmpty()) {
            appendWhereClause(whereClause, Variables.CAMPO_NAME + "=?");
            whereArgs.add(campoName.getText().toString().trim());
        }
        if (!campoLastName.getText().toString().trim().isEmpty()) {
            appendWhereClause(whereClause, Variables.CAMPO_LASTNAME + "=?");
            whereArgs.add(campoLastName.getText().toString().trim());
        }
        if (!campoPhone.getText().toString().trim().isEmpty()) {
            appendWhereClause(whereClause, Variables.CAMPO_PHONE + "=?");
            whereArgs.add(campoPhone.getText().toString().trim());
        }
        if (!campoAge.getText().toString().trim().isEmpty()) {
            appendWhereClause(whereClause, Variables.CAMPO_AGE + "=?");
            whereArgs.add(campoAge.getText().toString().trim());
        }
        if (!campoSex.getText().toString().trim().isEmpty()) {
            appendWhereClause(whereClause, Variables.CAMPO_SEX + "=?");
            whereArgs.add(campoSex.getText().toString().trim());
        }
        if (!campoBirthday.getText().toString().trim().isEmpty()) {
            appendWhereClause(whereClause, Variables.CAMPO_BIRTHDAY + "=?");
            whereArgs.add(campoBirthday.getText().toString().trim());
        }
        if (!campoHeight.getText().toString().trim().isEmpty()) {
            appendWhereClause(whereClause, Variables.CAMPO_HEIGHT + "=?");
            whereArgs.add(campoHeight.getText().toString().trim());
        }
        if (!campoId.getText().toString().trim().isEmpty()) {
            appendWhereClause(whereClause, Variables.CAMPO_ID + "=?");
            whereArgs.add(campoId.getText().toString().trim());
        }

        String[] columns = {
                Variables.CAMPO_ID,
                Variables.CAMPO_NAME,
                Variables.CAMPO_LASTNAME,
                Variables.CAMPO_PHONE,
                Variables.CAMPO_AGE,
                Variables.CAMPO_SEX,
                Variables.CAMPO_BIRTHDAY,
                Variables.CAMPO_HEIGHT
        };

        try {
            Cursor cursor = db.query(
                    Variables.NAME_TABLE,
                    columns,
                    whereClause.length() > 0 ? whereClause.toString() : null,
                    whereArgs.size() > 0 ? whereArgs.toArray(new String[0]) : null,
                    null,
                    null,
                    null
            );

            if (cursor.getCount() == 0) {
                Toast.makeText(this, "No users found"+whereClause.length(), Toast.LENGTH_SHORT).show();
                resetEditText();
            } else if (cursor.getCount() == 1) {
                cursor.moveToFirst();
                campoId.setText(cursor.getString(0));
                campoName.setText(cursor.getString(1));
                campoLastName.setText(cursor.getString(2));
                campoPhone.setText(cursor.getString(3));
                campoAge.setText(cursor.getString(4));
                campoSex.setText(cursor.getString(5));
                campoBirthday.setText(cursor.getString(6));
                campoHeight.setText(cursor.getString(7));
                Toast.makeText(this, "User found", Toast.LENGTH_SHORT).show();
            } else {
                // Multiple results found - send to ListActivity
                i = new Intent(MainActivity.this, ListActivity.class);
                i.putExtra("whereClause", whereClause.toString());
                i.putExtra("whereArgs", whereArgs.toArray(new String[0]));
                startActivity(i);
            }
            cursor.close();
        } catch (Exception e) {
            Toast.makeText(this, "Error during search: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            resetEditText();
            e.printStackTrace();
        }

        db.close();
    }

    private void appendWhereClause(StringBuilder whereClause, String condition) {
        if (whereClause.length() > 0) {
            whereClause.append(" OR ");
        }
        whereClause.append(condition);
    }

    private void edit() {
        if ( !validInput() ) {
            return;
        }

        SQLiteDatabase db = connect.getWritableDatabase();
        String[] params = {campoId.getText().toString()};
        ContentValues values = setContentValues();

        db.update(Variables.NAME_TABLE, values, Variables.CAMPO_ID+"=?", params);

        Toast.makeText(this, "User updated successfully", Toast.LENGTH_SHORT).show();

        resetEditText();

        db.close();
    }

    private void delete() {
        SQLiteDatabase db = connect.getWritableDatabase();
        String[] params = {campoId.getText().toString()};

        int n = db.delete(Variables.NAME_TABLE, Variables.CAMPO_ID+"=?", params);

        Toast.makeText(this, "User deleted successfully", Toast.LENGTH_SHORT).show();

        resetEditText();

        db.close();
    }

    private void resetDB() {
        SQLiteDatabase db = connect.getWritableDatabase();
        try {
            db.execSQL(Variables.DELETE_TABLE);
            db.execSQL(Variables.CREATE_TABLE);

            Toast.makeText(this, "Database reset successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error resetting database: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    private ContentValues setContentValues() {
        ContentValues values = new ContentValues();

        values.put(Variables.CAMPO_NAME, campoName.getText().toString());
        values.put(Variables.CAMPO_LASTNAME, campoLastName.getText().toString());
        values.put(Variables.CAMPO_PHONE, campoPhone.getText().toString());
        values.put(Variables.CAMPO_AGE, campoAge.getText().toString());
        values.put(Variables.CAMPO_SEX, campoSex.getText().toString());
        values.put(Variables.CAMPO_BIRTHDAY, campoBirthday.getText().toString());
        values.put(Variables.CAMPO_HEIGHT, campoHeight.getText().toString());

        return values;
    }

    private boolean validInput() {
        String name = campoName.getText().toString().trim();
        String lastname = campoLastName.getText().toString().trim();
        String phone = campoPhone.getText().toString().trim();
        String sex = campoSex.getText().toString().trim();
        String birthday = campoBirthday.getText().toString().trim();
        String ageTmp = campoAge.getText().toString().trim();
        Integer age = ageTmp.isEmpty() ? 0 : Integer.parseInt(ageTmp);
        String heightTmp = campoHeight.getText().toString().trim();
        Double height = heightTmp.isEmpty() ? 0.0 : Double.parseDouble(heightTmp);

        if ( name.isEmpty() || lastname.isEmpty() || phone.isEmpty() || sex.isEmpty() ||
                birthday.isEmpty() || age.equals(0) || height.equals(0.0)) {
            Toast.makeText(this, "Fill all inputs", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void resetEditText() {
        campoId.setText("");
        campoName.setText("");
        campoLastName.setText("");
        campoPhone.setText("");
        campoAge.setText("");
        campoSex.setText("");
        campoBirthday.setText("");
        campoHeight.setText("");
    }

    private void initializeViews() {
        // EDIT TEXT
        campoId = findViewById(R.id.campoId);
        campoName = findViewById(R.id.campoName);
        campoLastName = findViewById(R.id.campoLastName);
        campoPhone = findViewById(R.id.campoPhone);
        campoAge = findViewById(R.id.campoAge);
        campoSex = findViewById(R.id.campoSex);
        campoBirthday = findViewById(R.id.campoBirthday);
        campoHeight = findViewById(R.id.campoHeight);

        // BUTTON
        insert = findViewById(R.id.insert);
        search =  findViewById(R.id.search);
        edit = findViewById(R.id.edit);
        delete = findViewById(R.id.delete);
        seeMore = findViewById(R.id.seeMore);
        seeMoreSorted = findViewById(R.id.seeMoreSorted);
        reset = findViewById(R.id.reset);
    }

    private void setClickListeners() {
        insert.setOnClickListener(this);
        search.setOnClickListener(this);
        edit.setOnClickListener(this);
        delete.setOnClickListener(this);
        seeMore.setOnClickListener(this);
        seeMoreSorted.setOnClickListener(this);
        reset.setOnClickListener(this);
    }
}
