package com.lejiebennett.proccoli2.ui.home.groupGoalWall.groupGoalSubGoalCreation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lejiebennett.proccoli2.R;
import com.lejiebennett.proccoli2.oldModels.GoalModel;
import com.lejiebennett.proccoli2.oldModels.SubGoalModel;
import com.lejiebennett.proccoli2.ui.home.groupGoalWall.groupgoalsingleGoalView;
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.util.Date;

/**
 * Copyright © 2022 Le Jie Bennett. All rights reserved.
 * Needs to be integrated with firebase, this version of the file is prior to firebase integration
 * This class is for when a user clicks on the admin plus button to create a new subgoal
 * on a group goal.
 * Layout: R.layout.groupgoalsubgoal_view
 * Data Needs to be sent back to the single Group goal view to add the new subgoal
 */
public class groupgoalsubGoalView extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    String[] subGoalDifficultyLevels = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    String [] subGoalHowLongHours = {"hours",
            "1 hour", "2 hours", "3 hours", "4 hours", "5 hours", "6 hours", "7 hours", "8 hours", "9 hours", "10 hours",
            "11 hour", "12 hours", "13 hours", "14 hours", "15 hours", "16 hours", "17 hours", "18 hours", "19 hours", "20 hours",
            "21 hour", "22 hours", "23 hours", "24 hours", "5 hours", "26 hours", "27 hours", "28 hours", "29 hours", "30 hours",
            "31 hour", "32 hours", "33 hours", "34 hours", "35 hours", "36 hours", "37 hours", "38 hours", "39 hours", "40 hours",
            "41 hour", "42 hours", "43 hours", "44 hours", "45 hours", "46 hours", "47 hours", "48 hours", "49 hours", "50 hours",
            "51 hour", "52 hours", "53 hours", "54 hours", "55 hours", "56 hours", "57 hours", "58 hours", "59 hours", "60 hours",
            "61 hour", "62 hours", "63 hours", "64 hours", "65 hours", "66 hours", "67 hours", "68 hours", "69 hours", "70 hours",
            "71 hour", "72 hours", "73 hours", "74 hours", "75 hours", "76 hours", "77 hours", "78 hours", "79 hours", "80 hours",
            "81 hour", "82 hours", "83 hours", "84 hours", "85 hours", "86 hours", "87 hours", "88 hours", "89 hours", "90 hours",
            "91 hour", "92 hours", "93 hours", "94 hours", "95 hours", "96 hours", "97 hours", "98 hours", "99 hours", "100 hours"
    };

    String [] subGoalHowLongMinutes = { "minutes",
            "1 minutes", "2 minutes", "3 minutes", "4 minutes", "5 minutes", "6 minutes", "7 minutes", "8 minutes", "9 minutes", "10 minutes",
            "11 minutes", "12 minutes", "13 minutes", "14 minutes", "15 minutes", "16 minutes", "17 minutes", "18 minutes", "19 minutes", "20 minutes",
            "21 minutes", "22 minutes", "23 minutes", "24 minutes", "5 minutes", "26 minutes", "27 minutes", "28 minutes", "29 minutes", "30 minutes",
            "31 minutes", "32 minutes", "33 minutes", "34 minutes", "35 minutes", "36 minutes", "37 minutes", "38 minutes", "39 minutes", "40 minutes",
            "41 minutes", "42 minutes", "43 minutes", "44 minutes", "45 minutes", "46 minutes", "47 minutes", "48 minutes", "49 minutes", "50 minutes",
            "51 minutes", "52 minutes", "53 minutes", "54 minutes", "55 minutes", "56 minutes", "57 minutes", "58 minutes", "59 minutes"
    };
    Context context = this;
    TextView subGoalHowLongLabel;
    TextView subGoalCompleteBy;
    TextInputEditText  subGoalGoal;
    Button saveBtn;
    ImageButton exitSubGoalBtn;
    groupgoalsubGoalCreation_Controller controller = new groupgoalsubGoalCreation_Controller(this);
    RadioGroup radioGroup;
    RadioButton assignToSelf,assignToGroup;

    TextView subGoalHowLong;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groupgoalsubgoal_view);


        subGoalGoal = findViewById(R.id.subGoalInputGG);
        subGoalHowLongLabel = findViewById(R.id.HowLongLabelGG);


        subGoalCompleteBy = findViewById(R.id.subGoalDeadlineInputGG);
        subGoalCompleteBy.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                closeKeyboard(view);
                subGoalGoal.setCursorVisible(false);
                Log.d("I was clicked", "onClick: ");
                new SingleDateAndTimePickerDialog.Builder(context)
                        .displayListener(new SingleDateAndTimePickerDialog.DisplayListener() {
                            @Override
                            public void onDisplayed(SingleDateAndTimePicker picker) {
                                // Retrieve the SingleDateAndTimePicker
                            }

                            //@Override
                            public void onClosed(SingleDateAndTimePicker picker) {
                                // On dialog closed
                            }
                        })
                        .title("Subgoal Complete By")
                        .listener(new SingleDateAndTimePickerDialog.Listener() {
                            @Override
                            public void onDateSelected(Date date) {
                              //  subGoalCompleteBy.setText(date.toString());
                                subGoalCompleteBy.setText(controller.dateToStr(date));

                            }
                        }).display();

            }
        });


        subGoalHowLong = findViewById(R.id.subGoalHowLongInputGG);
        subGoalHowLong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("subGoalLevel", "onClick: I am looking at subgoalHowLong");
                openHowLongPicker();
            }
        });

        radioGroup = findViewById(R.id.radioAssignment);
        assignToGroup = findViewById(R.id.makeAvailableRadioGG);
        assignToSelf = findViewById(R.id.assignToSelfRadioGG);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = radioGroup.getCheckedRadioButtonId();
                switch(id){
                    case R.id.assignToSelfRadioGG:
                        subGoalHowLong.setVisibility(View.VISIBLE);
                        subGoalHowLongLabel.setVisibility(View.VISIBLE);
                        break;
                    case R.id.makeAvailableRadioGG:
                        subGoalHowLong.setVisibility(View.INVISIBLE);
                        subGoalHowLongLabel.setVisibility(View.INVISIBLE);
                        break;
                }
            }
        });


        saveBtn = findViewById(R.id.saveSubgoalBtnGG);

        GoalModel passedGoal = (GoalModel) getIntent().getSerializableExtra("bigGoal");

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (assignToSelf.isChecked()) {
                    Log.d("I was clicked", "onClick: ");
                    boolean null0 = false;
                    boolean null1 = false;
                    boolean null2 = false;

                    if (null0 = controller.nullFieldCheck(subGoalGoal.getText().toString(), "Please enter a goal", context, subGoalGoal)) {
                        subGoalGoal.setTextColor(Color.rgb(128, 128, 128));
                        if (null1 = controller.nullFieldCheck(subGoalCompleteBy.getText().toString(), "Please enter a deadline", context, subGoalCompleteBy)) {
                            if (null2 = controller.nullFieldCheck(subGoalHowLong.getText().toString(), "Please select a time length", context, subGoalHowLong)) {
                                subGoalHowLong.setTextColor(Color.rgb(128, 128, 128));
                                Log.d("Date", "compareDatesSubgoal: "+ subGoalCompleteBy.getText().toString());
                                try {
                                    if (controller.compareDates(subGoalCompleteBy.getText().toString(), context, controller.unixToStringDateTime(passedGoal.getCompletedBy()))) {
                                        Log.d("Success", "onClick: ran");
                                        subGoalCompleteBy.setBackgroundColor(Color.WHITE);

                                        subGoalCompleteBy.setTextColor(Color.rgb(128, 128, 128));

                                        int uComplete = controller.dateStrToUnix(subGoalCompleteBy.getText().toString());

                                        String formatedHowLong = subGoalHowLong.getText().toString();
                                        long currentUnix = System.currentTimeMillis() / 1000L;

                                        Log.d("Dates confirmed", "onClick: Passed all date conversions");

                                        /**
                                         * ASK IF WE USE THE SAME SUBGOAL AND GROUP GOAL MODEL FOR GORUP GOALS AND SUBGOALS
                                         */
                                        SubGoalModel subgoal = new SubGoalModel("SUBGOALID###",subGoalGoal.getText().toString(),uComplete,"selfID",formatedHowLong,(int) currentUnix);

                                        Log.d("SubMade", "onClick: " + subgoal.toString());

                                        passedGoal.getSubGoals().add(subgoal);

                                        Intent i = new Intent(groupgoalsubGoalView.this, groupgoalsingleGoalView.class);
                                        // i.putExtra("subGoal", subgoal);
                                        i.putExtra("bigGoal", passedGoal);
                                        // startActivity(i);
                                        Log.d("putExtras", "onClick: " + passedGoal);
                                        setResult(RESULT_OK, i);
                                        Log.d("setExtras", "onClick: " + passedGoal);
                                        finish();

                                    } else {
                                        Toast toast = Toast.makeText(getBaseContext(), "At least one date value is invalid.", Toast.LENGTH_LONG);
                                        toast.show();

                                        subGoalCompleteBy.setTextColor(Color.RED);
                                    }

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
                else{
                    Log.d("I was clicked", "onClick: ");
                    boolean null0 = false;
                    boolean null1 = false;
                    if (null0 = controller.nullFieldCheck(subGoalGoal.getText().toString(), "Please enter a goal", context, subGoalGoal)) {
                        subGoalGoal.setTextColor(Color.rgb(128, 128, 128));
                        if (null1 = controller.nullFieldCheck(subGoalCompleteBy.getText().toString(), "Please enter a deadline", context, subGoalCompleteBy)) {
                            Log.d("Date", "compareDatesSubgoal: "+ subGoalCompleteBy.getText().toString());
                            try {
                                if (controller.compareDates(subGoalCompleteBy.getText().toString(), context,  controller.unixToStringDateTime(passedGoal.getCompletedBy()))) {
                                    Log.d("Success", "onClick: ran");
                                    subGoalCompleteBy.setBackgroundColor(Color.WHITE);

                                    subGoalCompleteBy.setTextColor(Color.rgb(128, 128, 128));

                                    int uComplete = controller.dateStrToUnix(subGoalCompleteBy.getText().toString());

                                    long currentUnix = System.currentTimeMillis() / 1000L;

                                    Log.d("Dates confirmed", "onClick: Passed all date conversions");

                                    /**
                                     * ASK IF WE USE THE SAME SUBGOAL AND GROUP GOAL MODEL FOR GORUP GOALS AND SUBGOALS
                                     */
                                    SubGoalModel subgoal = new SubGoalModel("SUBGOALID###",subGoalGoal.getText().toString(),uComplete, "available",(int)currentUnix);

                                    passedGoal.getSubGoals().add(subgoal);
                                    Log.d("SubMade", "onClick: " + subgoal.toString());

                                    Intent i = new Intent(groupgoalsubGoalView.this, groupgoalsingleGoalView.class);
                                    // i.putExtra("subGoal", subgoal);
                                    i.putExtra("bigGoal", passedGoal);
                                    // startActivity(i);
                                    Log.d("putExtras", "onClick: " + passedGoal);
                                    setResult(RESULT_OK, i);
                                    Log.d("setExtras", "onClick: " + passedGoal);
                                    finish();

                                } else {
                                    Toast toast = Toast.makeText(getBaseContext(), "At least one date value is invalid.", Toast.LENGTH_LONG);
                                    toast.show();
                                    subGoalCompleteBy.setTextColor(Color.RED);
                                }

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

            }
        });



        exitSubGoalBtn= findViewById(R.id.exitSubgoalBtnGG);
        exitSubGoalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("I was clicked", "onClick: ");
                finish();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("subGoalLevel selected", "onItemSelected: " + subGoalDifficultyLevels[i]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void openHowLongPicker(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View view = this.getLayoutInflater().inflate(R.layout.goaltwowheels, null);
        builder.setView(view);
        //builder.setTitle();

        //Sets up Hour Picker
        final NumberPicker hourPicker = (NumberPicker) view.findViewById(R.id.hourPicker);
        hourPicker.setMinValue(0);
        hourPicker.setMaxValue(subGoalHowLongHours.length-1);
        hourPicker.setDisplayedValues(subGoalHowLongHours);

        //Sets up Minute Picker
        final NumberPicker minutePicker = (NumberPicker) view.findViewById(R.id.minutePicker);
        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(subGoalHowLongMinutes.length-1);
        minutePicker.setDisplayedValues(subGoalHowLongMinutes);


        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //positive button action

                Log.d("Picked", "onClick: " + subGoalHowLongHours[hourPicker.getValue()]);

                //If user selects "minutes" and "hours"
                if(subGoalHowLongHours[hourPicker.getValue()].equals("hours") == true && subGoalHowLongMinutes[minutePicker.getValue()].equals("minutes") == true ){
                    Toast toast = Toast.makeText(context, "Please select a time duration", Toast.LENGTH_LONG);
                    toast.show();
                }
                else {
                    //If "hours is selected" then only minutes should show
                    if(subGoalHowLongHours[hourPicker.getValue()].equals("hours") == true){
                        subGoalHowLong.setText(subGoalHowLongMinutes[minutePicker.getValue()]);

                    }
                    //If "minutes is selected" then only hours should show
                    else if(subGoalHowLongMinutes[minutePicker.getValue()].equals("minutes") == true){
                        subGoalHowLong.setText(subGoalHowLongHours[hourPicker.getValue()]);
                    }
                    else{
                        subGoalHowLong.setText(subGoalHowLongHours[hourPicker.getValue()] + " " + subGoalHowLongMinutes[minutePicker.getValue()]);
                    }

                }
            }
        })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //negative button action
                    }
                });
        builder.create().show();
    }

    public void closeKeyboard(View view){
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
