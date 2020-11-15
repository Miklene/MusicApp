package com.example.musicapp.main;

import android.widget.Toast;

import com.example.musicapp.Deprecated.Frequency;
import com.example.musicapp.buffer.WaveBuffer;
import com.example.musicapp.buffer.WaveBufferMultiThread;
import com.example.musicapp.buffer.WaveBufferSingleThread;
import com.example.musicapp.model.Settings;
import com.example.musicapp.wave.Wave;
import com.example.musicapp.buffer.ComplexWaveBuffer;
import com.example.musicapp.common.Type;
import com.example.musicapp.wave.WaveFactory;

class WaveBuffersSpeedTest {

    private Wave[] waves = new Wave[3];
    private int[] testedDuration = {1000, 2000, 5000};
    private int[] testedHarmonicsNumber = {15, 23, 31};
    private WaveBuffer[] wavesBuffers = new WaveBuffer[9];

    public void WaveBuffersSpeedTest() {
        speedTestSingle();
        speedTestMulti();
    }

    private void speedTestSingle() {
        long start;
        long finish;
        long singleResult;

        String result;

        constructWaveBufferSingleThread();

        for (int i = 0, j = -1, k = 0; i < wavesBuffers.length && j < testedDuration.length && k < testedHarmonicsNumber.length; i++) {
            if (i % 3 == 0) j++;
            if (i % 3 != 0) k++;
            else k = 0;

            start = System.nanoTime();

            generateWaveBufferSingleThread(i);

            finish = System.nanoTime();

            singleResult = finish - start;
            result = "Single thread " + testedDuration[j] * 2 + " and " + testedHarmonicsNumber[k] +
                    ", testedDuration[duration[" + testedDuration[j] + "] " +
                    "; testedHarmonicsNumber[" + testedHarmonicsNumber[k] + "]]: " + singleResult / 1000;
            System.out.println(result);
        }
    }

    private void speedTestMulti() {
        long start;
        long finish;
        long multiResult;

        String result;

        constructWaveBufferMultiThread();

        for (int i = 0, j = -1, k = 0; i < wavesBuffers.length && j < testedDuration.length && k < testedHarmonicsNumber.length; i++) {
            if (i % 3 == 0) j++;
            if (i % 3 != 0) k++;
            else k = 0;

            start = System.nanoTime();

            generateWaveBufferMultiThread(i);

            finish = System.nanoTime();

            multiResult = finish - start;
            result = "Multi thread " + testedDuration[j] * 2 + " and " + testedHarmonicsNumber[k] +
                    ", testedDuration[duration[" + testedDuration[j] * 2 + "] " +
                    "; testedHarmonicsNumber[" + testedHarmonicsNumber[k] + "]]: " + multiResult / 1000;
            System.out.println(result);
        }
    }

    private void constructWaves() {
        for (int i = 0; i < testedHarmonicsNumber.length; i++)
            waves[i] = WaveFactory.createWave(Type.VIOLIN, 200, testedHarmonicsNumber[i], 0);
    }


    private void constructWaveBufferSingleThread() {
        int w = 0;
        constructWaves();
        for (int i = 0; i < testedHarmonicsNumber.length; i++) {
            Settings.duration = testedDuration[i];
            for (int j = 0; j < testedDuration.length && w < wavesBuffers.length; j++) {
                wavesBuffers[w] = new WaveBufferSingleThread(waves[i], testedDuration[j]);
                if (wavesBuffers[w] == wavesBuffers[w])
                    w++;
            }
        }
    }

    private void constructWaveBufferMultiThread() {
        int w = 0;
        constructWaves();
        for (int i = 0; i < testedHarmonicsNumber.length; i++) {
            Settings.duration = testedDuration[i];
            for (int j = 0; j < testedDuration.length && w < wavesBuffers.length; j++) {
                wavesBuffers[w] = new WaveBufferMultiThread(waves[i], testedDuration[j]);
                if (wavesBuffers[w] == wavesBuffers[w])
                    w++;
            }
        }
    }

    private void generateWaveBufferSingleThread(int j) {
        for (int i = 0; i < 1000; i++) {
            wavesBuffers[j].createBuffer();
        }
    }

    private void generateWaveBufferMultiThread(int j) {
        for (int i = 0; i < 1000; i++) {
            wavesBuffers[j].createBuffer();
        }
    }

}