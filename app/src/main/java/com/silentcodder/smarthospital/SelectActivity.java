package com.silentcodder.smarthospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.silentcodder.smarthospital.Counter.CounterMainActivity;

public class SelectActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] User = {"Choose","User","Doctor","Counter","Medical"};
    String selectCategory;
    Button mBtnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        Spinner spin = (Spinner) findViewById(R.id.spinner);
        mBtnStart = findViewById(R.id.btnStart);
        spin.setOnItemSelectedListener(this);

        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (selectCategory == "Counter"){
                        startActivity(new Intent(SelectActivity.this, CounterMainActivity.class));
                    }
            }
        });

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,User);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        selectCategory = User[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}