package com.example.musicapp.wav;

public class WavFile {
    private WavHeader wavHeader;
    private float[] data;

    public WavFile(WavHeader wavHeader, float[] data) {
        this.wavHeader = wavHeader;
        this.data = data;
    }

    public WavHeader getWavHeader() {
        return wavHeader;
    }

    public float[] getData() {
        return data;
    }
}
