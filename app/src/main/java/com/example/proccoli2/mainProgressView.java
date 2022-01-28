package com.example.proccoli2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.renderer.XAxisRendererHorizontalBarChart;
import com.github.mikephil.charting.renderer.XAxisRendererRadarChart;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.grpc.okhttp.internal.Util;


public class mainProgressView extends AppCompatActivity {
    BarChart chart;
    Toolbar toolbar;
    Button activeBtn, expiredBtn, finishedBtn;
    MaterialButtonToggleGroup toggleGroup;

    @Override
    public void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.mainprogress_view);

        toolbar = findViewById(R.id.toolbarGoalProgress);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("toolbar clicked", "onClick: I was clicked");
                finish();
            }
        });

        activeBtn = findViewById(R.id.activeBtnMainProgress);
        expiredBtn = findViewById(R.id.expiredBtnMainProgress);
        finishedBtn  = findViewById(R.id.finishedBtnMainProgress);
        toggleGroup = findViewById(R.id.toggleButtonGroupMainProgress);
        toggleGroup.check(R.id.activeBtnMainProgress);
        chart = findViewById(R.id.goalProgressChart);


        //Rotates the chart horizontally
        chart.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onGlobalLayout() {

                        chart.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        int offset = (chart.getHeight() - chart.getWidth()) / 2;

                        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) chart.getLayoutParams();
                        layoutParams.width = chart.getHeight();
                        layoutParams.height = chart.getWidth();
                        chart.setLayoutParams(layoutParams);

                        chart.setTranslationX(-offset);
                        chart.setTranslationY(offset);


                    }
                });

        //Used to initialize chart
        chart.setPinchZoom(false);
        chart.setDrawGridBackground(true);
        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(false);
        chart.setHighlightFullBarEnabled(false);
        chart.getDescription().setEnabled(false);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setFormToTextSpace(4f);
        l.setXEntrySpace(6f);

        final ArrayList<String> xAxisLabel = new ArrayList<>();
        xAxisLabel.add("Dec 01 Write paper");
        xAxisLabel.add("Dec 01 Submit paper");
        xAxisLabel.add("Dec 04 Read Chapter 1");
        xAxisLabel.add("Dec 05 Take Quiz");
        xAxisLabel.add("Dec 10 Finish Project");
        xAxisLabel.add("Dec 01 Write paper");
        xAxisLabel.add("Dec 01 Submit paper");
        xAxisLabel.add("Dec 04 Read Chapter 1");
        xAxisLabel.add("Dec 05 Take Quiz");
        xAxisLabel.add("Dec 10 Finish Project");
        xAxisLabel.add("Dec 01 Write paper");
        xAxisLabel.add("Dec 01 Submit paper");
        xAxisLabel.add("Dec 04 Read Chapter 1");
        xAxisLabel.add("Dec 05 Take Quiz");
        xAxisLabel.add("Dec 10 Finish Project");
        xAxisLabel.add("Dec 01 Write paper");
        xAxisLabel.add("Dec 01 Submit paper");
        xAxisLabel.add("Dec 04 Read Chapter 1");
        xAxisLabel.add("Dec 05 Take Quiz");
        xAxisLabel.add("Dec 10 Finish Project");


        XAxis xAxis = chart.getXAxis();
        xAxis.setDrawLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelRotationAngle(-90);

        ValueFormatter formatter = new ValueFormatter() {


            @Override
            public String getFormattedValue(float value) {
                String[] label = xAxisLabel.get((int) value).split(" ");
                String finalLabel = "";
                for(int i = 0; i<label.length;i++)
                    finalLabel = finalLabel + "\n"+ label[i];

                return  finalLabel;
            }


        };

        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(new CustomFormatter(xAxisLabel));
       // chart.setXAxisRenderer(new CustomXAxisRenderer(chart.getViewPortHandler(), xAxis, chart.getTransformer(YAxis.AxisDependency.LEFT), chart));


        //Data for the chart
        ArrayList<BarEntry> values = new ArrayList<BarEntry>();
        //NOTE: IN ORDER TO GET THEM FLIPPED, NEED TO MAKE ONE NEGATIVE
        int size = 4;

        for (int i = 0; i < size + 1; i++) {
            float mult = (size + 1);
            float val1 = (float) (Math.random() * mult) + mult / 3;
            float val2 = (float) (Math.random() * mult) + mult / 3;
            val2 = (float) ((-1.0)*val2);
            values.add(new BarEntry(
                    i,
                    new float[]{val1, val2},
                    getResources().getDrawable(R.drawable.circle)));
        }

        ArrayList<BarEntry> values2 = new ArrayList<BarEntry>();

        int size2 = 8;

        for (int i = 0; i < size2 + 1; i++) {
            float mult = (size2 + 1);
            float val1 = (float) (Math.random() * mult) + mult / 3;
            float val2 = (float) (Math.random() * mult) + mult / 3;
            val2 = (float) ((-1.0)*val2);

            values2.add(new BarEntry(
                    i,
                    new float[]{val1, val2},
                    getResources().getDrawable(R.drawable.circle)));
        }

        ArrayList<BarEntry> values3 = new ArrayList<BarEntry>();

        int size3 = 12;

        for (int i = 0; i < size3 + 1; i++) {
            float mult = (size3 + 1);
            float val1 = (float) (Math.random() * mult) + mult / 3;
            float val2 = (float) (Math.random() * mult) + mult / 3;
            val2 = (float) ((-1.0)*val2);

            values3.add(new BarEntry(
                    i,
                    new float[]{val1, val2},
                    getResources().getDrawable(R.drawable.circle)));
        }


        BarDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "Statistics Vienna 2014");
            set1.setDrawIcons(false);
            set1.setColors(getColors());
            set1.setStackLabels(new String[]{"Studied","Proposed"});


            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueFormatter(new MyValueFormatter());
            data.setValueTextSize(12);
            data.setValueTextColor(Color.WHITE);


            chart.getAxisLeft().setValueFormatter(new MyValueFormatter());
            chart.setData(data);
        }

        chart.setFitBars(true);
        chart.invalidate();

        toggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if(isChecked){
                    if(checkedId == R.id.activeBtnMainProgress){
                        set1.setValues(values);
                        chart.getData().notifyDataChanged();
                        chart.notifyDataSetChanged();
                        chart.setFitBars(true);
                        chart.invalidate();

                    }


                    if(checkedId == R.id.expiredBtnMainProgress){
                        Log.d("expired", "onButtonChecked: ExpiredChecked");
                        set1.setValues(values2);
                        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
                        xAxis.setValueFormatter(new CustomFormatter(xAxisLabel));
                        chart.getData().notifyDataChanged();
                        chart.notifyDataSetChanged();
                        chart.setFitBars(true);
                        chart.invalidate();
                    }
                    if(checkedId == R.id.finishedBtnMainProgress){
                        Log.d("finished", "onButtonChecked: finishedChecked");
                        set1.setValues(values3);
                        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
                        xAxis.setValueFormatter(new CustomFormatter(xAxisLabel));
                        chart.getData().notifyDataChanged();
                        chart.notifyDataSetChanged();
                        chart.setFitBars(true);
                        chart.invalidate();
                    }
                }
            }
        });
    }


    private int[] getColors() {

        // have as many colors as stack-values per entry
        int[] colors = new int[2];
        System.arraycopy(ColorTemplate.MATERIAL_COLORS, 0, colors, 0, 2);

        return colors;
    }

    public class MyValueFormatter extends ValueFormatter implements IValueFormatter
    {

        private final DecimalFormat mFormat;

        public MyValueFormatter() {
            mFormat = new DecimalFormat("###,###,###,###");
        }

        @Override
        public String getBarStackedLabel(float value, BarEntry stackedEntry) {
            super.getBarStackedLabel(value, stackedEntry);
            return mFormat.format(abs(value)) + " m";
        }

        private float abs(float value) {
            if (value>=0)
                return value;
            else
                return -1*value;
        }

        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            return mFormat.format(abs(value)) + " m";
        }


        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return mFormat.format(value) + " m";
        }
    }

    public class CustomXAxisRenderer extends XAxisRendererHorizontalBarChart{
        public CustomXAxisRenderer(ViewPortHandler viewPortHandler, XAxis xAxis, Transformer trans, BarChart chart) {
            super(viewPortHandler, xAxis, trans, chart);
        }

        @Override
        protected void drawLabel(Canvas c, String formattedLabel, float x, float y, MPPointF anchor, float angleDegrees) {
            String[] line = formattedLabel.split("\n");
            Utils.drawXAxisValue(c,line[0], x,y,mAxisLabelPaint,anchor,angleDegrees);
            for(int i = 1; i< line.length;i++){
                Utils.drawXAxisValue(c,line[i],x,y+mAxisLabelPaint.getTextSize()*i,mAxisLabelPaint,anchor,angleDegrees);
            }

        }
    }

    public class CustomFormatter extends IndexAxisValueFormatter{
        public CustomFormatter(ArrayList<String>values) {
            super(values);
        }

        @Override
        public String getFormattedValue(float value) {
            String[] label = getValues()[((int) value)].split(" ");
            String finalLabel = "";
            for(int i = 0; i<label.length;i++)
                finalLabel = finalLabel + "\n"+ label[i];

            return  finalLabel;
        }
    }







}


