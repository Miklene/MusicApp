package com.example.musicapp;

public interface HarmonicBehavior {
    void addHarmonic(WaveHarmonic waveHarmonic);
    void deleteHarmonic(int position);
    void changeHarmonic(int position, WaveHarmonic waveHarmonic);
}
