package com.example.bloodbagbb.Fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.example.bloodbagbb.Adapters.DonorAdapter;
import com.example.bloodbagbb.Adapters.ManageAdapter;
import com.example.bloodbagbb.Model.User;
import com.example.bloodbagbb.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManageableUserListFragment extends Fragment {

    public static final String TAG = "FindDonorFragment";
    private AutoCompleteTextView bloodSearchBox;
    private Context context;
    TextView resetList;
    private RecyclerView recyclerView;
    private ArrayList<User> userInfoList;
    private ManageAdapter appUserAdapter;
    DatabaseReference donorRef;
    private String districtAndContact;

    public ManageableUserListFragment() {
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
        return inflater.inflate(R.layout.fragment_manageable_user_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

        userInfoList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        appUserAdapter = new ManageAdapter(context, userInfoList);
        recyclerView.setAdapter(appUserAdapter);

        RetrievedAllDonorData();

        bloodSearchBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findDesireDistrictDonor();
            }
        });

        bloodSearchBox.setThreshold(1);
        bloodSearchBox.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.districts)));

        resetList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrievedAllDonorData();
            }
        });

    }

    private void RetrievedAllDonorData() {

        donorRef = FirebaseDatabase.getInstance().getReference("donor");

        donorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //userInfoList.clear();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);

                    userInfoList.add(user);
                }
                appUserAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void findDesireDistrictDonor() {

        districtAndContact = bloodSearchBox.getText().toString().trim();

            searchByDistrict(districtAndContact);

    }

    private void searchByDistrict(String districtAndContact) {
        DatabaseReference districtRef = FirebaseDatabase.getInstance().getReference("donor");

        String firstChar = String.valueOf(districtAndContact.charAt(0));
        Log.d(TAG, "searchByDistrict: " + firstChar);

        if(!firstChar.equals("0")){
            Query queryDis = districtRef.orderByChild("district").equalTo(districtAndContact);
            queryDis.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    userInfoList.clear();
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        User user = userSnapshot.getValue(User.class);
                        userInfoList.add(user);
                    }
                    appUserAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else{
            Query queryCon = districtRef.orderByChild("contact").equalTo(districtAndContact);
            queryCon.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    userInfoList.clear();
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        User user = userSnapshot.getValue(User.class);
                        userInfoList.add(user);
                    }
                    appUserAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

    }

    private void initView(View view) {

        bloodSearchBox = view.findViewById(R.id.searchBox);
        recyclerView = view.findViewById(R.id.donorRecycleView);
        resetList = view.findViewById(R.id.resetDonorList);
    }
}
