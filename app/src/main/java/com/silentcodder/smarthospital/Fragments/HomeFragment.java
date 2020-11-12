package com.silentcodder.smarthospital.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.silentcodder.smarthospital.Adapter.AppointmentRecycleAdapter;
import com.silentcodder.smarthospital.Model.Appointment;
import com.silentcodder.smarthospital.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    Button mBtnGetAppointment;
    TextView mCompleted;
    RecyclerView mRecycleView;

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    List<Appointment> appointment;
    AppointmentRecycleAdapter appointmentRecycleAdapter;
    String UserId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mBtnGetAppointment = view.findViewById(R.id.btnGetAppointment);
        mCompleted = view.findViewById(R.id.completed);
        mRecycleView = view.findViewById(R.id.recycleView);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        UserId = firebaseAuth.getCurrentUser().getUid();


        appointment = new ArrayList<>();
        appointmentRecycleAdapter = new AppointmentRecycleAdapter(appointment);

        mRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycleView.setAdapter(appointmentRecycleAdapter);

        CollectionReference appointmentRef = firebaseFirestore.collection("Appointments");

        Query query = appointmentRef.whereEqualTo("UserId",UserId);

        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentChange doc : value.getDocumentChanges()){
                    if (doc.getType() == DocumentChange.Type.ADDED){
                        Appointment mAppointment = doc.getDocument().toObject(Appointment.class);
                        appointment.add(mAppointment);
                        appointmentRecycleAdapter.notifyDataSetChanged();
                    }
                }
            }
        });


        mBtnGetAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment mFragment = new BookAppointmentFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, mFragment).commit();
            }
        });

        mCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment mFragment = new CompletedAppointmentFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, mFragment).commit();
            }
        });
        return view;
    }
}