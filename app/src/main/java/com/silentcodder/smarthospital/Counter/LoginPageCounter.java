package com.silentcodder.smarthospital.Counter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.silentcodder.smarthospital.R;

public class LoginPageCounter extends AppCompatActivity {

    Button mBtnLogin;
    EditText mEmail,mPassword;
    ProgressDialog pd;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page_counter);

        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mBtnLogin = findViewById(R.id.btnLogin);
        firebaseAuth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(this);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = mEmail.getText().toString();
                String Password = mPassword.getText().toString();
                if (TextUtils.isEmpty(Email) && TextUtils.isEmpty(Password)){
                    Toast.makeText(LoginPageCounter.this, "Try again..", Toast.LENGTH_SHORT).show();
                }else {
                    Login(Email,Password);
                    pd.setMessage("Please wait...");
                    pd.setCanceledOnTouchOutside(false);
                    pd.show();
                }
            }
        });

    }

    private void Login(String email, String password) {


        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    pd.dismiss();
                    startActivity(new Intent(LoginPageCounter.this,CounterMainActivity.class));
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(LoginPageCounter.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}