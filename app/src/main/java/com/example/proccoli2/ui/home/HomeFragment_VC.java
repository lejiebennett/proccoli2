package com.example.proccoli2.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.proccoli2.MainActivity;
import com.example.proccoli2.NewModels.GoalModel;
import com.example.proccoli2.NewModels.IndividualGoalModel;
import com.example.proccoli2.R;
import com.example.proccoli2.ui.notifications.NotificationsFragment;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HomeFragment_VC {
    private HomeFragment homeFragment;

    public HomeFragment_VC(HomeFragment homeFragment){
        this.homeFragment = homeFragment;
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
            if (goalList.get(i).isCompleted() == true)
                counter++;
        }
        return counter;
    }

    public void assignGoal(GoalModel goal){
        long todayUnixDateTime = System.currentTimeMillis() / 1000L;
        int goalPersonal = (int) goal.getPersonalDeadline();
        int goalDue = (int)goal.getWhenIsDue();
        boolean goalComplete =goal.isCompleted();

        Log.d("todayCurrently", "assignGoal: UNIX TIME CURRENT" + todayUnixDateTime);
        Log.d("CompleteBy", "assignGoal: " + goal.isCompleted());


        if((int)todayUnixDateTime-goalPersonal < 0 && goalComplete == false){
            homeFragment.activePersonal.add(goal);
            Log.d("AP", "assignGoal: adding to activePersonal" + homeFragment.activePersonal);
        }

        if((int)todayUnixDateTime-goalDue < 0 && goalComplete == false){
            homeFragment.activeDue.add(goal);
            Log.d("Ad", "assignGoal: adding to activeDue" + homeFragment.activeDue);

        }

        if((int)todayUnixDateTime-goalPersonal<0 && goalComplete==true){
            homeFragment.finishedPersonal.add(goal);
            Log.d("FP", "assignGoal: adding to fisnihPersonal" + homeFragment.finishedPersonal);

        }

        if((int)todayUnixDateTime-goalDue<0 && goalComplete==true){
            homeFragment.finishedDue.add(goal);
            Log.d("FD", "assignGoal: adding to finishDue" + homeFragment.finishedDue);

        }

        if((int)todayUnixDateTime-goalPersonal>0 && goalComplete==true){
            homeFragment.finishedPersonal.add(goal);
            Log.d("FP", "assignGoal: adding to fisnihPersonal" + homeFragment.finishedPersonal);

        }

        if((int)todayUnixDateTime-goalDue>0 && goalComplete==true){
            homeFragment.finishedDue.add(goal);
            Log.d("FD", "assignGoal: adding to finishDue" + homeFragment.finishedDue);

        }

        if((int)todayUnixDateTime-goalDue>0 && goalComplete == false){
            homeFragment.expiredDue.add(goal);
            Log.d("ED", "assignGoal: adding to expiredGoal" + homeFragment.expiredDue);

        }
        if((int)todayUnixDateTime-goalPersonal>0 && goalComplete==false){
            homeFragment.expiredPersonal.add(goal);
            Log.d("EP", "assignGoal: adding to experiedPersonal" + homeFragment.expiredPersonal);

        }
    }


    Comparator<GoalModel> compareByDeadline = new Comparator<GoalModel>() {
        @Override
        public int compare(GoalModel goal1, GoalModel goal2) {
            return Long.compare(goal1.getWhenIsDue(),goal2.getWhenIsDue());
        }
    };

    Comparator<GoalModel> compareByPersonal = new Comparator<GoalModel>() {
        @Override
        public int compare(GoalModel goal1, GoalModel goal2) {
            return Long.compare(goal1.getPersonalDeadline(),goal2.getPersonalDeadline());
        }
    };

    public ArrayList<GoalModel> setRecyclerViewList2(HomeFragment homeFragment){
        boolean personalEnabled = homeFragment.personalSelected;
        boolean dueEnabled = homeFragment.dueDateSelected;
        int checkedBtn = homeFragment.toggleGroup.getCheckedButtonId();
        Log.d("setRecyclerList", "setRecyclerViewList: " + String.valueOf(personalEnabled) + " due: " + String.valueOf(dueEnabled) + " checked: " + String.valueOf(checkedBtn));



        if(checkedBtn == R.id.activeBtn && personalEnabled == true){
            Collections.sort(homeFragment.activePersonal,compareByPersonal);
            Log.d("activePersonal", "setRecyclerViewList: activePersonal" + homeFragment.activePersonal);
            return homeFragment.activePersonal;
        }
        else if(checkedBtn == R.id.activeBtn && dueEnabled == true){
            Collections.sort(homeFragment.activeDue,compareByDeadline);
            Log.d("activeDue", "setRecyclerViewList: activeDue" + homeFragment.activeDue);
            return homeFragment.activeDue;
        }
        else if (checkedBtn == R.id.expiredBtn && personalEnabled == true){
            Collections.sort(homeFragment.expiredPersonal,compareByPersonal);
            Log.d("expiredPersonal", "setRecyclerViewList: expieredPersonal" + homeFragment.expiredPersonal);
            return homeFragment.expiredPersonal;
        }
        else if(checkedBtn == R.id.expiredBtn && dueEnabled == true){
            Collections.sort(homeFragment.expiredDue,compareByDeadline);
            Log.d("expiredDue", "setRecyclerViewList: expiredDue" + homeFragment.expiredDue);
            return homeFragment.expiredDue;
        }
        else if (checkedBtn == R.id.finishedBtn && personalEnabled == true){
            Collections.sort(homeFragment.finishedPersonal,compareByPersonal);
            Log.d("finishedPersonal", "setRecyclerViewList: finishedPerosnal" + homeFragment.finishedPersonal);

            return homeFragment.finishedPersonal;
        }
        else if(checkedBtn == R.id.finishedBtn && dueEnabled == true){
            Collections.sort(homeFragment.finishedDue,compareByDeadline);
            Log.d("finishedDue", "setRecyclerViewList: finishedDue" + homeFragment.finishedDue);
            return homeFragment.finishedDue;
        }
        else
            return homeFragment.goalList;
    }


    public String setRecyclerViewList(HomeFragment homeFragment){
        boolean personalEnabled = homeFragment.personalSelected;
        boolean dueEnabled = homeFragment.dueDateSelected;
        int checkedBtn = homeFragment.toggleGroup.getCheckedButtonId();
        Log.d("checkedToggle", "setRecyclerViewList: " + homeFragment.toggleGroup.getCheckedButtonId());
        Log.d("setRecyclerList", "setRecyclerViewList: " + String.valueOf(personalEnabled) + " due: " + String.valueOf(dueEnabled) + " checked: " + String.valueOf(checkedBtn));



        if(checkedBtn == R.id.activeBtn && personalEnabled == true){
            Collections.sort(homeFragment.activePersonal,compareByPersonal);
            Log.d("activePersonal", "setRecyclerViewList: activePersonal" + homeFragment.activePersonal);
            return "activePersonal";
        }
        else if(checkedBtn == R.id.activeBtn && dueEnabled == true){
            Collections.sort(homeFragment.activeDue,compareByDeadline);
            Log.d("activeDue", "setRecyclerViewList: activeDue" + homeFragment.activeDue);
            return "activeDue";
        }
        else if (checkedBtn == R.id.expiredBtn && personalEnabled == true){
            Collections.sort(homeFragment.expiredPersonal,compareByPersonal);
            Log.d("expiredPersonal", "setRecyclerViewList: expieredPersonal" + homeFragment.expiredPersonal);
            return "expiredPersonal";
        }
        else if(checkedBtn == R.id.expiredBtn && dueEnabled == true){
            Collections.sort(homeFragment.expiredDue,compareByDeadline);
            Log.d("expiredDue", "setRecyclerViewList: expiredDue" + homeFragment.expiredDue);
            return "expiredDue";
        }
        else if (checkedBtn == R.id.finishedBtn && personalEnabled == true){
            Collections.sort(homeFragment.finishedPersonal,compareByPersonal);
            Log.d("finishedPersonal", "setRecyclerViewList: finishedPerosnal" + homeFragment.finishedPersonal);

            return "finishedPersonal";
        }
        else if(checkedBtn == R.id.finishedBtn && dueEnabled == true){
            Collections.sort(homeFragment.finishedDue,compareByDeadline);
            Log.d("finishedDue", "setRecyclerViewList: finishedDue" + homeFragment.finishedDue);
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

        Log.d("activePersonal", "activePersonal: "+homeFragment.activePersonal);
        Log.d("AD", "AD: "+homeFragment.activeDue );
        Log.d("eP", "EP: "+homeFragment.expiredPersonal );
        Log.d("ED", "ED: "+homeFragment.expiredDue );
        Log.d("FP", "FP: "+homeFragment.finishedPersonal );
        Log.d("FD", "FD: "+ homeFragment.finishedDue );

        if((index = searchArray(homeFragment.activeDue,updatedGoal)) != -1){
            removeGoalFromList(index, homeFragment.activeDue);
        }
        if((index = searchArray(homeFragment.activePersonal,updatedGoal))!=-1){
            removeGoalFromList(index, homeFragment.activePersonal);
        }
        if((index = searchArray(homeFragment.expiredDue,updatedGoal))!=-1){
            removeGoalFromList(index,homeFragment.expiredDue);
        }
        if((index = searchArray(homeFragment.expiredPersonal,updatedGoal))!=-1){
            removeGoalFromList(index,homeFragment.expiredPersonal);
        }

        if((index = searchArray(homeFragment.finishedDue,updatedGoal))!=-1){
            removeGoalFromList(index,homeFragment.finishedDue);


        }
        if((index = searchArray(homeFragment.finishedPersonal,updatedGoal))!=-1){
            removeGoalFromList(index,homeFragment.finishedPersonal);
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

    public GoalModel convertIndividualToGoalModel(IndividualGoalModel individual){
         return new GoalModel(individual.getBigGoal(),individual.getPersonalDeadline(),individual.getTaskType(),null,individual.getCreatedAt(),individual.getGoalCreaterEmail(),individual.isCompleted(),individual.getGoalType(),individual.getWhenIsDue(),null,individual.getProposedStudyTime(),0,false);
    }


}
