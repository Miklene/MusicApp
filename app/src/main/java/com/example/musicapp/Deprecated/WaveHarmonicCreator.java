package com.example.musicapp.Deprecated;

import com.example.musicapp.Deprecated.Wave;
import com.example.musicapp.Deprecated.WaveHarmonic;

import java.util.ArrayList;

public class WaveHarmonicCreator {
    private static final int[] amplitudeRatios = { 825, 896, 360, 522, 309, 20, 388, 191,
                                612, 610, 255, 168, 131, 46, 56,  56,
                                74,  132, 30,  80,  30,  68, 22,  43,
                                16,  20,  16,  7,   6,   6,  5};

    public static void addWaveHarmonic(Wave wave) {
        int harmonicsNumber;
        float frequency;
        int frequencyRatio;
        harmonicsNumber = wave.getHarmonicsNumber();
        frequencyRatio = harmonicsNumber + 2;
        frequency = wave.getFrequency()*frequencyRatio;
        WaveHarmonic waveHarmonic = new WaveHarmonic(frequency, amplitudeRatios[harmonicsNumber]);
        wave.addHarmonic(waveHarmonic);
    }

    public static void UpdateWaveHarmonics(Wave wave){
        WaveHarmonic waveHarmonic;
        ArrayList<WaveHarmonic> waveHarmonics;// = new ArrayList<>();
        int harmonicsNumber;
        float frequency;
        int frequencyRatio;
        harmonicsNumber = wave.getHarmonicsNumber();
        frequency = wave.getFrequency();
        waveHarmonics = wave.getWaveHarmonics();
        for(int i = 0; i < harmonicsNumber; i++){
            frequencyRatio = i + 2;
            waveHarmonic = waveHarmonics.get(i);
            waveHarmonic.setFrequency(frequency*frequencyRatio);
            waveHarmonic.setAmplitude(amplitudeRatios[i]);
            waveHarmonics.set(i,waveHarmonic);
        }
    }
}
