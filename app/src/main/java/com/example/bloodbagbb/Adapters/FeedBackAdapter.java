package com.example.bloodbagbb.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodbagbb.Activity.CheckFeedBackActivity;
import com.example.bloodbagbb.Fragment.ResponseFeedBackFragment;
import com.example.bloodbagbb.Model.User;
import com.example.bloodbagbb.R;

import java.util.ArrayList;

public class FeedBackAdapter extends RecyclerView.Adapter<FeedBackAdapter.viewHolder> {

    private Context context;
    private ArrayList<User> feedbackList;

    public FeedBackAdapter(Context context, ArrayList<User> feedbackList) {
        this.context = context;
        this.feedbackList = feedbackList;
    }

    @NonNull
    @Override
    public FeedBackAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_list, parent, false);
        return new FeedBackAdapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedBackAdapter.viewHolder holder, int position) {

        final User feedInfo = feedbackList.get(position);

        String fName = feedInfo.getName();
        String fContact = feedInfo.getContact();
        String fDescription = feedInfo.getFeedBack();

        holder.userName.setText(fName);
        holder.userContact.setText(fContact);
        holder.userFeedBack.setText(fDescription);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ResponseFeedBackFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("dataList", feedInfo);
                fragment.setArguments(bundle);

                ((CheckFeedBackActivity)context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameForFeedBack, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return feedbackList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private TextView userName, userContact, userFeedBack;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.feedUserName);
            userContact = itemView.findViewById(R.id.feedUserContact);
            userFeedBack = itemView.findViewById(R.id.feedDescription);
        }
    }
}
