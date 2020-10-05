package com.example.musicapp.wave;


import com.example.musicapp.R;
import com.example.musicapp.common.Type;

import java.net.URL;
import java.util.ArrayList;

public class SinWave  {
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

    public double getInitialPhase() {
        return initialPhase;
    }

    public void setFrequency(float frequency) {
        this.frequency = frequency;
    }
}
