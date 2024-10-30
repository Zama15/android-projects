package com.example.localdatabase;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {
    TextView txtId, txtName, txtLastName, txtPhone, txtAge, txtSex, txtBirthday, txtHeight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setUser();
    }

    private void setUser() {
        txtId = findViewById(R.id.textId);
        txtName = findViewById(R.id.textName);
        txtLastName = findViewById(R.id.textLastName);
        txtPhone = findViewById(R.id.textPhone);
        txtAge = findViewById(R.id.textAge);
        txtSex = findViewById(R.id.textSex);
        txtBirthday = findViewById(R.id.textBirthday);
        txtHeight = findViewById(R.id.textHeight);

        Bundle obj = getIntent().getExtras();
        Users user = null;
        if (obj != null) {
            user = (Users) obj.getSerializable("user");
            txtId.setText(String.valueOf(user.getId()));
            txtName.setText(user.getName());
            txtLastName.setText(user.getLastName());
            txtPhone.setText(user.getPhone());
            txtAge.setText(String.valueOf(user.getAge()));
            txtSex.setText(user.getSex());
            txtBirthday.setText(user.getBirthday());
            txtHeight.setText(String.valueOf(user.getHeight()));

            setTitle(String.format("User %s details", user.getId()));
        }
    }
}
