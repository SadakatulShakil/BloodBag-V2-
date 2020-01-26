package com.example.bloodbagbb.Fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bloodbagbb.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePasswordFragment extends Fragment {

    private Context context;
    ImageView backTo;
    private EditText oldPasswordET, newPasswordET, confirmPasswordET;
    private Button updatePassword;
    private FirebaseAuth firebaseAuth;

    public ChangePasswordFragment() {
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
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inItView(view);

        firebaseAuth = FirebaseAuth.getInstance();
        updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });

        backTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayoutID, new SettingsFragment())
                        .commit();
            }
        });
    }

    private void changePassword() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user!=null){
            if(newPasswordET.getText().toString().trim() .equals(confirmPasswordET.getText().toString().trim())){
                user.updatePassword(newPasswordET.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(context, "Password Changed Successfully !",Toast.LENGTH_SHORT).show();
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.frameLayoutID, new SuccessfullMessageFragment())
                                    .commit();
                    }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(context, e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
            else{
                Toast.makeText(context, "Password is not Matching",Toast.LENGTH_SHORT).show();
            }

        }

    }

    private void inItView(View view) {

        oldPasswordET = view.findViewById(R.id.oldPassword);
        newPasswordET = view.findViewById(R.id.newPassword);
        confirmPasswordET = view.findViewById(R.id.confirmPassword);
        backTo = view.findViewById(R.id.arrow);
        updatePassword = view.findViewById(R.id.changeBTN);
    }
}
