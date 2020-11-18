package com.example.musicapp.buffer;

import com.example.musicapp.common.TypeOfBuffer;
import com.example.musicapp.common.Settings;
import com.example.musicapp.sound_effect.AmplitudeDynamics;
import com.example.musicapp.sound_effect.Normalization;
import com.example.musicapp.sound_effect.SoundEffectsStatus;
import com.example.musicapp.sound_effect.Stereo;
import com.example.musicapp.wave.Wave;

public class WaveBufferBuilder {

    private static WaveBuffer waveBuffer;

    public static WaveBuffer getWaveBuffer(Wave wave, int duration){
        if(Settings.currentWaveBuffer.equals(TypeOfBuffer.SINGLE))
            waveBuffer = new WaveBufferSingleThread(wave, duration);
        else
            waveBuffer = new WaveBufferMultiThread(wave, duration);
       // if(SoundEffectsStatus.amplitudeDynamic)
       //     waveBuffer = new AmplitudeDynamics(waveBuffer);
        if(SoundEffectsStatus.normalization)
            waveBuffer = new Normalization(waveBuffer);
        if(SoundEffectsStatus.stereo)
            waveBuffer = new Stereo(waveBuffer);
        return waveBuffer;
    }

    public static WaveBuffer getWaveBufferGraph(Wave wave, int duration){
        if(Settings.currentWaveBuffer.equals(TypeOfBuffer.SINGLE))
            waveBuffer = new WaveBufferSingleThread(wave, duration);
        else
            waveBuffer = new WaveBufferMultiThread(wave, duration);
        if(SoundEffectsStatus.amplitudeDynamic)
            waveBuffer = new AmplitudeDynamics(waveBuffer);
        if(SoundEffectsStatus.normalization)
            waveBuffer = new Normalization(waveBuffer);
        return waveBuffer;
    }
}
