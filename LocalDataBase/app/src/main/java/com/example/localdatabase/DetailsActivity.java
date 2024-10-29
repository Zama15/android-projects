package com.example.localdatabase;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {
    TextView txtId, txtName, txtPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setUser();
    }

    private void setUser() {
        txtId = findViewById(R.id.textId);
        txtName = findViewById(R.id.textName);
        txtPhone = findViewById(R.id.textPhone);

        Bundle obj = getIntent().getExtras();
        Users user = null;
        if (obj != null) {
            user = (Users) obj.getSerializable("user");
            txtId.setText(user.getId().toString());
            txtName.setText(user.getName());
            txtPhone.setText(user.getPhone());

            setTitle(String.format("User %s details", user.getId()));
        }
    }
}
