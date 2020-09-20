package com.example.musicapp.wave;

public class ViolinWave extends ComplexWave implements ViolinCoefficients {

    public ViolinWave(float frequency, float amplitude, int harmonicsNumber) {
        super(frequency, amplitude, harmonicsNumber);
    }

    @Override
    void createMainTone(float frequency, float amplitude) {
        mainTone = new SinWave(frequency, amplitudeRatios[0],initialPhase[0]);
        waveHarmonics.add(mainTone);
    }

    @Override
    void createWaveHarmonics(int harmonicsNumber) {
        float frequency =  mainTone.getFrequency();
        double[] initialPhase = ViolinCoefficients.initialPhase;
        for(int i =0;i<harmonicsNumber; i++)
            waveHarmonics.add(new SinWave(frequency*(i+2), amplitudeRatios[i+1], initialPhase[i+1]));
    }
}
