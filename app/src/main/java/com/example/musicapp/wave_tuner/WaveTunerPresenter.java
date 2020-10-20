package com.example.musicapp.wave_tuner;

import android.content.Context;

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

    private void startWavePlayer() {
        wavePlayer.playWave(currentWave, 1000);
    }

    private void stopWavePlayer() {
        wavePlayer.stopWavePlayer();
        //wavePlayer = null;
    }


}
