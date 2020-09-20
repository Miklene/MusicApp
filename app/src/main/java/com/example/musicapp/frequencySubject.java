package com.example.musicapp;



public interface frequencySubject {
    void registerFrequencyObserver(frequencyObserver observer);
    void removeFrequencyObserver(frequencyObserver observer);
    void notifyFrequencyObservers();
}
