package com.example.musicapp.wave_player;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

import com.example.musicapp.buffer.WaveBuffer;
import com.example.musicapp.common.RecordParameters;

public class WavePlayer implements RecordParameters {

    private Thread thread;
    private AudioTrack audioTrack;
    private WaveBuffer waveBuffer;
    private static WavePlayer wavePlayer;

    private WavePlayer() {
    }

    public static WavePlayer getInstance() {
        if (wavePlayer == null)
            wavePlayer = new WavePlayer();
        return wavePlayer;
    }

    public void playWave(WaveBuffer waveBuffer) {
        if (thread != null)
            return;
        this.waveBuffer = waveBuffer;
        play();
    }

    public void play() {
        thread = new Thread(new Runnable() {
            public void run() {
                float[] buffer;
                while (!Thread.currentThread().isInterrupted()) {
                    buffer = waveBuffer.createBuffer();
                    if (audioTrack != null) {
                        audioTrack.write(buffer, 0, buffer.length, AudioTrack.WRITE_BLOCKING);
                    } else {
                        try {
                            audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                                    RecordParameters.sampleRate, AudioFormat.CHANNEL_OUT_STEREO,
                                    AudioFormat.ENCODING_PCM_FLOAT,
                                    buffer.length, AudioTrack.MODE_STREAM);
                        } catch (IllegalArgumentException e) {
                            return;
                        }
                        audioTrack.write(buffer, 0, buffer.length, AudioTrack.WRITE_BLOCKING);
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

    public void updateWaveBuffer(WaveBuffer waveBuffer) {
        this.waveBuffer = waveBuffer;
    }

    public void stopWavePlayer() {
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

    public boolean isPlayed() {
        try {
            return thread.isAlive();
        } catch (NullPointerException e) {
            return false;
        }
    }

    private static void interrupt(Thread thread) {
        if (thread != null) {
            thread.interrupt();
        }
    }

    private static void wait(Thread thread) {
        /*if (thread != null) {
            try {
                //thread.join();
            } catch (InterruptedException e) {
            }
        }*/
    }
}
