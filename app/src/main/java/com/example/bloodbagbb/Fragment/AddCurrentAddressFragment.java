package com.example.bloodbagbb.Fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bloodbagbb.Model.CurrentAddress;
import com.example.bloodbagbb.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCurrentAddressFragment extends Fragment {
    private Context context;
    private TextView districtET, areaET, houseET, roadET, flatET;
    private Button addHomeBTN;
    private DatabaseReference addressRef;
    private FirebaseAuth firebaseAuth;
    private String userId;
    private ImageView back;

    public AddCurrentAddressFragment() {
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
        return inflater.inflate(R.layout.fragment_add_current_address, container, false);
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
                UploadAddress();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayoutID, new CurrentAddressFragment())
                        .commit();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayoutID, new CurrentAddressFragment())
                        .commit();
            }
        });
    }

    private void UploadAddress() {
        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();
        addressRef = FirebaseDatabase.getInstance().getReference().child("address").child(userId);
        CurrentAddress currentAddress = new CurrentAddress(userId, districtET.getText().toString().trim(),
                areaET.getText().toString().trim(), houseET.getText().toString().trim(),
                roadET.getText().toString().trim(), flatET.getText().toString().trim());
        addressRef.child("currentAddress").setValue(currentAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Successfully Upload address !", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void inItView(View view) {

        districtET = view.findViewById(R.id.cDistrict);
        areaET = view.findViewById(R.id.cArea);
        houseET = view.findViewById(R.id.cHouseNo);
        roadET = view.findViewById(R.id.cRoadNo);
        flatET = view.findViewById(R.id.cFlatNo);
        back = view.findViewById(R.id.arrow);
        addHomeBTN = view.findViewById(R.id.addHomeBTN);
    }
}
