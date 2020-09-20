package com.example.musicapp;

public class SinWaveHarmonic {
    private float frequency;
    private double waveVolume;
    private int amplitude;

    public SinWaveHarmonic(float frequency, int amplitude) {
        this.frequency = frequency;
        this.amplitude = amplitude;
    }

    public float getFrequency() {
        return frequency;
    }

    public double getWaveVolume() {
        return waveVolume;
    }

    public int getAmplitude() {
        return amplitude;
    }
}
