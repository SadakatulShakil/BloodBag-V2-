package com.example.bloodbagbb.Fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodbagbb.Adapters.DonorAdapter;
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
public class FindDonorFragment extends Fragment {

    public static final String TAG ="FindDonorFragment";
    private AutoCompleteTextView bloodSearchBox;
    private TextView filterBlood;
    private Context context;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private RecyclerView recyclerView;
    private ArrayList<User> userInfoList;
    private DonorAdapter appUserAdapter;
    DatabaseReference donorRef;
    private String bloodGroup;
    private String district;
    public FindDonorFragment() {
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
        return inflater.inflate(R.layout.fragment_find_donor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inItView(view);

        userInfoList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        appUserAdapter = new DonorAdapter(context,userInfoList);
        recyclerView.setAdapter(appUserAdapter);

        RetrievedAllDonorData();

        filterBlood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFilteredGroup();
            }
        });

        bloodSearchBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findDesireDistrictDonor();
            }
        });

        bloodSearchBox.setThreshold(1);
        bloodSearchBox.setAdapter(new ArrayAdapter<>(context,android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.districts)));


    }

    private void findDesireDistrictDonor() {

        bloodGroup = filterBlood.getText().toString().trim();

        district = bloodSearchBox.getText().toString().trim();
        if(bloodGroup.equals("filter")){
            searchByDistrict(district);
        }
        else {
            searchByDistrict(district,bloodGroup);
        }

    }

    private void searchByDistrict(String district, final String bloodGroup) {

        DatabaseReference districtRef = FirebaseDatabase.getInstance().getReference("donor");

        Query query = districtRef.orderByChild("district").equalTo(district);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userInfoList.clear();
                for(DataSnapshot userSnapshot : dataSnapshot.getChildren()){
                    User user = userSnapshot.getValue(User.class);
                    if(user.getBloodGroup().equals(bloodGroup)){
                        userInfoList.add(user);
                    }
                }
                appUserAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void searchByDistrict(String district) {

        DatabaseReference districtRef = FirebaseDatabase.getInstance().getReference("donor");

        Query query = districtRef.orderByChild("district").equalTo(district);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userInfoList.clear();
                for(DataSnapshot userSnapshot : dataSnapshot.getChildren()){
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


    private void openFilteredGroup() {
        Log.d(TAG, "openFilteredGroup: start");
        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        View dialogView = getLayoutInflater().inflate(R.layout.filter_blood_group, null);

        final TextView  aPositive, aNegative, bPositive, bNegative, oPositive, oNegative, abPositive, abNegative;
        final TextView cancelBt = dialogView.findViewById(R.id.btCancel);

        aPositive = dialogView.findViewById(R.id.aPossitive);
        aNegative = dialogView.findViewById(R.id.aNegetive);
        bPositive = dialogView.findViewById(R.id.bPossitive);
        bNegative = dialogView.findViewById(R.id.bNegetive);
        oPositive = dialogView.findViewById(R.id.oPossitive);
        oNegative = dialogView.findViewById(R.id.oNegetive);
        abPositive = dialogView.findViewById(R.id.abPossitive);
        abNegative = dialogView.findViewById(R.id.abNegetive);

        alert.setView(dialogView);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(true);

        cancelBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        aPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            alertDialog.dismiss();
            filterBlood.setText("A+");
             bloodGroup = filterBlood.getText().toString().trim();

             district = bloodSearchBox.getText().toString().trim();
            if (district.isEmpty()){
                searchByBloodGroup(bloodGroup);

            }else {
                searchByBloodGroup(district,bloodGroup);
            }

            }
        });
        aNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                filterBlood.setText("A-");
                //do search
                String bloodGroup = filterBlood.getText().toString().trim();

                String district = bloodSearchBox.getText().toString().trim();
                if (district.isEmpty()){
                    searchByBloodGroup(bloodGroup);

                }else {
                    searchByBloodGroup(district,bloodGroup);
                }

            }
        });
        bPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                filterBlood.setText("B+");
                //do search
                 bloodGroup = filterBlood.getText().toString().trim();

                 district = bloodSearchBox.getText().toString().trim();
                if (district.isEmpty()){
                    searchByBloodGroup(bloodGroup);

                }else {
                    searchByBloodGroup(district,bloodGroup);
                }

            }
        });
        bNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                filterBlood.setText("B-");
                //do search
                 bloodGroup = filterBlood.getText().toString().trim();

                 district = bloodSearchBox.getText().toString().trim();
                if (district.isEmpty()){
                    searchByBloodGroup(bloodGroup);

                }else {
                    searchByBloodGroup(district,bloodGroup);
                }

            }
        });
        oPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                filterBlood.setText("O+");
                //do search
                 bloodGroup = filterBlood.getText().toString().trim();

                 district = bloodSearchBox.getText().toString().trim();
                if (district.isEmpty()){
                    searchByBloodGroup(bloodGroup);

                }else {
                    searchByBloodGroup(district,bloodGroup);
                }

            }
        });
        oNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                filterBlood.setText("O-");
                //do search
                 bloodGroup = filterBlood.getText().toString().trim();

                 district = bloodSearchBox.getText().toString().trim();
                if (district.isEmpty()){
                    searchByBloodGroup(bloodGroup);

                }else {
                    searchByBloodGroup(district,bloodGroup);
                }

            }
        });
        abPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                filterBlood.setText("AB+");
                //do search
                 bloodGroup = filterBlood.getText().toString().trim();

                 district = bloodSearchBox.getText().toString().trim();
                if (district.isEmpty()){
                    searchByBloodGroup(bloodGroup);

                }else {
                    searchByBloodGroup(district,bloodGroup);
                }

            }
        });
        abNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                filterBlood.setText("AB-");
                //do search
                 bloodGroup = filterBlood.getText().toString().trim();

                 district = bloodSearchBox.getText().toString().trim();
                if (district.isEmpty()){
                    searchByBloodGroup(bloodGroup);

                }else {
                    searchByBloodGroup(district,bloodGroup);
                }

            }
        });

        alertDialog.show();

    }

    private void searchByBloodGroup(final String district, String bloodGroup) {
        //do search
        DatabaseReference bloodRef = FirebaseDatabase.getInstance().getReference("donor");

        Query query = bloodRef.orderByChild("bloodGroup").equalTo(bloodGroup);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userInfoList.clear();
                for(DataSnapshot userSnapshot : dataSnapshot.getChildren()){
                    User user = userSnapshot.getValue(User.class);
                    if(user.getDistrict().equals(district)){
                        userInfoList.add(user);
                    }

                }
                appUserAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void searchByBloodGroup(String bloodGroup) {

        //do search
        DatabaseReference bloodRef = FirebaseDatabase.getInstance().getReference("donor");

        Query query = bloodRef.orderByChild("bloodGroup").equalTo(bloodGroup);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userInfoList.clear();
                for(DataSnapshot userSnapshot : dataSnapshot.getChildren()){
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

    private void RetrievedAllDonorData() {
        donorRef = FirebaseDatabase.getInstance().getReference("donor");

        donorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //userInfoList.clear();
                for(DataSnapshot userSnapshot : dataSnapshot.getChildren()){
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

    private void inItView(View view) {

        bloodSearchBox = view.findViewById(R.id.searchBox);
        recyclerView = view.findViewById(R.id.donorRecycleView);
        filterBlood = view.findViewById(R.id.filterBox);
    }
}
