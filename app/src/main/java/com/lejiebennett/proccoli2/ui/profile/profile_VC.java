package com.lejiebennett.proccoli2.ui.profile;
/**
 * Copyright © 2022 Le Jie Bennett. All rights reserved.
 * Controller for profile actiivty
 */

import static com.lejiebennett.proccoli2.NewModels.SingletonStrings.EDIT_PROFILE_BACK_BTN_TAPPED_REF;
import static com.lejiebennett.proccoli2.NewModels.SingletonStrings.PROFILE_DATA_EDITTED_REF;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.lejiebennett.proccoli2.NewModels.DataServices;
import com.lejiebennett.proccoli2.NewModels.LogActivityModel;
import com.lejiebennett.proccoli2.NewModels.ResultHandler;
import com.lejiebennett.proccoli2.NewModels.SingletonStrings;
import com.lejiebennett.proccoli2.NewModels.UserDataModel;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class profile_VC {
    SingletonStrings ss = new SingletonStrings();
    private profileView profileView; //View
    private UserDataModel model = UserDataModel.sharedInstance;

    public profile_VC(profileView profileView){
        this.profileView = profileView;
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

            textView.setBackgroundColor(Color.RED);
            return false;
        }
        return true;
    }


    /**
     * Verify if dates are logical or not
     * @param birthdate
     * @param context
     * @param profileView
     * @return If dates are illogical return false, if dates are logical return true
     * @throws ParseException
     */
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

    /**
     * Convers the unix formatted date to a String version in the following format: "MMM, dd, yyyy --hh:mm aa"
     * @param unix Unix time to convert
     * @return Converted String
     */
    public String unixToString(int unix){
        Date date = new Date(unix*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");
        return sdf.format(date);

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


    /**
     * Loads current user info from firebase
     */
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

    /**
     * Updates current user info in firepase with passed updated Data
     * @param updateData Data to update in firebase
     */
    public void updateProfileInfo(HashMap<String,Object> updateData) {
        Log.d("UpdateProfileInfo", "setAvatarIntent: " + updateData);

        for(String field: updateData.keySet()){
            handleUserdataModelLocaly(field,updateData.get(field));
        }
        DataServices.getInstance().updateUserInfo(updateData);
        //EditProfileViews.sharedInstance?.preapreForRemove();
        //dismiss(animated: true, completion: nil)
        LogActivityModel.getActivityChain().addActivity(PROFILE_DATA_EDITTED_REF);
    }

    public void backBtnTapped() {
        LogActivityModel.getActivityChain().addActivity(EDIT_PROFILE_BACK_BTN_TAPPED_REF);
        //EditProfileViews.sharedInstance?.preapreForRemove()
        //dismiss(animated: true, completion: nil)
    }



    public void handleUserdataModelLocaly(String field, Object newData){

        if(field ==ss.POFILE_IMG_WITH_COLOR_REF) {
            UserDataModel.sharedInstance.profileImg((HashMap<String,String>) newData);
        }
        else if(field == ss.FULL_NAME_REF){
            UserDataModel.sharedInstance.setFullName((String) newData);
        }
        else if(field == ss.OCCUPATION_REF){
            UserDataModel.sharedInstance.setOccupation((String) newData);
        }
        else if(field == ss.HIGHEST_LEVEL_OF_EDUCATION_REF){
            UserDataModel.sharedInstance.setHighestLevelOfEducation((String) newData);

        }
        else if(field == ss.BIRTHDAY_REF){
            UserDataModel.sharedInstance.setBirthday(Integer.valueOf((int)newData).longValue());
        }

        Log.d("handleUser", "handleUserdataModelLocaly: " + UserDataModel.sharedInstance.toString());
    }

}
