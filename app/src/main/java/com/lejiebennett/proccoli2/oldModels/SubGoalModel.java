package com.lejiebennett.proccoli2.oldModels;

import java.io.Serializable;

public class SubGoalModel implements Serializable {
    private int deadline;
    private int difficultyLevel;
    private String subGoalName;
    private String howLongHours;
    private boolean isDeleted;
    private int startDate;
    private String subGoalId;
    private int totalStudyTime;
    private boolean isChecked;
    private int createdAt;

    //Below are attributes used for Group Subgoals
    private String subGoalAssignerID;
    private int percentageComplete = 0;

    //Constructor for group subgoal assigned to self
    public SubGoalModel(String subGoalID, String subGoalName, int deadline, String subGoalAssignerID,String howLongHours,int createdAt){
        this.subGoalId = subGoalID;
        this.subGoalName = subGoalName;
        this.deadline = deadline;
        this.subGoalAssignerID=subGoalAssignerID;
        this.howLongHours = howLongHours;
        this.createdAt = createdAt;
    }

    //Constructor gor group subgoal that is available to group members
    public SubGoalModel(String subGoalID, String subGoalName, int deadline, String subGoalAssignerID,int createdAt){
        this.subGoalId = subGoalID;
        this.subGoalName = subGoalName;
        this.deadline = deadline;
        this.subGoalAssignerID=subGoalAssignerID;
        this.createdAt=createdAt;
    }




    public SubGoalModel(String subGoalId, String subGoalName, int deadline, int difficultyLevel,
                        String howLongHours, int startDate, int createdAt){
        this.subGoalId = subGoalId;
        this.subGoalName = subGoalName;
        this.deadline = deadline;
        this.difficultyLevel = difficultyLevel;
        this.howLongHours = howLongHours;
        this.startDate = startDate;
        this.createdAt = createdAt;
    }

    public SubGoalModel(String subGoalName, int deadline, int difficultyLevel,
                        String howLongHours, int startDate, int createdAt){
//        this.subGoalId = subGoalId;
        this.subGoalName = subGoalName;
        this.deadline = deadline;
        this.difficultyLevel = difficultyLevel;
        this.howLongHours = howLongHours;
        this.startDate = startDate;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "SubGoal: " + subGoalName + "\n" + percentageComplete + "%deadline = " +deadline +
                ", difficultyLevel=" + difficultyLevel +
                ", howLongHours=" + howLongHours +
                ", startDate=" + startDate + " isChecked: " + isChecked +
                '}';
    }

    public boolean getIsChecked(){
        return this.isChecked;
    }

    public int getDeadline() {
        return deadline;
    }

    public String getSubGoalName(){
        return this.subGoalName;
    }

    public int getDifficultyLevel(){
        return this.difficultyLevel;
    }

    public String getHowLongHours(){
        return howLongHours;
    }

    public int getStartDate(){
        return startDate;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public void setSubGoalAssignerID(String subGoalAssignerID) {
        this.subGoalAssignerID = subGoalAssignerID;
    }

    public String getSubGoalAssignerID() {
        return subGoalAssignerID;
    }

    public int getPercentageComplete() {
        return percentageComplete;
    }

    public void setPercentageComplete(int percentageComplete) {
        this.percentageComplete = percentageComplete;
    }

    public void setHowLongHours(String howLongHours) {
        this.howLongHours = howLongHours;
    }

    public void setTotalStudyTime(int totalStudyTime){
        this.totalStudyTime = totalStudyTime;
    }

    public int getTotalStudyTime(){
        return this.totalStudyTime;
    }
}
