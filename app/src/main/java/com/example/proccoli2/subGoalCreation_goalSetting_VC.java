package com.example.proccoli2;

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


public class subGoalCreation_goalSetting_VC extends AppCompatActivity {

    private subGoalView_goalSetting subGoalView;

    public subGoalCreation_goalSetting_VC(subGoalView_goalSetting subGoalView){
        this.subGoalView = subGoalView;
    }

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

    public boolean compareDates(String start, String complete, Context context,String goalComplete) throws ParseException {
        Log.d("Date", "compareDates: " + start.toString() + complete.toString());

        //Sun Oct 17 10:55:00 EDT 2021
     //   SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("MMM, dd, yyyy --hh:mm aa");


        Date today = new Date(System.currentTimeMillis());
        Log.d("Today's Date and time", "compareDates: " + today.toString());


        if(today.after(sdf.parse(start)) || today.after(sdf.parse(complete)) || today.after(sdf.parse(goalComplete)))
        {
            Log.d("Compare FAiled", "compareDates: else RAN HERE");
            return false;
        }


        if(sdf.parse(start).before(sdf.parse(complete)) &&
                sdf.parse(complete).after(sdf.parse(start)) &&
                sdf.parse(goalComplete).after(sdf.parse(complete)) &&
                sdf.parse(goalComplete).after(sdf.parse(start)))
            return true;

        else{
            Log.d("Compare FAiled", "compareDates: else RAN");
            return false;
        }

    }


    //https://stackoverflow.com/questions/7784421/getting-unix-timestamp-from-date
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
    public String unixToStringDateTime(int unix){
        Date date = new java.util.Date(unix*1000L);
       // SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");//Specify your timezone
        SimpleDateFormat sdf = new SimpleDateFormat("MMM, dd, yyyy --hh:mm aa");

        return sdf.format(date);

    }
    public String dateToStr(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("MMM, dd, yyyy --hh:mm aa");
        return formatter.format(date);
    }



}
