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

import com.example.bloodbagbb.Model.User;
import com.example.bloodbagbb.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private Context context;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference donorRef;
    FirebaseUser firebaseUser;
    private User user;

    public static final String TAG ="ProfileFragment";
    TextView userNameTV, userBloodGrioupTV,userContactTV;
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

        retrievedData();
    }

    private void retrievedData() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        donorRef = fdb.getReference("donor");
        String userID = firebaseUser.getUid();

        donorRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    user = dataSnapshot.getValue(User.class);
                    Log.d(TAG, "onDataChange: "+ user.toString());

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

    }
}