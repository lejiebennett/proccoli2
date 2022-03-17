package com.example.proccoli2.NewModels;

import java.io.Serializable;

public class groupGoalForPersonalCollection implements Serializable {
 String goalId;
 String bigGoal;
 String goalType;
 boolean isGoalCompleted;
 String taskType;
 String goalCreaterUid;
 long whenIsItDue;
 long createdAt;
 double totalProposedStudyTime;
 double totalStudiedTime;
 long personalDeadline;

 public static <T> T getValueOrDefault(T value, T defaultValue) {
     return value == null ? defaultValue : value;
 }

 public groupGoalForPersonalCollection(String goalId, String bigGoal, String goalType, boolean isGoalCompleted, String taskType, String goalCreaterUid, long whenIsItDue, long createdAt, double totalProposedStudyTime, double totalStudiedTime, long personalDeadline) {
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

 public static GoalModel goalsModelConverterForDataWrite(groupGoalForPersonalCollection data) {
     return new GoalModel(data.bigGoal, data.personalDeadline, data.taskType, data.goalId, data.createdAt, data.isGoalCompleted, data.goalType, data.whenIsItDue, data.goalCreaterUid, (double) getValueOrDefault(data.totalProposedStudyTime, 0.0), 0.0, false);

 }
}
