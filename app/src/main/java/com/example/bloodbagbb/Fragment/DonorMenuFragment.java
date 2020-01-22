package com.example.bloodbagbb.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.bloodbagbb.Activity.SignInActivity;
import com.example.bloodbagbb.Model.Utils;
import com.example.bloodbagbb.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.bloodbagbb.Model.Utils.bucketFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class DonorMenuFragment extends Fragment {

    private BottomNavigationView bottomNavView;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private FrameLayout frameLayout;
    private Context context;

    public DonorMenuFragment() {
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
        return inflater.inflate(R.layout.fragment_donor_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        }

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_closed);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        /*if(bucketFragment.equals("empty")){
            initBottomNavigation();
        }
        else{
            initBottomNavigation(bucketFragment);
        }*/
        initBottomNavigation();
        initNavigationViewDrawer();


        getChildFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new ProfileFragment())
                .commit();

    }

    private void initBottomNavigation(final String bucketFragment) {


        Fragment selectedFragment = null;
        if (bucketFragment.equals("profile")) {
            selectedFragment = new ProfileFragment();
        } else if (bucketFragment.equals("findDonor")) {
            selectedFragment = new FindDonorFragment();
        } else if (bucketFragment.equals("request")) {
            selectedFragment = new ForRequestFragment();
        } else if (bucketFragment.equals("history")) {
            selectedFragment = new DonationHistoryFragment();
        }
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragmentContainer, selectedFragment)
                .commit();


    }

    private void initNavigationViewDrawer() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch ((item.getItemId())) {

                    case R.id.settings:
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayoutID, new SettingsFragment())
                                .commit();
                        Toast.makeText(context, "Settings Under Construction be Happy!", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.terms:
                        Toast.makeText(context, "Terms Under Construction be Happy!", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.licenses:
                        Toast.makeText(context, "Licenses Under Construction be Happy!", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.about:
                        Toast.makeText(context, "About Under Construction be Happy!", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.logOut:
                        FirebaseAuth.getInstance().signOut();
                        getActivity().finish();
                        Intent intent = new Intent(context, SignInActivity.class);
                        startActivity(intent);
                        break;

                    default:
                        break;
                }
                return false;
            }
        });

    }

    private void initBottomNavigation() {

        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.profileFm:
                        selectedFragment = new ProfileFragment();
                        bucketFragment = "profile";
                        break;
                    case R.id.donorFindFm:
                        selectedFragment = new FindDonorFragment();
                        bucketFragment = "findDonor";
                        break;
                    case R.id.requestFm:
                        selectedFragment = new ForRequestFragment();
                        bucketFragment = "request";
                        break;
                    case R.id.historyFm:
                        selectedFragment = new DonationHistoryFragment();
                        bucketFragment = "history";
                        break;

                    default:
                        break;
                }
                if (selectedFragment != null) {
                    FragmentManager fm = getChildFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.fragmentContainer, selectedFragment)
                            .commit();
                }
                return true;
            }
        });
    }


    private void initView(View view) {

        frameLayout = view.findViewById(R.id.fragmentContainer);

        drawerLayout = view.findViewById(R.id.drawer);
        navigationView = view.findViewById(R.id.navigationDrawer);
        toolbar = view.findViewById(R.id.mainTB);
        bottomNavView = view.findViewById(R.id.bottomNavigationView);

    }
}
