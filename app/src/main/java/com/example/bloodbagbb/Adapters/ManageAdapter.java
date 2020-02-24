package com.example.bloodbagbb.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodbagbb.Activity.CommunicationActivity;
import com.example.bloodbagbb.Activity.DonorMaintainActivity;
import com.example.bloodbagbb.Model.User;
import com.example.bloodbagbb.R;

import java.util.ArrayList;

public class ManageAdapter extends RecyclerView.Adapter<ManageAdapter.viewHolder>{
    private Context context;
    private ArrayList<User> userArrayList;


    public ManageAdapter(Context context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;

    }

    @NonNull
    @Override
    public ManageAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.donor_list, parent, false);
        return new ManageAdapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ManageAdapter.viewHolder holder, int position) {

        final User donorInfo = userArrayList.get(position);

        String bg = donorInfo.getBloodGroup();
        final String name = donorInfo.getName();
        final String contact = donorInfo.getContact();
        final String area = donorInfo.getArea();

        holder.userBloodGroup.setText(bg);
        holder.userName.setText(name);
        holder.userContact.setText(contact);
        holder.userArea.setText(area);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DonorMaintainActivity.class);
                intent.putExtra("donorInfo",donorInfo);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private TextView userBloodGroup, userName, userContact, userArea;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            userBloodGroup = itemView.findViewById(R.id.bloodSample);
            userName = itemView.findViewById(R.id.donorName);
            userContact = itemView.findViewById(R.id.donorContact);
            userArea = itemView.findViewById(R.id.donorArea);
        }
    }
}
