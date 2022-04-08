package com.lejiebennett.proccoli2.NewModels;

public class NotificationTableDataModel {
    /*

    class notificationsStruct{
        int totalWaitingGoalInvitations;
        HashMap<String,goalInvitationFields> goalInvitationPacks;
        int totalWaitingSurvey;
        HashMap<String, waitingSurveyEventIdsFields> waitingSurveyEventIds;
        public notificationsStruct(int totalWaitingGoalInvitations, HashMap<String,goalInvitationFields> goalInvitationPacks, int totalWaitingSurvey, HashMap<String, waitingSurveyEventIdsFields> waitingSurveyEventIds){
            this.totalWaitingGoalInvitations = totalWaitingGoalInvitations;
            this.goalInvitationPacks = goalInvitationPacks;
            this.totalWaitingSurvey = totalWaitingSurvey;
            this.waitingSurveyEventIds = waitingSurveyEventIds;
        }

    }
    class waitingSurveyEventIdsFields {
        double deadline;
        String surveyDisplayName;
        String surveyIds;

        public waitingSurveyEventIdsFields(double deadline, String surveyDisplayName, String surveyIds) {
            this.deadline = deadline;
            this.surveyDisplayName = surveyDisplayName;
            this.surveyIds = surveyIds;
        }

        class parsedSurveyEventsStruct {
            String eventId;
            String surveyId;
            Date deadline;
            String surveyDisplayName;

            public parsedSurveyEventsStruct(String key, waitingSurveyEventIdsFields waitingSurveyEventIdAfterKey) {
                this.eventId = key;
                this.surveyId = waitingSurveyEventIdAfterKey.surveyIds;
                this.surveyDisplayName = waitingSurveyEventIdAfterKey.surveyDisplayName;
                this.deadline = waitingSurveyEventIdAfterKey.deadline;
            }

        }
    }
    class goalInvitationFields{
            double createdAt;
            String invitedByEmail;
            double invitedGoalDeadline;
            String invitedGoalId;
            String invitedGoalName;
            String taskType;
            String goalCreaterUid;

            public goalInvitationFields(double createdAt,String invitedByEmail, double invitedGoalDeadline, String invitedGoalId,String invitedGoalName,String taskType,String goalCreaterUid){
                this.createdAt = createdAt;
                this.invitedByEmail = invitedByEmail;
                this.invitedGoalDeadline = invitedGoalDeadline;
                this.invitedGoalId = invitedGoalId;
                this.invitedGoalName = invitedGoalName;
                this.taskType = taskType;
                this.goalCreaterUid = goalCreaterUid;
            }

        }
        class NotificationTable{
            NotificationTable sharedInstance = new NotificationTable();
            notificationsStruct data;
            public void setNotificationsStruct(notificationsStruct newValue){
                notificationsStruct value;
                if((value = newValue)==null) {
                    return;
                }
                NotificationTable.parserForSurveyEvents(value.waitingSurveyEventIds);
                TabbarVC.sharedInstance.surveyNotifCounter(true, value.totalWaitingSurvey, dotLblForSurveys);
                TabbarVC.sharedInstance.surveyNotifCounter(true, value.totalWaitingGoalInvitations, dotLblForGroupInvitation);
                InvitationViews.sharedInstance.data = value.goalInvitationPacks.values.sorted({$0.createdAt > $1.createdAt});
            }

            public void didSet(notificationsStruct oldValue){
                if(oldValue.totalWaitingSurvey!=this.data.totalWaitingSurvey){
                    TabbarVC.sharedInstance.surveyVCInstance.refreshTableData();
                }
            }

            ArrayList<waitingSurveyEventIdsFields.parsedSurveyEventsStruct> parsedSurveyEvents = new ArrayList<>();

            public void parserForSurveyEvents(HashMap<String, waitingSurveyEventIdsFields> data){
                if(data==null)
                    return;
                HashMap<String,waitingSurveyEventIdsFields> waitingSurveyEventIds = data;
                ArrayList<waitingSurveyEventIdsFields.parsedSurveyEventsStruct> events = new ArrayList<>();
                if(waitingSurveyEventIds.size() ==0){
                    this.sharedInstance.parsedSurveyEvents = null;
                    return;
                }
                for(String key: waitingSurveyEventIds.keySet()){
                    waitingSurveyEventIdsFields value = waitingSurveyEventIds.get(key);
                    if(value==null){
                        return;
                    }
                    events.add(new waitingSurveyEventIdsFields().parsedSurveyEventsStruct(key,value));
                }
                this.sharedInstance.parsedSurveyEvents = events;
            }
        }
        public boolean sanityCheckForDeadline(String eventKey, waitingSurveyEventIdsFields singleEvent){
            boolean status;
            Date now = Date().timeIntervalSince1970;
            if(singleEvent.deadline<=now && singleEvent.deadline!=0.0){
                //deleteEvent;
                DatabaseService.deleteExpiredSurvey(eventKey);
                status = false;
            }
            else{
                //Good to display
                status = true;
            }
            return status;
        }
        */
}
