package com.example.musicapp.wave_tuner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.musicapp.R;
import com.example.musicapp.buffer.ComplexWaveBuffer;
import com.example.musicapp.buffer.WaveBuffer;
import com.example.musicapp.model.WaveBufferBuilder;
import com.example.musicapp.model.WavePlayer;
import com.example.musicapp.sound_effect.SoundEffectsStatus;
import com.example.musicapp.sound_effect.amplitude.DynamicAmplitude;
import com.example.musicapp.sound_effect.frequency.DynamicFrequency;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WaveTunerActivity extends Activity implements WaveTunerView, View.OnClickListener,
        SeekBar.OnSeekBarChangeListener, CompoundButton.OnCheckedChangeListener {

    private ImageView imageViewPlay, imageViewStop;
    private TextView textViewFrequencyPercent, textViewAmplitudePercent;
    private WaveTunerPresenter waveTunerPresenter;
    private EditText editTextStep, editTextFrequency;
    private ImageView buttonIncrease, buttonDecrease;
    private CustomExpandableListView expandableListView;
    private ExpandableListAdapter expListAdapter;
    private List<String> expListTitle;
    private HashMap<String, List<String>> expListDetail;
    private CheckBox checkBoxAmplitudeDynamic, checkBoxFrequencyDynamic, checkBoxNormalization;
    private GraphView graph;
    private SeekBar seekBar;
    private int lastProgress = 0;
    private float step = 1;
    DynamicFrequency dynamicFrequency;
    DynamicAmplitude dynamicAmplitude;
    //private final SoundEffectsStatus soundEffectsStatus = SoundEffectsStatus.getInstance();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave_tuner);
        waveTunerPresenter = new WaveTunerPresenter(this, WaveTunerActivity.this);
        Intent intent = getIntent();
        waveTunerPresenter.setCurrentWave(intent.getIntExtra("id", 0));
        imageViewPlay = findViewById(R.id.imageViewPlayWaveTuner);
        imageViewStop = findViewById(R.id.imageViewStopWaveTuner);
        setImageViewState(waveTunerPresenter.getWavePlayerInstance());
        editTextStep = findViewById(R.id.editTextStepWaveTuner);
        editTextFrequency = findViewById(R.id.editTextFrequencyWaveTuner);
        buttonIncrease = findViewById(R.id.imageViewIncreaseFrequencyWaveTuner);
        buttonDecrease = findViewById(R.id.imageViewDecreaseFrequencyWaveTuner);
        seekBar = findViewById(R.id.seekBarWaveTuner);

        checkBoxAmplitudeDynamic = findViewById(R.id.checkBoxAmplitudeDynamicWaveTuner);
        checkBoxFrequencyDynamic = findViewById(R.id.checkBoxFrequencyDynamicWaveTuner);
        checkBoxNormalization = findViewById(R.id.checkBoxNormalizationWaveTuner);

        textViewFrequencyPercent = findViewById(R.id.textViewFrequencyPercent);
        textViewAmplitudePercent = findViewById(R.id.textViewAmplitudePercent);
        dynamicFrequency = new DynamicFrequency(waveTunerPresenter.getCurrentWave().getMainTone());
        dynamicAmplitude= new DynamicAmplitude(waveTunerPresenter.getCurrentWave().getMainTone());
        textViewFrequencyPercent.setText(String.valueOf(dynamicFrequency.getDynamicPercent()));
        textViewAmplitudePercent.setText(String.valueOf(dynamicAmplitude.getDynamicPercent()));

        initCheckBoxes();
        checkBoxAmplitudeDynamic.setOnCheckedChangeListener(this);
        checkBoxFrequencyDynamic.setOnCheckedChangeListener(this);
        checkBoxNormalization.setOnCheckedChangeListener(this);
        seekBar.setOnSeekBarChangeListener(this);
        imageViewPlay.setOnClickListener(this);
        imageViewStop.setOnClickListener(this);
        buttonIncrease.setOnClickListener(this);
        buttonDecrease.setOnClickListener(this);

        expandableListView = (CustomExpandableListView) findViewById(R.id.expandableListViewWaveTuner);
        expListDetail = ExListDataSoundEffects.loadData();
        expListTitle = new ArrayList<>(expListDetail.keySet());
        expListAdapter = new ExpandableListAdapter(this, expListTitle, expListDetail);
        expandableListView.setAdapter(expListAdapter);
        expandableListView.setExpanded(true);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                                        int childPosition, long id) {
                return false;
            }
        });
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });
        graph = findViewById(R.id.graphWaveTuner);
        drawGraph();
        initSeekBar();
        editTextStep.setText(String.valueOf(step));
        editTextStep.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    step = Float.parseFloat(editTextStep.getText().toString());
                    //String s = "-" + editTextStep.getText().toString();
                    //buttonDecrease.setText(s);
                    //s = "+" + editTextStep.getText().toString();
                    //buttonIncrease.setText(s);
                    return true;
                }
                return false;
            }
        });
    }

    private void setListViewHeightTitle(ExpandableListView listView, int group) {
        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += groupItem.getMeasuredHeight();
            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                    totalHeight += listItem.getMeasuredHeight();
                }
            }
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    private void initCheckBoxes() {
        checkBoxAmplitudeDynamic.setChecked(SoundEffectsStatus.amplitudeDynamic);
        checkBoxNormalization.setChecked(SoundEffectsStatus.normalization);
    }

    private void initSeekBar() {
        int progress = (int) waveTunerPresenter.getCurrentWave().getFrequency();
        int newProgress = (int) Math.sqrt(progress * 3500) + 1;
        int max = 3500;
        seekBar.setMax(max);
        seekBar.setProgress(newProgress);
        setEditTextFrequencyValue(progress);
    }

    private void setEditTextFrequencyValue(int frequency) {
        String text = String.valueOf(frequency);
        editTextFrequency.setText(text);
        int position = text.length();
        Editable editable = editTextFrequency.getText();
        Selection.setSelection(editable, position);
    }

    @Override
    public void drawGraph() {
        graph.removeAllSeries();
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScrollableY(true);
        WaveBuffer waveBuffer = WaveBufferBuilder.getWaveBuffer(waveTunerPresenter.getCurrentWave(), 1000);
        float[] buffer1 = waveBuffer.createBuffer();
        float[] buffer2 = waveBuffer.createBuffer();
        float[] buffer = new float[buffer1.length + buffer2.length];
        System.arraycopy(buffer1, 0, buffer, 0, buffer1.length);
        System.arraycopy(buffer2, 0, buffer, buffer1.length, buffer2.length);
        DataPoint[] points = new DataPoint[buffer.length];
        for (int i = 0; i < buffer.length; i++)
            points[i] = new DataPoint(i, buffer[i]);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(points);
        graph.addSeries(series);
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScrollableY(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewPlayWaveTuner: {
                setImageViewClicked(true);
                waveTunerPresenter.onButtonPlayClicked();
                setImageViewState(true);
                break;
            }
            case R.id.imageViewStopWaveTuner: {
                setImageViewClicked(false);
                waveTunerPresenter.onButtonStopClicked();
                setImageViewState(false);
                break;
            }
            case R.id.imageViewDecreaseFrequencyWaveTuner: {
                waveTunerPresenter.onButtonDecreaseClicked(step);
                float curFrequency = Float.parseFloat(editTextFrequency.getText().toString());
                editTextFrequency.setText(String.valueOf(curFrequency - step));
                dynamicFrequency = new DynamicFrequency(waveTunerPresenter.getCurrentWave().getMainTone());
                dynamicAmplitude= new DynamicAmplitude(waveTunerPresenter.getCurrentWave().getMainTone());
                textViewFrequencyPercent.setText(String.valueOf(dynamicFrequency.getDynamicPercent()));
                textViewAmplitudePercent.setText(String.valueOf(dynamicAmplitude.getDynamicPercent()));
                break;
            }
            case R.id.imageViewIncreaseFrequencyWaveTuner: {
                waveTunerPresenter.onButtonIncreaseClicked(step);
                float curFrequency = Float.parseFloat(editTextFrequency.getText().toString());
                editTextFrequency.setText(String.valueOf(curFrequency + step));
                dynamicFrequency = new DynamicFrequency(waveTunerPresenter.getCurrentWave().getMainTone());
                dynamicAmplitude= new DynamicAmplitude(waveTunerPresenter.getCurrentWave().getMainTone());
                textViewFrequencyPercent.setText(String.valueOf(dynamicFrequency.getDynamicPercent()));
                textViewAmplitudePercent.setText(String.valueOf(dynamicAmplitude.getDynamicPercent()));
                break;
            }
        }
    }

    private void setImageViewState(boolean isPlayed) {
        if (isPlayed) {
            imageViewPlay.setVisibility(View.INVISIBLE);
            imageViewStop.setVisibility(View.VISIBLE);
        } else {
            imageViewStop.setVisibility(View.INVISIBLE);
            imageViewPlay.setVisibility(View.VISIBLE);
        }
    }

    private void setImageViewClicked(boolean isClicked) {
        if (isClicked) {
            imageViewPlay.setClickable(false);
            imageViewStop.setClickable(true);
        } else {
            imageViewStop.setClickable(false);
            imageViewPlay.setClickable(true);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.checkBoxAmplitudeDynamicWaveTuner: {
                waveTunerPresenter.enableAmplitudeDynamic(isChecked);
                break;
            }
            case R.id.checkBoxFrequencyDynamicWaveTuner: {
                waveTunerPresenter.enableFrequencyDynamic(isChecked);
                break;
            }
            case R.id.checkBoxNormalizationWaveTuner: {
                waveTunerPresenter.enableNormalization(isChecked);
                break;
            }
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        if (lastProgress != progress) {
            int newProgress = (progress * progress / 3500);
            seekBar.setProgress(progress);
            lastProgress = progress;
            setEditTextFrequencyValue(newProgress);
            waveTunerPresenter.frequencyChanged(newProgress);
            dynamicFrequency = new DynamicFrequency(waveTunerPresenter.getCurrentWave().getMainTone());
            dynamicAmplitude= new DynamicAmplitude(waveTunerPresenter.getCurrentWave().getMainTone());
            textViewFrequencyPercent.setText(String.valueOf(dynamicFrequency.getDynamicPercent()));
            textViewAmplitudePercent.setText(String.valueOf(dynamicAmplitude.getDynamicPercent()));
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int progress = seekBar.getProgress();
        int newProgress = (progress * progress / 3500);
        setEditTextFrequencyValue(newProgress);
    }

}
