package com.example.musicapp.wave;


public class SinWave {
    private float frequency;
    private float amplitude;
    private double initialPhase;


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

    public double getInitialPhase() {
        return initialPhase;
    }

}
