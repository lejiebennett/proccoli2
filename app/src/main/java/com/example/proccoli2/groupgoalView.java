package com.example.proccoli2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class groupgoalView extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    String[] goalTypes = {"Assignment", "Discussion/Forum","Exam (Final)", "Exam (Midterm)",
            "Exam (Other)", "Group Project", "Project (Individual)", "Studying (Reading)", "Studying (Watching Videos)",
            "Studying (Other)", "Other"};

    GoalModel myGoal = new GoalModel();
    TextInputEditText bigGoalInput, courseNumberInput;
    Button createGoalBtn;
    TextView goalDueDate, goalType;
    Context context = this;
    groupgoalCreation_VC controller = new groupgoalCreation_VC(this);
    Toolbar toolbar;

    //Pickers used to get input
    SingleDateAndTimePicker deadlinePicker;
    NumberPicker goalTypePicker;



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groupgoal_view);


        bigGoalInput = findViewById(R.id.bigGoalInputGG);
        bigGoalInput.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                bigGoalInput.setCursorVisible(true);
            }
        });

        courseNumberInput = findViewById(R.id.courseNumberInputGG);
        courseNumberInput.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                courseNumberInput.setCursorVisible(true);
            }
        });

        toolbar = findViewById(R.id.toolbarGoalViewGG);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("toolbar clicked", "onClick: I was clicked");
                finish();
            }
        });

        //Initialize and hide pickers

        deadlinePicker = findViewById(R.id.groupDeadlinePickerGG);
        goalTypePicker = findViewById(R.id.goalTypePickerGG);
        deadlinePicker.setVisibility(View.INVISIBLE);
        goalTypePicker.setVisibility(View.INVISIBLE);


        //Goal Type Picker and goalType Label
        goalType = findViewById(R.id.goalTypePickedGG);
        goalTypePicker.setMinValue(0);
        goalTypePicker.setMaxValue(goalTypes.length-1);
        goalTypePicker.setDisplayedValues(goalTypes);
        goalType.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.d("goalType", "onClick: I am looking at goalType");
                closeKeyboard(view);
                goalTypePicker.setVisibility(View.VISIBLE);
                deadlinePicker.setVisibility(View.INVISIBLE);
                bigGoalInput.setCursorVisible(false);
                courseNumberInput.setCursorVisible(false);
            }
        });
        goalTypePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                Log.d("Picked", "onClick: " + goalTypes[goalTypePicker.getValue()]);
                goalType.setText(goalTypes[goalTypePicker.getValue()]);
            }
        });


        //Goal Due Date and deadline picker
        goalDueDate = findViewById(R.id.goalDueDatePickedGG);
        goalDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard(view);
                deadlinePicker.setVisibility(View.VISIBLE);
                goalTypePicker.setVisibility(View.INVISIBLE);
            }
        });
        deadlinePicker.addOnDateChangedListener(new SingleDateAndTimePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(String s, Date date) {
                Log.d("deadlinePicker", "onScrollChange: " + controller.dateToStr(deadlinePicker.getDate()));
                goalDueDate.setText(controller.dateToStr(deadlinePicker.getDate()));
            }
        });



        /*
        Goal is not created or checked for errors until the createGoalBtn is clicked.
        This is where it will do nullFieldChecks and other logical checks.
        It also does the actual goal creation of the goal model.
         */
        createGoalBtn = findViewById(R.id.createGoalBtnGG);
        createGoalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("I was clicked", "onClick: createGoal");
                closeKeyboard(view);
                //goalCompleteBy, goalStartDate, goalDueDate, goalType

                boolean null0 = false;
                boolean null1 = false;
                boolean null2 = false;
                boolean null3 = false;


                if(null0 = controller.nullFieldCheck(bigGoalInput.getText().toString(),"Please enter a goal",context,bigGoalInput)) {
                    goalType.setHintTextColor(Color.rgb(128,128,128));
                            if (null1 = controller.nullFieldCheck(goalDueDate.getText().toString(), "Please enter a goal due date", context, goalDueDate)) {
                                goalDueDate.setHintTextColor(Color.rgb(128,128,128));;
                                if (null2 = controller.nullFieldCheck(goalType.getText().toString(), "Please select a goal type", context, goalType)) {
                                    goalType.setHintTextColor(Color.rgb(128,128,128));
                                    if (null3 = controller.nullFieldCheck(courseNumberInput.getText().toString().toUpperCase(), "Please enter a course number", context, courseNumberInput)) {
                                        courseNumberInput.setHintTextColor(Color.rgb(128, 128, 128));
                                      //  Log.d("Date", "compareDates: " + goalStartDate.getText().toString() + goalCompleteBy.getText().toString() + goalDueDate.getText().toString()); DO NOT NEED FOR GROUP GOAL UI
                                          Log.d("Date", "compareDates: " + goalDueDate.getText().toString());

                                        try {
                                            if (controller.compareDates(goalDueDate.getText().toString(), context, groupgoalView.this)) {
                                                Log.d("Success", "onClick: ran");
                                               // goalCompleteBy.setHintTextColor(Color.rgb(128, 128, 128)); DO NOT NEED FOR GROUP GOAL UI
                                               // goalStartDate.setHintTextColor(Color.rgb(128, 128, 128)); DO NOT NEED FOR GOUP GOAL UI
                                                goalDueDate.setHintTextColor(Color.rgb(128, 128, 128));

                                              //  goalCompleteBy.setTextColor(Color.rgb(128, 128, 128)); DO NOT NEED FOR GROUP GOAL UI
                                              //  goalStartDate.setTextColor(Color.rgb(128, 128, 128)); DO NOT NEED FOR GROUP GOAL UI
                                                goalDueDate.setTextColor(Color.rgb(128, 128, 128));


                                               // int uStart = controller.dateStrToUnix(goalStartDate.getText().toString()); DO NOT NEED FOR GROUP GOAL UI
                                                int uDue = controller.dateStrToUnix(goalDueDate.getText().toString());
                                                int uComplete =controller.dateStrToUnix(goalDueDate.getText().toString());

                                                long currentUnix = System.currentTimeMillis() / 1000L;

                                                // GoalModel goal = new GoalModel(bigGoalInput.getText().toString(),uComplete,goalType.getText().toString(),uStart,uDue,(int)currentUnix,subgoals);
                                                myGoal.setBigGoal(bigGoalInput.getText().toString());
                                                myGoal.setCompletedBy(uComplete);
                                                myGoal.setGoalType(goalType.getText().toString());
                                               // myGoal.setStartDate(uStart); DO NOT NEED FOR GROUP GOAL UI
                                                myGoal.setDeadline(uDue);
                                                myGoal.setCreatedAt((int) currentUnix);
                                                myGoal.setCourseNumber(courseNumberInput.getText().toString().toUpperCase());
                                                myGoal.setGoalType("group");

                                                String myGoalID = "TESTGOALID";
                                                myGoal.setGoalId(myGoalID);

                                                Log.d("GoalMade", "onClick: " + myGoal.toString());


                                                Intent i = new Intent(groupgoalView.this, MainActivity.class);
                                                i.putExtra("bigGoal", myGoal);
                                                Log.d("putExtras", "onClick: " + myGoal);
                                                setResult(RESULT_OK, i);
                                                Log.d("setExtras", "onClick: " + myGoal);
                                                finish();
                                            } else {
                                                Toast toast = Toast.makeText(getBaseContext(), "At least one date value is invalid.", Toast.LENGTH_LONG);
                                                toast.show();

                                                goalDueDate.setTextColor(Color.RED);

                                            }

                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }

                        }

                    }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("goalType selected", "onItemSelected: " + goalTypes[i]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Hides keyboard input from the view and also closes the cursors for bigGoalInput and courseNumberInput
     * @param view
     */
    public void closeKeyboard(View view){
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            bigGoalInput.setCursorVisible(false);
            courseNumberInput.setCursorVisible(false);

        }
    }
}