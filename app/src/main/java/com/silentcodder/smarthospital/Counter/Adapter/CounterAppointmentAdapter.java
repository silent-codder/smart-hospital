package com.silentcodder.smarthospital.Counter.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.silentcodder.smarthospital.Counter.CounterChildFile;
import com.silentcodder.smarthospital.Counter.Model.CounterAppointment;
import com.silentcodder.smarthospital.R;
import com.silentcodder.smarthospital.User.ChildFile;


import java.util.List;

public class CounterAppointmentAdapter extends RecyclerView.Adapter<CounterAppointmentAdapter.ViewHolder> {

    Context context;

    public List<CounterAppointment> counterAppointments;

    public CounterAppointmentAdapter(List<CounterAppointment> counterAppointments) {
        this.counterAppointments = counterAppointments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.counter_appointment_view,parent,false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String ChildName = counterAppointments.get(position).getChildName();
        String Problem = counterAppointments.get(position).getProblem();
        String Date = counterAppointments.get(position).getAppointmentDate();
        String UserId = counterAppointments.get(position).getUserId();

        holder.mBtnShowFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CounterChildFile.class);
                intent.putExtra("UserId",UserId);
                context.startActivity(intent);
            }
        });

        holder.mChildName.setText(ChildName);
        holder.mProblem.setText(Problem);
        holder.mDate.setText(Date);

    }

    @Override
    public int getItemCount() {

        return counterAppointments.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mChildName,mProblem,mDate;
        CardView mBtnShowFile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mChildName = itemView.findViewById(R.id.childName);
            mProblem = itemView.findViewById(R.id.problem);
            mDate = itemView.findViewById(R.id.date);
            mBtnShowFile = itemView.findViewById(R.id.btnShowFile);
        }
    }
}
