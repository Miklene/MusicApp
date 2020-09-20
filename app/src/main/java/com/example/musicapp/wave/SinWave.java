package com.example.musicapp.wave;


public class SinWave {
    private float frequency;
    private float amplitude;
    private double initialPhase;
    //private float phase;

    public SinWave(float frequency, float amplitude, double initialPhase) {
        this.frequency = frequency;
        this.amplitude = amplitude;
        this.initialPhase = initialPhase;

    }

    public float getFrequency() {
        return frequency;
    }

    public float getAmplitude() {
        return amplitude;
    }

    public void setFrequency(float frequency) {
        this.frequency = frequency;
    }

    public void setAmplitude(float amplitude) {
        this.amplitude = amplitude;
    }












   /* public float[] makeBuffer(int duration) {
        float[] buffer = new float[duration];
        for (int i = 0; i < duration; i++) {
            buffer[i] = (float) (amplitude * Math.sin(twoPI * (frequency * i * dt + phase)));// * (wave.getWaveVolume()) * systemVolume);
        }
        countPhase(duration*dt);
        return buffer;
    }*/
}
