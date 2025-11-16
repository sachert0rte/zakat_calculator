package com.example.zakatcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText goldWeight, goldValue;
    RadioGroup goldTypeGroup;
    RadioButton keepButton, wearButton;
    Button btnCalculate, btnClear;
    TextView totalValue, zakatWeight, zakatPayable, totalZakat;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.about) {

            Intent intent = new Intent(this, com.example.zakatcalculator.AboutActivity.class);
            startActivity(intent);

        } else if (item.getItemId() == R.id.settings) {

            Toast.makeText(this, "This is settings", Toast.LENGTH_LONG).show();

        } else if (item.getItemId() == R.id.search) {

            Toast.makeText(this, "This is search", Toast.LENGTH_LONG).show();

        }

        else if (item.getItemId() == R.id.share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Zakat Calculator App");
            shareIntent.putExtra(Intent.EXTRA_TEXT,
                    "Try this Zakat Calculator app! Download now.");

            startActivity(Intent.createChooser(shareIntent, "Share via"));
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        goldWeight = findViewById(R.id.goldWeight);
        goldValue = findViewById(R.id.goldValue);
        goldTypeGroup = findViewById(R.id.goldTypeGroup);
        keepButton = findViewById(R.id.keepButton);
        wearButton = findViewById(R.id.wearButton);
        btnCalculate = findViewById(R.id.btnCalculate);
        totalValue = findViewById(R.id.totalValue);
        zakatWeight = findViewById(R.id.zakatWeight);
        zakatPayable = findViewById(R.id.zakatPayable);
        totalZakat = findViewById(R.id.totalZakat);
        btnClear = findViewById(R.id.btnClear); // Added Clear button reference

        btnCalculate.setOnClickListener(v -> {
            try {
                double weight = Double.parseDouble(goldWeight.getText().toString());
                double valuePerGram = Double.parseDouble(goldValue.getText().toString());
                double uruf;

                int selectedId = goldTypeGroup.getCheckedRadioButtonId();
                if (selectedId == R.id.keepButton) {
                    uruf = 85;
                } else if (selectedId == R.id.wearButton) {
                    uruf = 200;
                } else {
                    Toast.makeText(this, "Please select gold type", Toast.LENGTH_SHORT).show();
                    return;
                }

                double totalGoldValue = weight * valuePerGram;
                double goldWeightMinusUruf = weight - uruf;
                if (goldWeightMinusUruf < 0) goldWeightMinusUruf = 0;
                double zakatPayableValue = goldWeightMinusUruf * valuePerGram;
                double totalZakatValue = zakatPayableValue * 0.025;

                totalValue.setText(String.format("RM %.2f", totalGoldValue));
                zakatWeight.setText(String.format("%.2f g", goldWeightMinusUruf));
                zakatPayable.setText(String.format("RM %.2f", zakatPayableValue));
                totalZakat.setText(String.format("RM %.2f", totalZakatValue));

            } catch (Exception e) {
                Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
            }
        });

        // Clear button functionality
        btnClear.setOnClickListener(v -> {
            goldWeight.setText("");
            goldValue.setText("");
            goldTypeGroup.clearCheck();
            totalValue.setText("");
            zakatWeight.setText("");
            zakatPayable.setText("");
            totalZakat.setText("");
        });

        if (goldWeight.getText().toString().isEmpty() ||
                goldValue.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
}
