package com.example.bloodbagbb.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodbagbb.Model.BloodRequest;
import com.example.bloodbagbb.R;

import java.util.ArrayList;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.viewHolder> {
    private Context context;
    private ArrayList<BloodRequest> userRequestList;

    public RequestAdapter(Context context, ArrayList<BloodRequest> userRequestList) {
        this.context = context;
        this.userRequestList = userRequestList;
    }

    @NonNull
    @Override
    public RequestAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_list, parent, false);
        return new RequestAdapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestAdapter.viewHolder holder, int position) {
        final BloodRequest requestInfo = userRequestList.get(position);

        String enDate = requestInfo.getEndDate();
        String number = requestInfo.getContact();
        String home = requestInfo.getArea();
        String description = requestInfo.getReason();
        String sample = requestInfo.getBloodGroup();

        holder.date2.setText(enDate);
        holder.contact.setText(number);
        holder.area.setText(home);
        holder.reason.setText(description);
        holder.sampleBlood.setText(sample);
    }

    @Override
    public int getItemCount() {
        return userRequestList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        private TextView date1, date2, contact, area, reason, sampleBlood;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            date2 = itemView.findViewById(R.id.date2);
            sampleBlood = itemView.findViewById(R.id.bloodSample);
            contact = itemView.findViewById(R.id.patiantNumber);
            area = itemView.findViewById(R.id.patiantArea);
            reason = itemView.findViewById(R.id.patiantReason);
        }
    }
}
