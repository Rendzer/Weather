package com.example.ilonka.pogoda;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.text.DecimalFormat;

public class SettingsActivity extends AppCompatActivity {

    private EditText longitudeView;
    private EditText latitudeView;
    private EditText refreshRateView;
    private DecimalFormat precision = new DecimalFormat("0.000");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        longitudeView = (EditText) findViewById(R.id.textLongitude);
        latitudeView = (EditText) findViewById(R.id.textLatitude);
        refreshRateView = (EditText) findViewById(R.id.textRefresh);
        longitudeView.setText(precision.format(getIntent().getDoubleExtra(String.valueOf(R.string.longitude_value_name), AstroValues.longitude)).replace(",", "."));
        latitudeView.setText(precision.format(getIntent().getDoubleExtra(String.valueOf(R.string.latitude_value_name), AstroValues.latitude)).replace(",", "."));
        refreshRateView.setText(String.format("%d", getIntent().getIntExtra(String.valueOf(R.string.refreshTime_value_name), AstroValues.refreshTime)));
    }

    public void saveSettings(View view) {
        Intent intentResult = new Intent();
        intentResult.putExtra(String.valueOf(R.string.longitude_value_name), Double.valueOf(longitudeView.getText().toString().replace(",", ".")));
        intentResult.putExtra(String.valueOf(R.string.latitude_value_name), Double.valueOf(latitudeView.getText().toString().replace(",", ".")));
        intentResult.putExtra(String.valueOf(R.string.refreshTime_value_name), Integer.valueOf(refreshRateView.getText().toString().replace(",", ".")));
        setResult(1, intentResult);
        finish();
    }

}
