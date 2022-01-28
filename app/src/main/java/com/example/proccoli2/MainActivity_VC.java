package com.example.proccoli2;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class MainActivity_VC extends AppCompatActivity{
    private MainActivity mainActivity;
    public MainActivity_VC(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String calculateDaysLeft(int unix){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime unixConverted = LocalDateTime.ofEpochSecond(unix, 0, ZoneOffset.UTC);
        return String.valueOf(Duration.between(now,unixConverted).toDays());
    }

    public int countCompletedGoals(ArrayList<GoalModel> goalList){
        int counter = 0;
        for(int i = 0; i<goalList.size();i++) {
            if (goalList.get(i).getIsCompleted() == true)
                counter++;
        }
        return counter;
    }

    public void assignGoal(GoalModel goal){
        long todayUnixDateTime = System.currentTimeMillis() / 1000L;
        int goalPersonal = goal.getCompletedBy();
        int goalDue = goal.getDeadline();
        boolean goalComplete =goal.getIsCompleted();

        Log.d("todayCurrently", "assignGoal: UNIX TIME CURRENT" + todayUnixDateTime);
        Log.d("CompleteBy", "assignGoal: " + goal.getIsCompleted());


        if((int)todayUnixDateTime-goalPersonal < 0 && goalComplete == false){
            mainActivity.activePersonal.add(goal);
            Log.d("AP", "assignGoal: adding to activePersonal" + mainActivity.activePersonal);
        }

        if((int)todayUnixDateTime-goalDue < 0 && goalComplete == false){
            mainActivity.activeDue.add(goal);
            Log.d("Ad", "assignGoal: adding to activeDue" + mainActivity.activeDue);

        }

        if((int)todayUnixDateTime-goalPersonal<0 && goalComplete==true){
            mainActivity.finishedPersonal.add(goal);
            Log.d("FP", "assignGoal: adding to fisnihPersonal" + mainActivity.finishedPersonal);

        }

        if((int)todayUnixDateTime-goalDue<0 && goalComplete==true){
            mainActivity.finishedDue.add(goal);
            Log.d("FD", "assignGoal: adding to finishDue" + mainActivity.finishedDue);

        }

        if((int)todayUnixDateTime-goalPersonal>0 && goalComplete==true){
            mainActivity.finishedPersonal.add(goal);
            Log.d("FP", "assignGoal: adding to fisnihPersonal" + mainActivity.finishedPersonal);

        }

        if((int)todayUnixDateTime-goalDue>0 && goalComplete==true){
            mainActivity.finishedDue.add(goal);
            Log.d("FD", "assignGoal: adding to finishDue" + mainActivity.finishedDue);

        }

        if((int)todayUnixDateTime-goalDue>0 && goalComplete == false){
            mainActivity.expiredDue.add(goal);
            Log.d("ED", "assignGoal: adding to expiredGoal" + mainActivity.expiredDue);

        }
        if((int)todayUnixDateTime-goalPersonal>0 && goalComplete==false){
            mainActivity.expiredPersonal.add(goal);
            Log.d("EP", "assignGoal: adding to experiedPersonal" + mainActivity.expiredPersonal);

        }
    }


    Comparator<GoalModel> compareByDeadline = new Comparator<GoalModel>() {
        @Override
        public int compare(GoalModel goal1, GoalModel goal2) {
            return Integer.compare(goal1.getDeadline(),goal2.getDeadline());
        }
    };

    Comparator<GoalModel> compareByPersonal = new Comparator<GoalModel>() {
        @Override
        public int compare(GoalModel goal1 , GoalModel goal2) {
            return Integer.compare(goal1.getCompletedBy(),goal2.getCompletedBy());
        }
    };

    public ArrayList<GoalModel> setRecyclerViewList2(MainActivity mainActivity){
        boolean personalEnabled = mainActivity.personalSelected;
        boolean dueEnabled = mainActivity.dueDateSelected;
        int checkedBtn = mainActivity.toggleGroup.getCheckedButtonId();
        Log.d("setRecyclerList", "setRecyclerViewList: " + String.valueOf(personalEnabled) + " due: " + String.valueOf(dueEnabled) + " checked: " + String.valueOf(checkedBtn));



        if(checkedBtn == R.id.activeBtn && personalEnabled == true){
            Collections.sort(mainActivity.activePersonal,compareByPersonal);
            Log.d("activePersonal", "setRecyclerViewList: activePersonal" + mainActivity.activePersonal);
            return mainActivity.activePersonal;
        }
        else if(checkedBtn == R.id.activeBtn && dueEnabled == true){
            Collections.sort(mainActivity.activeDue,compareByDeadline);
            Log.d("activeDue", "setRecyclerViewList: activeDue" + mainActivity.activeDue);
            return mainActivity.activeDue;
        }
        else if (checkedBtn == R.id.expiredBtn && personalEnabled == true){
            Collections.sort(mainActivity.expiredPersonal,compareByPersonal);
            Log.d("expiredPersonal", "setRecyclerViewList: expieredPersonal" + mainActivity.expiredPersonal);
            return mainActivity.expiredPersonal;
        }
        else if(checkedBtn == R.id.expiredBtn && dueEnabled == true){
            Collections.sort(mainActivity.expiredDue,compareByDeadline);
            Log.d("expiredDue", "setRecyclerViewList: expiredDue" + mainActivity.expiredDue);
            return mainActivity.expiredDue;
        }
        else if (checkedBtn == R.id.finishedBtn && personalEnabled == true){
            Collections.sort(mainActivity.finishedPersonal,compareByPersonal);
            Log.d("finishedPersonal", "setRecyclerViewList: finishedPerosnal" + mainActivity.finishedPersonal);

            return mainActivity.finishedPersonal;
        }
        else if(checkedBtn == R.id.finishedBtn && dueEnabled == true){
            Collections.sort(mainActivity.finishedDue,compareByDeadline);
            Log.d("finishedDue", "setRecyclerViewList: finishedDue" + mainActivity.finishedDue);
            return mainActivity.finishedDue;
        }
        else
            return mainActivity.goalList;
    }


    public String setRecyclerViewList(MainActivity mainActivity){
        boolean personalEnabled = mainActivity.personalSelected;
        boolean dueEnabled = mainActivity.dueDateSelected;
        int checkedBtn = mainActivity.toggleGroup.getCheckedButtonId();
        Log.d("checkedToggle", "setRecyclerViewList: " + mainActivity.toggleGroup.getCheckedButtonId());
        Log.d("setRecyclerList", "setRecyclerViewList: " + String.valueOf(personalEnabled) + " due: " + String.valueOf(dueEnabled) + " checked: " + String.valueOf(checkedBtn));



        if(checkedBtn == R.id.activeBtn && personalEnabled == true){
            Collections.sort(mainActivity.activePersonal,compareByPersonal);
            Log.d("activePersonal", "setRecyclerViewList: activePersonal" + mainActivity.activePersonal);
            return "activePersonal";
        }
        else if(checkedBtn == R.id.activeBtn && dueEnabled == true){
            Collections.sort(mainActivity.activeDue,compareByDeadline);
            Log.d("activeDue", "setRecyclerViewList: activeDue" + mainActivity.activeDue);
            return "activeDue";
        }
        else if (checkedBtn == R.id.expiredBtn && personalEnabled == true){
            Collections.sort(mainActivity.expiredPersonal,compareByPersonal);
            Log.d("expiredPersonal", "setRecyclerViewList: expieredPersonal" + mainActivity.expiredPersonal);
            return "expiredPersonal";
        }
        else if(checkedBtn == R.id.expiredBtn && dueEnabled == true){
            Collections.sort(mainActivity.expiredDue,compareByDeadline);
            Log.d("expiredDue", "setRecyclerViewList: expiredDue" + mainActivity.expiredDue);
            return "expiredDue";
        }
        else if (checkedBtn == R.id.finishedBtn && personalEnabled == true){
            Collections.sort(mainActivity.finishedPersonal,compareByPersonal);
            Log.d("finishedPersonal", "setRecyclerViewList: finishedPerosnal" + mainActivity.finishedPersonal);

            return "finishedPersonal";
        }
        else if(checkedBtn == R.id.finishedBtn && dueEnabled == true){
            Collections.sort(mainActivity.finishedDue,compareByDeadline);
            Log.d("finishedDue", "setRecyclerViewList: finishedDue" + mainActivity.finishedDue);
            return "finishedDue";
        }
        else
            return "error";
    }



    public void removeGoalFromList(int goalSelected,ArrayList<GoalModel> arrayList){

        arrayList.remove(goalSelected);
    }

    public void findOldAndRemoveFromRecyclers(GoalModel updatedGoal){
        int index;
        Log.d("findOldandRemove", "started");

        Log.d("activePersonal", "activePersonal: "+mainActivity.activePersonal);
        Log.d("AD", "AD: "+mainActivity.activeDue );
        Log.d("eP", "EP: "+mainActivity.expiredPersonal );
        Log.d("ED", "ED: "+mainActivity.expiredDue );
        Log.d("FP", "FP: "+mainActivity.finishedPersonal );
        Log.d("FD", "FD: "+ mainActivity.finishedDue );

        if((index = searchArray(mainActivity.activeDue,updatedGoal)) != -1){
            removeGoalFromList(index, mainActivity.activeDue);
        }
        if((index = searchArray(mainActivity.activePersonal,updatedGoal))!=-1){
            removeGoalFromList(index, mainActivity.activePersonal);
        }
        if((index = searchArray(mainActivity.expiredDue,updatedGoal))!=-1){
            removeGoalFromList(index,mainActivity.expiredDue);
        }
        if((index = searchArray(mainActivity.expiredPersonal,updatedGoal))!=-1){
            removeGoalFromList(index,mainActivity.expiredPersonal);
        }

        if((index = searchArray(mainActivity.finishedDue,updatedGoal))!=-1){
            removeGoalFromList(index,mainActivity.finishedDue);


        }
        if((index = searchArray(mainActivity.finishedPersonal,updatedGoal))!=-1){
            removeGoalFromList(index,mainActivity.finishedPersonal);
        }
    }

    public int searchArray(ArrayList<GoalModel> goalList, GoalModel updatedGoal){
        int index = -1;

        for(int i = 0; i<goalList.size(); i++){
            Log.d("Search", "searchArray: " + goalList.get(i) + " " + updatedGoal);
            if(goalList.get(i).goalsEqual(updatedGoal) == true){
               Log.d("MATCHED", "searchArray: " + goalList.get(i) + " " + updatedGoal);
               index = i;
           }
        }
        return index;

    }

    /**
     * Used to get the correct counts of completed goals, group goals and individual goals when adding, compleiting and editing goals
     * @param activeDue
     * @param expiredDue
     * @param finishedDue
     * @return A single list of all of the goals
     */
    public ArrayList<GoalModel> combineArraysForCount(ArrayList<GoalModel> activeDue, ArrayList<GoalModel> expiredDue, ArrayList<GoalModel> finishedDue){
        ArrayList<GoalModel> countSubGoalList = new ArrayList<>();

        for (GoalModel value : activeDue) {
            countSubGoalList.add(value);
        }

        for (GoalModel value : expiredDue) {
            countSubGoalList.add(value);
        }

        for (GoalModel value : finishedDue) {
            countSubGoalList.add(value);
        }
        Log.d("countSubGoalList", "combineArraysForCount: " + countSubGoalList);
        return countSubGoalList;
    }



}
