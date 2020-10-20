package com.example.musicapp.Deprecated;

import com.example.musicapp.common.Type;

public class WaveCreator {
 //   private Wave wave;

    public Wave createWave(Type type, float frequency, int amplitude, int channelsNumber, int harmonicsNumber) {
        Wave wave;
        if (type.equals(Type.SAW))
            wave = new SawWave(frequency, amplitude, channelsNumber, harmonicsNumber);
        else
            wave = new SinWave(frequency, amplitude, channelsNumber, harmonicsNumber);
        return wave;
    }
}
