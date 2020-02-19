package com.example.bloodbagbb.Fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bloodbagbb.Adapters.FeedBackAdapter;
import com.example.bloodbagbb.Model.User;
import com.example.bloodbagbb.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedBackListFragment extends Fragment {

    private Context context;
    private DatabaseReference feedbackRef;
    private FirebaseAuth firebaseAuth;
    private RecyclerView feedbackRecyclerView;
    private int listSize;
    String userId;
    private ArrayList<User> mFeedbackList;
    private FeedBackAdapter feedAdapter;
    private static final String TAG = "FeedBackListFragment";

    public FeedBackListFragment() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed_back_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();
        mFeedbackList = new ArrayList<>();

        feedbackRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        feedAdapter = new FeedBackAdapter(context, mFeedbackList);
        feedbackRecyclerView.setAdapter(feedAdapter);

        retrievingFeedBackData();
    }

    private void retrievingFeedBackData() {
        feedbackRef = FirebaseDatabase.getInstance().getReference("feedback");

        feedbackRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mFeedbackList.clear();
                for(DataSnapshot snap : dataSnapshot.getChildren()){

                    User feedInfo = snap.getValue(User.class);

                    mFeedbackList.add(feedInfo);
                }

                listSize = mFeedbackList.size();
                Log.d(TAG, "feedBack item: " + listSize);
                feedAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initView(View view) {
        feedbackRecyclerView = view.findViewById(R.id.recyclerViewForFeedBack);
    }
}
