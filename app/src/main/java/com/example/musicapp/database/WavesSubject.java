package com.example.musicapp.database;



public interface WavesSubject {
    void  registerObserver(WavesObserver observer);
    void removeObserver(WavesObserver observer);
    void notifyObservers();
}
