package com.example.localdatabase;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView list;
    ArrayList<Users> usersData;
    Connect connect;
    UserAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        list = findViewById(R.id.list);

        show();

        adapter = new UserAdapter(this, usersData);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);

        setTitle("Users List");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Users user = usersData.get(position);
        Intent ii = new Intent(this, DetailsActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("user", user);
        ii.putExtras(b);
        startActivity(ii);
    }

    private void show() {
        connect = new Connect(this, Variables.NAME_DB, null, 1);
        SQLiteDatabase db = connect.getReadableDatabase();
        Users user;
        usersData = new ArrayList<>();

        Intent intent = getIntent();
        String whereClause = intent.getStringExtra("whereClause");
        String[] whereArgs = intent.getStringArrayExtra("whereArgs");
        Boolean sort = intent.getBooleanExtra("sorted", false);

        Cursor cursor;

        if (whereClause != null && whereArgs != null) {
            // Search results case
            String query = String.format("SELECT * FROM %s WHERE %s",
                    Variables.NAME_TABLE,
                    whereClause);

            if (sort) {
                query += String.format(" ORDER BY %s ASC", Variables.CAMPO_NAME);
            }

            cursor = db.rawQuery(query, whereArgs);
        } else {
            // Normal list case
            if (sort) {
                cursor = db.rawQuery(
                        String.format("SELECT * FROM %s ORDER BY %s ASC",
                                Variables.NAME_TABLE,
                                Variables.CAMPO_NAME),
                        null
                );
            } else {
                cursor = db.rawQuery(
                        String.format("SELECT * FROM %s", Variables.NAME_TABLE),
                        null
                );
            }
        }

        while (cursor.moveToNext()) {
            user = new Users();
            user.setId(cursor.getInt(0));
            user.setName(cursor.getString(1));
            user.setLastName(cursor.getString(2));
            user.setPhone(cursor.getString(3));
            user.setAge(Integer.parseInt(cursor.getString(4)));
            user.setSex(cursor.getString(5));
            user.setBirthday(cursor.getString(6));
            user.setHeight(Double.parseDouble(cursor.getString(7)));

            usersData.add(user);
        }

        if (usersData.isEmpty()) {
            Toast.makeText(this, "No users found", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
        db.close();
    }
}

class UserAdapter extends ArrayAdapter<Users> {
    private final Context context;
    private final ArrayList<Users> usersList;

    public UserAdapter(Context context, ArrayList<Users> usersList) {
        super(context, 0, usersList);
        this.context = context;
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_user, parent, false);
        }

        TextView textId = convertView.findViewById(R.id.textId);
        TextView textName = convertView.findViewById(R.id.textName);
        TextView textPhone = convertView.findViewById(R.id.textPhone);

        Users user = usersList.get(position);
        textId.setText(String.valueOf(user.getId()));
        textName.setText(user.getName() + " " + user.getLastName());
        textPhone.setText(user.getPhone());

        return convertView;
    }
}