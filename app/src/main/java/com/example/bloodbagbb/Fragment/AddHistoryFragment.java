package com.example.bloodbagbb.Fragment;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class AddHistoryFragment extends Fragment {
    private Context context;
    private EditText takeDate, location, description;
    String addingTime;
    private TextView btCancel, btAdd;
    private DatabaseReference historyRef;
    private History history;
    private FirebaseUser user;
    protected static TextView viewDate;
    public AddHistoryFragment() {
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
        return inflater.inflate(R.layout.fragment_add_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final FragmentManager fm = (getActivity()).getSupportFragmentManager();
        inItView(view);

        takeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDialogFragment newFragment = new DatePickerFragment();

                newFragment.show(fm, "datePicker");
            }
        });
        clickEvents();
    }

    private void clickEvents() {

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dDate = viewDate.getText().toString().trim();
                String dLocation = location.getText().toString().trim();
                String dDescription = description.getText().toString().trim();

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat myDateFormat = new SimpleDateFormat("hh:mm a");
                addingTime = myDateFormat.format(calendar.getTime());

                Toast.makeText(context, "Time: " + addingTime , Toast.LENGTH_SHORT).show();

                if (dDate.isEmpty()) {
                    viewDate.setError("Date is required!");
                    viewDate.requestFocus();
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
                viewDate.setText("");
                location.setText("");
                description.setText("");
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
                    Toast.makeText(context, "Successful!", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void inItView(View view) {
        location = view.findViewById(R.id.locationET);
        description = view.findViewById(R.id.donationDescriptionEt);
        takeDate = view.findViewById(R.id.donationDateET);
        btAdd = view.findViewById(R.id.addHistoryTV);
        btCancel = view.findViewById(R.id.closeTV);
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
