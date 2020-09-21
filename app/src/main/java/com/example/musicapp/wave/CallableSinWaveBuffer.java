package com.example.musicapp.wave;

import java.util.concurrent.Callable;

public class CallableSinWaveBuffer implements Callable, RecordParameters {
    private SinWave sinWave;
    private ReadyBuffers readyBuffers;
    private int duration;
    private float phase;
    private float d;

    public CallableSinWaveBuffer(SinWave sinWave, int duration, ReadyBuffers readyBuffers){
        this.sinWave = sinWave;
        this.duration = duration;
        this.readyBuffers = readyBuffers;
        d =duration*dt;
        phase = 0;
    }

    @Override
    public float[] call()  {
        float frequency = sinWave.getFrequency();
        float amplitude = sinWave.getAmplitude();
        double initialPhase = sinWave.getInitialPhase();
        float[] buffer = new float[duration];
        for (int i = 0; i < duration; i++) {
            buffer[i] = (float) ((Math.sin(twoPI * (frequency * i * dt + initialPhase + phase)) * amplitude));// * (wave.getWaveVolume()) * systemVolume);
        }
        phase = countPhase(frequency, phase);
        return  buffer;
    }

    private float countPhase(float frequency, float phase) {
        float T = 1 / frequency;
        phase = (float)phase + d / T;
        phase -= (float) phase;
        return phase;
    }
}
