package com.example.musicapp.buffer;

import com.example.musicapp.wave.Wave;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class WaveBufferMultiThread extends WaveBuffer {

    private Wave wave;
    private ReadyBuffers readyBuffers;
    private ArrayList<SinWaveBuffer> sinWaveBuffers = new ArrayList<>();
    private int duration;

    public WaveBufferMultiThread(Wave wave, int duration) {
        this.wave = wave;
        this.duration = duration;
        writeSinWaveBuffers();
    }

    private void writeSinWaveBuffers() {
        int harmonicsNumber = wave.getWaveHarmonics().size();
        sinWaveBuffers.add((new SinWaveBuffer(wave.getMainTone(), duration)));
        for (int i = 0; i < harmonicsNumber; i++) {
            sinWaveBuffers.add(new SinWaveBuffer(wave.getWaveHarmonics().get(i), duration));
        }
    }

    @Override
    public float[] createBuffer() {
        float[] buffer;
        int harmonicsNumber = wave.getWaveHarmonics().size();
        readyBuffers = new ReadyBuffers(harmonicsNumber);
        ArrayList<Future<float[]>> readyBuffer = new ArrayList<>();
        ExecutorService executorService;
        if (harmonicsNumber == 0)
            executorService = Executors.newFixedThreadPool(1);
        else
            executorService = Executors.newFixedThreadPool(harmonicsNumber / 8);
        for (int i = 0; i < sinWaveBuffers.size(); i++) {
            readyBuffer.add(executorService.submit(sinWaveBuffers.get(i)));
        }
        for (Future<float[]> fs : readyBuffer) {
            try {
                readyBuffers.putBuffer(fs.get());
            } catch (InterruptedException | ExecutionException e) {
                System.out.println(e);
            } finally {
                executorService.shutdown();
            }
        }
        while (readyBuffers.getNumberOfOperations() != 0) {
            tryAddBuffer();
        }
        buffer = readyBuffers.getBuffer();
        executorService.shutdown();
        return buffer;
    }

    private synchronized void tryAddBuffer() {
        if (readyBuffers.getSize() > 1)
            readyBuffers.putBuffer(addBuffer(readyBuffers.getBuffer(), readyBuffers.getBuffer()));
    }

    private float[] addBuffer(float[] buf1, float[] buf2) {
        int duration = buf1.length;
        for (int i = 0; i < duration; i++)
            buf1[i] += buf2[i];
        return buf1;
    }
}
