package com.example.musicapp.activities.main;

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


import com.example.musicapp.R;
import com.example.musicapp.common.GraphDialogActivity;
import com.example.musicapp.common.StringFormer;
import com.example.musicapp.common.Settings;
import com.example.musicapp.buffer.WaveBufferBuilder;
import com.example.musicapp.database.WavesObserver;
import com.example.musicapp.activities.wave_dialog.WaveDialogActivity;
import com.example.musicapp.wave.Wave;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
        implements WavesObserver {

    private static MainPresenter mainPresenter;
    private ArrayList<com.example.musicapp.wave.Wave> waves;
    private LayoutInflater inflater;
    private Context context;
    private int wavePosition = -1;
    private ViewHolder myViewHolder;


    public RecyclerViewAdapter(Context context, ArrayList<Wave> waves, MainPresenter mainPresenter) {
        inflater = LayoutInflater.from(context);
        this.mainPresenter = mainPresenter;
        this.context = context;
        this.waves = waves;
        //Waves waves1 = Waves.getInstance();
        //waves1.registerObserver(this);
    }

    /**
     * Создание новых View и ViewHolder элемента списка, которые впоследствии могут переиспользоваться.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.cardview, viewGroup, false);
        return new ViewHolder((view));
    }

    /**
     * Заполнение виджетов View данными из элемента списка с номером i
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        final Wave wave = waves.get(i);
        viewHolder.textFrequency.setText(StringFormer.formString("Частота: ", String.valueOf(wave.getFrequency())));
        viewHolder.textViewNumberOfHarmonics.setText(StringFormer.formString("Гармоники: ",
                String.valueOf(wave.getHarmonicsNumber())));
        viewHolder.textViewDigitCapacity.setText("Разрядность: ");
        viewHolder.imageViewType.setImageResource(wave.getImageId());
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
                                        WaveBufferBuilder.getWaveBufferGraph(waves.get(i), Settings.duration).createBuffer());
                                context.startActivity(intent);
                                break;
                            }
                            case R.id.menu_item_showShortGraph:{
                                Intent intent = new Intent(context, GraphDialogActivity.class);
                                float[] buffer = WaveBufferBuilder.getWaveBufferGraph(waves.get(i), Settings.duration).createBuffer();
                                short[] dataShort = new short[buffer.length];
                                for(int i = 0; i<buffer.length;i++)
                                    dataShort[i] = (short)(buffer[i]*0x7FFF);
                                intent.putExtra("shortbuf",
                                        dataShort);
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