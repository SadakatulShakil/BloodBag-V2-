package com.example.bloodbagbb.Fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewProfileFragment extends Fragment {

    private Context context;
    private TextView btnEdit;
    private TextView userNameTV, userBloodGrioupTV, userContactTV, userDistrictTV, userAreaTV, userEmailTV;
    private ArrayList<User> userInfo;
    private User user;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference donorRef;
    private DatabaseReference historyRef;
    FirebaseUser firebaseUser;
    private ImageView backToParent;

    public ViewProfileFragment() {
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
        return inflater.inflate(R.layout.fragment_view_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userInfo = new ArrayList<>();
        inItView(view);

        retrieveUserInfo();
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayoutID, new EditProfileFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
        backToParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayoutID, new SettingsFragment())
                        .commit();
            }
        });
    }

    private void retrieveUserInfo() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        String userID = firebaseUser.getUid();
        donorRef = fdb.getReference("donor");
        donorRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    user = dataSnapshot.getValue(User.class);
                    Log.d(TAG, "onDataChange: " + user.toString());

                    userNameTV.setText(user.getName());
                    userBloodGrioupTV.setText(user.getBloodGroup());
                    userContactTV.setText(user.getContact());
                    userEmailTV.setText(user.getEmail());
                    userDistrictTV.setText(user.getDistrict());
                    userAreaTV.setText(user.getArea());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void inItView(View view) {
        btnEdit = view.findViewById(R.id.editBTN);
        userNameTV = view.findViewById(R.id.userName);
        userEmailTV = view.findViewById(R.id.userEmail);
        userBloodGrioupTV = view.findViewById(R.id.userBloodGroup);
        userDistrictTV = view.findViewById(R.id.userDistrict);
        userAreaTV = view.findViewById(R.id.userArea);
        userContactTV = view.findViewById(R.id.userContact);
        backToParent = view.findViewById(R.id.arrow);

    }
}
