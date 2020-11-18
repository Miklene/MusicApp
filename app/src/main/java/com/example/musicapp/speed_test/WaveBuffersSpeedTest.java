package com.example.musicapp.speed_test;

import com.example.musicapp.buffer.WaveBuffer;
import com.example.musicapp.common.TypeOfBuffer;
import com.example.musicapp.common.Settings;
import com.example.musicapp.buffer.WaveBufferBuilder;
import com.example.musicapp.wave.Wave;
import com.example.musicapp.common.Type;
import com.example.musicapp.wave.WaveFactory;

class WaveBuffersSpeedTest {

    private final int[] testedDuration = {1000, 2000, 5000};
    private final int[] testedHarmonicsNumber = {15, 23, 31};
    private final Wave[] waves = new Wave[testedHarmonicsNumber.length];
    private WaveBuffer[] wavesBuffers = new WaveBuffer[testedHarmonicsNumber.length * testedDuration.length];

    public WaveBuffersSpeedTest() {
        constructWaves();
    }

    public String startTest() {
        String string;
        string = startTestSM(TypeOfBuffer.SINGLE) ;
        string += startTestSM(TypeOfBuffer.MULTI);
        return string;
    }

    private String startTestSM(TypeOfBuffer type) {
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
                    testedHarmonicsNumber[i / testedDuration.length] + ", : " + multiResult / 1000 + "\n";
            speedTestMulti.append(result);
        }
        return speedTestMulti.toString();
    }

    private void constructWaves() {
        for (int i = 0; i < testedHarmonicsNumber.length; i++)
            waves[i] = WaveFactory.createWave(Type.VIOLIN, 200, testedHarmonicsNumber[i], 0);
    }

    private void constructWaveBuffer(TypeOfBuffer type) {
        Settings.currentWaveBuffer = type;
        for (int i = 0; i < wavesBuffers.length; i++) {
            Settings.duration = testedDuration[i % testedDuration.length];
            wavesBuffers[i] = WaveBufferBuilder.getWaveBuffer(waves[i / testedDuration.length], testedDuration[i % testedDuration.length]);
        }
    }

    private void generateWaveBuffer(int item) {
        for (int i = 0; i < 1000; i++) {
            wavesBuffers[item].createBuffer();
        }
    }

}