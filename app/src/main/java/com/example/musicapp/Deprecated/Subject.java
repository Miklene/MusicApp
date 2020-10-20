package com.example.musicapp.Deprecated;


import com.example.musicapp.Deprecated.Observer;

public interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}
