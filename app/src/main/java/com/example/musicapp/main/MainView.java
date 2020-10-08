package com.example.musicapp.main;

import android.os.Bundle;

import com.example.musicapp.main.MainFragment;

public interface MainView {

    void displayFragment(MainFragment mainFragment, Bundle bundle);

    void removeFragment();

    void startWaveTunerActivity(int waveId);

    void startDialogWaveActivity();

    void updateFragment(float frequency, int harmonicsNumber);


}
