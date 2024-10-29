package com.example.localdatabase;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
        Cursor cursor = db.rawQuery(String.format("SELECT * FROM %s", Variables.NAME_TABLE), null);
        while (cursor.moveToNext()) {
            user = new Users();
            user.setId(cursor.getInt(0));
            user.setName(cursor.getString(1));
            user.setPhone(cursor.getString(2));
            usersData.add(user);
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
        textName.setText(user.getName());
        textPhone.setText(user.getPhone());

        return convertView;
    }
}