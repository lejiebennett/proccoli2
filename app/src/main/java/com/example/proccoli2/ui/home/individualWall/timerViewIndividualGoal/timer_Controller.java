package com.example.proccoli2.ui.home.individualWall.timerViewIndividualGoal;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Copyright © 2022 Le Jie Bennett. All rights reserved.
 * Controller for timer
 */
public class timer_Controller extends AppCompatActivity {
    private timerView timerView; //View

    public timer_Controller(timerView timerView){
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
