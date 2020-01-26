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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bloodbagbb.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddParmanentAddressFragment extends Fragment {

    private Context context;
    private EditText divisiontET, districtET, upazillaET, villageET, postOfficeET;
    private Button addHomeBTN;
    private ImageView back;
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
        getOldDataPassing();

        clickEvents();

    }

    private void clickEvents() {
        addHomeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDataPassing();
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

    private void updateDataPassing() {

        String newDivision = divisiontET.getText().toString().trim();
        String newDistrict = districtET.getText().toString().trim();
        String newUpazilla = upazillaET.getText().toString().trim();
        String newVillage = villageET.getText().toString().trim();
        String newPostOffice = postOfficeET.getText().toString().trim();

        Fragment fragment = new CurrentAddressFragment();
        Bundle bundle = new Bundle();

        bundle.putString("NewDivision", newDivision);
        bundle.putString("NewDistrict", newDistrict);
        bundle.putString("NewUpazilla", newUpazilla);
        bundle.putString("NewVillage", newVillage);
        bundle.putString("NewPostOffice", newPostOffice);

        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayoutID, fragment)
                .commit();
    }

    private void getOldDataPassing() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String oldDivision = bundle.getString("OldDivision");
            String oldDistrict = bundle.getString("OldDistrict");
            String oldUpazilla = bundle.getString("OldUpazilla");
            String oldVillage = bundle.getString("OldVillage");
            String oldPostOffice = bundle.getString("OldPostOffice");

            divisiontET.setText(oldDivision);
            districtET.setText(oldDistrict);
            upazillaET.setText(oldUpazilla);
            villageET.setText(oldVillage);
            postOfficeET.setText(oldPostOffice);
        }
    }
    private void inItView(View view) {
        divisiontET = view.findViewById(R.id.pDivision);
        districtET = view.findViewById(R.id.pDistrict);
        upazillaET = view.findViewById(R.id.pUpazilla);
        villageET = view.findViewById(R.id.pVillage);
        postOfficeET = view.findViewById(R.id.pPostOffice);

        addHomeBTN = view.findViewById(R.id.addHomeBTN);
    }
}
