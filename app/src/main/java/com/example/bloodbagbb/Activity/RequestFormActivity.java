package com.example.bloodbagbb.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bloodbagbb.Fragment.ForRequestFragment;
import com.example.bloodbagbb.Fragment.RequestFormFragment;
import com.example.bloodbagbb.Model.BloodRequest;
import com.example.bloodbagbb.Model.Utils;
import com.example.bloodbagbb.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RequestFormActivity extends AppCompatActivity {
    private RadioGroup rgType;
    String postingTime;
    private EditText pickDate2, userArea, description, number;
    private AutoCompleteTextView expectedBG, userDistrict;
    private TextView btCancel, btPost;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference requestRef;
    private BloodRequest bloodRequest;
    private FirebaseUser user;
    protected static TextView startDate, endDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_form);
        inItView();

        final FragmentManager fm = getSupportFragmentManager();

        expectedBG.setThreshold(1);
        expectedBG.setAdapter(new ArrayAdapter<>(RequestFormActivity.this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.bloodGroup)));

        userDistrict.setThreshold(1);
        userDistrict.setAdapter(new ArrayAdapter<>(RequestFormActivity.this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.districts)));

        pickDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create the datePickerFragment
                AppCompatDialogFragment newFragment = new RequestFormFragment.DatePickerFragment1();

                newFragment.show(fm, "datePicker");

            }
        });
        clickEvents();
    }

    private void clickEvents() {
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Fragment fragment = new ForRequestFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                Log.d(TAG, "onClick: " + Utils.bucketFragment.toString());
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, fragment)
                        .commit();*/
                onBackPressed();
            }
        });

        btPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date2 = pickDate2.getText().toString().trim();
                String bGroup = expectedBG.getText().toString().trim();
                String contact = number.getText().toString().trim();
                String district = userDistrict.getText().toString().trim();
                String area = userArea.getText().toString().trim();
                String reason = description.getText().toString().trim();


                RadioButton rb = (RadioButton) rgType.findViewById(rgType.getCheckedRadioButtonId());
                String typeOfNeed = (String) rb.getText();

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat myDateFormat = new SimpleDateFormat("hh:mm a");
                postingTime = myDateFormat.format(calendar.getTime());

                if (date2.isEmpty()) {
                    pickDate2.setError("End date is required!");
                    pickDate2.requestFocus();
                    return;
                }
                if (bGroup.isEmpty()) {
                    expectedBG.setError("blood group is required!");
                    expectedBG.requestFocus();
                    return;
                }
                if (contact.isEmpty()) {
                    number.setError("contact is required!");
                    number.requestFocus();
                    return;
                }
                if (district.isEmpty()) {
                    userDistrict.setError("district is required!");
                    userDistrict.requestFocus();
                    return;
                }
                if (area.isEmpty()) {
                    userArea.setError("area is required!");
                    userArea.requestFocus();
                    return;
                }
                if (reason.isEmpty()) {
                    description.setError("Give a reason is required!");
                    description.requestFocus();
                    return;
                }

                storeRequestData(date2, bGroup, contact, district, area, typeOfNeed, reason, postingTime);

                pickDate2.setText("");
                expectedBG.setText("");
                number.setText("");
                userDistrict.setText("");
                userArea.setText("");
                description.setText("");

                Toast.makeText(RequestFormActivity.this, "Type :" + typeOfNeed + " " + "Time: " + postingTime, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void storeRequestData(final String date2, final String bGroup, final String contact,
                                  final String district, final String area, final String typeOfNeed,
                                  final String reason, final String postingTime) {


        user = FirebaseAuth.getInstance().getCurrentUser();
        requestRef = FirebaseDatabase.getInstance().getReference("requests");
        String userId = user.getUid();
        String pushId = requestRef.push().getKey();
        bloodRequest = new BloodRequest(userId, pushId, date2,
                bGroup, contact, district, area, typeOfNeed, reason, postingTime);

        requestRef.child(pushId).setValue(bloodRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RequestFormActivity.this, "Successful!", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RequestFormActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void inItView() {

        pickDate2 = findViewById(R.id.dateET);
        expectedBG = findViewById(R.id.userBloodGroup);
        number = findViewById(R.id.contactET);
        userDistrict = findViewById(R.id.locationEt);
        userArea = findViewById(R.id.areaET);
        description = findViewById(R.id.descriptionET);
        btCancel = findViewById(R.id.cancelTV);
        btPost = findViewById(R.id.postTV);
        rgType = findViewById(R.id.typeOfBloodNeed);
    }

    //DatePickerMethods
    @SuppressLint("ValidFragment")
    public static class DatePickerFragment1 extends AppCompatDialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                    AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);
            return dpd;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the chosen date
            endDate = getActivity().findViewById(R.id.dateET);
           /* int actualMonth = month+1; // Because month index start from zero
            // Display the unformatted date to TextView
            tvDate.setText("Year : " + year + ", Month : " + actualMonth + ", Day : " + day + "\n\n");*/

            // Create a Date variable/object with user chosen date
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(0);
            cal.set(year, month, day, 0, 0, 0);
            Date chosenDate = cal.getTime();

            // Format the date using style medium and UK locale
            DateFormat df_medium_uk = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.UK);
            String df_medium_uk_str = df_medium_uk.format(chosenDate);
            // Display the formatted date
            endDate.setText(df_medium_uk_str);
        }
    }
    //End of DatePickerMethods
}
