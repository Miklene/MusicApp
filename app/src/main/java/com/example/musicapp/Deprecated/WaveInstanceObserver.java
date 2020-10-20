package com.example.musicapp.Deprecated;

import com.example.musicapp.Deprecated.Wave;
import com.example.musicapp.Deprecated.WaveHarmonic;

import java.util.ArrayList;

public interface WaveInstanceObserver {
   void updateWaveInstance(Wave wave);
   void updateWaveHarmonicInstance(ArrayList<WaveHarmonic> waveHarmonics);
   void noResult();
}
