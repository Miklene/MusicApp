package com.example.musicapp.main;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Frequency;
import com.example.musicapp.R;
import com.example.musicapp.Wave;
import com.example.musicapp.WaveCreator;
import com.example.musicapp.buffer.ComplexWaveBuffer;
import com.example.musicapp.common.Type;
import com.example.musicapp.model.Waves;
import com.example.musicapp.wave.WaveFactory;
import com.example.musicapp.wave_dialog.WaveDialogActivity;
import com.example.musicapp.WaveHarmonic;
import com.example.musicapp.WaveInstance;
import com.example.musicapp.WaveInstanceObserver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static android.os.Environment.DIRECTORY_MUSIC;


public class MainActivity extends AppCompatActivity implements WaveInstanceObserver,
        MainView, View.OnClickListener {
    private Button buttonAdd;
    private RecyclerView recyclerView;
    private Wave wave;
    private WaveCreator waveCreator = new WaveCreator();
    Frequency frequency = Frequency.getInstance();
    WaveInstance waveInstance = WaveInstance.getInstance();
    ArrayList<Wave> waveArrayList = new ArrayList<>();

    private MainPresenter mainPresenter;
    private MainFragment mainFragment;
    private FragmentTransaction fragmentTransaction;
    private Waves waves = Waves.getInstance();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainFragment = new MainFragment();
        mainPresenter = new MainPresenter(this);
        recyclerView = findViewById(R.id.recyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(MainActivity.this, waves.getWaves(), mainPresenter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        mainPresenter.attachRecyclerViewAdapter(adapter);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(this);
        File myFile = new File(this.getExternalFilesDir(null), "text.txt");
        if(!myFile.exists()) {
            try {
                myFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //speedTest();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonAdd: {
                mainPresenter.onButtonCreateWaveClicked();
                break;
            }
        }
    }

    @Override
    public void displayFragment(MainFragment mainFragment, Bundle bundle) {
        setCurrentFragment(mainFragment);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        mainFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.fragmentContainer, mainFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void removeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(mainFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void updateFragment(float frequency, int harmonicsNumber) {
        mainFragment.displayNewWave(frequency, harmonicsNumber);
    }

    @Override
    public void startDialogWaveActivity() {
        Intent intent = new Intent(MainActivity.this, WaveDialogActivity.class);
        startActivity(intent);
    }

    public void onFragmentClicked() {
        Bundle bundle;
        View v = mainFragment.getView().findViewById(R.id.imageViewPlayFragment);
        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(
                MainActivity.this, v, MainActivity.this.getString(R.string.transition));
        //bundle = activityOptions.toBundle();
        Intent intent = new Intent(MainActivity.this, WaveTunerActivity.class);
        startActivity(intent);//, bundle);
        overridePendingTransition(R.anim.activity_grow, R.anim.activity_hide);
    }

    private void setCurrentFragment(MainFragment mainFragment) {
        this.mainFragment = mainFragment;
    }

    public void onButtonPlayClicked() {
        mainPresenter.onFragmentButtonPlayClicked();
    }

    public void onButtonStopClicked() {
        mainPresenter.onFragmentButtonStopClicked();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void updateWaveInstance(Wave wave) {
        waveArrayList.add(wave);
        waveInstance.removeWaveInstanceObserver(MainActivity.this);
    }

    @Override
    public void updateWaveHarmonicInstance(ArrayList<WaveHarmonic> waveHarmonics) {
    }

    @Override
    public void noResult() {
        waveInstance.removeWaveInstanceObserver(MainActivity.this);
    }

    private void speedTest() {
        long start;
        long finish;
        long singleResult;
        long multiResult;
        int items;
        String result;
        Toast toast;
        items = 1000;
        ComplexWaveBuffer complexWaveBuffer = new ComplexWaveBuffer(
                WaveFactory.createWave(Type.VIOLIN, 200, 31,0), items);
        start = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            complexWaveBuffer.createBufferSingleThread();
        }
        finish = System.nanoTime();
        singleResult = finish - start;
        result = "Single thread " + items * 2 + " items: " + singleResult / 1000;
        System.out.println(result);
        result = String.valueOf(singleResult/1000);
        toast = Toast.makeText(this, result, Toast.LENGTH_LONG);
        toast.show();
        start = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            complexWaveBuffer.createBuffer();
        }
        finish = System.nanoTime();
        multiResult = finish - start;
        System.out.println("Multi thread " + items * 2 + " items: " + multiResult / 1000);
        result = String.valueOf(singleResult/1000 - multiResult/1000);
        toast = Toast.makeText(this, result, Toast.LENGTH_LONG);
        toast.show();
    }
}
