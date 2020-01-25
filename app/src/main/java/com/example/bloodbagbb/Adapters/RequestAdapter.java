package com.example.bloodbagbb.Adapters;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodbagbb.Model.BloodRequest;
import com.example.bloodbagbb.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.viewHolder> {
    private Context context;
    private ArrayList<BloodRequest> userRequestList;
    private String checkingRequest;

    public RequestAdapter(Context context, ArrayList<BloodRequest> userRequestList, String checkingRequest) {
        this.context = context;
        this.userRequestList = userRequestList;
        this.checkingRequest = checkingRequest;
    }

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
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //Checking current user and set accepting and deleting request
        if(requestInfo.getUserId().equals(firebaseUser.getUid())){
            holder.acceptBTN.setVisibility(View.GONE);
            holder.declineBTN.setVisibility(View.VISIBLE);
        }
        else {
            holder.declineBTN.setVisibility(View.GONE);
            holder.acceptBTN.setVisibility(View.VISIBLE);
        }
        // Checking emergency or Normal and set number Visible and Invisible
        Log.d(TAG, "onBindViewHolder: " + checkingRequest);
        if(checkingRequest!= null){

            if(checkingRequest.equals("flagNM")){
            holder.contact.setVisibility(View.GONE);
            holder.viewContact.setVisibility(View.GONE);
        }
        else if(checkingRequest.equals("flagEM")){
            holder.viewContact.setVisibility(View.VISIBLE);
            holder.contact.setText(number);
        }

        else if(checkingRequest.equals("flagAll")){
                holder.viewContact.setVisibility(View.VISIBLE);
                holder.contact.setText(number);
            }
        }

        holder.date2.setText(enDate);
        holder.area.setText(home);
        holder.reason.setText(description);
        holder.sampleBlood.setText(sample);
    }

    @Override
    public int getItemCount() {
        return userRequestList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        private TextView date2, contact, area, reason, sampleBlood, viewContact;
        private Button declineBTN, acceptBTN;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            date2 = itemView.findViewById(R.id.date2);
            sampleBlood = itemView.findViewById(R.id.bloodSample);
            contact = itemView.findViewById(R.id.patiantNumber);
            area = itemView.findViewById(R.id.patiantArea);
            reason = itemView.findViewById(R.id.patiantReason);
            viewContact = itemView.findViewById(R.id.contactText);
            declineBTN = itemView.findViewById(R.id.declineBT);
            acceptBTN = itemView.findViewById(R.id.acceptBT);

        }
    }
}
