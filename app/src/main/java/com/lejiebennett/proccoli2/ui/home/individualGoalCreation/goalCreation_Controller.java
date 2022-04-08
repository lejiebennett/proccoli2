package com.lejiebennett.proccoli2.ui.home.individualGoalCreation;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lejiebennett.proccoli2.NewModels.DataServices;
import com.lejiebennett.proccoli2.NewModels.GoalModel;
import com.lejiebennett.proccoli2.NewModels.IndividualGoalModel;
import com.lejiebennett.proccoli2.NewModels.LogActivityModel;
import com.lejiebennett.proccoli2.NewModels.SingletonStrings;
import com.lejiebennett.proccoli2.NewModels.UserDataModel;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Copyright © 2022 Le Jie Bennett. All rights reserved.
 * Controller for the creation of individual goals
 */

public class goalCreation_Controller extends AppCompatActivity {
    private com.lejiebennett.proccoli2.ui.home.individualGoalCreation.goalView2 goalView2;

    public goalCreation_Controller(goalView2 goalView2){
        this.goalView2= goalView2;
    }

    /**
     * Used to verify that field is not empty/null
     * @param input The textview that acts as the input
     * @param errorMessage The message to show as the toast
     * @param context
     * @param textView The textview to change the color if error
     * @return True if valid, else false
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
     * Used to verify that field is not empty/null
     * @param input TextEditView that acts as the input
     * @param errorMessage The error message to show in the toast
     * @param context
     * @param textView The text view to change color if errored
     * @return True if valid, else false
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
     * Verifies that the date logic is followed and that all the dates are valid
     * @param start Start Goal Date
     * @param complete Complete By/Personal Deadline date
     * @param due Due date
     * @param context
     * @param goalView2 The view that we are working with (Variable not actually used)
     * @return True if the dates are valid, else false
     * @throws ParseException If the dates could not be parsed correctly
     */
    public boolean compareDates(String start, String complete, String due, Context context,goalView2 goalView2) throws ParseException {
        Log.d("Date", "compareDates: " + start.toString() + complete.toString() + due.toString());

        //Sun Oct 17 10:55:00 EDT 2021
      //  SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("MMM, dd, yyyy --hh:mm aa");


        Date today = new Date(System.currentTimeMillis());
        Log.d("Today's Date and time", "compareDates: " + today.toString());


        //Verifies that all the dates are after the current time and day
        if(today.after(sdf.parse(start)) || today.after(sdf.parse(complete))
        || today.after(sdf.parse(due)))
        {
            Log.d("Compare Failed", "compareDates: else RAN");
            return false;
        }


        //Checks the rest of the date logic
        if(sdf.parse(start).before(sdf.parse(complete)) &&
                sdf.parse(start).before(sdf.parse(due)) &&
                sdf.parse(complete).before(sdf.parse(due)) &&
                sdf.parse(complete).after(sdf.parse(start)) &&
                sdf.parse(due).after(sdf.parse(complete))&&
                sdf.parse(due).after(sdf.parse(start)))
            return true;

        else{
            Log.d("Compare Failed", "compareDates: else RAN");
            return false;
        }

    }

    /**
     * Converts the String version of the date to Unix time so it can be saved into the goal model
     * https://stackoverflow.com/questions/7784421/getting-unix-timestamp-from-date
     * @param time String to convert to unix time, Must be in "MMM, dd, yyyy --hh:mm aa" format
     * @return Converted Unix time
     */
    public int dateStrToUnix(String time) {
        long unixTime = 0;
      //  SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");//Specify your timezone
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
     * Converts the date from the date picker to the formatted String "MMM, dd, yyyy -hh:mm aa"
     * @param date Date from picked
     * @return Converted String
     */
    public String dateToStr(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("MMM, dd, yyyy --hh:mm aa");
        return formatter.format(date);
    }

    /**
     * Convers the unix formatted date to a String version in the following format: "MMM, dd, yyyy --hh:mm aa"
     * @param unix Unix time to convert
     * @return Converted String
     */
    public String unixToStringDateTime(int unix){
        Date date = new java.util.Date(unix*1000L);
        //  SimpleDateFormat sdf = new java.text.SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("MMM, dd, yyyy --hh:mm aa");
        return sdf.format(date);

    }

    /**
     * This exacts the planned/proposed study time from the picker, assuming hours as the unit.
     * Would need to multiply to make it minutes or unix seconds
     * @param proposedTime Time selected to be the planned study time from the picker
     * @return The time extracted as a double
     */
    public double strToDoubleProposedTime(String proposedTime){
        String[] substring = proposedTime.split(" ");
        double time = Double.parseDouble(substring[0]);
        Log.d("plannedStudyTime", "strToDoubleProposedTime: " + time);
        return time;
    }

    public IndividualGoalModel saveIndividualGoal(IndividualGoalModel data) {
        IndividualGoalModel returnData = DataServices.getInstance().saveIndividualGoal(data);
        //self.removeCreateGoalVC()
        //self.gotoIndividualVCAfterIndividualGoalCreation(data: returnData)
        //PersonalNoteViews.sharedInstance.removeExistingDataForNewEventCreatation(goalId: returnData.goalId)
        //update userdata model here
        UserDataModel.sharedInstance.setIndividualGoalTotal((int)getValueOrDefault(UserDataModel.sharedInstance.getIndividualGoalTotal(),0) + 1);
        GoalModel newGoal = IndividualGoalModel.goalsModelConverterForDataWrite(data);
        if(UserDataModel.sharedInstance.getRawGoalsData() != null) {
            UserDataModel.sharedInstance.getRawGoalsData().add(newGoal);
        }else {
            ArrayList<GoalModel> newGoals = new ArrayList<>();
            newGoals.add(newGoal);
            UserDataModel.sharedInstance.setRawGoalsData(newGoals);
        }


        //log activity
        LogActivityModel.getActivityChain().addActivityForGoal(SingletonStrings.INDIVIDUAL_GOAL_CREATE_REF,returnData.getGoalId());
        return returnData;
    }

    public void individualDataSend(IndividualGoalModel data) {
        //save individual goal data here
        saveIndividualGoal(data);

    }


    public static <T> T getValueOrDefault(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }

}
