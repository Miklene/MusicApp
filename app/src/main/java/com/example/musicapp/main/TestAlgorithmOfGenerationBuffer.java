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

public class TestAlgorithmOfGenerationBuffer {

    //Wave wave = WaveFactory.createWave(Type type, Frequency 2000, harmonicsNumber 15 23 31, tabled 0);

    Wave[] waves = new Wave[3];
    int[] testedDuration = {1000, 2000, 5000};
    int[] testedHarmonicsNumber = {15, 23, 31};
    //ComplexWaveBuffer complexWaveBuffer = new ComplexWaveBuffer(WaveFactory.createWave(Type.VIOLIN, 200, 31. 0), duration 1000);
    //ComplexWaveBuffer[] complexWaveBuffer = new ComplexWaveBuffer[9];
    WaveBuffer[] wavesBuffers = new WaveBuffer[9];

    //int items = 1000; //duration


    public void speedTestByMeSingle() {
        long start;
        long finish;
        long singleResult;

        String result;
        // Toast toast;

//        ComplexWaveBuffer complexWaveBuffer = new ComplexWaveBuffer(
//                WaveFactory.createWave(Type.VIOLIN, 200, 31, 0), items);

//        for (int i = 0; i < testedDuration.length; i++) {       //вынести как отдельный метод
//            Settings.duration = testedDuration[i];
//            for (int j = 0; j < testedHarmonicsNumber.length; j++) {
//                constructWaves();
//                ComplexWaveBuffer complexWaveBuffer = new ComplexWaveBuffer(waves[j], testedDuration[i]);
//            }
//        }
//        Settings.duration = testedDuration[0];

        constructWaveBufferSingleThread();

        //по сути и это можно в отдельный метод \\ sorry, but no
        for (int i = 0, j = 0; i < wavesBuffers.length && j < testedDuration.length; i++, j++) {
            start = System.nanoTime();
            //вынести как отдельный метод #1
            generateWaveBufferSingleThread(i);
            // конец метода #1
            finish = System.nanoTime();

            singleResult = finish - start;
            result = "Single thread " + testedDuration[j] * 2 + ", testedDuration[duration: " + testedDuration[j] + ". №:" + j + "]: " + singleResult / 1000;
            System.out.println(result);
            //result = String.valueOf(singleResult / 1000);
            //System.out.println(result);
            //toast = Toast.makeText(this, result, Toast.LENGTH_LONG);
            //toast.show();
        }
    }

    public void speedTestByMeMulti() {
        long start;
        long finish;
        long multiResult;

        String result;
        //Toast toast;

        constructWaveBufferMultiThread();

        for (int i = 0, j = 0; i < wavesBuffers.length && j < testedDuration.length; i++, j++) {
            start = System.nanoTime();
            // метод #2
//        for (int i = 0; i < 1000; i++) {
//            complexWaveBuffer.createBufferMultiThread();
//        }

            generateWaveBufferMultiThread(i);
            // конец метода #2
            finish = System.nanoTime();

            multiResult = finish - start;
            System.out.println("Multi thread " + ", testedDuration[duration: " + testedDuration[j] * 2 + ". №:" + j + "]: " + multiResult / 1000);
            //result = String.valueOf(singleResult / 1000 - multiResult / 1000);
            //result = String.valueOf(multiResult / 1000);
            //toast = Toast.makeText(this, result, Toast.LENGTH_LONG);
            //toast.show();
            //System.out.println(result);
        }
    }

    private void constructWaves() {
        for (int i = 0; i < testedHarmonicsNumber.length; i++)
            waves[i] = WaveFactory.createWave(Type.VIOLIN, 200, testedHarmonicsNumber[i], 0);
    }

//    private void constructComplexWaveBuffer() {
//        for (int i = 0; i < testedDuration.length; i++) {
//            Settings.duration = testedDuration[i];
//            constructWaves();
//            for (int j = 0; j < testedHarmonicsNumber.length; j++) {
//
//                //complexWaveBuffer[i] = new ComplexWaveBuffer(waves[j], testedDuration[i]);
//                /*waveBuffers[i] = new WaveBufferMultiThread(waves[j],  testedDuration[i]);
//                waveBuffers[i] = new WaveBufferSingleThread(waves[j],  testedDuration[i]);
//                waveBuffers[i].createBuffer();*/
//                wavesBuffers[i] = new WaveBufferMultiThread(waves[i],  testedDuration[i]);
//                wavesBuffers[i] = new WaveBufferSingleThread(waves[i],  testedDuration[i]);
//                wavesBuffers[i].createBuffer();
//
//            }
//        }
//    }

    private void constructWaveBufferSingleThread() {
        for (int i = 0/*, j = 0 */; i < testedHarmonicsNumber.length /*&& j < testedHarmonicsNumber.length*/; i++/*, j++*/) {
            constructWaves();
            Settings.duration = testedDuration[i];
            for (int j = 0, w = 0; j < testedDuration.length && w < waves.length; j++, w++) {
                wavesBuffers[w] = new WaveBufferSingleThread(waves[i], testedDuration[j]);
               // wavesBuffers[w].createBuffer();
            }
        }
    }

    private void constructWaveBufferMultiThread(){
        for (int i = 0/*, j = 0 */; i < testedHarmonicsNumber.length /*&& j < testedHarmonicsNumber.length*/; i++/*, j++*/) {
            constructWaves();
            Settings.duration = testedDuration[i];
            for (int j = 0, w = 0; j < testedDuration.length && w < waves.length; j++, w++) {
                wavesBuffers[w] = new WaveBufferMultiThread(waves[i], testedDuration[j]);
              //  wavesBuffers[w].createBuffer();
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

//    private void determineComplexWaveBufferSingleThread() {
//        long start;
//        long finish;
//        long multiResult;
//
//        String result;
//
//        for (int i = 0; i < complexWaveBuffer.length; i++) {
//            start = System.nanoTime();
//
//            generateComplexWaveBufferMultiThread(i);
//
//            finish = System.nanoTime();
//
//            multiResult = finish - start;
//            System.out.println("Multi thread " + items * 2 + " items: " + multiResult / 1000);
//            result = String.valueOf(multiResult / 1000);
//            System.out.println(result);
//        }
//    }
//
//    private void determineComplexWaveBufferMultiThread() {
//        long start;
//        long finish;
//        long multiResult;
//
//        String result;
//
//        for (int i = 0; i < complexWaveBuffer.length; i++) {
//            start = System.nanoTime();
//
//            finish = System.nanoTime();
//
//            multiResult = finish - start;
//            System.out.println("Multi thread " + items * 2 + " items: " + multiResult / 1000);
//            //result = String.valueOf(singleResult / 1000 - multiResult / 1000);
//            result = String.valueOf(multiResult / 1000);
//            //toast = Toast.makeText(this, result, Toast.LENGTH_LONG);
//            //toast.show();
//            System.out.println(result);
//        }
//    }


}