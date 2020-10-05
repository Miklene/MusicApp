package com.example.musicapp;

import android.os.Parcel;

import com.example.musicapp.common.Type;

public class SawWave extends Wave {
    private int myAmplitude;

    public SawWave(float frequency, int amplitude, int channelsNumber, int harmonicsNumber) {
        this.frequency = frequency;
        this.amplitude = amplitude;
        this.channelsNumber = channelsNumber;
        this.harmonicsNumber = harmonicsNumber;
        myAmplitude = countAmplitude(harmonicsNumber);
        this.type = Type.SAW;
    }

    @Override
    public short[] createBuffer() {

        return buffer;
    }

    @Override
    public float[] createFloatBuffer() {
        return new float[0];
    }

    private int countAmplitude(int numberOfHarmonics) {
        float x = 1, x2 = 0;
        int maxAmplitude = 0x7FFF;
        for (int i = 1; i <= numberOfHarmonics; i++) {
            x2 += x / i;
        }
        return (int) (maxAmplitude / x2);
    }

    @Override
    public void setHarmonicsNumber(int harmonicsNumber){
        this.harmonicsNumber = harmonicsNumber;
        myAmplitude = countAmplitude(harmonicsNumber);
    }

    @Override
    public void addHarmonic(WaveHarmonic waveHarmonic) {

    }

    @Override
    public void deleteHarmonic(int position) {

    }

    @Override
    public void changeHarmonic(int position, WaveHarmonic waveHarmonic) {

    }

    public static final Creator<SawWave> CREATOR = new Creator<SawWave>() {
        @Override
        public SawWave createFromParcel(Parcel source) {
            final int sampleRate = 44100;
            float frequency = source.readFloat();
            int amplitude = source.readInt();
            int harmonicsNumber = source.readInt();
            int channelsNumber = source.readInt();
            Type type = Type.SIN;
            int phase;
            return new SawWave(frequency, amplitude, channelsNumber, harmonicsNumber);
        }

        @Override
        public SawWave[] newArray(int size) {
            return new SawWave[0];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
