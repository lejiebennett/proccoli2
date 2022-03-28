package com.example.proccoli2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proccoli2.ui.home.individualWall.singleGoalView;

import java.util.Arrays;

public class progressReportView extends AppCompatActivity{

    Button cancelBtn, submitBtn;
    TextView pickerPercentageLabel, subGoalLabel;
    Context context = this;
    NumberPicker percentPicker;

    //All possible percentages
    String [] percentage = {
            "1%", "2%", "3%", "4%", "5%", "6%", "7%", "8%", "9%", "10%",
            "11%", "12%", "13%", "14%", "15%", "16%", "17%", "18%", "19%", "20%",
            "21%", "22%", "23%", "24%", "25%", "26%", "27%", "28%", "29%", "30%",
            "31%", "32%", "33%", "34%", "35%", "36%", "37%", "38%", "39%", "40%",
            "41%", "42%", "43%", "44%", "45%", "46%", "47%", "48%", "49%", "50%",
            "51%", "52%", "53%", "54%", "55%", "56%", "57%", "58%", "59%", "60%",
            "61%", "62%", "63%", "64%", "65%", "66%", "67%", "68%", "69%", "70%",
            "71%", "72%", "73%", "74%", "75%", "76%", "77%", "78%", "79%", "80%",
            "81%", "82%", "83%", "84%", "85%", "86%", "87%", "88%", "89%", "90%",
            "91%", "92%", "93%", "94%", "95%", "96%", "97%", "98%", "99%", "100%"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reportprogresspercentage_popup);
        Bundle bundle = getIntent().getExtras();
        String subgoalText = (String) bundle.getSerializable("subgoalText");
        subGoalLabel = findViewById(R.id.reportProgressSubgoalLabel);
        subGoalLabel.setText(subgoalText);

        //Get current Percentage complete, used to set the picker range
        int currentPercentageComplete = Integer.parseInt((String)bundle.getSerializable("currentPercentageComplete"));
        String [] subarrayPercentage = Arrays.copyOfRange(percentage,currentPercentageComplete,percentage.length);

        //Set up picker
        pickerPercentageLabel = findViewById(R.id.percentageLabel);
        pickerPercentageLabel.setText(subarrayPercentage[0]);
        percentPicker = findViewById(R.id.percentPicker);
        percentPicker.setMinValue(0);
        percentPicker.setMaxValue(subarrayPercentage.length-1);
        percentPicker.setDisplayedValues(subarrayPercentage);

        //When picker is changed, update the picker label
        percentPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                pickerPercentageLabel.setText(subarrayPercentage[percentPicker.getValue()]);
            }
        });

        submitBtn = findViewById(R.id.submitPercentBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int reportedProgress = Integer.parseInt(subarrayPercentage[percentPicker.getValue()].substring(0, subarrayPercentage[percentPicker.getValue()].length() - 1));

                Log.d("Picked", "onClick: " + subarrayPercentage[percentPicker.getValue()] + " int version:" + reportedProgress);


                //Pas new percentage complete back to singleGoal view so it can be subtracted
                Intent i = new Intent(progressReportView.this, singleGoalView.class);
                // i.putExtra("subGoal", subgoal);
                i.putExtra("pickedPercentage",reportedProgress);
                Log.d("putExtras", "onClick: " + reportedProgress);
                setResult(RESULT_OK,i);
                Log.d("setExtras", "onClick: " + reportedProgress);
                finish();
            }
        });

            cancelBtn = findViewById(R.id.cancelPercentBtn);
            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

        }
}