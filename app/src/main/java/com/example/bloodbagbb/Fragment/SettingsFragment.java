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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bloodbagbb.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    private Context context;
    CircleImageView demoProfile;
    private TextView viewProfileTV, viewPasswordTV, viewCurrentAddress, viewParmanentAddress;
    private ImageView backToParent;
    private DatabaseReference donorRef;
    private DatabaseReference imageRef;
    private FirebaseAuth firebaseAuth;
    private String userId;

    public SettingsFragment() {
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
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inItView(view);

        imageRef = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();

        clickEvents();
    }

    private void clickEvents() {
        viewProfileTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayoutID, new ViewProfileFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        viewPasswordTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayoutID, new ChangePasswordFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        backToParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayoutID, new DonorMenuFragment())
                        .commit();
            }
        });

        viewCurrentAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayoutID, new CurrentAddressFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
        viewParmanentAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayoutID, new ParmanentAddressFragment())
                        .addToBackStack(null)
                        .commit();
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
                    Picasso.get().load(url).into(demoProfile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void inItView(View view) {

        viewProfileTV = view.findViewById(R.id.profileEdit);
        viewPasswordTV = view.findViewById(R.id.tvPassword);
        backToParent = view.findViewById(R.id.arrow);
        viewCurrentAddress = view.findViewById(R.id.currentAddressTV);
        viewParmanentAddress = view.findViewById(R.id.parmanentAddressTV);
        demoProfile = view.findViewById(R.id.profileIV);
    }
}
