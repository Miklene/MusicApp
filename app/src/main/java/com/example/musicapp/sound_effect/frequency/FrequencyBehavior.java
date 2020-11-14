package com.example.musicapp.sound_effect.frequency;

import com.example.musicapp.wave.SinWave;

public abstract class FrequencyBehavior {

    SinWave sinWave;

    public FrequencyBehavior(SinWave sinWave) {
        this.sinWave = sinWave;
    }

    public abstract float create();

    public abstract float getLastValue();
}
