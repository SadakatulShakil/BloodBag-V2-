package com.example.bloodbagbb.Fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bloodbagbb.Model.User;
import com.example.bloodbagbb.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class GiveFeedBackFragment extends Fragment {

    private Context context;
    private EditText feedText;
    private Button sendBtn;
    private String userId;
    private User user;
    private DatabaseReference feedBackRef;
    private DatabaseReference donorRef;
    private FirebaseAuth firebaseAuth;
    public GiveFeedBackFragment() {
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
        return inflater.inflate(R.layout.fragment_give_feed_back, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        clickEvents();

        userData();

    }

    private void userData() {
        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();
        donorRef = FirebaseDatabase.getInstance().getReference().child("donor");

        donorRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    user = dataSnapshot.getValue(User.class);
                    Log.d(TAG, "onDataChange: " + user.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void clickEvents() {

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "FeedBAck is Send Successfully!", Toast.LENGTH_SHORT).show();
                String informationText = feedText.getText().toString().trim();
                if (informationText.isEmpty()) {
                    feedText.setError("This Field is required!");
                    feedText.requestFocus();
                    return;
                }

                firebaseAuth = FirebaseAuth.getInstance();
                userId = firebaseAuth.getCurrentUser().getUid();
                User userFeedBack = new User(userId,user.getName(), user.getContact(), informationText);
                feedBackRef = FirebaseDatabase.getInstance().getReference().child("feedback");
                String pushId = feedBackRef.push().getKey();

                        feedBackRef.child(pushId).setValue(userFeedBack).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Successfully Send !", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                feedText.setText("");
            }
        });
    }

    private void initView(View view) {
        feedText = view.findViewById(R.id.feedBackText);
        sendBtn = view.findViewById(R.id.sendFeedBack);
    }
}
