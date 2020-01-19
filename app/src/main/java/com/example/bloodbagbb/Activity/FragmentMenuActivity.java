package com.example.bloodbagbb.Activity;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.bloodbagbb.Fragment.DonorMenuFragment;
import com.example.bloodbagbb.Fragment.ProfileFragment;
import com.example.bloodbagbb.R;

public class FragmentMenuActivity extends AppCompatActivity {

    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_menu);

        initViews();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutID, new DonorMenuFragment())
                .commit();
    }

    private void initViews() {
        frameLayout = findViewById(R.id.frameLayoutID);
    }
}
