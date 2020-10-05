package com.example.musicapp.wave;

import com.example.musicapp.common.Type;

import java.net.URL;
import java.util.ArrayList;

public interface Wave {


    float getFrequency();

    float getAmplitude();

    double getInitialPhase();

    int getHarmonicsNumber();

    ArrayList<SinWave> getWaveHarmonics();

    SinWave getMainTone();

    Type getType();

    int getImageId();

    int getTableId();

    void setFrequency(float frequency);



}
