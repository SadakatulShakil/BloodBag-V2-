package com.example.bloodbagbb.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bloodbagbb.Activity.CheckFeedBackActivity;
import com.example.bloodbagbb.Activity.FragmentMenuActivity;
import com.example.bloodbagbb.Activity.ManageDonorActivity;
import com.example.bloodbagbb.Activity.ManuallyAddDonorActivity;
import com.example.bloodbagbb.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminPanelFragment extends Fragment {

    private CardView checkFeedBack, manageDonor, addDonor, backToHome;
    private Context context;

    public AdminPanelFragment() {
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
        return inflater.inflate(R.layout.fragment_admin_panel, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        clickEvents();

    }

    private void clickEvents() {
        checkFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Check FeedBack Here!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, CheckFeedBackActivity.class);
                startActivity(intent);
            }
        });

        manageDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Manage Donor Here!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ManageDonorActivity.class);
                startActivity(intent);
            }
        });

        addDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Manually Add Donor Here!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ManuallyAddDonorActivity.class);
                startActivity(intent);
            }
        });
        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Back to Home Here!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, FragmentMenuActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

    }

    private void initView(View view) {
        checkFeedBack = view.findViewById(R.id.checkFeedBackCard);
        manageDonor = view.findViewById(R.id.manageDonorCard);
        addDonor = view.findViewById(R.id.addDonorCard);
        backToHome = view.findViewById(R.id.backToHomeCard);

    }
}
