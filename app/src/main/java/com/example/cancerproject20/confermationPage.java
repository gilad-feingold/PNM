package com.example.cancerproject20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class confermationPage extends AppCompatActivity implements View.OnClickListener {

    Button btContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confermation_page);

        btContinue = findViewById(R.id.btcontinue);
        btContinue.setOnClickListener(this);





    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(confermationPage.this, PatiantLogin.class);
        startActivity(intent);
    }
}