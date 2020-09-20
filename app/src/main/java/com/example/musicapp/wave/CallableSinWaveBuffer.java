package com.example.musicapp.wave;

import java.util.concurrent.Callable;

public class CallableSinWaveBuffer implements Callable, RecordParameters {
    SinWave sinWave;
    ReadyBuffers readyBuffers;
    int duration;
    float phase;

    public CallableSinWaveBuffer(SinWave sinWave, int duration, ReadyBuffers readyBuffers){
        this.sinWave = sinWave;
        this.duration = duration;
        this.readyBuffers = readyBuffers;
        phase = 0;
        //createBuffer();
    }
    @Override
    public float[] call()  {
        float frequency = sinWave.getFrequency();
        float amplitude = sinWave.getAmplitude();
        float[] buffer = new float[duration];
        for (int i = 0; i < duration; i++) {
            buffer[i] = (float) ((Math.sin(twoPI * (frequency * i * dt + phase)) * amplitude));// * (wave.getWaveVolume()) * systemVolume);
        }
        return  buffer;
    }

    public void run()  {
        float frequency = sinWave.getFrequency();
        float amplitude = sinWave.getAmplitude();
        float[] buffer = new float[duration];
        for (int i = 0; i < duration; i++) {
            buffer[i] = (float) ((Math.sin(twoPI * (frequency * i * dt + phase)) * amplitude));// * (wave.getWaveVolume()) * systemVolume);
        }
        readyBuffers.putBuffer(buffer);
    }
}
