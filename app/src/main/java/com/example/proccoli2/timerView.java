package com.example.proccoli2;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
    int displayTime;
    Toolbar toolbar;

    //Set Timer
    NotificationChannel notificationChannel;
    NotificationManager notificationManager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_view);

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
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calTimer.getTimeInMillis(),pendingIntent);
                Toast toast = Toast.makeText(getBaseContext(), "Created an alarm", Toast.LENGTH_LONG);
                toast.show();

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
                        }
                    }.start();

            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.cancel();
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent myIntent = new Intent(getApplicationContext(), NotificationPublisherTimer.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        getApplicationContext(), 1, myIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                alarmManager.cancel(pendingIntent);

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
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calTimer.getTimeInMillis(),pendingIntent);
                Toast toast = Toast.makeText(getBaseContext(), "Created an alarm", Toast.LENGTH_LONG);
                toast.show();

                stopBtn.setVisibility(View.VISIBLE);
                timerSlider.setVisibility(View.INVISIBLE);
                breakBtn.setVisibility(View.VISIBLE);
                startBtn.setVisibility(View.INVISIBLE);
                Log.d("timerset", "onClick: " + desiredCountDownInMillis);


                desiredCountDownInMillis =displayTime;
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
                    }
                }.start();
            }
        });

    }

}
