package com.lejiebennett.proccoli2.NewModels;

import java.util.HashMap;

public class TemporaryNotificationModel {

    HashMap<String,temporaryNotificationModelFields> notificationInfo;
    public TemporaryNotificationModel(){
    }

    class temporaryNotificationModelFields {

        String invitedGoalId;
        String invitedEmail;
        String invitedByEmail;
        String invitedGoalName;
        double invitedGoalDeadline;
        String invitedGoalTaskType;
    }






}
