package com.example.musicapp.wave;

import com.example.musicapp.R;
import com.example.musicapp.common.Type;

import java.util.ArrayList;

public abstract class ComplexWave implements Wave{
     Type type;
     SinWave mainTone;
     ArrayList<SinWave> waveHarmonics = new ArrayList<>();
     int imageId;
     private int tableId;


    public ComplexWave(float frequency, int harmonicsNumber, int tableId) {
        createMainTone(frequency);
        createWaveHarmonics(frequency, harmonicsNumber);
        this.tableId = tableId;
    }

    abstract void createMainTone(float frequency);

    abstract void createWaveHarmonics(float frequency, int harmonicsNumber);

    @Override
    public SinWave getMainTone() {
        return mainTone;
    }

    @Override
    public ArrayList<SinWave> getWaveHarmonics() {
        return waveHarmonics;
    }

    @Override
    public float getFrequency() {
        return mainTone.getFrequency();
    }

    @Override
    public float getAmplitude() {
        return mainTone.getAmplitude();
    }

    @Override
    public double getInitialPhase() {
        return mainTone.getInitialPhase();
    }

    @Override
    public int getHarmonicsNumber() {
        return waveHarmonics.size();
    }

    @Override
    public int getImageId() {
        return imageId;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public int getTableId() {
        return tableId;
    }

    @Override
    public void setFrequency(float frequency) {
        mainTone.setFrequency(frequency);
    }
}
