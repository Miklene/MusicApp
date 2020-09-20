package com.example.musicapp;


import java.util.ArrayList;

public class Buffer{
    static Reverberation reverberation =Reverberation.getInstance();
    private static final double twoPI = 2 * Math.PI;
    private short[] buffer;
    private double systemVolume;
    private Wave wave;
    private float[] phase;
    private static final double[] realPhase = new double[8];
    private static float echo = 0;

    public Buffer(Wave wave, double systemVolume, float[] phase) {
        this.wave = wave;
        this.systemVolume = systemVolume;
        this.phase = phase;
    }


    public static float[] makeSinFloatBuffer(float dt, float frequency, int duration, double phase, float maxAmplitude, float amplitude) {
        float[] mBuffer = new float[duration];
        for (int i = 0; i < duration; i++) {
            mBuffer[i] = (float) ( maxAmplitude*(Math.sin(twoPI * (frequency * i * dt + phase)) * amplitude));// * (wave.getWaveVolume()) * systemVolume);
            reverberation.addEcho(mBuffer[i]);
            mBuffer[i] += echo;

        }
        return mBuffer;
    }

    public static short[] makeSinBuffer(float dt, float frequency, int duration, double phase, int maxAmplitude, float amplitude) {
        short[] mBuffer = new short[duration];
        for (int i = 0; i < duration; i++) {
            mBuffer[i] = (short) ((maxAmplitude * (Math.sin(twoPI * (frequency * i * dt + phase)) * amplitude)));// * (wave.getWaveVolume()) * systemVolume);
        }
        return mBuffer;
    }

    public static float[] addFloatWave(float[] buff, ArrayList<float[]> waveHarmonicsBuffer) {
        int size = buff.length;
        int inv;
        int harmonicNumber = waveHarmonicsBuffer.size();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < harmonicNumber; j++)
                buff[i] = (float) (buff[i] + waveHarmonicsBuffer.get(j)[i]);
        }
        return buff;
    }

    public static short[] addWave(short[] buff, ArrayList<short[]> waveHarmonicsBuffer) {
        int size = buff.length;
        int inv;
        int harmonicNumber = waveHarmonicsBuffer.size();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < harmonicNumber; j++)
                buff[i] = (short) (buff[i] + waveHarmonicsBuffer.get(j)[i]);
        }
        return buff;
    }
   /* public static short[] addWave(){

    }*/

    public double makeSinBuffer(float frequency, double phase) {
        float dt = 1f / wave.getSampleRate();
        //double frame =wave.getSampleRate()/ frequency;
        //duration = wave.getSampleRate() * 201 / 1000;
        int frameSize = (int) (wave.getSampleRate() / frequency) + 1;
        int duration;
        int size = frameSize + 1;
        int framesNumber = 1000 / frameSize;
        int fullSize = frameSize * framesNumber;
        int correction = 0;
        //  duration *= frame;
        //  kolvo = (duration / kolvo) + 1;
        duration = frameSize;
        buffer = new short[fullSize];
        //for (int j = 0; j < 100; j++)
        for (int i = 0; i <= duration; i++) {
            buffer[i] = (short) (0x7AFF * Math.sin(2 * Math.PI * (frequency * i * dt)) + phase);// * (wave.getWaveVolume()) * systemVolume);
        }
        duration = frameSize - 1;
        if (buffer[duration] < 0 && buffer[duration + 1] > 0) {
            phase = buffer[duration + 1];
        }
        if (buffer[duration] < 0 && buffer[duration + 1] < 0) {
            phase = buffer[duration + 1];
            correction++;
        }
        if (buffer[duration] > 0 && buffer[duration + 1] > 0) {
            phase = buffer[duration + 1];
            correction--;
        }
        //buffer[size - 1] = 0;
        int k = frameSize;
        duration += correction;
        for (int j = 0; j < framesNumber - 1; j++) {
            for (int i = 0; i <= duration; i++)
                buffer[i + k] = (short) (buffer[i] + phase);
            if (buffer[duration - 1 + k] < 0 && buffer[duration + k] > 0) {
                phase = buffer[duration + k];
            }
            if (buffer[duration - 1 + k] < 0 && buffer[duration + k] < 0) {
                phase = buffer[duration + k];
                correction++;
            }
            if (buffer[duration - 1 + k] > 0 && buffer[duration + k] < 0) {
                phase = buffer[duration + k];
                correction--;
            }
            k += duration;
            duration += correction;
        }


      /*  for (int i = duration-10; i < duration; i++) {
            buffer[i] =0;
        }*/
        return phase;
    }

    private int countAmplitude(int numberOfHarmonics) {
        float x = 1, x2 = 0;
        float maxAmplitude = 0x7FFF;
        int myAmplitude;
        for (int i = 1; i <= numberOfHarmonics; i++) {
            x2 += x / i;
        }
        myAmplitude = (int) (maxAmplitude / x2);
        return myAmplitude;
    }

    public void makeSawBuffer(float frequency, int numberOfHarmonics) {
        byte inv = 1;
        float dt = 1f / wave.getSampleRate();
        int frameSize = (int) (wave.getSampleRate() / frequency) + 1;
        //int size = frameSize + 1;
        int framesNumber = 1000 / frameSize;
        int fullSize = frameSize * framesNumber;
        //duration =wave.getSampleRate() * 201 / 1000;
        //duration = 1000;
        //int size = numberOfHarmonics * duration;
        //duration =1000;
        int myAmplitude = countAmplitude(numberOfHarmonics);
        short[] buffer2 = new short[frameSize * numberOfHarmonics];
        //    short[] buffer3 = new short[duration];
        //    short[] buffer4 = new short[duration];
        buffer = new short[fullSize];

       /*for (int j = 0; j < 10; j++) {
            for (int i = 0; i < duration; i++) {
                buffer2[i][j] = (short) (0x7FFF * (Math.sin(2 * Math.PI * (frequency * numberOfHarmonic * i * dt + phase))/ numberOfHarmonic));// * (wave.getWaveVolume()) * systemVolume);
            }
            numberOfHarmonic++;
        }*/
        int k, k2, k3;
        for (int j = 0; j < numberOfHarmonics; j++) {
            k3 = j + 1;
            k2 = j * frameSize;
            for (int i = 0; i < frameSize; i++) {
                buffer2[i + k2] = (short) (myAmplitude * Math.sin(2 * Math.PI * (frequency * k3 * i * dt /*+ phase*/)) / k3);
            }
            buffer2[k2 + frameSize - 1] = 0;
        }
        for (int i = 0; i < frameSize; i++) {
            for (int j = 0; j < numberOfHarmonics; j++) {
                k = frameSize * (j + 1);
                k2 = frameSize * j;
                if (buffer2[i % k] < 0)
                    inv = -1;
                else
                    inv = 1;
                buffer[i] = (short) (inv * (Math.sqrt(Math.pow(buffer[i], 2) + Math.pow(buffer2[k2 + i], 2) + 2 * buffer[i] * buffer2[k2 + i])));
            }
        }
        for (int j = 1; j < framesNumber; j++) {
            int k4 = j * frameSize;
            for (int i = 0; i < frameSize; i++)
                buffer[i + k4] = buffer[i];
        }
        //double frame =wave.getSampleRate()/ frequency;
      /*  buffer = new short[duration];
        double period =wave.getSampleRate() / (wave.getFrequency()/2);
        int c=0;
        for (int i = 0; i < duration; i++) {
            if(c>=(period))
                c=0; //если период закончился, то начать следующий
            //buffer[i]= (short)(0x7FFF*(c/(period))); //вычисление волны
            buffer[i]= (short)(((wave.getWaveVolume()/100) * systemVolume) *(c/period)-((wave.getWaveVolume()/100) * systemVolume)); //вычисление волны
            //buffer[i] = (float) (Math.sin(Math.PI * c / period) * (wave.getWaveVolume()/100) * systemVolume);
            c++;
        }/*
            buffer[i] = (float)((float)(2/Math.PI)*(Math.sin(Math.PI*i / period)  -
                    1/2*Math.sin( Math.PI*i * 2 / period) +
                    1/3*Math.sin( Math.PI*i * 3 / period) -
                    1/4*Math.sin(Math.PI* i * 4 / period) +
                    1/5*Math.sin( Math.PI*i * 5 / period) -
                    1/6*Math.sin(Math.PI* i * 6 / period) +
                    1/7*Math.sin(Math.PI* i * 7 / period)) * (wave.getWaveVolume()/100) * systemVolume);
                    }*/
    }

    public short[] getBuffer() {
        return buffer;
    }


    public int getSampleRate() {
        return wave.getSampleRate();
    }

    public static void setEcho(float e) {
        echo = e;
    }
}
