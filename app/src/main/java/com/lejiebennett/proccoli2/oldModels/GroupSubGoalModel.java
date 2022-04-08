package com.lejiebennett.proccoli2.oldModels;

import java.io.Serializable;

public class GroupSubGoalModel implements Serializable {
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


    public GroupSubGoalModel(String subGoalId, String subGoalName, int deadline,
                             int createdAt){
        this.subGoalId = subGoalId;
        this.subGoalName = subGoalName;
        this.deadline = deadline;
        this.createdAt = createdAt;
    }


    public GroupSubGoalModel(String subGoalId, String subGoalName, int deadline,
                             String howLongHours, int createdAt){
        this.subGoalId = subGoalId;
        this.subGoalName = subGoalName;
        this.deadline = deadline;
        this.howLongHours = howLongHours;
        this.createdAt = createdAt;
    }

    public GroupSubGoalModel(String subGoalId, String subGoalName, int deadline, int difficultyLevel,
                             String howLongHours, int startDate, int createdAt){
        this.subGoalId = subGoalId;
        this.subGoalName = subGoalName;
        this.deadline = deadline;
        this.difficultyLevel = difficultyLevel;
        this.howLongHours = howLongHours;
        this.startDate = startDate;
        this.createdAt = createdAt;
    }

    public GroupSubGoalModel(String subGoalName, int deadline, int difficultyLevel,
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
        return "SubGoal: " + subGoalName + "\n,deadline = " +deadline +
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
}
