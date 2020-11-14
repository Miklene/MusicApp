package com.example.musicapp.sound_effect.amplitude;

import com.example.musicapp.wave.SinWave;

public abstract class AmplitudeBehavior {

    SinWave sinWave;
    public AmplitudeBehavior(SinWave sinWave){
        this.sinWave = sinWave;
    }

    public abstract float create(int itemNumber);
}
