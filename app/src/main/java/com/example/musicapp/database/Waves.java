package com.example.musicapp.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.musicapp.common.Type;
import com.example.musicapp.wave.Wave;
import com.example.musicapp.database.WaveDb.WaveTable;
import java.util.ArrayList;

public class Waves implements WavesSubject {

    private ArrayList<Wave> waveArrayList = new ArrayList<>();
    private ArrayList<WavesObserver> observers = new ArrayList<>();
    private static Waves instance;

    private SQLiteDatabase database;

    private Waves() {

    }

    public void setDatabase(SQLiteDatabase database){
        this.database = database;
        getWavesFromDatabase();
    }

    public static Waves getInstance(){
        if(instance == null){
            instance = new Waves();
        }
        return instance;
    }

    public void addWave(Type type, float frequency, int harmonicsNumber){
        database.insert(WaveTable.NAME,null, getContentValues(type,frequency,harmonicsNumber));
        waveArrayList.clear();
        getWavesFromDatabase();
        wavesChanged();
    }

    public void deleteWave(int index){
        int id = waveArrayList.get(index).getTableId();
        waveArrayList.remove(index);
        deleteWaveFromDatabase(id);
    }

    public int getWavePosition(Wave wave){
        return waveArrayList.indexOf(wave);
    }

    public Wave getWave(int index){
        return waveArrayList.get(index);
    }

    public ArrayList<Wave> getWaves() {
        return waveArrayList;
    }

    public int getSize(){
        return waveArrayList.size();
    }

    @Override
    public void registerObserver(WavesObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(WavesObserver observer) {
        int i = observers.indexOf(observer);
        if(i>=0)
            observers.remove(i);
    }

    @Override
    public void notifyObservers() {
        for(int i = 0; i <observers.size(); i++) {
            WavesObserver observer = observers.get(i);
            observer.update(waveArrayList.get(waveArrayList.size() - 1));
        }
    }

    private void wavesChanged(){
        notifyObservers();
    }

    private static ContentValues getContentValues(Type type, float frequency, int harmonicsNumber){
        ContentValues contentValues= new ContentValues();
        contentValues.put(WaveTable.Columns.TYPE, type.toString());
        contentValues.put(WaveTable.Columns.FREQUENCY,frequency);
        contentValues.put(WaveTable.Columns.HARMONICS_NUMBER, harmonicsNumber);
        return contentValues;
    }

    private WaveCursorWrapper queryWaves(String whereClause, String[] whereArgs){
        Cursor cursor = database.query(
                WaveTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new WaveCursorWrapper(cursor);
    }

    private void deleteWaveFromDatabase(int id){
        database.delete(WaveTable.NAME, "_id = " +  id,null);
    }

    private void getWavesFromDatabase(){
        WaveCursorWrapper cursor = queryWaves(null, null);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                waveArrayList.add(cursor.getWave());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
    }
}
