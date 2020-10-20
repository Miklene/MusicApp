package com.example.musicapp.sound_effect;

import com.example.musicapp.buffer.WaveBuffer;

public class Normalization extends SoundEffectDecorator {

    WaveBuffer waveBuffer;

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
        return buffer;
    }

    private float findMaxAmplitude(float[] buffer) {
        float max;
        max = buffer[0];
        for (int i = 0; i < buffer.length; i++) {
            if (max < buffer[i])
                max = buffer[i];
        }
        return max;
    }
}
