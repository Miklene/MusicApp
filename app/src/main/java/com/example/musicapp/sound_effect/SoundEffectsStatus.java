package com.example.musicapp.sound_effect;

public class SoundEffectsStatus {

    public static boolean amplitudeDynamic = true;
    public static boolean normalization = true;
    public static boolean reverberation;
    public static boolean stereo = true;
    public static boolean frequencyDynamic = false;
    public static boolean startPlayback = false;
    public static boolean endPlayback = false;

    /*private static SoundEffectsStatus instance;

    private SoundEffectsStatus() {
        initStatus();
    }

    public static SoundEffectsStatus getInstance(){
        if(instance==null)
            instance = new SoundEffectsStatus();
        return instance;
    }


    private void initStatus(){
        amplitudeDynamic = true;
        normalization = true;
        reverberation = true;
    }

    public void setAmplitudeDynamic(boolean amplitudeDynamic) {
        this.amplitudeDynamic = amplitudeDynamic;
    }

    public void setNormalization(boolean normalization) {
        this.normalization = normalization;
    }

    public void setReverberation(boolean reverberation) {
        this.reverberation = reverberation;
    }

    public boolean isAmplitudeDynamic() {
        return amplitudeDynamic;
    }

    public boolean isNormalization() {
        return normalization;
    }

    public boolean isReverberation() {
        return reverberation;
    }*/
}
