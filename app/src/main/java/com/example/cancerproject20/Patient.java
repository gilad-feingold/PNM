package com.example.cancerproject20;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.Query;


import java.net.PasswordAuthentication;
import java.time.LocalDate;
import java.util.Calendar;

public class Patient extends Human{

    private String age;
    private String id;
    private String Hospital;
    private String Password;
    private String fullName;

    public Patient(String age, String id, String fullName, String Password, String Hospital)
    {
        this.age = age;
        this.id = id;
        this.fullName = fullName;
        this.Password = Password;
        this.Hospital = Hospital;
    }

    public String getAge() {
        return age;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getHospital() {
        return Hospital;
    }

    public String getPassword() {
        return Password;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setHospital(String hospital) {
        Hospital = hospital;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        Password = password;
    }


    public static void addPatient(DatabaseReference rootReference, Patient p)
    {
        rootReference.child("Hospital").child(p.Hospital).child("Patients").child(p.fullName).child("personal info").setValue(p);

    }

    public static void addSideEffect(DatabaseReference rootReference, String hospital, String username, String date, SideEffects sideEffects)
    {
        rootReference.child("Hospital").child(hospital)
        .child("Patients").child(username).child("side effects").child(date).setValue(sideEffects);
    }


    //public static boolean getPatientByIdAndPassword(int id, String Password, DatabaseReference rootReference)
   // {


    //}






}
