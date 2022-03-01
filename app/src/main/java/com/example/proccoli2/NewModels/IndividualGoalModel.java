package com.example.proccoli2.NewModels;

import com.example.proccoli2.SubGoalModel;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class IndividualGoalModel {

    SingletonStrings ss = new SingletonStrings();
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
    long proposedStartDate;
    ArrayList<IndividualSubGoalStructModel> subGoals = new ArrayList<>();
    double proposedStudyTime;
    String relatedCourse;

    public IndividualGoalModel(String bigGoal, long personalDeadline, String taskType, ArrayList<IndividualSubGoalStructModel> subGoals, String eventCreaterUid, String eventId, long createdAt, long proposedStartDate, String eventCreaterEmail, boolean isCompleted, String goalType, long whenIsDue, String relatedCourse){
        this.bigGoal = bigGoal;
        this.personalDeadline = personalDeadline;
        this.subGoals = subGoals;
        this.goalCreaterUid = eventCreaterUid;
        this.createdAt = createdAt;
        this.taskType = taskType;
        this.goalId = eventId;
        this.proposedStartDate = proposedStartDate;
        this.goalCreaterEmail = eventCreaterEmail;
        this.isCompleted = isCompleted;
        this.goalType = goalType;
        this.whenIsDue = whenIsDue;
        this.relatedCourse = relatedCourse;
    }

    public IndividualGoalModel parseData(DocumentSnapshot docSnap){
        Map<String,Object> data = docSnap.getData();
        HashMap<String,Object> parsedSubgoals = (HashMap<String,Object>)getValueOrDefault(data.get(ss.SUB_GOAL_PACK_REF),new HashMap<String,Object>());
        return new IndividualGoalModel((String)getValueOrDefault(data.get(ss.BIG_GOAL_REF),"nil"),(long)getValueOrDefault(data.get(ss.PERSONAL_DEADLINE_REF),0),(String)getValueOrDefault(data.get(ss.TASK_TYPE_REF),""),IndividualSubGoalStructModel.parseData(parsedSubgoals),(String) getValueOrDefault(data.get(ss.GOAL_CREATER_UID_REF),""), (String)getValueOrDefault(data.get(ss.GOAL_ID_REF),""),(long) getValueOrDefault(data.get(ss.CREATED_AT),0),(long)getValueOrDefault(data.get(ss.PROPOSED_START_TIME_REF),0), (String) getValueOrDefault(data.get(ss.GOAL_CREATER_EMAIL_REF),""), (boolean)getValueOrDefault(data.get(ss.IS_GOAL_COMPLETED_REF),false), (String) getValueOrDefault(data.get(ss.GOAL_TYPE_REF),""), (long) getValueOrDefault(data.get(ss.WHEN_IS_IT_DUE_REF),0), (String) getValueOrDefault(data.get(ss.RELATED_COURSE_REF),""));
    }

    public static HashMap<String,Object> jsonFormatterForIndividualEvent(IndividualGoalModel data){
        SingletonStrings ss = new SingletonStrings();
        HashMap<String,Object> hashmap = new HashMap<>();
        hashmap.put(ss.IS_GOAL_COMPLETED_REF,data.isCompleted);
        hashmap.put(ss.GOAL_CREATER_UID_REF,DataServices.uid);
        hashmap.put(ss.TASK_TYPE_REF,data.taskType);
        hashmap.put(ss.BIG_GOAL_REF,data.bigGoal);
        hashmap.put(ss.PERSONAL_DEADLINE_REF,data.personalDeadline);
        hashmap.put(ss.CREATED_AT,data.createdAt);
        hashmap.put(ss.PROPOSED_START_TIME_REF,data.proposedStartDate);
        hashmap.put(ss.GOAL_ID_REF,data.goalId);
        hashmap.put(ss.SUB_GOAL_PACK_REF,IndividualSubGoalStructModel.jsonFormatterIndividual(data.subGoals));
        hashmap.put(ss.GOAL_CREATER_EMAIL_REF,data.goalCreaterEmail);
        hashmap.put(ss.GOAL_TYPE_REF,data.goalType);
        hashmap.put(ss.WHEN_IS_IT_DUE_REF,data.whenIsDue);
        hashmap.put(ss.RELATED_COURSE_REF,data.relatedCourse);

        return hashmap;
    }

    public static GoalModel goalsModelConverterForDataWrite(IndividualGoalModel data){
        return new GoalModel(data.bigGoal,data.personalDeadline, data.taskType, data.goalId, data.createdAt, data.goalCreaterEmail, data.isCompleted, data.goalType, data.whenIsDue, data.goalCreaterEmail, (double)getValueOrDefault(data.proposedStudyTime, 0.0), 0.0, false);

    }



    public static <T> T getValueOrDefault(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }

    public void setBigGoal(String bigGoal){
        this.bigGoal=bigGoal;
    }

    public void setGoalType(String goalType) {
        this.goalType = goalType;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
    public void setGoalCreaterUid(String goalCreaterUid){
        this.goalCreaterUid=goalCreaterUid;
    }
    public void setTaskType(String taskType){
        this.taskType=taskType;
    }

    public void setGoalCreaterEmail(String goalCreaterEmail){
        this.goalCreaterEmail=goalCreaterEmail;
    }

    public void setProposedStartDate(long proposedStartDate){
        this.proposedStartDate=proposedStartDate;
    }

    public String getGoalType() {
        return goalType;
    }

    public String getBigGoal() {
        return bigGoal;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public String getGoalCreaterUid() {
        return goalCreaterUid;
    }

    public String getTaskType() {
        return taskType;
    }

    public String getGoalCreaterEmail() {
        return goalCreaterEmail;
    }

    public long getProposedStartDate() {
        return proposedStartDate;
    }


}
