package com.example.proccoli2.NewModels;

import static com.example.proccoli2.NewModels.SingletonStrings.EXIST_REF;
import static com.example.proccoli2.NewModels.SingletonStrings.NO_SUB_GOAL_REF;
import static com.example.proccoli2.NewModels.SingletonStrings.ROBUST_DATA_REF;

import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.proccoli2.ui.home.individualWall.individualChart.goalProgressView;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.data.BarEntry;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ProgressViewIndividualWallModel {
    ShapeSize shapeSize = new ShapeSize();
    long timeSpendHolder = System.currentTimeMillis()/100L;
    static ProgressViewIndividualWallModel sharedInstance;
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
    int xMinProposedStarDate;
    /*
    int xMinProposedStarDate = 0.0 {
        return Date.dailyBaseTimeIntervalConverterForDeadlines(deadline: IndividualWallVC.sharedInstance?.data?.proposedStartDate ?? 0.0)
    }

     */
    int xMinFirstStudyTime;

    /*
    int xMinFirstStudyTime = Date.dailyBaseTimeIntervalConverterForDeadlines(deadline: Date().timeIntervalSince1970)

     */

    public void setxMinProposedStarDate(long xMinProposedStarDate) {
        this.xMinProposedStarDate = new DateExtended().dailyBaseTimeIntervalConverterForDeadlines(xMinProposedStarDate);
    }


    ArrayList<BarEntry> parsedDataWithBlankData = new ArrayList<>();
    ArrayList<BarEntry> parsedDataForRobustData;
    IndividualGoalModel goalModel;
    int parseProgressDocForRobustDatai;
    int parseProgressDocForRobustDatacounter;

    int parseProgressDocForBlankDatai;
    int parseProgressDocForBlankDatacounter;

    public static ProgressViewIndividualWallModel getSharedInstance() {
        return sharedInstance;
    }

    public ProgressViewIndividualWallModel(String goalId, ArrayList<String> subGoalIds, IndividualGoalModel goalModel){
        this.mainGoalId = goalId;
        this.sortedSubsIds = subGoalIds;
        this.clickEventId = DataServices.getInstance().getAlphaNumericString(11);
        this.sharedInstance = this;
        DataServices.getInstance().chartOpenBtnClickIndividualWall(goalId,this.clickEventId);
        //print("ProgressViewIndividualWallModel DE-INIT 4444")
        this.xMinFirstStudyTime = new DateExtended().dailyBaseTimeIntervalConverterForDeadlines(System.currentTimeMillis()/100L);
        this.xMinProposedStarDate = new DateExtended().dailyBaseTimeIntervalConverterForDeadlines((long) 0.0);
        this.goalModel = goalModel;

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
            float[] yValsForEntry = barEntry.getYVals();
            float floatTotal = 0;
            for(float fl: yValsForEntry){
                floatTotal = floatTotal + fl;
            }
            double total = floatTotal;
            if(yMax < getValueOrDefault(total,0.0)){
                yMax = getValueOrDefault(total,0.0);
            }
        }
    }

    public void callDetailedStudyData(boolean isItForGoalCompletion2, ResultHandler<Object> handler){
        DataServices.requestStudiedTimeDetailsForIndividualWallProgress(mainGoalId, new ResultHandler<Object>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
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
         //   HashMap<String,Object> sortedData = (HashMap<String, Object>) data.entrySet().stream().sorted((Comparator<? super Map.Entry<String, Object>>) Map.Entry.<String,BarEntry>comparingByKey());
            Map<String, Object> sortedData = new TreeMap<String, Object>(data);

            // HashMap<String, Object> sortedData = Collections.sort(data);
            this.rawData = data;

           // int i = 1;
            parseProgressDocForRobustDatai = 1;
            for(Map.Entry<String,Object> singlePack: sortedData.entrySet()) {
                if (singlePack.getKey() != EXIST_REF) {
                    this.getyValues((HashMap<String, Double>) singlePack.getValue(), new ResultHandler<Object>() {
                        @Override
                        public void onSuccess(Object data) {
                            HashMap<String, Object> result = (HashMap<String, Object>) data;
                            if ((boolean) result.get("_status") == true) {
                                ArrayList<Double> yValues = (ArrayList<Double>) result.get("_response");
                                xAxisLabelNamesForRobustData.add(new DateExtended().reverseDailyBaseTimeIntervalAsString(singlePack.getKey()));
                                Float[] array = (Float[]) yValues.toArray();
                                float[] floatArray = new float[array.length];
                                int arrayCounter = 0;
                                for (Float fl: array) {
                                    floatArray[arrayCounter] = fl.floatValue();
                                    arrayCounter = arrayCounter +1;
                                }
                                response.add(new BarEntry(parseProgressDocForRobustDatai,floatArray));
                                if (parseProgressDocForRobustDatai == shapeSize.chartDataPoint) {
                                    parseProgressDocForRobustDatai = 1;
                                } else {
                                    parseProgressDocForRobustDatai = parseProgressDocForRobustDatai + 1;
                                }
                                if (xMinFirstStudyTime > (int) getValueOrDefault(singlePack, System.currentTimeMillis() / 100L)) {
                                    xMinFirstStudyTime = (int) getValueOrDefault(singlePack, System.currentTimeMillis() / 100L);
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
                    handler.onSuccess(hashMap);

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void parseProgressDocDataWithBlankData(long whenIsDue, ResultHandler<Object> handler) {
//completion:@escaping(_ status:Bool, _ response:[BarChartDataEntry]?, _ deadlineLines:[Int:[ChartLimitLine]]?
        HashMap<String,Object> sortedData = this.rawData;
        if(sortedData==null){
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("_status", true);
            hashMap.put("_response",null);
            hashMap.put("_deadlineLines",null);
            handler.onSuccess(hashMap);
        }

        ArrayList<BarEntry> response = new ArrayList<>();
        int secondInDay = 86400;
        int dueDate;
//        int dueDate = new DateExtended().dailyBaseTimeIntervalConverterForDeadlines((double)getValueOrDefault(IndividualWallVC.sharedInstance.data.whenIsDue, 0.0));
        try{
            dueDate = new DateExtended().dailyBaseTimeIntervalConverterForDeadlines(whenIsDue);

        }
        catch (Exception e){
            dueDate = new DateExtended().dailyBaseTimeIntervalConverterForDeadlines((long)0.0);
        }
        //int i = 1;
        //int counter = this.xMinCalculatorForBlankChart();
        parseProgressDocForBlankDatai = 1;
        parseProgressDocForBlankDatacounter = this.xMinCalculatorForBlankChart();
        int dataPointCount = ((dueDate - parseProgressDocForBlankDatacounter) / secondInDay) + 1;

        while(parseProgressDocForBlankDatacounter <= dueDate) {
            HashMap<String,Double> singlePack = (HashMap<String, Double>) sortedData.get(parseProgressDocForRobustDatacounter);
            if(singlePack!=null){
                this.getyValues(singlePack, new ResultHandler<Object>() {

                    @Override
                    public void onSuccess(Object data) {
                        HashMap<String,Object> hashMap = (HashMap<String, Object>) data;
                        if((boolean)hashMap.get("_status")==true){
                            xAxisLabelNamesWithBlackData.add(new DateExtended().reverseDailyBaseTimeIntervalAsString(String.valueOf(parseProgressDocForBlankDatacounter)));
                            ArrayList<Double> yValues = (ArrayList<Double>)hashMap.get("_response");
                            Float[] array = (Float[]) yValues.toArray();
                            float[] floatArray = new float[array.length];
                            int arrayCounter = 0;
                            for (Float fl: array) {
                                floatArray[arrayCounter] = fl.floatValue();
                                arrayCounter = arrayCounter +1;

                            }


                            response.add(new BarEntry(parseProgressDocForBlankDatai,floatArray));
                            if(parseProgressDocForBlankDatai==shapeSize.chartDataPoint){
                                parseProgressDocForBlankDatai = 1;
                            }
                            else{
                                parseProgressDocForBlankDatai=parseProgressDocForBlankDatai+1;
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
                xAxisLabelNamesWithBlackData.add(new DateExtended().reverseDailyBaseTimeIntervalAsString(String.valueOf(parseProgressDocForBlankDatacounter)));
                response.add(this.addBlankData((double)(parseProgressDocForBlankDatai)));
                if(parseProgressDocForBlankDatai == shapeSize.chartDataPoint) {
                    parseProgressDocForBlankDatai = 1;
                } else {
                    parseProgressDocForBlankDatai = parseProgressDocForBlankDatai+1;
                }
            }

            if(response.size() == dataPointCount) {
                parseProgressDocForBlankDatacounter += secondInDay;
                //data points are done
                //handle chart limit lines
                this.chartLimitLines((int) (response.size() / shapeSize.chartDataPoint) + 1,goalModel, new ResultHandler<Object>() {
                    @Override
                    public void onSuccess(Object data) {

                        HashMap<String,Object> hashMap = (HashMap<String, Object>) data;
                        if((boolean)hashMap.get("_status")==true) {
                            HashMap<String,Object> results = new HashMap<>();
                            //completion:@escaping(_ status:Bool, _ response:[BarChartDataEntry]?, _ deadlineLines:[Int:[ChartLimitLine]]?

                            hashMap.put("_status",true);
                            hashMap.put("_response",response);

                            hashMap.put("_deadlineLines",results.get("_response"));
                        }
                    }

                    @Override
                    public void onFailure(Exception e) {

                    }
                });
            }

        }


    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void chartLimitLines(int cellSize, IndividualGoalModel data, ResultHandler<Object> handler ) {
        //completion:@escaping(_ status:Bool, _ response:[Int:[ChartLimitLine]])->()
        ArrayList<HashMap<Integer,ArrayList<LimitLine>>> response = new ArrayList<>();
        for(int i = 0; i <cellSize; i++){
            HashMap<Integer,ArrayList<LimitLine>> element = new HashMap<>();
            ArrayList<LimitLine> limitLines = new ArrayList<>();
            element.put(i,limitLines);
            response.add(element);
        }

        //let data = IndividualWallVC.sharedInstance.data;
        long personalDeadline = data.personalDeadline;
        LimitLine limitLineDeadline = new LimitLine((int)getValueOrDefault(this.lineIndexCalculator(personalDeadline),this.lineIndexCalculator((long) 0.0)),"Deadline");
        limitLineDeadline.setLineColor(Color.RED);
        limitLineDeadline.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        long chunkParameter = (long)getValueOrDefault(personalDeadline,(long)0.0);
        int index = (int)this.chunkIndexCalculator(chunkParameter);
        ArrayList<LimitLine> newLimitLines = response.get(index).get(index);
        response.get(index).replace(index,newLimitLines);

        long dueDate = data.whenIsDue;
        LimitLine limitLineDue = new LimitLine((int)getValueOrDefault(this.lineIndexCalculator(dueDate),this.lineIndexCalculator((long)0.0)),"Due");
        limitLineDue.setLineColor(Color.RED);
        limitLineDue.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        long chunkParameter2 = (long)getValueOrDefault(limitLineDue,(long)0.0);
        int index2 = (int)this.chunkIndexCalculator(chunkParameter2);
        ArrayList<LimitLine> newLimitLines2 = response.get(index2).get(index2);
        response.get(index2).replace(index2,newLimitLines2);

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

                        int index3 = this.chunkIndexCalculator(sub.get_deadline());
                        ArrayList<LimitLine> newLimitLines3 = response.get(index3).get(index3);
                        response.get(index3).replace(index3,newLimitLines3);
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
        return ((int)(dayLater) / shapeSize.chartDataPoint);
    }

    public BarEntry addBlankData(Double i){
        ArrayList<Double> yValues = new ArrayList<>();
        for(int j = 0; j <this.sortedSubsIds.size();j++) {
            yValues.add(0.0);
        }

        Float[] array = (Float[]) yValues.toArray();
        float[] floatArray = new float[array.length];
        int arrayCounter = 0;
        for (Float fl: array) {
            floatArray[arrayCounter] = fl.floatValue();
            arrayCounter = arrayCounter +1;
        }
        return new BarEntry(i.floatValue(), floatArray);
    }
    public void dataSourceChange(String type) {
        this.dataSourceType = type;
        if(this.dataSourceType == ROBUST_DATA_REF) {
            goalProgressView.getSharedInstance().getSignalAfterSourceChanged(parsedDataForRobustData,xAxisLabelNamesForRobustData);
        }
		else if(dataSourceType == SingletonStrings.BLANK_DATA_REF) {
		    ArrayList<BarEntry> data = this.parsedDataWithBlankData;
            if(data !=null) {
                // alredy parsed before
                Log.d("placeholder", "dataSourceChange: Placeholder");
               // goalProgressView.getSharedInstance().getSignalAfterSourceChanged(data, xAxisLabelNamesWithBlackData);
            }
			else {
                Log.d("placeholder", "dataSourceChange: Placeholder");
			    /*
			    this.parseProgressDocDataWithBlankData(new ResultHandler<Object>() {

                    @Override
                    public void onSuccess(Object data) {
                        HashMap<String,Object> result = (HashMap<String,Object>) data;
                        if((boolean)result.get("_status") == true){
                            parsedDataWithBlankData = (ArrayList<BarEntry>) result.get("_response");
                            deadlineChartLimitLines = (HashMap<Integer, ArrayList<LimitLine>>) result.get("_deadlineLines");
                            goalProgressView.getSharedInstance().getSignalAfterSourceChanged(parsedDataWithBlankData,xAxisLabelNamesWithBlackData);
                        }
                    }

                    @Override
                    public void onFailure(Exception e) {

                    }
                });

			     */
            }

        }

    }

}
