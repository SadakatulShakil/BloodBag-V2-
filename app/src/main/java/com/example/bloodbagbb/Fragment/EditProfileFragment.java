package com.example.bloodbagbb.Fragment;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bloodbagbb.Model.User;
import com.example.bloodbagbb.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment {

    private Context context;
    private Button btnUpdate;
    private TextView saveImageBtn;
    private ImageView backToParent, uploadImage;
    private CircleImageView previewImage;
    private EditText userNameTV, userBloodGrioupTV, userContactTV, userDistrictTV, userAreaTV, userEmailTV;
    private User user;
    private Uri imageUri;
    private StorageTask uploadTask;
    private String userId;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference donorRef;
    private StorageReference storageReference;
    FirebaseUser firebaseUser;
    public EditProfileFragment() {
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
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        donorRef = FirebaseDatabase.getInstance().getReference().child("donor");
        storageReference = FirebaseStorage.getInstance().getReference("UploadImage");

        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();

        inItView(view);

        retrieveUserInfo();

        clickEvents();

        displayProfileImage();

    }

    private void displayProfileImage() {

        final DatabaseReference displayUrl = donorRef.child("ProfileImages").child(userId);

        displayUrl.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    String url = snap.getValue()+"";
                    Log.d(TAG, "proImageUrl: " + url);

                    Picasso.get().load(url).into(previewImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void clickEvents() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Successfully Updated!", Toast.LENGTH_LONG).show();
                updateUserInfo();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayoutID, new ViewProfileFragment())
                        .commit();
            }
        });

        backToParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayoutID, new ViewProfileFragment())
                        .commit();
            }
        });

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        saveImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadTask != null && uploadTask.isInProgress()) {
                    Toast.makeText(context, "Uploading is Pending !", Toast.LENGTH_SHORT).show();
                } else {
                    uploadImageToStorage();
                }
            }
        });
    }

    private void openFileChooser() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(previewImage);
        }
    }

    public String getFileExtension(Uri imageUri) {
        ContentResolver contentResolver = context.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUri));
    }

    private void uploadImageToStorage() {
        final StorageReference storeRef = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
        storeRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(context, "Successfully stored this Image !", Toast.LENGTH_SHORT).show();

                        storeRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String upId = donorRef.push().getKey();
                                donorRef.child("ProfileImages").child(userId).child(upId).setValue(uri.toString());
                                //Picasso.get().load(uri.toString()).into(dUserImage);
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                        Toast.makeText(context, "Successfully NOT stored this Image !", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void updateUserInfo() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        String userID = firebaseUser.getUid();
        donorRef = fdb.getReference("donor");

        String newName = userNameTV.getText().toString().trim();
        String newEmail = userEmailTV.getText().toString().trim();
        String newBloodGroup = userBloodGrioupTV.getText().toString().trim();
        String newDistrict = userDistrictTV.getText().toString().trim();
        String newArea = userAreaTV.getText().toString().trim();
        String newContact = userContactTV.getText().toString().trim();

        User updateUser = new User(userID, newName, newEmail, newContact, newDistrict, newArea, newBloodGroup);

        donorRef.child(userID).setValue(updateUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                {
                    Toast.makeText(context, "Successfully Updated!", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    private void retrieveUserInfo() {

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        String userID = firebaseUser.getUid();
        donorRef = fdb.getReference("donor");
        donorRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    user = dataSnapshot.getValue(User.class);
                    Log.d(TAG, "onDataChange: " + user.toString());

                    userNameTV.setText(user.getName());
                    userBloodGrioupTV.setText(user.getBloodGroup());
                    userContactTV.setText(user.getContact());
                    userEmailTV.setText(user.getEmail());
                    userDistrictTV.setText(user.getDistrict());
                    userAreaTV.setText(user.getArea());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void inItView(View view) {
        btnUpdate = view.findViewById(R.id.upDateBTN);
        userNameTV = view.findViewById(R.id.userName);
        userEmailTV = view.findViewById(R.id.userEmail);
        userBloodGrioupTV = view.findViewById(R.id.userBloodGroup);
        userDistrictTV = view.findViewById(R.id.userDistrict);
        userAreaTV = view.findViewById(R.id.userArea);
        userContactTV = view.findViewById(R.id.userContact);
        backToParent = view.findViewById(R.id.arrow);
        uploadImage = view.findViewById(R.id.uploadImage);
        previewImage = view.findViewById(R.id.previewImage);
        saveImageBtn = view.findViewById(R.id.saveImage);
    }
}

