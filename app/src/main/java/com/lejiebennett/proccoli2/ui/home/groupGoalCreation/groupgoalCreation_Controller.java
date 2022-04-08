package com.lejiebennett.proccoli2.ui.home.groupGoalCreation;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lejiebennett.proccoli2.NewModels.DataServices;
import com.lejiebennett.proccoli2.NewModels.GoalModel;
import com.lejiebennett.proccoli2.NewModels.GroupGoalModel;
import com.lejiebennett.proccoli2.NewModels.LogActivityModel;
import com.lejiebennett.proccoli2.NewModels.ResultHandler;
import com.lejiebennett.proccoli2.NewModels.SingletonStrings;
import com.lejiebennett.proccoli2.NewModels.UserDataModel;
import com.lejiebennett.proccoli2.NewModels.groupGoalForPersonalCollection;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class groupgoalCreation_Controller extends AppCompatActivity {
    private com.lejiebennett.proccoli2.ui.home.groupGoalCreation.groupgoalView groupgoalView;

    public groupgoalCreation_Controller(groupgoalView groupgoalView){
        this.groupgoalView = groupgoalView;
    }

    /**
     * Checks to see if input is null or not, if it is null, turn the textfield hint color red
     * and show the error message
     * @param input item to see if null
     * @param errorMessage message to pass if null
     * @param context
     * @param textView view to change hint color
     * @return false if the field is null, true if it passes the check
     */
    public boolean nullFieldCheck(String input, String errorMessage, Context context, TextView textView) {
        if (input.length() == 0) {
            CharSequence text = errorMessage;
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            textView.setHintTextColor(Color.RED);
            return false;
        }
        return true;
    }


    /**
     * Checks to see if input is null or not, if it is null, turn the textview hint color red
     * and show error message
     * @param input item to see if null
     * @param errorMessage message to pass if null
     * @param context
     * @param textView view to change hint color
     * @return false if the field is null, true if it passes the check
     */
    public boolean nullFieldCheck(String input, String errorMessage, Context context, TextInputEditText textView) {
        if (input.length() == 0) {
            CharSequence text = errorMessage;
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            textView.setHintTextColor(Color.RED);
            return false;
        }
        return true;
    }

    /**
     * Verifies that the dates are logically correct
     * @param due
     * @param context
     * @param groupgoalView
     * @return
     * @throws ParseException
     */
    public boolean compareDates(String due, Context context,groupgoalView groupgoalView) throws ParseException {
        Log.d("Date", "compareDates: " + due.toString());

        //Sun Oct 17 10:55:00 EDT 2021
      //  SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("MMM, dd, yyyy --hh:mm aa");


        Date today = new Date(System.currentTimeMillis());
        Log.d("Today's Date and time", "compareDates: " + today.toString());


        if(today.after(sdf.parse(due)))
        {
            Log.d("Compare Failed", "compareDates: else RAN");
            return false;
        }
        else
            return true;

    }

    /**
     * Converts Date String to Unix in seconds
     * //https://stackoverflow.com/questions/7784421/getting-unix-timestamp-from-date
     * @param time
     * @return
     */
    public int dateStrToUnix(String time) {
        long unixTime = 0;
        SimpleDateFormat sf = new SimpleDateFormat("MMM, dd, yyyy --hh:mm aa");

        try {
            unixTime = sf.parse(time).getTime();
            unixTime = unixTime / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (int)unixTime;
    }

    /**
     * Converts Date to String
     * @param date
     * @return
     */
    public String dateToStr(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("MMM, dd, yyyy --hh:mm aa");
        return formatter.format(date);
    }

    /**
     * Converts unix to String Date Time
     * @param unix
     * @return
     */
    public String unixToStringDateTime(int unix){
        Date date = new Date(unix*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM, dd, yyyy --hh:mm aa");
        return sdf.format(date);
    }

    public GroupGoalModel saveGroupGoal(GroupGoalModel data) {
        GroupGoalModel returnData = DataServices.getInstance().createGroupGoal(data.getBigGoal(), data.getGoalType(), data.isGoalCompleted(), data.getTaskType(), data.getGoalCreaterUid(), data.getGroupMembers(), data.getRelatedCourse(), data.getWhenIsItDue(), data.getCreatedAt(), new ResultHandler<Object>() {
            @Override
            public void onSuccess(Object data) {
                HashMap<String,Object> hashMap = (HashMap<String, Object>) data;
                if(hashMap.get("_response")!=null){
                    GroupGoalModel newGroupGoal = (GroupGoalModel) hashMap.get("_response");

                    //self.removeCreateGoalVC()
                    //self.gotoIndividualVCAfterIndividualGoalCreation(data: returnData)
                    //PersonalNoteViews.sharedInstance.removeExistingDataForNewEventCreatation(goalId: returnData.goalId)
                    //update userdata model here
                    UserDataModel.sharedInstance.setGroupGoalTotal((int)getValueOrDefault(UserDataModel.sharedInstance.getGroupGoalTotal(),0) + 1);

                    groupGoalForPersonalCollection groupGoalForPersonal = new groupGoalForPersonalCollection(newGroupGoal.getGoalId(), newGroupGoal.getBigGoal(), newGroupGoal.getGoalType(), newGroupGoal.isGoalCompleted(), newGroupGoal.getTaskType(), newGroupGoal.getGoalCreaterUid(), newGroupGoal.getWhenIsItDue(), newGroupGoal.getCreatedAt(), 0, 0, newGroupGoal.getWhenIsItDue());
                    GoalModel newGoal = groupGoalForPersonalCollection.goalsModelConverterForDataWrite(groupGoalForPersonal);

                    ArrayList<GoalModel> newGoals = new ArrayList<>();
                    newGoals.add(newGoal);
                    UserDataModel.sharedInstance.setRawGoalsData(newGoals);
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
            if(returnData!=null){
                //log activity
                LogActivityModel.getActivityChain().addActivityForGoal(SingletonStrings.GROUP_GOAL_CREATE_REF, returnData.getGoalId());
                return returnData;
            }
            return null;

        }

    public static <T> T getValueOrDefault(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }


}
