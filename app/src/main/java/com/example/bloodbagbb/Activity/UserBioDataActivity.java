package com.example.bloodbagbb.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bloodbagbb.Model.CurrentAddress;
import com.example.bloodbagbb.Model.PermanentAddress;
import com.example.bloodbagbb.Model.User;
import com.example.bloodbagbb.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserBioDataActivity extends AppCompatActivity {

    private TextView nameTV, emailTV, contactTV, bloodGroupTV,
                     currentDisTv, currentAreaTV, currentHouseNoTv, currentRoadNoTV, currentFlatNoTV,
                     pDivisionTV, pDistrictTV, pUpazillaTV, pVillageTV, pPostOfficeTV;

    CircleImageView profileImage;
    private User user;
    private DatabaseReference currentAddressRef;
    private DatabaseReference permanentAddressRef;
    private DatabaseReference imageRef;
    ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_biodata);

        final Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("donorInfo");

        displayProfileImage();
        initView();
        getUserInfo();
        getUserCurrentAddress();
        getUserPermanentAddress();

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void displayProfileImage() {

        imageRef = FirebaseDatabase.getInstance().getReference();

        final DatabaseReference displayUrl = imageRef.child("profileImages").child(user.getUserId());

        displayUrl.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    String url = snap.getValue()+"";

                    Picasso.get().load(url).into(profileImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getUserInfo() {
        nameTV.setText(user.getName());
        emailTV.setText(user.getEmail());
        contactTV.setText(user.getContact());
        bloodGroupTV.setText(user.getBloodGroup());
    }

    private void getUserCurrentAddress() {
        currentAddressRef = FirebaseDatabase.getInstance().getReference().child("address").child(user.getUserId());
        currentAddressRef.child("currentAddress").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    CurrentAddress currentAddressInfo = dataSnapshot.getValue(CurrentAddress.class);

                    currentDisTv.setText(currentAddressInfo.getDistrict());
                    currentAreaTV.setText(currentAddressInfo.getArea());
                    currentHouseNoTv.setText(currentAddressInfo.getHouseNo());
                    currentRoadNoTV.setText(currentAddressInfo.getRoadNo());
                    currentFlatNoTV.setText(currentAddressInfo.getFlatNo());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getUserPermanentAddress() {
        permanentAddressRef = FirebaseDatabase.getInstance().getReference().child("address").child(user.getUserId());
        permanentAddressRef.child("permanentAddress").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    PermanentAddress permanentAddressInfo = dataSnapshot.getValue(PermanentAddress.class);

                    pDivisionTV.setText(permanentAddressInfo.getDivision());
                    pDistrictTV.setText(permanentAddressInfo.getDistrict());
                    pUpazillaTV.setText(permanentAddressInfo.getUpazilla());
                    pVillageTV.setText(permanentAddressInfo.getVillage());
                    pPostOfficeTV.setText(permanentAddressInfo.getPost_office());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initView() {
        nameTV = findViewById(R.id.userNameTV);
        emailTV = findViewById(R.id.userEmail);
        contactTV = findViewById(R.id.userContact);
        bloodGroupTV = findViewById(R.id.userBloodGroup);
        currentDisTv = findViewById(R.id.cDistrict);
        currentAreaTV = findViewById(R.id.cArea);
        currentHouseNoTv = findViewById(R.id.cHouseNo);
        currentRoadNoTV = findViewById(R.id.cRoadNo);
        currentFlatNoTV = findViewById(R.id.cFlatNo);
        pDivisionTV = findViewById(R.id.pDivision);
        pDistrictTV = findViewById(R.id.pDistrict);
        pUpazillaTV = findViewById(R.id.pUpazilla);
        pVillageTV = findViewById(R.id.pVillage);
        pPostOfficeTV = findViewById(R.id.pPostOffice);
        profileImage = findViewById(R.id.previewImage);
        backArrow = findViewById(R.id.arrow);
    }
}
