package com.example.cancerproject20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddPatient extends AppCompatActivity {

    EditText etHospital, etUsername, etId;
    TextView tvRedirectDoctorLogin;
    Button btEnter;

    String doctorHospital, doctorName;

    boolean isPatientReal;

    public Boolean validateUsername(){
        String val = etUsername.getText().toString();
        if(val.isEmpty())
        {
            etUsername.setError("username cant be empty");
            return false;
        }
        etUsername.setError(null);
        return true;
    }

    public Boolean validateId(){
        String val = etId.getText().toString();
        if(val.isEmpty())
        {
            etId.setError("Password cant be empty");
            return false;
        }
        etId.setError(null);
        return true;
    }

    public Boolean validateHospital(){
        String val = etHospital.getText().toString();
        if(val.isEmpty())
        {
            etHospital.setError("Hospital cant be empty");
            return false;
        }
        etHospital.setError(null);
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);
        btEnter = findViewById(R.id.BTAddEnter);
        etHospital = findViewById(R.id.ETAddHospital);
        etId = findViewById(R.id.ETAddId);
        etUsername = findViewById(R.id.ETAddUserName);
        tvRedirectDoctorLogin = findViewById(R.id.AddDoctorLogonRedirectText);


        //prevent visual bug
        etHospital.setError(null);
        etId.setError(null);
        etUsername.setError(null);

        Intent intent = getIntent();
        doctorHospital = intent.getStringExtra(DoctorControl.DOCTOR_HOSPITAL_KEY_ADD);
        doctorName = intent.getStringExtra(DoctorControl.DOCTOR_USER_KEY_ADD);

        btEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateUser();
            }
        });

        tvRedirectDoctorLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPatient.this, DoctorLogin.class);
                startActivity(intent);
            }
        });





    }

    public void validateUser()
    {
        isPatientReal = false;
        if(!(validateUsername() && validateHospital() && validateId()))
        {
            return;
        }

        String stHospital = etHospital.getText().toString().trim();
        String stUserName = etUsername.getText().toString().trim();
        String stId = etId.getText().toString().trim();

        FirebaseDatabase.getInstance().getReference().child("root").child("Hospital").child(stHospital)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(!snapshot.exists())
                        {
                            etHospital.setError("בית חולים לא קיים");
                            etHospital.requestFocus();
                            return;
                        }
                        FirebaseDatabase.getInstance().getReference().child("root").child("Hospital")
                                .child(stHospital).child("Patients").child(stUserName).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.exists())
                                        {
                                            if(stId.equals(snapshot.child("personal info").child("id").getValue(String.class)))
                                            {
                                                isPatientReal = true;
                                                addPatientToDoctor();
                                            }

                                            else
                                            {
                                                etId.setError("מספר זהות לא קיים");
                                                etId.requestFocus();
                                            }


                                        }

                                        else
                                        {
                                            etUsername.setError("שם משתמש שגוי");
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



    }

    public void addPatientToDoctor() {
        FirebaseDatabase.getInstance().getReference().child("root").child("Hospital").child(doctorHospital).child("Doctors")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!snapshot.exists())
                            return;


                        FirebaseDatabase database = FirebaseDatabase.getInstance();

                        Doctor doctor = snapshot.child(doctorName).getValue(Doctor.class);
                        doctor.addPatient(etUsername.getText().toString().trim());
                        DatabaseReference reference = database.getReference("/root/Hospital/" + doctorHospital + "/Doctors/" + doctorName);
                        reference.setValue(doctor);

                        Intent intent = new Intent(AddPatient.this, DoctorLogin.class);
                        startActivity(intent);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}