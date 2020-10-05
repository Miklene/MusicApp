package com.example.musicapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.main.RecyclerViewAdapterHarmonics;

import java.util.ArrayList;


public class HarmonicDialogActivity extends Activity {
    RecyclerView recyclerView;
    ArrayList<WaveHarmonic> waveHarmonics = new ArrayList<>();
    ArrayList<WaveHarmonic> waveHarmonics2 = new ArrayList<>();
    Wave wave;
    EditText editTextFrequency, editTextAmplitude;
    Button buttonAddHarmonic, positiveButtonHarmonicDialog,
            negativeButtonHarmonicDialog, buttonFastAddHarmonic;
    WaveHarmonic waveHarmonic;
    private WaveInstance waveInstance = WaveInstance.getInstance();

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            //wave = (Wave) arguments.getParcelable("Wave");
            wave = arguments.getParcelable("Wave");
            //if(wave.getWaveHarmonics()!=null)
              //  waveHarmonics = wave.getWaveHarmonics();
            waveHarmonics2 = arguments.getParcelableArrayList("Array"); //Как обработать?
            wave.setWaveHarmonics(waveHarmonics2);
            waveHarmonics = waveHarmonics2;

            //wave.setWaveHarmonics(waveHarmonicArrayList);
        }
        setContentView(R.layout.activity_dialog_harmonic);
        positiveButtonHarmonicDialog = findViewById(R.id.positiveButtonHarmonicDialog);
        negativeButtonHarmonicDialog = findViewById(R.id.negativeButtonHarmonicDialog);
        buttonFastAddHarmonic = findViewById(R.id.buttonFastAddHarmonic);
        buttonAddHarmonic = findViewById(R.id.buttonAddHarmonic);
        editTextAmplitude = findViewById(R.id.editTextAmplitude);
        editTextFrequency = findViewById(R.id.editTextFrequency);
        recyclerView = findViewById(R.id.recyclerViewHarmonics);
        RecyclerViewAdapterHarmonics adapter = new RecyclerViewAdapterHarmonics(HarmonicDialogActivity.this, waveHarmonics2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        buttonAddHarmonic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float frequency;
                int amplitude;
                if (editTextFrequency.getText().toString().equals("")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Введите частоту!", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                frequency = Float.parseFloat(editTextFrequency.getText().toString());
                if (editTextAmplitude.getText().toString().equals("")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Введите амплитуду!", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                amplitude = Integer.parseInt(editTextAmplitude.getText().toString());
                waveHarmonic = new WaveHarmonic(frequency, amplitude);
                //waveHarmonicArrayList.add(waveHarmonic);
                wave.addHarmonic(waveHarmonic);
                adapter.notifyItemInserted(waveHarmonics2.size() - 1);
                editTextFrequency.setText("");
                editTextAmplitude.setText("");
                recyclerView.smoothScrollToPosition(adapter.getItemCount());
            }
        });
        buttonFastAddHarmonic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WaveHarmonicCreator.addWaveHarmonic(wave);
                adapter.notifyItemInserted(waveHarmonics2.size() - 1);
                recyclerView.smoothScrollToPosition(adapter.getItemCount());
            }
        });
        positiveButtonHarmonicDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waveInstance.setWaveInstance(wave);
                waveInstance.setSuccess(true);
                finish();
            }
        });
        negativeButtonHarmonicDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waveInstance.setSuccess(false);
                wave.setWaveHarmonics(waveHarmonics);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //waveInstance.setWaveInstance(wave);
        waveInstance.setSuccess(false);
        // finish();
    }

}
