package com.example.cancerproject20;

import com.example.cancerproject20.Human;
import com.example.cancerproject20.Patient;

import java.util.ArrayList;
import java.util.List;

public class Doctor implements Human {

    public static final String FIRSTVALINLIST = "Patients";

    private String id;
    private String Hospital;
    private String Password;
    private String fullName;

    private List<String> patients;

    public Doctor() {
    }
    public Doctor(String id, String fullName, String Password, String Hospital)
    {
        this.id = id;
        this.fullName = fullName;
        this.Password = Password;
        this.Hospital = Hospital;
        this.patients = new ArrayList<>();
        this.patients.add(FIRSTVALINLIST);

    }


    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    public String getHospital() {
        return Hospital;
    }

    public String getPassword() {
        return Password;
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

    public void setPassword(String password) { Password = password; }

    public List<String> getPatients() {return this.patients;}

    public void setPatients(List<String> patients) {this.patients = patients;}

    public void addPatient(String p)
    {
        this.patients.add(p);
    }

}
