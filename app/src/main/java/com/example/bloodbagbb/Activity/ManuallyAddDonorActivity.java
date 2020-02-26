package com.example.bloodbagbb.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.bloodbagbb.Model.User;
import com.example.bloodbagbb.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ManuallyAddDonorActivity extends AppCompatActivity {
    private ImageView backArrow;
    private EditText nameET, emailET, areaET, contactET;
    private AutoCompleteTextView bloodGroupET, districtET;
    private Button manuallyAddBtn;
    public static final String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manually_add_donor);

        initView();
        bloodGroupET.setThreshold(1);
        bloodGroupET.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.bloodGroup)));

        districtET.setThreshold(1);
        districtET.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.districts)));

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManuallyAddDonorActivity.this, AdminActivity.class);
                startActivity(intent);
                finish();
            }
        });

        manuallyAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beforeAdding();
            }
        });
    }

    private void beforeAdding() {
        String nameId = nameET.getText().toString().trim();
        String emailId = emailET.getText().toString().trim();
        String bloodGroupId = bloodGroupET.getText().toString().trim();
        String districtId = districtET.getText().toString().trim();
        String areaId = areaET.getText().toString().trim();
        String contactId = contactET.getText().toString().trim();

        if (nameId.isEmpty()) {
            nameET.setError("Name is rerquired!");
            nameET.requestFocus();
            return;
        }

        if (emailId.isEmpty()) {
            emailET.setError("Email is rerquired!");
            emailET.requestFocus();
            return;
        }

        if (bloodGroupId.isEmpty()) {
            bloodGroupET.setError("Blood Group is rerquired!");
            bloodGroupET.requestFocus();
            return;
        }

        if (districtId.isEmpty()) {
            districtET.setError("District is rerquired!");
            districtET.requestFocus();
            return;
        }

        if (areaId.isEmpty()) {
            areaET.setError("Area is rerquired!");
            areaET.requestFocus();
            return;
        }

        if (contactId.isEmpty()) {
            contactET.setError("Contact is rerquired!");
            contactET.requestFocus();
            return;
        }

        if (!emailId.matches(EMAIL_PATTERN)) {
            emailET.setError("Invalid email");
            emailET.requestFocus();
            return;
        }
        signUp(nameId, emailId, contactId, districtId, areaId, bloodGroupId);
    }

    private void signUp(final String nameId, final String emailId, final String contactId, final String districtId,
                        final String areaId, final String bloodGroupId) {
        progressBar.setVisibility(View.VISIBLE);
        String userId = databaseReference.push().getKey();
        User user = new User(userId, nameId, emailId, contactId, districtId, areaId, bloodGroupId);
        DatabaseReference userRef = databaseReference.child("donor").child(userId);
        userRef.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ManuallyAddDonorActivity.this, "Successfully Sign up!", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    Intent intent = new Intent(ManuallyAddDonorActivity.this, AdminActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(ManuallyAddDonorActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);

                }
            }
        });

    }


    private void initView() {
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        backArrow = findViewById(R.id.arrow);
        nameET = findViewById(R.id.userName);
        emailET = findViewById(R.id.userEmail);
        bloodGroupET = findViewById(R.id.userBloodGroup);
        districtET = findViewById(R.id.userDistrict);
        areaET = findViewById(R.id.userArea);
        contactET = findViewById(R.id.userContact);
        manuallyAddBtn = findViewById(R.id.addDonorBTN);

        progressBar = findViewById(R.id.progressBar);
    }
}
