package com.example.musicapp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Reverberation {
    private static final Reverberation ourInstance = new Reverberation();
    private ArrayList observers = new ArrayList();
    private Queue<Float> echo = new LinkedList<>();
    private final int delay = 200;
    private final int delaySamples = (int) ((float) delay * 44.1f);
    private final float decay = 0.5f;

    public static Reverberation getInstance() {
        return ourInstance;
    }

    private Reverberation() {
    }

    public boolean addEcho(float element) {
        boolean result;
        result = echo.offer(element);
        if (echo.size() == delay)
            echoFull();
        return result;
    }

    public void clearEcho(){
        echo.clear();
    }

  /*  @Override
    public void registerReverberationObserver(ReverberationObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeReverberationObserver(ReverberationObserver observer) {
        int i = observers.indexOf(observer);
        if (i >= 0)
            observers.remove(i);
    }

    @Override
    public void notifyReverberationObservers() {
        for (int i = 0; i < observers.size(); i++) {
            ReverberationObserver observer = (ReverberationObserver) observers.get(i);
            observer.updateReverberation(echo.poll() * decay);
        }
    }*/

    private void echoFull() {
       Buffer.setEcho((echo.poll()*decay));
    }
}
