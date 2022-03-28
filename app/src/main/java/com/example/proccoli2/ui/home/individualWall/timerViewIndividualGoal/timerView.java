package com.example.proccoli2.ui.home.individualWall.timerViewIndividualGoal;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.proccoli2.NewModels.DataServices;
import com.example.proccoli2.NewModels.IndividualGoalModel;
import com.example.proccoli2.NewModels.IndividualSubGoalStructModel;
import com.example.proccoli2.NewModels.SingletonStrings;
import com.example.proccoli2.NewModels.TimerDataModel;
import com.example.proccoli2.R;
import com.example.proccoli2.smileyFaceSurveyView;
import com.example.proccoli2.ui.home.individualWall.singleGoalView;
import com.example.proccoli2.ui.notificationPublisher.NotificationPublisherTimer;
import com.google.android.material.button.MaterialButton;

import java.util.Calendar;
import java.util.HashMap;
/**
 * Copyright © 2022 Le Jie Bennett. All rights reserved.
 * Activity for timer
 * Will need further testing, but it does create a notification and sets if off
 * Timer currently appears only on the timer page, tested when app is set in the background with one timer set
 */

public class timerView extends AppCompatActivity {

    SingletonStrings ss = new SingletonStrings();
    SeekBar timerSlider;
    MaterialButton startBtn, stopBtn, breakBtn, resumeBtn;
    TextView timerClock;
    int counter;
    timer_Controller controller = new timer_Controller(this);
    CountDownTimer timer;
    int desiredCountDownInMillis;
    int displayTime; //Time that is displayed on the countdown timer
    Toolbar toolbar;
    int smileRating;
    int selectedStudiedTime; //Time the user selected for the timer
    int studiedTime; //Time that the user actually studied. This is sent back to the singleGoalView


    //Firebase integration
    String selectedSubGoalId;
    String goalId;
    String studyId;

    IndividualGoalModel originalGoal;
    IndividualSubGoalStructModel clickedSubGoal;
    int positionToUpdateStudiedTime;

    /***
     * Used to catch data from the smileyFace survey where users can rate their satisfaction with the goal they just completed
     */
    ActivityResultLauncher<Intent> activityResultLaunchSmileyRating = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        cancelNotification(getBaseContext(),1);
                        Log.d("onActivityResult", "ActivityResultLaunchSmileyRating: RUNNING");
                        smileRating = Integer.parseInt(result.getData().getStringExtra("smileRating"));
                        Log.d("RESULTSFROMsetSmile", String.valueOf(smileRating));

                        Intent myIntent = new Intent(timerView.this, singleGoalView.class);
                        myIntent.putExtra("smileRating",Integer.toString(smileRating));
                        myIntent.putExtra("studiedTime",Integer.toString(studiedTime));
                        setResult(RESULT_OK,myIntent);
                        finish();
                    }
                }
            });

    //Set Timer
    NotificationChannel notificationChannel;
    NotificationManager notificationManager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_view);
        Log.d("Created new", "onCreate: Created new");

        Bundle bundle = getIntent().getExtras();
        originalGoal = (IndividualGoalModel) bundle.getSerializable("bigGoal");
        if((IndividualSubGoalStructModel) bundle.getSerializable("subGoal_selected")!=null){
            clickedSubGoal = (IndividualSubGoalStructModel) bundle.getSerializable("subGoal_selected");
            selectedSubGoalId = clickedSubGoal.get_subGoalId();
            positionToUpdateStudiedTime = (int) bundle.getSerializable("positionToUpdateStudiedTime");
        }
        else{
            selectedSubGoalId = ss.NO_SUB_GOAL_REF;
        }
        goalId = originalGoal.getGoalId();



        toolbar = findViewById(R.id.toolbarTimer);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        startBtn = findViewById(R.id.startBtn);
        startBtn.setVisibility(View.VISIBLE);
        stopBtn = findViewById(R.id.stopBtn);
        stopBtn.setVisibility(View.INVISIBLE);

        breakBtn = findViewById(R.id.breakBtn);
        breakBtn.setVisibility(View.INVISIBLE);


        resumeBtn = findViewById(R.id.resumeBtn);


        timerSlider = findViewById(R.id.timerSlider);
        timerSlider.setMin(1);
        timerSlider.setMax(59);

        timerSlider.setProgress(25);
        timerClock = findViewById(R.id.timerClock);
        timerClock.setText(String.valueOf(timerSlider.getProgress()) + ":00");
        desiredCountDownInMillis=controller.convertMinutesToMilliseconds(25);

        timerSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                timerClock.setText(String.valueOf(i) + ":00");
                desiredCountDownInMillis=controller.convertMinutesToMilliseconds(i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        startBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.d("startbtn", "onClick: START CLICKED");
                    stopBtn.setVisibility(View.VISIBLE);
                    timerSlider.setVisibility(View.INVISIBLE);
                    breakBtn.setVisibility(View.VISIBLE);
                    startBtn.setVisibility(View.INVISIBLE);
                    Log.d("timerset", "onClick: " + desiredCountDownInMillis);

                //Build a notification channel
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    notificationChannel = new NotificationChannel("12345", "Proccoli", NotificationManager.IMPORTANCE_HIGH);
                    notificationChannel.enableLights(true);
                    notificationChannel.enableVibration(true);
                    notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                    notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.createNotificationChannel(notificationChannel);
                }

                //Create notification channel
                notificationChannel = new NotificationChannel("12345", "Proccoli", NotificationManager.IMPORTANCE_HIGH);
                notificationChannel.enableLights(true);
                notificationChannel.enableVibration(true);
                notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.createNotificationChannel(notificationChannel);

                //Alarm approach
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(timerView.this, NotificationPublisherTimer.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);


                //Get the time of when to set the alarm
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(System.currentTimeMillis());
                Calendar calTimer = Calendar.getInstance();
                calTimer.setTimeInMillis(cal.getTimeInMillis() + desiredCountDownInMillis);

                Log.d("calSet", "onClick: " + calTimer.get(Calendar.YEAR));
                Log.d("calSet", "onClick: " + calTimer.get(Calendar.MONTH));
                Log.d("calSet", "onClick: " + calTimer.get(Calendar.DATE));
                Log.d("calSet", "onClick: " + calTimer.get(Calendar.HOUR));
                Log.d("calSet", "onClick: " + calTimer.get(Calendar.MINUTE));
                Log.d("calSet", "onClick: " + calTimer.get(Calendar.SECOND));


                //Need to had 1900 snce the year returned from setReminder is picked-1900  = year
                //but in order for the alarm to properly go off, it needs have +1900 to have the actual year
                int year = calTimer.get(Calendar.YEAR);
                calTimer.set(Calendar.YEAR,year +1900);
                calTimer.set(Calendar.MONTH,calTimer.get(Calendar.MONTH));
                calTimer.set(Calendar.DATE, calTimer.get(Calendar.DATE));
                calTimer.set(Calendar.HOUR_OF_DAY, calTimer.get(Calendar.HOUR));  // set hour
                calTimer.set(Calendar.MINUTE, calTimer.get(Calendar.MINUTE));          // set minute
                calTimer.set(Calendar.SECOND, calTimer.get(Calendar.SECOND));               // set seconds

                //Create the alarm
                Toast toast = Toast.makeText(getBaseContext(), "Created an alarm", Toast.LENGTH_LONG);
                toast.show();
                selectedStudiedTime = desiredCountDownInMillis;

                studyId = getAlphaNumericString(11);
                TimerDataModel startData = new TimerDataModel(studyId,System.currentTimeMillis()/100L,0,false,desiredCountDownInMillis/100L);
                DataServices.getInstance().startTimerIndividualGoal(goalId,selectedSubGoalId,studyId,startData);


                timer = new CountDownTimer(desiredCountDownInMillis,1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            displayTime = desiredCountDownInMillis-counter;
                            timerClock.setText(controller.convertMilliSecondsToTimerDisplay(displayTime));
                            counter=counter+1000;
                        }
                        @Override
                        public void onFinish() {
                            Log.d("timer finished", "onFinish: The timer has finished");
                            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),pendingIntent);
                            timerClock.setText("0:00");
                            stopBtn.setVisibility(View.INVISIBLE);
                            breakBtn.setVisibility(View.INVISIBLE);
                            startBtn.setVisibility(View.VISIBLE);
                            studiedTime = selectedStudiedTime;


                            DataServices.getInstance().finishTimerForIndividual(goalId,selectedSubGoalId,studyId,studiedTime,prepareFinishData());

                            Intent i = new Intent(timerView.this, smileyFaceSurveyView.class);
                            //Launch activity to get smileFace Survey
                            intent.putExtra("questionType",ss.FACE_QUESTION_TYPE_REF_FOR_TIMER);
                            intent.putExtra("isGroupStudy",false);
                            intent.putExtra("goalId",goalId);
                            intent.putExtra("selectedSubGoalId",selectedSubGoalId);


                            activityResultLaunchSmileyRating.launch(i);
                        }
                    }.start();

            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.cancel();
                //Cancel the alarm
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent myIntent = new Intent(getApplicationContext(), NotificationPublisherTimer.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        getApplicationContext(), 1, myIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                alarmManager.cancel(pendingIntent);
                //Get time that they did study
                studiedTime = selectedStudiedTime - displayTime;

                DataServices.getInstance().stopTimerForIndividualGoal(goalId,selectedSubGoalId,studyId, prepareStopData(studiedTime),studiedTime);
                Intent myIntent2 = new Intent(timerView.this, singleGoalView.class);
                myIntent2.putExtra("studiedTime",Integer.toString(studiedTime));
                Log.d("StopStudyTime", "onClick: Timer stopped, passing studied time " + studiedTime);
                setResult(RESULT_OK,myIntent2);
                finish();

            }
        });

        breakBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                desiredCountDownInMillis = counter;
                counter = 0;
                timer.cancel();
                resumeBtn.setVisibility(View.VISIBLE);
                breakBtn.setVisibility(View.INVISIBLE);

                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent myIntent = new Intent(getApplicationContext(), NotificationPublisherTimer.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        getApplicationContext(), 1, myIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                alarmManager.cancel(pendingIntent);
                DataServices.getInstance().breakTimerForIndividualGoal(goalId,selectedSubGoalId,studyId,prepareBreakData());

            }
        });
        resumeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    notificationChannel = new NotificationChannel("12345", "Proccoli", NotificationManager.IMPORTANCE_HIGH);
                    notificationChannel.enableLights(true);
                    notificationChannel.enableVibration(true);
                    notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                    notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.createNotificationChannel(notificationChannel);
                }

                //Create notification channel
                notificationChannel = new NotificationChannel("12345", "Proccoli", NotificationManager.IMPORTANCE_HIGH);
                notificationChannel.enableLights(true);
                notificationChannel.enableVibration(true);
                notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.createNotificationChannel(notificationChannel);

                //Alarm approach
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(timerView.this, NotificationPublisherTimer.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                //Get the time of when to set the alarm
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(System.currentTimeMillis());
                Calendar calTimer = Calendar.getInstance();
                calTimer.setTimeInMillis(cal.getTimeInMillis() + desiredCountDownInMillis);

                Log.d("calSet", "onClick: " + calTimer.get(Calendar.YEAR));
                Log.d("calSet", "onClick: " + calTimer.get(Calendar.MONTH));
                Log.d("calSet", "onClick: " + calTimer.get(Calendar.DATE));
                Log.d("calSet", "onClick: " + calTimer.get(Calendar.HOUR));
                Log.d("calSet", "onClick: " + calTimer.get(Calendar.MINUTE));
                Log.d("calSet", "onClick: " + calTimer.get(Calendar.SECOND));

                //Need to had 1900 snce the year returned from setReminder is picked-1900  = year
                //but in order for the alarm to properly go off, it needs have +1900 to have the actual year
                int year = calTimer.get(Calendar.YEAR);
                calTimer.set(Calendar.YEAR,year +1900);
                calTimer.set(Calendar.MONTH,calTimer.get(Calendar.MONTH));
                calTimer.set(Calendar.DATE, calTimer.get(Calendar.DATE));
                calTimer.set(Calendar.HOUR_OF_DAY, calTimer.get(Calendar.HOUR));  // set hour
                calTimer.set(Calendar.MINUTE, calTimer.get(Calendar.MINUTE));          // set minute
                calTimer.set(Calendar.SECOND, calTimer.get(Calendar.SECOND));               // set seconds

                //Create the alarm
                //alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calTimer.getTimeInMillis(),pendingIntent);
                Toast toast = Toast.makeText(getBaseContext(), "Created an alarm", Toast.LENGTH_LONG);
                toast.show();

                stopBtn.setVisibility(View.VISIBLE);
                timerSlider.setVisibility(View.INVISIBLE);
                breakBtn.setVisibility(View.VISIBLE);
                startBtn.setVisibility(View.INVISIBLE);
                Log.d("timerset", "onClick: " + desiredCountDownInMillis);


                desiredCountDownInMillis =displayTime;
                selectedStudiedTime = desiredCountDownInMillis;
                DataServices.getInstance().resumeTimerForIndividualGoal(goalId,selectedSubGoalId,studyId,prepareResumeData());

                counter =0;
                timer = new CountDownTimer(desiredCountDownInMillis,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        displayTime = desiredCountDownInMillis-counter;
                        timerClock.setText(controller.convertMilliSecondsToTimerDisplay((displayTime)));
                        counter=counter+1000;
                    }
                    @Override
                    public void onFinish() {
                        Log.d("timer finished", "onFinish: The timer has finished");
                        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),pendingIntent);
                        timerClock.setText("0:00");
                        stopBtn.setVisibility(View.INVISIBLE);
                        breakBtn.setVisibility(View.INVISIBLE);
                        startBtn.setVisibility(View.VISIBLE);
                        studiedTime = selectedStudiedTime;


                        DataServices.getInstance().finishTimerForIndividual(goalId,selectedSubGoalId,studyId,studiedTime,prepareFinishData());
                        //Launch activity to get smileFace Survey
                        intent.putExtra("questionType",ss.FACE_QUESTION_TYPE_REF_FOR_TIMER);
                        intent.putExtra("isGroupStudy",false);
                        intent.putExtra("goalId",goalId);
                        intent.putExtra("selectedSubGoalId",selectedSubGoalId);

                        Intent i = new Intent(timerView.this, smileyFaceSurveyView.class);
                        activityResultLaunchSmileyRating.launch(i);
                    }
                }.start();
            }
        });

    }

    public static void cancelNotification(Context ctx, int notifyId) {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) ctx.getSystemService(ns);
        nMgr.cancel(notifyId);
    }

    public HashMap<String,Object> prepareBreakData(){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put(ss.BREAK_TIME_REF, "TabbarVC.sharedInstance.timerClass.calculateStuiedTime()");
        hashMap.put(ss.CREATED_AT, System.currentTimeMillis()/100L);
        hashMap.put(ss.BREAK_LOCATION_REF, ss.NO_LOCATION_REF );
        return  hashMap;

    }
    public HashMap<String,Object> prepareStopData(double studiedTime){

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put(ss.CREATED_AT, System.currentTimeMillis()/100L);
        hashMap.put(ss.STOPED_STUYD_TIME_REF,studiedTime);
        hashMap.put(ss.STOP_LOCATION_REF,ss.NO_LOCATION_REF);
        return hashMap;
    }
    public HashMap<String,Object> prepareStopDataForAppTerminate(double studiedTime){

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put(ss.CREATED_AT, System.currentTimeMillis()/100L);
        hashMap.put(ss.STOPED_STUYD_TIME_REF,studiedTime);
        hashMap.put(ss.STOP_LOCATION_REF,ss.NO_LOCATION_REF);
        hashMap.put("reason", "app terminated");
        return hashMap;
    }
    public HashMap<String,Object> prepareStopDataForDifferentGoalSelectedFromProfileWhileTimerIsON(double studiedTime){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put(ss.CREATED_AT, System.currentTimeMillis()/100L);
        hashMap.put(ss.STOPED_STUYD_TIME_REF,studiedTime);
        hashMap.put(ss.STOP_LOCATION_REF,ss.NO_LOCATION_REF);
        hashMap.put("reason", "profile page different goal selection");
        return hashMap;
    }
    public HashMap<String,Object> prepareFinishData(){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put(ss.CREATED_AT, System.currentTimeMillis()/100L);
        hashMap.put(ss.FINISH_LOCATION_REF, ss.NO_LOCATION_REF);
        return hashMap;
    }
    public HashMap<String,Object> prepareResumeData(){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put(ss.RESUME_TIME_REF,"TabbarVC.sharedInstance.timerClass.calculateStuiedTime()");
        hashMap.put(ss.CREATED_AT, System.currentTimeMillis()/100L);
        hashMap.put(ss.RESUME_LOCATION_REF, ss.NO_LOCATION_REF);

        return  hashMap;
    }

    public static String getAlphaNumericString(int n)
    {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

}
