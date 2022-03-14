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
    double whenIsItDue;
    double createdAt;
    //@ExplicitNull groupSubgoalPack subGoalPack;
    groupSubgoalPack subGoalPack;


    public GroupGoalModel(String id, String bigGoal, String goalType, boolean isGoalCompleted, String taskType, String goalCreaterUid, HashMap<String, groupMembersPack> groupMembers, String relatedCourse, double whenIsItDue, double createdAt, groupSubgoalPack subGoalPack) {
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
    class groupGoalForPersonalCollection implements Serializable{
        String goalId;
        String bigGoal;
        String goalType;
        boolean isGoalCompleted;
        String taskType;
        String goalCreaterUid;
        double whenIsItDue;
        double createdAt;
        double totalProposedStudyTime;
        double totalStudiedTime;
        double personalDeadline;

        public groupGoalForPersonalCollection(String goalId, String bigGoal, String goalType, boolean isGoalCompleted, String taskType, String goalCreaterUid, double whenIsItDue, double createdAt, double totalProposedStudyTime, double totalStudiedTime, double personalDeadline){
            this.goalId = goalId;
            this.bigGoal = bigGoal;
            this.goalType = goalType;
            this.isGoalCompleted = isGoalCompleted;
            this.taskType = taskType;
            this.goalCreaterUid = goalCreaterUid;
            this.whenIsItDue = whenIsItDue;
            this.createdAt = createdAt;
            this.totalProposedStudyTime = totalProposedStudyTime;
            this.totalStudiedTime = totalStudiedTime;
            this.personalDeadline = personalDeadline;

        }
    }



