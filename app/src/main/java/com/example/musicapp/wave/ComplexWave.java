package com.example.musicapp.wave;

import java.util.ArrayList;

public abstract class ComplexWave {
    SinWave mainTone;
    ArrayList<SinWave> waveHarmonics = new ArrayList<>();


    public ComplexWave(float frequency, float amplitude, int harmonicsNumber) {
        createMainTone(frequency,amplitude);
        createWaveHarmonics(harmonicsNumber);
    }

    abstract void createMainTone(float frequency, float amplitude);

    abstract void createWaveHarmonics(int harmonicsNumber);

    public SinWave getMainTone() {
        return mainTone;
    }

    public ArrayList<SinWave> getWaveHarmonics() {
        return waveHarmonics;
    }

}
