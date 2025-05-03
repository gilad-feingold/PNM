package com.example.cancerproject20;

import com.google.firebase.database.DatabaseReference;
import com.google.firestore.v1.StructuredQuery;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SideEffects {
    private Map<String, String> m_effects;

    public SideEffects(Map<String, String> effects)
    {
        m_effects = effects;
    }

    public SideEffects()
    {}
    public SideEffects(String[] values, String[] data)
    {
       m_effects = new HashMap<>();
        if(values.length != data.length)
            return;

        for(int i = 0; i < data.length; i++)
        {
            m_effects.put(values[i], data[i]);
        }
    }


    public static String GetDate()
    {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1; // Month is zero-based
        int year = calendar.get(Calendar.YEAR);
        String formattedDate = day + " " + month + " " + year;

        return formattedDate;
    }

    public static void  addSideEffects(DatabaseReference rootReference, String hospital, String UserName, SideEffects sideEffects)

    {
        String formattedDate = GetDate();


        rootReference.child("Hospital").child(hospital).child("Patients").child(UserName).child("side effects").child(formattedDate).setValue(sideEffects);
    }
    public static void  addSideEffects(DatabaseReference rootReference, Patient p, SideEffects sideEffects)
    {

        String formattedDate = GetDate();


        rootReference.child("Hospital").child(p.getHospital()).child("Patients").child(p.getFullName()).child("side effects").child(formattedDate).setValue(sideEffects);

    }

    public Map<String, String> getM_effects()
    {
        return m_effects;
    }

    public void setM_effects(Map<String, String> m_effects) {
        this.m_effects = m_effects;
    }


    public String ToSting() {
        StringBuilder result = new StringBuilder();
        result.append("\n");
        for (Map.Entry<String, String> entry : this.m_effects.entrySet()) {
            String val = entry.getValue();
            if(val.length() > 2)
                val = "0";
            else
                result.append(entry.getKey()).append(" : ").append(val).append("\n");

        }
        


        return result.toString().trim(); // Trim to remove the last newline
    }



}
