package com.example.proccoli2.oldModels;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

public class GroupGoalModel implements Serializable{
    private String bigGoal;
    private int completedBy;
    private int deadline;
    private boolean isCompleted;
    private String goalType;
    private String goalCreatorUid;
    private int createdAt;
    private String goalId;
    private String goalCreatorEmail;
    private double proposedStudyTime;
    private double studiedTime;
    private boolean isGraded;
    private String courseNumber;

    //Added based on UI Flow
    private int startDate;
    private ArrayList<SubGoalModel> subgoals = new ArrayList<SubGoalModel>();
    private ArrayList<PersonalNoteModel> personalNotes = new ArrayList<PersonalNoteModel>();



    public GroupGoalModel(){
    }
    public GroupGoalModel(String goalId, String bigGoal, int deadline, int createdAt, String goalType){
        this.goalId = goalId;
        this.bigGoal = bigGoal;
        this.deadline = deadline;
        this.createdAt = createdAt;
        this.goalType = goalType;
    }

    public GroupGoalModel(String goalId, String bigGoal, int completedBy, String goalType, int startDate, int deadline, int createdAt, ArrayList<SubGoalModel> subgoals){
//        this.goalId = goalId;
        this.bigGoal = bigGoal;
        this.completedBy = completedBy;
        this.goalType = goalType;
        this.startDate = startDate;
        this.deadline = deadline;
        this.createdAt = createdAt;
        this.subgoals = subgoals;
    }

    public GroupGoalModel(String bigGoal, int completedBy, String goalType, int startDate, int deadline, int createdAt, ArrayList<SubGoalModel> subgoals){
        this.bigGoal = bigGoal;
        this.completedBy = completedBy;
        this.goalType = goalType;
        this.startDate = startDate;
        this.deadline = deadline;
        this.createdAt = createdAt;
        this.subgoals = subgoals;
    }

    public ArrayList<SubGoalModel> getSubGoals(){
        return subgoals;
    }

    public void setStartDate(int startDate) {
        this.startDate = startDate;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    public void setBigGoal(String bigGoal) {
        this.bigGoal = bigGoal;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public void setCompletedBy(int completedBy) {
        this.completedBy = completedBy;
    }

    public void setCreatedAt(int createdAt) {
        this.createdAt = createdAt;
    }

    public void setGoalCreatorEmail(String goalCreatorEmail) {
        this.goalCreatorEmail = goalCreatorEmail;
    }

    public void setGoalCreatorUid(String goalCreatorUid) {
        this.goalCreatorUid = goalCreatorUid;
    }

    public void setGoalId(String goalId) {
        this.goalId = goalId;
    }

    public void setGoalType(String goalType) {
        this.goalType = goalType;
    }

    public void setSubgoals(ArrayList<SubGoalModel> subgoals) {
        this.subgoals = subgoals;
    }

    public void setGraded(boolean graded) {
        isGraded = graded;
    }

    public void setProposedStudyTime(double proposedStudyTime) {
        this.proposedStudyTime = proposedStudyTime;
    }

    public void setStudiedTime(double studiedTime) {
        this.studiedTime = studiedTime;
    }

    public String toString(){
        return "Goal: " + bigGoal + " " + completedBy + " " + goalType + " " + startDate + " " + deadline + isCompleted + " " + subgoals;
    }

    public int getDeadline() {
        return deadline;
    }

    public boolean getIsCompleted() {
        return isCompleted;
    }

    public int getCompletedBy() {
        return completedBy;
    }

    public int getCreatedAt() {
        return createdAt;
    }

    public String getBigGoal() {
        return bigGoal;
    }

    public String getGoalCreatorUid() {
        return goalCreatorUid;
    }

    public String getGoalType() {
        return goalType;
    }

    public String getGoalId() {
        return goalId;
    }

    public int getStartDate() {
        return startDate;
    }

    public String getGoalCreatorEmail() {
        return goalCreatorEmail;
    }

    public ArrayList<PersonalNoteModel> getPersonalNotes(){
        return personalNotes;
    }

    public void setPersonalNotes(ArrayList<PersonalNoteModel> personalNotes){
        this.personalNotes = personalNotes;
    }

    public String getCourseNumber() {
        return courseNumber;
    }
    public void setCourseNumber(String courseNumber){
        this.courseNumber = courseNumber;
    }

    public boolean goalsEqual(GroupGoalModel goal){
        Log.d("goalsEqualTest", "goalsEqual: " + this.bigGoal + " and " + goal.getBigGoal());
        Log.d("GoalsEqualREsult", "goalsEqual: " + String.valueOf(this.bigGoal.equals(goal.getBigGoal())));
        return this.bigGoal.equals(goal.getBigGoal());
    }



}
