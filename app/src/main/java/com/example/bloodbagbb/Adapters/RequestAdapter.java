package com.example.bloodbagbb.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodbagbb.Activity.RequestFormActivity;
import com.example.bloodbagbb.Model.BloodRequest;
import com.example.bloodbagbb.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.viewHolder> {
    private Context context;
    private ArrayList<BloodRequest> userRequestList;
    private String checkingRequest;
    private DatabaseReference requestRef;
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
    public void onBindViewHolder(@NonNull RequestAdapter.viewHolder holder, final int position) {
        final BloodRequest requestInfo = userRequestList.get(position);

        String enDate = requestInfo.getEndDate();
        String number = requestInfo.getContact();
        String home = requestInfo.getArea();
        String description = requestInfo.getReason();
        String sample = requestInfo.getBloodGroup();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //Checking current user and set accepting and deleting request
        if (requestInfo.getUserId().equals(firebaseUser.getUid())) {
            holder.acceptBTN.setVisibility(View.GONE);
            holder.declineBTN.setVisibility(View.VISIBLE);
        } else {
            holder.declineBTN.setVisibility(View.GONE);
            holder.acceptBTN.setVisibility(View.VISIBLE);
        }
        // Checking emergency or Normal and set number Visible and Invisible
        Log.d(TAG, "onBindViewHolder: " + checkingRequest);
        if (checkingRequest != null) {

            if (checkingRequest.equals("flagNM")) {
                holder.contact.setVisibility(View.GONE);
                holder.viewContact.setVisibility(View.GONE);
            } else if (checkingRequest.equals("flagEM")) {
                holder.viewContact.setVisibility(View.VISIBLE);
                holder.contact.setText(number);
            } else if (checkingRequest.equals("flagAll")) {
                holder.viewContact.setVisibility(View.VISIBLE);
                holder.contact.setText(number);
            }
        }
        holder.contact.setText(number);
        holder.date2.setText(enDate);
        holder.area.setText(home);
        holder.reason.setText(description);
        holder.sampleBlood.setText(sample);

        requestRef = FirebaseDatabase.getInstance().getReference("requests");
        holder.acceptBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+Uri.encode(requestInfo.getContact()))));
            }
        });

        holder.declineBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestRef.child(requestInfo.getPushId()).removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(context, "Successfully Deleted!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                userRequestList.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.sharedLayOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sDate = requestInfo.getEndDate();
                String sContact = requestInfo.getContact();
                String sArea = requestInfo.getArea();
                String sReason = requestInfo.getReason();
                String sImportance = requestInfo.getType();

                String sharedContent = "Powered By 'Blood Bag' App" +System.getProperty("line.separator")
                        + "Importance : "+sImportance +System.getProperty("line.separator")
                        + " Needed Date : "+ sDate +System.getProperty("line.separator")
                        + "Contact : "+sContact +System.getProperty("line.separator")
                        + "Reason : "+sReason +System.getProperty("line.separator")
                        + "Location : "+sArea +System.getProperty("line.separator")
                        +"Advanced Thank You!";
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Blood Request");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, sharedContent);
                context.startActivity(Intent.createChooser(sharingIntent, "Share text via"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return userRequestList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        private TextView date2, contact, area, reason, sampleBlood, viewContact;
        private Button declineBTN, acceptBTN;
        private LinearLayout sharedLayOut;

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
            sharedLayOut = itemView.findViewById(R.id.shareWithSocialMedia);

        }
    }
}
