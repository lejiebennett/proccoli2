package com.example.proccoli2.NewModels;

import static com.example.proccoli2.NewModels.SingletonStrings.EXIST_REF;
import static com.example.proccoli2.NewModels.SingletonStrings.NO_SUB_GOAL_REF;
import static com.example.proccoli2.NewModels.SingletonStrings.ROBUST_DATA_REF;

import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ProgressViewIndividualWallModel {
    long timeSpendHolder = System.currentTimeMillis()/100L;
    ProgressViewIndividualWallModel sharedInstance;
    String dataSourceType = ROBUST_DATA_REF;
    String mainGoalId;
    ArrayList<String> sortedSubsIds;
    HashMap<String,Object> rawData;

    boolean isItForGoalCompletion;
    HashMap<Integer,ArrayList<LimitLine>> deadlineChartLimitLines;
    String clickEventId;

    ArrayList<String> xAxisLabelNamesForRobustData = new ArrayList<>();
    double yMax =0.0;
    ArrayList<String> xAxisLabelNamesWithBlackData = new ArrayList<>();
    int xMinProposedStarDate = 0.0;
    /*
    int xMinProposedStarDate = 0.0 {
        return Date.dailyBaseTimeIntervalConverterForDeadlines(deadline: IndividualWallVC.sharedInstance?.data?.proposedStartDate ?? 0.0)
    }

     */
    int xMinFirstStudyTime;
    /*
    int xMinFirstStudyTime = Date.dailyBaseTimeIntervalConverterForDeadlines(deadline: Date().timeIntervalSince1970)

     */
    ArrayList<BarEntry> parsedDataWithBlankData = new ArrayList<>();
    ArrayList<BarEntry> parsedDataForRobustData;

    public ProgressViewIndividualWallModel(String goalId, ArrayList<String> subGoalIds){
        this.mainGoalId = goalId;
        this.sortedSubsIds = subGoalIds;
        this.clickEventId = DataServices.getInstance().getAlphaNumericString(11);
        this.sharedInstance = this;
        DataServices.getInstance().chartOpenBtnClickIndividualWall(goalId,this.clickEventId);
        //print("ProgressViewIndividualWallModel DE-INIT 4444")
    }


    public static <T> T getValueOrDefault(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }

    public void calculateYmax(){
        ArrayList<BarEntry> data = this.parsedDataForRobustData;
        if(data==null){
            return;
        }
        for(BarEntry barEntry: data){
            double total = barEntry.getYVals().reduce(0,+);
            if(yMax < getValueOrDefault(total,0.0)){
                yMax = getValueOrDefault(total,0.0);
            }
        }
    }

    public void callDetailedStudyData(boolean isItForGoalCompletion2, ResultHandler<Object> handler){
        DataServices.requestStudiedTimeDetailsForIndividualWallProgress(mainGoalId, new ResultHandler<Object>() {
            @Override
            public void onSuccess(Object data) {
                HashMap<String,Object> results = (HashMap<String, Object>) data;
                if(results.get("_response")!=null){
                    DocumentSnapshot docSnap = (DocumentSnapshot) results.get("_response");
                    parseProgressDocForRobustData(docSnap, new ResultHandler<Object>() {
                        @Override
                        public void onSuccess(Object data) {
                            HashMap<String,Object> hashMap = (HashMap<String, Object>) data;
                            if((boolean)hashMap.get("_status") == true){
                                isItForGoalCompletion = isItForGoalCompletion2;
                                parsedDataForRobustData = (ArrayList<BarEntry>) hashMap.get("dataSets");
                                HashMap<String,Object> result = new HashMap<>();
                                result.put("_status", true);
                                handler.onSuccess(result);

                            }
                        }

                        @Override
                        public void onFailure(Exception e) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void parseProgressDocForRobustData(DocumentSnapshot docSnap, ResultHandler<Object> handler) {
        //completion:@escaping(_ status:Bool, _ response:[BarChartDataEntry]?);
        HashMap<String, Object> data = (HashMap<String, Object>) docSnap.getData();
        if (data == null) {
            HashMap<String, Object> resultHandler = new HashMap<>();
            resultHandler.put("_status", true);
            resultHandler.put("_response", null);
            handler.onSuccess(resultHandler);
        } else {
            ArrayList<BarEntry> response = new ArrayList<>();
            HashMap<String,Object> sortedData = (HashMap<String, Object>) data.entrySet().stream().sorted((Comparator<? super Map.Entry<String, Object>>) Map.Entry.<String,BarEntry>comparingByKey());
           // HashMap<String, Object> sortedData = Collections.sort(data);
            this.rawData = data;
            int i = 1;
            for(Map.Entry<String,Object> singlePack: sortedData.entrySet()) {
                if (singlePack.getKey() != EXIST_REF) {
                    this.getyValues((HashMap<String, Double>) singlePack.getValue(), new ResultHandler<Object>() {
                        @Override
                        public void onSuccess(Object data) {
                            HashMap<String, Object> result = (HashMap<String, Object>) data;
                            if ((boolean) result.get("_status") == true) {
                                ArrayList<Double> yValues = (ArrayList<Double>) result.get("_response");
                                xAxisLabelNamesForRobustData.add(Date.reverseDailyBaseTimeIntervalASString(singlePack.getKey()));
                                response.add(new BarEntry((double) i, yValues));
                                if (i == shapeSize.chartdataPoint) {
                                    i = 1;
                                } else {
                                    i = i + 1;
                                }
                                if (xMinFirstStudyTime > (int) getValueOrDefault(singlePack.getKey(), System.currentTimeMillis() / 100L)) {
                                    xMinFirstStudyTime = (int) getValueOrDefault(singlePack.getKey(), System.currentTimeMillis() / 100L);
                                }

                                if (response.size() == (((HashMap<?, ?>) data).keySet().size()) - 1) {
                                    HashMap<String, Object> hashMap = new HashMap<>();
                                    hashMap.put("_status", true);
                                    hashMap.put("_response", response);
                                    handler.onSuccess(hashMap);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Exception e) {

                        }
                    });
                } else if (sortedData.size() == 1 && singlePack.getKey() == EXIST_REF) {
                    //empty doc let chart refresh data
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("_status", true);
                    hashMap.put("_response", null);

                }
            }
        }
    }
    public void getyValues(HashMap<String,Double> data,ResultHandler<Object> handler) {
        // completion:@escaping(_ status:Bool, _ response:[Double])->()
        ArrayList<Double> response = new ArrayList<>();
        for(String id: sortedSubsIds){
            if(data.get(id)!=null){
                Double singleDataPoint = data.get(id);
                response.add(singleDataPoint/60);

            }
			else {
                //give it 0
                response.add(0.0);
            }
            if(response.size() == sortedSubsIds.size()) {
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("_status",true);
                hashMap.put("_response", response);
                handler.onSuccess(hashMap);
            }
        }
    }

    public int xMinCalculatorForBlankChart() {
        if(this.xMinFirstStudyTime <= this.xMinProposedStarDate) {
            return xMinFirstStudyTime;
        }
		else {
            int cal = (this.xMinFirstStudyTime - this.xMinProposedStarDate) % 86400;
            return this.xMinProposedStarDate + cal;
        }
    }

    public void parseProgressDocDataWithBlankData(ResultHandler<Object> handler) {
//completion:@escaping(_ status:Bool, _ response:[BarChartDataEntry]?, _ deadlineLines:[Int:[ChartLimitLine]]?
        HashMap<String,Object> sortedData = this.rawData;
        if(sortedData==null){
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("_status", true);
            hashMap.put("_response",null);
            hashMap.put("_deadlineLines",null);
        }

        ArrayList<BarEntry> response = new ArrayList<>();
        int secondInDay = 86400;
        int dueDate = Date.dailyBaseTimeIntervalConverterForDeadlines(IndividualWallVC.sharedInstance.data.whenIsDue ?? 0.0);
        int i = 1;
        int counter = this.xMinCalculatorForBlankChart();
        int dataPointCount = ((dueDate - counter) / secondInDay) + 1;

        while(counter <= dueDate) {
            HashMap<String,Double> singlePack = (HashMap<String, Double>) sortedData[counter];
            if(singlePack!=null){
                this.getyValues(singlePack, new ResultHandler<Object>() {

                    @Override
                    public void onSuccess(Object data) {
                        HashMap<String,Object> hashMap = (HashMap<String, Object>) data;
                        if((boolean)hashMap.get("_status")==true){
                            xAxisLabelNamesWithBlackData.add(Date.reverseDailyBaseTimeIntervalAsString(counter));
                            ArrayList<Double> yValues = (ArrayList<Double>)hashMap.get("_response");
                            response.add(new BarEntry((double)i,yValues));
                            if(i==shapeSize.chartDataPoint){
                                i = 1;
                            }
                            else{
                                i=i+1;
                            }
                        }
                    }

                    @Override
                    public void onFailure(Exception e) {

                    }
                });
                }
			else {
                //send blank data
                xAxisLabelNamesWithBlackData.add(Date.reverseDailyBaseTimeIntervalAsString(counter));
                response.add(this.addBlankData((double)(i));
                if(i == shapeSize.chartDataPoint) {
                    i = 1;
                } else {
                    i = i+1;
                }
            }

            if(response.size() == dataPointCount) {
                counter += secondInDay;
                //data points are done
                //handle chart limit lines
                this.chartLimitLines((response.size() / shapeSize.chartDataPoint) + 1), new ResultHandler<Object>() {
                    @Override
                    public void onSuccess(Object data) {
                        HashMap<String,Object> hashMap = (HashMap<String, Object>) data;
                        if(hashMap.put("_data")==n) {
                        }
                    }

                    @Override
                    public void onFailure(Exception e) {

                    }
                });
            }

        }


    }
    public void chartLimitLines(int cellSize, IndividualGoalModel data, ResultHandler<Object> handler ) {
        //completion:@escaping(_ status:Bool, _ response:[Int:[ChartLimitLine]])->()
        ArrayList<HashMap<Integer,LimitLine>> response = new ArrayList<>();
        for(int i = 0; i <cellSize; i++){
            HashMap<Integer,LimitLine> element = new HashMap<>();
            element.put(i,new ArrayList<LimitLine>);
            response.add(element);
        }

        //let data = IndividualWallVC.sharedInstance.data;
        long personalDeadline = data.personalDeadline;
        LimitLine limitLineDeadline = new LimitLine((int)getValueOrDefault(this.lineIndexCalculator(personalDeadline),this.lineIndexCalculator((long) 0.0)),"Deadline");
        limitLineDeadline.setLineColor(Color.RED);
        limitLineDeadline.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        response[this.chunkIndexCalculator(personalDeadline ?? 0.0)].add(limitLineDeadline);

        long dueDate = data.whenIsDue;
        LimitLine limitLineDue = new LimitLine((int)getValueOrDefault(this.lineIndexCalculator(dueDate),this.lineIndexCalculator((long)0.0)),"Due");
        limitLineDue.setLineColor(Color.RED);
        limitLineDue.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        response[this.chunkIndexCalculator(getValueOrDefault(dueDate,0.0))].add(limitLineDue);

        ArrayList<IndividualSubGoalStructModel> subs = data.getSubGoals();
        if(subs!=null){
            int counter = subs.size();
            if(counter>0) {
                for (IndividualSubGoalStructModel sub : subs) {
                    counter = counter - 1;
                    if (sub.get_subGoalName() != NO_SUB_GOAL_REF) {
                        Float d = Float.valueOf((this.lineIndexCalculator(sub.get_deadline())));
                        LimitLine limitLine = new LimitLine(d, "");
                        limitLine.setLineColor(Color.CYAN);
                        response[this.chunkIndexCalculator(sub.get_deadline())] ?.append(limitLine)
                    }
                    if (counter == 0) {
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("_status", true);
                        hashMap.put("_response", response);
                        handler.onSuccess(hashMap);
                    }
                }
            }
            else {
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("_status",true);
                hashMap.put("_response",response);
                handler.onSuccess(hashMap);
            }
        }
		else {
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("_status",true);
            hashMap.put("_response",response);
            handler.onSuccess(hashMap);
        }

    }

    public int lineIndexCalculator(long deadline){
        long dayIndSecond = (long) 86400.0;
        long dayLater = (deadline - (long)(this.xMinCalculatorForBlankChart())) / dayIndSecond;
        //chart data point index
        return ((int)(dayLater) % shapeSize.chartDataPoint) + 1;

    }
    public int chunkIndexCalculator(long deadline){
        long dayIndSecond = (long)86400.0;
        long dayLater = (deadline - (long)(this.xMinCalculatorForBlankChart())) / dayIndSecond;
        return ((int)(dayLater) / shapeSize.chartDataPoint;
    }

    public BarEntry addBlankData(Double i){
        ArrayList<Double> yValues = new ArrayList<>();
        for(int j = 0; j <this.sortedSubsIds.size();j++) {
            yValues.add(0.0);
        }
        return new BarDataSet(i, yValues);
    }
    public void dataSourceChange(String type) {
        this.dataSourceType = type;
        if(this.dataSourceType == ROBUST_DATA_REF) {
            IndividualWallChartView.sharedInstance.getSignalAfterSourceChanged(parsedDataForRobustData,xAxisLabelNamesForRobustData);
        }
		else if(dataSourceType == SingletonStrings.BLANK_DATA_REF) {
            if(let data = self.parsedDataWithBlankData) {
                // alredy parsed before
                IndividualWallChartView.sharedInstance.getSignalAfterSourceChanged(data, xAxisLabelNamesWithBlackData);
            }
			else {
			    this.parseProgressDocDataWithBlankData(new ResultHandler<Object>() {

                    @Override
                    public void onSuccess(Object data) {
                        HashMap<String,Object> result = (HashMap<String,Object>) data;
                        if((boolean)result.get("_status") == true){
                            parsedDataWithBlankData = (ArrayList<BarEntry>) result.get("_response");
                            deadlineChartLimitLines = (HashMap<Integer, ArrayList<LimitLine>>) result.get("_deadlineLines");
                            IndividualWallChartView.sharedInstance.getSignalAfterSourceChanged(parsedDataWithBlankData,xAxisLabelNamesWithBlackData);
                        }
                    }

                    @Override
                    public void onFailure(Exception e) {

                    }
                });
            }

        }

    }

}
