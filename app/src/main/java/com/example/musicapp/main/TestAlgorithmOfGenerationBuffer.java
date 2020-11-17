package com.example.musicapp.main;

import com.example.musicapp.buffer.WaveBuffer;
import com.example.musicapp.buffer.WaveBufferMultiThread;
import com.example.musicapp.buffer.WaveBufferSingleThread;
import com.example.musicapp.common.TypeOfBuffer;
import com.example.musicapp.model.Settings;
import com.example.musicapp.model.WaveBufferBuilder;
import com.example.musicapp.wave.Wave;
import com.example.musicapp.common.Type;
import com.example.musicapp.wave.WaveFactory;

class WaveBuffersSpeedTest {

    private int[] testedDuration = {1000, 2000, 5000};
    private int[] testedHarmonicsNumber = {15, 23, 31};
    private Wave[] waves = new Wave[3];
    private WaveBuffer[] wavesBuffers = new WaveBuffer[testedHarmonicsNumber.length * testedDuration.length];

    public void WaveBuffersSpeedTest() {
        speedTestSM(TypeOfBuffer.SINGLE);
        speedTestSM(TypeOfBuffer.MULTI);
    }

    private void speedTestSM(TypeOfBuffer type) {
        long start;
        long finish;
        long multiResult;
        String result;
        constructWaveBuffer(type);
        StringBuilder speedTestMulti = new StringBuilder();
        for (int i = 0; i < wavesBuffers.length; i++) {
            start = System.nanoTime();
            generateWaveBuffer(i);
            finish = System.nanoTime();
            multiResult = finish - start;
            result = type + " " + testedDuration[i % testedDuration.length] * 2 + " and " +
                    testedHarmonicsNumber[i / testedDuration.length] + ", : " + multiResult / 1000;
            System.out.println(result);
        }
    }

    private void constructWaves() {
        for (int i = 0; i < testedHarmonicsNumber.length; i++)
            waves[i] = WaveFactory.createWave(Type.VIOLIN, 200, testedHarmonicsNumber[i], 0);
    }

    private void constructWaveBuffer(TypeOfBuffer type) {
        constructWaves();
        Settings.currentWaveBuffer = type;
        WaveBufferBuilder waveBufferBuilder = new WaveBufferBuilder();
        for (int i = 0; i < wavesBuffers.length; i++) {
            Settings.duration = testedDuration[i % testedDuration.length];
            wavesBuffers[i] = waveBufferBuilder.getWaveBuffer(waves[i / testedDuration.length], testedDuration[i % testedDuration.length]);

        }
    }

    private void generateWaveBuffer(int item) {
        for (int i = 0; i < 1000; i++) {
            wavesBuffers[item].createBuffer();
        }
    }

}