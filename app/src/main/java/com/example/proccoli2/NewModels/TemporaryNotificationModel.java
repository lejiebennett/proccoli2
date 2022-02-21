package com.example.proccoli2.NewModels;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Base64;
import java.util.HashMap;

@RequiresApi(api = Build.VERSION_CODES.O)
public class TemporaryNotificationModel extends Base64.Decoder {
    HashMap<String,TemporaryNotificationModelFields> notificationInfo;
    String invitedGoalId;
    String invitedEmail;
    String invitedByEmail;
    String invitedGoalName;
    double invitedGoalDeadline;
    String invitedGoalTaskType;


    public TemporaryNotificationModel(String invitedGoalId, String invitedEmail, String invitedByEmail, String invitedGoalName, double invitedGoalDeadline, String invitedGoalTaskType){
        this.invitedGoalId = invitedGoalId;
        this.invitedEmail =  invitedEmail;
        this.invitedByEmail =  invitedByEmail;
        this.invitedGoalName =  invitedGoalName;
        this.invitedGoalDeadline =  invitedGoalDeadline;
        this.invitedGoalTaskType =  invitedGoalTaskType;
    }

}
