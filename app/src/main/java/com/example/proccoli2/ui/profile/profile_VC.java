package com.example.proccoli2.ui.profile;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.proccoli2.NewModels.DataServices;
import com.example.proccoli2.NewModels.ResultHandler;
import com.example.proccoli2.NewModels.UserDataModel;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class profile_VC {
    private profileView profileView; //View
    private UserDataModel model = UserDataModel.sharedInstance;

    public profile_VC(profileView profileView){
        this.profileView = profileView;
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

    public String unixToString(int unix){
        Date date = new Date(unix*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");
        return sdf.format(date);

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



    public int dateStrToUnix2(String time) {
        long unixTime = 0;
        Log.d("ConvertBirthdate", "dateStrToUnix2: " + time);
        //  SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");//Specify your timezone
        SimpleDateFormat sf = new SimpleDateFormat("MMM dd yyyy");

        try {
            unixTime = sf.parse(time).getTime();
            unixTime = unixTime / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (int)unixTime;
    }


    public void loadCurrentUserInfoIntoView(){
        Log.d("loadCurrentUserInfo", "loadCurrentUserInfoIntoView: trying to load" );

        DataServices.getInstance().callUserInfo(new ResultHandler<Object>() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onSuccess(Object data) {

                Log.d("loadCurrentUser", "onSuccess: " + data);
                profileView.fullNameInput.setText(model.getFullName());
                profileView.occupationInput.setText(model.getOccupation());
                profileView.highestEducationInput.setText(model.getHighestLevelOfEducation());

                HashMap<String,String> profImage = model.getProfileImg();
                Log.d("profile_VC", "onSuccess: " + profImage);
                Set<String> image = profImage.keySet();
                String imageNumberKey = image.iterator().next();
                int imageNumber;
                if(imageNumberKey=="light6"){
                    imageNumber = 6;

                }
                else
                    imageNumber = Integer.valueOf(imageNumberKey);

                profileView.newColor = profImage.get(imageNumberKey);

                profileView.setAvatar(imageNumber);
                profileView.avatarImageO=profImage.get(profileView.newColor);
                profileView.dateButtonBirthdate.setText(unixToString((int)model.getBirthday()));
            }

            @Override
            public void onFailure(Exception e) {
                Log.d("Error", "onFailure: " + e.getLocalizedMessage());

            }
        });
    }

}
