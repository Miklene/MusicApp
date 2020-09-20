package com.example.musicapp;

public class SingleToneGenerator extends ToneGenerator {

    SingleToneGenerator(double frequency) {
        double frame;
        duration = 16384;
        buffer = new short[duration];
        frame = sampleRate / frequency;
        MainActivity mainActivity = new MainActivity();
        float[]volume = mainActivity.getVolume();
        for (int i = 0; i < duration; i++) {
            buffer[i] = (short) (Math.sin(2 * Math.PI * i / frame)*Short.MAX_VALUE*volume[0]) ;
        }
    }
    SingleToneGenerator(double frequency, int duration) {
        double frame;

        //duration = 16384;
        buffer = new short[duration];
        frame = sampleRate / frequency;
        MainActivity mainActivity = new MainActivity();
        float[] volume = mainActivity.getVolume();
        for (int i = 0; i < duration; i++) {
            buffer[i] = (short) (Math.sin(2 * Math.PI * i / frame)*Short.MAX_VALUE*volume[0]);
        }
    }

}
