package com.example.musicapp.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.musicapp.common.Type;
import com.example.musicapp.wave.Wave;
import com.example.musicapp.database.WaveDb.WaveTable;
import com.example.musicapp.wave.WaveFactory;

public class WaveCursorWrapper extends CursorWrapper {
    public WaveCursorWrapper(Cursor cursor) {
        super(cursor);
    }


    public Wave getWave(){
        Type type = Type.SIN;
        int tableId = getInt(getColumnIndex("_id"));
        String stringType = getString(getColumnIndex(WaveTable.Columns.TYPE));
        float frequency = getFloat(getColumnIndex(WaveTable.Columns.FREQUENCY));
        int harmonicsNumber = getInt(getColumnIndex(WaveTable.Columns.HARMONICS_NUMBER));
        if(stringType.equals(Type.SIN.toString()))
            type = Type.SIN;
        if(stringType.equals(Type.VIOLIN.toString()))
            type = Type.VIOLIN;
        if(stringType.equals(Type.ORGAN.toString()))
            type = Type.ORGAN;
        return WaveFactory.createWave(type, frequency, harmonicsNumber, tableId);
    }
}
