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
import android.widget.Toast;

import com.example.bloodbagbb.Activity.SignUpActivity;
import com.example.bloodbagbb.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileActivity extends Fragment {

    private Context context;
    private Button btnUpdate;

    public EditProfileActivity() {
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
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inItView(view);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Successfully Updated!", Toast.LENGTH_LONG).show();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayoutID, new SettingsFragment())
                        .commit();
            }
        });
    }

    private void inItView(View view) {
        btnUpdate = view.findViewById(R.id.upDateBTN);
    }
}
