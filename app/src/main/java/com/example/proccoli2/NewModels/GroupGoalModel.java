package com.example.proccoli2.NewModels;

import com.example.proccoli2.SubGoalModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

public class GroupGoalModel implements Serializable {

    String goalId;
    String bigGoal;
    String goalType;
    String isGoalCompleted;
    String taskType;
    String goalCreaterUid;
    HashMap<String, groupMembersPack> groupMembers;
    String relatedCourse;
    double whenIsItDue;
    double createdAt;
    @ExplicitNull groupSubgoalPack subGoalPack;

}
    class groupMembersPack implements Serializable {
        String userName;
        String uid;
        String email;
        String status;
        @ExplicitNull HashMap<String, assignedSubgoalsField> assignedSubgoals;

        class assignedSubgoalsField implements Serializable {
            String subgoalName;
            String subgoalId;
            String subgoalStatus;

            public HashMap<String, Object> jsonConverter(assignedSubgoalsField data) {
                HashMap<String, Object> hashmap = new HashMap<>();
                hashmap.put(SUB_GOAL_NAME_REF, data.subgoalName);
                hashmap.put(SUB_GOAL_ID_REF, data.subgoalId);
                hashmap.put(SUBGOAL_STATUS_REF, data.subgoalStatus);

                return hashmap;

            }
        }

        public HashMap<String, Object> jsonConverter(groupMembersPack data) {
            HashMap<String, Object> hashmap = new HashMap<>();
            hashmap.put(USER_NAME_REF, data.userName);
            hashmap.put(UID, data.uid);
            hashmap.put(EMAIL, data.email);
            hashmap.put(STATUS_REF, data.status);
            hashmap.put(ASSIGNED_SUBGOAL_REF, data.assignedSubgoals);
            return hashmap;

        }
    }

    class groupSubgoalPack implements Serializable{
        ArrayList<groupSubGoalFields> subGoalsArray;
        class DynamicCodingKeysForGroup implements Serializable{
            String stringValue;
            int intValue;
            public DynamicCodingKeysForGroup(String stringValue){
                this.stringValue = stringValue;
            }

            public Object DynamicCodingKeysForGroup(int intValue){
                return null;
            }

        public groupSubgoalPack(){
            ArrayList<groupSubGoalFields> tempArr = new ArrayList<>();
            for(DynamicCodingKeysForGroup key: container.allKeys){
                if(DynamicCodingKeysForGroup(key.stringValue)!=null){
                    try{
                        data = container.decode(groupSubGoalFields.this,jsonKey);
                        data.subgoalID = key.stringValue;
                        tempArr.append(data);
                    }
                }
                else return;
            }
            this.subGoalsArray = tempArr;
        }
    }
    class groupSubGoalFields implements Serializable{
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
            hashmap.put(STATUS_REF,data.status);
            hashmap.put(IS_CHECKED_REF,data.isChecked);
            hashmap.put(IS_DELETED_REF,data.isDeleted);
            hashmap.put(SUB_DEADLINE_REF,data.subDeadline);
            hashmap.put(SUB_GOAL_NAME_REF,data.subgoalName);
            hashmap.put(TOTAL_STUDIED_TIME_REF,data.totalStudiedTime);
            hashmap.put(ASSIGNED_TO_USER_NAME_REF,(String) getValueOrDefault(data.assignedToUserName,"");
            hashmap.put(ASSIGNED_TO_UID_REF,(String) getValueOrDefault(data.assignedToUid,"");
            hashmap.put(SUB_GOAL_ID_REF,data.subgoalId);
            hashmap.put(PROGRESS_PERCENTAGE_REF,data.progressPercentage);
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
}
