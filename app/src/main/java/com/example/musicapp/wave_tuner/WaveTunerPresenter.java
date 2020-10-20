package com.example.musicapp.wave_tuner;

import android.content.Context;

import com.example.musicapp.buffer.WaveBuffer;
import com.example.musicapp.buffer.WaveBufferSingleThread;
import com.example.musicapp.model.Settings;
import com.example.musicapp.model.WaveBufferBuilder;
import com.example.musicapp.model.WavePlayer;
import com.example.musicapp.model.Waves;
import com.example.musicapp.wave.Wave;

public class WaveTunerPresenter {

    private WaveTunerView waveTunerView;
    private Context context;
    private Waves waves;
    private WavePlayer wavePlayer;
    private Wave currentWave;

    public Wave getCurrentWave() {
        return currentWave;
    }

    public WaveTunerPresenter(WaveTunerView waveTunerView, Context context) {
        this.waveTunerView = waveTunerView;
        this.context = context;
        waves = Waves.getInstance();
        wavePlayer = WavePlayer.getInstance();
    }

    public void setCurrentWave(int layoutPosition) {
        this.currentWave = waves.getWave(layoutPosition);
    }

    public void onButtonPlayClicked() {
        startWavePlayer();
    }

    public void onButtonStopClicked() {
        stopWavePlayer();
    }

    public void onButtonIncreaseClicked() {

    }

    public void onButtonDecreaseClicked() {

    }

    public void enableAmplitudeDynamic(boolean isEnable){

    }

    public void enableNormalization(boolean isEnable){

    }

    private void startWavePlayer() {
        wavePlayer.playWave(WaveBufferBuilder.getWaveBuffer(currentWave, Settings.duration));
    }

    private void stopWavePlayer() {
        wavePlayer.stopWavePlayer();
        //wavePlayer = null;
    }


}
