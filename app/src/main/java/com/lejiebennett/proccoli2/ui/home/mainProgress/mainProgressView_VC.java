package com.lejiebennett.proccoli2.ui.home.mainProgress;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.lejiebennett.proccoli2.NewModels.GoalModel;
import com.github.mikephil.charting.data.BarEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class mainProgressView_VC extends AppCompatActivity {
    private mainProgressView mainProgressView;
    public mainProgressView_VC(mainProgressView mainProgressView){
        this.mainProgressView = mainProgressView;
    }



    /**
     * This assumges that getStudiedTime is already in hours not in unix seconds or minutes
     * Fix this method to change the units
     * @param list The goal data
     * @return the bar entry values
     */
    public  ArrayList<BarEntry> generateBarEntries(ArrayList<com.lejiebennett.proccoli2.NewModels.GoalModel> list){
        ArrayList<BarEntry> values = new ArrayList<BarEntry>();
        for(int i = 0; i<list.size();i++){
            float studied = (float) hourConverterForChart(list.get(i).getStudiedTime());
            float proposed = (float) list.get(i).getProposedStudyTime();
            proposed = -1*proposed;
            values.add(new BarEntry(i,new float[]{proposed,studied}));
            Log.d("entries", "createdBar: " + proposed + " " + studied + " " + list.get(i).getBigGoal());
          //  values.add(new BarEntry(proposed,studied,list.get(i)));
        }
        Log.d("values", "generateBarEntries: " + values);
        return values;
    }

    public ArrayList<String> generateXAxisLabel(ArrayList<com.lejiebennett.proccoli2.NewModels.GoalModel> list){
        ArrayList<String> xAxisLabels = new ArrayList<>();
        for (GoalModel goal:list) {
            /*
            String[] label = goal.getBigGoal().split(" ");
            String finalLabel = "";
            for(int i = 0; i< label.length;i++){
                finalLabel = finalLabel + "\n" + label[i];
            }


            xAxisLabels.add(finalLabel);

             */

           if(goal.getWhenIsDue()==0 && goal.getBigGoal()=="#")
               xAxisLabels.add("");

           else{
               xAxisLabels.add(convertDateForChart(new Date(goal.getWhenIsDue())) + "\n" + xlabelTextSplitterWithCharaterLimit(goal.getBigGoal()));
           }
          //  xAxisLabels.add(convertDateForChart(new Date(goal.getWhenIsDue())) + " " + goal.getBigGoal());
        }
        Log.d("number of elements", "generateXAxisLabel: " + xAxisLabels + xAxisLabels.size());
        return xAxisLabels;

    }

    public String unixToStringMonthDay(int unix){
        Date date = new java.util.Date(unix*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMMdd");
        return sdf.format(date);

    }

    public String xlabelTextSplitterWithCharaterLimit(String goalName) {
        String response = "";
        String[] words = goalName.split(" ");
        for (String word : words) {
            if (response != "") {
                if(word.length()>7)
                    response += "\n" + word.subSequence(0,8);
                else
                    response += "\n" + word;
            } else {
                if(word.length()>7)
                    response = response + word.subSequence(0,8);
                else
                    response = response + word;
            }
        }

        return response;
    }

    public String convertDateForChart(Date date){
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMM, dd");
        return sdf.format(date);
    }

    public double hourConverterForChart(double studiedTime){
        double a = studiedTime/3600;
        double roundOff = Math.round(a * 100.0) / 100.0;
        return roundOff;
    }

         public void assignGoal(com.lejiebennett.proccoli2.NewModels.GoalModel goal){
             long todayUnixDateTime = System.currentTimeMillis() / 1000L;
             int goalPersonal = (int) goal.getPersonalDeadline();
             int goalDue = (int)goal.getWhenIsDue();
             boolean goalComplete =goal.isCompleted();

             Log.d("todayCurrently", "assignGoal: UNIX TIME CURRENT" + todayUnixDateTime);
             Log.d("CompleteBy", "assignGoal: " + goal.isCompleted());

             if((int)todayUnixDateTime-goalDue < 0 && goalComplete == false){
                 mainProgressView.activeList.add(goal);
                 Log.d("Ad", "assignGoal: adding to activeDue" + mainProgressView.activeList);

             }


             if((int)todayUnixDateTime-goalDue<0 && goalComplete==true){
                 mainProgressView.finishedList.add(goal);
                 Log.d("FD", "assignGoal: adding to finishDue" + mainProgressView.finishedList);

             }

             if((int)todayUnixDateTime-goalDue>0 && goalComplete==true){
                 mainProgressView.finishedList.add(goal);
                 Log.d("FD", "assignGoal: adding to finishDue" + mainProgressView.finishedList);

             }

             if((int)todayUnixDateTime-goalDue>0 && goalComplete == false){
                 mainProgressView.expiredList.add(goal);
                 Log.d("ED", "assignGoal: adding to expiredGoal" + mainProgressView.expiredList);

             }
         }

    public ArrayList<GoalModel> checkBarCount(ArrayList<GoalModel> list){
        ArrayList<GoalModel> dummyList = new ArrayList<>();
        dummyList.addAll(list);
        if(dummyList.size()<9){
              for(int i = dummyList.size(); i < 9; i++){
                  dummyList.add(new GoalModel("#",0,0));
              }
        }
        return dummyList;
    }
}
