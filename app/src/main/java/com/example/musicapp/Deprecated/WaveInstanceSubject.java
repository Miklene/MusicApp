package com.example.musicapp.Deprecated;


import com.example.musicapp.Deprecated.WaveInstanceObserver;

public interface WaveInstanceSubject {
    void registerWaveInstanceObserver(WaveInstanceObserver observer);

    void removeWaveInstanceObserver(WaveInstanceObserver observer);

    void notifyWaveInstanceObservers();
}
