package com.example.musicapp.wave_tuner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import com.example.musicapp.R;
import com.example.musicapp.buffer.ComplexWaveBuffer;
import com.example.musicapp.buffer.WaveBuffer;
import com.example.musicapp.model.WaveBufferBuilder;
import com.example.musicapp.sound_effect.SoundEffectsStatus;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class WaveTunerActivity extends Activity implements WaveTunerView, View.OnClickListener,
        SeekBar.OnSeekBarChangeListener, CompoundButton.OnCheckedChangeListener {

    private ImageView imageViewPlay, imageViewStop;
    private WaveTunerPresenter waveTunerPresenter;
    private EditText editTextStep, editTextFrequency;
    private ImageView buttonIncrease, buttonDecrease;
    private CheckBox checkBoxAmplitudeDynamic, checkBoxNormalization;
    private GraphView graph;
    private SeekBar seekBar;
    private int lastProgress = 0;
    //private final SoundEffectsStatus soundEffectsStatus = SoundEffectsStatus.getInstance();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave_tuner);
        waveTunerPresenter = new WaveTunerPresenter(this, WaveTunerActivity.this);
        Intent intent = getIntent();
        waveTunerPresenter.setCurrentWave(intent.getIntExtra("id", 0));
        imageViewPlay = findViewById(R.id.imageViewPlayWaveTuner);
        imageViewStop = findViewById(R.id.imageViewStopWaveTuner);
        imageViewPlay.setVisibility(View.INVISIBLE);
        imageViewStop.setVisibility(View.VISIBLE);
        editTextStep = findViewById(R.id.editTextStepWaveTuner);
        editTextFrequency = findViewById(R.id.editTextFrequencyWaveTuner);
        buttonIncrease = findViewById(R.id.imageViewIncreaseFrequencyWaveTuner);
        buttonDecrease = findViewById(R.id.imageViewDecreaseFrequencyWaveTuner);
        seekBar = findViewById(R.id.seekBarWaveTuner);
        checkBoxAmplitudeDynamic = findViewById(R.id.checkBoxAmplitudeDynamicWaveTuner);
        checkBoxNormalization = findViewById(R.id.checkBoxNormalizationWaveTuner);
        initCheckBoxes();
        checkBoxAmplitudeDynamic.setOnCheckedChangeListener(this);
        checkBoxNormalization.setOnCheckedChangeListener(this);
        seekBar.setOnSeekBarChangeListener(this);
        imageViewPlay.setOnClickListener(this);
        imageViewStop.setOnClickListener(this);
        buttonIncrease.setOnClickListener(this);
        buttonDecrease.setOnClickListener(this);
        graph = findViewById(R.id.graphWaveTuner);
        drawGraph();
        initSeekBar();
        editTextStep.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String s = "-" + editTextStep.getText().toString();
                    //buttonDecrease.setText(s);
                    s = "+" + editTextStep.getText().toString();
                    //buttonIncrease.setText(s);
                    return true;
                }
                return false;
            }
        });
    }

    private void initCheckBoxes(){
        checkBoxAmplitudeDynamic.setChecked(SoundEffectsStatus.amplitudeDynamic);
        checkBoxNormalization.setChecked(SoundEffectsStatus.normalization);
    }

    private void initSeekBar() {
        int progress = (int) waveTunerPresenter.getCurrentWave().getFrequency();
        int newProgress = (int) Math.sqrt(progress * 3500);
        int max = 3500;
        seekBar.setMax(max);
        seekBar.setProgress(newProgress);
        setEditTextFrequencyValue(progress);
    }

    private void setEditTextFrequencyValue(int frequency) {
        String text = String.valueOf(frequency);
        editTextFrequency.setText(text);
        int position = text.length();
        Editable editable = editTextFrequency.getText();
        Selection.setSelection(editable, position);
    }

    private void drawGraph() {
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScrollableY(true);
        WaveBuffer waveBuffer = WaveBufferBuilder.getWaveBuffer(waveTunerPresenter.getCurrentWave(),1000);
        float[] buffer = waveBuffer.createBuffer();
        DataPoint[] points = new DataPoint[buffer.length];
        for (int i = 0; i < buffer.length; i++)
            points[i] = new DataPoint(i, buffer[i]);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(points);
        graph.addSeries(series);
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScrollableY(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewPlayWaveTuner: {
                imageViewPlay.setClickable(false);
                waveTunerPresenter.onButtonPlayClicked();
                imageViewPlay.setVisibility(View.INVISIBLE);
                imageViewStop.setVisibility(View.VISIBLE);
                imageViewStop.setClickable(true);
                break;
            }
            case R.id.imageViewStopWaveTuner: {
                imageViewStop.setClickable(false);
                waveTunerPresenter.onButtonStopClicked();
                imageViewStop.setVisibility(View.INVISIBLE);
                imageViewPlay.setVisibility(View.VISIBLE);
                imageViewPlay.setClickable(true);
                break;
            }
            case R.id.imageViewDecreaseFrequencyWaveTuner: {
                waveTunerPresenter.onButtonDecreaseClicked();
                break;
            }
            case R.id.imageViewIncreaseFrequencyWaveTuner: {
                waveTunerPresenter.onButtonIncreaseClicked();
                break;
            }
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.checkBoxAmplitudeDynamicWaveTuner: {
                SoundEffectsStatus.amplitudeDynamic = isChecked;
                waveTunerPresenter.enableAmplitudeDynamic(isChecked);
                break;
            }
            case R.id.checkBoxNormalizationWaveTuner: {
                SoundEffectsStatus.normalization = isChecked;
                waveTunerPresenter.enableNormalization(isChecked);
                break;
            }
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        if (lastProgress != progress) {
            int newProgress = progress * progress / 3500;
            seekBar.setProgress(progress);
            lastProgress = progress;
            setEditTextFrequencyValue(newProgress);
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int progress = seekBar.getProgress();
        int newProgress = progress * progress / 3500;
        setEditTextFrequencyValue(newProgress);
    }

}
