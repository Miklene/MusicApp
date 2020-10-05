package com.example.musicapp.wave;

import com.example.musicapp.common.Type;


public class WaveFactory {

    public static Wave createWave(Type type, float frequency, int harmonicsNumber, int tableId) {
        if (type == Type.VIOLIN)
            return new com.example.musicapp.wave.ViolinWave(frequency,harmonicsNumber, tableId);
        else
            return new com.example.musicapp.wave.SineComplexWave(frequency, harmonicsNumber, tableId);
    }
}
