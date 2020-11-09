package com.silentcodder.smarthospital.RegisterActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hbb20.CountryCodePicker;
import com.silentcodder.smarthospital.MainActivity;
import com.silentcodder.smarthospital.R;

import java.util.HashMap;

public class RegisterActivity_1 extends AppCompatActivity {

    EditText mMobileNumber;
    Button mBtnNext;
    CountryCodePicker mCpp;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_1);

        mMobileNumber = findViewById(R.id.mobileNumber);
        mBtnNext = findViewById(R.id.btnNext);
        mCpp = findViewById(R.id.cpp);
        firebaseFirestore = FirebaseFirestore.getInstance();
        mCpp.registerCarrierNumberEditText(mMobileNumber);

        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RegisterActivity_1.this,RegisterOtpVerification.class);
                intent.putExtra("Mobile",mCpp.getFullNumberWithPlus().replace(" ",""));
                startActivity(intent);

                HashMap<String,Object> map = new HashMap<>();
                map.put("mobile",mCpp.getFullNumberWithPlus());

                firebaseFirestore.collection("MobileNumber").add(map);

            }
        });

    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//       FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();
//
//       if (user != null){
//           startActivity(new Intent(RegisterActivity_1.this, MainActivity.class));
//           finish();
//       }
//    }
}