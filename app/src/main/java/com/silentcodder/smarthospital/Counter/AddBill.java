package com.silentcodder.smarthospital.Counter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.silentcodder.smarthospital.R;

import java.util.HashMap;

public class AddBill extends AppCompatActivity {

    EditText mDescription_1,mAmount_1,mDescription_2,mAmount_2,mDescription_3,mAmount_3,mDescription_4,mAmount_4;
    Button mBtnSubmitBill;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String UserId;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);
        findIds();
        pd = new ProgressDialog(this);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        UserId = getIntent().getStringExtra("UserId");

        mBtnSubmitBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Adding bill..");
                pd.setCanceledOnTouchOutside(false);
                pd.show();

                String Description_1 = mDescription_1.getText().toString();
                String Description_2 = mDescription_2.getText().toString();
                String Description_3 = mDescription_3.getText().toString();
                String Description_4 = mDescription_4.getText().toString();

                String Amount_1 = mAmount_1.getText().toString();
                String Amount_2 = mAmount_2.getText().toString();
                String Amount_3 = mAmount_3.getText().toString();
                String Amount_4 = mAmount_4.getText().toString();

                HashMap<String , Object> map = new HashMap<>();
                map.put("Description1",Description_1);
                map.put("Description2",Description_2);
                map.put("Description3",Description_3);
                map.put("Description4",Description_4);
                map.put("Amount1",Amount_1);
                map.put("Amount2",Amount_2);
                map.put("Amount3",Amount_3);
                map.put("Amount4",Amount_4);
                map.put("UserId",UserId);
                map.put("TimeStamp",System.currentTimeMillis());


                firebaseFirestore.collection("Bill-Details").add(map)
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if (task.isSuccessful()){
                                    pd.dismiss();
                                    Toast.makeText(AddBill.this, "Add bill successfully..", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(AddBill.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void findIds() {
        mDescription_1 = findViewById(R.id.description_1);
        mDescription_2 = findViewById(R.id.description_2);
        mDescription_3 = findViewById(R.id.description_3);
        mDescription_4 = findViewById(R.id.description_4);

        mAmount_1 = findViewById(R.id.amount_1);
        mAmount_2 = findViewById(R.id.amount_2);
        mAmount_3 = findViewById(R.id.amount_3);
        mAmount_4 = findViewById(R.id.amount_4);

        mBtnSubmitBill = findViewById(R.id.btnSubmitBill);
    }
}