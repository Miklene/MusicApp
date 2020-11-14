package com.example.musicapp.sound_effect.amplitude;

import com.example.musicapp.common.RecordParameters;
import com.example.musicapp.sound_effect.DynamicCoefficients;
import com.example.musicapp.wave.SinWave;

public class DynamicAmplitude extends AmplitudeBehavior implements DynamicCoefficients,
        RecordParameters {

    private double dynamicDecibel;
    private double shift = 0;
    private double dynamicPercent;
    private double halfDynamicPercent;
    private final float amplitude;
    private double period = 0.250;
    private double w = (2 * Math.PI / period);
    double phase = 0;//-Math.PI / 2;
    private int counter;

    public DynamicAmplitude(SinWave sinWave) {
        super(sinWave);
        amplitude = sinWave.getAmplitude();
        counter = 0;
        calculateDynamicDecibel();
        convertDecibelIntoPercent();
    }

    @Override
    public float create(int itemNumber) {
        float currentDynamic = calculateDynamicCoefficient();
        counter++;
        return amplitude + amplitude * currentDynamic;
    }

    public float getDynamicPercent(){
        return (float)(dynamicPercent*100);
    }

    private float calculateDynamicCoefficient() {
        double sin = Math.sin(w * counter * dt + phase);
        if (sin > 0.9) {
            sin++;
            sin--;
        }
        return (float) (halfDynamicPercent * sin + shift);
    }

    private void calculateDynamicDecibel() {
        dynamicDecibel = Math.pow(Math.log10(sinWave.getFrequency()) - v0, 2) / k;
    }

    private void convertDecibelIntoPercent() {
        dynamicPercent = Math.pow(10, dynamicDecibel / 20) / 100;
        halfDynamicPercent = dynamicPercent / 2;
    }
}
