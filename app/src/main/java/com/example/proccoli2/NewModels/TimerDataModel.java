package com.example.proccoli2.NewModels;

import java.util.Date;
import java.util.HashMap;

public class TimerDataModel {
    static SingletonStrings ss = new SingletonStrings();
    //study location will handle in DatabaseServices
    private String studyId;
    private long startDateTime;
    private int totalBreakeTimes;
    private boolean didFinish;
    private double proposedStudyTime;

    public TimerDataModel(String studyId, long startDateTime, int totalBreakeTime, boolean didFinish, double proposedStudyTime){
        this.studyId = studyId;
        this.startDateTime = startDateTime;
        this.totalBreakeTimes = totalBreakeTime;
        this.didFinish = didFinish;
        this.proposedStudyTime = proposedStudyTime;

    }

    public void setStudyId(String studyId) {
        this.studyId = studyId;
    }

    public void setTotalBreakeTimes(int totalBreakeTimes) {
        this.totalBreakeTimes = totalBreakeTimes;
    }

    public void setStartDateTime(long startDateTime) {
        this.startDateTime = startDateTime;
    }

    public void setDidFinish(boolean didFinish) {
        this.didFinish = didFinish;
    }

    public void setProposedStudyTime(double proposedStudyTime) {
        this.proposedStudyTime = proposedStudyTime;
    }

    public String getStudyId() {
        return studyId;
    }

    public long getStartDateTime() {
        return startDateTime;
    }

    public int getTotalBreakeTimes() {
        return totalBreakeTimes;
    }

    public boolean isDidFinish() {
        return didFinish;
    }

    public double getProposedStudyTime() {
        return proposedStudyTime;
    }

    public HashMap<String,Object> jsonFormatter(TimerDataModel data){
        HashMap<String,Object> hashMap1 = new HashMap<>();
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap1.put(ss.CREATED_AT,data.startDateTime);
        hashMap1.put(ss.TOTAL_BREAK_TIMES_REF,data.totalBreakeTimes);
        hashMap1.put(ss.IS_FINISHED_REF,data.didFinish);
        hashMap1.put(ss.PROPOSED_STUDY_TIME_REF,data.proposedStudyTime);
        hashMap1.put(ss.START_LOCATION_REF,ss.NO_LOCATION_REF);
        hashMap.put(data.studyId,hashMap1);
        return hashMap;

    }

    public static HashMap<String, Object> jsonFormatterForGroupGoal(String uid, String subgoalId, TimerDataModel data){
        HashMap<String,Object> hashMap1 = new HashMap<>();
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap1.put(ss.CREATED_AT, data.startDateTime);
        hashMap1.put(ss.TOTAL_BREAK_TIMES_REF,data.totalBreakeTimes);
        hashMap1.put(ss.IS_FINISHED_REF, data.didFinish);
        hashMap1.put(ss.PROPOSED_STUDY_TIME_REF,data.proposedStudyTime);
        hashMap1.put(ss.START_LOCATION_REF,ss.NO_LOCATION_REF);
        hashMap.put(uid + "." + subgoalId + "." + data.studyId, hashMap1);
        return hashMap;

    }

    public HashMap<String,Object> jsonFormatterForSelfTimeReportForStartTimerIndividual(String studyId, double startTime, double finishTime, int reportedTime){
        HashMap<String,Object> hashMap1 = new HashMap<>();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap1.put("reportTime",System.currentTimeMillis());
        hashMap1.put("reportedStudyTime", reportedTime);
        hashMap1.put("startTime", startTime);
        hashMap1.put("finishTime", finishTime);
        hashMap1.put("reportLocation", ss.NO_LOCATION_REF);
        hashMap.put(studyId, hashMap1);
        return hashMap;
    }

    public HashMap<String, Object> jsonFormatterForSelfTimeReportForStartTimerGroup(String uid, String subgoalId, String studyId, double startTime,double finishTime, int reportedTime){
        HashMap<String,Object> hashMap1 = new HashMap<>();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap1.put("reportTime",System.currentTimeMillis());
        hashMap1.put("reportedStudyTime", reportedTime);
        hashMap1.put("startTime",startTime);
        hashMap1.put("finishTime",finishTime);
        hashMap1.put("reportLocation",ss.NO_LOCATION_REF);
        hashMap.put(uid+"." + subgoalId + "." + studyId,hashMap1);
        return hashMap;
    }

}
