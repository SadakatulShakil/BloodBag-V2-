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

import com.example.bloodbagbb.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCurrentAddressFragment extends Fragment {
    private Context context;
    private TextView districtET, areaET, houseET, roadET, flatET;
    private Button addHomeBTN;
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
                        .replace(R.id.frameLayoutID, new CurrentAddressFragment())
                        .commit();
            }
        });
    }

    private void updateDataPassing() {

        String newDistrict = districtET.getText().toString().trim();
        String newArea = areaET.getText().toString().trim();
        String newHouse = houseET.getText().toString().trim();
        String newRoad = roadET.getText().toString().trim();
        String newFlat = flatET.getText().toString().trim();

        Fragment fragment = new CurrentAddressFragment();
        Bundle bundle = new Bundle();

        bundle.putString("NewDistrict", newDistrict);
        bundle.putString("NewArea", newArea);
        bundle.putString("NewHouse", newHouse);
        bundle.putString("NewRoad", newRoad);
        bundle.putString("NewFlat", newFlat);

        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayoutID, fragment)
                .commit();
    }

    private void getOldDataPassing() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String oldDistrict = bundle.getString("OldDistrict");
            String oldArea = bundle.getString("OldArea");
            String oldHouse = bundle.getString("OldHouse");
            String oldRoad = bundle.getString("OldRoad");
            String oldFlat = bundle.getString("OldFlat");

            districtET.setText(oldDistrict);
            areaET.setText(oldArea);
            houseET.setText(oldHouse);
            roadET.setText(oldRoad);
            flatET.setText(oldFlat);
        }
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
