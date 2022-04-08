package com.lejiebennett.proccoli2.ui.home.groupGoalWall.groupGoalSubGoalCreation;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Copyright © 2022 Le Jie Bennett. All rights reserved.
 * Needs to be integrated with firebase, this version of the file is prior to firebase integration
 * Controller for groupgoalsubGoalCreation
 */
public class groupgoalsubGoalCreation_Controller extends AppCompatActivity {

    private groupgoalsubGoalView groupgoalsubGoalView;

    public groupgoalsubGoalCreation_Controller(groupgoalsubGoalView groupgoalsubGoalView){
        this.groupgoalsubGoalView = groupgoalsubGoalView;
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

            textView.setTextColor(Color.RED);

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
     * @param complete
     * @param context
     * @param goalComplete
     * @return if the dates are not logically correct return false, if they are return true
     * @throws ParseException
     */
    public boolean compareDates(String complete, Context context,String goalComplete) throws ParseException {
        Log.d("Date", "compareDates: " + complete.toString());

        //Sun Oct 17 10:55:00 EDT 2021
      //  SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("MMM, dd, yyyy --hh:mm aa");


        Date today = new Date(System.currentTimeMillis());
        Log.d("Today's Date and time", "compareDates: " + today.toString());


        if(today.after(sdf.parse(complete)) || today.after(sdf.parse(goalComplete)))
        {
            Log.d("Compare FAiled", "compareDates: else RAN HERE");
            return false;
        }
        if(sdf.parse(goalComplete).after(sdf.parse(complete)))
            return true;

        else{
            Log.d("Compare FAiled", "compareDates: else RAN");
            return false;
        }

    }


    /**
     * Converts Date String to Unix in seconds
     * //https://stackoverflow.com/questions/7784421/getting-unix-timestamp-from-date
     * @param time
     * @return
     */    public int dateStrToUnix(String time) {
        long unixTime = 0;
    //    SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");//Specify your timezone
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
     * Converts unix to String Date Time
     * @param unix
     * @return
     */
    public String unixToStringDateTime(int unix){
        Date date = new Date(unix*1000L);
        //  SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");//Specify your timezone
        SimpleDateFormat sdf = new SimpleDateFormat("MMM, dd, yyyy --hh:mm aa");
        return sdf.format(date);

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



}
