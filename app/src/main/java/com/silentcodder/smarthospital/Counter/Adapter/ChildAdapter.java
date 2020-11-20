package com.silentcodder.smarthospital.Counter.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.silentcodder.smarthospital.Counter.CounterChildFile;
import com.silentcodder.smarthospital.Counter.Model.ChildDetails;
import com.silentcodder.smarthospital.R;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_details_view,parent,false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String ChildName = childDetails.get(position).getChildName();
        String Dob = childDetails.get(position).getChildDOB();
        String FileNumber = childDetails.get(position).getFileNumber();
        String ParentId = childDetails.get(position).getParentId();

        holder.mBtnShowFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CounterChildFile.class);
                intent.putExtra("ParentId",ParentId);
                context.startActivity(intent);
            }
        });

        holder.mChildName.setText("Child Name : " + ChildName);
        holder.mFileNumber.setText("File Number : " +FileNumber);
        holder.mDate.setText("DOB : " + Dob);

    }

    @Override
    public int getItemCount() {
        return childDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mChildName,mFileNumber,mDate;
        CardView mBtnShowFile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mChildName = itemView.findViewById(R.id.childName);
            mFileNumber = itemView.findViewById(R.id.fileNumber);
            mDate = itemView.findViewById(R.id.date);
            mBtnShowFile = itemView.findViewById(R.id.btnShowFile);

        }
    }
}
