package com.example.musicapp.sound_effect.amplitude;

import com.example.musicapp.wave.SinWave;

public class StaticAmplitude extends AmplitudeBehavior {

    public StaticAmplitude(SinWave sinWave) {
        super(sinWave);
    }

    @Override
    public float create(int itemNumber) {
        return sinWave.getAmplitude();
    }
}
