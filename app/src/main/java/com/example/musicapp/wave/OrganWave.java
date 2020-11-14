package com.example.musicapp.wave;

import com.example.musicapp.R;
import com.example.musicapp.common.Type;

public class OrganWave extends ComplexWave implements OrganCoefficients {

    public OrganWave(float frequency, int harmonicsNumber, int tableId) {
        super(frequency, harmonicsNumber, tableId);
        type = Type.ORGAN;
        imageId = R.drawable.organ;
    }

    @Override
    void createMainTone(float frequency) {
        mainTone = new SinWave(frequency, amplitudeRatios[0], initialPhaseCoefficients[0]);
    }

    @Override
    void createWaveHarmonics(float frequency, int harmonicsNumber) {
        double[] initialPhaseCoefficients = ViolinCoefficients.initialPhaseCoefficients;
        waveHarmonics.clear();
        for(int i =0;i<harmonicsNumber; i++)
            waveHarmonics.add(new SinWave(frequency*(i+2), amplitudeRatios[i+1], initialPhaseCoefficients[i+1]));
    }
}
