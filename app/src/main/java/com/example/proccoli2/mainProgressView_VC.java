package com.example.proccoli2;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class mainProgressView_VC extends AppCompatActivity {
    private mainProgressView mainProgressView;
    public mainProgressView_VC(mainProgressView mainProgressView){
        this.mainProgressView = mainProgressView;
    }

    public  ArrayList<BarEntry> generateBarEntries(ArrayList<GoalModel> list){
        ArrayList<BarEntry> values = new ArrayList<BarEntry>();
        for(int i = 0; i<list.size();i++){
            float studied = (float) list.get(i).getStudiedTime();
            float proposed = (float) list.get(i).getProposedStudyTime();
            proposed = -1*proposed;
            values.add(new BarEntry(i,new float[]{proposed,studied}));
          //  values.add(new BarEntry(proposed,studied,list.get(i)));
        }
        Log.d("values", "generateBarEntries: " + values);
        return values;
    }

    public ArrayList<String> generateXAxisLabel(ArrayList<GoalModel> list){
        ArrayList<String> xAxisLabels = new ArrayList<>();
        for (GoalModel goal:list) {
            String[] label = goal.getBigGoal().split(" ");
            String finalLabel = "";
            for(int i = 0; i< label.length;i++){
                finalLabel = finalLabel + "\n" + label[i];
            }
            xAxisLabels.add(finalLabel);
        }
        return xAxisLabels;

    }
}
