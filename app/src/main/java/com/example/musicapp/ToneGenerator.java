package com.example.musicapp;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;


public class ToneGenerator {
    protected final int sampleRate = 44100;// = 44100 ;
    protected int duration = 10000;
    protected double frequency;
    protected boolean stereo;
    protected double waveVolume;
    protected short[] buffer;
    protected double systemVolume;
    protected String type;
    AudioTrack audioTrack;
    boolean bufferPlay;
    private Buffer mBuffer;
    //Context context;
    Thread thread;

    ToneGenerator() {
        MainActivity mainActivity = new MainActivity();
        float[] volume = mainActivity.getVolume();
        duration = 16384;
        int frequency = 500;
        int frame;
        buffer = new short[duration];
        frame = sampleRate / frequency;
        for (int i = 0; i < duration; i++) {
            buffer[i] = (short) ((float) (Math.sin(2 * Math.PI * i / frame) * Short.MAX_VALUE * volume[0])
                    + Math.sin(2 * Math.PI * i / (frame) * Short.MAX_VALUE * volume[1]))//* 0.91
                    /*+ Math.sin(2 * Math.PI * i / (float)(period/3))*Short.MAX_VALUE*0.92
                    + Math.sin(2 * Math.PI * i / (float)(period/4))*Short.MAX_VALUE*0.86
                    + Math.sin(2 * Math.PI * i / (float)(period/5))*Short.MAX_VALUE*0.86
                    + Math.sin(2 * Math.PI * i / (float)(period/6))*Short.MAX_VALUE*0.79*/;
        }
    }

    public ToneGenerator(Buffer mBuffer) {
        this.mBuffer = mBuffer;
       // this.context = context;
    }

    ToneGenerator(ArrayList<Wave> waveArrayList, int systemVolume) {
        this.frequency = frequency;
        this.waveVolume = waveVolume;
        this.stereo = stereo;
        this.type = type;
        this.systemVolume = systemVolume;
    }

    ToneGenerator(double frequency, int waveVolume, boolean stereo, String type, int systemVolume) {
        this.frequency = frequency;
        this.waveVolume = waveVolume;
        this.stereo = stereo;
        this.type = type;
        this.systemVolume = systemVolume;
    }

    public void makeBuffer() {
        buffer = new short[duration];
        double frame = sampleRate / frequency;
        for (int i = 0; i < duration; i++) {
            buffer[i] = (short) (Math.sin(Math.PI * i / frame) * (waveVolume / 100) * systemVolume);
        }
    }

    ToneGenerator(double frequency, int duration, float[] volume, int vol, float[] frequencyGain) {
        //MainActivity mainActivity = new MainActivity();
        //float[] frequencyGain = mainActivity.getFrequencyGain();
        double frame;
        //duration = 16384;
        buffer = new short[duration];
        frame = sampleRate / frequency;
        for (int i = 0; i < duration; i++) {
            buffer[i] = (short) (Math.sin(Math.PI * i / frame / frequencyGain[0]) * vol * volume[0]
                    //+ Math.sin(2 * Math.PI * i / (float) (frame /frequencyGain[1])) * vol * volume[1]//0.91
                    /*+ Math.sin(2 * Math.PI * i / (float)(period/3))*Short.MAX_VALUE*0.92
                    + Math.sin(2 * Math.PI * i / (float)(period/4))*Short.MAX_VALUE*0.86
                    + Math.sin(2 * Math.PI * i / (float)(period/5))*Short.MAX_VALUE*0.86
                    + Math.sin(2 * Math.PI * i / (float)(period/6))*Short.MAX_VALUE*0.79*/);
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void makeMonoSound() {
        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, sampleRate,
                AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, buffer.length,
                AudioTrack.MODE_STREAM);
        audioTrack.write(buffer, 0, buffer.length, AudioTrack.WRITE_BLOCKING);
        audioTrack.play();

    }



    public void soundStop() {
        if (audioTrack != null) {
            audioTrack.stop();
            audioTrack.release();
            bufferPlay = false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void makeStereoSound() {
     /*   audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, mBuffer.getSampleRate(),
                AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT, mBuffer.getBuffer().length,
                AudioTrack.MODE_STREAM);
        audioTrack.write(mBuffer.getBuffer(), 0, mBuffer.getBuffer().length, AudioTrack.WRITE_NON_BLOCKING);
        audioTrack.play();
        bufferPlay = true;
        audioTrack.setNotificationMarkerPosition((mBuffer.getBuffer().length));
     //   audioTrack.setPositionNotificationPeriod(((mBuffer.getBuffer().length)/8)-100);
        audioTrack.setPlaybackPositionUpdateListener(new AudioTrack.OnPlaybackPositionUpdateListener() {
            @Override
            public void onMarkerReached(AudioTrack track) {
                if(bufferPlay) {
                    audioTrack.pause();
                    audioTrack.stop();
                    audioTrack.release();
                    makeStereoSound();
                }
            }
            @Override
            public void onPeriodicNotification(AudioTrack track) {

            }
        });*/

        thread = new Thread(new Runnable() {
            public void run() {
                float f = 250;
                float p = 0;
                while (!Thread.currentThread().isInterrupted()) {
                 //   generate(f, p);
                    if (audioTrack != null) {
                        audioTrack.write(mBuffer.getBuffer(), 0,
                                mBuffer.getBuffer().length, AudioTrack.WRITE_NON_BLOCKING);
                    } else {
                        try {
                            audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, mBuffer.getSampleRate(),
                                    AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT,
                                    mBuffer.getBuffer().length, AudioTrack.MODE_STREAM);
                        } catch (IllegalArgumentException e) {
                            return;
                        }
                        audioTrack.write(mBuffer.getBuffer(), 0, mBuffer.getBuffer().length, AudioTrack.WRITE_NON_BLOCKING);
                        try {
                            audioTrack.play();
                        } catch (IllegalStateException e) {
                            return;
                        }
                    }
                }
            }
        });
        thread.start();
    }

    public boolean isBufferPlay(){
        return bufferPlay;
    }

    public AudioTrack getAudioTrack(){
        return audioTrack;
    }

    public void makeSixStereoWaves(double waveFrequency, int duration, float[] volume, float[] frequencyGain) {
        double frame;
        float[] buffer = new float[duration];
        frame = sampleRate / waveFrequency;
        //MainActivity mainActivity = new MainActivity();
        //float[] volume = mainActivity.getVolume();
        //float[] frequencyGain = mainActivity.getFrequencyGain();
        for (int i = 0; i < duration; i++) {
            buffer[i] = (float) (Math.sin(2 * Math.PI * i / frame / frequencyGain[0]) * Short.MAX_VALUE * volume[0]
                    + Math.sin(2 * Math.PI * i / (float) (frame / frequencyGain[1])) * Short.MAX_VALUE * volume[1]// 0.91
                    + Math.sin(2 * Math.PI * i / (float) (frame / frequencyGain[2])) * Short.MAX_VALUE * volume[2]//0.92
                    + Math.sin(2 * Math.PI * i / (float) (frame / frequencyGain[3])) * Short.MAX_VALUE * volume[3]// 0.86
                    + Math.sin(2 * Math.PI * i / (float) (frame / frequencyGain[4])) * Short.MAX_VALUE * volume[4]//0.86
                    + Math.sin(2 * Math.PI * i / (float) (frame / frequencyGain[5])) * Short.MAX_VALUE * volume[5]);//0.79);
        }
        AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, sampleRate,
                AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_FLOAT, buffer.length,
                AudioTrack.MODE_STATIC);
        audioTrack.write(buffer, 0, buffer.length, AudioTrack.WRITE_NON_BLOCKING);
        audioTrack.play();
    }

    public void makeSixMonoWaves(double waveFrequency, int duration, float[] volume, float[] frequencyGain) {
        double frame;
        float[] buffer = new float[duration];
        frame = sampleRate / waveFrequency;
        //MainActivity mainActivity = new MainActivity();
        //float[] volume = mainActivity.getVolume();
        //float[] frequencyGain = mainActivity.getFrequencyGain();
        for (int i = 0; i < duration; i++) {
            buffer[i] = (float) (Math.sin(2 * Math.PI * i / frame / frequencyGain[0]) * Short.MAX_VALUE * volume[0]
                    + Math.sin(2 * Math.PI * i / (float) (frame / frequencyGain[1])) * Short.MAX_VALUE * volume[1]// 0.91
                    + Math.sin(2 * Math.PI * i / (float) (frame / frequencyGain[2])) * Short.MAX_VALUE * volume[2]//0.92
                    + Math.sin(2 * Math.PI * i / (float) (frame / frequencyGain[3])) * Short.MAX_VALUE * volume[3]// 0.86
                    + Math.sin(2 * Math.PI * i / (float) (frame / frequencyGain[4])) * Short.MAX_VALUE * volume[4]//0.86
                    + Math.sin(2 * Math.PI * i / (float) (frame / frequencyGain[5])) * Short.MAX_VALUE * volume[5]);//0.79);
        }
        AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, sampleRate,
                AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_FLOAT, buffer.length,
                AudioTrack.MODE_STATIC);
        audioTrack.write(buffer, 0, buffer.length, AudioTrack.WRITE_NON_BLOCKING);
        audioTrack.play();
    }
}
