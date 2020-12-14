package com.example.musicapp.common;

import com.example.musicapp.buffer.WaveBuffer;
import com.example.musicapp.common.TypeOfBuffer;
import com.example.musicapp.wave_player.WavePlayer;

public class Settings {

    public static TypeOfBuffer currentWaveBuffer = TypeOfBuffer.SINGLE;
    public static  int duration = 1000;
    public static boolean frequencyChanged = false;//в макет не добавлять
}
