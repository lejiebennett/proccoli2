package com.example.proccoli2.NewModels;

import android.provider.ContactsContract;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

public class LogActivityModel implements Serializable {
    DataServices ss = new DataServices();
    HashMap<String,activityPack> activity;

    public LogActivityModel(){
        HashMap<String,activityPack> hashMap = new HashMap<>();
        hashMap.put(getAlphaNumericString(11),new activityPack(ss.APP_OPEN_REF,System.currentTimeMillis(),null,null,null));
        this.activity = hashMap;
    }

    public void addActivity(String type){
        this.activity.put(getAlphaNumericString(11),new activityPack(type,System.currentTimeMillis(),null,null,null));

    }

    public void addActivityForGoal(String type, String goalId){
        this.activity.put(getAlphaNumericString(11),new activityPack(type, System.currentTimeMillis(),goalId,null,null));

    }

    public void addActivityForSubGoal(String type, String goalId ,String subgoalId){
        this.activity.put(getAlphaNumericString(11), new activityPack(type, System.currentTimeMillis(),goalId,null,subgoalId));

    }

    public void addSurveyActivity(String type, String surveyId){
        this.activity.put(getAlphaNumericString(11), new activityPack(type, System.currentTimeMillis(),null,surveyId,null));
    }

    public String getAlphaNumericString(int n)
    {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

}

class activityPack implements Serializable{
    String activityType;
    long activityTime;
    String goalId;
    String surveyId;
    String subgoalId;

    public activityPack(String activityType, long activityTime, String goalId, String surveyId, String subgoalId){
        this.activityType = activityType;
        this.activityTime = activityTime;
        this.goalId = goalId;
        this.surveyId = surveyId;
        this.subgoalId = subgoalId;
    }
}
