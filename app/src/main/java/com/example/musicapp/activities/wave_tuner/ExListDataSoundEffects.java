package com.example.musicapp.activities.wave_tuner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExListDataSoundEffects {

    public static HashMap<String, List<String>> loadData() {
        HashMap<String, List<String>> expDetails = new HashMap<>();

        List<String> soundEffects = new ArrayList<>();
        soundEffects.add("Динамика амплитуды");
        soundEffects.add("Нормализация");
        expDetails.put("Звуковые эффекты", soundEffects);
        return expDetails;
    }
}
