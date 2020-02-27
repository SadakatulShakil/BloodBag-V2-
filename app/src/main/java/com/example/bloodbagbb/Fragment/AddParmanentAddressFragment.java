package com.example.bloodbagbb.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bloodbagbb.Model.CurrentAddress;
import com.example.bloodbagbb.Model.PermanentAddress;
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
public class AddParmanentAddressFragment extends Fragment {

    private Context context;
    private EditText divisiontET, districtET, upazillaET, villageET, postOfficeET;
    private Button addHomeBTN;
    private ImageView back;
    private DatabaseReference addressRef;
    private FirebaseAuth firebaseAuth;
    private String userId;
    public AddParmanentAddressFragment() {
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
        return inflater.inflate(R.layout.fragment_add_parmanent_address, container, false);
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
                        .replace(R.id.frameLayoutID, new ParmanentAddressFragment())
                        .commit();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayoutID, new ParmanentAddressFragment())
                        .commit();
            }
        });
    }

    private void UploadAddress() {

        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();
        addressRef = FirebaseDatabase.getInstance().getReference().child("address").child(userId);
        PermanentAddress permanentAddress = new PermanentAddress(userId, divisiontET.getText().toString().trim(),
                districtET.getText().toString().trim(), upazillaET.getText().toString().trim(),
                villageET.getText().toString().trim(), postOfficeET.getText().toString().trim());
        addressRef.child("permanentAddress").setValue(permanentAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
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
        divisiontET = view.findViewById(R.id.pDivision);
        districtET = view.findViewById(R.id.pDistrict);
        upazillaET = view.findViewById(R.id.pUpazilla);
        villageET = view.findViewById(R.id.pVillage);
        postOfficeET = view.findViewById(R.id.pPostOffice);

        back = view.findViewById(R.id.arrow);
        addHomeBTN = view.findViewById(R.id.addHomeBTN);
    }
}
