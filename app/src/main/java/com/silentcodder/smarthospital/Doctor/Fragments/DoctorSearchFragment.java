package com.silentcodder.smarthospital.Doctor.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.silentcodder.smarthospital.Doctor.Adapter.ChildAdapter;
import com.silentcodder.smarthospital.Doctor.Model.ChildDetails;
import com.silentcodder.smarthospital.R;

import java.util.ArrayList;
import java.util.List;


public class DoctorSearchFragment extends Fragment {

    RecyclerView mRecycleView;
    ImageView mBtnSearch;
    EditText mSearch;

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    List<ChildDetails> childDetail;
    ChildAdapter childAdapter;
    String UserId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         View view = inflater.inflate(R.layout.fragment_counter_search, container, false);

        mRecycleView = view.findViewById(R.id.recycleView);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        mBtnSearch = view.findViewById(R.id.btnSearch);
        mSearch = view.findViewById(R.id.search);

        UserId = firebaseAuth.getCurrentUser().getUid();

        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Search_txt = mSearch.getText().toString();
                loadData(Search_txt);
            }
        });

         return view;
    }

    private void loadData(String search_txt) {
        //RecycleView
        childDetail = new ArrayList<>();
        childAdapter = new ChildAdapter(childDetail);

        mRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycleView.setAdapter(childAdapter);

        CollectionReference appointmentRef = firebaseFirestore.collection("Child-Details");




        Query query = appointmentRef.orderBy("FileNumber").startAt(search_txt).endAt(search_txt + "\uf8ff");


        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentChange doc : value.getDocumentChanges()){
                    if (doc.getType() == DocumentChange.Type.ADDED){
                        ChildDetails mChildDetails = doc.getDocument().toObject(ChildDetails.class);
                        childDetail.add(mChildDetails);
                        childAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}