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


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    private Context context;
    private TextView viewProfileTV, viewPasswordTV, viewCurrentAddress, viewParmanentAddress;
    private ImageView backToParent;

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
    }

    private void inItView(View view) {

        viewProfileTV = view.findViewById(R.id.profileEdit);
        viewPasswordTV = view.findViewById(R.id.tvPassword);
        backToParent = view.findViewById(R.id.arrow);
        viewCurrentAddress = view.findViewById(R.id.currentAddressTV);
        viewParmanentAddress = view.findViewById(R.id.parmanentAddressTV);
    }
}
