package com.silentcodder.smarthospital.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.silentcodder.smarthospital.R;

public class CompletedAppointmentFragment extends Fragment {
    TextView mBtnAppointment;
    Button mBtnGetAppointment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_completed_appointment, container, false);

        mBtnAppointment = view.findViewById(R.id.appointment);
        mBtnGetAppointment = view.findViewById(R.id.btnGetAppointment);


        mBtnAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment mFragment = new HomeFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, mFragment).commit();
            }
        });

        mBtnGetAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment mFragment = new BookAppointmentFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, mFragment).commit();
            }
        });

        return view;
    }
}