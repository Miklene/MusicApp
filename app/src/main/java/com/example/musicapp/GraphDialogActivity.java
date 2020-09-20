package com.example.musicapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class GraphDialogActivity extends Activity {

    CheckBox checkboxHorizontalZoomScroll;
    GraphView graph;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_graph);
        checkboxHorizontalZoomScroll = findViewById(R.id.checkboxHorizontalZoomScroll);
        Bundle arguments = getIntent().getExtras();
        short[] buffer  = (short[]) arguments.get("buffer");
        graph = findViewById(R.id.graph);
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScrollableY(true);
        checkboxHorizontalZoomScroll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    graph.getViewport().setScalable(true);
                    graph.getViewport().setScalableY(true);
                    graph.getViewport().setScrollable(true);
                    graph.getViewport().setScrollableY(true);
                }
                else {
                    graph.getViewport().setScalable(false);
                    graph.getViewport().setScalableY(false);
                    graph.getViewport().setScrollable(false);
                    graph.getViewport().setScrollableY(false);
                }
            }
        });

        int size = buffer.length;
        DataPoint[] points = new DataPoint[size];
        for(int i=0; i<size; i++)
            points[i] = new DataPoint(i,buffer[i]);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(points);
        graph.addSeries(series);
    }


}
