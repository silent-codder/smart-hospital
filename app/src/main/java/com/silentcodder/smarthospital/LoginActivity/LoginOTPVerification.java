package com.silentcodder.smarthospital.LoginActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.silentcodder.smarthospital.MainActivity;
import com.silentcodder.smarthospital.R;
import com.silentcodder.smarthospital.RegisterActivity.RegisterActivity_2;
import com.silentcodder.smarthospital.RegisterActivity.RegisterOtpVerification;

import java.util.concurrent.TimeUnit;

public class LoginOTPVerification extends AppCompatActivity {

    EditText mOtp;
    String mPhoneNumber,OtpId;
    Button mBtnVerifyOTP;

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_otp_verification);

        mOtp = findViewById(R.id.getOtp);
        mBtnVerifyOTP = findViewById(R.id.btnVerifyOTP);

        mPhoneNumber = getIntent().getStringExtra("MobileNumber");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        pd = new ProgressDialog(this);
        pd.setMessage("Please wait..." +
                "\n To verify reCAPTCHA ");
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        GetOTP();

        mBtnVerifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOtp.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please enter OTP", Toast.LENGTH_SHORT).show();
                }else if (mOtp.getText().toString().length()!=6){
                    Toast.makeText(getApplicationContext(), "Incorrect OTP", Toast.LENGTH_SHORT).show();
                }else {
                    pd.dismiss();
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(OtpId,mOtp.getText().toString());
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Intent intent = new Intent(LoginOTPVerification.this, MainActivity.class);
                            intent.putExtra("Mobile",mPhoneNumber);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginOTPVerification.this, "Error...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void GetOTP() {

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(mPhoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeSent(String verificationId,
                                           PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        pd.dismiss();
                        OtpId = verificationId;

                    }

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        pd.dismiss();

                        signInWithPhoneAuthCredential(phoneAuthCredential);

                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Toast.makeText(LoginOTPVerification.this, "Server Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }
}