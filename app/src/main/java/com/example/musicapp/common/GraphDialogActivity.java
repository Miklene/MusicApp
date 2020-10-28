package com.example.musicapp.common;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;

import com.example.musicapp.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class GraphDialogActivity extends Activity {

    private GraphView graph;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_graph);
        graph = findViewById(R.id.graph);
        Bundle arguments = getIntent().getExtras();
        if(arguments.get("buffer")!= null) {
            float[] buffer = (float[]) arguments.get("buffer");
            createGraph(buffer);
        }
         else {
            short[] buffer = (short[]) arguments.get("shortbuf");
            createGraph(buffer);
        }
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScrollableY(true);
    }

    private void createGraph(float[] buffer){
        int size = buffer.length;
        DataPoint[] points = new DataPoint[size];
        for(int i=0; i<size; i++)
            points[i] = new DataPoint(i,buffer[i]);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(points);
        graph.addSeries(series);
    }

    private void createGraph(short[] buffer){
        int size = buffer.length;
        DataPoint[] points = new DataPoint[size];
        for(int i=0; i<size; i++)
            points[i] = new DataPoint(i,buffer[i]);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(points);
        graph.addSeries(series);
    }
}
