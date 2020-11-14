package com.example.musicapp.sound_effect;

import com.example.musicapp.buffer.WaveBuffer;

public class Stereo extends SoundEffectDecorator {

    private final WaveBuffer waveBuffer;

    public Stereo(WaveBuffer waveBuffer) {
        this.waveBuffer = waveBuffer;
    }

    @Override
    public float[] createBuffer() {
        float[] buffer = waveBuffer.createBuffer();
        int stereoBufferLength = buffer.length*2;
        float[] stereoBuffer = new float[stereoBufferLength];
        for(int i = 0; i < buffer.length; i++){
            stereoBuffer[2*i] = buffer[i];
            stereoBuffer[2*i+1] = buffer[i];
        }
        return stereoBuffer;
    }
}
