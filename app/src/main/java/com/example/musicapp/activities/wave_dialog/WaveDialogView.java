package com.example.musicapp.activities.wave_dialog;

import com.example.musicapp.common.Type;

public interface WaveDialogView {

    String getFrequencyFromEditText();
    String getHarmonicsNumberFromEditText();
    Type getType();
    void finishActivity();
}
