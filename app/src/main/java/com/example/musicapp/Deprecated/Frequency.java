package com.example.musicapp.Deprecated;

import java.util.ArrayList;

public class Frequency implements frequencySubject {
    private float frequency;
    private static final Frequency ourInstance = new Frequency();
    private ArrayList observers = new ArrayList();

    public static Frequency getInstance() {
        return ourInstance;
    }

    public void setFrequency(float frequency) {
        this.frequency = frequency;
        frequencyChanged();
    }

    public float getFrequency() {
        return frequency;
    }

    private Frequency() {
    }

    @Override
    public void registerFrequencyObserver(frequencyObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeFrequencyObserver(frequencyObserver observer) {
        int i = observers.indexOf(observer);
        if(i>=0)
            observers.remove(i);
    }

    @Override
    public void notifyFrequencyObservers() {
        for(int i = 0; i <observers.size(); i++) {
            frequencyObserver observer = (frequencyObserver) observers.get(i);
            observer.updateFrequency(frequency);
        }
    }
     private void frequencyChanged(){
        notifyFrequencyObservers();
     }
}
