package com.example.musicapp.sound_effect.frequency;

import com.example.musicapp.wave.SinWave;

public class StaticFrequency extends FrequencyBehavior {

    public StaticFrequency(SinWave sinWave) {
        super(sinWave);
    }

    @Override
    public float create() {
        return sinWave.getFrequency();
    }

    @Override
    public float getLastValue() {
        return sinWave.getFrequency();
    }
}
