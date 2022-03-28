package com.example.proccoli2.ui.home.mainProgress;

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

import com.example.proccoli2.NewModels.DataServices;
import com.example.proccoli2.NewModels.ResultHandler;
import com.example.proccoli2.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class for when main progress is clicked on which displays progress charts for all of the goals
 * Notes to self:
 *          Need to ask if the chart is scrollable in the x direction so more goals can be viewed
 *          Also need to update the input data to make sure the proposed vs studied time is in hours vs. minutes vs unix seconds
 *          Formatting issues about rotating bar labels, need to fix bar sizes, have them be seen outside of the bars etc
 */
public class mainProgressView extends AppCompatActivity {
    double timeSpendHolder = System.currentTimeMillis()/100L;
    BarChart activeChart, expiredChart, finishedChart;
    Toolbar toolbar;
    Button activeBtn, expiredBtn, finishedBtn;
    MaterialButtonToggleGroup toggleGroup;

    ArrayList<com.example.proccoli2.NewModels.GoalModel> activeList = new ArrayList<>();
    ArrayList<com.example.proccoli2.NewModels.GoalModel> expiredList= new ArrayList<>();
    ArrayList<com.example.proccoli2.NewModels.GoalModel> finishedList = new ArrayList<>();

    ArrayList<BarEntry> activeBarEntries;
    ArrayList<BarEntry> expiredBarEntries;
    ArrayList<BarEntry> finishedBarEntries;


    mainProgressView_VC controller = new mainProgressView_VC(this);

    @Override
    public void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.mainprogress_view);
        DataServices.getInstance().requestPersonalGoals(new ResultHandler<Object>() {

            @Override
            public void onSuccess(Object data) {
                HashMap<String, Object> hashMap = (HashMap<String, Object>) data;
                if (hashMap.get("_error") == null) {
                    Log.d("personalGoals", "onSuccess: " + hashMap);
                    ArrayList<com.example.proccoli2.NewModels.GoalModel> goalList = (ArrayList<com.example.proccoli2.NewModels.GoalModel>) hashMap.get("_response");

                    for (com.example.proccoli2.NewModels.GoalModel goal : goalList) {
                        controller.assignGoal(goal);
                    }

                    Log.d("AD", "AD: " + activeList + activeList.size());
                    Log.d("ED", "ED: " + expiredList);
                    Log.d("FD", "FD: " + finishedList);

                    //Makes sure the bars and labels line up
                    activeList = controller.checkBarCount(activeList);
                    expiredList = controller.checkBarCount(expiredList);
                    finishedList = controller.checkBarCount(finishedList);


                    final ArrayList<String> activeXAxisLabel = controller.generateXAxisLabel(activeList);
                    final ArrayList<String> finishedXAxisLabel = controller.generateXAxisLabel(finishedList);
                    final ArrayList<String> expiredXAxisLabel = controller.generateXAxisLabel(expiredList);

                    XAxis activeXAxis = activeChart.getXAxis();
                    activeXAxis.setDrawLabels(true);
                    activeXAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    Log.d("Active Xlabels", "onSuccess: " + activeXAxisLabel);
                    activeXAxis.setLabelRotationAngle(-90);
                    activeXAxis.setCenterAxisLabels(true);


                    XAxis expiredXAxis = expiredChart.getXAxis();
                    expiredXAxis.setDrawLabels(true);
                    expiredXAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    Log.d("Active Xlabels", "onSuccess: " + expiredXAxisLabel);
                    expiredXAxis.setLabelRotationAngle(-90);
                    expiredXAxis.setCenterAxisLabels(true);


                    XAxis finishedXAxis = finishedChart.getXAxis();
                    finishedXAxis.setDrawLabels(true);
                    finishedXAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    Log.d("Active Xlabels", "onSuccess: " + finishedXAxisLabel);
                    finishedXAxis.setLabelRotationAngle(-90);
                    finishedXAxis.setCenterAxisLabels(true);


                    activeXAxis.setGranularity(1f); // minimum axis-step (interval) is 1
                    activeXAxis.setValueFormatter(new CustomFormatter(activeXAxisLabel));
                  //  activeXAxis.setValueFormatter(new MyXAxisValueFormatter(activeXAxisLabel));

                    activeChart.setXAxisRenderer(new CustomXAxisRenderer(activeChart.getViewPortHandler(), activeXAxis, activeChart.getTransformer(YAxis.AxisDependency.LEFT)));
                    if(activeXAxis.getTextSize()<10){
                        activeXAxis.setLabelCount(activeXAxisLabel.size(), true);
                    }
                    else{
                        activeXAxis.setLabelCount(10,true);
                    }

                    activeChart.getAxisLeft().setDrawLabels(false);
                    activeChart.getAxisRight().setDrawLabels(true);
                    activeChart.getAxisRight().setDrawZeroLine(true);
                    activeChart.getAxisLeft().setAxisMinimum(-100f);
                    activeChart.getAxisLeft().setAxisMaximum(100f);
                    activeChart.getAxisRight().setLabelCount(20,true);
                    activeChart.getAxisRight().setAxisMaximum(100f);
                    activeChart.getAxisRight().setAxisMinimum(-100f);
                    activeChart.getAxisRight().setLabelCount(3,true);
                    //activeChart.setRenderer(new MyBarChartRenderer(activeChart, activeChart.getAnimator(), activeChart.getViewPortHandler()));



                    /////////////Expired////////////
                    expiredXAxis.setGranularity(1f); // minimum axis-step (interval) is 1
                    expiredXAxis.setValueFormatter(new CustomFormatter(expiredXAxisLabel));
                    //  activeXAxis.setValueFormatter(new MyXAxisValueFormatter(activeXAxisLabel));

                    expiredChart.setXAxisRenderer(new CustomXAxisRenderer(expiredChart.getViewPortHandler(), expiredXAxis, expiredChart.getTransformer(YAxis.AxisDependency.LEFT)));
                    //expiredXAxis.setLabelCount(expiredXAxisLabel.size()+1, true);
                    if(expiredXAxis.getTextSize()<10){
                        expiredXAxis.setLabelCount(expiredXAxisLabel.size(), true);
                    }
                    else{
                        expiredXAxis.setLabelCount(10,true);
                    }

                    expiredChart.getAxisLeft().setDrawLabels(false);
                    expiredChart.getAxisRight().setDrawLabels(true);
                    expiredChart.getAxisRight().setDrawZeroLine(true);
                    expiredChart.getAxisLeft().setAxisMinimum(-100f);
                    expiredChart.getAxisLeft().setAxisMaximum(100f);
                    expiredChart.getAxisRight().setLabelCount(20,true);
                    expiredChart.getAxisRight().setAxisMaximum(100f);
                    expiredChart.getAxisRight().setAxisMinimum(-100f);
                    expiredChart.getAxisRight().setLabelCount(3,true);
                    //activeChart.setRenderer(new MyBarChartRenderer(activeChart, activeChart.getAnimator(), activeChart.getViewPortHandler()));
                    /////////////Expired////////////


                    /////////////Finished////////////
                    finishedXAxis.setGranularity(1f); // minimum axis-step (interval) is 1
                    finishedXAxis.setValueFormatter(new CustomFormatter(finishedXAxisLabel));
                    //  activeXAxis.setValueFormatter(new MyXAxisValueFormatter(activeXAxisLabel));

                    finishedChart.setXAxisRenderer(new CustomXAxisRenderer(finishedChart.getViewPortHandler(), finishedXAxis, finishedChart.getTransformer(YAxis.AxisDependency.LEFT)));
                    //finishedXAxis.setLabelCount(finishedXAxisLabel.size()+1, true);
                    if(finishedXAxis.getTextSize()<10){
                        finishedXAxis.setLabelCount(finishedXAxisLabel.size(), true);
                    }
                    else{
                        finishedXAxis.setLabelCount(10,true);
                    }


                    finishedChart.getAxisLeft().setDrawLabels(false);
                    finishedChart.getAxisRight().setDrawLabels(true);
                    finishedChart.getAxisRight().setDrawZeroLine(true);
                    finishedChart.getAxisLeft().setAxisMinimum(-100f);
                    finishedChart.getAxisLeft().setAxisMaximum(100f);
                    finishedChart.getAxisRight().setLabelCount(20,true);
                    finishedChart.getAxisRight().setAxisMaximum(100f);
                    finishedChart.getAxisRight().setAxisMinimum(-100f);
                    finishedChart.getAxisRight().setLabelCount(3,true);
                    //activeChart.setRenderer(new MyBarChartRenderer(activeChart, activeChart.getAnimator(), activeChart.getViewPortHandler()));
                    /////////////Finished////////////


                    /*




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


                     */
                    activeBarEntries = controller.generateBarEntries(activeList);
                    expiredBarEntries = controller.generateBarEntries(expiredList);
                    finishedBarEntries = controller.generateBarEntries(finishedList);

                    BarDataSet activeSet, expiredSet,finishedSet;
                    ArrayList<IBarDataSet> activeDataSets, expiredDataSets, finishedDataSets;
                    BarData activeData, expiredData, finishedData;

                    if (activeChart.getData() != null &&
                            activeChart.getData().getDataSetCount() > 0) {
                        Log.d("check", "onSuccess: if here");

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
                        activeData.setValueTextColor(Color.BLACK);
                        activeData.setBarWidth(0.90f);


                        activeChart.getAxisRight().setValueFormatter(new MyValueFormatter());
                        activeChart.setData(activeData);
                    }
                    activeChart.setVisibleXRangeMaximum(9);
                    activeChart.invalidate();


                    if (expiredChart.getData() != null &&
                            expiredChart.getData().getDataSetCount() > 0) {
                        Log.d("check", "onSuccess: if here");

                        expiredSet = (BarDataSet) expiredChart.getData().getDataSetByIndex(0);
                        expiredSet.setValues(expiredBarEntries);
                        expiredChart.getData().notifyDataChanged();
                        expiredChart.notifyDataSetChanged();
                    } else {
                        expiredSet = new BarDataSet(expiredBarEntries, "Active Goals");
                        expiredSet.setDrawIcons(false);
                        expiredSet.setColors(getColors());
                        expiredSet.setStackLabels(new String[]{"Proposed","Studied"});


                        expiredDataSets = new ArrayList<>();
                        expiredDataSets.add(expiredSet);

                        expiredData = new BarData(expiredDataSets);
                        expiredData.setValueFormatter(new MyValueFormatter());
                        expiredData.setValueTextSize(12);
                        expiredData.setValueTextColor(Color.BLACK);
                        expiredData.setBarWidth(0.90f);


                        expiredChart.getAxisRight().setValueFormatter(new MyValueFormatter());
                        expiredChart.setData(expiredData);
                    }
                    expiredChart.setVisibleXRangeMaximum(9);
                    expiredChart.invalidate();


                    if (finishedChart.getData() != null &&
                            finishedChart.getData().getDataSetCount() > 0) {
                        Log.d("check", "onSuccess: if here");

                        finishedSet = (BarDataSet) finishedChart.getData().getDataSetByIndex(0);
                        finishedSet.setValues(finishedBarEntries);
                        finishedChart.getData().notifyDataChanged();
                        finishedChart.notifyDataSetChanged();
                    } else {
                        finishedSet = new BarDataSet(finishedBarEntries, "Active Goals");
                        finishedSet.setDrawIcons(false);
                        finishedSet.setColors(getColors());
                        finishedSet.setStackLabels(new String[]{"Proposed","Studied"});


                        finishedDataSets = new ArrayList<>();
                        finishedDataSets.add(finishedSet);

                        finishedData = new BarData(finishedDataSets);
                        finishedData.setValueFormatter(new MyValueFormatter());
                        finishedData.setValueTextSize(12);
                        finishedData.setValueTextColor(Color.BLACK);
                        finishedData.setBarWidth(0.90f);


                        finishedChart.getAxisRight().setValueFormatter(new MyValueFormatter());
                        finishedChart.setData(finishedData);
                    }
                    finishedChart.setVisibleXRangeMaximum(9);
                    finishedChart.invalidate();


                    /*

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
                        expiredData.setBarWidth(1f);
                        expiredData.setValueTextColor(Color.BLACK);


                        expiredChart.getAxisLeft().setValueFormatter(new MyValueFormatter());
                        expiredChart.setData(expiredData);
                    }

                    expiredChart.setFitBars(false);
                    expiredChart.setVisibleXRangeMaximum(10);

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
                        finishedData.setValueTextColor(Color.BLACK);



                        finishedChart.getAxisLeft().setValueFormatter(new MyValueFormatter());
                        finishedChart.setData(finishedData);
                    }

                    finishedChart.setFitBars(false);
                    finishedChart.setVisibleXRangeMaximum(10);

                    finishedChart.invalidate();



                     */
                    activeChart.setVisibility(View.VISIBLE);

                    finishedChart.setVisibility(View.INVISIBLE);
                    expiredChart.setVisibility(View.INVISIBLE);


                    activeSet.setValues(activeBarEntries);
                    activeChart.setVisibleYRange(-100f,100f, YAxis.AxisDependency.RIGHT);
                    activeChart.setAutoScaleMinMaxEnabled(false);
                    activeChart.getData().notifyDataChanged();
                    activeChart.notifyDataSetChanged();

                    activeChart.invalidate();

                    toggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
                        @Override
                        public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                            if(isChecked){
                                if(checkedId == R.id.activeBtnMainProgress){
                                    Log.d("toggle", "onButtonChecked: active checked");
                                    activeChart.setVisibility(View.VISIBLE);

                                    finishedChart.setVisibility(View.INVISIBLE);
                                    expiredChart.setVisibility(View.INVISIBLE);

                                    /*
                                    activeSet.setValues(activeBarEntries);
                                    activeChart.getData().notifyDataChanged();
                                    activeChart.notifyDataSetChanged();
                                    activeChart.setVisibleYRange(-100f,100f, YAxis.AxisDependency.RIGHT);
                                    activeChart.setAutoScaleMinMaxEnabled(false);
                                    activeChart.getAxisRight().setValueFormatter(new MyValueFormatter());
                                    activeChart.animateY(2000);
                                  //  activeChart.invalidate();

                                     */
                                }


                                if(checkedId == R.id.expiredBtnMainProgress){
                                    Log.d("expired", "onButtonChecked: ExpiredChecked");
                                    /*
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
                                    */



                                    expiredChart.setVisibility(View.VISIBLE);


                                    activeChart.setVisibility(View.INVISIBLE);
                                    finishedChart.setVisibility(View.INVISIBLE);


                                    /*
                                    expiredSet.setValues(expiredBarEntries);
                                    expiredChart.getData().notifyDataChanged();
                                    expiredChart.notifyDataSetChanged();
                                    expiredChart.setVisibleYRange(-100f,100f, YAxis.AxisDependency.RIGHT);
                                    expiredChart.setAutoScaleMinMaxEnabled(false);
                                    expiredChart.getAxisRight().setValueFormatter(new MyValueFormatter());
                                    expiredChart.animateY(2000);

                                     */


                                }
                                if(checkedId == R.id.finishedBtnMainProgress){
                                    Log.d("finished", "onButtonChecked: finishedChecked");
                                   /*
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

                                    */
                                    finishedChart.setVisibility(View.VISIBLE);

                                    activeChart.setVisibility(View.INVISIBLE);
                                    expiredChart.setVisibility(View.INVISIBLE);

                                    /*
                                    finishedSet.setValues(finishedBarEntries);
                                    finishedChart.getData().notifyDataChanged();
                                    finishedChart.notifyDataSetChanged();
                                    finishedChart.setVisibleYRange(-100f,100f, YAxis.AxisDependency.RIGHT);
                                    finishedChart.setAutoScaleMinMaxEnabled(false);
                                    finishedChart.getAxisRight().setValueFormatter(new MyValueFormatter());
                                    finishedChart.animateY(2000);

                                     */

                                }
                            }
                        }
                    });

                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });

        /*
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



         */


        toolbar = findViewById(R.id.toolbarGoalProgress);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("toolbar clicked", "onClick: I was clicked");
              //  DataServices.getInstance().sendMainProgressViewTimeSpend((System.currentTimeMillis()/100L)-timeSpendHolder);
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
        activeChart.setDrawValueAboveBar(true);
        activeChart.setHighlightFullBarEnabled(false);
        activeChart.getDescription().setEnabled(false);
        activeChart.animateY(3000);


        expiredChart.setPinchZoom(false);
        expiredChart.setDrawGridBackground(true);
        expiredChart.setDrawBarShadow(false);
        expiredChart.setDrawValueAboveBar(true);
        expiredChart.setHighlightFullBarEnabled(false);
        expiredChart.getDescription().setEnabled(false);
        expiredChart.animateY(3000);


        finishedChart.setPinchZoom(false);
        finishedChart.setDrawGridBackground(true);
        finishedChart.setDrawBarShadow(false);
        finishedChart.setDrawValueAboveBar(true);
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


        Legend finishLegend = finishedChart.getLegend();
        finishLegend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        finishLegend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        finishLegend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        finishLegend.setDrawInside(false);
        finishLegend.setFormSize(8f);
        finishLegend.setFormToTextSpace(4f);
        finishLegend.setXEntrySpace(6f);


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
            mFormat = new DecimalFormat("###,###,##0.00");       }

        @Override
        public String getBarStackedLabel(float value, BarEntry stackedEntry) {
            super.getBarStackedLabel(value, stackedEntry);
            Log.d("beforeFormat", "getBarStackedLabel: " + value);
            Log.d("afterFormat", "getBarStackedLabel: " + mFormat.format(abs(value)));
            if(stackedEntry.getPositiveSum()==0 && stackedEntry.getNegativeSum()==0){
                return "";
            }
            return mFormat.format(abs(value)) + " h";
        }

        private float abs(float value) {
            if(value ==-0.0)
                return 0;
            if (value>=0)
                return value;
            else
                return -1*value;
        }

        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            return Math.round(abs(value)) + " h";
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
                String line[] = formattedLabel.split("\n");
                for (String a:
                        line) {
                    Log.d("labels", "drawLabel: " + a);
                }

                Utils.drawXAxisValue(c, line[0], x, y, mAxisLabelPaint, anchor, angleDegrees);
                for (int i =1; i<line.length;i++)
                    Utils.drawXAxisValue(c, line[i], x + (i)*mAxisLabelPaint.getTextSize(), y, mAxisLabelPaint, anchor, angleDegrees);

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
            //Log.d("getFormmated", "getFormattedValue: " + value + " " + (int)(value));
            //Log.d("getFormmated", "getFormattedValue: " + value + " " + Math.ceil(value));

            if(Math.ceil(value)<getValues().length){
              //  Log.d("getValues", "getFormattedValue: " + getValues()[((int) value)]);
              //  Log.d("getValues", "getFormattedValue: " + getValues()[((int) Math.abs(Math.ceil(value)))]);

                finalLabel  = getValues()[((int) Math.abs(Math.ceil(value)))];
            }
            else{
            //    Log.d("getValues", "getFormattedValue: " + getValues()[((int) Math.abs(Math.floor(value)))]);

                finalLabel  = getValues()[((int) Math.abs(Math.floor(value)))];
            }
            //Log.d("label", "getFormattedValue: " + finalLabel);


            return  finalLabel;
        }




    }
    /*

    public class MyXAxisValueFormatter extends ValueFormatter implements IAxisValueFormatter {
        private ArrayList<String> mValues;

        MyXAxisValueFormatter(ArrayList<String> values) {
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            int index = Math.round(value);

            if(index >= mValues.size()){
                index = mValues.size()-1;
            }
            return mValues.get(index);
        }
    }

     */

    /*
    public class BarChartCustomRenderer extends BarChartRenderer {

        public BarChartCustomRenderer(BarDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
            super(chart, animator, viewPortHandler);
        }

        public void drawValue(Canvas c, IValueFormatter formatter, float value, Entry entry, int dataSetIndex, float x, float y, int color) {
            mValuePaint.setColor(color);
            c.save();
            c.rotate(90f, x, y);
            Log.d("here", formatter.getFormattedValue(value, entry, dataSetIndex, mViewPortHandler) );
            c.drawText(formatter.getFormattedValue(value, entry, dataSetIndex, mViewPortHandler), x, y, mValuePaint);
            c.restore();
        }
    }

     */






    /**
     * Used to rotate the bar labels, but if you implement this then it will make them overlap
     */
    /*
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

     */
}