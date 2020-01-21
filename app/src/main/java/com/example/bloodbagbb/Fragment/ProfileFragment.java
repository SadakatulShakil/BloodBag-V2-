package com.example.bloodbagbb.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bloodbagbb.Model.History;
import com.example.bloodbagbb.Model.User;
import com.example.bloodbagbb.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private Context context;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference donorRef;
    private DatabaseReference historyRef;
    FirebaseUser firebaseUser;
    private User user;
    private int listSize;
    private ArrayList<History>historyArrayList;

    public static final String TAG = "ProfileFragment";
    private TextView userNameTV, userBloodGrioupTV, userContactTV, countingDonation;

    public ProfileFragment() {
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: started");

        initView(view);
        historyArrayList = new ArrayList<>();
        retrievedData();
    }

    private void retrievedData() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        donorRef = fdb.getReference("donor");
        historyRef = fdb.getReference("history");
        String userID = firebaseUser.getUid();

        historyRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot childsnapshot : dataSnapshot.getChildren()){
                    History historyInfo = childsnapshot.getValue(History.class);

                    historyArrayList.add(historyInfo);
                }
                listSize = historyArrayList.size();
                Log.d(TAG, "size: "+listSize);
                countingDonation.setText(listSize + " times");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        donorRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    user = dataSnapshot.getValue(User.class);
                    Log.d(TAG, "onDataChange: " + user.toString());

                    userNameTV.setText(user.getName());
                    userBloodGrioupTV.setText(user.getBloodGroup());
                    userContactTV.setText(user.getContact());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void initView(View view) {
        userNameTV = view.findViewById(R.id.userName);
        userBloodGrioupTV = view.findViewById(R.id.bloodGroup);
        userContactTV = view.findViewById(R.id.userContact);
        countingDonation =view.findViewById(R.id.donationCount);

    }
}