package com.lejiebennett.proccoli2.UnusedFiles;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lejiebennett.proccoli2.ui.mainActivity.MainActivity;
import com.lejiebennett.proccoli2.R;
import com.lejiebennett.proccoli2.oldModels.GoalModel;
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;

import java.text.ParseException;
import java.util.Date;

public class timeReportView extends AppCompatActivity{


    GoalModel myGoal = new GoalModel();
    Button cancelBtn, submitBtn;
    TextView goalStopTime, goalStartTime;
    Context context = this;
    timeReport_VC controller = new timeReport_VC(this);
    GoalModel passedGoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timereport_view);

        Bundle bundle = getIntent().getExtras();
        passedGoal = (GoalModel) bundle.getSerializable("passedGoal");
        //Hardocded startDate to test function, must delete later
        passedGoal.setStartDate(1577979909);


        goalStopTime = findViewById(R.id.stopTimeInput);
        goalStopTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("I was clicked", "onClick: goalDueDate");
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
                        .title("Report Stop Time")
                        .listener(new SingleDateAndTimePickerDialog.Listener() {
                            @Override
                            public void onDateSelected(Date date) {
                                //goalStopTime.setText(date.toString());
                                goalStopTime.setText(controller.dateToStr(date));


                            }
                        }).display();

            }
        });

        /*
        Goal is not created or checked for errors until the createGoalBtn is clicked.
        This is where it will do nullFieldChecks and other logical checks.
        It also does the actual goal creation of the goal model.
         */
        submitBtn = findViewById(R.id.submitTimeReport);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("I was clicked", "onClick: createGoal");

                boolean null0 = false;
                boolean null1 = false;


                if (null0 = controller.nullFieldCheck(goalStartTime.getText().toString(), "Please enter a goal start time", context, goalStartTime)) {
                    goalStartTime.setHintTextColor(Color.rgb(128, 128, 128));
                    if (null1 = controller.nullFieldCheck(goalStopTime.getText().toString(), "Please enter a goal stop time", context, goalStopTime)) {
                        goalStopTime.setHintTextColor(Color.rgb(128, 128, 128));
                        ;
                        Log.d("Date", "compareDates: " + goalStartTime.getText().toString() + goalStopTime.getText().toString());
                        try {
                            if (controller.compareDates(controller.unixToStringDateTime(passedGoal.getStartDate()), goalStartTime.getText().toString(), goalStopTime.getText().toString(), context, timeReportView.this)) {
                                Log.d("Success", "onClick: ran");
                                goalStartTime.setHintTextColor(Color.rgb(128, 128, 128));
                                goalStopTime.setHintTextColor(Color.rgb(128, 128, 128));

                                goalStartTime.setTextColor(Color.rgb(128, 128, 128));
                                goalStopTime.setTextColor(Color.rgb(128, 128, 128));

                                int uStart = controller.dateStrToUnix(goalStartTime.getText().toString());
                                int uStop = controller.dateStrToUnix(goalStopTime.getText().toString());
                                long currentUnix = System.currentTimeMillis() / 1000L;

                                // GoalModel goal = new GoalModel(bigGoalInput.getText().toString(),uComplete,goalType.getText().toString(),uStart,uDue,(int)currentUnix,subgoals);
                                //myGoal.setStartDate(uStart);
                                Log.d("collected Start/stop", "ustart: " + uStart + "stop: " + uStop);

                                Intent i = new Intent(timeReportView.this, MainActivity.class);
                                i.putExtra("bigGoal", myGoal);
                                //  startActivity(i);
                                //  finish();
                                Log.d("putExtras", "onClick: " + myGoal);
                                setResult(RESULT_OK, i);
                                Log.d("setExtras", "onClick: " + myGoal);
                                finish();
                            } else {
                                Toast toast = Toast.makeText(getBaseContext(), "At least one date value is invalid.", Toast.LENGTH_LONG);
                                toast.show();

                                goalStartTime.setTextColor(Color.RED);
                                goalStopTime.setTextColor(Color.RED);
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        cancelBtn = findViewById(R.id.cancelTimeReport);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        goalStartTime = findViewById(R.id.startTimeInput);
        goalStartTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Log.d("I was clicked", "onClick: goalStartDate");
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
                        .title("Report Start Time")
                        .listener(new SingleDateAndTimePickerDialog.Listener() {
                                      @Override
                                      public void onDateSelected(Date date) {
                                        //  goalStartTime.setText(date.toString());
                                          goalStartTime.setText(controller.dateToStr(date));

                                      }
                                  }

                        ).display();
            }
        });

    }
}
