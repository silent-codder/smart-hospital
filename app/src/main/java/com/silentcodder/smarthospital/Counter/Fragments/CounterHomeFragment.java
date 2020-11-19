package com.silentcodder.smarthospital.Counter.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CounterHomeFragment extends Fragment {

    RecyclerView mRecycleView;
    TextView mHistory;

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    SwipeRefreshLayout swipeRefreshLayout;

    List<CounterAppointment> counterAppointment;
    CounterAppointmentAdapter counterAppointmentAdapter;
    String UserId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_counter_home, container, false);

        swipeRefreshLayout=(SwipeRefreshLayout) view.findViewById(R.id.refreshPage);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                refreshData();
            }
        });

        mRecycleView = view.findViewById(R.id.recycleView);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        mHistory = view.findViewById(R.id.history);

        UserId = firebaseAuth.getCurrentUser().getUid();

        loadData();


        //Fragment Button
        mHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new CounterHistoryFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
        });

        return view;
    }

    private void refreshData() {
        loadData();
    }

    private void loadData() {
        //Recycle view
        counterAppointment = new ArrayList<>();
        counterAppointmentAdapter = new CounterAppointmentAdapter(counterAppointment);

        mRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycleView.setAdapter(counterAppointmentAdapter);

        CollectionReference appointmentRef = firebaseFirestore.collection("Appointments");

        Long date = System.currentTimeMillis();
        String dateString = (String) DateFormat
                .format("dd MMM yyyy",new Date(date)).toString();


        Query query = appointmentRef.whereGreaterThanOrEqualTo("AppointmentDate",dateString)
                .orderBy("AppointmentDate",Query.Direction.ASCENDING).limit(100);

        swipeRefreshLayout.setRefreshing(false);

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
    }
}