package com.example.proccoli2;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
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
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.example.proccoli2.databinding.SmileyfacesurveyViewBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.slider.Slider;

import java.util.Calendar;
import java.util.Date;

public class timerView extends AppCompatActivity {

    SeekBar timerSlider;
    MaterialButton startBtn, stopBtn, breakBtn, resumeBtn;
    TextView timerClock;
    int counter;
    timer_VC controller = new timer_VC(this);
    CountDownTimer timer;
    int desiredCountDownInMillis;
    int displayTime; //Time that is displayed on the countdown timer
    Toolbar toolbar;
    int smileRating;
    int selectedStudiedTime; //Time the user selected for the timer
    int studiedTime; //Time that the user actually studied. This is sent back to the singleGoalView

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
                        //NEED TO ADD A NEW ATTRIBUTE TO GOAL MODAL TO HOLD SMILE RATING

                        Intent myIntent = new Intent(timerView.this, singleGoalView.class);
                        myIntent.putExtra("smileRating",Integer.toString(smileRating));
                        myIntent.putExtra("studiedTime",Integer.toString(studiedTime));
                        setResult(RESULT_OK,myIntent);
                        finish();
                        //recreate();
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

                            Intent i = new Intent(timerView.this, smileyFaceSurveyView.class);
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
}
