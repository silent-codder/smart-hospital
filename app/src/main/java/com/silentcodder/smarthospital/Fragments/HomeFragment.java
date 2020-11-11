package com.silentcodder.smarthospital.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.silentcodder.smarthospital.R;
public class HomeFragment extends Fragment {

    Button mBtnGetAppointment;
    TextView mCompleted;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mBtnGetAppointment = view.findViewById(R.id.btnGetAppointment);
        mCompleted = view.findViewById(R.id.completed);
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