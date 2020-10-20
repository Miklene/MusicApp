package com.example.musicapp.Deprecated;

import com.example.musicapp.Deprecated.ReverberationObserver;

public interface ReverberationSubject {
    void registerReverberationObserver(ReverberationObserver observer);
    void removeReverberationObserver(ReverberationObserver observer);
    void notifyReverberationObservers();
}
