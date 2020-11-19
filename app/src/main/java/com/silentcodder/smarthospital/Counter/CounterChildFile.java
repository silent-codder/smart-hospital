package com.silentcodder.smarthospital.Counter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.silentcodder.smarthospital.R;
import com.silentcodder.smarthospital.User.ChildFile;

public class CounterChildFile extends AppCompatActivity {

    TextView mChildName,mParentName,mFileNumber,mBirthDay,mWeight;
    String FirstName,LastName,UserId;
    Button mBtnAddBill;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_child_file);

        mChildName = findViewById(R.id.childName);
        mParentName = findViewById(R.id.parentName);
        mFileNumber = findViewById(R.id.fileNumber);
        mBirthDay = findViewById(R.id.childDOB);
        mWeight = findViewById(R.id.childWeight);
        mBtnAddBill = findViewById(R.id.btnAddBill);
        firebaseFirestore = FirebaseFirestore.getInstance();

        FirstName = getIntent().getStringExtra("firstName");
        LastName = getIntent().getStringExtra("lastName");
        UserId = getIntent().getStringExtra("UserId");


        firebaseFirestore.collection("Child-Details").document(UserId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String name = documentSnapshot.getString("Child-Name");
                        String weight = documentSnapshot.getString("Child-Weight");
                        String DOB = documentSnapshot.getString("Child-DOB");
                        String fileNumber = documentSnapshot.getString("File-Number");
                        String ParentId = documentSnapshot.getString("Parent-Id");

                        firebaseFirestore.collection("Parent-Details").document(ParentId).get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        String FirstName = task.getResult().getString("First-Name");
                                        String LastName = task.getResult().getString("Last-Name");

                                        mParentName.setText(FirstName + " " + LastName);
                                    }
                                });

                        mChildName.setText(name);
                        mBirthDay.setText(DOB);
                        mWeight.setText(weight + "Kg");
                        mFileNumber.setText(fileNumber);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CounterChildFile.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        // Buttons

        mBtnAddBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CounterChildFile.this,AddBill.class);
                intent.putExtra("UserId",UserId);
                startActivity(intent);
            }
        });

    }
}