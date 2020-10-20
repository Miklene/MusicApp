package com.example.musicapp.main;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.musicapp.buffer.ComplexWaveBuffer;
import com.example.musicapp.buffer.WaveBuffer;
import com.example.musicapp.buffer.WaveBufferSingleThread;
import com.example.musicapp.common.TypeOfBuffer;
import com.example.musicapp.model.Settings;
import com.example.musicapp.model.WaveBufferBuilder;
import com.example.musicapp.model.database.WaveDbHelper;
import com.example.musicapp.model.WavePlayer;
import com.example.musicapp.model.Waves;
import com.example.musicapp.wav.WavFile;
import com.example.musicapp.wav.WavFileWriter;
import com.example.musicapp.wav.WavHeader;
import com.example.musicapp.wav.WavHeader32Bit;
import com.example.musicapp.wave.Wave;
import com.example.musicapp.wave.WaveFactory;
import com.example.musicapp.wave_tuner.WaveTunerView;

import java.io.File;
import java.io.IOException;

public class MainPresenter {

    private MainView mainView;
    static WaveTunerView waveTunerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private MainFragment mainFragment;
    private WavePlayer wavePlayer;
    private Wave currentWave;
    private int checkedItem = -1;
    private Waves waves;
    private Context context;



    public MainPresenter(MainView mainView, Context context) {
        this.mainView = mainView;
        this.context = context;
        waves = Waves.getInstance();
        wavePlayer = WavePlayer.getInstance();
        SQLiteDatabase database  = new WaveDbHelper((Context)mainView).getWritableDatabase();
        waves.setDatabase(database);
        /*waves.addWave(WaveFactory.createWave(Type.VIOLIN, 200, 31));
        waves.addWave(WaveFactory.createWave(Type.VIOLIN, 200, 15));
        waves.addWave(WaveFactory.createWave(Type.VIOLIN, 200, 7));
        waves.addWave(WaveFactory.createWave(Type.VIOLIN, 200, 0));*/
    }

    public void attachRecyclerViewAdapter(RecyclerViewAdapter recyclerViewAdapter) {
        this.recyclerViewAdapter = recyclerViewAdapter;
    }

    public void onButtonCreateWaveClicked() {
        mainView.startDialogWaveActivity();
    }

    public void onRecyclerViewItemSelected(int wavePosition) {
        currentWave = recyclerViewAdapter.getItem(wavePosition);
        if(mainFragment==null) {
            createFragment(writeInBundle(currentWave));
        } else{
            mainView.updateFragment(currentWave.getFrequency(), currentWave.getHarmonicsNumber());
        }
        if (checkedItem != wavePosition) {
            checkedItem = wavePosition;
            stopWavePlayer();
        }
        startWavePlayer();
    }

    public void onFragmentClicked(){
        mainView.startWaveTunerActivity(waves.getWavePosition(currentWave));
    }

    private void startWavePlayer() {
        wavePlayer.playWave(WaveBufferBuilder.getWaveBuffer(currentWave, Settings.duration));
    }

    private void stopWavePlayer() {
        wavePlayer.stopWavePlayer();
        //wavePlayer = null;
    }

    public void onFragmentButtonPlayClicked() {
        startWavePlayer();
        /*thread = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
        thread.start();*/
    }

    public void onFragmentButtonStopClicked() {
        stopWavePlayer();
        /*thread = new Thread(new Runnable() {
            @Override
            public void run() {
                stopWavePlayer();
            }
        });
        thread.start();*/
    }

    public void createWav(int index){
        String fileName;
        Wave wave = waves.getWave(index);
        /*ComplexWaveBuffer complexWaveBuffer = new ComplexWaveBuffer(WaveFactory.createWave(
                wave.getType(), wave.getFrequency(), wave.getHarmonicsNumber(), wave.getTableId()), 88200);*/
        WaveBuffer waveBuffer  = WaveBufferBuilder.getWaveBuffer(wave,88200);
        fileName = "wave" + wave.getFrequency() + "," + wave.getHarmonicsNumber() + ".wav";
        File myFile = new File(context.getExternalFilesDir(null), fileName);
        if (!myFile.exists()) {
            try {
                myFile.createNewFile();
                //
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        float[] buffer = waveBuffer.createBuffer();
        WavHeader wavHeader = new WavHeader32Bit((short) 2, buffer.length);
        WavFile wavFile = new WavFile(wavHeader, buffer);
        //FileOutputStream writer = new FileOutputStream(myFile);
        WavFileWriter wavFileWriter = new WavFileWriter(wavFile, myFile);
        wavFileWriter.writeFile();
    }

    private void removeFragmentIfExist() {
        if (mainFragment != null)
            mainView.removeFragment();
    }

    private void createFragment(Bundle bundle) {
        mainFragment = new MainFragment();
        mainView.displayFragment(mainFragment, bundle);
    }

    private Bundle writeInBundle(Wave wave) {
        Bundle bundle = new Bundle();
        bundle.putFloat("f", wave.getFrequency());
        bundle.putInt("hn", wave.getHarmonicsNumber());
        bundle.putInt("s", 2);
        //добавить разрядность
        return bundle;
    }

    public void deleteWave(int index){
        waves.deleteWave(index);
    }

}
