package com.example.cancerproject20;

import static android.app.PendingIntent.getActivity;

import static com.example.cancerproject20.Patient.addPatient;
import static com.example.cancerproject20.SideEffects.addSideEffects;
import static com.example.cancerproject20.SpinnerClassHandlers.classicSpinnerValues;
import static com.example.cancerproject20.SpinnerClassHandlers.spinner2Values;

import static com.example.cancerproject20.SpinnerClassHandlers.spinner1Values;
import static com.example.cancerproject20.SpinnerClassHandlers.spinner3Values;
import com.example.cancerproject20.SpinnerClassHandlers.SpinnerHandle;
import com.google.firebase.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Spinner spinner1, spinner2, spinner3, spinner4, spinner5, spinner6, spinner7, spinner8, spinner9, spinner10,
            spinner11, spinner12, spinner13, spinner14, spinner15, spinner16, spinner17, spinner18, spinner19, spinner20,
            spinner21, spinner22, spinner23, spinner24, spinner25, spinner26, spinner27, spinner28, spinner29, spinner30;



    Map<String, Object> user;


    FirebaseDatabase database;
    DatabaseReference reference;

    static int counter = 0;

    String[] allFields = {
            "שיעול", "כאבי ראש", "קשיים בבליעה", "עייפות", "חולשה", "סחרחורות", "בחילה", "הקאות", "כאבים בחזה", "רגישות בעור",
            "נשירת שיער", "קוצר נשימה", "אובדן תיאבון", "שלשול", "פצעים בפה", "בעיות שמיעה", "בעיות בראייה", "חום", "צמרמורות", "צמאון",
            "מתיקות בפה", "ירידה במשקל", "תאבון קיצוני", "שתן כהה", "צואה בהירה", "קשיי שינה", "ירידה במצב הרוח", "נימול או עקצוץ ברגליים", "שינויי פיגמנטציה בעור", "רעד"
    };


    SpinnerHandle[] allSpinnerHandles = new SpinnerHandle[30];

    Button btSend;

    public void set_Spinner(Spinner spinner, String[] values, String field)
    {
        String[] newValues = new String[values.length+1];
        if(counter >= allSpinnerHandles.length)
            return;
        allSpinnerHandles[counter] = new SpinnerHandle(newValues);
        counter++;
        Utils.insertValueAtStartOfArray(values, newValues, field);
        ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(this, R.layout.simple_spinner_item, newValues );
        langAdapter.setDropDownViewResource(R.layout.simple_spinner_item);
        spinner.setAdapter(langAdapter);

    }

    public void makeSpinner(Spinner spin, int handleIndex, String field)
    {
        set_Spinner(spin, classicSpinnerValues, field);
        spin.setOnItemSelectedListener(allSpinnerHandles[handleIndex]);


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner1 = (Spinner) findViewById(R.id.sp1);
        spinner2 = (Spinner) findViewById(R.id.sp2);
        spinner3 = (Spinner) findViewById(R.id.sp3);
        spinner4 = (Spinner) findViewById(R.id.sp4);
        spinner5 = (Spinner) findViewById(R.id.sp5);
        spinner6 = (Spinner) findViewById(R.id.sp6);
        spinner7 = (Spinner) findViewById(R.id.sp7);
        spinner8 = (Spinner) findViewById(R.id.sp8);
        spinner9 = (Spinner) findViewById(R.id.sp9);
        spinner10 = (Spinner) findViewById(R.id.sp10);
        spinner11 = (Spinner) findViewById(R.id.sp11);
        spinner12 = (Spinner) findViewById(R.id.sp12);
        spinner13 = (Spinner) findViewById(R.id.sp13);
        spinner14 = (Spinner) findViewById(R.id.sp14);
        spinner15 = (Spinner) findViewById(R.id.sp15);
        spinner16 = (Spinner) findViewById(R.id.sp16);
        spinner17 = (Spinner) findViewById(R.id.sp17);
        spinner18 = (Spinner) findViewById(R.id.sp18);
        spinner19 = (Spinner) findViewById(R.id.sp19);
        spinner20 = (Spinner) findViewById(R.id.sp20);
        spinner21 = (Spinner) findViewById(R.id.sp21);
        spinner22 = (Spinner) findViewById(R.id.sp22);
        spinner23 = (Spinner) findViewById(R.id.sp23);
        spinner24 = (Spinner) findViewById(R.id.sp24);
        spinner25 = (Spinner) findViewById(R.id.sp25);
        spinner26 = (Spinner) findViewById(R.id.sp26);
        spinner27 = (Spinner) findViewById(R.id.sp27);
        spinner28 = (Spinner) findViewById(R.id.sp28);
        spinner29 = (Spinner) findViewById(R.id.sp29);
        spinner30 = (Spinner) findViewById(R.id.sp30);

        Spinner[] allSpinners = {spinner1, spinner2, spinner3, spinner4, spinner5, spinner6, spinner7, spinner8, spinner9, spinner10,
                spinner11, spinner12, spinner13, spinner14, spinner15, spinner16, spinner17, spinner18, spinner19, spinner20,
                spinner21, spinner22, spinner23, spinner24, spinner25, spinner26, spinner27, spinner28, spinner29, spinner30};


        for(int i =0; i <allSpinnerHandles.length; i++)
        {
            makeSpinner(allSpinners[i], i, allFields[i]);
        }

        set_Spinner(spinner1, classicSpinnerValues, "שיעול");
        spinner1.setOnItemSelectedListener(allSpinnerHandles[0]);

        spinner2 = (Spinner) findViewById(R.id.sp2);
        set_Spinner(spinner2, classicSpinnerValues, "קשיים בבליעה");
        spinner2.setOnItemSelectedListener(allSpinnerHandles[1]);

        spinner3 = (Spinner) findViewById(R.id.sp3);
        set_Spinner(spinner3, classicSpinnerValues, "עייפות");
        spinner3.setOnItemSelectedListener(allSpinnerHandles[2]);

        spinner4 = (Spinner) findViewById(R.id.sp4);
        set_Spinner(spinner4, classicSpinnerValues, "חולשה");
        spinner4.setOnItemSelectedListener(allSpinnerHandles[3]);

        spinner5 = (Spinner) findViewById(R.id.sp5);
        set_Spinner(spinner5, classicSpinnerValues, "סחרחורות");
        spinner5.setOnItemSelectedListener(allSpinnerHandles[4]);

        spinner6 = (Spinner) findViewById(R.id.sp6);
        set_Spinner(spinner6, classicSpinnerValues, "בחילה");
        spinner6.setOnItemSelectedListener(allSpinnerHandles[5]);

        spinner7 = (Spinner) findViewById(R.id.sp7);
        set_Spinner(spinner7, classicSpinnerValues, "הקאות");
        spinner7.setOnItemSelectedListener(allSpinnerHandles[6]);

        spinner8 = (Spinner) findViewById(R.id.sp8);
        set_Spinner(spinner8, classicSpinnerValues, "כאבים בחזה");
        spinner8.setOnItemSelectedListener(allSpinnerHandles[7]);

        spinner9 = (Spinner) findViewById(R.id.sp9);
        set_Spinner(spinner9, classicSpinnerValues, "נשירת שיער");
        spinner9.setOnItemSelectedListener(allSpinnerHandles[8]);

        spinner10 = (Spinner) findViewById(R.id.sp10);
        set_Spinner(spinner10, classicSpinnerValues, "קוצר נשימה");
        spinner10.setOnItemSelectedListener(allSpinnerHandles[9]);

        spinner10 = (Spinner) findViewById(R.id.sp10);
        set_Spinner(spinner10, classicSpinnerValues, "קוצר נשימה");
        spinner10.setOnItemSelectedListener(allSpinnerHandles[9]);

        spinner10 = (Spinner) findViewById(R.id.sp10);
        set_Spinner(spinner10, classicSpinnerValues, "קוצר נשימה");
        spinner10.setOnItemSelectedListener(allSpinnerHandles[9]);

        spinner10 = (Spinner) findViewById(R.id.sp10);
        set_Spinner(spinner10, classicSpinnerValues, "קוצר נשימה");
        spinner10.setOnItemSelectedListener(allSpinnerHandles[9]);

        spinner10 = (Spinner) findViewById(R.id.sp10);
        set_Spinner(spinner10, classicSpinnerValues, "קוצר נשימה");
        spinner10.setOnItemSelectedListener(allSpinnerHandles[9]);

        spinner10 = (Spinner) findViewById(R.id.sp10);
        set_Spinner(spinner10, classicSpinnerValues, "קוצר נשימה");
        spinner10.setOnItemSelectedListener(allSpinnerHandles[9]);

        spinner10 = (Spinner) findViewById(R.id.sp10);
        set_Spinner(spinner10, classicSpinnerValues, "קוצר נשימה");
        spinner10.setOnItemSelectedListener(allSpinnerHandles[9]);

        spinner10 = (Spinner) findViewById(R.id.sp10);
        set_Spinner(spinner10, classicSpinnerValues, "קוצר נשימה");
        spinner10.setOnItemSelectedListener(allSpinnerHandles[9]);

        spinner10 = (Spinner) findViewById(R.id.sp10);
        set_Spinner(spinner10, classicSpinnerValues, "קוצר נשימה");
        spinner10.setOnItemSelectedListener(allSpinnerHandles[9]);

        spinner10 = (Spinner) findViewById(R.id.sp10);
        set_Spinner(spinner10, classicSpinnerValues, "קוצר נשימה");
        spinner10.setOnItemSelectedListener(allSpinnerHandles[9]);

        spinner10 = (Spinner) findViewById(R.id.sp10);
        set_Spinner(spinner10, classicSpinnerValues, "קוצר נשימה");
        spinner10.setOnItemSelectedListener(allSpinnerHandles[9]);

        spinner10 = (Spinner) findViewById(R.id.sp10);
        set_Spinner(spinner10, classicSpinnerValues, "קוצר נשימה");
        spinner10.setOnItemSelectedListener(allSpinnerHandles[9]);

        spinner10 = (Spinner) findViewById(R.id.sp10);
        set_Spinner(spinner10, classicSpinnerValues, "קוצר נשימה");
        spinner10.setOnItemSelectedListener(allSpinnerHandles[9]);

        spinner10 = (Spinner) findViewById(R.id.sp10);
        set_Spinner(spinner10, classicSpinnerValues, "קוצר נשימה");
        spinner10.setOnItemSelectedListener(allSpinnerHandles[9]);

        spinner10 = (Spinner) findViewById(R.id.sp10);
        set_Spinner(spinner10, classicSpinnerValues, "קוצר נשימה");
        spinner10.setOnItemSelectedListener(allSpinnerHandles[9]);

        spinner10 = (Spinner) findViewById(R.id.sp10);
        set_Spinner(spinner10, classicSpinnerValues, "קוצר נשימה");
        spinner10.setOnItemSelectedListener(allSpinnerHandles[9]);

        spinner10 = (Spinner) findViewById(R.id.sp10);
        set_Spinner(spinner10, classicSpinnerValues, "קוצר נשימה");
        spinner10.setOnItemSelectedListener(allSpinnerHandles[9]);

        spinner10 = (Spinner) findViewById(R.id.sp10);
        set_Spinner(spinner10, classicSpinnerValues, "קוצר נשימה");
        spinner10.setOnItemSelectedListener(allSpinnerHandles[9]);

        spinner10 = (Spinner) findViewById(R.id.sp10);
        set_Spinner(spinner10, classicSpinnerValues, "קוצר נשימה");
        spinner10.setOnItemSelectedListener(allSpinnerHandles[9]);

        spinner10 = (Spinner) findViewById(R.id.sp10);
        set_Spinner(spinner10, classicSpinnerValues, "קוצר נשימה");
        spinner10.setOnItemSelectedListener(allSpinnerHandles[9]);

        spinner10 = (Spinner) findViewById(R.id.sp10);
        set_Spinner(spinner10, classicSpinnerValues, "קוצר נשימה");
        spinner10.setOnItemSelectedListener(allSpinnerHandles[9]);

        spinner10 = (Spinner) findViewById(R.id.sp10);
        set_Spinner(spinner10, classicSpinnerValues, "קוצר נשימה");
        spinner10.setOnItemSelectedListener(allSpinnerHandles[9]);

        spinner10 = (Spinner) findViewById(R.id.sp10);
        set_Spinner(spinner10, classicSpinnerValues, "קוצר נשימה");
        spinner10.setOnItemSelectedListener(allSpinnerHandles[9]);

        spinner10 = (Spinner) findViewById(R.id.sp10);
        set_Spinner(spinner10, classicSpinnerValues, "קוצר נשימה");
        spinner10.setOnItemSelectedListener(allSpinnerHandles[9]);

        spinner10 = (Spinner) findViewById(R.id.sp10);
        set_Spinner(spinner10, classicSpinnerValues, "קוצר נשימה");
        spinner10.setOnItemSelectedListener(allSpinnerHandles[9]);

        spinner10 = (Spinner) findViewById(R.id.sp10);
        set_Spinner(spinner10, classicSpinnerValues, "קוצר נשימה");
        spinner10.setOnItemSelectedListener(allSpinnerHandles[9]);

        spinner10 = (Spinner) findViewById(R.id.sp10);
        set_Spinner(spinner10, classicSpinnerValues, "קוצר נשימה");
        spinner10.setOnItemSelectedListener(allSpinnerHandles[9]);

        spinner10 = (Spinner) findViewById(R.id.sp10);
        set_Spinner(spinner10, classicSpinnerValues, "קוצר נשימה");
        spinner10.setOnItemSelectedListener(allSpinnerHandles[9]);

        spinner10 = (Spinner) findViewById(R.id.sp10);
        set_Spinner(spinner10, classicSpinnerValues, "קוצר נשימה");
        spinner10.setOnItemSelectedListener(allSpinnerHandles[9]);

        spinner10 = (Spinner) findViewById(R.id.sp10);
        set_Spinner(spinner10, classicSpinnerValues, "קוצר נשימה");
        spinner10.setOnItemSelectedListener(allSpinnerHandles[9]);

        spinner10 = (Spinner) findViewById(R.id.sp10);
        set_Spinner(spinner10, classicSpinnerValues, "קוצר נשימה");
        spinner10.setOnItemSelectedListener(allSpinnerHandles[9]);

















        database = FirebaseDatabase.getInstance();
        reference = database.getReference("root");


        btSend = findViewById(R.id.BTsend);
        btSend.setOnClickListener(this);


        //String text = dropdown.getSelectedItem().toString();//tv.setText(text);



    }

    @Override
    public void onClick(View v) {
        String[] allSelectedData = new String[allSpinnerHandles.length];
        String[] allFields = new String[allSpinnerHandles.length];


        for(int spin = 0; spin < allSpinnerHandles.length; spin++)
        {
            allSelectedData[spin] =  allSpinnerHandles[spin].getSelectedData();
            allFields[spin] = new String(allSpinnerHandles[spin].getFieldTitle());

            if(allSpinnerHandles[spin].getSelectedData() == null
                   )
            {
                Toast.makeText(MainActivity.this,
                                "חובה למלא: "+ this.allSpinnerHandles[spin].getFieldTitle(), // this expression represents the value of the spinner
                                Toast.LENGTH_SHORT)
                        .show();
                return;
            }
        }

        SideEffects s = new SideEffects(allFields, allSelectedData);

        Intent intent = getIntent();
        String userName = intent.getStringExtra(PatiantLogin.USER_KEY);
        String hospitalName = intent.getStringExtra(PatiantLogin.HOSPITAL_KEY);


        addSideEffects(reference, hospitalName, userName, s);
        // Query checkDataBase = reference.child("doctorA").orderByChild("users").equalTo(userData.getUserName());

        intent = new Intent(MainActivity.this, confermationPage.class);
        startActivity(intent);

    }
}
