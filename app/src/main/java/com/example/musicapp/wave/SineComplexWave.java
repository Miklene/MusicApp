package com.example.musicapp.wave;

import android.os.Parcel;

import com.example.musicapp.R;
import com.example.musicapp.WaveHarmonic;
import com.example.musicapp.common.Type;

public class SineComplexWave extends ComplexWave {

    public SineComplexWave(float frequency, int harmonicsNumber, int tableId) {
        super(frequency, harmonicsNumber, tableId);
        type = Type.SIN;
        imageId = R.drawable.sine;
    }

    @Override
    void createMainTone(float frequency) {
        mainTone = new SinWave(frequency,100,0);
    }

    @Override
    void createWaveHarmonics(float frequency, int harmonicsNumber) {
        for(int i =0;i<harmonicsNumber; i++)
            waveHarmonics.add(new SinWave(frequency*(i+2), 100, 0));
    }
}
