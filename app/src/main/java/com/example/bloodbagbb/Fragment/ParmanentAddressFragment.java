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

import com.example.bloodbagbb.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class ParmanentAddressFragment extends Fragment {

    private Context context;
    private TextView divisiontTV, districtTV, upazillaTV, villageTV, postOfficeTV;
    private FloatingActionButton addHomeBTN;
    private ImageView back;

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
        getNewDataPassing();

        clickEvents();
    }

    private void clickEvents() {
        addHomeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passOldDataToEditAddress();
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
    }

    private void getNewDataPassing() {

        Bundle bundle = getArguments();
        if (bundle != null) {
            String newDistrict = bundle.getString("NewDistrict");
            String newArea = bundle.getString("NewArea");
            String newHouse = bundle.getString("NewHouse");
            String newRoad = bundle.getString("NewRoad");
            String newFlat = bundle.getString("NewFlat");

            divisiontTV.setText(newDistrict);
            districtTV.setText(newArea);
            upazillaTV.setText(newHouse);
            villageTV.setText(newRoad);
            postOfficeTV.setText(newFlat);
        }
    }

    private void passOldDataToEditAddress() {

        String oldDivision = divisiontTV.getText().toString().trim();
        String oldDistrict = districtTV.getText().toString().trim();
        String oldUpazilla = upazillaTV.getText().toString().trim();
        String oldVillage = villageTV.getText().toString().trim();
        String oldPostOffice = postOfficeTV.getText().toString().trim();

        Fragment fragment = new AddCurrentAddressFragment();

        Bundle bundle = new Bundle();
        bundle.putString("OldDivision", oldDivision);
        bundle.putString("OldDistrict", oldDistrict);
        bundle.putString("OldUpazilla", oldUpazilla);
        bundle.putString("OldVillage", oldVillage);
        bundle.putString("OldPostOffice", oldPostOffice);

        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayoutID, fragment)
                .commit();
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
