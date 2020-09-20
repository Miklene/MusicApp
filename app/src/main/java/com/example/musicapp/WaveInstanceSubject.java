package com.example.musicapp;


public interface WaveInstanceSubject {
    void registerWaveInstanceObserver(WaveInstanceObserver observer);

    void removeWaveInstanceObserver(WaveInstanceObserver observer);

    void notifyWaveInstanceObservers();
}
