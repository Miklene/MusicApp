package com.example.musicapp.main;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Deprecated.Frequency;
import com.example.musicapp.R;
import com.example.musicapp.Deprecated.Reverberation;
import com.example.musicapp.Deprecated.SinWaveHarmonic;
import com.example.musicapp.buffer.WaveBuffer;
import com.example.musicapp.buffer.WaveBufferSingleThread;
import com.example.musicapp.common.GraphDialogActivity;
import com.example.musicapp.common.StringFormer;
import com.example.musicapp.Deprecated.ToneGenerator;
import com.example.musicapp.model.Settings;
import com.example.musicapp.model.WaveBufferBuilder;
import com.example.musicapp.model.Waves;
import com.example.musicapp.model.WavesObserver;
import com.example.musicapp.sound_effect.AmplitudeDynamic;
import com.example.musicapp.sound_effect.Normalization;
import com.example.musicapp.sound_effect.SoundEffectsStatus;
import com.example.musicapp.wave_dialog.WaveDialogActivity;
import com.example.musicapp.Deprecated.WaveHarmonic;
import com.example.musicapp.Deprecated.WaveInstance;
import com.example.musicapp.Deprecated.WavePlayer;
import com.example.musicapp.wave.Wave;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
        implements WavesObserver {

    private static MainPresenter mainPresenter;
    private ArrayList<com.example.musicapp.wave.Wave> waves;
    private SinWaveHarmonic[] sinWaveHarmonics;
    private ArrayList<WaveHarmonic> waveHarmonicsArrayList;
    private ArrayList<ArrayList<SinWaveHarmonic>> sinArrayLists;
    private LayoutInflater inflater;
    private ToneGenerator toneGenerator;
    private Context context;
    private Frequency frequency = Frequency.getInstance();
    private WavePlayer wavePlayer = WavePlayer.getInstance();
    private WaveInstance waveInstance = WaveInstance.getInstance();
    private int wavePosition = -1;
    private ViewHolder myViewHolder;
    Reverberation reverberation = Reverberation.getInstance();


    public RecyclerViewAdapter(Context context, ArrayList<com.example.musicapp.wave.Wave> waves, MainPresenter mainPresenter) {
        inflater = LayoutInflater.from(context);
        this.mainPresenter = mainPresenter;
        this.context = context;
        this.waves = waves;
        Waves waves1 = Waves.getInstance();
        waves1.registerObserver(this);
    }

    /**
     * Создание новых View и ViewHolder элемента списка, которые впоследствии могут переиспользоваться.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.cardview, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder((view));
        return viewHolder;
    }

    /**
     * Заполнение виджетов View данными из элемента списка с номером i
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        final Wave wave = (Wave) waves.get(i);
        viewHolder.textFrequency.setText(StringFormer.formString("Частота: ",String.valueOf(waves.get(i).getFrequency())));
        viewHolder.textViewNumberOfHarmonics.setText(StringFormer.formString("Гармоники: ",
                String.valueOf(waves.get(i).getHarmonicsNumber())));
        viewHolder.textViewDigitCapacity.setText("Разрядность: ");
        viewHolder.imageViewType.setImageResource(waves.get(i).getImageId());
        viewHolder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, viewHolder.menu);
                popupMenu.inflate(R.menu.option_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_item_change: {
                                //waveInstance.registerWaveInstanceObserver(RecyclerViewAdapter.this);
                                wavePosition = i;
                                myViewHolder = viewHolder;
                                Intent intent = new Intent(context, WaveDialogActivity.class);
                                //intent.putExtra("wave", waves.get(i));
                                context.startActivity(intent);
                                break;
                            }
                          /*  case R.id.menu_item_addHarmonic: {
                                break;
                            }*/
                            case R.id.menu_item_showGraph:{
                                Intent intent = new Intent(context, GraphDialogActivity.class);
                                intent.putExtra("buffer",
                                        WaveBufferBuilder.getWaveBuffer(waves.get(i), Settings.duration).createBuffer());
                                context.startActivity(intent);
                                break;
                            }
                            case R.id.menu_item_delete: {
                                mainPresenter.deleteWave(i);
                                //waves.remove(i);
                                notifyDataSetChanged();
                                break;
                            }
                            case R.id.menu_item_createWav: {
                                mainPresenter.createWav(i);
                                break;
                            }
                            default: {
                                break;
                            }
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
       /* viewHolder.imageButtonAddHarmonic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                waveInstance.registerWaveInstanceObserver(RecyclerViewAdapter.this);
                wavePosition = i;
                myViewHolder = viewHolder;
                Intent intent = new Intent(context, HarmonicDialogActivity.class);
                //waveHarmonicsArrayList = waves.get(i).getWaveHarmonics();
                intent.putExtra("Wave", waves.get(i));
                intent.putParcelableArrayListExtra("Array", waves.get(i).getWaveHarmonics());
                context.startActivity(intent);
            }
        });*/
       /* viewHolder.imageButtonPlay.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                waveInstance.registerWaveInstanceObserver(RecyclerViewAdapter.this);
                wavePosition = i;
                myViewHolder = viewHolder;
                wavePlayer.updateWavePlayer(waves.get(i), context,
                        viewHolder.checkboxPhaseCorrection.isChecked());

                /*if (toneGenerator != null) {
                    if (toneGenerator.isBufferPlay())
                        return;
                }
                int duration;
                AudioManager manager = (AudioManager) context
                        .getSystemService(Context.AUDIO_SERVICE);
                final int systemVolume = manager.getStreamVolume(AudioManager.STREAM_MUSIC);
                MainActivity mainActivity = (MainActivity) context.getApplicationContext();
                duration = mainActivity.getDuration();
                if (duration == 0)
                    return;
                Buffer mBuffer = new Buffer(duration, waves.get(i), systemVolume);
                if (waves.get(i).getType().equals("Синус")) {
                    mBuffer.makeSinBuffer();
                }
                if (waves.get(i).getType().equals("Пила")) {
                    mBuffer.makeSawBuffer();
                }
                if (waves.get(i).isStereo()) {
                    toneGenerator = new ToneGenerator(mBuffer);
                    toneGenerator.makeStereoSound();
                } else {
                    toneGenerator = new ToneGenerator(mBuffer);
                    toneGenerator.makeMonoSound();
                }*/
        /*    }
        });*/
      /*  viewHolder.imageButtonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               wavePlayer.stopWavePlayer();
               wavePosition = -1;
               frequency.setFrequency(waves.get(i).getFrequency());
               reverberation.clearEcho();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return waves.size();
    }

    public Wave getItem(int i){
        return waves.get(i);
    }

    @Override
    public void update(Wave wave) {
       notifyDataSetChanged();
    }

    /**
     * Реализация класса ViewHolder, хранящего ссылки на виджеты.
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textStereo, textFrequency, textViewDigitCapacity, menu,
                textViewNumberOfHarmonics;
        private ImageView imageViewType;


        public ViewHolder(View view) {
            super(view);
            textStereo = view.findViewById(R.id.textStereo);
            textFrequency = view.findViewById(R.id.textFrequency);
            textViewNumberOfHarmonics = view.findViewById(R.id.textViewNumberOfHarmonics);
            textViewDigitCapacity =view.findViewById(R.id.textViewDigitCapacity);
            imageViewType = view.findViewById(R.id.imageViewType);
            menu = view.findViewById(R.id.menu);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainPresenter.onRecyclerViewItemSelected(getLayoutPosition());
                }
            });
        }
    }
}