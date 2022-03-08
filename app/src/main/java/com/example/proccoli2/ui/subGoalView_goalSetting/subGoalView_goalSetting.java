package com.example.proccoli2.ui.subGoalView_goalSetting;

import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proccoli2.GoalModel;
import com.example.proccoli2.NewModels.DataServices;
import com.example.proccoli2.NewModels.IndividualGoalModel;
import com.example.proccoli2.NewModels.IndividualSubGoalStructModel;
import com.example.proccoli2.R;
import com.example.proccoli2.SubGoalModel;
import com.example.proccoli2.ui.goalSetting.goalSettingView;
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.util.Date;

/**
 * Class used to edit a subgoal that has already been previously created
 * This is used in the goal setting edit page when the pencil is clicked on a certain subgoal
 * It first fills in the current fields and then updates the fields if they are changed
 * The changes are then sent  back to the goalSetting view
 */
public class subGoalView_goalSetting extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    String[] subGoalDifficultyLevels = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    String [] subGoalHowLongTimes = {
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
    Context context = this;
    TextView subGoalCompleteBy, subGoalStartDate, bigGoalCompleteByLabel,subGoalLevel, subGoalHowLong;
    TextInputEditText subGoalGoal;
    Button saveBtn;
    ImageButton exitSubGoalBtn;
    subGoalCreation_goalSetting_VC controller = new subGoalCreation_goalSetting_VC(this);

    NumberPicker difficultLevelPicker, howLongPicker;
    SingleDateAndTimePicker deadlinePicker, startPicker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("addSubbySetting", "onCreate: SUBGOALSETTING ADD ME");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subgoal_view);

        subGoalGoal = findViewById(R.id.subGoalInput);

        subGoalCompleteBy = findViewById(R.id.subGoalDeadlineInput);
        subGoalStartDate = findViewById(R.id.subGoalStartDate);
        subGoalLevel = findViewById(R.id.subGoalDifficultyLevelInput);
        subGoalHowLong = findViewById(R.id.subGoalHowLongInput);
        saveBtn = findViewById(R.id.saveSubgoalBtn);
        exitSubGoalBtn= findViewById(R.id.exitSubgoalBtn);


        //Initialize input pickers
        difficultLevelPicker = findViewById(R.id.subDifficultyLevelPicker);
        howLongPicker = findViewById(R.id.subHowLongPicker);
        deadlinePicker = findViewById(R.id.subCompletePicker);
        startPicker = findViewById(R.id.subStartPicker);

        //Make all input pickers invisible
        difficultLevelPicker.setVisibility(View.INVISIBLE);
        howLongPicker.setVisibility(View.INVISIBLE);
        deadlinePicker.setVisibility(View.INVISIBLE);
        startPicker.setVisibility(View.INVISIBLE);

        //Initialize howLongPicker
        howLongPicker.setMinValue(0);
        howLongPicker.setMaxValue(subGoalHowLongTimes.length-1);
        howLongPicker.setDisplayedValues(subGoalHowLongTimes);
        howLongPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                Log.d("Picked", "onClick: " +subGoalHowLongTimes[howLongPicker.getValue()]);
                subGoalHowLong.setText(subGoalHowLongTimes[howLongPicker.getValue()]);
            }
        });

        subGoalHowLong.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.d("goalType", "onClick: I am looking at goalType");
                //   openGoalPicker();
                closeKeyboard(view);
                difficultLevelPicker.setVisibility(View.INVISIBLE);
                deadlinePicker.setVisibility(View.INVISIBLE);
                startPicker.setVisibility(View.INVISIBLE);

                howLongPicker.setVisibility(View.VISIBLE);

            }
        });


        //Initialize difficulty level picker
        difficultLevelPicker.setMinValue(0);
        difficultLevelPicker.setMaxValue(subGoalDifficultyLevels.length-1);
        difficultLevelPicker.setDisplayedValues(subGoalDifficultyLevels);

        difficultLevelPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                Log.d("Picked", "onClick: " +subGoalDifficultyLevels[difficultLevelPicker.getValue()]);
                subGoalLevel.setText(subGoalDifficultyLevels[difficultLevelPicker.getValue()]);
            }
        });

        subGoalLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard(view);
                deadlinePicker.setVisibility(View.INVISIBLE);
                startPicker.setVisibility(View.INVISIBLE);
                howLongPicker.setVisibility(View.INVISIBLE);

                difficultLevelPicker.setVisibility(View.VISIBLE);
                Log.d("subGoalLevel", "onClick: I am looking at subgoalLevel");

            }
        });

        subGoalCompleteBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard(view); //Closes keyboard if it is open
                subGoalGoal.setCursorVisible(false);
                Log.d("I was clicked", "onClick: ");

                startPicker.setVisibility(View.INVISIBLE);
                howLongPicker.setVisibility(View.INVISIBLE);
                difficultLevelPicker.setVisibility(View.INVISIBLE);

                deadlinePicker.setVisibility(View.VISIBLE);
            }
        });

        deadlinePicker.addOnDateChangedListener(new SingleDateAndTimePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(String s, Date date) {
                Log.d("deadlinePicker", "onScrollChange: ");
                subGoalCompleteBy.setText(controller.dateToStr(deadlinePicker.getDate()));

            }
        });

        startPicker.addOnDateChangedListener(new SingleDateAndTimePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(String s, Date date) {
                subGoalStartDate.setText(controller.dateToStr(startPicker.getDate()));

            }
        });
        subGoalStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subGoalGoal.setCursorVisible(false);
                closeKeyboard(view); //Closes keyboard if it is open
                Log.d("I was clicked", "onClick: ");
                difficultLevelPicker.setVisibility(View.INVISIBLE);
                howLongPicker.setVisibility(View.INVISIBLE);
                deadlinePicker.setVisibility(View.INVISIBLE);

                startPicker.setVisibility(View.VISIBLE);
            }
        });

        /*
        Bundle bundle = getIntent().getExtras();
        String completeByGoal= bundle.getString("completeBy");

         */
        Bundle bundle = getIntent().getExtras();
        IndividualGoalModel passedGoal = (IndividualGoalModel) bundle.getSerializable("bigGoal");
        int passedCompleteBy = (int)passedGoal.getPersonalDeadline();

        bigGoalCompleteByLabel = findViewById(R.id.BigGoalCompleteByLabel);
        bigGoalCompleteByLabel.setText("Big Goal Deadline " + controller.unixToStringDateTime(passedCompleteBy));

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("clickedGoalSetting", "onClick:save ");
                closeKeyboard(view);
                boolean null0 = false;
                boolean null1 = false;
                boolean null2 = false;
                boolean null3 = false;
                boolean null4 = false;
                if(null0 = controller.nullFieldCheck(subGoalGoal.getText().toString(),"Please enter a goal",context,subGoalGoal)) {
                    subGoalGoal.setTextColor(Color.rgb(128,128,128));
                    if(null1 = controller.nullFieldCheck(subGoalCompleteBy.getText().toString(),"Please enter a deadline", context,subGoalCompleteBy)){
                        if (null2 = controller.nullFieldCheck(subGoalHowLong.getText().toString(), "Please select a time length", context, subGoalHowLong)) {
                            subGoalHowLong.setTextColor(Color.rgb(128,128,128));
                            if (null3 = controller.nullFieldCheck(subGoalLevel.getText().toString(), "Please select a difficulty level", context, subGoalLevel)) {
                                subGoalLevel.setTextColor(Color.rgb(128,128,128));
                                if (null4 = controller.nullFieldCheck(subGoalStartDate.getText().toString(), "Please select a start date", context, subGoalStartDate)) {
                                    subGoalStartDate.setTextColor(Color.rgb(128,128,128));

                                    Log.d("Date", "compareDatesSubgoal: " + subGoalStartDate.getText().toString() + subGoalCompleteBy.getText().toString());
                                    try {
                                        if(controller.compareDates(subGoalStartDate.getText().toString(),subGoalCompleteBy.getText().toString(),context,controller.unixToStringDateTime(passedCompleteBy))){
                                            Log.d("Success", "onClick: ran");
                                            subGoalCompleteBy.setBackgroundColor(Color.WHITE);
                                            subGoalStartDate.setBackgroundColor(Color.WHITE);

                                            subGoalCompleteBy.setTextColor(Color.rgb(128,128,128));
                                            subGoalStartDate.setTextColor(Color.rgb(128,128,128));

                                            int uComplete = controller.dateStrToUnix(subGoalCompleteBy.getText().toString());
                                            int uStart = controller.dateStrToUnix(subGoalStartDate.getText().toString());
                                            int formatedLevel = Integer.parseInt(subGoalLevel.getText().toString());
                                            long formatedHowLong = Long.parseLong(subGoalHowLong.getText().toString().split("")[0]);
                                            long currentUnix = System.currentTimeMillis() / 1000L;

                                            Log.d("Dates confirmed", "onClick: Passed all date conversions");


                                            //SubGoalModel subgoal = new SubGoalModel(subGoalID, subGoalGoal.getText().toString(), uComplete, formatedLevel,formatedHowLong,uStart,currentUnix;
                                            String subGoalID = DataServices.getInstance().getAlphaNumericString(11);
                                            IndividualSubGoalStructModel subgoal = new IndividualSubGoalStructModel(subGoalID, subGoalGoal.getText().toString(),uComplete, formatedLevel, (int)formatedHowLong,false,uStart,0,false);

                                            Log.d("SubMade", "onClick: " + subgoal.toString());

                                            IndividualGoalModel passedGoal = (IndividualGoalModel) getIntent().getSerializableExtra("bigGoal");
                                            passedGoal.getSubGoals().add(subgoal);
                                            DataServices.getInstance().addNewSubGoal(subgoal,passedGoal.getGoalId());

                                            Intent i = new Intent(subGoalView_goalSetting.this, goalSettingView.class);
                                            // i.putExtra("subGoal", subgoal);
                                            i.putExtra("bigGoal",passedGoal);
                                            // startActivity(i);
                                            Log.d("putExtrasSetting", "onClick: " + passedGoal);
                                            setResult(RESULT_OK,i);
                                            Log.d("setExtrasSetting", "onClick: " + passedGoal);
                                            finish();

                                        }
                                        else{
                                            Toast toast = Toast.makeText(getBaseContext(), "At least one date value is invalid.", Toast.LENGTH_LONG);
                                            toast.show();

                                            subGoalCompleteBy.setTextColor(Color.RED);
                                            subGoalStartDate.setTextColor(Color.RED);
                                        }

                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });

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

    public void closeKeyboard(View view){
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


}
