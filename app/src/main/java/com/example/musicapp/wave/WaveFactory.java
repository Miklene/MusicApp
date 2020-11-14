package com.example.musicapp.wave;

import com.example.musicapp.common.Type;

public class WaveFactory {

    public static Wave createWave(Type type, float frequency, int harmonicsNumber, int tableId) {
        if (type == Type.VIOLIN)
            return new ViolinWave(frequency, harmonicsNumber, tableId);
        if (type == Type.ORGAN)
            return new OrganWave(frequency, harmonicsNumber, tableId);
        else
            return new SineComplexWave(frequency, harmonicsNumber, tableId);
    }
}
