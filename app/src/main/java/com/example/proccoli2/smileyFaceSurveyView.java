package com.example.proccoli2;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;

import java.util.Random;

public class smileyFaceSurveyView extends AppCompatActivity {

    ImageView face1, face2, face3, face4, face5;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smileyfacesurvey_view);
        face1 = findViewById(R.id.face1);
        face2 = findViewById(R.id.face2);
        face3 = findViewById(R.id.face3);
        face4 = findViewById(R.id.face4);
        face5 = findViewById(R.id.face5);

        face1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRating(1);
            }
        });

        face2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRating(2);
            }
        });

        face3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRating(3);
            }
        });

        face4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRating(4);
            }
        });

        face5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRating(5);
            }
        });


    }

    /**
     * Sets the goal completion feedback based on the face icon that was selected and sends it back to the singleGoalView
     * @param i The corresponding face rating that is to be set
     */
    public void setRating(int i){
        Intent myIntent = new Intent(this, singleGoalView.class);
        myIntent.putExtra("smileRating",Integer.toString(i));
        Log.d("setSmileRating", "smileRating: " + Integer.toString(i));
        setResult(RESULT_OK,myIntent);
        finish();

    }

}