package com.example.musicapp.Deprecated;

public interface HarmonicBehavior {
    void addHarmonic(WaveHarmonic waveHarmonic);
    void deleteHarmonic(int position);
    void changeHarmonic(int position, WaveHarmonic waveHarmonic);
}
