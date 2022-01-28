package com.example.proccoli2;

import android.content.Context;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class goalSetting_VC {

    private goalSettingView goalSettingView;
    public goalSetting_VC(goalSettingView goalSettingView){
        this.goalSettingView = goalSettingView;
    }

    public boolean compareDates(int start, int complete, int due) throws ParseException {
        Log.d("Date", "compareDates: " + unixToStringDateTime(start) + unixToStringDateTime(complete) + unixToStringDateTime(due));

        //Sun Oct 17 10:55:00 EDT 2021
        SimpleDateFormat sdf = new SimpleDateFormat("MMM, dd, yyyy --hh:mm aa");


        Date today = new Date(System.currentTimeMillis());
        Log.d("Today's Date and time", "compareDates: " + today.toString());


        if(today.after(sdf.parse(unixToStringDateTime(start))) || today.after(sdf.parse(unixToStringDateTime(complete)))
                || today.after(sdf.parse(unixToStringDateTime(due))))
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

    //https://stackoverflow.com/questions/7784421/getting-unix-timestamp-from-date
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

    public String unixToStringDateTime(int unix){
        Date date = new java.util.Date(unix*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMM, dd, yyyy --hh:mm aa");
        return sdf.format(date);

    }

    public String dateToStr(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("MMM, dd, yyyy --hh:mm aa");
        return formatter.format(date);
    }
}
