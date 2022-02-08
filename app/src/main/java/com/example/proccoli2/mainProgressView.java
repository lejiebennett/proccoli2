package com.example.proccoli2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
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

import com.github.mikephil.charting.animation.ChartAnimator;
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
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.renderer.BarChartRenderer;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.renderer.XAxisRendererHorizontalBarChart;
import com.github.mikephil.charting.renderer.XAxisRendererRadarChart;
import com.github.mikephil.charting.renderer.YAxisRendererHorizontalBarChart;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.common.base.Splitter;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.grpc.okhttp.internal.Util;


public class mainProgressView extends AppCompatActivity {
    BarChart activeChart, expiredChart, finishedChart;
    Toolbar toolbar;
    Button activeBtn, expiredBtn, finishedBtn;
    MaterialButtonToggleGroup toggleGroup;

    ArrayList<GoalModel> activeList = new ArrayList<>();
    ArrayList<GoalModel> expiredList= new ArrayList<>();
    ArrayList<GoalModel> finishedList = new ArrayList<>();

    ArrayList<BarEntry> activeBarEntries;
    ArrayList<BarEntry> expiredBarEntries;
    ArrayList<BarEntry> finishedBarEntries;


    mainProgressView_VC controller = new mainProgressView_VC(this);

    @Override
    public void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.mainprogress_view);

        //Since the data pass isn't working, hardcoded data in instead
        activeList.add(new GoalModel("GoalIDDD1", "Biggie",1643895301,1643290501,"Project",false,10,20));
        activeList.add(new GoalModel("GoalIDDD2", "BigGoal",1643895302,1643290502,"Discussion",false,5,13));
        activeList.add(new GoalModel("GoalIDDD3", "Indy",1643895303,1643290503,"Exam",false,1,10));
        activeList.add(new GoalModel("GoalIDDD4", "Goally",1643895304,1643290504,"Assignment",false,2,3));
        activeList.add(new GoalModel("GoalIDDD5", "BearGoal",1643895305,1643290505,"Project",false,9,10));

        //Since the data pass isn't working, hardcoded data in instead
        expiredList.add(new GoalModel("GoalIDDD6", "Bug",1643895301,1643290501,"Project",false,5,5));
        expiredList.add(new GoalModel("GoalIDDD7", "To graduate",1643895302,1643290502,"Discussion",false,1,30));
        expiredList.add(new GoalModel("GoalIDDD8", "Read book",1643895303,1643290503,"Exam",false,14,10));

        //Since the data pass isn't working, hardcoded data in instead
        finishedList.add(new GoalModel("GoalIDDD9", "Eat some food",1643895301,1643290501,"Project",false,30,20));
        finishedList.add(new GoalModel("GoalIDDD10", "Workout at home",1643895302,1643290502,"Discussion",false,2,20));




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
        activeChart = findViewById(R.id.goalProgressChartActive);
        expiredChart = findViewById(R.id.goalProgressChartExpired);
        finishedChart = findViewById(R.id.goalProgressChartFinished);

        //Rotates the chart horizontally
        activeChart.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onGlobalLayout() {
                        activeChart.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        int offset = (activeChart.getHeight() - activeChart.getWidth()) / 2;

                        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) activeChart.getLayoutParams();
                        layoutParams.width = activeChart.getHeight();
                        layoutParams.height = activeChart.getWidth();
                        activeChart.setLayoutParams(layoutParams);

                        activeChart.setTranslationX(-offset);
                        activeChart.setTranslationY(offset);


                    }
                });


        expiredChart.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onGlobalLayout() {

                        expiredChart.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        int offset = (expiredChart.getHeight() - expiredChart.getWidth()) / 2;

                        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) expiredChart.getLayoutParams();
                        layoutParams.width = expiredChart.getHeight();
                        layoutParams.height = expiredChart.getWidth();
                        expiredChart.setLayoutParams(layoutParams);

                        expiredChart.setTranslationX(-offset);
                        expiredChart.setTranslationY(offset);


                    }
                });

        finishedChart.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onGlobalLayout() {

                        finishedChart.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        int offset = (finishedChart.getHeight() - finishedChart.getWidth()) / 2;

                        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) finishedChart.getLayoutParams();
                        layoutParams.width = finishedChart.getHeight();
                        layoutParams.height = finishedChart.getWidth();
                        finishedChart.setLayoutParams(layoutParams);

                        finishedChart.setTranslationX(-offset);
                        finishedChart.setTranslationY(offset);


                    }
                });



        //Used to initialize chart
        activeChart.setPinchZoom(false);
        activeChart.setDrawGridBackground(true);
        activeChart.setDrawBarShadow(false);
        activeChart.setDrawValueAboveBar(false);
        activeChart.setHighlightFullBarEnabled(false);
        activeChart.getDescription().setEnabled(false);
        activeChart.animateY(3000);


        expiredChart.setPinchZoom(false);
        expiredChart.setDrawGridBackground(true);
        expiredChart.setDrawBarShadow(false);
        expiredChart.setDrawValueAboveBar(false);
        expiredChart.setHighlightFullBarEnabled(false);
        expiredChart.getDescription().setEnabled(false);
        expiredChart.animateY(3000);


        finishedChart.setPinchZoom(false);
        finishedChart.setDrawGridBackground(true);
        finishedChart.setDrawBarShadow(false);
        finishedChart.setDrawValueAboveBar(false);
        finishedChart.setHighlightFullBarEnabled(false);
        finishedChart.getDescription().setEnabled(false);
        finishedChart.animateY(3000);

        //Sets up the legend for showing what is
        Legend activeLegend = activeChart.getLegend();
        activeLegend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        activeLegend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        activeLegend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        activeLegend.setDrawInside(false);
        activeLegend.setFormSize(8f);
        activeLegend.setFormToTextSpace(4f);
        activeLegend.setXEntrySpace(6f);


        Legend expiredLegend = expiredChart.getLegend();
        expiredLegend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        expiredLegend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        expiredLegend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        expiredLegend.setDrawInside(false);
        expiredLegend.setFormSize(8f);
        expiredLegend.setFormToTextSpace(4f);
        expiredLegend.setXEntrySpace(6f);

        Legend finishedLegend = finishedChart.getLegend();
        finishedLegend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        finishedLegend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        finishedLegend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        finishedLegend.setDrawInside(false);
        finishedLegend.setFormSize(8f);
        finishedLegend.setFormToTextSpace(4f);
        finishedLegend.setXEntrySpace(6f);



        final ArrayList<String> activeXAxisLabel = controller.generateXAxisLabel(activeList);
        final ArrayList<String> finishedXAxisLabel = controller.generateXAxisLabel(finishedList);
        final ArrayList<String> expiredXAxisLabel = controller.generateXAxisLabel(expiredList);


        XAxis activeXAxis = activeChart.getXAxis();
        activeXAxis.setDrawLabels(true);
        activeXAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        activeXAxis.setLabelRotationAngle(-90);


        XAxis expiredXAxis = expiredChart.getXAxis();
        expiredXAxis.setDrawLabels(true);
        expiredXAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        expiredXAxis.setLabelRotationAngle(-90);

        XAxis finishedXAxis = finishedChart.getXAxis();
        finishedXAxis.setDrawLabels(true);
        finishedXAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        finishedXAxis.setLabelRotationAngle(-90);



        /*
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

         */
        activeXAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        activeXAxis.setValueFormatter(new CustomFormatter(activeXAxisLabel));
        activeChart.setXAxisRenderer(new CustomXAxisRenderer(activeChart.getViewPortHandler(), activeXAxis, activeChart.getTransformer(YAxis.AxisDependency.LEFT)));
        activeChart.getAxisLeft().setDrawZeroLine(true);
        activeChart.getAxisLeft().setAxisMinimum(-100f);
        activeChart.getAxisLeft().setAxisMaximum(100f);
        activeChart.getAxisLeft().setLabelCount(3);
        //activeChart.setRenderer(new MyBarChartRenderer(activeChart, activeChart.getAnimator(), activeChart.getViewPortHandler()));


        expiredXAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        expiredXAxis.setValueFormatter(new CustomFormatter(expiredXAxisLabel));
        expiredChart.setXAxisRenderer(new CustomXAxisRenderer(expiredChart.getViewPortHandler(), expiredXAxis, expiredChart.getTransformer(YAxis.AxisDependency.LEFT)));
        expiredChart.getAxisLeft().setDrawZeroLine(true);
        expiredChart.getAxisLeft().setAxisMinimum(-100f);
        expiredChart.getAxisLeft().setAxisMaximum(100f);
        expiredChart.getAxisLeft().setLabelCount(3);

        //This will rotate the labels so they are horizontal but then they will go off the chart?
       // activeChart.setRenderer(new MyBarChartRenderer(activeChart, activeChart.getAnimator(), activeChart.getViewPortHandler()));

        finishedXAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        finishedXAxis.setValueFormatter(new CustomFormatter(finishedXAxisLabel));
        finishedChart.setXAxisRenderer(new CustomXAxisRenderer(finishedChart.getViewPortHandler(), finishedXAxis, finishedChart.getTransformer(YAxis.AxisDependency.LEFT)));
        finishedChart.getAxisLeft().setDrawZeroLine(true);
        finishedChart.getAxisLeft().setAxisMinimum(-100f);
        finishedChart.getAxisLeft().setAxisMaximum(100f);
        finishedChart.getAxisLeft().setLabelCount(3);


        activeBarEntries = controller.generateBarEntries(activeList);
        expiredBarEntries = controller.generateBarEntries(expiredList);
        finishedBarEntries = controller.generateBarEntries(finishedList);

        BarDataSet activeSet, expiredSet,finishedSet;
        ArrayList<IBarDataSet> activeDataSets, expiredDataSets, finishedDataSets;
        BarData activeData, expiredData, finishedData;

        if (activeChart.getData() != null &&
                activeChart.getData().getDataSetCount() > 0) {
            activeSet = (BarDataSet) activeChart.getData().getDataSetByIndex(0);
            activeSet.setValues(activeBarEntries);
            activeChart.getData().notifyDataChanged();
            activeChart.notifyDataSetChanged();
        } else {
            activeSet = new BarDataSet(activeBarEntries, "Active Goals");
            activeSet.setDrawIcons(false);
            activeSet.setColors(getColors());
            activeSet.setStackLabels(new String[]{"Proposed","Studied"});


            activeDataSets = new ArrayList<>();
            activeDataSets.add(activeSet);

            activeData = new BarData(activeDataSets);
            activeData.setValueFormatter(new MyValueFormatter());
            activeData.setValueTextSize(12);
            activeData.setValueTextColor(Color.WHITE);


           // activeChart.getAxisRight().setValueFormatter(new MyValueFormatter());
            activeChart.setData(activeData);
        }

        activeChart.setFitBars(true);

        activeChart.invalidate();




        if (expiredChart.getData() != null &&
                expiredChart.getData().getDataSetCount() > 0) {
            expiredSet = (BarDataSet) expiredChart.getData().getDataSetByIndex(0);
            expiredSet.setValues(expiredBarEntries);
            expiredChart.getData().notifyDataChanged();
            expiredChart.notifyDataSetChanged();
        } else {
            expiredSet = new BarDataSet(expiredBarEntries, "Expired Goals");
            expiredSet.setDrawIcons(false);
            expiredSet.setColors(getColors());
            expiredSet.setStackLabels(new String[]{"Studied","Proposed"});


            expiredDataSets = new ArrayList<>();
            expiredDataSets.add(activeSet);

            expiredData = new BarData(expiredDataSets);
            expiredData.setValueFormatter(new MyValueFormatter());
            expiredData.setValueTextSize(12);
            expiredData.setValueTextColor(Color.WHITE);


            expiredChart.getAxisLeft().setValueFormatter(new MyValueFormatter());
            expiredChart.setData(expiredData);
        }

        expiredChart.setFitBars(true);
        expiredChart.invalidate();


        if (finishedChart.getData() != null &&
                finishedChart.getData().getDataSetCount() > 0) {
            finishedSet = (BarDataSet) finishedChart.getData().getDataSetByIndex(0);
            finishedSet.setValues(finishedBarEntries);
            finishedChart.getData().notifyDataChanged();
            finishedChart.notifyDataSetChanged();
        } else {
            finishedSet = new BarDataSet(finishedBarEntries, "Finished Goals");
            finishedSet.setDrawIcons(false);
            finishedSet.setColors(getColors());
            finishedSet.setStackLabels(new String[]{"Studied","Proposed"});


            finishedDataSets = new ArrayList<>();
            finishedDataSets.add(activeSet);

            finishedData = new BarData(finishedDataSets);
            finishedData.setValueFormatter(new MyValueFormatter());
            finishedData.setValueTextSize(12);
            finishedData.setValueTextColor(Color.WHITE);



            finishedChart.getAxisLeft().setValueFormatter(new MyValueFormatter());
            finishedChart.setData(finishedData);
        }

        finishedChart.setFitBars(true);
        finishedChart.invalidate();


        activeChart.setVisibility(View.VISIBLE);

        finishedChart.setVisibility(View.INVISIBLE);
        expiredChart.setVisibility(View.INVISIBLE);


        activeSet.setValues(activeBarEntries);
        activeChart.setVisibleYRange(-100f,100f, YAxis.AxisDependency.LEFT);
        activeChart.setFitBars(true);
        activeChart.setAutoScaleMinMaxEnabled(false);
        activeChart.getData().notifyDataChanged();
        activeChart.notifyDataSetChanged();

        activeChart.invalidate();


        toggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if(isChecked){
                    if(checkedId == R.id.activeBtnMainProgress){
                        activeChart.setVisibility(View.VISIBLE);

                        finishedChart.setVisibility(View.INVISIBLE);
                        expiredChart.setVisibility(View.INVISIBLE);

                        activeSet.setValues(activeBarEntries);
                        activeChart.getData().notifyDataChanged();
                        activeChart.notifyDataSetChanged();
                       // activeChart.getAxisRight().setValueFormatter(new MyValueFormatter());
                        activeChart.animateY(2000);
                        activeChart.setFitBars(true);
                        activeChart.invalidate();
                    }


                    if(checkedId == R.id.expiredBtnMainProgress){
                        Log.d("expired", "onButtonChecked: ExpiredChecked");
                        expiredChart.setVisibility(View.VISIBLE);

                        //activeChart.setVisibility(View.INVISIBLE);
                        finishedChart.setVisibility(View.INVISIBLE);

                        Log.d("expiredEntries", "onButtonChecked: " + expiredBarEntries);
                        activeSet.setValues(expiredBarEntries);

                        expiredChart.setVisibleYRange(-100f,100f, YAxis.AxisDependency.LEFT);
                        expiredChart.setFitBars(true);
                        expiredChart.setAutoScaleMinMaxEnabled(false);
                        expiredChart.getAxisLeft().setDrawZeroLine(true);
                        expiredChart.getAxisLeft().setAxisMinimum(-100f);
                        expiredChart.getAxisLeft().setAxisMaximum(100f);
                        expiredChart.getAxisLeft().setLabelCount(3);
                        expiredChart.getData().notifyDataChanged();
                        expiredChart.notifyDataSetChanged();
                        expiredChart.animateY(2000);


                    }
                    if(checkedId == R.id.finishedBtnMainProgress){
                        Log.d("finished", "onButtonChecked: finishedChecked");
                        finishedChart.setVisibility(View.VISIBLE);

                        //activeChart.setVisibility(View.INVISIBLE);
                        expiredChart.setVisibility(View.INVISIBLE);

                        Log.d("finishedEntries", "onButtonChecked: " + finishedBarEntries);
                        activeSet.setValues(finishedBarEntries);

                        finishedChart.setVisibleYRange(-100f,100f, YAxis.AxisDependency.LEFT);
                        finishedChart.setFitBars(true);
                        finishedChart.setAutoScaleMinMaxEnabled(false);
                        finishedChart.getData().notifyDataChanged();
                        finishedChart.notifyDataSetChanged();
                        finishedChart.animateY(2000);

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
            return mFormat.format(abs(value)) + " h";
        }

        private float abs(float value) {
            if (value>=0)
                return value;
            else
                return -1*value;
        }

        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            return mFormat.format(abs(value)) + " h";
        }


        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return mFormat.format(value) + " h";
        }
    }

    public class CustomXAxisRenderer extends XAxisRenderer {
        public CustomXAxisRenderer(ViewPortHandler viewPortHandler, XAxis xAxis, Transformer trans) {
            super(viewPortHandler, xAxis, trans);
        }

        @Override
        protected void drawLabel(Canvas c, String formattedLabel, float x, float y, MPPointF anchor, float angleDegrees) {

            if(formattedLabel.contains(" ")){
                String line[] = formattedLabel.split(" ");
                for (String a:
                        line) {
                    Log.d("labels", "drawLabel: " + a);
                }

                Utils.drawXAxisValue(c, line[0], x, y, mAxisLabelPaint, anchor, angleDegrees);
                for (int i =1; i<line.length;i++)
                    Utils.drawXAxisValue(c, line[i], x + (i)*mAxisLabelPaint.getTextSize(), y +mAxisLabelPaint.getTextSize(), mAxisLabelPaint, anchor, angleDegrees);

            }
            else
                Utils.drawXAxisValue(c, formattedLabel, x, y, mAxisLabelPaint, anchor, angleDegrees);


        }
    }

    public class CustomFormatter extends IndexAxisValueFormatter{
        public CustomFormatter(ArrayList<String>values) {
            super(values);
        }

        /**
         * This sets the xaxis label to actually be the goal name rather than a number 0,1,2,etc...
         * @param value
         * @return
         */
        @Override
        public String getFormattedValue(float value) {
            String finalLabel = "";
            if((int)value<getValues().length){
                Log.d("getValues", "getFormattedValue: " + getValues()[((int) value)]);
                finalLabel  = getValues()[((int) value)];
            }
            Log.d("label", "getFormattedValue: " + finalLabel);
            return  finalLabel;
        }
    }


    /**
     * Used to rotate the bar labels, but if you implement this then it will make them overlap
     */
    public class MyBarChartRenderer extends BarChartRenderer {
        public MyBarChartRenderer(BarDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
            super(chart, animator, viewPortHandler);
        }

        @Override
        public void drawValue(Canvas c, String valueText, float x, float y, int color) {
           // super.drawValue(c, valueText, x, y, color);

            Paint paint=super.mDrawPaint;
            paint.setTextSize(25f);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            Utils.drawXAxisValue(c,valueText,x,y,paint, MPPointF.getInstance(),270);
        }
    }
}