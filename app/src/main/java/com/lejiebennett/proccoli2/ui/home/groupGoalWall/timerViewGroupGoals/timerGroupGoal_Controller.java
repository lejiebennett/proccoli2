package com.lejiebennett.proccoli2.ui.home.groupGoalWall.timerViewGroupGoals;

import androidx.appcompat.app.AppCompatActivity;
/**
 *  Copyright © 2022 Le Jie Bennett. All rights reserved.
 *  Needs to be integrated with firebase, this version of the file is prior to firebase integration
 *  Controller for timerGroupGoal
 */
public class timerGroupGoal_Controller extends AppCompatActivity {
    private timerViewGroupGoal timerView; //View

    public timerGroupGoal_Controller(timerViewGroupGoal timerView){
        this.timerView = timerView;
    }

    public String convertMilliSecondsToTimerDisplay(int milliseconds){
        return String.valueOf((milliseconds / 1000)  / 60) + ":" + String.valueOf(((milliseconds/ 1000) % 60));
    }

    public int convertMinutesToMilliseconds(int mins){
        long millis = mins * 60 * 1000;
        return (int) millis;
    }
}
