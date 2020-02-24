package com.example.bloodbagbb.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.example.bloodbagbb.Fragment.FeedBackListFragment;
import com.example.bloodbagbb.Fragment.ManageableUserListFragment;
import com.example.bloodbagbb.R;

public class ManageDonorActivity extends AppCompatActivity {
    private FrameLayout frameLayout;
    private Toolbar dToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_donor);
        initViews();

        dToolbar.setTitle(getString(R.string.manageDonor));
        dToolbar.setNavigationIcon(R.drawable.ic_arrow);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameForManageDonor,new ManageableUserListFragment())
                .commit();

        dToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initViews() {
        frameLayout = findViewById(R.id.frameForFeedBack);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dToolbar = findViewById(R.id.toolbar);
        }
    }
}
