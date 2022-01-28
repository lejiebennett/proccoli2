package com.example.proccoli2;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.slider.Slider;

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
        timerSlider.setMax(50);

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
                            timerClock.setText("0:00");
                        }
                    }.start();

            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.cancel();
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
            }
        });
        resumeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                    }
                }.start();

            }
        });







    }

}
