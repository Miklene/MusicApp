package com.example.musicapp;

import java.util.ArrayList;

public interface WaveInstanceObserver {
   void updateWaveInstance(Wave wave);
   void updateWaveHarmonicInstance(ArrayList<WaveHarmonic> waveHarmonics);
   void noResult();
}
