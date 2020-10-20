package com.example.musicapp.Deprecated;


import com.example.musicapp.Deprecated.frequencyObserver;

public interface frequencySubject {
    void registerFrequencyObserver(frequencyObserver observer);
    void removeFrequencyObserver(frequencyObserver observer);
    void notifyFrequencyObservers();
}
