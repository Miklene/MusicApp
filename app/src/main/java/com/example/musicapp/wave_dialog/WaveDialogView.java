package com.example.musicapp.wave_dialog;

import com.example.musicapp.common.Type;

public interface WaveDialogView {

    String getFrequencyFromEditText();
    String getHarmonicsNumberFromEditText();
    Type getType();
    void finishActivity();
}
