package com.example.proccoli2.NewModels;

import com.squareup.okhttp.internal.DiskLruCache;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class GoalModel {
    //common varibles
    private String bigGoal;
    Date personalDeadline;
    Date whenIsDue;
    boolean isCompleted;
    private String goalType;
    private String goalCreaterUid;
    private Date createdAt;
    private String taskType;
    String goalId;
    private String goalCreaterEmail;
    double proposedStudyTime;
    double studiedTime;
    boolean isGraded;

    public void setBigGoal(String bigGoal) {
        this.bigGoal = bigGoal;
    }

    public void setGoalType(String goalType) {
        this.goalType = goalType;
    }

    public void setGoalCreaterUid(String goalCreaterUid) {
        this.goalCreaterUid = goalCreaterUid;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public void setGoalCreaterEmail(String goalCreaterEmail) {
        this.goalCreaterEmail = goalCreaterEmail;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getTaskType() {
        return taskType;
    }

    public String getGoalCreaterEmail() {
        return goalCreaterEmail;
    }

    public GoalModel(String bigGoal, Date personalDeadline, String taskType, String eventId, Date createdAt, String eventCreaterEmail, boolean isCompleted, String goalType, Date whenIsDue, String eventCreaterUid, double proposedStudyTime, double studiedTime, boolean isGraded){
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

    //New constructor for parseSingleGoalData

    public static ArrayList<GoalModel> parseGoalsData(QuerySnapshot snapshots){
        ArrayList<GoalModel> events = new ArrayList<>();
        if(snapshots==null)
            return null;
        else{
            QuerySnapshot snaps = snapshots;
            for(snap :snaps.documents) {
                data = snap.data();
                String taskType = (String) getValueOrDefault(data.get(TASK_TYPE_REF),"");
                String bigGoal = (String) getValueOrDefault(data.get(BIG_GOAL_REF),"");
                Date personalDeadline = (Date) getValueOrDefault(data.get(PERSONAL_DEADLINE_REF), 0.0);
                String eventCreaterUid = (String) getValueOrDefault(data.get(GOAL_CREATER_UID_REF),"");
                Date createdAt = (Date) getValueOrDefault(data.get(CREATED_AT),0.0);
                String eventId = (String) getValueOrDefault(data.get(GOAL_ID_REF),"");
                String eventCreaterEmail = (String) getValueOrDefault(data.get(GOAL_CREATER_EMAIL_REF),"");
                boolean isCompleted = (boolean) getValueOrDefault(data.get(IS_GOAL_COMPLETED_REF),false);
                String goalType = (String) getValueOrDefault(data.get(GOAL_TYPE_REF),"");
                Date whenIsItDue = (Date) getValueOrDefault(data.get(WHEN_IS_IT_DUE_REF),0.0);
                double proposedStudyTIme = (double) getValueOrDefault(getValueOrDefault(data.get(TOTAL_STUDIED_TIME_REF),0.0));
                String grade = (String) getValueOrDefault(data.get(IS_GRADED_REF),"");
                boolean isGraded = false;
                if(grade.equals("") == false){
                    isGraded = true;
                }
                GoalModel event = new GoalModel(bigGoal,personalDeadline, taskType, eventId, createdAt, eventCreaterEmail, isCompleted, goalType,  whenIsItDue,  eventCreaterUid, proposedStudyTIme,  studiedTime, isGraded);
                events.add(event);
            }
            return events;
        }
    }

    public GoalModel parseSingleGoalData(DocumentSnapshot snapshots){
        if(snapshots == null)
            return null;
        else{
            DocumentSnapshot snap = snapshots;
            data = snap.data();
            String taskType = (String) getValueOrDefault(data.get(TASK_TYPE_REF),"");
            String bigGoal = (String) getValueOrDefault(data.get(BIG_GOAL_REF),"");
            Date personalDeadline = (Date) getValueOrDefault(data.get(PERSONAL_DEADLINE_REF), 0.0);
            String eventCreaterUid = (String) getValueOrDefault(data.get(GOAL_CREATER_UID_REF),"");
            Date createdAt = (Date) getValueOrDefault(data.get(CREATED_AT),0.0);
            String eventId = (String) getValueOrDefault(data.get(GOAL_ID_REF),"");
            String eventCreaterEmail = (String) getValueOrDefault(data.get(GOAL_CREATER_EMAIL_REF),"");
            boolean isCompleted = (boolean) getValueOrDefault(data.get(IS_GOAL_COMPLETED_REF),false);
            String goalType = (String) getValueOrDefault(data.get(GOAL_TYPE_REF),"");
            Date whenIsItDue = (Date) getValueOrDefault(data.get(WHEN_IS_IT_DUE_REF),0.0);
            double proposedStudyTIme = (double) getValueOrDefault(getValueOrDefault(data.get(TOTAL_STUDIED_TIME_REF),0.0));
            String grade = (String) getValueOrDefault(data.get(IS_GRADED_REF),"");
            boolean isGraded = false;
            if(grade.equals("") == false){
                isGraded = true;
            }
            GoalModel goalModel = new GoalModel(bigGoal, personalDeadline, taskType, eventId, createdAt, eventCreaterEmail, isCompleted, goalType, whenIsItDue, eventCreaterUid, proposedStudyTIme, studiedTime, isGraded);
            return goalModel;

        }
    }

    public HashMap<String,Object> jsonFormatterForGoals(GoalModel data){
        HashMap<String,Object> hashmap = new HashMap<>();
        hashmap.put(IS_GOAL_COMPLETED_REF,data.isCompleted);
        hashmap.put(GOAL_CREATER_UID_REF,DatabaseService.uid);
        hashmap.put(TASK_TYPE_REF, data.taskType);
        hashmap.put(BIG_GOAL_REF,data.bigGoal);
        hashmap.put(PERSONAL_DEADLINE_REF,data.personalDeadline);
        hashmap.put(CREATED_AT,data.createdAt);
        hashmap.put(GOAL_ID_REF,data.goalId);
        hashmap.put(GOAL_CREATER_EMAIL_REF,data.goalCreaterEmail);
        hashmap.put(GOAL_TYPE_REF,data.goalType);
        hashmap.put(WHEN_IS_IT_DUE_REF,data.whenIsDue);
        hashmap.put(TOTAL_PROPOSED_STUDY_TIME_REF,data.proposedStudyTime);
        hashmap.put(TOTAL_STUDIED_TIME_REF, data.studiedTime);
        return hashmap;
    }

    public static <T> T getValueOrDefault(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }


}
