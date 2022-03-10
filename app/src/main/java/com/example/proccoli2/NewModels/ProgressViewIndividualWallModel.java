package com.example.proccoli2.NewModels;
import android.provider.ContactsContract;

import com.github.mikephil.*;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.HashMap;

public class ProgressViewIndividualWallModel {
    long timeSpendHolder;
    String dataSourceType = SingletonStrings.ROBUST_DATA_REF;
    String mainGoalId;
    ArrayList<String> sortedSubsIds;
    HashMap<String,Object> rawData;

    boolean isItForGoalCompletion;
    HashMap<Integer,ArrayList<ChartLimitLine>> deadlineChartLimitLines;
    String clickEventId;

    ArrayList<String> xAxisLabelNamesForRobustData = new ArrayList<>();
    double yMax =0.0;
    ArrayList<String> xAxisLabelNamesWithBlackData = new ArrayList<>();
    long xMinProposedStartDate;
    int xMinFirstStudyTime;
    ArrayList<BarEntry> parsedDataWithBlankData = new ArrayList<>();
    ArrayList<BarEntry> parsedDataForRobustData;

    public ProgressViewIndividualWallModel(String goalId, ArrayList<String> subGoalIds){
        this.mainGoalId = goalId;
        this.sortedSubsIds = subGoalIds;
        this.clickEventId = DataServices.getInstance().getAlphaNumericString(11);
        ProgressviewIndivdiualWallModel.sharedInstared = self;
        DataServices.chartOpenBtnCLickIndividualWall(goalId,this.clickEventId);
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
          //  let total = point.yValues?.reduce(0,+)
           // double total = barEntry.setY(barEntry.getYVals());
            if(yMax < getValueOrDefault(total,0.0)){
                yMax = getValueOrDefault(total,0.0);
            }
        }
    }

    public void callDetailedStudyData(boolean isItForGoalCompletion, ResultHandler<Object> handler){
        DataServices.requestStudiedTimeDetailsForIndividualWallProgress(mainGoalId)
        if status {
            guard let docSnap = response else {return}
            self.parseProgressDocForRobustData(docSnap: docSnap) { (status, dataSets) in
                if status {
                    self.isItForGoalCompletion = isItForGoalCompletion
                    self.parsedDataForRobustData = dataSets
                    completion(true)
                }
            }
        }
    }
}
