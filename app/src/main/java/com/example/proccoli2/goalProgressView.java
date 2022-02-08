package com.example.proccoli2;

import android.app.ActionBar;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Class for when an individual goal is clicked to view goal progress in the single goal view page
 * Need to update from Fall 2021
 */
public class goalProgressView extends AppCompatActivity {
    BarChart chart;
    Toolbar toolbar;
    ImageView calendar, graphs;
    boolean twoGraph = true;

    //Calendar Date Picker
    Spinner dateSpinner;
    TextView cancelBtn, doneBtn;

    LinearLayout dateGoalProgressLinearLayout;


    @Override
    public void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.goalprogress_view);

        toolbar = findViewById(R.id.toolbarGoalProgress);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("toolbar clicked", "onClick: I was clicked");
                finish();
            }
        });

        dateGoalProgressLinearLayout = findViewById(R.id.dateGoalProgressLinearLayout);
        cancelBtn = findViewById(R.id.cancelGoalProgressBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateGoalProgressLinearLayout.setVisibility(View.INVISIBLE);
            }
        });
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

        //Set the chart legend
        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setFormToTextSpace(4f);
        l.setXEntrySpace(6f);

        final ArrayList<String> xAxisLabel = new ArrayList<>();
        xAxisLabel.add("Dec 01");
        xAxisLabel.add("Dec 02");
        xAxisLabel.add("Dec 03");
        xAxisLabel.add("Dec 04");
        xAxisLabel.add("Dec 05");


        XAxis xAxis = chart.getXAxis();
        xAxis.setDrawLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);

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
        xAxis.setValueFormatter(formatter);

        //Data for the chart
        ArrayList<BarEntry> values = new ArrayList<BarEntry>();

        int size = 4;

        for (int i = 0; i < size + 1; i++) {
            float mult = (size + 1);
            float val1 = (float) (Math.random() * mult) + mult / 3;
            float val2 = (float) (Math.random() * mult) + mult / 3;
            float val3 = (float) (Math.random() * mult) + mult / 3;

            values.add(new BarEntry(
                    i,
                    new float[]{val1, val2, val3},
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
            set1 = new BarDataSet(values, "");
            set1.setDrawIcons(false);
            set1.setColors(getColors());
            set1.setStackLabels(new String[]{"Read Preamble", "Read Article I", "Read Article II and Article III"});

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

        calendar = findViewById(R.id.calendarBtn);
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Calendar", "onClick: I clicked the calendar btn");
                dateGoalProgressLinearLayout.setVisibility(View.VISIBLE);
            }
        });

        graphs = findViewById(R.id.graphsBtn);
        graphs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("graphs", "onClick: I clicked the graph btn" + getResources());
                if(twoGraph == true){
                    graphs.setImageResource(R.drawable.onegraph_foreground);
                    twoGraph = false;

                }
                else{
                    graphs.setImageResource(R.drawable.twograph_foreground);
                    twoGraph=true;

                }

            }
        });

        doneBtn = findViewById(R.id.doneGoalProgressBtn);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("SelectedDate", "onClick: " + dateSpinner.getSelectedItem().toString());
                dateGoalProgressLinearLayout.setVisibility(View.INVISIBLE);

            }
        });


    }


    private int[] getColors() {

        // have as many colors as stack-values per entry
        int[] colors = new int[3];

        System.arraycopy(ColorTemplate.MATERIAL_COLORS, 0, colors, 0, 3);

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
            return mFormat.format(value) + " m";
        }

        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            return mFormat.format(value) + " m";
        }


        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return mFormat.format(value) + " m";
        }


    }

}
