package com.example.proccoli2.NewModels;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

public class LogActivityModel implements Serializable {
    HashMap<String,activityPack> activity;

    public LogActivityModel(){
        HashMap<String,activityPack> hashMap = new HashMap<>();
        hashMap.put(getAlphaNumericString(11),new activityPack(APP_OPEN_REF,Date().timeIntervalSince1970,nil,nil,nil));
        this.activity = hashMap;
    }

    public addActivity(String type){
        this.activity.put(getAlphaNumericString(11),new activityPack(type,Date.timeIntervalSince1970,null,null,null));

    }

    public addActivityForGoal(String type, String goalId){
        this.activity.put(getAlphaNumericString(11),new activityPack(type, Date.timeIntervalSince1970,goalId,null,null));

    }

    public addActivityForSubGoal(String type, String goalId ,String subgoalId){
        this.activity.put(getAlphaNumericString(11), new activityPack(type, Date().timeIntervalSince1970,goalId,null,subgoalId));

    }

    public addSurveyActivity(String type, String surveyId){
        this.activity.put(getAlphaNumericString(11), new activityPack(type, Date().timeIntervalSince1970,null,surveyId,null));
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
    Date activityTime;
    String goalId;
    String surveyId;
    String subgoalId;

    public activityPack(String activityType, Date activityTime, String goalId, String surveyId, String subgoalId){
        this.activityType = activityType;
        this.activityTime = activityTime;
        this.goalId = goalId;
        this.surveyId = surveyId;
        this.subgoalId = subgoalId;
    }
}
