package com.example.musicapp;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements WaveInstanceObserver{

    private ArrayList<Wave> waves;
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

    public RecyclerViewAdapter(Context context, ArrayList<Wave> waves) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.waves = waves;
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
        String s;
        s = "Частота: ";
        s += String.valueOf(waves.get(i).getFrequency());
        viewHolder.textFrequency.setText(s);
        s = "Громкость: ";
        s += String.valueOf(waves.get(i).getAmplitude());
        s += "%";
        viewHolder.textWaveVolume.setText(s);
        if (waves.get(i).getChannelsNumber() == 2)
            viewHolder.textStereo.setText(R.string.stereo);
        else
            viewHolder.textStereo.setText(R.string.mono);
        s = "Кол-во гармоник: ";
        s += String.valueOf(waves.get(i).getHarmonicsNumber());
        viewHolder.textViewNumberOfHarmonics.setText(s);
        if(waves.get(i).getType().equals(Type.SAW))
            viewHolder.textType.setText(R.string.saw);
        else
            viewHolder.textType.setText(R.string.sin);
        viewHolder.checkboxPhaseCorrection.setChecked(true);
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
                                waveInstance.registerWaveInstanceObserver(RecyclerViewAdapter.this);
                                wavePosition = i;
                                myViewHolder = viewHolder;
                                Intent intent = new Intent(context, WaveDialogActivity.class);
                                intent.putExtra("wave", waves.get(i));
                                context.startActivity(intent);
                                break;
                            }
                          /*  case R.id.menu_item_addHarmonic: {
                                break;
                            }*/
                            case R.id.menu_item_showGraph:{
                                Intent intent = new Intent(context, GraphDialogActivity.class);
                                intent.putExtra("buffer", waves.get(i).createBuffer());
                                context.startActivity(intent);
                                break;
                            }
                            case R.id.menu_item_delete: {
                                waves.remove(i);
                                notifyDataSetChanged();
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
        viewHolder.imageButtonAddHarmonic.setOnClickListener(new View.OnClickListener() {
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
        });
        viewHolder.imageButtonPlay.setOnClickListener(new View.OnClickListener() {
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
            }
        });
        viewHolder.imageButtonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               wavePlayer.stopWavePlayer();
               wavePosition = -1;
               frequency.setFrequency(waves.get(i).getFrequency());
               reverberation.clearEcho();
            }
        });
    }

    @Override
    public int getItemCount() {
        return waves.size();
    }

    @Override
    public void updateWaveInstance(Wave wave) {
        if(wavePosition == -1)
            return;
        waves.set(wavePosition,wave);
        waveInstance.removeWaveInstanceObserver(RecyclerViewAdapter.this);
        String s;
        s = "Частота: ";
        s += String.valueOf(waves.get(wavePosition).getFrequency());
        myViewHolder.textFrequency.setText(s);
        s = "Громкость: ";
        s += String.valueOf(waves.get(wavePosition).getAmplitude());
        s += "%";
        myViewHolder.textWaveVolume.setText(s);
        if (waves.get(wavePosition).getChannelsNumber() == 2)
            myViewHolder.textStereo.setText(R.string.stereo);
        else
            myViewHolder.textStereo.setText(R.string.mono);
        s = "Кол-во гармоник: ";
        s += String.valueOf(waves.get(wavePosition).getHarmonicsNumber());
        myViewHolder.textViewNumberOfHarmonics.setText(s);
        if(waves.get(wavePosition).getType().equals(Type.SAW))
            myViewHolder.textType.setText(R.string.saw);
        else
            myViewHolder.textType.setText(R.string.sin);
        wavePosition = -1;
        wave.checkMaxAmplitude();
    }

    @Override
    public void updateWaveHarmonicInstance(ArrayList<WaveHarmonic> waveHarmonics) {
        if(wavePosition == -1)
            return;
        waves.get(wavePosition).setWaveHarmonics(waveHarmonics);
        waveInstance.removeWaveInstanceObserver(RecyclerViewAdapter.this);
        String s;
        s = "Кол-во гармоник: ";
        s += String.valueOf(waves.get(wavePosition).getHarmonicsNumber());
        myViewHolder.textViewNumberOfHarmonics.setText(s);
    }


    @Override
    public void noResult() {
        waveInstance.removeWaveInstanceObserver(RecyclerViewAdapter.this);
        wavePosition = -1;
    }


    public void changeCurrentFrequency(float value){
        if(wavePosition == -1)
            return;
        waves.get(wavePosition).setFrequency(waves.get(wavePosition).getFrequency()+value);
        WaveHarmonicCreator.UpdateWaveHarmonics(waves.get(wavePosition));
        String s;
        s = "Частота: ";
        s += String.valueOf(waves.get(wavePosition).getFrequency());
        myViewHolder.textFrequency.setText(s);
    }

    /**
     * Реализация класса ViewHolder, хранящего ссылки на виджеты.
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textStereo, textFrequency, textWaveVolume, textType, menu,
                textViewNumberOfHarmonics, textViewPhaseCorrection ;
        private ImageButton imageButtonPlay, imageButtonStop, imageButtonAddHarmonic;
        private CheckBox checkboxPhaseCorrection ;

        public ViewHolder(View view) {
            super(view);
            textStereo = view.findViewById(R.id.textStereo);
            textFrequency = view.findViewById(R.id.textFrequency);
            textWaveVolume = view.findViewById(R.id.textWaveVolume);
            textType = view.findViewById(R.id.textType);
            textViewNumberOfHarmonics = view.findViewById(R.id.textViewNumberOfHarmonics);
            textViewPhaseCorrection = view.findViewById(R.id.textViewPhaseCorrection);
            imageButtonPlay = view.findViewById(R.id.imageButtonPlay);
            imageButtonStop = view.findViewById(R.id.imageButtonStop);
            imageButtonAddHarmonic = view.findViewById(R.id.imageButtonAddHarmonic);
            checkboxPhaseCorrection = view.findViewById(R.id.checkboxPhaseCorrection);
            menu = view.findViewById(R.id.menu);
        }
    }
}