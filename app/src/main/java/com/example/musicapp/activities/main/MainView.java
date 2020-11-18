package com.example.musicapp.activities.main;

import android.os.Bundle;

public interface MainView {

    void displayFragment(MainFragment mainFragment, Bundle bundle);

    void removeFragment();

    void startWaveTunerActivity(int waveId);

    void startDialogWaveActivity();

    void updateFragment(float frequency, int harmonicsNumber);


}
