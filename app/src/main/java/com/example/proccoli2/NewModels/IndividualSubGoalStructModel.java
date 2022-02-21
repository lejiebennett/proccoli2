package com.example.proccoli2.NewModels;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class IndividualSubGoalStructModel {
    private Date _deadline;
    private int _difficultyLevel;
    private String _subGoalName;
    private int _howLongHours;
    private boolean _isDeleted;
    private Date _startDate;
    private String _subGoalId;
    private int _totalStudyTime;
    boolean _isChecked;

    public IndividualSubGoalStructModel(String subgoalId, String subGoalName, Date deadline, int difficultyLevel,int howLongHours,boolean isDeleted,Date startDate, int totalStudyTime,boolean isChecked) {
        _deadline = deadline;
        _difficultyLevel = difficultyLevel;
        _subGoalName = subGoalName;
        _howLongHours = howLongHours;
        _isDeleted = isDeleted;
        _startDate = startDate;
        _subGoalId = subgoalId;
        _totalStudyTime = totalStudyTime;
        _isChecked = isChecked;
    }

    public ArrayList<IndividualSubGoalStructModel> parseData(HashMap<String,Object> data){
        if(data==null){
            return null;
        }
        else{
            ArrayList<IndividualSubGoalStructModel> response = new ArrayList<IndividualSubGoalStructModel>();
            for(String subgoalId: data.keySet()){
                if(subgoalId.equals( NO_SUB_GOAL_REF)==false){
                    if(data.get(subgoalId)==null){
                        return null;
                    }
                    else {
                        HashMap<String,Object> subgoalData = (HashMap<String, Object>) data.get(subgoalId);
                        response.add(new IndividualSubGoalStructModel(subgoalId,(String)getValueOrDefault(subgoalData.get(SUB_GOAL_NAME_REF),"err"), (Date) getValueOrDefault(subgoalData.get(SUB_DEADLINE_REF),0), (int)getValueOrDefault(subgoalData.get(DIFFICULTY_LEVEL_REF),0),(int)getValueOrDefault(subgoalData.get(HOW_LONG_REF),0), (boolean) getValueOrDefault(subgoalData.get(IS_DELETED_REF),false), (Date)getValueOrDefault(subgoalData.get(PROPOSED_START_TIME_REF),0), (int)getValueOrDefault(subgoalData.get(TOTAL_STUDIED_TIME_REF),0), (boolean) getValueOrDefault(subgoalData.get(IS_CHECKED_REF),false)));

                    }

                }
            }
            return response;
        }

    }

    public static <T> T getValueOrDefault(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }

    public HashMap<String, Object> jsonFormatterSingleIndividualSubGoal(IndividualSubGoalStructModel data){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(SUB_GOAL_NAME_REF,data._subGoalName);
        hashMap.put(SUB_DEADLINE_REF,data._deadline);
        hashMap.put(HOW_LONG_REF,data._howLongHours);
        hashMap.put(DIFFICULTY_LEVEL_REF,data._difficultyLevel);
        hashMap.put(CREATED_AT,Date().timeIntervalSince1970);
        hashMap.put(IS_DELETED_REFmdata._isDeleted);
        hashMap.put(PROPOSED_START_TIME_REF,data._startDate);
        hashMap.put(TOTAL_STUDIED_TIME_REF,data._totalStudyTime);
        hashMap.put(IS_CHECKED_REF,data._isChecked);
        return hashMap;
    }

    public static HashMap<String,Object> jsonFormatterIndividual(ArrayList<IndividualSubGoalStructModel> data){
        HashMap<String,Object> finalData = new HashMap<>();
        if( data!=null) {
            for (IndividualSubGoalStructModel subgoal : data) {
                finalData.put(SUB_GOAL_NAME_REF, subGoal._subGoalName);
                finalData.put(SUB_DEADLINE_REF, subGoal._deadline);
                finalData.put(HOW_LONG_REF, subGoal._howLongHours);
                finalData.put(DIFFICULTY_LEVEL_REF, subGoal._difficultyLevel);
                finalData.put(CREATED_AT, Date().timeIntervalSince1970);
                finalData.put(IS_DELETED_REF, subGoal._isDeleted);
                finalData.put(PROPOSED_START_TIME_REF, subGoal._startDate);
                finalData.put(TOTAL_STUDIED_TIME_REF, subGoal._totalStudyTime);
                finalData.put(IS_CHECKED_REF, subGoal._isChecked);
            }
            return finalData;

        }
        else return null;
    }

    public HashMap<String,Object> addNoSubGoal(){
        HashMap<String,Object> hashMap = new HashMap<>();
        HashMap<String,Object> hashMap1 = new HashMap<>();
        hashMap1.put(SUB_GOAL_NAME_REF,NO_SUB_GOAL_REF);
        hashMap1.put(CREATED_AT,Date().timeIntervalSince1970);
        hashMap1.put(TOTAL_STUDIED_TIME_REF, 0);

        hashMap.put(getAlphaNumericString(14),hashMap1);
        return hashMap;
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
