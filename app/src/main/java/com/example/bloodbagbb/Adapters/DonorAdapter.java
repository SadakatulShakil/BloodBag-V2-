package com.example.bloodbagbb.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodbagbb.Model.User;
import com.example.bloodbagbb.R;

import java.util.ArrayList;

public class DonorAdapter extends RecyclerView.Adapter<DonorAdapter.viewHolder> {

    private Context context;
    private ArrayList<User> userArrayList;

    public DonorAdapter(Context context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public DonorAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.donor_list, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DonorAdapter.viewHolder holder, int position) {

        final User donorInfo = userArrayList.get(position);

        String bg = donorInfo.getBloodGroup();
        String name = donorInfo.getName();
        String contact = donorInfo.getContact();
        String area = donorInfo.getArea();

        holder.userBloodGroup.setText(bg);
        holder.userName.setText(name);
        holder.userContact.setText(contact);
        holder.userArea.setText(area);
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
