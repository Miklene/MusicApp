package com.example.musicapp;

import android.os.Parcel;

import java.util.ArrayList;


public class SinWave extends Wave {
    float maxFloatAmplitude;
    int maxAmplitude;
    float[] buff = new float[1000];
    private final float dt = 1f / sampleRate;
    private int duration = 1000;
    private int nSamplesPerChannel;//44100 * duration / 1000;
    private int nSamples;
    private float d;// = samplesPerChannel *dt;
    private Runnable myThread;
    private short[] harmonicBuffer = new short[duration];
    private final double[] degrees = {-10.47, -22.87, 174.82, -113.37, 31.67, -97.76, -147.28, 167.50,
            60.95, -133.69, -81.50, -153.50, 25.63, -60.95, -37.05, 24.03,
            64.57, 52.95, 109.83, -150.09, -167.28, 81.16, -134.89, -52.63,
            -37.29, 136.04, 114.37, -117.72, -110.30, -18.51, -153.00, -89.79};
    private double totalPhase;
    private int counter;


    public SinWave(float frequency, int amplitude, int channelsNumber, int harmonicsNumber) {
        this.frequency = frequency;
        this.amplitude = amplitude;
        this.channelsNumber = channelsNumber;
        this.harmonicsNumber = harmonicsNumber;
        this.type = Type.SIN;
        phase = 0;
    }

    public static final Creator<SinWave> CREATOR = new Creator<SinWave>() {
        @Override
        public SinWave createFromParcel(Parcel source) {
            final int sampleRate = 44100;
            float frequency = source.readFloat();
            int amplitude = source.readInt();
            int harmonicsNumber = source.readInt();
            int channelsNumber = source.readInt();
            Type type = Type.SIN;
            int phase;
            return new SinWave(frequency, amplitude, channelsNumber, harmonicsNumber);
        }

        @Override
        public SinWave[] newArray(int size) {
            return new SinWave[0];
        }
    };

    public void addHarmonic(WaveHarmonic waveHarmonic) {
        waveHarmonics.add(waveHarmonic);
        harmonicsNumber = waveHarmonics.size();
        //checkMaxAmplitude();
    }

    @Override
    public void checkMaxAmplitude() {
        maxAmplitude = countMaxAmplitude();
        createBuffer();
        int max = buffer[0];
        for (int i = 1; i < buffer.length; i++)
            max = Math.max(max, Math.abs(buffer[i]));
        if (max != 0)
            maxAmplitude = (0x7FFF / (int)((max/maxAmplitude)-100));
        maxFloatAmplitude = countFloatMaxAmplitude();
        createFloatBuffer();
        float maxFloat = buff[0];
        for (int i = 1; i < buffer.length; i++)
            maxFloat = Math.max(maxFloat, Math.abs(buff[i]));
        if (maxFloat != 0) {
            float temp = maxFloat / maxFloatAmplitude;
            maxFloatAmplitude = (float)((1 / temp)*0.3);
        }
    }

    public void deleteHarmonic(int position) {
        waveHarmonics.remove(position);
    }

    public void changeHarmonic(int position, WaveHarmonic waveHarmonic) {
        waveHarmonics.set(position, waveHarmonic);
    }


    private double countPhase(float frequency, double p) {
        float T = 1 / frequency;
        double phase = p + d / T;
        phase -= (int) phase;
        return phase;
    }

    private float countFloatMaxAmplitude() {
        float x = 1, x2 = 1;
        float myMaxAmplitude = 1;
        float myAmplitude;
        harmonicsNumber = waveHarmonics.size();
        for (int i = 0; i < harmonicsNumber; i++) {
            x2 += x * waveHarmonics.get(i).getAmplitude();
        }
        myAmplitude = (float) (myMaxAmplitude / x2);
        return myAmplitude;
    }

    private int countMaxAmplitude() {
        float x = 1, x2 = 1;
        float myMaxAmplitude = 0x7FFF;
        int myAmplitude;
        harmonicsNumber = waveHarmonics.size();
        for (int i = 0; i < harmonicsNumber; i++) {
            x2 += x * waveHarmonics.get(i).getAmplitude();
        }
        myAmplitude = (int) (myMaxAmplitude / x2);
        return myAmplitude;
    }

    @Override
    public float[] createFloatBuffer() {
        //float[] buff = new float[1000];
        float[] harmonicBuff = new float[1000];
        // int maxAmplitude;
        //float maxFloatAmplitude;
        counter = 0;
        ArrayList<float[]> waveHarmonicsFloatBuffer = new ArrayList<>();
        nSamplesPerChannel = 1000;// sampleRate * frameSize / 1000;
        nSamples = nSamplesPerChannel * channelsNumber;
        d = nSamplesPerChannel * dt;
        buffer = new short[duration];
        // maxAmplitude = countMaxAmplitude();
        totalPhase = phase + Math.cos(Math.toRadians(degrees[counter]));
        counter++;
        buff = Buffer.makeSinFloatBuffer(dt, frequency, duration, totalPhase, maxFloatAmplitude, (float) amplitude / 100);
        phase = countPhase(frequency, phase);
        harmonicsNumber = waveHarmonics.size();
        for (int i = 0; i < harmonicsNumber; i++) {
            totalPhase = waveHarmonics.get(i).getPhase() + Math.cos(Math.toRadians(degrees[counter]));
            counter++;
            waveHarmonics.get(i).setPhase(countPhase(waveHarmonics.get(i).getFrequency(), waveHarmonics.get(i).getPhase()));
            harmonicBuff = Buffer.makeSinFloatBuffer(dt, waveHarmonics.get(i).getFrequency(), duration, totalPhase, maxFloatAmplitude, (float) waveHarmonics.get(i).getAmplitude() / 100);
            // waveHarmonics.get(i).setBuffer(harmonicBuffer);
            // waveHarmonicsBuffer.add(harmonicBuffer);

            waveHarmonicsFloatBuffer.add(harmonicBuff);
        }
            /*}
       });
       thread.start();*/
        buff = Buffer.addFloatWave(buff, waveHarmonicsFloatBuffer);
        return buff;
    }

    @Override
    public void setWaveHarmonics(ArrayList<WaveHarmonic> waveHarmonics) {
        super.setWaveHarmonics(waveHarmonics);
        checkMaxAmplitude();
    }

    @Override
    public short[] createBuffer() {
        //int maxAmplitude;
        counter = 0;
        ArrayList<short[]> waveHarmonicsBuffer = new ArrayList<>();
        nSamplesPerChannel = 1000;// sampleRate * frameSize / 1000;
        nSamples = nSamplesPerChannel * channelsNumber;
        d = nSamplesPerChannel * dt;
       // buffer = new short[duration];
        //Handler handler = new Handler();
        //maxAmplitude = countMaxAmplitude();
        // totalPhase = phase + Math.cos(Math.toRadians(degrees[counter]));
     /*  Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {*/
        totalPhase = phase + Math.cos(Math.toRadians(degrees[counter]));
        counter++;
        buffer = Buffer.makeSinBuffer(dt, frequency, duration, totalPhase, maxAmplitude, (float) amplitude / 100);
        phase = countPhase(frequency, phase);
        harmonicsNumber = waveHarmonics.size();
        for (int i = 0; i < harmonicsNumber; i++) {
            totalPhase = waveHarmonics.get(i).getPhase() + Math.cos(Math.toRadians(degrees[counter]));
            counter++;
            waveHarmonics.get(i).setPhase(countPhase(waveHarmonics.get(i).getFrequency(), waveHarmonics.get(i).getPhase()));
            harmonicBuffer = Buffer.makeSinBuffer(dt, waveHarmonics.get(i).getFrequency(), duration, totalPhase, maxAmplitude, (float) waveHarmonics.get(i).getAmplitude() / 100);
            waveHarmonics.get(i).setBuffer(harmonicBuffer);
            waveHarmonicsBuffer.add(harmonicBuffer);
        }
            /*}
       });
       thread.start();*/
        buffer = Buffer.addWave(buffer, waveHarmonicsBuffer);
        return buffer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeFloat(frequency);
        parcel.writeInt(amplitude);
        parcel.writeInt(harmonicsNumber);
        parcel.writeInt(channelsNumber);
    }
}
