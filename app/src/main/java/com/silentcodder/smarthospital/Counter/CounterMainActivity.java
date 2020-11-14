package com.silentcodder.smarthospital.Counter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.silentcodder.smarthospital.Counter.Fragments.CounterHomeFragment;
import com.silentcodder.smarthospital.Counter.Fragments.CounterProfileFragment;
import com.silentcodder.smarthospital.R;
import com.silentcodder.smarthospital.User.Fragments.HistoryFragment;
import com.silentcodder.smarthospital.User.Fragments.HomeFragment;
import com.silentcodder.smarthospital.User.Fragments.ProfileFragment;

public class CounterMainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    Fragment selectFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_counter);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home :
                        selectFragment = new CounterHomeFragment();
                        break;
                    case R.id.nav_profile :
                        selectFragment = new CounterProfileFragment();
                        break;
                }

                if (selectFragment != null)
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectFragment).commit();
                }

                return true;
            }

        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CounterHomeFragment()).commit();

    }
}