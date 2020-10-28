package com.example.musicapp.buffer;


import com.example.musicapp.common.RecordParameters;
import com.example.musicapp.wave.SinWave;

import java.util.concurrent.Callable;

public class SinWaveBuffer implements Callable, RecordParameters {
    private SinWave sinWave;
    private int duration;
    private float phase;
    private float d;

    public SinWaveBuffer(SinWave sinWave, int duration) {
        this.sinWave = sinWave;
        this.duration = duration;
        d = (float)duration * dt;
        phase = 0;
    }

    public float[] makeSinBuffer() {
        float frequency = sinWave.getFrequency();
        float amplitude = sinWave.getAmplitude();
        double initialPhase = sinWave.getInitialPhase();
        int stereoDuration = duration * 2;
        float[] buffer = new float[stereoDuration];
        for (int i = 0, j=0; i < stereoDuration; i +=2, j++) {
            buffer[i] = (float) (Math.sin(twoPI * (frequency * j * dt + initialPhase + phase)) * amplitude);
            buffer[i + 1] = buffer[i];
        }
        phase = countPhase(frequency, phase);
        return buffer;
    }

    public short[] makeShortSinBuffer() {
        float frequency = sinWave.getFrequency();
        float amplitude = sinWave.getAmplitude();
        double initialPhase = sinWave.getInitialPhase();
        int stereoDuration = duration * 2;
        short[] buffer = new short[stereoDuration];
        for (int i = 0, j=0; i < stereoDuration; i +=2, j++) {
            buffer[i] = (short) (0x7FFF*Math.sin(twoPI * (frequency * j * dt + initialPhase + phase)) /* amplitude*/);
            buffer[i + 1] = buffer[i];
        }
        phase = countPhase(frequency, phase);
        return buffer;
    }

    private float countPhase(float frequency, float p) {
        float T = 1 / frequency;
        phase = p + d / T;
        phase -= (int) phase;
        return phase;
    }

    @Override
    public float[] call() {
        return makeSinBuffer();
    }
}
