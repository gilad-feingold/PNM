package com.example.cancerproject20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class PatiantLogin extends AppCompatActivity  {



    public static final String USER_KEY = "USER_KEY";
    public static final String HOSPITAL_KEY = "HOSPITAL_KEY";


    EditText etPassword, etUserName, etHospital;
    Button btEnter;
    TextView etsignUpRedirectText, etDoctorRedirectText;

    boolean isHospitalReal;
    boolean isPasswordReal;
    boolean isUserNameReal;


    String stPassword, stUserName, stHospital;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patiant_login);

        // Trigger the alarm setup
        getPremonitions();


        etHospital = findViewById(R.id.EThospital);
        etPassword = findViewById(R.id.ETpassword);
        etUserName = findViewById(R.id.ETuserName);
        btEnter = findViewById(R.id.BTEnter);
        etsignUpRedirectText = findViewById(R.id.signUpRedirectText);
        etDoctorRedirectText = findViewById(R.id.doctorLogonRedirectText);

        etHospital.setError(null);
        etPassword.setError(null);
        etUserName.setError(null);

        btEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateHospital() && validatePassword() && validateUsername())
                    checkUser();
            }
        });

        etsignUpRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatiantLogin.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        etDoctorRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatiantLogin.this, DoctorLogin.class);
                startActivity(intent);

            }
        });
    }


    public Boolean validateUsername(){
        String val = etUserName.getText().toString();
        if(val.isEmpty())
        {
            etUserName.setError("username cant be empty");
            etUserName.requestFocus();
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
            etPassword.requestFocus();
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
            etHospital.requestFocus();
            return false;
        }
        etHospital.setError(null);
        return true;
    }

    public void checkUser(){        String userUsername = etUserName.getText().toString().trim();
        String userPassword = etPassword.getText().toString().trim();
        String userHospital = etHospital.getText().toString().trim();

        String passwordFromDataBase = null;

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
                                    .child(userHospital).child("Patients").child(userUsername).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()) {
                                                etUserName.setError(null);
                                                String passwordFromDB = snapshot.child("personal info").child("password").getValue(String.class);

                                                if (Objects.equals(passwordFromDB, userPassword)) {
                                                    etUserName.setError(null);
                                                    Intent intent = new Intent(PatiantLogin.this, MainActivity.class);
                                                    intent.putExtra(USER_KEY, userUsername);
                                                    intent.putExtra(HOSPITAL_KEY, userHospital);
                                                    startActivity(intent);
                                                }
                                                else {
                                                    etPassword.setError("סיסמא לא חוקית");
                                                    etPassword.requestFocus();
                                                }
                                            }

                                            else{
                                                etUserName.setError("משתמש לא קיים");
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



        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query checkUserDatabase = reference.child("users").orderByChild("username").equalTo(userUsername);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    etUserName.setError(null);
                    String passwordFromDB =  snapshot.child(userUsername).child("password").getValue(String.class);

                    if(Objects.equals(passwordFromDB, userPassword)){
                        etUserName.setError(null);
                        Intent intent = new Intent(PatiantLogin.this, MainActivity.class);
                        startActivity(intent);
                    }
                    else{
                        etPassword.setError("Invalid credentials");
                        etPassword.requestFocus();
                    }
                }
                else{
                    etUserName.setError("User dos not exist");
                    etUserName.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (requestCode == 101 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            new Utils().scheduleDailyReminder(this);
        }
    }

    private void getPremonitions()
    {
        Utils utils = new Utils();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(PatiantLogin.this, android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(PatiantLogin.this,
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 101);
            } else {
                utils.scheduleDailyReminder(PatiantLogin.this);
            }
        } else {
            utils.scheduleDailyReminder(PatiantLogin.this);
        }
    }

}