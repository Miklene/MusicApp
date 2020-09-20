package com.example.musicapp;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.audiofx.PresetReverb;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class WavePlayer implements frequencyObserver{
    private Wave wave;
    private float[] myPhase = new float[32];
    private Frequency myFrequency;
    float frequency;
    Context context;
    private static float dt = 1f / 44100;
    private int duration = 201;
    private int samplesPerChannel = 1000;//44100 * duration / 1000;
    private  float d = samplesPerChannel *dt;
    private  Buffer mBuffer;
    private ToneGenerator toneGenerator;
    private AudioTrack audioTrack;
    private Thread thread;
    private static final WavePlayer ourInstance = new WavePlayer();
    private boolean frequencyIsChanged = false;
    private boolean numberOfHarmonicsChanged = false;
    private boolean phaseCorrection;
    private int numberOfHarmonics;
    private short[] buffer = new short[1000];

    public static WavePlayer getInstance() {
        return ourInstance;
    }

    private WavePlayer(){
        myFrequency = Frequency.getInstance();
        myFrequency.registerFrequencyObserver(this);
    }

    private float countPhase(float frequency, float p){
        float T = 1 / frequency;
        float phase = p + d / T;
        phase -= (int) phase;
        return phase;
    }

    private void makeBuffer(){
        int duration;
        AudioManager manager = (AudioManager) context
                .getSystemService(Context.AUDIO_SERVICE);
        final int systemVolume = manager.getStreamVolume(AudioManager.STREAM_MUSIC);
        MainActivity mainActivity = MainActivity.activity;
        //duration = mainActivity.getDuration();
        if(myFrequency.getFrequency()!=wave.getFrequency())
            myFrequency.setFrequency(wave.getFrequency());
        mBuffer = new Buffer(wave, systemVolume,myPhase);
        if (wave.getType().equals(Type.SIN))
            wave.setPhase(mBuffer.makeSinBuffer(wave.getFrequency(),wave.getPhase()));
        else
            mBuffer.makeSawBuffer(wave.getFrequency(),wave.getHarmonicsNumber());
    }



    private void playWave(){

        thread = new Thread(new Runnable() {
            PresetReverb reverb;
            @RequiresApi(api = Build.VERSION_CODES.M)
            public void run() {
                float[] buff =new float[1000];
                while (!Thread.currentThread().isInterrupted()) {
                    if(phaseCorrection) {
                        buff = wave.createFloatBuffer();
                        if (audioTrack != null) {
                           /* reverb = new PresetReverb(1, audioTrack.getAudioSessionId());
                            reverb.setPreset(PresetReverb.PRESET_SMALLROOM);
                            reverb.setEnabled(true);*/
                            audioTrack.write(buff,0,buff.length,AudioTrack.WRITE_BLOCKING);
                        } else {
                            try {
                                if(wave.getChannelsNumber()==2) {
                                    audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, mBuffer.getSampleRate(),
                                            AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_FLOAT,
                                            mBuffer.getBuffer().length, AudioTrack.MODE_STREAM);
                                }
                                else {
                                    audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, wave.getSampleRate(),
                                            AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_FLOAT,
                                            buff.length*2, AudioTrack.MODE_STREAM);
                                    if(reverb == null) {
                                        /*reverb = new PresetReverb(1, audioTrack.getAudioSessionId());
                                        reverb.setPreset(PresetReverb.PRESET_SMALLROOM);
                                        reverb.setEnabled(true);*/
                                        }

                                }
                            } catch (IllegalArgumentException e) {
                                return;
                            }
                            audioTrack.write(buff, 0, buff.length,AudioTrack.WRITE_BLOCKING);
                            try {
                            /*Thread thread2 = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    buffer = wave.createBuffer();
                                }
                            });*/
                                audioTrack.play();

                            } catch (IllegalStateException e) {
                                return;
                            }
                        }
                    /*if(phaseCorrection) {
                        int num = wave.getHarmonicsNumber();
                        for (int i = 0; i < num;i++)
                            myPhase[i] = countPhase(wave.getFrequency(), myPhase[i]);
                    }*/
                        if (frequencyIsChanged){
                            //   wave = new Wave(frequency,wave.isStereo(),wave.getWaveVolume(),wave.getType(),wave.getNumberOfHarmonics());
                            frequencyIsChanged =false;
                        }
                        if(numberOfHarmonicsChanged) {
                            // wave = new Wave(wave.getFrequency(), wave.isStereo(), wave.getWaveVolume(), wave.getType(), numberOfHarmonics);
                            numberOfHarmonicsChanged = false;
                        }
                    }
                    else
                        buffer = wave.createBuffer();
                    if (audioTrack != null) {
                        audioTrack.write(buffer, 0,
                                buffer.length);
                    } else {
                        try {
                           if(wave.getChannelsNumber()==2) {
                               audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, mBuffer.getSampleRate(),
                                       AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT,
                                       mBuffer.getBuffer().length, AudioTrack.MODE_STREAM);
                           }
                           else {
                               audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, wave.getSampleRate(),
                                       AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT,
                                       buffer.length * 2, AudioTrack.MODE_STREAM);
                           }
                        } catch (IllegalArgumentException e) {
                            return;
                        }
                        audioTrack.write(buffer, 0, buffer.length);
                        try {
                            /*Thread thread2 = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    buffer = wave.createBuffer();
                                }
                            });*/
                            audioTrack.play();

                        } catch (IllegalStateException e) {
                            return;
                        }
                    }
                    /*if(phaseCorrection) {
                        int num = wave.getHarmonicsNumber();
                        for (int i = 0; i < num;i++)
                            myPhase[i] = countPhase(wave.getFrequency(), myPhase[i]);
                    }*/
                    if (frequencyIsChanged){
                     //   wave = new Wave(frequency,wave.isStereo(),wave.getWaveVolume(),wave.getType(),wave.getNumberOfHarmonics());
                        frequencyIsChanged =false;
                    }
                    if(numberOfHarmonicsChanged) {
                       // wave = new Wave(wave.getFrequency(), wave.isStereo(), wave.getWaveVolume(), wave.getType(), numberOfHarmonics);
                        numberOfHarmonicsChanged = false;
                    }
                }

            }
        });
        thread.start();
    }


 /*   @RequiresApi(api = Build.VERSION_CODES.M)
    private void playWave(){
        if (wave.isStereo()) {
            toneGenerator = new ToneGenerator(mBuffer);
            toneGenerator.makeStereoSound();
        } else {
            toneGenerator = new ToneGenerator(mBuffer);
            toneGenerator.makeMonoSound();
        }
    }*/



    public void stopWavePlayer(){
        interrupt(thread);

        if (audioTrack != null) {
            try {
                audioTrack.pause();
            } catch (IllegalStateException e) {
            }
        }

        wait(thread);

        if (audioTrack != null) {
            audioTrack.flush();
            audioTrack.release();
            audioTrack = null;
        }
        thread = null;
       // myPhase=0;
    }

    private static void interrupt(Thread thread) {
        if (thread != null) {
            thread.interrupt();
        }
    }

    private static void wait(Thread thread) {
        if (thread != null) {
            try {
                thread.join();
            } catch (InterruptedException e) {
            }
        }
    }


    public void updateWavePlayer(Wave wave, Context context, boolean checked){
        if(thread != null)
            return;
        this.wave = wave;
        this.context = context;
        phaseCorrection = checked;
        wave.checkMaxAmplitude();
//        makeBuffer();
        playWave();
    }

    @Override
    public void updateFrequency(float frequency) {
        if(wave == null)
            return;
    //    if(!toneGenerator.isBufferPlay())
    //        return;
       this.frequency = frequency;
        //wave = new Wave(frequency, wave.isStereo(), wave.getWaveVolume(), wave.getType());
       frequencyIsChanged = true;
      //  makeBuffer();
   //     toneGenerator.soundStop();
      //  playWave();
    }



    public short[] getBuffer(Wave wave, Context context, boolean checked){
        this.wave = wave;
        this.context = context;
        phaseCorrection = checked;
        makeBuffer();
        return mBuffer.getBuffer();
    }
}
