package com.example.proccoli2.ui.home.groupGoalWall;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Copyright © 2022 Le Jie Bennett. All rights reserved.
 * Needs to be integrated with firebase, this version of the file is prior to firebase integration
 * Controller for GroupGoalSingleGoalView
 */
public class groupgoalsingleGoalView_Controller extends AppCompatActivity {

    private groupgoalsingleGoalView groupgoalsingleGoalView;
    public groupgoalsingleGoalView_Controller(groupgoalsingleGoalView singleGoalView){
        this.groupgoalsingleGoalView = groupgoalsingleGoalView;
    }

    /**
     * Converts unix to String Date
     * @param unix
     * @return unix as String Date
     */
    public String unixToString(int unix){
        Date date = new Date(unix*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM, dd, yyyy");
        return sdf.format(date);

    }

    /**
     * Converts unix to String Date with time
     * @param unix
     * @return unix as String date with time
     */
    public String unixToStringDateTime(int unix){
        Date date = new Date(unix*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM, dd, yyyy --\nhh:mm aa");
        return sdf.format(date);

    }

    /**
     * Calculates the difference between passed unix and current time and returns it as a string
     * @param unix Time created
     * @return The amount of hours or minutes passed since the passed unix
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String calculateMinutesAgo(int unix){
        long today = System.currentTimeMillis()/1000L;

        Log.d("calculateMinutes", "calculateMinutesAgo: " + today + " - " + unix);
        int difference = (int)today - unix;
        if ((difference/3600)<1)
            return "\t"+ String.valueOf(difference/60) + " m ago";
        else{
            int hours = 0;
            while(difference >3600){
                hours++;
                difference = difference-3600;
            }
            return "\t" + String.valueOf(hours) + "h " + (difference/60) + " m ago";
        }
    }

    /**
     * Converts date which is passed in as a String to unix time
     * @param time
     * @return unix time
     */
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



}
