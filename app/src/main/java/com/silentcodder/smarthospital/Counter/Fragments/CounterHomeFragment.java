package com.silentcodder.smarthospital.Counter.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.silentcodder.smarthospital.Counter.Adapter.CounterAppointmentAdapter;
import com.silentcodder.smarthospital.Counter.Model.CounterAppointment;
import com.silentcodder.smarthospital.R;
import com.silentcodder.smarthospital.User.Adapter.AppointmentRecycleAdapter;
import com.silentcodder.smarthospital.User.Model.Appointment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CounterHomeFragment extends Fragment {

    RecyclerView mRecycleView;
    TextView mTomorrow,mYesterday;

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    List<CounterAppointment> counterAppointment;
    CounterAppointmentAdapter counterAppointmentAdapter;
    String UserId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_counter_home, container, false);
        mRecycleView = view.findViewById(R.id.recycleView);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        mTomorrow = view.findViewById(R.id.tomorrow);
        mYesterday = view.findViewById(R.id.yesterday);

        UserId = firebaseAuth.getCurrentUser().getUid();


        //Recycle view
        counterAppointment = new ArrayList<>();
        counterAppointmentAdapter = new CounterAppointmentAdapter(counterAppointment);

        mRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycleView.setAdapter(counterAppointmentAdapter);

        CollectionReference appointmentRef = firebaseFirestore.collection("Appointments");

        Long date = System.currentTimeMillis();
        String dateString = (String) DateFormat
                .format("dd MMM yyyy",new Date(date)).toString();

        Toast.makeText(getContext(), dateString, Toast.LENGTH_LONG).show();

        Query query = appointmentRef.whereEqualTo("AppointmentDate",dateString);

        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentChange doc : value.getDocumentChanges()){
                    if (doc.getType() == DocumentChange.Type.ADDED){
                        CounterAppointment mCounterAppointment = doc.getDocument().toObject(CounterAppointment.class);
                        counterAppointment.add(mCounterAppointment);
                        counterAppointmentAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        //fragments button

        mTomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new TomorrowFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
        });
        mYesterday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new YesterdayFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
        });

        return view;
    }
}