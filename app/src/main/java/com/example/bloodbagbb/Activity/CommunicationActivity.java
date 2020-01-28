package com.example.bloodbagbb.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bloodbagbb.Model.User;
import com.example.bloodbagbb.R;

public class CommunicationActivity extends AppCompatActivity {

    public static final String TAG= "Communication";
    private TextView comNameTV, comEmailTV, comAddressTV, comContact;
    private User user;
    private Button comMessageBt, comCallBt;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication);

        intItView();
        Intent intent = getIntent();

        user = (User) intent.getSerializableExtra("donorInfo");
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
        back = findViewById(R.id.arrow);
    }
}
