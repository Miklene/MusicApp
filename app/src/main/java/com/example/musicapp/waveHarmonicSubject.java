package com.example.musicapp;



public interface waveHarmonicSubject {
    void registerWaveHarmonicsObserver(waveHarmonicObserver observer);
    void removeWaveHarmonicsObserver(waveHarmonicObserver observer);
    void notifyWaveHarmonicsObservers();
}
