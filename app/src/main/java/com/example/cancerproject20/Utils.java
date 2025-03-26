package com.example.cancerproject20;

import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Utils extends SignupActivity{

    public interface ResultCallback {
        void onResult(boolean result);
    }


    public static boolean dosExist;
    public static boolean dosExist2;
    public static boolean isPositiveInteger(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }
        try {
            int number = Integer.parseInt(input);
            return number >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public static void checkPersonalInfo(EditText etId, EditText etUsername, String hospitalId, String targetId, String targetUsername, FirebaseDatabase database, Callback callback) {
        DatabaseReference patientsRef = database.getReference("root/Hospital/" + hospitalId + "/Patients");

        patientsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean valueExists = false;
                boolean exit = false;
                for (DataSnapshot patient : snapshot.getChildren()) {
                    exit = false;
                    String patientId = patient.child("personal info/id").getValue(String.class);
                    String patientUsername = patient.getKey();

                    if (patientId != null && patientId.equals(targetId)) {
                        valueExists = true;
                        exit = true;
                        etId.setError("מספר זהות כבר קיים במערכת");
                        etId.requestFocus();
                    }

                    if(patientUsername.equals(targetUsername)){
                        exit = true;
                        valueExists = true;
                        etUsername.setError("שם משתמש תפוס");
                        etUsername.requestFocus();
                    }

                    if(exit)
                        break;

                }
                callback.onResult(valueExists);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                callback.onResult(false);  // Handle error by returning false
            }
        });
    }

    // Callback Interface
    public interface Callback {
        void onResult(boolean exists);
    }

    public static void isInDataBase(String val, String path, ResultCallback callback) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(path);
        Query checkUserDatabase = reference.child(val);
        dosExist = false;
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                    callback.onResult(true);
                else
                    callback.onResult(false);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onResult(false);
            }
        });

    }

    public static void dosValueExistInUsersPersonalInfoExist(String path, String field, String val, ResultCallback callback)
    {
        dosExist2 = false;
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(path);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot patientSnapshot : snapshot.getChildren()) {
                    DataSnapshot personalInfoSnapshot = snapshot.child("/" + patientSnapshot.getKey() + "/personal info");

                    if (personalInfoSnapshot.exists()) {
                        String retrievedVal = personalInfoSnapshot.child(field).getValue(String.class);

                        if (retrievedVal != null && retrievedVal.equals(String.valueOf(val)))
                        {
                            dosExist2 = true;
                            break;
                        }
                    }
                }
                callback.onResult(dosExist2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onResult(false);
            }
        });
    }



    public static boolean isInDataBaseOrderByChild(String dataVal, String dataPath, String dataField)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(dataPath);
        Query checkUserDatabase = reference.orderByChild(dataField).equalTo(dataVal);

        dosExist = false;
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                    dosExist = true;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return dosExist;
    }

    public static String[] listToArray(List<String> list, String[] strArr)
    {
        if(list.size() != strArr.length)
            return null;


        for (int i =0; i< strArr.length; i++)
        {
            strArr[i] = list.get(i);
        }
        return strArr;
    }



    public static void insertValueAtStartOfArray(String[] oldarr, String[] newarr, String insertValue)
    {
        if(newarr.length - oldarr.length  != 1)
            return;

        newarr[0] = insertValue;
        for(int i = 0; i < oldarr.length; i++)
            newarr[i+1] = oldarr[i];
    }

}
