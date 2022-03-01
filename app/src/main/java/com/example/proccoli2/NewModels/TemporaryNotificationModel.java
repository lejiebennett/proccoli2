package com.example.proccoli2.NewModels;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Base64;
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
