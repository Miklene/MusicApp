package com.example.musicapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapterHarmonics extends RecyclerView.Adapter<RecyclerViewAdapterHarmonics.ViewHolderHarmonic> {
    private ArrayList<WaveHarmonic> waveHarmonics;

    private LayoutInflater inflater;
    private Context context;
    private RecyclerViewAdapter.ViewHolder myViewHolder;

    public RecyclerViewAdapterHarmonics(Context context, ArrayList<WaveHarmonic> waveHarmonics) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.waveHarmonics = waveHarmonics;
    }



    @NonNull
    @Override
    public ViewHolderHarmonic onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.wave_harmonic, viewGroup, false);
        ViewHolderHarmonic viewHolder = new ViewHolderHarmonic(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolderHarmonic viewHolder, int i) {
        final WaveHarmonic waveHarmonic = (WaveHarmonic) waveHarmonics.get(i);
        String s;
        s = "Частота: ";
        s += String.valueOf(waveHarmonics.get(i).getFrequency());
        viewHolder.textHarmonicFrequency.setText(s);
        s = "Амплитуда: ";
        s += String.valueOf(waveHarmonics.get(i).getAmplitude());
        s += "%";
        viewHolder.textHarmonicAmplitude.setText(s);

    }

    @Override
    public int getItemCount() {
        return waveHarmonics.size();
    }

    static class ViewHolderHarmonic extends RecyclerView.ViewHolder {
        TextView textHarmonicFrequency,textHarmonicAmplitude;
        public ViewHolderHarmonic(@NonNull View view) {
            super(view);
            textHarmonicAmplitude = view.findViewById(R.id.textHarmonicAmplitude);
            textHarmonicFrequency = view.findViewById(R.id.textHarmonicFrequency);
        }
    }
}
