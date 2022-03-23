package com.example.proccoli2.NewModels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class GroupGoalModel implements Serializable {

    DataServices ss = new DataServices();
    String goalId;
    String bigGoal;
    String goalType;
    boolean isGoalCompleted;
    String taskType;
    String goalCreaterUid;
    HashMap<String, groupMembersPack> groupMembers;
    String relatedCourse;
    long whenIsItDue;
    long createdAt;
    //@ExplicitNull groupSubgoalPack subGoalPack;
    groupSubgoalPack subGoalPack;


    public GroupGoalModel(String id, String bigGoal, String goalType, boolean isGoalCompleted, String taskType, String goalCreaterUid, HashMap<String, groupMembersPack> groupMembers, String relatedCourse, long whenIsItDue, long createdAt, groupSubgoalPack subGoalPack) {
        this.goalId = id;
        this.bigGoal=bigGoal;
        this.goalType=goalType;
        this.isGoalCompleted=isGoalCompleted;
        this.taskType=taskType;
        this.goalCreaterUid=goalCreaterUid;
        this.groupMembers=groupMembers;
        this.relatedCourse=relatedCourse;
        this.whenIsItDue=whenIsItDue;
        this.createdAt=createdAt;
        this.subGoalPack=subGoalPack;
    }

    public GroupGoalModel(String bigGoal, String goalType, boolean isGoalCompleted, String taskType, String goalCreaterUid, HashMap<String, groupMembersPack> groupMembers, String relatedCourse, long whenIsItDue, long createdAt, groupSubgoalPack subGoalPack) {
        this.bigGoal=bigGoal;
        this.goalType=goalType;
        this.isGoalCompleted=isGoalCompleted;
        this.taskType=taskType;
        this.goalCreaterUid=goalCreaterUid;
        this.groupMembers=groupMembers;
        this.relatedCourse=relatedCourse;
        this.whenIsItDue=whenIsItDue;
        this.createdAt=createdAt;
        this.subGoalPack=subGoalPack;
    }



    public boolean isGoalCompleted() {
        return isGoalCompleted;
    }


    public String getGoalId() {
        return goalId;
    }

    public String getTaskType() {
        return taskType;
    }

    public String getGoalCreaterUid() {
        return goalCreaterUid;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public String getGoalType() {
        return goalType;
    }

    public String getBigGoal() {
        return bigGoal;
    }

    public groupSubgoalPack getSubGoalPack() {
        return subGoalPack;
    }

    public HashMap<String, groupMembersPack> getGroupMembers() {
        return groupMembers;
    }

    public long getWhenIsItDue() {
        return whenIsItDue;
    }

    public String getRelatedCourse() {
        return relatedCourse;
    }

    public static GoalModel goalsModelConverterForDataWrite(GroupGoalModel data){
        return new GoalModel(data.bigGoal, data.taskType, data.goalId, data.createdAt, data.isGoalCompleted, data.goalType, data.whenIsItDue, data.goalCreaterUid, 0.0, 0.0, false);

    }
}
    class groupMembersPack implements Serializable {
        DataServices ss = new DataServices();
        String userName;
        String uid;
        String email;
        String status;
        //@ExplicitNull HashMap<String, assignedSubgoalsField> assignedSubgoals;
        HashMap<String, assignedSubgoalsField> assignedSubgoals;

        class assignedSubgoalsField implements Serializable {
            String subgoalName;
            String subgoalId;
            String subgoalStatus;

            public HashMap<String, Object> jsonConverter(assignedSubgoalsField data) {
                HashMap<String, Object> hashmap = new HashMap<>();
                hashmap.put(ss.SUB_GOAL_NAME_REF, data.subgoalName);
                hashmap.put(ss.SUB_GOAL_ID_REF, data.subgoalId);
                hashmap.put(ss.SUBGOAL_STATUS_REF, data.subgoalStatus);

                return hashmap;

            }
        }

        public HashMap<String, Object> jsonConverter(groupMembersPack data) {
            HashMap<String, Object> hashmap = new HashMap<>();
            hashmap.put(ss.USER_NAME_REF, data.userName);
            hashmap.put(ss.UID, data.uid);
            hashmap.put(ss.EMAIL, data.email);
            hashmap.put(ss.STATUS_REF, data.status);
            hashmap.put(ss.ASSIGNED_SUBGOAL_REF, data.assignedSubgoals);
            return hashmap;

        }
    }


    class groupSubgoalPack implements Serializable{
        ArrayList<groupSubGoalFields> subGoalsArray;

    }


    class groupSubGoalFields implements Serializable{
        DataServices ss = new DataServices();
        String status;
        boolean isChecked;
        boolean isDeleted;
        double subDeadline;
        String subgoalName;
        int totalStudiedTime;
        String assignedToUserName;
        String assignedToUid;
        String subgoalId;
        int progressPercentage;

        public groupSubGoalFields(String status, boolean isChecked, boolean isDeleted, double subDeadline, String subgoalName, int totalStudiedTime, String assignedToUserName, String assignedToUid, String subgoalId, int progressPercentage){
            this.status = status;
            this.isChecked= isChecked;
            this.isChecked = isDeleted;
            this.subDeadline = subDeadline;
            this.subgoalName = subgoalName;
            this.totalStudiedTime = totalStudiedTime;
            this.assignedToUserName = assignedToUserName;
            this.assignedToUid = assignedToUid;
            this.subgoalId = subgoalId;
            this.progressPercentage = progressPercentage;

        }

        public HashMap<String,Object> jsonConverter(groupSubGoalFields data){
            HashMap<String,Object> hashmap = new HashMap<>();
            hashmap.put(ss.STATUS_REF,data.status);
            hashmap.put(ss.IS_CHECKED_REF,data.isChecked);
            hashmap.put(ss.IS_DELETED_REF,data.isDeleted);
            hashmap.put(ss.SUB_DEADLINE_REF,data.subDeadline);
            hashmap.put(ss.SUB_GOAL_NAME_REF,data.subgoalName);
            hashmap.put(ss.TOTAL_STUDIED_TIME_REF,data.totalStudiedTime);
            hashmap.put(ss.ASSIGNED_TO_USER_NAME_REF,(String) getValueOrDefault(data.assignedToUserName,""));
            hashmap.put(ss.ASSIGNED_TO_UID_REF,(String) getValueOrDefault(data.assignedToUid,""));
            hashmap.put(ss.SUB_GOAL_ID_REF,data.subgoalId);
            hashmap.put(ss.PROGRESS_PERCENTAGE_REF,data.progressPercentage);
            return hashmap;
        }

        public  <T> T getValueOrDefault(T value, T defaultValue) {
            return value == null ? defaultValue : value;
        }


    }




