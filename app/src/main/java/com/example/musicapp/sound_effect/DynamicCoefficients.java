package com.example.musicapp.sound_effect;

public interface DynamicCoefficients {

    double vMax = Math.log10(4000);
    double vMin = Math.log10(1000);
    double v0 = (vMax + vMin) / 2;
    double xE = 0.1;
    double k  = Math.pow((vMax - v0), 2) / xE;

}
