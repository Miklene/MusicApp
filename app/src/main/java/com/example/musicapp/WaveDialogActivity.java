package com.example.musicapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class WaveDialogActivity extends Activity implements RadioGroup.OnCheckedChangeListener,
        SeekBar.OnSeekBarChangeListener {

    TextView seekBarProgress;
    EditText editText, editTextNumberOfHarmonic;
    SeekBar seekBar;
    Button negativeButton, positiveButton;
    RadioButton radioButtonMono, radioButtonStereo, radioButtonViolin, radioButtonSin;
    RadioGroup radioGroupChannelsNumber, radioGroupType;
    private Wave wave;
    private Type type;
    private WaveCreator waveCreator = new WaveCreator();
    private Frequency myFrequency = Frequency.getInstance();
    private WaveInstance waveInstance = WaveInstance.getInstance();

    private ArrayList observers = new ArrayList();
    private byte channelsNumber = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dialog_wave);
        int myHarmonicsNumber = 0;
        radioButtonMono = findViewById(R.id.radioButtonMono);
        radioButtonStereo = findViewById(R.id.radioButtonStereo);
        radioGroupChannelsNumber = findViewById(R.id.radioGroupChannelsNumber);
        radioGroupChannelsNumber.setOnCheckedChangeListener(this);
        radioButtonViolin = findViewById(R.id.radioButtonViolin);
        radioButtonSin = findViewById(R.id.radioButtonSin);
        radioGroupType = findViewById(R.id.radioGroupType);
        radioGroupType.setOnCheckedChangeListener(this);
        seekBarProgress = findViewById(R.id.seekBarProgress);
        positiveButton = findViewById(R.id.positiveButton);
        negativeButton = findViewById(R.id.negativeButton);
        editText = findViewById(R.id.editText);
        editTextNumberOfHarmonic = findViewById(R.id.editTextNumberOfHarmonic);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(this);
        seekBarProgress.setText("0%");
        editTextNumberOfHarmonic.setText("1");
        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            wave = (Wave) arguments.get("wave");
            assert wave != null;
            type = wave.getType();
            myHarmonicsNumber = wave.getHarmonicsNumber();
            if (type.equals(Type.SAW)) {
                radioButtonViolin.setChecked(true);
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
            editText.setText(String.valueOf(wave.getFrequency()));
            editTextNumberOfHarmonic.setText(String.valueOf(wave.getHarmonicsNumber()));
            seekBar.setProgress((int) (wave.getAmplitude()));
            seekBarProgress.setText(String.valueOf(wave.getAmplitude()));
            String s;
            s = String.valueOf(seekBar.getProgress());
            s = s + "%";
            seekBarProgress.setText(s);
            if (wave.getChannelsNumber() == 1) {
                radioButtonMono.setChecked(true);
                channelsNumber = 1;
            }
            else {
                radioButtonStereo.setChecked(true);
                channelsNumber = 2;
            }
        }
        int finalMyHarmonicsNumber = myHarmonicsNumber;
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String seekBarResult;
                if (editText.getText().toString().equals("")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Введите частоту!", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                seekBarResult = seekBarProgress.getText().toString();
                seekBarResult = seekBarResult.replace("%", "");
                if (seekBarResult.equals("0")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Громкость не может быть ноль!", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                if (type == null) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Выберите тип!", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                if(channelsNumber == 0){
                    Toast toast = Toast.makeText(getApplicationContext(), "Выберите количество каналов!", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                myFrequency.setFrequency(Float.parseFloat(editText.getText().toString()));
                //sawWaveHarmonics.setNumberOfHarmonic(Integer.parseInt(editTextNumberOfHarmonic.getText().toString()));
                //wave = new Wave(myFrequency.getFrequency(), stereo,Double.parseDouble(seekBarResult),type, sawWaveHarmonics.getNumberOfHarmonic());
                wave = waveCreator.createWave(type, myFrequency.getFrequency(), Integer.parseInt(seekBarResult), channelsNumber,
                        0);
                for(int i = 0; i< finalMyHarmonicsNumber; i++)
                    WaveHarmonicCreator.addWaveHarmonic(wave);
                //intent = new Intent(WaveDialogActivity.this, MainActivity.class);
                //intent.putExtra(Wave.class.getSimpleName(), wave);
                waveInstance.setWaveInstance(wave);
                waveInstance.setSuccess(true);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(positiveButton.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                finish();
            }
        });
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waveInstance.setSuccess(false);
                finish();
            }
        });
    }


    @Override
    protected void onDestroy() {
        waveInstance.setSuccess(false);
        super.onDestroy();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        String s;
        s = String.valueOf(seekBar.getProgress());
        s = s + "%";
        seekBarProgress.setText(s);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        String s;
        s = String.valueOf(seekBar.getProgress());
        s = s + "%";
        seekBarProgress.setText(s);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.radioButtonMono:
                channelsNumber = 1;
                break;
            case R.id.radioButtonStereo:
                channelsNumber = 2;
                break;
            case R.id.radioButtonViolin:
                type = Type.SAW;
                editTextNumberOfHarmonic.setEnabled(true);
                editTextNumberOfHarmonic.setFocusable(true);
                editTextNumberOfHarmonic.setLongClickable(true);
                editTextNumberOfHarmonic.setCursorVisible(true);
                editTextNumberOfHarmonic.setFocusableInTouchMode(true);
                editTextNumberOfHarmonic.requestFocus();
                break;
            case R.id.radioButtonSin:
                type = Type.SIN;
                editTextNumberOfHarmonic.setText("1");
                editTextNumberOfHarmonic.setEnabled(false);
                editTextNumberOfHarmonic.setFocusable(false);
                editTextNumberOfHarmonic.setLongClickable(false);
                editTextNumberOfHarmonic.setCursorVisible(false);
                break;
        }
    }
}
