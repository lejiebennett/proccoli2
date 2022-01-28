package com.example.proccoli2;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class profile_VC {
    private profileView profileView; //View
    private UserModel model;

    public profile_VC(profileView profileView){
        this.profileView = profileView;
        model = new UserModel();
    }

    public boolean nullFieldCheck(String input, String errorMessage, Context context, TextInputEditText textView) {
        if (input.length() == 0) {
            CharSequence text = errorMessage;
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            textView.setBackgroundColor(Color.RED);
            return false;
        }
        return true;
    }


    public boolean compareDates(String birthdate, Context context,profileView profileView) throws ParseException {
        Log.d("Date", "compareDates: " + birthdate.toString());

        //Sun Oct 17 10:55:00 EDT 2021
      //  SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("MMM, dd, yyyy --hh:mm aa");



        Date today = new Date(System.currentTimeMillis());
        Log.d("Today's Date and time", "compareDates: " + today.toString());


        if(sdf.parse(birthdate).after(today))
        {
            Log.d("Compare FAiled", "compareDates: else RAN");
            return false;
        }


        if(today.before(sdf.parse(birthdate)) &&
                sdf.parse(birthdate).before(today))
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

}
