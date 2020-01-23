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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bloodbagbb.Model.User;
import com.example.bloodbagbb.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
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
public class EditProfileFragment extends Fragment {

    private Context context;
    private Button btnUpdate;
    private ImageView backToParent;

    private EditText userNameTV, userBloodGrioupTV, userContactTV, userDistrictTV, userAreaTV, userEmailTV;
    private User user;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference donorRef;
    FirebaseUser firebaseUser;
    public EditProfileFragment() {
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
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inItView(view);

        retrieveUserInfo();
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Successfully Updated!", Toast.LENGTH_LONG).show();
                updateUserInfo();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayoutID, new ViewProfileFragment())
                        .commit();
            }
        });

        backToParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayoutID, new ViewProfileFragment())
                        .commit();
            }
        });
    }

    private void updateUserInfo() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        String userID = firebaseUser.getUid();
        donorRef = fdb.getReference("donor");

        String newName = userNameTV.getText().toString().trim();
        String newEmail = userEmailTV.getText().toString().trim();
        String newBloodGroup = userBloodGrioupTV.getText().toString().trim();
        String newDistrict = userDistrictTV.getText().toString().trim();
        String newArea = userAreaTV.getText().toString().trim();
        String newContact = userContactTV.getText().toString().trim();

        User updateUser = new User(userID, newName, newEmail, newContact, newDistrict, newArea, newBloodGroup);

        donorRef.child(userID).setValue(updateUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                {
                    Toast.makeText(context, "Successfully Updated!", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
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
        btnUpdate = view.findViewById(R.id.upDateBTN);

        userNameTV = view.findViewById(R.id.userName);
        userEmailTV = view.findViewById(R.id.userEmail);
        userBloodGrioupTV = view.findViewById(R.id.userBloodGroup);
        userDistrictTV = view.findViewById(R.id.userDistrict);
        userAreaTV = view.findViewById(R.id.userArea);
        userContactTV = view.findViewById(R.id.userContact);

        backToParent = view.findViewById(R.id.arrow);
    }
}

