package com.example.proccoli2.NewModels;

import java.util.Date;
import java.util.HashMap;

public class TimerDataModel {
    //study location will handle in DatabaseServices
    private String studyId;
    private Date startDateTime;
    private int totalBreakeTimes;
    private boolean didFinish;
    private Date proposedStudyTime;

    public TimerDataModel(String studyId, Date startDateTime, int totalBreakeTime, boolean didFinish, Date proposedStudyTime){
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

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public void setDidFinish(boolean didFinish) {
        this.didFinish = didFinish;
    }

    public void setProposedStudyTime(Date proposedStudyTime) {
        this.proposedStudyTime = proposedStudyTime;
    }

    public String getStudyId() {
        return studyId;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public int getTotalBreakeTimes() {
        return totalBreakeTimes;
    }

    public boolean isDidFinish() {
        return didFinish;
    }

    public Date getProposedStudyTime() {
        return proposedStudyTime;
    }

    public HashMap<String,Object> jsonFormatter(TimerDataModel timeData){
        HashMap<String,Object> hashMap1 = new HashMap<>();
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap1.put(CREATED_AT,data.startDateTime);
        hashMap1.put(TOTAL_BREAK_TIMES_REF,data.totalBreakeTimes);
        hashMap1.put(IS_FINISHED_REF,data.didFinish);
        hashMap1.put(PROPOSED_STUDY_TIME_REF,data.proposedStudyTime);
        hashMap1.put(START_LOCATION_REF,NO_LOCATION_REF);
        hashMap.put(timeData.studyId,hashMap1);
        return hashMap;

    }

    public HashMap<String, Object> jsonFormatterForGroupGoal(String uid, String subgoalId, TimerDataModel data){
        HashMap<String,Object> hashMap1 = new HashMap<>();
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap1.put(CREATED_AT, data.startDateTime);
        hashMap1.put(TOTAL_BREAK_TIMES_REF,data.totalBreakeTimes);
        hashMap1.put(IS_FINISHED_REF, data.didFinish);
        hashMap1.put(PROPOSED_STUDY_TIME_REF,data.proposedStudyTime);
        hashMap1.put(START_LOCATION_REF,NO_LOCATION_REF);
        hashMap.put(uid + "." + subgoalId + "." + data.studyId, hashMap1);
        return hashMap;

    }

    public HashMap<String,Object> jsonFormatterForSelfTimeReportForStartTimerIndividual(String studyId, double startTime, double finishTime, int reportedTime){
        HashMap<String,Object> hashMap1 = new HashMap<>();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap1.put("reportTime",Date().timeIntervalSince1970);
        hashMap1.put("reportedStudyTime", reportedTime);
        hashMap1.put("startTime", startTime);
        hashMap1.put("finishTime", finishTime);
        hashMap1.put("reportLocation", NO_LOCATION_REF);
        hashMap.put(studyId, hashMap1);
        return hashMap;
    }

    public HashMap<String, Object> jsonFormatterForSelfTimeReportForStartTimerGroup(String uid, String subgoalId, String studyId, double startTime,double finishTime, int reportedTime){
        HashMap<String,Object> hashMap1 = new HashMap<>();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap1.put("reportTime",Date().timeIntervalSince1970);
        hashMap1.put("reportedStudyTime", reportedTime);
        hashMap1.put("startTime",startTime);
        hashMap1.put("finishTime",finishTime);
        hashMap1.put("reportLocation",NO_LOCATION_REF);
        hashMap.put(uid+"." + subgoalId + "." + studyId,hashMap1);
        return hashMap;
    }

}
