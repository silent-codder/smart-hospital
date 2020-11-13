package com.silentcodder.smarthospital.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.silentcodder.smarthospital.R;

public class ChildFile extends AppCompatActivity {

    TextView mChildName,mParentName,mFileNumber,mBirthDay,mWeight;
    String FirstName,LastName,UserId;

    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_file);

        mChildName = findViewById(R.id.childName);
        mParentName = findViewById(R.id.parentName);
        mFileNumber = findViewById(R.id.fileNumber);
        mBirthDay = findViewById(R.id.childDOB);
        mWeight = findViewById(R.id.childWeight);
        firebaseFirestore = FirebaseFirestore.getInstance();

        FirstName = getIntent().getStringExtra("firstName");
        LastName = getIntent().getStringExtra("lastName");
        UserId = getIntent().getStringExtra("UserId");

        mParentName.setText(FirstName + " " + LastName);

        firebaseFirestore.collection("Child-Details").document(UserId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String name = documentSnapshot.getString("Child-Name");
                        String weight = documentSnapshot.getString("Child-Weight");
                        String DOB = documentSnapshot.getString("Child-DOB");
                        String fileNumber = documentSnapshot.getString("File-Number");

                        mChildName.setText(name);
                        mBirthDay.setText(DOB);
                        mWeight.setText(weight + "Kg");
                        mFileNumber.setText(fileNumber);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ChildFile.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}