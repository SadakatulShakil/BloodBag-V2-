package com.example.bloodbagbb.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodbagbb.Activity.RequestFormActivity;
import com.example.bloodbagbb.Adapters.RequestAdapter;
import com.example.bloodbagbb.Model.BloodRequest;
import com.example.bloodbagbb.Model.Utils;
import com.example.bloodbagbb.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.example.bloodbagbb.Model.Utils.search;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForRequestFragment extends Fragment {

    private Context context;
    private FloatingActionButton postFab;
    private TextView emergencyRequest, normalRequest, allRequest;
    private AutoCompleteTextView requestSearchBox;
    private String district;
    private FirebaseAuth firebaseAuth;
    //private String hide = "f";
    private DatabaseReference requestRef;
    private FirebaseUser user;
    private ArrayList<BloodRequest> requestArrayList;
    private RecyclerView recyclerView;
    private RequestAdapter bloodPostAdapter;

    public ForRequestFragment() {
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
        return inflater.inflate(R.layout.fragment_for_request, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final FragmentManager fm = (getActivity()).getSupportFragmentManager();

        initViews(view);

        requestSearchBox.setThreshold(1);
        requestSearchBox.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.districts)));

        requestArrayList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        bloodPostAdapter = new RequestAdapter(context, requestArrayList);
        recyclerView.setAdapter(bloodPostAdapter);
       // Log.d(TAG, "onViewCreated: " + search);

        ClickEvents();
        RetrievedRequestData();
    }

    private void ClickEvents() {

        postFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //openPostRequestDialog();
                /*getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayoutID, new RequestFormFragment())
                        .commit();*/
                Intent intent = new Intent(context, RequestFormActivity.class);
                startActivity(intent);
            }
        });

        emergencyRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    emergencyRequest.setBackgroundTintList(getResources().getColorStateList(R.color.programChangeColor));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    normalRequest.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                }
                search = "flagEM";
                bloodPostAdapter = new RequestAdapter(context, requestArrayList,search);
                recyclerView.setAdapter(bloodPostAdapter);

                Log.d(TAG, "onClick emergency: " + search);
                district = requestSearchBox.getText().toString().trim();
                if (district.isEmpty()) {
                    searchByEmergency();
                } else {
                    searchByCombineEM(district);
                }
            }
        });

        normalRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    normalRequest.setBackgroundTintList(getResources().getColorStateList(R.color.programChangeColor));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    emergencyRequest.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                }
                search = "flagNM";
                bloodPostAdapter = new RequestAdapter(context, requestArrayList,search);
                recyclerView.setAdapter(bloodPostAdapter);

                Log.d(TAG, "onClick normal: " + search);

                district = requestSearchBox.getText().toString().trim();
                if (district.isEmpty()) {
                    searchByNormal();
                } else {
                    searchByCombineNM(district);
                }
            }
        });

        requestSearchBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchByDesireDistrict();
            }
        });

        allRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    normalRequest.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    emergencyRequest.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                }
                search = "flagAll";
                bloodPostAdapter = new RequestAdapter(context, requestArrayList,search);
                recyclerView.setAdapter(bloodPostAdapter);
                RetrievedRequestData();
            }
        });

    }

    private void RetrievedRequestData() {
        requestRef = FirebaseDatabase.getInstance().getReference("requests");
        requestRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    requestArrayList.clear();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    BloodRequest bloodRequest = childSnapshot.getValue(BloodRequest.class);

                    requestArrayList.add(bloodRequest);
                }
                bloodPostAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void searchByDesireDistrict() {
        district = requestSearchBox.getText().toString().trim();
        if (search.equals("flagEM")) {
            searchByCombineEM(district);
        } else if (search.equals("flagNM")) {
            searchByCombineNM(district);
        } else {
            searchByDistrict(district);
        }
    }

    private void searchByCombineNM(final String district) {
        requestRef = FirebaseDatabase.getInstance().getReference("requests");

        Query query = requestRef.orderByChild("type").equalTo("Regular");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                requestArrayList.clear();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    BloodRequest bloodRequest = childSnapshot.getValue(BloodRequest.class);
                    if (bloodRequest.getDistrict().equals(district)) {
                        requestArrayList.add(bloodRequest);
                    }
                }
                bloodPostAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void searchByCombineEM(final String district) {

        requestRef = FirebaseDatabase.getInstance().getReference("requests");

        Query query = requestRef.orderByChild("type").equalTo("Emergency");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                requestArrayList.clear();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    BloodRequest bloodRequest = childSnapshot.getValue(BloodRequest.class);
                    if (bloodRequest.getDistrict().equals(district)) {
                        requestArrayList.add(bloodRequest);
                    }
                }
                bloodPostAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void searchByDistrict(String district) {
        requestRef = FirebaseDatabase.getInstance().getReference("requests");

        Query query = requestRef.orderByChild("district").equalTo(district);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                requestArrayList.clear();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    BloodRequest bloodRequest = childSnapshot.getValue(BloodRequest.class);

                    requestArrayList.add(bloodRequest);
                }
                bloodPostAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void searchByNormal() {
        requestRef = FirebaseDatabase.getInstance().getReference("requests");

        Query query = requestRef.orderByChild("type").equalTo("Regular");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                requestArrayList.clear();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    BloodRequest bloodRequest = childSnapshot.getValue(BloodRequest.class);

                    requestArrayList.add(bloodRequest);
                }
                bloodPostAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void searchByEmergency() {
        requestRef = FirebaseDatabase.getInstance().getReference("requests");

        Query query = requestRef.orderByChild("type").equalTo("Emergency");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                requestArrayList.clear();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    BloodRequest bloodRequest = childSnapshot.getValue(BloodRequest.class);

                    requestArrayList.add(bloodRequest);
                }
                bloodPostAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initViews(View view) {
        postFab = view.findViewById(R.id.goToPostFAB);
        recyclerView = view.findViewById(R.id.recyclerViewForPost);
        emergencyRequest = view.findViewById(R.id.filterEmergency);
        normalRequest = view.findViewById(R.id.filterNormal);
        requestSearchBox = view.findViewById(R.id.searchRequestBox);
        allRequest = view.findViewById(R.id.filterAll);
    }
}
