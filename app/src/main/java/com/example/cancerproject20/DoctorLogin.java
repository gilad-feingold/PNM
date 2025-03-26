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

import java.util.Objects;

public class DoctorLogin extends AppCompatActivity {




    public static final String DOCTOR_USER_KEY = "USER_KEY";
    public static final String DOCTOR_HOSPITAL_KEY = "HOSPITAL_KEY";


    EditText etPassword, etUserName, etHospital;
    Button btEnter;
    TextView etDoctorRedirectText;

    boolean isHospitalReal;
    boolean isPasswordReal;
    boolean isUserNameReal;


    String stPassword, stUserName, stHospital;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);




        etHospital = findViewById(R.id.DoctorEThospital);
        etPassword = findViewById(R.id.DoctorETpassword);
        etUserName = findViewById(R.id.DoctorETuserName);
        btEnter = findViewById(R.id.DoctorBTEnter);
        etDoctorRedirectText = findViewById(R.id.DoctorLogonRedirectText);

        btEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateHospital() && validatePassword() && validateUsername())
                    tryLogAdmin();
            }
        });


        etDoctorRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorLogin.this, PatiantLogin.class);
                startActivity(intent);

            }
        });
    }


    public Boolean validateUsername(){
        String val = etUserName.getText().toString();
        if(val.isEmpty())
        {
            etUserName.setError("username cant be empty");
            return false;
        }
        etUserName.setError(null);
        return true;
    }

    public Boolean validatePassword(){
        String val = etPassword.getText().toString();
        if(val.isEmpty())
        {
            etPassword.setError("Password cant be empty");
            return false;
        }
        etPassword.setError(null);
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


    public void tryLogAdmin()
    {
        String doctorUsername = etUserName.getText().toString().trim();
        String userPassword = etPassword.getText().toString().trim();


        String passwordFromDataBase = null;

        DatabaseReference hospitalDatabaseReference = FirebaseDatabase.getInstance().getReference();
        hospitalDatabaseReference.child("root").child("Admins").child(doctorUsername)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {

                            etUserName.setError(null);
                            String passwordFromDB = snapshot.child("password").getValue(String.class);

                            if (Objects.equals(passwordFromDB, userPassword)) {
                                etUserName.setError(null);
                                Intent intent = new Intent(DoctorLogin.this, AdminControl.class);
                                startActivity(intent);
                            }
                            else {
                                etPassword.setError("סיסמא לא נכונה");
                                etPassword.requestFocus();
                            }
                        }

                        else{
                            etUserName.setError("רופא לא קיים במערכת");
                            etUserName.requestFocus();
                        }

                        logDoctor();

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        logDoctor();
                    }
                });

    }





    public void logDoctor(){
        String doctorUsername = etUserName.getText().toString().trim();
        String userPassword = etPassword.getText().toString().trim();
        String userHospital = etHospital.getText().toString().trim();

        // prevent a visual bug in the system
        etUserName.setError(null);
        etPassword.setError(null);
        etHospital.setError(null);


        String passwordFromDataBase = null;
        etUserName.setError(null);
        DatabaseReference hospitalDatabaseReference = FirebaseDatabase.getInstance().getReference();
        hospitalDatabaseReference.child("root").child("Hospital").child(userHospital)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {
                            etHospital.setError(null);
                            DatabaseReference userDatabasereference = FirebaseDatabase.getInstance().getReference();

                            userDatabasereference.child("root").child("Hospital")
                                    .child(userHospital).child("Doctors").child(doctorUsername).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()) {
                                                etUserName.setError(null);
                                                String passwordFromDB = snapshot.child("password").getValue(String.class);

                                                if (Objects.equals(passwordFromDB, userPassword)) {
                                                    etUserName.setError(null);
                                                    Intent intent = new Intent(DoctorLogin.this, DoctorControl.class);
                                                    intent.putExtra(DOCTOR_USER_KEY, doctorUsername);
                                                    intent.putExtra(DOCTOR_HOSPITAL_KEY, userHospital);
                                                    startActivity(intent);
                                                }
                                                else {
                                                    etPassword.setError("Invalid credentials");
                                                    etPassword.requestFocus();
                                                }
                                            }

                                            else{
                                                etUserName.setError("רופא לא קיים במערכת");
                                                etUserName.requestFocus();
                                            }

                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });


                        }

                        else {
                            etHospital.setError("בית חולים לא קיים");
                            etHospital.requestFocus();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        return;
                    }
                });





    }

}

