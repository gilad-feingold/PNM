package com.example.cancerproject20;

import static com.example.cancerproject20.Utils.dosExist;
import static com.example.cancerproject20.Utils.isInDataBase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    public boolean exitFlag;

    protected static final Object lock = new Object();

    boolean isValInDataBase;
    EditText signupFullName, signupHospital, signupUsername, signupPassword, signupAge, signupId;
    TextView LoginRedirectText;
    Button signupButton;
    FirebaseDatabase database;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupFullName = findViewById(R.id.signup_name);
        signupHospital = findViewById(R.id.signup_hospital);
        signupUsername = findViewById(R.id.signup_username);
        signupAge = findViewById(R.id.signup_Age);
        signupPassword = findViewById(R.id.signup_password);
        signupId = findViewById(R.id.signup_id);
        LoginRedirectText = findViewById(R.id.loginRedirectText);
        signupButton = findViewById(R.id.signup_button);

        LoginRedirectText.setOnClickListener(this);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");
                String name = signupFullName.getText().toString();
                String Hospital = signupHospital.getText().toString();
                String username = signupUsername.getText().toString();
                String password = signupPassword.getText().toString();
                String stId = signupId.getText().toString();
                String stAge = signupAge.getText().toString();
                if (stId.equals("") || !Utils.isPositiveInteger(stId)) {
                    signupId.setError("נחוץ מספר חיובי");
                    signupId.requestFocus();
                    return;
                }

                if (stAge.equals("") || !Utils.isPositiveInteger(stAge)) {
                    signupAge.setError("נחוץ מספר חיובי");
                    signupAge.requestFocus();
                    return;
                }


                Utils.isInDataBase(Hospital, "/root/Hospital", exist -> {
                    if (!exist) {
                        signupHospital.setError("בית חולים לא קיים");
                        signupHospital.requestFocus();
                        return;
                    } else {
                        if (username.equals("") || isValInDataBase) {
                            signupUsername.setError("שם משתמש תפוס");
                            signupUsername.requestFocus();
                            return;
                        }

                        if (name.equals("")) {
                            signupFullName.setError("שם לא יכול להיות ריק");
                            signupFullName.requestFocus();
                            return;
                        }

                        exitFlag = true;
                        database = FirebaseDatabase.getInstance();
                        Utils.checkPersonalInfo(signupId, signupUsername, Hospital, stId, username, database, exists -> {
                            if (exists) {
                                signupId.setError("מספר זהות כבר קיים במערכת");
                                signupId.requestFocus();
                                return;
                            } else {
                                performSignup(Hospital, stAge, stId, name, username, password);

                            }
                        });

                    }


                });

            }
        });


    }


    public void performSignup(String Hospital, String Age, String Id, String name, String username, String password) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("/root/Hospital/" + Hospital + "/Patients/" + username + "/personal info");
        Patient p = new Patient(Age, Id, name, password, Hospital);
        reference.setValue(p);

        Intent intent = new Intent(SignupActivity.this, PatiantLogin.class);
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(SignupActivity.this, PatiantLogin.class);
        startActivity(intent);
    }
}
 