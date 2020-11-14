package com.example.musicapp.wave_dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;

import com.example.musicapp.R;
import com.example.musicapp.Deprecated.Wave;
import com.example.musicapp.common.Type;


public class WaveDialogActivity extends Activity implements RadioGroup.OnCheckedChangeListener,
        WaveDialogView, View.OnClickListener {

    private EditText editTextFrequency, editTextNumberOfHarmonic;
    Button positiveButton, negativeButton;
    private Type type;
    private WaveDialogPresenter waveDialogPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_wave);
        Wave wave;
        waveDialogPresenter = new WaveDialogPresenter(this, this);
        RadioButton radioButtonViolin = findViewById(R.id.radioButtonViolin);
        RadioButton radioButtonSin = findViewById(R.id.radioButtonSin);
        RadioButton radioButtonOrgan = findViewById(R.id.radioButtonOrgan);
        RadioGroup radioGroupType = findViewById(R.id.radioGroupType);
        radioGroupType.setOnCheckedChangeListener(this);
        positiveButton = findViewById(R.id.positiveButton);
        negativeButton = findViewById(R.id.negativeButton);
        editTextFrequency = findViewById(R.id.editTextFrequency);
        editTextNumberOfHarmonic = findViewById(R.id.editTextNumberOfHarmonic);
        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            wave = (Wave) arguments.get("wave");
            assert wave != null;
            type = wave.getType();
            if (type.equals(Type.VIOLIN)) {
                radioButtonViolin.setChecked(true);
                editTextNumberOfHarmonic.setEnabled(true);
                editTextNumberOfHarmonic.setFocusable(true);
                editTextNumberOfHarmonic.setLongClickable(true);
                editTextNumberOfHarmonic.setCursorVisible(true);
                editTextNumberOfHarmonic.setFocusableInTouchMode(true);
                editTextNumberOfHarmonic.requestFocus();
            } else if (type.equals(Type.ORGAN)) {
                radioButtonOrgan.setChecked(true);
                editTextNumberOfHarmonic.setEnabled(true);
                editTextNumberOfHarmonic.setFocusable(true);
                editTextNumberOfHarmonic.setLongClickable(true);
                editTextNumberOfHarmonic.setCursorVisible(true);
                editTextNumberOfHarmonic.setFocusableInTouchMode(true);
                editTextNumberOfHarmonic.requestFocus();
            } else {
                radioButtonSin.setChecked(true);
                editTextNumberOfHarmonic.setText("1");
                editTextNumberOfHarmonic.setEnabled(false);
                editTextNumberOfHarmonic.setFocusable(false);
                editTextNumberOfHarmonic.setLongClickable(false);
                editTextNumberOfHarmonic.setCursorVisible(false);
            }
            editTextFrequency.setText(String.valueOf(wave.getFrequency()));
            editTextNumberOfHarmonic.setText(String.valueOf(wave.getHarmonicsNumber()));
        }
        positiveButton.setOnClickListener(this);
        negativeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.positiveButton: {
                hideKeyboardPositive();
                waveDialogPresenter.onPositiveButtonClicked();
                break;
            }
            case R.id.negativeButton: {
                hideKeyboardNegative();
                waveDialogPresenter.onNegativeButtonClicked();
                break;
            }
        }
    }

    private void hideKeyboardPositive() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(positiveButton.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void hideKeyboardNegative() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(negativeButton.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public String getFrequencyFromEditText() {
        return editTextFrequency.getText().toString();
    }

    @Override
    public String getHarmonicsNumberFromEditText() {
        return editTextNumberOfHarmonic.getText().toString();
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.radioButtonViolin:
                type = Type.VIOLIN;
                editTextNumberOfHarmonic.setText("");
                editTextNumberOfHarmonic.setEnabled(true);
                editTextNumberOfHarmonic.setFocusable(true);
                editTextNumberOfHarmonic.setLongClickable(true);
                editTextNumberOfHarmonic.setCursorVisible(true);
                editTextNumberOfHarmonic.setFocusableInTouchMode(true);
                editTextNumberOfHarmonic.requestFocus();
                editTextNumberOfHarmonic.setBackgroundResource(R.drawable.border_rectangle);
                break;
            case R.id.radioButtonSin:
                type = Type.SIN;
                editTextNumberOfHarmonic.setText("");
                /*editTextNumberOfHarmonic.setEnabled(false);
                editTextNumberOfHarmonic.setFocusable(false);
                editTextNumberOfHarmonic.setLongClickable(false);
                editTextNumberOfHarmonic.setCursorVisible(false);*/
                // editTextNumberOfHarmonic.setBackgroundResource(R.drawable.border_rectangle_fill);
                break;
            case R.id.radioButtonOrgan:
                type = Type.ORGAN;
                editTextNumberOfHarmonic.setText("");
                editTextNumberOfHarmonic.setEnabled(true);
                editTextNumberOfHarmonic.setFocusable(true);
                editTextNumberOfHarmonic.setLongClickable(true);
                editTextNumberOfHarmonic.setCursorVisible(true);
                editTextNumberOfHarmonic.setFocusableInTouchMode(true);
                editTextNumberOfHarmonic.requestFocus();
                editTextNumberOfHarmonic.setBackgroundResource(R.drawable.border_rectangle);
                break;
        }
    }


}
