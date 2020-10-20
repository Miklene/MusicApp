package com.example.musicapp.buffer;

import com.example.musicapp.sound_effect.AmplitudeDynamic;
import com.example.musicapp.wave.Wave;

import java.util.ArrayList;
import java.util.concurrent.*;

public class ComplexWaveBuffer {
    Wave wave;
    ReadyBuffers readyBuffers;
    SinWaveBuffer mainToneBuffer;
    SinWaveBuffer harmonicBuffer;

    ArrayList<SinWaveBuffer> sinWaveBuffers = new ArrayList<>();
    int duration;

    public ComplexWaveBuffer(Wave wave, int duration) {
        this.duration = duration;
        this.wave = wave;
        int harmonicsNumber = wave.getWaveHarmonics().size();
        sinWaveBuffers.add((new SinWaveBuffer(wave.getMainTone(), duration)));
        for (int i = 0; i < harmonicsNumber; i++) {
            sinWaveBuffers.add(new SinWaveBuffer(wave.getWaveHarmonics().get(i), duration));
        }
    }

    public float[] createBufferSingleThread() {
        float[] buffer;
        int harmonicsNumber = wave.getWaveHarmonics().size();
        readyBuffers = new ReadyBuffers(harmonicsNumber);
        //mainToneBuffer = new SinWaveBuffer(wave.getMainTone(), duration, readyBuffers);
        for (int i = 0; i < sinWaveBuffers.size(); i++) {
            //harmonicBuffer = new SinWaveBuffer(wave.getWaveHarmonics().get(i), duration, readyBuffers);
            readyBuffers.putBuffer(sinWaveBuffers.get(i).makeSinBuffer());
        }
        for (int i = 0; i < harmonicsNumber; i++) {
            readyBuffers.putBuffer(addBuffer(readyBuffers.getBuffer(), readyBuffers.getBuffer()));
        }
        buffer = readyBuffers.getBuffer();
        //AmplitudeDynamic amplitudeDynamic = new AmplitudeDynamic();
        //amplitudeDynamic.applyEffect(buffer);
        return correctAmplitude(buffer);
    }



    public float[] createBufferMultiThread() {
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
        return correctAmplitude(buffer);
    }

    private float[] correctAmplitude(float[] buffer) {
        float correctionValue = findMaxAmplitude(buffer)*1.2f;
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = buffer[i] / correctionValue;
        }
        return buffer;
    }

    private float findMaxAmplitude(float[] buffer) {
        float max;
        max = buffer[0];
        for (int i = 0; i < buffer.length; i++) {
            if (max < buffer[i])
                max = buffer[i];
        }
        return max;
    }

    private float[] addBuffer(float[] buf1, float[] buf2) {
        int duration = buf1.length;
        for (int i = 0; i < duration; i++)
            buf1[i] += buf2[i];
        return buf1;
    }

    private synchronized void tryAddBuffer() {
        if (readyBuffers.getSize() > 1)
            readyBuffers.putBuffer(addBuffer(readyBuffers.getBuffer(), readyBuffers.getBuffer()));
    }
}
