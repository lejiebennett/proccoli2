package com.lejiebennett.proccoli2.ui.home.individualWall;

import static com.lejiebennett.proccoli2.NewModels.SingletonStrings.COMPLETE_GOAL_BTN_TAPPED_REF;
import static com.lejiebennett.proccoli2.NewModels.SingletonStrings.SET_REMINDER_BTN_TAPPED_REF;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.lejiebennett.proccoli2.NewModels.DataServices;
import com.lejiebennett.proccoli2.NewModels.IndividualGoalModel;
import com.lejiebennett.proccoli2.NewModels.IndividualSubGoalStructModel;
import com.lejiebennett.proccoli2.NewModels.LogActivityModel;
import com.lejiebennett.proccoli2.NewModels.ProgressViewIndividualWallModel;
import com.lejiebennett.proccoli2.NewModels.ResultHandler;
import com.lejiebennett.proccoli2.NewModels.SingletonStrings;
import com.lejiebennett.proccoli2.NewModels.UserDataModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

/**
 * Copyright © 2022 Le Jie Bennett. All rights reserved.
 * Controller for singleGoalView
 */
public class singleGoalView_Controller extends AppCompatActivity {

    private final singleGoalView singleGoalView;
    public singleGoalView_Controller(singleGoalView singleGoalView){
        this.singleGoalView = singleGoalView;
    }

    /**
     * Converts unix to Date String
     * @param unix
     * @return unix as Date String
     */
    public String unixToString(int unix){
        Date date = new java.util.Date(unix*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMM, dd, yyyy");
        return sdf.format(date);

    }

    /**
     * Converts the unix formatted date to a String version in the following format: "MMM, dd, yyyy --hh:mm aa"
     * @param unix Unix time to convert
     * @return Converted String
     */
    public String unixToStringDateTime(int unix){
        Date date = new java.util.Date(unix*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMM, dd, yyyy --\nhh:mm aa");
        return sdf.format(date);

    }

    /**
     * Calculates the amount of time passed from the passed unix to the current time
     * @param unix
     * @return The amount of time passed
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String calculateMinutesAgo(int unix){
        long today = System.currentTimeMillis()/1000L;

        Log.d("calculateMinutes", "calculateMinutesAgo: " + today + " - " + unix);
        int difference = (int)today - unix;
        if ((difference/3600)<1)
            return "\t"+ difference / 60 + " m ago";
        else{
            int hours = 0;
            while(difference >3600){
                hours++;
                difference = difference-3600;
            }
            return "\t" + hours + "h " + (difference/60) + " m ago";
        }
    }

    public int dateStrToUnix(String time) {
        long unixTime = 0;
        //SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");//Specify your timezone
        SimpleDateFormat sf = new SimpleDateFormat("MMM, dd, yyyy --hh:mm aa");

        try {
            unixTime = sf.parse(time).getTime();
            unixTime = unixTime / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (int)unixTime;
    }

    public void completeBtnTapped(IndividualGoalModel goalModel, ResultHandler<Object> handler) {
        if(goalModel==null){
            return ;
        }
        if(goalModel.getSubGoals().size()!=0){
            //chech if there is any uncheck sub goal
            int uncheck = countIncompleteSubGoals(goalModel.getSubGoals());
            Log.d("completeBtnTapped", "completeBtnTapped: " + goalModel.getSubGoals());
            Log.d("completeBtnTapped", "completeBtnTapped: " + uncheck);
            if( uncheck > 0){
                //send alert here
                AlertDialog.Builder builder1 = new AlertDialog.Builder(singleGoalView);
                builder1.setTitle("Warning!");
                builder1.setMessage("You have " + uncheck + "  incomplete subgoals!\nDo you still want to complete the goal?");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                proccedGoalCompletion(goalModel);
                                evaluationCheckerForCompletion(goalModel);
                                HashMap<String,Object> hashMap = new HashMap<>();
                                hashMap.put("foundSubgoals",true);
                                hashMap.put("proceedToComplete", true);
                                handler.onSuccess(hashMap);

                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                HashMap<String,Object> hashMap = new HashMap<>();
                                hashMap.put("foundSubgoals",true);
                                hashMap.put("proceedToComplete", false);
                                handler.onSuccess(hashMap);
                                }
                            });

                AlertDialog alert11 = builder1.create();
                alert11.show();
                return;
            }
			else {
                //procceed the completion
                //all sub goals are checked
                proccedGoalCompletion(goalModel);
                evaluationCheckerForCompletion(goalModel);
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("foundSubgoals",true);
                hashMap.put("proceedToComplete", true);
                handler.onSuccess(hashMap);
            }
        }else {
            //there is no sub goal
            //procceed the completion
            proccedGoalCompletion(goalModel);
            evaluationCheckerForCompletion(goalModel);
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("foundSubgoals",false);
            hashMap.put("proceedToComplete", true);
            handler.onSuccess(hashMap);
        }

    }

    Comparator<IndividualSubGoalStructModel> compareBysubGoalId = new Comparator<IndividualSubGoalStructModel>() {
        @Override
        public int compare(IndividualSubGoalStructModel goal1 ,IndividualSubGoalStructModel goal2) {
            return goal1.get_subGoalId().compareTo(goal2.get_subGoalId());
        }
    };

    /**
     * This method needs work since it is connected to chart data for the individual progress chart
     * @param goal
     */
    public void evaluationCheckerForCompletion(IndividualGoalModel goal) {
        String mainGoalId = goal.getGoalId();
        String goalName = goal.getBigGoal();
        ArrayList<String> subgoalIds = new ArrayList<>();
        subgoalIds.add(SingletonStrings.NO_SUB_GOAL_REF);
        if(goal.getSubGoals().size()>0){
            ArrayList<IndividualSubGoalStructModel> sortedSubIds = goal.getSubGoals();
            Collections.sort(sortedSubIds,compareBysubGoalId);
            for(IndividualSubGoalStructModel subId: sortedSubIds){
                subgoalIds.add(subId.get_subGoalId());
                if(sortedSubIds.size()+1 == subgoalIds.size()){
                    //make one more sort because of no sub
                    ArrayList<String> newSortedIds = subgoalIds;
                    Collections.sort(newSortedIds);
                  //  letUsKnowChartDataDownloaded(goalName, mainGoalId, newSortedIds);
                }
            }
        }
        else{
           // self.letUsKnowChartDataDownloaded(goalName: goalName, goalId: mainGoalId, subGoalIds:subGoalIds)

        }

        /*
        guard let mainGoalId = self.data?.goalId,
                let goalName = self.data?.bigGoal  else {return}
        var subGoalIds = [NO_SUB_GOAL_REF]
        if self.data?.subGoals?.count ?? 0 > 0 {
            if let sortedSubIds = self.data?.subGoals?.sorted(by: {$0._subGoalId < $1._subGoalId}){
                for subId in sortedSubIds {
                    subGoalIds.append(subId._subGoalId)
                    if sortedSubIds.count + 1 == subGoalIds.count{
                        //make one more sort because of no sub
                        let newSortedIds = subGoalIds.sorted(by: {$0 < $1})
                        self.letUsKnowChartDataDownloaded(goalName: goalName, goalId: mainGoalId, subGoalIds: newSortedIds)
                    }
                }
            }
        }
			else {
            self.letUsKnowChartDataDownloaded(goalName: goalName, goalId: mainGoalId, subGoalIds:subGoalIds)
        }

         */
    }

    /**
     * Marks the goal complete
     * @param newData
     */
    public void proccedGoalCompletion(IndividualGoalModel newData) {
        //procceed the completion
        newData.setCompleted(true);
        singleGoalView.myGoal.setCompleted(true);
        //send data server here
        DataServices.completeGoal(newData.getGoalId());
        //update local data on Profile VC
        UserDataModel.sharedInstance.setCompletedGoalTotal((int)getValueOrDefault(UserDataModel.sharedInstance.getCompletedGoalTotal(),0)+1);
        //let profile VC data need to refresh
        /*
        if let index = UserDataModel.sharedInstance.rawGoalsData?.firstIndex(where: {$0.goalId == newData.goalId}) {
            UserDataModel.sharedInstance.rawGoalsData?[index].isCompleted = true
            UserDataModel.sharedInstance.rawGoalsData = UserDataModel.sharedInstance.rawGoalsData
        }


         */

        //activity log activity
        LogActivityModel.getActivityChain().addActivityForGoal(COMPLETE_GOAL_BTN_TAPPED_REF, newData.getGoalId());
        //end log activiy


    }


    /**
     * Counts the number of uncompleted subgoals
     * @param subgoals
     * @return # of incomplete subgoals
     */
    public int countIncompleteSubGoals(ArrayList<IndividualSubGoalStructModel> subgoals){
        Log.d("subgoalsToCheckComplete", "countCompletedSubGoals: " + subgoals);
        int counter = 0;
        for(int i = 0; i<subgoals.size();i++) {
            if (subgoals.get(i).is_isChecked() == false)
                counter++;
        }
        return counter;
    }


    public static <T> T getValueOrDefault(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }

    public String getAlphaNumericString(int n)
    {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

    public boolean setReminder(IndividualGoalModel goal,long date) {
        Log.d("setReminderPersonal", "setReminder: " + goal.getPersonalDeadline());
        Log.d("setReminderDate", "setReminder: " + date);

        boolean setReminder = false;
        //set local notification
        //guard let data = self.data else {return}
        long reminderDate = date;
        //reminder date sanity check
        long current = System.currentTimeMillis()/100L;
        if(reminderDate <= current){
            Toast.makeText(singleGoalView, "You cannot set expired reminder date!", Toast.LENGTH_LONG).show();
        }
		else if(reminderDate <goal.getPersonalDeadline()) {
            Toast.makeText(singleGoalView, "Your reminder date exceeds the proposed deadline.", Toast.LENGTH_LONG).show();
		}
		else {
           // setLocalNotification(goal.getBigGoal(), goal.getGoalId(), date, reminderData);
            setReminder=true;
        }

        //activity log activity

        LogActivityModel.getActivityChain().addActivityForGoal(SET_REMINDER_BTN_TAPPED_REF, goal.getGoalId());
        //end log activiy


        Log.d("singleGoalSetReminder", "setReminder: " + setReminder);
        return setReminder;
    }

    /**
     * This method needs work since it is connected to chart data for the individual progress chart
     * @param goalId
     * @param subGoalIds
     */
    public void createProgressModelandInstantiaveNewVC(String goalId, ArrayList<String> subGoalIds) {
        Log.d("createProgressModel", "createProgressModelandInstantiaveNewVC: createProgressModelandInstantiaveNewVC");

        /*
        let model = ProgressViewIndividualWallModel.init(goalId: goalId, subGoalIds:subGoalIds)
        model.callDetailedStudyData(isItForGoalCompletion: false) { (status) in
            if status {
                self.instantiateNewVC(destinationIdetifier: "individualWallProgress")
            }
        }

         */
    }

    /**
     * This method needs work since it is connected to chart data for the individual progress chart
     * @param goalName
     * @param goalId
     * @param subGoalIds
     * @param goalModel
     */
    public void letUsKnowChartDataDownloaded(String goalName, String goalId, ArrayList<String> subGoalIds,IndividualGoalModel goalModel) {
        Log.d("letUsKnow", "letUsKnowChartDataDownloaded: " + "letUsKnowChartDataDownloaded");

        ProgressViewIndividualWallModel model = new ProgressViewIndividualWallModel(goalId, subGoalIds,goalModel);
        model.callDetailedStudyData(true, new ResultHandler<Object>() {
            @Override
            public void onSuccess(Object data) {
                HashMap<String,Object> hashMap = (HashMap<String, Object>) data;
                if((boolean)hashMap.get("_status") == true){
                    Log.d("letUsKnowChart", "onSuccess: send double alert Question");
                    /*
                    if (ProgressViewIndividualWallModel.sharedInstance?.xMinFirstStudyTime ?? 0) > (ProgressViewIndividualWallModel.sharedInstance?.xMinProposedStarDate ?? 0) {

                        //late start
                        //send double alert question
                        let later = ((ProgressViewIndividualWallModel.sharedInstance?.xMinFirstStudyTime ?? 0) - (ProgressViewIndividualWallModel.sharedInstance?.xMinProposedStarDate ?? 0)) / 86400
                        let questionView = DoubleAlertQuestionView(goalName: goalName, goalId:goalId, later: later, delegate: self)
                        DispatchQueue.main.async {
                            self.view.addSubview(questionView.prepareForView())
                        }
                    }
                    else{
                        Log.d("letUsKnowChart", "onSuccess: send double alert Question");

                        //regular or early start
                        //send only single alert questions
                        let questionView = SingleAlertTypeQuestionView(questions: alertTypeQuestions(type: FACE_QUESTION_TYPE_REF_FOR_COMPLETION, question: "How well do you think you did on the goal you just completed?", goalId: goalId, selectedSubGoalId: "", studyId: ""), result: questionResult(questionId: "", result: nil, isMandatory: true), isGroupStudy: false)
                        DispatchQueue.main.async {
                            self.view.addSubview(questionView.prepareForView())
                        }
                    }

                     */
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

}
