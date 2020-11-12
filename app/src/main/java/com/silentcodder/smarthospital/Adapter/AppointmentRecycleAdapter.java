package com.silentcodder.smarthospital.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.silentcodder.smarthospital.Model.Appointment;
import com.silentcodder.smarthospital.R;

import java.util.List;

public class AppointmentRecycleAdapter extends RecyclerView.Adapter<AppointmentRecycleAdapter.ViewHolder> {

    public List<Appointment> appointments;

    public AppointmentRecycleAdapter(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String ChildName = appointments.get(position).getChildName();
        String Problem = appointments.get(position).getProblem();
        String Date = appointments.get(position).getAppointmentDate();

        holder.mChildName.setText(ChildName);
        holder.mProblem.setText(Problem);
        holder.mDate.setText(Date);
    }


    @Override
    public int getItemCount() {
        return appointments.size();
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
