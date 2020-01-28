package com.example.bloodbagbb.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bloodbagbb.Fragment.AddHistoryFragment;
import com.example.bloodbagbb.Model.History;
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

public class AddHistoryActivity extends AppCompatActivity {
    private EditText takeDate, location, description;
    String addingTime;
    private TextView btCancel, btAdd;
    private DatabaseReference historyRef;
    private History history;
    private FirebaseUser user;
    protected static TextView viewDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_history);
        inItView();
        final FragmentManager fm = getSupportFragmentManager();
        takeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDialogFragment newFragment = new AddHistoryFragment.DatePickerFragment();

                newFragment.show(fm, "datePicker");
            }
        });
        clickEvents();
    }

    private void clickEvents() {
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dDate = takeDate.getText().toString().trim();
                String dLocation = location.getText().toString().trim();
                String dDescription = description.getText().toString().trim();

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat myDateFormat = new SimpleDateFormat("hh:mm a");
                addingTime = myDateFormat.format(calendar.getTime());

                if (dDate.isEmpty()) {
                    takeDate.setError("Date is required!");
                    takeDate.requestFocus();
                    return;
                }
                if (dLocation.isEmpty()) {
                    location.setError("Location is required!");
                    location.requestFocus();
                    return;
                }
                if (dDescription.isEmpty()) {
                    description.setError("Description is required!");
                    description.requestFocus();
                    return;
                }

                storeHistoryData(dDate, dLocation, dDescription, addingTime);

                takeDate.setText("");
                location.setText("");
                description.setText("");

                Toast.makeText(AddHistoryActivity.this, "Time: " + addingTime , Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void storeHistoryData(final String dDate, final String dLocation,
                                  final String dDescription, final String addingTime) {

        user = FirebaseAuth.getInstance().getCurrentUser();
        historyRef = FirebaseDatabase.getInstance().getReference("history");
        String userId = user.getUid();
        String pushId = historyRef.push().getKey();

        history = new History(userId, pushId, addingTime, dDate, dLocation, dDescription);

        historyRef.child(userId).push().setValue(history).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(AddHistoryActivity.this, "Successful!", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddHistoryActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void inItView() {
        location = findViewById(R.id.locationET);
        description = findViewById(R.id.donationDescriptionEt);
        takeDate = findViewById(R.id.donationDateET);
        btAdd = findViewById(R.id.addHistoryTV);
        btCancel = findViewById(R.id.closeTV);
    }
    //DatePickerMethods
    @SuppressLint("ValidFragment")
    public static class DatePickerFragment extends AppCompatDialogFragment implements DatePickerDialog.OnDateSetListener {

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
            viewDate = getActivity().findViewById(R.id.donationDateET);
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
            viewDate.setText(df_medium_uk_str);
        }
    }
    //End of DatePickerMethods

}
