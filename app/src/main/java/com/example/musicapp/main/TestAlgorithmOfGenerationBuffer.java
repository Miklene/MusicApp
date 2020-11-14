package com.example.musicapp.main;

import android.widget.Toast;

import com.example.musicapp.Deprecated.Frequency;
import com.example.musicapp.model.Settings;
import com.example.musicapp.wave.Wave;
import com.example.musicapp.buffer.ComplexWaveBuffer;
import com.example.musicapp.common.Type;
import com.example.musicapp.wave.WaveFactory;

public class TestAlgorithmOfGenerationBuffer {

    //Wave wave = WaveFactory.createWave(Type type, Frequency 2000, harmonicsNumber 15 23 31, tabled 0);
//    Wave[] wave = WaveFactory.createWave(Type.VIOLIN; int Frequency; int harmonicsNumber; int tabled)[10];
//    wave[0] = WaveFactory.createWave() ;

    Wave wave = WaveFactory.createWave(Type.VIOLIN, 2000, 31, 0);
    //Wave[] wave = WaveFactory.createWave(Type.VIOLIN, 2000, 31, 0);
    Wave[] waves = new Wave[9];
    int[] testedDuration = {1000, 2000, 5000};
    int[] testedHarmonicsNumber = {15, 23, 31};
    //ComplexWaveBuffer complexWaveBuffer = new ComplexWaveBuffer(WaveFactory.createWave(Type.VIOLIN, 200, 31. 0), duration 1000);

    ComplexWaveBuffer complexWaveBuffer = new ComplexWaveBuffer(wave, 1000);

    //String ReceivingStore; // (harmonicsNumber, duration) - принимающие параметры

    int items = 1000;

    private void constructWaves() {
        for (int i = 0; i < testedHarmonicsNumber.length; i++)
            waves[i] = WaveFactory.createWave(Type.VIOLIN, 200, testedHarmonicsNumber[i], 0);
            //ComplexWaveBuffer complexWaveBuffer = new ComplexWaveBuffer(wave, 1000);
    }

    public void speedTestByMeSingle() {
        long start;
        long finish;
        long singleResult;

        String result;
        Toast toast;

//        ComplexWaveBuffer complexWaveBuffer = new ComplexWaveBuffer(
//                WaveFactory.createWave(Type.VIOLIN, 200, 31, 0), items);
        Settings.duration = testedDuration[0];
        start = System.nanoTime();
        //вынести как отдельный метод #1
//        for (int i = 0; i < 1000; i++) {
//            complexWaveBuffer.createBufferSingleThread();
//        }

        generateComplexWaveBufferSingleThread();
        // конец метода #1
        finish = System.nanoTime();
        singleResult = finish - start;
        result = "Single thread " + items * 2 + " items: " + singleResult / 1000;
        System.out.println(result);
        result = String.valueOf(singleResult / 1000);
        //toast = Toast.makeText(this, result, Toast.LENGTH_LONG);
        //toast.show();
    }

    public void speedTestByMeMulti() {
        long start;
        long finish;
        long multiResult;

        String result;
        Toast toast;
        start = System.nanoTime();
        // метод #2
//        for (int i = 0; i < 1000; i++) {
//            complexWaveBuffer.createBufferMultiThread();
//        }
        generateComplexWaveBufferMultiThread();
        // конец метода #2
        finish = System.nanoTime();
        multiResult = finish - start;
        System.out.println("Multi thread " + items * 2 + " items: " + multiResult / 1000);
        //result = String.valueOf(singleResult / 1000 - multiResult / 1000);
        result = String.valueOf(multiResult / 1000);
        //toast = Toast.makeText(this, result, Toast.LENGTH_LONG);
        //toast.show();
    }

    public void generateComplexWaveBufferSingleThread() {
        for (int i = 0; i < 1000; i++) {
            complexWaveBuffer.createBufferSingleThread();
        }
    }

    public void generateComplexWaveBufferMultiThread() {
        for (int i = 0; i < 1000; i++) {
            complexWaveBuffer.createBufferMultiThread();
        }
    }

}
