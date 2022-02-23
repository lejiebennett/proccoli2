package com.example.proccoli2.NewModels;

import com.example.proccoli2.SubGoalModel;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class IndividualGoalModel {
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
        return IndividualGoalModel((String)getValueOrDefault(data[BIG_GOAL_REF],"nil"),(long)getValueOrDefault(data[PERSONAL_DEADLINE_REF],0),(String)getValueOrDefault(data[TASK_TYPE_REF],""),IndividualSubGoalStructModel.parseData(data: data?[SUB_GOAL_PACK_REF] as? [String : AnyObject]),(String) getValueOrDefault(data[GOAL_CREATER_UID_REF],""), (String)getValueOrDefault(data[GOAL_ID_REF],""),(long) getValueOrDefault(data[CREATED_AT],0),(long)getValueOrDefault(data[PROPOSED_START_TIME_REF],0), (String) getValueOrDefault(data[GOAL_CREATER_EMAIL_REF],""), (boolean)getValueOrDefault(data[IS_GOAL_COMPLETED_REF],false), (String) getValueOrDefault(data[GOAL_TYPE_REF],""), (long) getValueOrDefault(data[WHEN_IS_IT_DUE_REF],0), (String) getValueOrDefault(data[RELATED_COURSE_REF],""));
    }

    public HashMap<String,Object> jsonFormatterForIndividualEvent(IndividualGoalModel data){
        HashMap<String,Object> hashmap = new HashMap<>();
        hashmap.put(IS_GOAL_COMPLETED_REF,data.isCompleted);
        hashmap.put(GOAL_CREATER_UID_REF,DatabaseService.uid);
        hashmap.put(TASK_TYPE_REF,data.taskType);
        hashmap.put(BIG_GOAL_REF,data.bigGoal);
        hashmap.put(PERSONAL_DEADLINE_REF,data.personalDeadline);
        hashmap.put(CREATED_AT,data.createdAt);
        hashmap.put(PROPOSED_START_TIME_REF,data.proposedStartDate);
        hashmap.put(GOAL_ID_REF,data.goalId);
        hashmap.put(SUB_GOAL_PACK_REF,IndividualSubGoalStructModel.jsonFormatterIndividual(data.subGoals));
        hashmap.put(GOAL_CREATER_EMAIL_REF,data.goalCreaterEmail);
        hashmap.put(GOAL_TYPE_REF,data.goalType);
        hashmap.put(WHEN_IS_IT_DUE_REF,data.whenIsDue);
        hashmap.put(RELATED_COURSE_REF,data.relatedCourse);

        /*
        HashMap<String,Object> hashmap = new HashMap<>();
        hashmap.put("IS_GOAL_COMPLETED_REF",data.isCompleted);
        hashmap.put("GOAL_CREATER_UID_REF",DatabaseService.uid);
        hashmap.put("TASK_TYPE_REF",data.taskType);
        hashmap.put("BIG_GOAL_REF",data.bigGoal);
        hashmap.put("PERSONAL_DEADLINE_REF",data.personalDeadline);
        hashmap.put("CREATED_AT",data.createdAt);
        hashmap.put("PROPOSED_START_TIME_REF",data.proposedStartDate);
        hashmap.put("GOAL_ID_REF",data.goalId);
        hashmap.put("SUB_GOAL_PACK_REF",individualSubGoals.jsonFormatterIndividual(data.subGoals));
        hashmap.put("GOAL_CREATER_EMAIL_REF",data.goalCreaterEmail);
        hashmap.put("GOAL_TYPE_REF",data.goalType);
        hashmap.put("WHEN_IS_IT_DUE_REF",data.whenIsDue);
        hashmap.put("RELATED_COURSE_REF",data.relatedCourse);
         */
        return hashmap;
    }

    public GoalModel goalsModelConverterForDataWrite(IndividualGoalModel data){
        return new GoalModel(data.bigGoal,data.personalDeadline, data.taskType, data.goalId, data.createdAt, data.goalCreaterEmail, data.isCompleted, data.goalType, data.whenIsDue, data.goalCreaterEmail, (double)getValueOrDefault(proposedStudyTime,0.0), 0.0, false);

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
