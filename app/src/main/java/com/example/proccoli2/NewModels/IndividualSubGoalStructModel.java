package com.example.proccoli2.NewModels;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class IndividualSubGoalStructModel implements Serializable {
    private long _deadline;
    private int _difficultyLevel;
    private String _subGoalName;
    private int _howLongHours;
    private boolean _isDeleted;
    private long _startDate;
    private String _subGoalId;
    private int _totalStudyTime;
    boolean _isChecked;

    static SingletonStrings ss = new SingletonStrings();

    public void set_deadline(long _deadline) {
        this._deadline = _deadline;
    }

    public void set_difficultyLevel(int _difficultyLevel) {
        this._difficultyLevel = _difficultyLevel;
    }

    public void set_isChecked(boolean _isChecked) {
        this._isChecked = _isChecked;
    }

    public void set_subGoalName(String _subGoalName) {
        this._subGoalName = _subGoalName;
    }

    public void set_howLongHours(int _howLongHours) {
        this._howLongHours = _howLongHours;
    }

    public void set_isDeleted(boolean _isDeleted) {
        this._isDeleted = _isDeleted;
    }

    public void set_startDate(long _startDate) {
        this._startDate = _startDate;
    }

    public void set_subGoalId(String _subGoalId) {
        this._subGoalId = _subGoalId;
    }

    public boolean is_isChecked() {
        return _isChecked;
    }

    public long get_deadline() {
        return _deadline;
    }

    public int get_difficultyLevel() {
        return _difficultyLevel;
    }

    public String get_subGoalName() {
        return _subGoalName;
    }

    public int get_howLongHours() {
        return _howLongHours;
    }

    public boolean is_isDeleted() {
        return _isDeleted;
    }

    public long get_startDate() {
        return _startDate;
    }

    public String get_subGoalId() {
        return _subGoalId;
    }

    public int get_totalStudyTime() {
        return _totalStudyTime;
    }

    public IndividualSubGoalStructModel(String subgoalId, String subGoalName, long deadline, int difficultyLevel, int howLongHours, boolean isDeleted, long startDate, int totalStudyTime, boolean isChecked) {
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
        public static ArrayList<IndividualSubGoalStructModel> parseData(HashMap<String, Object> data){
            if(data==null){
                return null;
            }
            else{
                ArrayList<IndividualSubGoalStructModel> response = new ArrayList<IndividualSubGoalStructModel>();
                for(String subgoalId: data.keySet()){
                    Log.d("keys", "parseData: " + subgoalId);
                    if(subgoalId.equals(ss.NO_SUB_GOAL_REF)==false){
                        Log.d("parseIndividualSubGoal", "parseDataGetSubgoalID: " + data.get(subgoalId));
                        if(data.get(subgoalId)==null){
                            Log.d("here", "parseData: returning null");
                            return null;
                        }
                        else {
                            Log.d("ELSERan", "parseData: ELSE" );
                            HashMap<String,Object> subgoalData = (HashMap<String, Object>) data.get(subgoalId);
                            Log.d("subgoalData", "parseData: " + subgoalData);
                            IndividualSubGoalStructModel test = new IndividualSubGoalStructModel(subgoalId, "subGoalName", 0, 0, 0, false,0, 0, false);
                            Log.d("parseTest", "parseData: " + test.toString());

                            IndividualSubGoalStructModel newSubgoal= new IndividualSubGoalStructModel(subgoalId, (String) getValueOrDefault(subgoalData.get(ss.SUB_GOAL_NAME_REF),"err"), (long) getValueOrDefault(subgoalData.get(ss.SUB_DEADLINE_REF),0), (int)(long)getValueOrDefault(subgoalData.get(ss.DIFFICULTY_LEVEL_REF),0),(int)(long)getValueOrDefault(subgoalData.get(ss.HOW_LONG_REF),0), (boolean) getValueOrDefault(subgoalData.get(ss.IS_DELETED_REF),false), (long)getValueOrDefault(subgoalData.get(ss.PROPOSED_START_TIME_REF),0), (int)(long)getValueOrDefault(subgoalData.get(ss.TOTAL_STUDIED_TIME_REF),0), (boolean) getValueOrDefault(subgoalData.get(ss.IS_CHECKED_REF),false));

                            Log.d("newsubgoal", "parseData: " + newSubgoal);
                            response.add(newSubgoal);
                            Log.d("response", "parseData: " + response);

                        }

                    }
                }
                return response;
            }

        }



    public static HashMap<String, Object> jsonFormatterSingleIndividualSubGoal(IndividualSubGoalStructModel data){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(ss.SUB_GOAL_NAME_REF,data._subGoalName);
        hashMap.put(ss.SUB_DEADLINE_REF,data._deadline);
        hashMap.put(ss.HOW_LONG_REF,data._howLongHours);
        hashMap.put(ss.DIFFICULTY_LEVEL_REF,data._difficultyLevel);
        hashMap.put(ss.CREATED_AT,System.currentTimeMillis());
        hashMap.put(ss.IS_DELETED_REF,data._isDeleted);
        hashMap.put(ss.PROPOSED_START_TIME_REF,data._startDate);
        hashMap.put(ss.TOTAL_STUDIED_TIME_REF,data._totalStudyTime);
        hashMap.put(ss.IS_CHECKED_REF,data._isChecked);
        return hashMap;
    }

    public static HashMap<String,Object> jsonFormatterIndividual(ArrayList<IndividualSubGoalStructModel> data){
        HashMap<String,Object> finalData = new HashMap<>();
        if( data!=null) {
            for (IndividualSubGoalStructModel subGoal : data) {
                finalData.put(ss.SUB_GOAL_NAME_REF, subGoal._subGoalName);
                finalData.put(ss.SUB_DEADLINE_REF, subGoal._deadline);
                finalData.put(ss.HOW_LONG_REF, subGoal._howLongHours);
                finalData.put(ss.DIFFICULTY_LEVEL_REF, subGoal._difficultyLevel);
                finalData.put(ss.CREATED_AT, System.currentTimeMillis());
                finalData.put(ss.IS_DELETED_REF, subGoal._isDeleted);
                finalData.put(ss.PROPOSED_START_TIME_REF, subGoal._startDate);
                finalData.put(ss.TOTAL_STUDIED_TIME_REF, subGoal._totalStudyTime);
                finalData.put(ss.IS_CHECKED_REF, subGoal._isChecked);
            }
            return finalData;

        }
        else return null;
    }

    public HashMap<String,Object> addNoSubGoal(){
        HashMap<String,Object> hashMap = new HashMap<>();
        HashMap<String,Object> hashMap1 = new HashMap<>();
        hashMap1.put(ss.SUB_GOAL_NAME_REF,ss.NO_SUB_GOAL_REF);
        hashMap1.put(ss.CREATED_AT,System.currentTimeMillis());
        hashMap1.put(ss.TOTAL_STUDIED_TIME_REF, 0);

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

    public static <T> T getValueOrDefault(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }

    @Override
    public String toString() {
        return "IndividualSubGoalStructModel{" +
                "_deadline=" + _deadline +
                ", _difficultyLevel=" + _difficultyLevel +
                ", _subGoalName='" + _subGoalName + '\'' +
                ", _howLongHours=" + _howLongHours +
                ", _isDeleted=" + _isDeleted +
                ", _startDate=" + _startDate +
                ", _subGoalId='" + _subGoalId + '\'' +
                ", _totalStudyTime=" + _totalStudyTime +
                ", _isChecked=" + _isChecked +
                '}';
    }
}
