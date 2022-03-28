package com.example.proccoli2.ui.home.individualWall.goalSetting;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Copyright © 2022 Le Jie Bennett. All rights reserved.
 * Controller for goalSetting Activity
 */
public class goalSetting_Controller {

    private goalSettingView goalSettingView;
    public goalSetting_Controller(goalSettingView goalSettingView){
        this.goalSettingView = goalSettingView;
    }

    /**
     * Verifies the date logic is logical
     * @param start
     * @param complete
     * @param due
     * @return If the date logic is illogical return false, if the dates are logical return true
     * @throws ParseException
     */
    public boolean compareDates(int start, int complete, int due) throws ParseException {
        Log.d("Date", "compareDates: " + unixToStringDateTime(start) + unixToStringDateTime(complete) + unixToStringDateTime(due));

        //Sun Oct 17 10:55:00 EDT 2021
        SimpleDateFormat sdf = new SimpleDateFormat("MMM, dd, yyyy --hh:mm aa");


        Date today = new Date(System.currentTimeMillis());
        Log.d("Today's Date and time", "compareDates: " + today.toString());

        if(today.after(sdf.parse(unixToStringDateTime(complete))) || today.after(sdf.parse(unixToStringDateTime(due))))
        {
            Log.d("Compare Failed", "compareDates: else RAN");
            return false;
        }


        if(sdf.parse(unixToStringDateTime(start)).before(sdf.parse(unixToStringDateTime(complete))) &&
                sdf.parse(unixToStringDateTime(start)).before(sdf.parse(unixToStringDateTime(due))) &&
                sdf.parse(unixToStringDateTime(complete)).before(sdf.parse(unixToStringDateTime(due))) &&
                sdf.parse(unixToStringDateTime(complete)).after(sdf.parse(unixToStringDateTime(start))) &&
                sdf.parse(unixToStringDateTime(due)).after(sdf.parse(unixToStringDateTime(complete)))&&
                sdf.parse(unixToStringDateTime(due)).after(sdf.parse(unixToStringDateTime(start))))
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
     * Converts the unix formatted date to a String version in the following format: "MMM, dd, yyyy --hh:mm aa"
     * @param unix Unix time to convert
     * @return Converted String
     */
    public String unixToStringDateTime(int unix){
        Date date = new java.util.Date(unix*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMM, dd, yyyy --hh:mm aa");
        return sdf.format(date);

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



}
