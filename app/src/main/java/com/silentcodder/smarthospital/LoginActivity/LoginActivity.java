package com.silentcodder.smarthospital.LoginActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.hbb20.CountryCodePicker;
import com.silentcodder.smarthospital.MainActivity;
import com.silentcodder.smarthospital.R;
import com.silentcodder.smarthospital.RegisterActivity.RegisterActivity_1;

public class LoginActivity extends AppCompatActivity {

    TextView mNewAccount;
    EditText mMobileNumber;
    Button mBtnSubmit;
    CountryCodePicker mCpp;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mNewAccount = findViewById(R.id.newAccount);
        mMobileNumber = findViewById(R.id.mobileNumber);
        mBtnSubmit = findViewById(R.id.btnSubmit);
        mCpp = findViewById(R.id.cpp);
        firebaseFirestore = FirebaseFirestore.getInstance();

        mCpp.registerCarrierNumberEditText(mMobileNumber);

        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Query query = firebaseFirestore.collection("MobileNumber").whereEqualTo("mobile", mCpp.getFullNumberWithPlus());
                    query.addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                            if (!value.isEmpty()){
                                Intent intent = new Intent(LoginActivity.this, LoginOTPVerification.class);
                                intent.putExtra("MobileNumber",mCpp.getFullNumberWithPlus().replace(" ", ""));
                                startActivity(intent);
                            } else {
                                Toast toast =  Toast.makeText(LoginActivity.this, "Mobile number not found", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER,0,0);
                                toast.show();
                                mMobileNumber.setHint("Register Mobile Number");

                            }

                        }
                    });
            }
        });

        mNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity_1.class));
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();

        if (user != null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }
}