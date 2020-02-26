package com.example.bloodbagbb.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.bloodbagbb.Activity.NeedToKnowActivity;
import com.example.bloodbagbb.Activity.WhyDonateBloodActivity;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private Context context;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference donorRef;
    private DatabaseReference imageRef;
    private DatabaseReference historyRef;
    FirebaseUser firebaseUser;
    private User user;
    private String userId;
    CircleImageView profileImage;
    private CardView cardForWhyDonate, cardForNeedToKnow;
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
        imageRef = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();

        historyArrayList = new ArrayList<>();
        retrievedData();

        cardForWhyDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WhyDonateBloodActivity.class);
                startActivity(intent);
            }
        });

        cardForNeedToKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NeedToKnowActivity.class);
                startActivity(intent);
            }
        });

        displayProfileImage();
    }

    private void displayProfileImage() {
        final DatabaseReference displayUrl = imageRef.child("profileImages").child(userId);

        displayUrl.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    String url = snap.getValue()+"";
                    Log.d(TAG, "proImageUrl: " + url);

                    Picasso.get().load(url).into(profileImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
        cardForWhyDonate = view.findViewById(R.id.whyDonate);
        cardForNeedToKnow = view.findViewById(R.id.needToKnow);
        profileImage = view.findViewById(R.id.userProfileImage);

    }
}