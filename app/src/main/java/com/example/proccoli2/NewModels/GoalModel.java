
package com.example.proccoli2.NewModels;

import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.okhttp.internal.DiskLruCache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class GoalModel implements Serializable {
    static SingletonStrings ss = new SingletonStrings();
    //common varibles
    private String bigGoal;
    long personalDeadline;
    long whenIsDue;
    boolean isCompleted;
    private String goalType;
    private String goalCreaterUid;
    private long createdAt;
    private String taskType;
    String goalId;
    private String goalCreaterEmail;
    double proposedStudyTime;
    double studiedTime;
    boolean isGraded;

    public GoalModel(String bigGoal, String taskType, String goalId, long createdAt, boolean isGoalCompleted, String goalType, long whenIsItDue, String goalCreaterUid, double proposedStudyTime, double studiedTime, boolean isGraded) {
        this.bigGoal = bigGoal;
        this.taskType = taskType;
        this.goalId = goalId;
        this.createdAt = createdAt;
        this.isCompleted = isGoalCompleted;
        this.goalType = goalType;
        this.whenIsDue = whenIsItDue;
        this.goalCreaterUid = goalCreaterUid;
        this.proposedStudyTime = proposedStudyTime;
        this.studiedTime = studiedTime;
        this.isGraded = isGraded;

    }

    public long getPersonalDeadline() {
        return personalDeadline;
    }

    public long getWhenIsDue() {
        return whenIsDue;
    }

    public String getGoalId() {
        return goalId;
    }

    public double getProposedStudyTime() {
        return proposedStudyTime;
    }

    public double getStudiedTime() {
        return studiedTime;
    }

    public void setStudiedTime(double studiedTime) {
        this.studiedTime = studiedTime;
    }

    public void setBigGoal(String bigGoal) {
        this.bigGoal = bigGoal;
    }

    public void setGoalType(String goalType) {
        this.goalType = goalType;
    }

    public void setGoalCreaterUid(String goalCreaterUid) {
        this.goalCreaterUid = goalCreaterUid;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public void setGoalCreaterEmail(String goalCreaterEmail) {
        this.goalCreaterEmail = goalCreaterEmail;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public String getBigGoal() {
        return bigGoal;
    }

    public String getGoalType() {
        return goalType;
    }

    public String getGoalCreaterUid() {
        return goalCreaterUid;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public String getTaskType() {
        return taskType;
    }

    public String getGoalCreaterEmail() {
        return goalCreaterEmail;
    }

    public boolean isGraded() {
        return isGraded;
    }

    public void setGraded(boolean graded) {
        isGraded = graded;
    }

    public GoalModel(String bigGoal, long personalDeadline, String taskType, String eventId, long createdAt, boolean isCompleted, String goalType, long whenIsDue, String eventCreaterUid, double proposedStudyTime, double studiedTime, boolean isGraded){
        this.bigGoal = bigGoal;
        this.personalDeadline = personalDeadline;
        this.goalCreaterUid = eventCreaterUid;
        this.createdAt = createdAt;
        this.taskType = taskType;
        this.goalId = eventId;
        this.isCompleted = isCompleted;
        this.goalType = goalType;
        this.whenIsDue = whenIsDue;
        this.proposedStudyTime = proposedStudyTime;
        this.studiedTime = studiedTime;
        this.isGraded = isGraded;


    }
    public GoalModel(String bigGoal, long personalDeadline, String taskType, String eventId, long createdAt, String eventCreaterEmail, boolean isCompleted, String goalType, long whenIsDue, String eventCreaterUid, double proposedStudyTime, double studiedTime, boolean isGraded){
        this.bigGoal = bigGoal;
        this.personalDeadline = personalDeadline;
        this.goalCreaterUid = eventCreaterUid;
        this.createdAt = createdAt;
        this.taskType = taskType;
        this.goalId = eventId;
        this.goalCreaterEmail = eventCreaterEmail;
        this.isCompleted = isCompleted;
        this.goalType = goalType;
        this.whenIsDue = whenIsDue;
        this.proposedStudyTime = proposedStudyTime;
        this.studiedTime = studiedTime;
        this.isGraded = isGraded;


    }

    //Use for progress chart dummy data
    public GoalModel(String bigGoal,double proposedStudyTime, double studiedTime){
        this.bigGoal = bigGoal;
        this.proposedStudyTime = proposedStudyTime;
        this.studiedTime = studiedTime;
    }

    //New constructor for parseSingleGoalData

    public static ArrayList<GoalModel> parseGoalsData(QuerySnapshot snapshots){
        ArrayList<GoalModel> events = new ArrayList<>();
        if(snapshots==null)
            return null;
        else{
            QuerySnapshot snaps = snapshots;
            for(DocumentSnapshot snap :snaps.getDocuments()){
                HashMap<String,Object> data = (HashMap<String, Object>) snap.getData();
                String taskType = (String) getValueOrDefault(data.get(ss.TASK_TYPE_REF),"");
                String bigGoal = (String) getValueOrDefault(data.get(ss.BIG_GOAL_REF),"");
                long personalDeadline = (long) getValueOrDefault(data.get(ss.PERSONAL_DEADLINE_REF), 0.0);
                String eventCreaterUid = (String) getValueOrDefault(data.get(ss.GOAL_CREATER_UID_REF),"");
                long createdAt = (long) getValueOrDefault(data.get(ss.CREATED_AT),0.0);
                String eventId = (String) getValueOrDefault(data.get(ss.GOAL_ID_REF),"");
                String eventCreaterEmail = (String) getValueOrDefault(data.get(ss.GOAL_CREATER_EMAIL_REF),"");
                boolean isCompleted = (boolean) getValueOrDefault(data.get(ss.IS_GOAL_COMPLETED_REF),false);
                String goalType = (String) getValueOrDefault(data.get(ss.GOAL_TYPE_REF),"");
                long whenIsItDue = (long) getValueOrDefault(data.get(ss.WHEN_IS_IT_DUE_REF),0.0);
                double proposedStudyTIme = (double) getValueOrDefault(data.get(ss.TOTAL_PROPOSED_STUDY_TIME_REF),0.0);
                double studiedTime = (double) getValueOrDefault(data.get(ss.TOTAL_STUDIED_TIME_REF),0.0);
                String grade = (String) getValueOrDefault(data.get(ss.IS_GRADED_REF),"");
                boolean isGraded = false;
                if(grade.equals("") == false){
                    isGraded = true;
                }
                GoalModel event = new GoalModel(bigGoal,personalDeadline, taskType, eventId, createdAt, eventCreaterEmail, isCompleted, goalType,  whenIsItDue,  eventCreaterUid, proposedStudyTIme,  studiedTime, isGraded);
                events.add(event);
            }
            Log.d("parseData", "parseGoalsData: " + events);
            return events;
        }
    }

    public GoalModel parseSingleGoalData(DocumentSnapshot snapshots){
        if(snapshots == null)
            return null;
        else{
            DocumentSnapshot snap = snapshots;
            HashMap<String,Object> data = (HashMap<String, Object>) snap.getData();
            String taskType = (String) getValueOrDefault(data.get(ss.TASK_TYPE_REF),"");
            String bigGoal = (String) getValueOrDefault(data.get(ss.BIG_GOAL_REF),"");
            long personalDeadline = (long) getValueOrDefault(data.get(ss.PERSONAL_DEADLINE_REF), 0.0);
            String eventCreaterUid = (String) getValueOrDefault(data.get(ss.GOAL_CREATER_UID_REF),"");
            long createdAt = (long) getValueOrDefault(data.get(ss.CREATED_AT),0.0);
            String eventId = (String) getValueOrDefault(data.get(ss.GOAL_ID_REF),"");
            String eventCreaterEmail = (String) getValueOrDefault(data.get(ss.GOAL_CREATER_EMAIL_REF),"");
            boolean isCompleted = (boolean) getValueOrDefault(data.get(ss.IS_GOAL_COMPLETED_REF),false);
            String goalType = (String) getValueOrDefault(data.get(ss.GOAL_TYPE_REF),"");
            long whenIsItDue = (long) getValueOrDefault(data.get(ss.WHEN_IS_IT_DUE_REF),0.0);
            double proposedStudyTIme = (double) getValueOrDefault(data.get(ss.TOTAL_PROPOSED_STUDY_TIME_REF),0.0);
            double studiedTime = (double) getValueOrDefault(data.get(ss.TOTAL_STUDIED_TIME_REF),0.0);
            String grade = (String) getValueOrDefault(data.get(ss.IS_GRADED_REF),"");
            boolean isGraded = false;
            if(grade.equals("") == false){
                isGraded = true;
            }
            GoalModel goalModel = new GoalModel(bigGoal, personalDeadline, taskType, eventId, createdAt, eventCreaterEmail, isCompleted, goalType, whenIsItDue, eventCreaterUid, proposedStudyTIme, studiedTime, isGraded);
            return goalModel;

        }
    }

    public static HashMap<String,Object> jsonFormatterForGoals(GoalModel data){
        HashMap<String,Object> hashmap = new HashMap<>();
        hashmap.put(ss.IS_GOAL_COMPLETED_REF,data.isCompleted);
        hashmap.put(ss.GOAL_CREATER_UID_REF,DataServices.uid);
        hashmap.put(ss.TASK_TYPE_REF, data.taskType);
        hashmap.put(ss.BIG_GOAL_REF,data.bigGoal);
        hashmap.put(ss.PERSONAL_DEADLINE_REF,data.personalDeadline);
        hashmap.put(ss.CREATED_AT,data.createdAt);
        hashmap.put(ss.GOAL_ID_REF,data.goalId);
        hashmap.put(ss.GOAL_CREATER_EMAIL_REF,data.goalCreaterEmail);
        hashmap.put(ss.GOAL_TYPE_REF,data.goalType);
        hashmap.put(ss.WHEN_IS_IT_DUE_REF,data.whenIsDue);
        hashmap.put(ss.TOTAL_PROPOSED_STUDY_TIME_REF,data.proposedStudyTime);
        hashmap.put(ss.TOTAL_STUDIED_TIME_REF, data.studiedTime);
        return hashmap;
    }

    public static <T> T getValueOrDefault(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }

    public void setGoalId(String goalId) {
        this.goalId = goalId;
    }

    @Override
    public String toString() {
        return "GoalModel{" +
                "bigGoal='" + bigGoal + '\'' +
                ", personalDeadline=" + personalDeadline +
                ", whenIsDue=" + whenIsDue +
                ", isCompleted=" + isCompleted +
                ", goalType='" + goalType + '\'' +
                ", goalCreaterUid='" + goalCreaterUid + '\'' +
                ", createdAt=" + createdAt +
                ", taskType='" + taskType + '\'' +
                ", goalId='" + goalId + '\'' +
                ", goalCreaterEmail='" + goalCreaterEmail + '\'' +
                ", proposedStudyTime=" + proposedStudyTime +
                ", studiedTime=" + studiedTime +
                ", isGraded=" + isGraded +
                '}';
    }

    public boolean goalsEqual(GoalModel goal){
        Log.d("goalsEqualTest", "goalsEqual: " + this.goalId + " and " + goal.goalId);
        Log.d("GoalsEqualREsult", "goalsEqual: " + String.valueOf(this.goalId.equals(goal.goalId)));
        return this.goalId.equals(goal.goalId);
    }
}
