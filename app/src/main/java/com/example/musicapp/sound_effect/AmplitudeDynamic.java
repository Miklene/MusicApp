package com.example.musicapp.sound_effect;

import com.example.musicapp.buffer.WaveBuffer;
import com.example.musicapp.wave.Wave;

public class AmplitudeDynamic extends SoundEffectDecorator {

    private WaveBuffer waveBuffer;
    private float[] dynamicCoefficient = {
            1.0f, 1.05f, 1.1f, 1.15f, 1.2f, 1.25f, 1.30f,
            1.30f, 1.25f, 1.2f, 1.15f, 1.1f, 1.05f, 1.0f,
            1.0f, 0.95f, 0.9f, 0.85f, 0.8f, 0.75f, 0.7f,
            0.7f, 0.75f, 0.8f, 0.85f, 0.9f, 0.95f, 1.0f
    };
    private int counter;

    public AmplitudeDynamic(WaveBuffer waveBuffer) {
        this.waveBuffer = waveBuffer;
    }

    @Override
    public float[] createBuffer() {
        float[] buffer = waveBuffer.createBuffer();
        counter = buffer.length/dynamicCoefficient.length+1;
        for(int i = 0;i<buffer.length;i+=2){
            buffer[i] = buffer[i] * getDynamicCoefficient(i);
            buffer[i+1] = buffer[i];
        }
        return buffer;
    }

    public float[] applyEffect(float[] buffer){
        counter = buffer.length/dynamicCoefficient.length+1;
        for(int i = 0;i<buffer.length;i+=2){
            buffer[i] = buffer[i] * getDynamicCoefficient(i);
            buffer[i+1] = buffer[i];
        }
        return buffer;
    }

    private float getDynamicCoefficient(int itemNumber){
        int index;
        index = itemNumber / counter;
        return dynamicCoefficient[index];
    }


}
