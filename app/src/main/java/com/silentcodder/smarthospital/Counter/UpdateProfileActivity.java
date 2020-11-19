package com.silentcodder.smarthospital.Counter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.silentcodder.smarthospital.Counter.Fragments.CounterProfileFragment;
import com.silentcodder.smarthospital.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

public class UpdateProfileActivity extends AppCompatActivity {

    ImageView mCounterImg;
    TextView mEditProfile;
    EditText mName,mEmail,mAddress;
    Button mBtnSubmit;
    Uri profileImgUri;

    String UserId;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    StorageReference storageReference;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        mCounterImg = findViewById(R.id.counterImg);
        mEditProfile = findViewById(R.id.editProfile);
        mName = findViewById(R.id.name);
        mEmail = findViewById(R.id.email);
        mAddress = findViewById(R.id.address);
        mBtnSubmit = findViewById(R.id.btnSubmit);
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        pd = new ProgressDialog(this);

        UserId = firebaseAuth.getCurrentUser().getUid();

        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Uploading...");
                pd.setCanceledOnTouchOutside(false);
                pd.show();
                String Name = mName.getText().toString();
                String Email = mEmail.getText().toString();
                String Address = mAddress.getText().toString();

                if (TextUtils.isEmpty(Name) && TextUtils.isEmpty(Email) && TextUtils.isEmpty(Address))
                {
                    Toast.makeText(UpdateProfileActivity.this, "Please fill all information", Toast.LENGTH_SHORT).show();
                }else {
                    HashMap<String,Object> map = new HashMap<>();
                    map.put("name",Name);
                    map.put("email",Email);
                    map.put("address",Address);

                    firebaseFirestore.collection("Staff-Details").document(UserId).update(map)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        pd.dismiss();
                                        finish();
                                        Toast.makeText(UpdateProfileActivity.this, "Upload", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(UpdateProfileActivity.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


        mEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImg();
            }
        });

    }

    private void SelectImg() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setOutputCompressQuality(10)
                .setAspectRatio(1,1)
                .start(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                profileImgUri = result.getUri();
                mCounterImg.setImageURI(profileImgUri);
                AddImg();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(UpdateProfileActivity.this, "Error : " + error, Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void AddImg() {
        pd.setMessage("Updating...");
        pd.show();
        StorageReference profileImgPath = storageReference.child("Profile").child(UserId + ".jpg");

        profileImgPath.putFile(profileImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                profileImgPath.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        String ProfileUri = task.getResult().toString();
                        HashMap<String,Object> map = new HashMap<>();
                        map.put("ProfileImgUrl" , ProfileUri);

                        firebaseFirestore.collection("Staff-Details").document(UserId).set(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        pd.dismiss();
                                        Toast.makeText(UpdateProfileActivity.this, "Profile Update...", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UpdateProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}