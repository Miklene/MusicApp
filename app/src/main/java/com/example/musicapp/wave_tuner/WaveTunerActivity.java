package com.example.musicapp.wave_tuner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.musicapp.R;
import com.example.musicapp.buffer.ComplexWaveBuffer;
import com.example.musicapp.main.MainPresenter;
import com.example.musicapp.wave.ComplexWave;
import com.example.musicapp.wave_tuner.WaveTunerView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class WaveTunerActivity extends Activity implements WaveTunerView, View.OnClickListener {

    private ImageView imageViewPlay, imageViewStop;
    private WaveTunerPresenter waveTunerPresenter;
    private EditText editText;
    private Button buttonIncrease, buttonDecrease;
    private GraphView graph;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave_tuner);
        waveTunerPresenter = new WaveTunerPresenter(this, WaveTunerActivity.this);
        Intent intent = getIntent();
        waveTunerPresenter.setCurrentWave(intent.getIntExtra("id", 0));
        imageViewPlay = findViewById(R.id.imageViewPlayWaveTuner);
        imageViewStop = findViewById(R.id.imageViewStopWaveTuner);
        editText = findViewById(R.id.editTextStepWaveTuner);
        imageViewPlay.setVisibility(View.INVISIBLE);
        imageViewStop.setVisibility(View.VISIBLE);
        buttonIncrease = findViewById(R.id.buttonIncreaseFrequency);
        buttonDecrease = findViewById(R.id.buttonDecreaseFrequency);
        imageViewPlay.setOnClickListener(this);
        imageViewStop.setOnClickListener(this);
        buttonIncrease.setOnClickListener(this);
        buttonDecrease.setOnClickListener(this);
        graph = findViewById(R.id.graphWaveTuner);
        drawGraph();
        /*checkboxHorizontalZoomScroll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    graph.getViewport().setScalable(true);
                    graph.getViewport().setScalableY(true);
                    graph.getViewport().setScrollable(true);
                    graph.getViewport().setScrollableY(true);
                }
                else {
                    graph.getViewport().setScalable(false);
                    graph.getViewport().setScalableY(false);
                    graph.getViewport().setScrollable(false);
                    graph.getViewport().setScrollableY(false);
                }
            }
        });*/
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String s = "-" + editText.getText().toString();
                    buttonDecrease.setText(s);
                    s = "+" + editText.getText().toString();
                    buttonIncrease.setText(s);
                    return true;
                }
                return false;
            }
        });
    }

    private void drawGraph(){
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScrollableY(true);
        ComplexWaveBuffer complexWaveBuffer = new ComplexWaveBuffer(waveTunerPresenter.getCurrentWave(),1000);
        float[] buffer = complexWaveBuffer.createBufferSingleThread();
        DataPoint[] points = new DataPoint[buffer.length];
        for(int i=0; i<buffer.length; i++)
            points[i] = new DataPoint(i,buffer[i]);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(points);
        graph.addSeries(series);
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
        }
    }

}
