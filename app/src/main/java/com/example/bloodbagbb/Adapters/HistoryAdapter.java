package com.example.bloodbagbb.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodbagbb.Model.History;
import com.example.bloodbagbb.R;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.viewHolder> {

    private Context context;
    private ArrayList<History> historyArrayList;

    public HistoryAdapter(Context context, ArrayList<History> historyArrayList) {
        this.context = context;
        this.historyArrayList = historyArrayList;
    }


    @NonNull
    @Override
    public HistoryAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list, parent, false);
        return new HistoryAdapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.viewHolder holder, int position) {

        final History historyInfo = historyArrayList.get(position);

        String dateOfDonate = historyInfo.getDonationDate();
        String locationOfDonate = historyInfo.getDonationLacation();
        String descriptionOfDonate = historyInfo.getDonationDescription();

        holder.donationDate.setText(dateOfDonate);
        holder.donationLocation.setText(locationOfDonate);
        holder.donationDescription.setText(descriptionOfDonate);


    }

    @Override
    public int getItemCount() {
        return historyArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private TextView donationDate, donationLocation, donationDescription;
        private Button btnDelete, btnAccept;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            donationDate = itemView.findViewById(R.id.donationDate);
            donationLocation = itemView.findViewById(R.id.donorLocation);
            donationDescription = itemView.findViewById(R.id.description);
        }
    }
}
