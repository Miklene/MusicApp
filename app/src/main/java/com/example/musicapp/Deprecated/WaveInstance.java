package com.example.musicapp.Deprecated;

import java.util.ArrayList;

public class WaveInstance implements WaveInstanceSubject {
    private static final WaveInstance ourInstance = new WaveInstance();
    private ArrayList observers = new ArrayList();
    private Wave wave;
    private ArrayList<WaveHarmonic> waveHarmonics;

    public static WaveInstance getInstance() {
        return ourInstance;
    }

    private  WaveInstance() {
    }

    public void setWaveInstance(Wave wave){
        this.wave = wave;
    }

    public void setWaveHarmonic(ArrayList<WaveHarmonic> waveHarmonics){
        this.waveHarmonics = waveHarmonics;
    }

    public void setHarmonicSuccess(boolean result){
        if(result)
            notifyWaveHarmonicInstanceObservers();
        else
            noResult();
    }

    public void setSuccess(boolean result){
        if(result)
            notifyWaveInstanceObservers();
        else
            noResult();
    }


    @Override
    public void registerWaveInstanceObserver( WaveInstanceObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeWaveInstanceObserver( WaveInstanceObserver observer) {
        int i = observers.indexOf(observer);
        if(i>=0)
            observers.remove(i);
    }

    @Override
    public void notifyWaveInstanceObservers() {
        for(int i = 0; i <observers.size(); i++) {
            WaveInstanceObserver observer = (WaveInstanceObserver) observers.get(i);
            observer.updateWaveInstance(wave);
        }
    }

    public void notifyWaveHarmonicInstanceObservers() {
        for(int i = 0; i <observers.size(); i++) {
            WaveInstanceObserver observer = (WaveInstanceObserver) observers.get(i);
            observer.updateWaveHarmonicInstance(waveHarmonics);
        }
    }

    private void noResult(){
        for(int i = 0; i <observers.size(); i++) {
            WaveInstanceObserver observer = (WaveInstanceObserver) observers.get(i);
            observer.noResult();
        }
    }
}
