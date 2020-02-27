package com.example.bloodbagbb.Fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bloodbagbb.Model.CurrentAddress;
import com.example.bloodbagbb.Model.User;
import com.example.bloodbagbb.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentAddressFragment extends Fragment {

    private Context context;
    private TextView districtTV, areaTV, houseTV, roadTV, flatTV;
    private FloatingActionButton addHomeBTN;
    private ImageView back;
    private DatabaseReference currentAddressRef;
    private FirebaseAuth firebaseAuth;
    private String userId;
    public CurrentAddressFragment() {
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
        return inflater.inflate(R.layout.fragment_current_address, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inItView(view);

        addHomeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayoutID, new AddCurrentAddressFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayoutID, new SettingsFragment())
                        .commit();
            }
        });

        getCurrentAddressInfo();
    }

    private void getCurrentAddressInfo() {

        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();
        currentAddressRef = FirebaseDatabase.getInstance().getReference().child("address").child(userId);
        currentAddressRef.child("currentAddress").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if(dataSnapshot.exists()){
                    CurrentAddress currentAddressInfo = dataSnapshot.getValue(CurrentAddress.class);
                    districtTV.setText(currentAddressInfo.getDistrict());
                    areaTV.setText(currentAddressInfo.getArea());
                    houseTV.setText(currentAddressInfo.getHouseNo());
                    roadTV.setText(currentAddressInfo.getRoadNo());
                    flatTV.setText(currentAddressInfo.getFlatNo());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void inItView(View view) {
        districtTV = view.findViewById(R.id.cDistrict);
        areaTV = view.findViewById(R.id.cArea);
        houseTV = view.findViewById(R.id.cHouseNo);
        roadTV = view.findViewById(R.id.cRoadNo);
        flatTV = view.findViewById(R.id.cFlatNo);
        back = view.findViewById(R.id.arrow);
        addHomeBTN = view.findViewById(R.id.addCurrentAddressFAB);

    }
}
