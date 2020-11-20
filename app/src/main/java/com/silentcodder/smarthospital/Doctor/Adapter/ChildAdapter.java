package com.silentcodder.smarthospital.Doctor.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.silentcodder.smarthospital.Counter.Adapter.CounterAppointmentAdapter;
import com.silentcodder.smarthospital.Counter.CounterChildFile;
import com.silentcodder.smarthospital.Counter.Model.CounterAppointment;
import com.silentcodder.smarthospital.Doctor.Model.ChildDetails;
import com.silentcodder.smarthospital.R;
import com.silentcodder.smarthospital.User.ChildFile;

import java.util.List;

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ViewHolder> {

    Context context;

    public List<ChildDetails> childDetails;

    public ChildAdapter(List<ChildDetails> childDetails) {
        this.childDetails = childDetails;
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

        String ChildName = childDetails.get(position).getChildName();
        String Dob = childDetails.get(position).getChildDOB();
        String Gender = childDetails.get(position).getChildGender();
        String ParentId = childDetails.get(position).getParentId();

        holder.mBtnShowFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CounterChildFile.class);
                intent.putExtra("ParentId",ParentId);
                context.startActivity(intent);
            }
        });

        holder.mChildName.setText(ChildName);
        holder.mProblem.setText(Gender);
        holder.mDate.setText(Dob);

    }

    @Override
    public int getItemCount() {
        return childDetails.size();
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
