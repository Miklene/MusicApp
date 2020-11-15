package com.example.musicapp.buffer;


import android.util.Log;

import com.example.musicapp.common.RecordParameters;
import com.example.musicapp.sound_effect.amplitude.AmplitudeBehavior;
import com.example.musicapp.sound_effect.amplitude.DynamicAmplitude;
import com.example.musicapp.sound_effect.amplitude.StaticAmplitude;
import com.example.musicapp.sound_effect.frequency.DynamicFrequency;
import com.example.musicapp.sound_effect.SoundEffectsStatus;
import com.example.musicapp.sound_effect.frequency.FrequencyBehavior;
import com.example.musicapp.sound_effect.frequency.StaticFrequency;
import com.example.musicapp.wave.SinWave;

import java.util.concurrent.Callable;

public class SinWaveBuffer implements Callable, RecordParameters {
    private SinWave sinWave;
    private int duration;
    private float phase;
    private float d;
    FrequencyBehavior frequency;
    AmplitudeBehavior amplitude;

    public SinWaveBuffer(SinWave sinWave, int duration) {
        this.sinWave = sinWave;
        this.duration = duration;
        d = (float) duration * dt;
        phase = 0;
        if (SoundEffectsStatus.frequencyDynamic)
            frequency = new DynamicFrequency(sinWave);
        else
            frequency = new StaticFrequency(sinWave);
        if (SoundEffectsStatus.amplitudeDynamic)
            amplitude = new DynamicAmplitude(sinWave);
        else
            amplitude = new StaticAmplitude(sinWave);
        Log.i("SinWave", String.valueOf(sinWave.getFrequency()));
    }

    public float[] makeSinBuffer() {

        //float frequency = sinWave.getFrequency();
        //float amplitude = sinWave.getAmplitude();
        double initialPhase = sinWave.getInitialPhase();
        float[] buffer = new float[duration];
        //FrequencyDynamic dynamic = new FrequencyDynamic(frequency, 10000 + (int)frequency);
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = (float) (Math.sin(twoPI *
                    (frequency.create() * i * dt + initialPhase + phase)) * amplitude.create(i));
        }
        phase = countPhase(frequency.getLastValue(), phase);
        return buffer;
    }

    /*private float[] prepareAmplitudeDynamic() {
        float[] amplitudeDynamic = new float[duration];
        DynamicAmplitude dynamicAmplitude = new DynamicAmplitude(sinWave.getFrequency());
        for (int i = 0; i < duration; i++)
            amplitudeDynamic[i] = dynamicAmplitude.applyEffect(i);
        return amplitudeDynamic;
    }

    private float[] prepareDefaultAmplitude() {
        float[] amplitudeDynamic = new float[duration];
        for (int i = 0; i < duration; i++)
            amplitudeDynamic[i] = sinWave.getAmplitude();
        return amplitudeDynamic;
    }

    private float[] prepareFrequencyDynamic() {
        float[] frequencyDynamic = new float[duration];
        DynamicFrequency dynamic = new DynamicFrequency(sinWave.getFrequency(), 10000 + (int) sinWave.getFrequency());
        for (int i = 0; i < duration; i++)
            frequencyDynamic[i] = dynamic.create(i);
        return frequencyDynamic;
    }

    private float[] prepareDefaultFrequency() {
        float[] frequencyDynamic = new float[duration];
        for (int i = 0; i < duration; i++)
            frequencyDynamic[i] = sinWave.getFrequency();
        return frequencyDynamic;
    }*/

    private float countPhase(float frequency, float p) {
        float T = 1 / frequency;
        phase = p + d / T;
        phase -= (int) phase;
        return phase;
    }

    @Override
    public float[] call() {
        return makeSinBuffer();
    }
}
