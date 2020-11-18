package com.example.musicapp.sound_effect.frequency;

import com.example.musicapp.common.RecordParameters;
import com.example.musicapp.sound_effect.DynamicCoefficients;
import com.example.musicapp.wave.SinWave;

public class DynamicFrequency extends FrequencyBehavior implements DynamicCoefficients, RecordParameters {

    private double dynamicPercent = 0.025;
    private double period = 0.250;
    private double w = (2 * Math.PI / period);
    private int counter;
    private int delay;
    double phase = -Math.PI / 2;
    private int time = 11025;

    public DynamicFrequency(SinWave sinWave) {
        super(sinWave);
        counter = 0;
        delay = 44100;
    }

    @Override
    public float create() {
        float currentDynamic = calculateDynamicCoefficient();
        counter++;
        float coef = currentDynamic;
        return sinWave.getFrequency() + sinWave.getFrequency() * currentDynamic;
    }

    @Override
    public float getLastValue() {
        return sinWave.getFrequency() + sinWave.getFrequency() * calculateDynamicCoefficient();
    }

    public float getDynamicPercent() {
        return (float) (dynamicPercent * 2) * 100;
    }

    /*private float calculateDynamicCoefficient() {
        if (delay-- > 0) {
            return 0;
        }
        double sin = Math.sin((w * counter * dt + phase));
        return (float) (dynamicPercent * sin + dynamicPercent);
    }*/
    private float calculateDynamicCoefficient() {
        float coefficient;
        if (delay-- > 0) {
            return 0;
        }
        if (delay == -1)
            counter = 0;
        if (counter < time)
            return 0;
        else if (counter < time + 11)
            return (float) ((float)((counter - time) / 11d) * dynamicPercent);
        if (counter > time * 2 + 11 && counter < time * 2 + 22)
            return (float) (((time * 2 + 22 - counter) / 11d) * dynamicPercent);
        if (counter == time * 2 + 22) {
            counter = 0;
        } else
            return (float) dynamicPercent;
        return 0;
    }
}
