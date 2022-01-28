package com.example.proccoli2;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class singleGoalView_VC extends AppCompatActivity {

    private singleGoalView singleGoalView;
    public singleGoalView_VC(singleGoalView singleGoalView){
        this.singleGoalView = singleGoalView;
    }

    public String unixToString(int unix){
        Date date = new java.util.Date(unix*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMM, dd, yyyy");
        return sdf.format(date);

    }

    public String unixToStringDateTime(int unix){
        Date date = new java.util.Date(unix*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMM, dd, yyyy --\nhh:mm aa");
        return sdf.format(date);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String calculateMinutesAgo(int unix){
        /*
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime unixConverted = LocalDateTime.ofEpochSecond(unix, 0, ZoneOffset.UTC);
        Log.d("currentUnix", "calculateMinutesAgo: " +now + " - " + unixConverted);
        return String.valueOf(Duration.between(now,unixConverted).toMinutes());

         */
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
