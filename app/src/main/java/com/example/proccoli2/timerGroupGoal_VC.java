package com.example.proccoli2;

import androidx.appcompat.app.AppCompatActivity;

public class timerGroupGoal_VC extends AppCompatActivity {
    private timerViewGroupGoal timerView; //View

    public timerGroupGoal_VC(timerViewGroupGoal timerView){
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
