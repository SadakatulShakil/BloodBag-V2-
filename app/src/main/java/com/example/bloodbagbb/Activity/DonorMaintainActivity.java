package com.example.bloodbagbb.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bloodbagbb.Model.User;
import com.example.bloodbagbb.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DonorMaintainActivity extends AppCompatActivity {

    public static final String TAG= "Communication";
    private TextView comNameTV, comEmailTV, comAddressTV, comContact;
    private User user;
    private DatabaseReference databaseReference;
    private Button comMessageBt, comCallBt, comRemove;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintain_donor);

        intItView();
        Intent intent = getIntent();


        user = (User) intent.getSerializableExtra("donorInfo");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("donor");

        Log.d(TAG, "onCreate: " +user.toString());
        if(user!=null){
            comNameTV.setText(user.getName());
            comEmailTV.setText(user.getEmail());
            comAddressTV.setText(user.getArea()+","+user.getDistrict());
            comContact.setText(user.getContact());}

        comCallBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+Uri.encode(user.getContact()))));
            }
        });

        comMessageBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto: "+ Uri.encode(user.getContact()))));

            }
        });

        comRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(user.getUserId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(DonorMaintainActivity.this, "Successfully deleted user!", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    }
                });
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void intItView() {

        comNameTV = findViewById(R.id.detailsWithName);
        comEmailTV = findViewById(R.id.detailsWithEmail);
        comAddressTV = findViewById(R.id.detailsWithAddress);
        comContact = findViewById(R.id.detailsWithContact);

        comMessageBt = findViewById(R.id.messageBT);
        comCallBt = findViewById(R.id.callBT);
        comRemove = findViewById(R.id.removeBT);
        back = findViewById(R.id.arrow);
    }
}
