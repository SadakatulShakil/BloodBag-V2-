package com.example.bloodbagbb.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bloodbagbb.Model.CurrentAddress;
import com.example.bloodbagbb.Model.PermanentAddress;
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
public class ParmanentAddressFragment extends Fragment {

    private Context context;
    private TextView divisiontTV, districtTV, upazillaTV, villageTV, postOfficeTV;
    private FloatingActionButton addHomeBTN;
    private ImageView back;
    private DatabaseReference currentAddressRef;
    private FirebaseAuth firebaseAuth;
    private String userId;

    public ParmanentAddressFragment() {
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
        return inflater.inflate(R.layout.fragment_parmanent_address, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inItView(view);
        clickEvents();
    }

    private void clickEvents() {
        addHomeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayoutID, new AddParmanentAddressFragment())
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
        currentAddressRef.child("permanentAddress").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    PermanentAddress permanentAddressInfo = dataSnapshot.getValue(PermanentAddress.class);
                    divisiontTV.setText(permanentAddressInfo.getDivision());
                    districtTV.setText(permanentAddressInfo.getDistrict());
                    upazillaTV.setText(permanentAddressInfo.getUpazilla());
                    villageTV.setText(permanentAddressInfo.getVillage());
                    postOfficeTV.setText(permanentAddressInfo.getPost_office());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void inItView(View view) {

        divisiontTV = view.findViewById(R.id.pDivision);
        districtTV = view.findViewById(R.id.pDistrict);
        upazillaTV = view.findViewById(R.id.pUpazilla);
        villageTV = view.findViewById(R.id.pVillage);
        postOfficeTV = view.findViewById(R.id.pPostOffice);
        back = view.findViewById(R.id.arrow);
        addHomeBTN = view.findViewById(R.id.addParmanentHomeFAB);
    }
}
