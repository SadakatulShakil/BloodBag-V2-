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

import com.example.bloodbagbb.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentAddressFragment extends Fragment {

    private Context context;
    private TextView districtTV, areaTV, houseTV, roadTV, flatTV;
    private FloatingActionButton addHomeBTN;
    private ImageView back;
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
        getNewDataPassing();
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

            districtTV.setText(newDistrict);
            areaTV.setText(newArea);
            houseTV.setText(newHouse);
            roadTV.setText(newRoad);
            flatTV.setText(newFlat);
        }
    }

    private void passOldDataToEditAddress() {

        String oldDistrict = districtTV.getText().toString().trim();
        String oldArea = areaTV.getText().toString().trim();
        String oldHouse = houseTV.getText().toString().trim();
        String oldRoad = roadTV.getText().toString().trim();
        String oldFlat = flatTV.getText().toString().trim();

        Fragment fragment = new AddCurrentAddressFragment();

        Bundle bundle = new Bundle();
        bundle.putString("OldDistrict", oldDistrict);
        bundle.putString("OldArea", oldArea);
        bundle.putString("OldHouse", oldHouse);
        bundle.putString("OldRoad", oldRoad);
        bundle.putString("OldFlat", oldFlat);

        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayoutID, fragment)
                .commit();
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
