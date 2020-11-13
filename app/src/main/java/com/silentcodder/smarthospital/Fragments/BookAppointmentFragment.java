package com.silentcodder.smarthospital.Fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.silentcodder.smarthospital.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;


public class BookAppointmentFragment extends Fragment {

    Button mBtnCancel,mBookAppointment;
    EditText mChildName,mProblem,mDate;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String UserId;
    ProgressDialog pd;
    Calendar calendar;
   // int day,month,year;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_appointment, container, false);

        mChildName = view.findViewById(R.id.apmChildName);
        mProblem = view.findViewById(R.id.apmProblem);
        mDate = view.findViewById(R.id.apmDate);
        mBookAppointment = view.findViewById(R.id.apmBtnBookAppointment);
        mBtnCancel = view.findViewById(R.id.apmBtnCancel);
        calendar = Calendar.getInstance();
        pd = new ProgressDialog(getContext());

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        UserId = firebaseAuth.getCurrentUser().getUid();

        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year

                try {
                    c.setTime(new SimpleDateFormat("MMM").parse("Aug"));
                    int mMonth = c.get(Calendar.MONTH) + 1;
                    int mDay = c.get(Calendar.DAY_OF_MONTH); // current day


                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    SimpleDateFormat format = new SimpleDateFormat(" MMM yyyy");
                                    String date=format.format(calendar.getTime());
                                    mDate.setText(dayOfMonth + "" +date);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        firebaseFirestore.collection("Child-Details").document(UserId)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                String ChildName = task.getResult().getString("Child-Name");
                mChildName.setText(ChildName);
            }
        });

        mBookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pd.setMessage("Please wait a moments...");
                pd.setCanceledOnTouchOutside(false);
                pd.show();

                String ApmChildName = mChildName.getText().toString();
                String ApmProblem = mProblem.getText().toString();
                String ApmDate = mDate.getText().toString();

                if (TextUtils.isEmpty(ApmChildName)){
                    Toast.makeText(getContext(), "Enter child name", Toast.LENGTH_SHORT).show();
                    mChildName.requestFocus();
                }else if (TextUtils.isEmpty(ApmDate)){
                    Toast.makeText(getContext(), "Select Appointment date", Toast.LENGTH_SHORT).show();
                }else {
                    HashMap<String,Object> map = new HashMap<>();
                    map.put("ChildName",ApmChildName);
                    map.put("Problem",ApmProblem);
                    map.put("AppointmentDate",ApmDate);
                    map.put("TimeStamp",System.currentTimeMillis());
                    map.put("UserId",UserId);

                    firebaseFirestore.collection("Appointments").add(map)
                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if (task.isSuccessful()){
                                        pd.dismiss();
                                        Toast.makeText(getContext(), "Get Appointment", Toast.LENGTH_SHORT).show();
                                        Fragment fragment = new HomeFragment();
                                        getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(getContext(), "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment mFragment = new HomeFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, mFragment).commit();            }
        });

        return view;
    }
}