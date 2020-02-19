package com.example.bloodbagbb.Fragment;


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
import android.widget.Button;
import android.widget.TextView;

import com.example.bloodbagbb.Model.User;
import com.example.bloodbagbb.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResponseFeedBackFragment extends Fragment {
    private User user;
    private TextView userName, userContact, userFeedback;
    private Button callUser, messageUser;
    private static final String TAG = "ResponseFeedBackFragmen";
    public ResponseFeedBackFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_response_feed_back, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        getDataList();

        callUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+Uri.encode(user.getContact()))));
            }
        });

        messageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto: "+ Uri.encode(user.getContact()))));
            }
        });
    }

    private void getDataList() {

        Bundle bundle = getArguments();
        user = (User) bundle.getSerializable("dataList");
        Log.d(TAG, "getDataList: " + user.toString());
        String name = user.getName();
        String contact = user.getContact();
        String feedBack = user.getFeedBack();

        userName.setText(name);
        userContact.setText(contact);
        userFeedback.setText(feedBack);

    }

    private void initView(View view) {
        userName = view.findViewById(R.id.detailsWithName);
        userContact = view.findViewById(R.id.detailsWithContact);
        userFeedback = view.findViewById(R.id.detailsWithFeedBack);
        callUser = view.findViewById(R.id.callBT);
        messageUser = view.findViewById(R.id.messageBT);

    }
}
