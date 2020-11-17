package com.example.musicapp.sound_effect;

import com.example.musicapp.buffer.WaveBuffer;

public class Normalization extends SoundEffectDecorator {

    private final WaveBuffer waveBuffer;

    public Normalization(WaveBuffer waveBuffer) {
        this.waveBuffer = waveBuffer;
    }

    @Override
    public float[] createBuffer() {
        float[] buffer = waveBuffer.createBuffer();
        float correctionValue = findMaxAmplitude(buffer)*1.2f;
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = buffer[i] / correctionValue;
        }
        if(SoundEffectsStatus.startPlayback)
            return new StartPlayback().createBuffer(buffer);
        if(SoundEffectsStatus.endPlayback)
            return new EndPlayback().createBuffer(buffer);
        return buffer;
    }

    private float findMaxAmplitude(float[] buffer) {
        float max;
        max = buffer[0];
        for (int i = 0; i < buffer.length; i++) {
            if (max < Math.abs(buffer[i]))
                max = Math.abs(buffer[i]);
        }
        return max;
    }
}
