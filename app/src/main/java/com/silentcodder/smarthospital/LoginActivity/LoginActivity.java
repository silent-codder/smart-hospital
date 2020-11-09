package com.silentcodder.smarthospital.LoginActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.silentcodder.smarthospital.R;
import com.silentcodder.smarthospital.RegisterActivity.RegisterActivity_1;

public class LoginActivity extends AppCompatActivity {

    TextView mNewAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mNewAccount = findViewById(R.id.newAccount);

        mNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(LoginActivity.this, RegisterActivity_1.class));
            }
        });
    }
}