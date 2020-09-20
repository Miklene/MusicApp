package com.example.musicapp;

public interface ReverberationSubject {
    void registerReverberationObserver(ReverberationObserver observer);
    void removeReverberationObserver(ReverberationObserver observer);
    void notifyReverberationObservers();
}
