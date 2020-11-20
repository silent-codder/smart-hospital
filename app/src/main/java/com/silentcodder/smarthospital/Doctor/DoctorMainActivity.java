package com.silentcodder.smarthospital.Doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.silentcodder.smarthospital.Counter.Fragments.CounterHomeFragment;
import com.silentcodder.smarthospital.Counter.Fragments.CounterProfileFragment;
import com.silentcodder.smarthospital.Doctor.Fragments.DoctorHomeFragment;
import com.silentcodder.smarthospital.Doctor.Fragments.DoctorProfileFragment;
import com.silentcodder.smarthospital.Doctor.Fragments.DoctorSearchFragment;
import com.silentcodder.smarthospital.R;

public class DoctorMainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    Fragment selectFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home :
                        selectFragment = new DoctorHomeFragment();
                        break;
                    case R.id.nav_search :
                        selectFragment = new DoctorSearchFragment();
                        break;
                    case R.id.nav_profile :
                        selectFragment = new DoctorProfileFragment();
                        break;
                }

                if (selectFragment != null)
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectFragment).commit();
                }

                return true;
            }

        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DoctorHomeFragment()).commit();

    }
}