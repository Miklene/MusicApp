package com.example.musicapp.model;

import com.example.musicapp.buffer.WaveBuffer;
import com.example.musicapp.buffer.WaveBufferMultiThread;
import com.example.musicapp.buffer.WaveBufferSingleThread;
import com.example.musicapp.common.TypeOfBuffer;
import com.example.musicapp.sound_effect.AmplitudeDynamic;
import com.example.musicapp.sound_effect.Normalization;
import com.example.musicapp.sound_effect.SoundEffectsStatus;
import com.example.musicapp.wave.Wave;

public class WaveBufferBuilder {

    private static WaveBuffer waveBuffer;
    public static WaveBuffer getWaveBuffer(Wave wave, int duration){
        if(Settings.currentWaveBuffer.equals(TypeOfBuffer.SINGLE))
            waveBuffer = new WaveBufferSingleThread(wave, duration);
        else
            waveBuffer = new WaveBufferMultiThread(wave, duration);
        if(SoundEffectsStatus.amplitudeDynamic)
            waveBuffer = new AmplitudeDynamic(waveBuffer);
        if(SoundEffectsStatus.normalization)
            waveBuffer = new Normalization(waveBuffer);
        return waveBuffer;
    }
}
