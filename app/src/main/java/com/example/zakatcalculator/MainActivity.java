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
        } else if (item.getItemId() == R.id.share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Zakat Calculator App");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "https://github.com/sachert0rte/zakat_calculator");
            startActivity(Intent.createChooser(shareIntent, "Share via"));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getColor(R.color.toolbar_red));
        }

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        goldWeight = findViewById(R.id.goldWeight);
        goldValue = findViewById(R.id.goldValue);
        goldTypeGroup = findViewById(R.id.goldTypeGroup);
        keepButton = findViewById(R.id.keepButton);
        wearButton = findViewById(R.id.wearButton);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnClear = findViewById(R.id.btnClear);
        totalValue = findViewById(R.id.totalValue);
        zakatWeight = findViewById(R.id.zakatWeight);
        zakatPayable = findViewById(R.id.zakatPayable);
        totalZakat = findViewById(R.id.totalZakat);

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

            } catch (NumberFormatException nfe) {
                Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
            }
        });

        btnClear.setOnClickListener(v -> {
            try {
                goldWeight.setText("");
                goldValue.setText("");
                goldTypeGroup.clearCheck();
                totalValue.setText("");
                zakatWeight.setText("");
                zakatPayable.setText("");
                totalZakat.setText("");
            } catch (Exception e) {
                Toast.makeText(this, "Error clearing fields", Toast.LENGTH_SHORT).show();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
