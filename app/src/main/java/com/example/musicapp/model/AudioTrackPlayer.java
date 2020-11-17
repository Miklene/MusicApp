package com.example.musicapp.model;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

import com.example.musicapp.buffer.WaveBuffer;
import com.example.musicapp.common.RecordParameters;
import com.example.musicapp.common.Type;
import com.example.musicapp.sound_effect.SoundEffectsStatus;
import com.example.musicapp.wave.Wave;
import com.example.musicapp.wave.WaveFactory;

public class AudioTrackPlayer implements Runnable {

    private float[] buffer;
    private WaveBuffer waveBuffer;
    private AudioTrack audioTrack;
    //Wave wave = WaveFactory.createWave(Type.VIOLIN, 200,0,0);

    public AudioTrackPlayer(WaveBuffer waveBuffer) {
        this.waveBuffer = waveBuffer;
    }

    void playAudioTrack(){
        audioTrack.play();
    }

    void writeAudioTrack(float[] buffer){
        audioTrack.write(buffer, 0, buffer.length, AudioTrack.WRITE_BLOCKING);
    }

    void createAudioTrack(float[] buffer){
        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                RecordParameters.sampleRate, AudioFormat.CHANNEL_OUT_STEREO,
                AudioFormat.ENCODING_PCM_FLOAT,
                buffer.length, AudioTrack.MODE_STREAM);
    }

    @Override
    public void run() {
        SoundEffectsStatus.startPlayback=true;
        buffer = waveBuffer.createBuffer();
        SoundEffectsStatus.startPlayback=false;
        createAudioTrack(buffer);
        writeAudioTrack(buffer);
        audioTrack.play();
        while(Settings.isPlayed) {
            buffer = waveBuffer.createBuffer();
            writeAudioTrack(buffer);
        }
        SoundEffectsStatus.endPlayback = true;
        buffer = waveBuffer.createBuffer();
        writeAudioTrack(buffer);
        SoundEffectsStatus.endPlayback = false;
    }
}
