package com.example.musicapp.wave;



public class SinWaveBuffer extends Thread implements RecordParameters {
    SinWave sinWave;
    ReadyBuffers readyBuffers;
    int duration;
    float phase;

    public SinWaveBuffer(SinWave sinWave, int duration, ReadyBuffers readyBuffers){
        this.sinWave = sinWave;
        this.duration = duration;
        this.readyBuffers = readyBuffers;
        phase = 0;
    }

    @Override
    public void run(){
        makeSinBuffer();
    }

    public void makeSinBuffer() {
        float frequency = sinWave.getFrequency();
        float amplitude = sinWave.getAmplitude();
        float[] buffer = new float[duration];
        for (int i = 0; i < duration; i++) {
            buffer[i] = (float) ((Math.sin(twoPI * (frequency * i * dt + phase)) * amplitude));// * (wave.getWaveVolume()) * systemVolume);
        }
        readyBuffers.putBuffer(buffer);
    }



}
