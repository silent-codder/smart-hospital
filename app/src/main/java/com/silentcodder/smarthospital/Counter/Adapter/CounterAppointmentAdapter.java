package com.silentcodder.smarthospital.Counter.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.silentcodder.smarthospital.Counter.Model.CounterAppointment;
import com.silentcodder.smarthospital.R;


import java.util.List;

public class CounterAppointmentAdapter extends RecyclerView.Adapter<CounterAppointmentAdapter.ViewHolder> {
    public List<CounterAppointment> counterAppointments;

    public CounterAppointmentAdapter(List<CounterAppointment> counterAppointments) {
        this.counterAppointments = counterAppointments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String ChildName = counterAppointments.get(position).getChildName();
        String Problem = counterAppointments.get(position).getProblem();
        String Date = counterAppointments.get(position).getAppointmentDate();

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mChildName = itemView.findViewById(R.id.childName);
            mProblem = itemView.findViewById(R.id.problem);
            mDate = itemView.findViewById(R.id.date);
        }
    }
}
