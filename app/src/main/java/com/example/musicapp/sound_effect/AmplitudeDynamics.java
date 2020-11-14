package com.example.musicapp.sound_effect;

import com.example.musicapp.buffer.WaveBuffer;
import com.example.musicapp.wave.Wave;

public class AmplitudeDynamics extends SoundEffectDecorator {

    private final WaveBuffer waveBuffer;
    private final float[] dynamicCoefficient = {
            1.0f, 1.01f, 1.02f, 1.03f, 1.04f, 1.05f, 1.06f,
            1.06f, 1.05f, 1.04f, 1.03f, 1.02f, 1.01f, 1.0f,
            1.0f, 0.99f, 0.98f, 0.97f, 0.96f, 0.95f, 0.94f,
            0.94f, 0.95f, 0.96f, 0.97f, 0.98f, 0.99f, 1.0f
    };

    private float dynamicPercent = 0.1f;
    private int length = 1000;
    private float percentPerPoint;
    private int pointsPerDynamicPercent;
    private int quarter = 0;

    public AmplitudeDynamics(WaveBuffer waveBuffer) {
        this.waveBuffer = waveBuffer;
        calculatePercentPerPoint();
    }

    @Override
    public float[] createBuffer() {
        float[] buffer = waveBuffer.createBuffer();
        for (int i = 0; i < buffer.length; i ++) {
            buffer[i] += buffer[i] * getDynamicCoefficient(i);
        }
        return buffer;
    }

    private void calculatePercentPerPoint() {
        pointsPerDynamicPercent = length / 4;
        percentPerPoint = dynamicPercent / pointsPerDynamicPercent;
    }

    private float getDynamicCoefficient(int itemNumber) {
        int multiplier;
        multiplier = itemNumber % pointsPerDynamicPercent;
        calculateQuarter(itemNumber);
        float result;

        if (quarter%4 == 0) {
            result = multiplier * percentPerPoint;
            return result;
        }
        if (quarter%4 == 1) {
            result = dynamicPercent - multiplier * percentPerPoint;
            return result;
        }
        if (quarter%4 == 2) {
            result = multiplier * percentPerPoint;
            return - result;
        }
        if (quarter%4 == 3) {
            result = -dynamicPercent + multiplier * percentPerPoint;
            return result;
        }
        return 0;
    }


    private void calculateQuarter(int itemNumber) {
        quarter = itemNumber / pointsPerDynamicPercent;
        /*if(itemNumber/pointsPerDynamicPercent == 0 || itemNumber/pointsPerDynamicPercent == 3)
            return 1;
        return -1;*/
    }

   /* @Override
    public short[] createShortBuffer() {
        short[] buffer = waveBuffer.createShortBuffer();
        counter = buffer.length/dynamicCoefficient.length+1;
        for(int i = 0;i<buffer.length;i+=2){
            buffer[i] = (short) (buffer[i] * getDynamicCoefficient(i));
            buffer[i+1] = buffer[i];
        }
        return buffer;
    }*/
}
