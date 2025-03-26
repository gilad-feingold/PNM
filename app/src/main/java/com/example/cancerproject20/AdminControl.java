package com.example.cancerproject20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminControl extends AppCompatActivity implements View.OnClickListener {

    public boolean exitFlag;

    protected static final Object lock = new Object();

    boolean isValInDataBase;
    EditText signupFullName, signupHospital, signupPassword, signupId;
    TextView LoginRedirectText;
    Button signupButton;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_control);

        signupFullName = findViewById(R.id.doctor_signup_name);
        signupHospital = findViewById(R.id.doctor_signup_hospital);
        signupPassword = findViewById(R.id.doctor_signup_password);
        signupId = findViewById(R.id.doctor_signup_id);
        LoginRedirectText = findViewById(R.id.doctor_loginRedirectText);
        signupButton = findViewById(R.id.doctor_signup_button);

        //prevent visual bug
        signupFullName.setError(null);
        signupHospital.setError(null);
        signupPassword.setError(null);
        signupId.setError(null);

        LoginRedirectText.setOnClickListener(this);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                String name = signupFullName.getText().toString();
                String Hospital = signupHospital.getText().toString();
                String password = signupPassword.getText().toString();
                String stId = signupId.getText().toString();
                if (stId.equals("") || !Utils.isPositiveInteger(stId)) {
                    signupId.setError("נחוץ מספר חיובי");
                    signupId.requestFocus();
                    return;
                }



                Utils.isInDataBase(Hospital, "/root/Hospital", exist -> {
                    if (!exist) {
                        signupHospital.setError("בית חולים לא קיים");
                        signupHospital.requestFocus();
                        return;
                    } else {


                        if (name.equals("")) {
                            signupFullName.setError("שם לא יכול להיות ריק");
                            signupFullName.requestFocus();
                            return;
                        }
                        performSignup(Hospital, stId, name, password);


                    }
                });
            }
        });
    }


    public void performSignup(String Hospital, String Id, String name, String password) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("/root/Hospital/" + Hospital + "/Doctors/" + name);
        Doctor d = new Doctor(Id, name, password, Hospital);
        reference.setValue(d);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(AdminControl.this, PatiantLogin.class);
        startActivity(intent);
    }
}
