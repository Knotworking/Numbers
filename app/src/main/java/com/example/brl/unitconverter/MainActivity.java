package com.example.brl.unitconverter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import static com.example.brl.unitconverter.Constants.*;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner typeSpinner;
    Spinner inputSpinner;
    Spinner outputSpinner;
    ArrayAdapter<CharSequence> typeAdapter;
    ArrayAdapter<CharSequence> massAdapter;
    ArrayAdapter<CharSequence> temperatureAdapter;
    ArrayAdapter<CharSequence> distanceAdapter;
    ArrayAdapter<CharSequence> currencyAdapter;

    EditText inputEditText;
    EditText outputEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupAdapters();

        typeSpinner = (Spinner)findViewById(R.id.activity_main_type_spinner);
        setupSpinner(typeSpinner, typeAdapter);

        inputSpinner = (Spinner)findViewById(R.id.activity_main_input_spinner);
        setupSpinner(inputSpinner, massAdapter);

        outputSpinner = (Spinner)findViewById(R.id.activity_main_output_spinner);
        setupSpinner(outputSpinner, massAdapter);

        inputEditText = (EditText)findViewById(R.id.activity_main_input_editText);
        outputEditText = (EditText)findViewById(R.id.activity_main_output_editText);
        setupTextWatchers();

    }

    private void setupTextWatchers() {
        inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (inputEditText.hasFocus()) {
                    parametersChanged(true);
                }
            }
        });
        outputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (outputEditText.hasFocus()) {
                    parametersChanged(false);
                }
            }
        });
    }

    private void setupAdapters() {
        typeAdapter = ArrayAdapter.createFromResource(this, R.array.type_array, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        massAdapter = ArrayAdapter.createFromResource(this, R.array.mass_array, android.R.layout.simple_spinner_item);
        massAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        temperatureAdapter = ArrayAdapter.createFromResource(this, R.array.temperature_array, android.R.layout.simple_spinner_item);
        temperatureAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        distanceAdapter = ArrayAdapter.createFromResource(this, R.array.distance_array, android.R.layout.simple_spinner_item);
        distanceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencyAdapter = ArrayAdapter.createFromResource(this, R.array.currency_array, android.R.layout.simple_spinner_item);
        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.activity_main_type_spinner:
                handleTypeSelected(position);
                break;
            case R.id.activity_main_input_spinner:
                parametersChanged(true);
                break;
            case R.id.activity_main_output_spinner:
                parametersChanged(false);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void setupSpinner(Spinner spinner, ArrayAdapter adapter) {
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    private void handleTypeSelected(int position) {
        switch (position) {
            case TYPE_MASS:
                inputSpinner.setAdapter(massAdapter);
                inputSpinner.setSelection(MASS_G);
                outputSpinner.setAdapter(massAdapter);
                outputSpinner.setSelection(MASS_OZ);
                break;
            case TYPE_TEMPERATURE:
                inputSpinner.setAdapter(temperatureAdapter);
                inputSpinner.setSelection(TEMP_C);
                outputSpinner.setAdapter(temperatureAdapter);
                outputSpinner.setSelection(TEMP_F);
                break;
            case TYPE_DISTANCE:
                break;
            case TYPE_CURRENCY:
                break;
        }
    }

    private void parametersChanged(boolean inputChanged) {
        switch (typeSpinner.getSelectedItemPosition()) {
            case TYPE_MASS:
                if (inputChanged) {
                    float input = Utils.toGrams(inputSpinner.getSelectedItemPosition(), Utils.getFloatFromString(inputEditText.getText().toString()));
                    float output = Utils.fromGrams(outputSpinner.getSelectedItemPosition(), input);
                    outputEditText.setText(String.valueOf(output));
                } else {
                    float input = Utils.toGrams(outputSpinner.getSelectedItemPosition(), Utils.getFloatFromString(outputEditText.getText().toString()));
                    float output = Utils.fromGrams(inputSpinner.getSelectedItemPosition(), input);
                    inputEditText.setText(String.valueOf(output));
                }
                break;
            case TYPE_TEMPERATURE:
                break;
            case TYPE_DISTANCE:
                break;
            case TYPE_CURRENCY:
                break;
        }
    }


}
