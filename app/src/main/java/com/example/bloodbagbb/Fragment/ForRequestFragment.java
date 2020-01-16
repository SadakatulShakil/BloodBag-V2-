package com.example.bloodbagbb.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodbagbb.Adapters.RequestAdapter;
import com.example.bloodbagbb.Model.BloodRequest;
import com.example.bloodbagbb.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForRequestFragment extends Fragment {

    private  Context context;
    private FloatingActionButton postFab;
    private FirebaseAuth firebaseAuth;
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

        requestArrayList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        bloodPostAdapter = new RequestAdapter(context, requestArrayList);
        recyclerView.setAdapter(bloodPostAdapter);

        RetrievedRequestData();
        ClickEvents();
    }

    private void RetrievedRequestData() {
        requestRef = FirebaseDatabase.getInstance().getReference("requests");

        requestRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                for(DataSnapshot childSnapshot : dataSnapshot.getChildren()){
                BloodRequest bloodRequest = childSnapshot.getValue(BloodRequest.class);

                requestArrayList.add(bloodRequest);
                }
                bloodPostAdapter.notifyDataSetChanged();
            }


            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void ClickEvents() {

        postFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //openPostRequestDialog();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayoutID, new RequestFormFragment())
                        .commit();
            }
        });
    }

    /*private void openPostRequestDialog() {

        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        View dialogView = getLayoutInflater().inflate(R.layout.post_request_dialog, null);

        final EditText startDate, endDate, userArea, description, number;
        final AutoCompleteTextView expectedBG, userDistrict;
        final TextView btCancel, btPost;

        startDate = dialogView.findViewById(R.id.datePickerET1);
        endDate = dialogView.findViewById(R.id.datePickerET2);
        expectedBG = dialogView.findViewById(R.id.userBloodGroup);
        userDistrict = dialogView.findViewById(R.id.locationEt);
        userArea = dialogView.findViewById(R.id.areaET);
        description = dialogView.findViewById(R.id.descriptionET);
        btCancel = dialogView.findViewById(R.id.cancelTV);
        btPost = dialogView.findViewById(R.id.postTV);
        number = dialogView.findViewById(R.id.contactET);

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        expectedBG.setThreshold(1);
        expectedBG.setAdapter(new ArrayAdapter<>(context,android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.bloodGroup)));

        userDistrict.setThreshold(1);
        userDistrict.setAdapter(new ArrayAdapter<>(context,android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.districts)));

        alert.setView(dialogView);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(true);
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        btPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String date1 = startDate.getText().toString().trim();
                String date2 = endDate.getText().toString().trim();
                String bGroup = expectedBG.getText().toString().trim();
                String contact = number.getText().toString().trim();
                String district = userDistrict.getText().toString().trim();
                String area = userArea.getText().toString().trim();
                String reason = description.getText().toString().trim();

                if (date1.isEmpty()) {
                    startDate.setError("Start date is required!");
                    startDate.requestFocus();
                    return;
                }

                if (date2.isEmpty()) {
                    endDate.setError("End date is required!");
                    endDate.requestFocus();
                    return;
                }
                if (bGroup.isEmpty()) {
                    expectedBG.setError("start date is required!");
                    expectedBG.requestFocus();
                    return;
                }
                if (district.isEmpty()) {
                    userDistrict.setError("start date is required!");
                    userDistrict.requestFocus();
                    return;
                }
                if (area.isEmpty()) {
                    userArea.setError("start date is required!");
                    userArea.requestFocus();
                    return;
                }
                if(reason.isEmpty()){
                    description.setError("Give a reason is required!");
                    description.requestFocus();
                    return;
                }
                if(contact.isEmpty()){
                    number.setError("contact is required!");
                    number.requestFocus();
                    return;
                }

                storeRequestData(date1, date2, bGroup, contact, district, area, reason);
            }
        });

        alertDialog.show();
    }

    private void storeRequestData(final String date1, final String date2,
                                  final String bGroup, final String contact, final String district,
                                  final String area, final String reason) {

        requestRef = FirebaseDatabase.getInstance().getReference("requests");
        user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        String pushId = requestRef.push().getKey();
        String postingTime = "12.45 pm";
        String type ="Emergency";
        bloodRequest = new BloodRequest(userId, pushId, date1, date2, bGroup, contact, district, area, type, reason, postingTime);

    }*/



    private void initViews(View view) {
        postFab = view.findViewById(R.id.goToPostFAB);
        recyclerView = view.findViewById(R.id.recyclerViewForPost);
    }
}
