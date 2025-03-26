package com.example.cancerproject20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DoctorControl extends AppCompatActivity {

    String selectedPatient, selectedDate;
    Spinner patients, dates;
    String doctorHospital, doctorName;
    Doctor doctor;
    TextView info;

    Button btAdd;


    String formattedSideEffect;

    public static final String DOCTOR_USER_KEY_ADD = "USER_KEY_ADD";
    public static final String DOCTOR_HOSPITAL_KEY_ADD = "HOSPITAL_KEY_ADD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_control);

        patients = findViewById(R.id.spPatients);
        dates = findViewById(R.id.spDate);
        info = findViewById(R.id.tvFormattedInfo);
        btAdd = findViewById(R.id.Addbtn);
        dates.setEnabled(false);

        // get doctor login info
        Intent intent= getIntent();
        doctorHospital = intent.getStringExtra(DoctorLogin.DOCTOR_HOSPITAL_KEY);
        doctorName = intent.getStringExtra(DoctorLogin.DOCTOR_USER_KEY);

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorControl.this, AddPatient.class);
                intent.putExtra(DOCTOR_HOSPITAL_KEY_ADD, doctorHospital);
                intent.putExtra(DOCTOR_USER_KEY_ADD, doctorName);
                startActivity(intent);
            }
        });


        // get doctor
        FirebaseDatabase.getInstance().getReference("root")
                .child("Hospital").child(doctorHospital).child("Doctors").child(doctorName)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {
                            doctor = snapshot.getValue(Doctor.class);
                            doctor.addPatient("gol");

                            List<String> doctorPatients = doctor.getPatients();
                            String[] vals = new String[doctorPatients.size()];
                            Utils.listToArray(doctorPatients, vals);
                            ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(DoctorControl.this, R.layout.simple_spinner_item, vals);
                            adapter.setDropDownViewResource(R.layout.simple_spinner_item);
                            patients.setAdapter(adapter);
                            adapter.setDropDownViewResource(R.layout.simple_spinner_item);
                            patients.setAdapter(adapter);

                            patients.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    dates.setEnabled(false);

                                    String[] strarr = {""};
                                    ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(DoctorControl.this, R.layout.simple_spinner_item, strarr);
                                    adapter.setDropDownViewResource(R.layout.simple_spinner_item);
                                    dates.setAdapter(adapter);

                                    info.setText("");

                                    selectedPatient = parent.getItemAtPosition(position).toString();
                                    Toast.makeText(DoctorControl.this, "Selected: " + selectedPatient, Toast.LENGTH_SHORT).show();
                                    addDates(selectedPatient, doctorHospital);

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                            dates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    selectedDate = parent.getItemAtPosition(position).toString();
                                    Toast.makeText(DoctorControl.this, "Selected: " + selectedDate, Toast.LENGTH_SHORT).show();
                                    showPatientInfo(selectedPatient, doctorHospital, selectedDate);

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }

    public void addDates(String userName, String hospital)
    {
        FirebaseDatabase.getInstance().getReference()
                .child("root").child("Hospital").child(hospital).child("Patients").child(userName).child("side effects")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            ArrayList<String> arrayList = new ArrayList<>();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                String value = dataSnapshot.getKey();
                                arrayList.add(value);
                            }


                            String[] vals = new String[arrayList.size()];
                            Utils.listToArray(arrayList, vals);
                            ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(DoctorControl.this, R.layout.simple_spinner_item, vals);
                            adapter.setDropDownViewResource(R.layout.simple_spinner_item);
                            dates.setAdapter(adapter);

                            dates.setEnabled(true);


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



    }


    public void showPatientInfo(String userName, String hospital, String date)
    {
        FirebaseDatabase.getInstance().getReference()
                .child("root").child("Hospital").child(hospital).child("Patients").child(userName)
                .child("side effects").child(date)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.child("m_effects").exists())
                        {
                            SideEffects side = snapshot.getValue(SideEffects.class);
                            info.setText(side.ToSting() + "\n\n\n\n"); //   make the values not clip the bottom of the screen
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}