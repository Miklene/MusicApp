package com.example.musicapp.wave_tuner;

import android.content.Context;

import com.example.musicapp.buffer.WaveBuffer;
import com.example.musicapp.buffer.WaveBufferSingleThread;
import com.example.musicapp.model.Settings;
import com.example.musicapp.model.WaveBufferBuilder;
import com.example.musicapp.model.WavePlayer;
import com.example.musicapp.model.Waves;
import com.example.musicapp.sound_effect.SoundEffectsStatus;
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


    public void onButtonIncreaseClicked(float change) {
        frequencyChanged(currentWave.getFrequency()+change);
    }

    public void onButtonDecreaseClicked(float change) {
        frequencyChanged(currentWave.getFrequency()-change);
    }

    public void frequencyChanged(float newFrequency){
        currentWave.setFrequency(newFrequency);
        waveTunerView.drawGraph();
        wavePlayer.updateWaveBuffer(WaveBufferBuilder.getWaveBuffer(currentWave, Settings.duration));
    }

    public void enableAmplitudeDynamic(boolean isEnable){
        SoundEffectsStatus.amplitudeDynamic = isEnable;
        wavePlayer.updateWaveBuffer(WaveBufferBuilder.getWaveBuffer(currentWave, Settings.duration));
        waveTunerView.drawGraph();
    }

    public void enableFrequencyDynamic(boolean isEnable){
        SoundEffectsStatus.frequencyDynamic = isEnable;
        wavePlayer.updateWaveBuffer(WaveBufferBuilder.getWaveBuffer(currentWave, Settings.duration));
        waveTunerView.drawGraph();
    }

    public void enableNormalization(boolean isEnable){
        SoundEffectsStatus.normalization = isEnable;
        wavePlayer.updateWaveBuffer(WaveBufferBuilder.getWaveBuffer(currentWave, Settings.duration));
        waveTunerView.drawGraph();
    }

    public boolean getWavePlayerInstance(){
        return wavePlayer.isPlayed();
    }

    private void startWavePlayer() {
        wavePlayer.playWave(WaveBufferBuilder.getWaveBuffer(currentWave, Settings.duration));
    }

    private void stopWavePlayer() {
        wavePlayer.stopWavePlayer();
        //wavePlayer = null;
    }




}
