package com.example.musicapp.model;



public interface WavesSubject {
    void  registerObserver(WavesObserver observer);
    void removeObserver(WavesObserver observer);
    void notifyObservers();
}
