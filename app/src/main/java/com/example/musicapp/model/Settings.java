package com.example.musicapp.model;

import com.example.musicapp.buffer.WaveBuffer;
import com.example.musicapp.common.TypeOfBuffer;

public class Settings {

    public static TypeOfBuffer currentWaveBuffer = TypeOfBuffer.SINGLE;
    public static int duration = 1000;
    public static boolean isPlayed = false;
    public static boolean frequencyChanged = false;
}
