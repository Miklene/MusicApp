package com.example.musicapp.Deprecated;

import android.os.Parcelable;

import com.example.musicapp.common.Type;

import java.util.ArrayList;

public abstract class Wave implements Parcelable, HarmonicBehavior {




    protected ArrayList<WaveHarmonic> waveHarmonics = new ArrayList<>();
    protected final int sampleRate = 44100;
    protected float frequency;
    protected int amplitude;
    protected int harmonicsNumber;
    protected int channelsNumber;
    protected Type type;
    protected short[] buffer =new short[1000];
    protected double phase;

    public abstract short[] createBuffer();
    public abstract float[] createFloatBuffer();

    public void checkMaxAmplitude(){

    };

    public ArrayList<WaveHarmonic> getWaveHarmonics() {
        return waveHarmonics;
    }
    public short[] getBuffer() {
        return buffer;
    }

    public void setWaveHarmonics(ArrayList<WaveHarmonic> waveHarmonics) {
        this.waveHarmonics = waveHarmonics;
        harmonicsNumber = waveHarmonics.size();
    }

    public void setFrequency(float frequency) {
        this.frequency = frequency;
    }

    public int getSampleRate() {
        return sampleRate;
    }

    public float getFrequency() {
        return frequency;
    }

    public double getAmplitude() {
        return amplitude;
    }

    public int getHarmonicsNumber() {
        return harmonicsNumber;
    }

    public Type getType() {
        return type;
    }

    public int getChannelsNumber() {
        return channelsNumber;
    }

    public double getPhase() {
        return phase;
    }

    public void setPhase(double phase) {
        this.phase = phase;
    }

    public void setHarmonicsNumber(int harmonicsNumber) {
        this.harmonicsNumber = harmonicsNumber;
    }
}
/*public class Wave implements Serializable {
    private final int sampleRate = 44100;
    protected float frequency;
    private boolean stereo;
    protected double waveVolume;
    private String type;
    private Frequency myFrequency;
    private int numberOfHarmonics;

    public Wave(float frequency, boolean stereo, double waveVolume, String type, int numberOfHarmonics) {
        this.frequency = frequency;
        this.stereo = stereo;
        this.waveVolume = waveVolume;
        this.type = type;
        this.numberOfHarmonics = numberOfHarmonics;
    }

    public void setNumberOfHarmonics(int numberOfHarmonics) {
        this.numberOfHarmonics = numberOfHarmonics;
    }

    public int getNumberOfHarmonics() {
        return numberOfHarmonics;
    }

    public String getType() {
        return type;
    }

    public double getWaveVolume() {
        return waveVolume;
    }

    public int getSampleRate() {
        return sampleRate;
    }

    public boolean isStereo() {
        return stereo;
    }

    public float getFrequency() {
        return frequency;
    }

    public void setWaveVolume(int waveVolume) {
        this.waveVolume = waveVolume;
    }

    public void setStereo(boolean stereo) {
        this.stereo = stereo;
    }

    public void setFrequency(float frequency) {
        this.frequency = frequency;
    }
}*/
