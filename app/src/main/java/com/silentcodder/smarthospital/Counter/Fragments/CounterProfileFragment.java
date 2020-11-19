package com.silentcodder.smarthospital.Counter.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.silentcodder.smarthospital.Counter.UpdateProfileActivity;
import com.silentcodder.smarthospital.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class CounterProfileFragment extends Fragment {

    Button BtnUpdateProfile;

    CircleImageView mProfileImg;
    TextView mProfileName,mProfileEmail,mProfileAddress;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String UserId;
    ProgressDialog pd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_counter_profile, container, false);

        final SwipeRefreshLayout swipeRefreshLayout=(SwipeRefreshLayout) view.findViewById(R.id.profilePage);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                refresh();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        BtnUpdateProfile = view.findViewById(R.id.updateProfile);
        mProfileImg = view.findViewById(R.id.profileImg);
        mProfileName = view.findViewById(R.id.profileName);
        mProfileEmail = view.findViewById(R.id.profileEmail);
        mProfileAddress = view.findViewById(R.id.profileAddress);
        pd = new ProgressDialog(getContext());

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        UserId = firebaseAuth.getCurrentUser().getUid();

        pd.setMessage("Loading..");
        pd.show();

        refresh();

        BtnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), UpdateProfileActivity.class));
            }
        });

        return view;
    }

    private void refresh() {

        firebaseFirestore.collection("Staff-Details").document(UserId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        String ProfileUrl = task.getResult().getString("ProfileImgUrl");
                        String Name = task.getResult().getString("name");
                        String Email = task.getResult().getString("email");
                        String Address = task.getResult().getString("address");

                        SetText(ProfileUrl,Name,Email,Address);
                    }
                });
    }

    private void SetText(String profileUrl, String name, String email, String address) {

        Picasso.get().load(profileUrl).into(mProfileImg);
        mProfileName.setText(name);
        mProfileEmail.setText(email);
        mProfileAddress.setText(address);
        pd.dismiss();

    }
}