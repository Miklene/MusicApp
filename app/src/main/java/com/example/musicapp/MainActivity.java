package com.example.musicapp;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements WaveInstanceObserver {
    Button buttonAdd, buttonPlay, buttonStop, buttonPlus1, buttonMinus1,
            buttonPlus50, buttonMinus50, buttonAddHarmonic, buttonDeleteHarmonic;
    EditText editTextFirstRatio, editTextSecondRatio;
    // EditText editText2;
    float[] volume = new float[6];
    boolean stereo;
    public final static int REQ_CODE_CHILD = 1;
    RecyclerView recyclerView;
    Wave wave;
    public static MainActivity activity;
    private WaveCreator waveCreator = new WaveCreator();
    Frequency frequency = Frequency.getInstance();
    //SawWaveHarmonics sawWaveHarmonics = SawWaveHarmonics.getInstance();
    WaveInstance waveInstance = WaveInstance.getInstance();
    ArrayList<Wave> waveArrayList = new ArrayList<>();
    ArrayList<WaveHarmonic> waveHarmonics = new ArrayList<>();
    WaveHarmonic waveHarmonic;
    // private static ArrayList<Wave> waveArrayList = new ArrayList<>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        final AudioManager manager = (AudioManager) this
                .getSystemService(Context.AUDIO_SERVICE);
        buttonAdd = findViewById(R.id.buttonAdd);
        //buttonPlay = findViewById(R.id.buttonPlay);
        // buttonStop = findViewById(R.id.buttonStop);
        buttonPlus1 = findViewById(R.id.buttonPlus1);
        buttonMinus1 = findViewById(R.id.buttonMinus1);
        buttonPlus50 = findViewById(R.id.buttonPlus50);
        buttonMinus50 = findViewById(R.id.buttonMinus50);
        buttonAddHarmonic = findViewById(R.id.buttonAddHarmonic);
        buttonDeleteHarmonic = findViewById(R.id.buttonDeleteHarmonic);
        editTextFirstRatio = findViewById(R.id.editTextFirstRatio);
        editTextSecondRatio = findViewById(R.id.editTextSecondRatio);
        editTextFirstRatio.setText("1");
        editTextSecondRatio.setText("5");
        String s;
        s = "+";
        s += editTextFirstRatio.getText().toString();
        buttonPlus1.setText(s);
        s = "-";
        s += editTextFirstRatio.getText().toString();
        buttonMinus1.setText(s);
        s = "+";
        s += editTextSecondRatio.getText().toString();
        buttonPlus50.setText(s);
        s = "-";
        s += editTextSecondRatio.getText().toString();
        buttonMinus50.setText(s);
        editTextFirstRatio.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN &&
                        (keyCode == KeyEvent.KEYCODE_ENTER))
                {
                    // сохраняем текст, введенный до нажатия Enter в переменную
                    String s;
                    s = "+";
                    s += editTextFirstRatio.getText().toString();
                    buttonPlus1.setText(s);
                    s = "-";
                    s += editTextFirstRatio.getText().toString();
                    buttonMinus1.setText(s);
                    return true;
                }
                return false;
            }
        });
        editTextSecondRatio.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN &&
                        (keyCode == KeyEvent.KEYCODE_ENTER))
                {
                    // сохраняем текст, введенный до нажатия Enter в переменную
                    String s;
                    s = "+";
                    s += editTextSecondRatio.getText().toString();
                    buttonPlus50.setText(s);
                    s = "-";
                    s += editTextSecondRatio.getText().toString();
                    buttonMinus50.setText(s);
                    return true;
                }
                return false;
            }
        });
       /* editText2 = findViewById(R.id.editText2);
        editText2.setText("10000");
        editText2.setFocusableInTouchMode(false);
        editText2.setFocusable(false);
        editText2.setFocusableInTouchMode(true);
        editText2.setFocusable(true);*/
        recyclerView = findViewById(R.id.recyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(MainActivity.this, waveArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                waveInstance.registerWaveInstanceObserver(MainActivity.this);
                Intent intent = new Intent(MainActivity.this, WaveDialogActivity.class);
                startActivity(intent);
            }
        });
        frequency.setFrequency(200);
        wave = waveCreator.createWave(Type.SIN, frequency.getFrequency(), 100, 1, 0);
        for(int i=0;i<7;i++)
            WaveHarmonicCreator.addWaveHarmonic(wave);
        waveArrayList.add(wave);
        wave = waveCreator.createWave(Type.SIN, frequency.getFrequency(), 100, 1, 0);
        for(int i=0;i<15;i++)
            WaveHarmonicCreator.addWaveHarmonic(wave);
        waveArrayList.add(wave);
        wave = waveCreator.createWave(Type.SIN, frequency.getFrequency(), 100, 1, 0);
        for(int i=0;i<31;i++)
            WaveHarmonicCreator.addWaveHarmonic(wave);
        waveArrayList.add(wave);
        // wave = new Wave(250, false, 100, "Синус",1);
        buttonMinus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.changeCurrentFrequency(Float.parseFloat(buttonMinus1.getText().toString()));
               // frequency.setFrequency(frequency.getFrequency() - 1);

            }
        });
        buttonMinus50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.changeCurrentFrequency(Float.parseFloat(buttonMinus50.getText().toString()));
                //frequency.setFrequency(frequency.getFrequency() - 50);

            }
        });

        buttonPlus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.changeCurrentFrequency(Float.parseFloat(buttonPlus1.getText().toString()));
                //frequency.setFrequency(frequency.getFrequency() + 1);

            }
        });
        buttonPlus50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.changeCurrentFrequency(Float.parseFloat(buttonPlus50.getText().toString()));
               // frequency.setFrequency(frequency.getFrequency() + 50);

            }
        });

        buttonDeleteHarmonic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            /*    sawWaveHarmonics.setNumberOfHarmonic(sawWaveHarmonics.getNumberOfHarmonic() - 1);
                Toast toast = Toast.makeText(getApplicationContext(), String.valueOf(sawWaveHarmonics.getNumberOfHarmonic()), Toast.LENGTH_SHORT);
                toast.show();*/
            }
        });
        buttonAddHarmonic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           /*    sawWaveHarmonics.setNumberOfHarmonic(sawWaveHarmonics.getNumberOfHarmonic() + 1);
               Toast toast = Toast.makeText(getApplicationContext(), String.valueOf(sawWaveHarmonics.getNumberOfHarmonic()), Toast.LENGTH_SHORT);
                toast.show();*/
            }
        });
    }

   /* @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE_CHILD) {
            if (resultCode != 321) {
                wave = (Wave) data.getExtras().getSerializable(Wave.class.getSimpleName());
                waveArrayList.add(wave);
            }
           // wavePlayer.updateWavePlayer(wave,MainActivity.this);
           // mBuffer = wavePlayer.makeBuffer(getDuration(), MainActivity.this,wave);
          //  fileManager = new FileManager(MainActivity.this,mBuffer);
           // fileManager.writeFile();
        }
    }*/

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

    public float[] getVolume() {
        return volume;
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
}
