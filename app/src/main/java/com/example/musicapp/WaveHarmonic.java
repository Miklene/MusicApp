package com.example.musicapp;

import android.os.Parcel;
import android.os.Parcelable;

public class WaveHarmonic implements Parcelable {
    private float frequency;
    private int amplitude;
    private double phase;
    private short[] buffer;



    public WaveHarmonic(float frequency, int amplitude) {
        this.frequency = frequency;
        this.amplitude = amplitude;
    }

    protected WaveHarmonic(Parcel in) {
        frequency = in.readFloat();
        amplitude = in.readInt();
        phase = in.readDouble();
    }

    public static final Creator<WaveHarmonic> CREATOR = new Creator<WaveHarmonic>() {
        @Override
        public WaveHarmonic createFromParcel(Parcel in) {
            return new WaveHarmonic(in);
        }

        @Override
        public WaveHarmonic[] newArray(int size) {
            return new WaveHarmonic[size];
        }
    };

    public float getFrequency() {
        return frequency;
    }

    public void setFrequency(float frequency) {
        this.frequency = frequency;
    }

    public float getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(int amplitude) {
        this.amplitude = amplitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(frequency);
        dest.writeInt(amplitude);
        dest.writeDouble(phase);
    }

    public short[] getBuffer() {
        return buffer;
    }
    public void setBuffer(short[] buffer) {
        this.buffer = buffer;
    }

    public double getPhase() {
        return phase;
    }

    public void setPhase(double phase) {
        this.phase = phase;
    }
}
