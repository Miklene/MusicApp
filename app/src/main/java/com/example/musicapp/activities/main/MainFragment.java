package com.example.musicapp.activities.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.musicapp.R;
import com.example.musicapp.common.StringFormer;

public class MainFragment extends Fragment implements View.OnClickListener {

    private TextView textViewAllParameters;//textFrequency, textStereo, textViewNumberOfHarmonics, textViewDigitCapacity;
    private ImageView imageViewPlay, imageViewStop;
    private View v;
    private MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_main, container, false);
        Bundle bundle = this.getArguments();
        imageViewPlay = v.findViewById(R.id.imageViewPlayFragment);
        imageViewStop = v.findViewById(R.id.imageViewStopFragment);
        imageViewPlay.setOnClickListener(this);
        imageViewStop.setOnClickListener(this);
        imageViewPlay.setVisibility(View.INVISIBLE);
        imageViewStop.setVisibility(View.VISIBLE);
        textViewAllParameters = v.findViewById(R.id.textViewAllParameters);
        String channels;
        if (bundle.getInt("s") == 2)
            channels = "Стерео";
        else
            channels = "Моно";
        textViewAllParameters.setText(StringFormer.formString(
                String.valueOf(bundle.getFloat("f")), "Гц, ",
                String.valueOf(bundle.getInt("hn")), ", ", channels, ", ", "32-bit"));
       /* textFrequency = v.findViewById(R.id.textFrequencyFragment);
        textStereo = v.findViewById(R.id.textStereoFragment);
        textViewNumberOfHarmonics = v.findViewById(R.id.textViewNumberOfHarmonicsFragment);
        textViewDigitCapacity = v.findViewById(R.id.textViewDigitCapacityFragment);*/
        /*textFrequency.setText(StringFormer.formString(textFrequency.getText().toString(),
                String.valueOf(bundle.getFloat("f"))));
        if (bundle.getInt("s") == 2)
            textStereo.setText(R.string.stereo);
        else
            textStereo.setText(R.string.mono);
        textViewNumberOfHarmonics.setText(StringFormer.formString(
                textViewNumberOfHarmonics.getText().toString(),
                String.valueOf(bundle.getInt("hn"))));*/
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.onFragmentClicked();
            }
        });
        return v;
    }

    public void displayNewWave(float frequency, int harmonicsNumber) {
        textViewAllParameters.setText(StringFormer.formString(
                String.valueOf(frequency), "Гц, ",
                String.valueOf(harmonicsNumber), ", ", "Стерео", ", ", "32-bit"));
        imageViewPlay.setClickable(false);
        imageViewPlay.setVisibility(View.INVISIBLE);
        imageViewStop.setVisibility(View.VISIBLE);
        imageViewStop.setClickable(true);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewPlayFragment: {
                imageViewPlay.setClickable(false);
                mainActivity.onButtonPlayClicked();
                imageViewPlay.setVisibility(View.INVISIBLE);
                imageViewStop.setVisibility(View.VISIBLE);
                imageViewStop.setClickable(true);
                break;
            }
            case R.id.imageViewStopFragment: {
                imageViewStop.setClickable(false);
                mainActivity.onButtonStopClicked();
                imageViewStop.setVisibility(View.INVISIBLE);
                imageViewPlay.setVisibility(View.VISIBLE);
                imageViewPlay.setClickable(true);
                break;
            }
        }
    }
}
