package com.example.lucas.prototipo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lucas.prototipo.histogram.Globals;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

/**
 * Created by User on 2/28/2017.
 */

public class Tab2Fragment extends Fragment {
    private static final String TAG = "Tab2Fragment";
    int numDataPoints;

    public Button btnRefresh, btnClear;
    Globals globals = Globals.getInstance();
    TextView tv_color, tv_max_color;

    LineChart lineChart;

    ArrayList<Entry> red = new ArrayList<>();
    ArrayList<Entry> green = new ArrayList<>();
    ArrayList<Entry> blue = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2_fragment,container,false);


        btnRefresh = (Button) view.findViewById(R.id.btnRefresh);
        btnClear = (Button) view.findViewById(R.id.btnClear);
        lineChart = (LineChart) view.findViewById(R.id.lineChart);
        tv_max_color = (TextView) view.findViewById(R.id.tv_maximum_color);

        btnRefresh.setOnClickListener(clickListener);
        btnClear.setOnClickListener(clickListener);

        return view;
    }
    // Create an anonymous implementation of OnClickListener
    private View.OnClickListener clickListener = new View.OnClickListener() {
        public void onClick(View v) {
            // do something when the button is clicked
            // Yes we will handle click here but which button clicked??? We don't know

            // So we will make
            switch (v.getId() /*to get clicked view id**/) {
                case R.id.btnRefresh:{
                    buildChart();
                }

                    break;
                case R.id.btnClear:{
                    clearChart();
                }

                    break;
                default:
                    break;
            }
        }
    };

    public void clearChart() {
        Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    public void buildChart(){

        int[] arrayR = new int[256];
        int[] arrayG = new int[256];
        int[] arrayB = new int[256];

        arrayR = globals.getArrayRed();
        arrayG = globals.getArrayGreen();
        arrayB = globals.getArrayBlue();

        double x = 0.0;
        //Defining the maximum amount of RGB
        if (globals.getArrayRed() == null && globals.getArrayGreen() == null && globals.getArrayBlue() == null){
            numDataPoints = 10;
        }
        else{
            numDataPoints = globals.getMaxRepeated();
        }


        for (int i = 0; i < 255; i++) {
            int redValues = arrayR[i];
            int greenValues = arrayG[i];
            int blueValues = arrayB[i];
            int xEntry = i;

            red.add(new Entry(xEntry, redValues));
            green.add(new Entry(xEntry, greenValues));
            blue.add(new Entry(xEntry, blueValues));

            Log.d(TAG, "Populating Arrays with Data: redValues = " + redValues);
            Log.d(TAG, "Populating Arrays with Data: greenValues = " + greenValues);
            Log.d(TAG, "Populating Arrays with Data: blueValues = " + blueValues);
            Log.d(TAG, "Populating Arrays with Data: xEntry = " + xEntry);

        }
        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();


        LineDataSet lineDataSetRed = new LineDataSet(red,"vermelho");
        LineDataSet lineDataSetGreen = new LineDataSet(green,"verde");
        LineDataSet lineDataSetBlue = new LineDataSet(blue,"azul");

        lineDataSetRed.setDrawCircles(false);
        lineDataSetRed.setColors(Color.RED);
        lineDataSetGreen.setDrawCircles(false);
        lineDataSetGreen.setColors(Color.GREEN);
        lineDataSetBlue.setDrawCircles(false);
        lineDataSetBlue.setColors(Color.BLUE);

        lineDataSets.add(lineDataSetRed);
        lineDataSets.add(lineDataSetGreen);
        lineDataSets.add(lineDataSetBlue);

        lineChart.setData(new LineData(lineDataSets));
        lineChart.setVisibleXRangeMaximum(2000f);

        //can also try calling invalidate() to refresh the graph
        lineChart.invalidate();

        tv_max_color.setText("\nMáximo Vermelho: " + globals.getMaxRepeatedRed() + "\nMáximo Verde: " + globals.getMaxRepeatedGreen()
                + "\nMáximo Azul: " + globals.getMaxRepeatedBlue());

    }
}
