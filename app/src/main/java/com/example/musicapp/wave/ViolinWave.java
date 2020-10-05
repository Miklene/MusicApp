package com.example.musicapp.wave;

import com.example.musicapp.R;
import com.example.musicapp.common.Type;

public class ViolinWave extends ComplexWave implements ViolinCoefficients {

    public ViolinWave(float frequency, int harmonicsNumber, int tableId) {
        super(frequency, harmonicsNumber, tableId);
        type = Type.VIOLIN;
        imageId = R.drawable.violin;
    }

    @Override
    void createMainTone(float frequency) {
        mainTone = new SinWave(frequency, amplitudeRatios[0], initialPhaseCoefficients[0]);
        //waveHarmonics.add(mainTone);
    }

    @Override
    void createWaveHarmonics(float frequency, int harmonicsNumber) {
        double[] initialPhaseCoefficients = ViolinCoefficients.initialPhaseCoefficients;
        for(int i =0;i<harmonicsNumber; i++)
            waveHarmonics.add(new SinWave(frequency*(i+2), amplitudeRatios[i+1], initialPhaseCoefficients[i+1]));
    }
}
