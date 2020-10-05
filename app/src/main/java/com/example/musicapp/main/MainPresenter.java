package com.example.musicapp.main;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.musicapp.common.Type;
import com.example.musicapp.model.database.WaveDbHelper;
import com.example.musicapp.model.WavePlayer;
import com.example.musicapp.model.Waves;
import com.example.musicapp.wave.Wave;
import com.example.musicapp.wave.WaveFactory;

public class MainPresenter {

    private MainView mainView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private MainFragment mainFragment;
    private WavePlayer wavePlayer;
    private Wave currentWave;
    private int checkedItem = -1;
    private Waves waves;


    public MainPresenter(MainView mainView) {
        this.mainView = mainView;
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

    public void onRecyclerViewItemSelected(int layoutPosition) {
        currentWave = recyclerViewAdapter.getItem(layoutPosition);
        if(mainFragment==null) {
            createFragment(writeInBundle(currentWave));
        } else{
            mainView.updateFragment(currentWave.getFrequency(), currentWave.getHarmonicsNumber());
        }
        if (checkedItem != layoutPosition) {
            checkedItem = layoutPosition;
            stopWavePlayer();
        }
        startWavePlayer();
    }

    private void startWavePlayer() {
        wavePlayer.playWave(currentWave, 1000);
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
