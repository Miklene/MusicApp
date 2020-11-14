package com.example.musicapp.buffer;

import com.example.musicapp.wave.Wave;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class WaveBufferSingleThread extends WaveBuffer {

    private Wave wave;
    private ArrayList<SinWaveBuffer> sinWaveBuffers = new ArrayList<>();
    private int duration;

    public WaveBufferSingleThread(Wave wave, int duration) {
        this.wave = wave;
        this.duration = duration;
        writeSinWaveBuffers();
    }

    @Override
    public float[] createBuffer() {
        int harmonicsNumber = wave.getWaveHarmonics().size();
        Queue<float[]> readyBuffers = new LinkedList<>();
        float[] buffer;
        for (int i = 0; i < sinWaveBuffers.size(); i++) {
            readyBuffers.offer(sinWaveBuffers.get(i).makeSinBuffer());
        }
        for (int i = 0; i < harmonicsNumber; i++) {
            readyBuffers.add(addBuffer((readyBuffers.poll()), readyBuffers.poll()));
        }
        buffer = readyBuffers.poll();
        return buffer;
    }

    private float[] addBuffer(float[] buf1, float[] buf2) {
        int duration = buf1.length;
        for (int i = 0; i < duration; i++)
            buf1[i] += buf2[i];
        return buf1;
    }

    private void writeSinWaveBuffers(){
        int harmonicsNumber = wave.getWaveHarmonics().size();
        sinWaveBuffers.add((new SinWaveBuffer(wave.getMainTone(), duration)));
        for (int i = 0; i < harmonicsNumber; i++) {
            sinWaveBuffers.add(new SinWaveBuffer(wave.getWaveHarmonics().get(i), duration));
        }
    }
}
