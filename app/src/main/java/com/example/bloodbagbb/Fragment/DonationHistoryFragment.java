package com.example.bloodbagbb.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodbagbb.Adapters.HistoryAdapter;
import com.example.bloodbagbb.Adapters.RequestAdapter;
import com.example.bloodbagbb.Model.BloodRequest;
import com.example.bloodbagbb.Model.History;
import com.example.bloodbagbb.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 *
 */
public class DonationHistoryFragment extends Fragment {


    private Context context;
    private FloatingActionButton historyFAB;
    private TextView historyCounting;
    private int listSize;
    private String size;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference historyRef;
    private FirebaseUser user;
    private ArrayList<History> historyList;
    private RecyclerView recyclerView;
    private HistoryAdapter donationHistoryAdapter;
    public DonationHistoryFragment() {
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
        return inflater.inflate(R.layout.fragment_donation_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inItView(view);

        historyList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        donationHistoryAdapter = new HistoryAdapter(context, historyList);
        recyclerView.setAdapter(donationHistoryAdapter);

        retrievedHistoryData();

        historyFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayoutID, new AddHistoryFragment())
                        .commit();
            }
        });
    }

    private void retrievedHistoryData() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();
        historyRef = FirebaseDatabase.getInstance().getReference("history");

      historyRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              historyList.clear();
              for(DataSnapshot childsnapshot : dataSnapshot.getChildren()){
                  History historyInfo = childsnapshot.getValue(History.class);

                  historyList.add(historyInfo);

              }
              listSize = historyList.size();
              historyCounting.setText(listSize +" times");

              Log.d(TAG, "Total Data: " +listSize);

              donationHistoryAdapter.notifyDataSetChanged();
          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      });
    }

    private void inItView(View view) {

        historyFAB = view.findViewById(R.id.addHistoryFAB);
        recyclerView = view.findViewById(R.id.recyclerViewForHistory);
        historyCounting = view.findViewById(R.id.countDonation);
    }

}
