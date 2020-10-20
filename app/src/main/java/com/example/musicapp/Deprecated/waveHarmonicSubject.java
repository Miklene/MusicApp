package com.example.musicapp.Deprecated;


import com.example.musicapp.Deprecated.waveHarmonicObserver;

public interface waveHarmonicSubject {
    void registerWaveHarmonicsObserver(waveHarmonicObserver observer);
    void removeWaveHarmonicsObserver(waveHarmonicObserver observer);
    void notifyWaveHarmonicsObservers();
}
