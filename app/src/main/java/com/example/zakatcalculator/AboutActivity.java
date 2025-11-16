package com.example.zakatcalculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.zakatcalculator.R;

public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.about_toolbar);
        setSupportActionBar(toolbar);

        // Enable Back Button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
