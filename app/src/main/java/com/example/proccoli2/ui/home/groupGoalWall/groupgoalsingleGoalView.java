package com.example.proccoli2.ui.home.groupGoalWall;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proccoli2.ui.mainActivity.MainActivity;
import com.example.proccoli2.R;
import com.example.proccoli2.ui.home.groupGoalWall.groupGoalSubGoalCreation.groupgoalsubGoalView;
import com.example.proccoli2.oldModels.GoalModel;
import com.example.proccoli2.oldModels.PersonalNoteModel;
import com.example.proccoli2.oldModels.SubGoalModel;
import com.example.proccoli2.progressReportView;
import com.example.proccoli2.ui.notificationPublisher.NotificationPublisher;
import com.example.proccoli2.ui.home.groupGoalWall.searchForFriends.searchforfreinds_view;
import com.example.proccoli2.ui.home.groupGoalWall.timerViewGroupGoals.timerViewGroupGoal;
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 *  Copyright © 2022 Le Jie Bennett. All rights reserved.
 *  Needs to be integrated with firebase, this version of the file is prior to firebase integration
 *  Activity opens when group goal is clicked on from the home view
 *
 */
public class groupgoalsingleGoalView extends AppCompatActivity {

    GoalModel passedGoal;
    GoalModel myGoal; //Goal that is a copy of the one from main activity
    GoalModel originalGoal; //Goal from Main Activity
    TextView goalInfo, subGoalLabel,chatLabel;
    RecyclerView subGoalRecyclerView, chatRecyclerView;
    groupgoalsingleGoalView_Controller controller = new groupgoalsingleGoalView_Controller(this);
    ImageButton uploadChatBtn;
    TextInputEditText noteInput;
    Button startWorking, setReminderBtn;
    Toolbar toolbar;
    SubGoalFullAdapter adapter;
    Context context = this;
    Button reportProgressBtn, seeProgressBtn;

    //StartWorking
    ImageView startWorkingCancelBtn;
    startWorkingFullAdapter startAdapter;
    RecyclerView startWorkingRecyclerView; //Recycler view for all popups
    int smileRating;
    int studiedFromTimer;
    int positionForSingleGoalReport = -1;
    int positionToUpdateStudiedTime = -1;


    //See Progress
    List<SubGoalModel> seeProgressItems;
    seeProgressFullAdapter seeProgressAdapter;

    //Report Progress popup
    List<SubGoalModel> reportProgressItems;
    reportProgressFullAdapter reportProgressAdapter;
    int subGoalPositionForProgressReport; //Keeps track of what to remove from the subgoal recycler view
    int subGoalPassedSubGoalPositionForProgressReport; //What goal to update with the new completion in the passed goal list


    //Set Reminder
    SingleDateAndTimePicker setReminderPicker;
    TextView cancelSetReminderBtn, doneSetReminderBtn,setReminderLabel;
    NotificationChannel notificationChannel;
    NotificationManager notificationManager;



    //Available to claim
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


    /***
     * Used to catch data from the smileyFace survey where users can rate their satisfaction with the goal they just completed
     */
    ActivityResultLauncher<Intent> activityResultLaunchTimer = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Log.d("onActivityResult", "ActivityResultLaunchSmileyRating: RUNNING");
                        if(result.getData().getStringExtra("smileRating")!=null){
                            smileRating = Integer.parseInt(result.getData().getStringExtra("smileRating"));
                            Log.d("RESULTSFROMTimer", "Smile" + String.valueOf(smileRating));
                        }

                        studiedFromTimer = Integer.parseInt(result.getData().getStringExtra("studiedTime"));
                        Log.d("RESULTSFROMTimer", "Studied" + String.valueOf(studiedFromTimer));
                        //NEED TO ADD A NEW ATTRIBUTE TO GOAL MODAL TO HOLD SMILE RATING
                        Log.d("Goal to update", "onActivityResult: " + myGoal.getSubGoals().get(positionToUpdateStudiedTime));
                        Log.d("ResultFromTimer", "onActivityResult: old studied" +myGoal.getSubGoals().get(positionToUpdateStudiedTime).getTotalStudyTime());
                        //Need to divide by 60000 since studied time is sent in milliseconds not minutes
                        myGoal.getSubGoals().get(positionToUpdateStudiedTime).setTotalStudyTime(myGoal.getSubGoals().get(positionToUpdateStudiedTime).getTotalStudyTime() + (Integer.valueOf(studiedFromTimer)/60000));
                        Log.d("ResultFromTimer", "onActivityResult: new studied" +myGoal.getSubGoals().get(positionToUpdateStudiedTime).getTotalStudyTime());

                    }
                }
            });


    //Get subgoals added from clicking admin button in toolbar
    ActivityResultLauncher<Intent> activityResultLaunch3 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Log.d("ActivityResultLaunch3", "onActivityResult: RUNNING");

                       passedGoal = (GoalModel) result.getData().getSerializableExtra("bigGoal");
                        Log.d("RESULTSFROMSetSub", String.valueOf(passedGoal));

                        myGoal.setBigGoal(passedGoal.getBigGoal());
                        myGoal.setDeadline(passedGoal.getDeadline());
                        goalInfo.setText("\n" + myGoal.getBigGoal() + "\n\nDeadline: " + controller.unixToString(myGoal.getDeadline()));
                        myGoal.setSubgoals(passedGoal.getSubGoals());
                        adapter = new SubGoalFullAdapter();
                        subGoalRecyclerView.setAdapter(adapter);
                        subGoalLabel.setVisibility(View.VISIBLE);

                        Log.d("newItems", "onActivityResult: " + adapter.items);
                        Log.d("newItems", "onActivityResult: " + myGoal.getSubGoals());


                    }
                }
            });


    //Catch data from Progress Report
    ActivityResultLauncher<Intent> activityResultLaunchProgressReport = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Log.d("onActivityResult", "ActivityResultLaunchprogressReport: RUNNING");
                        //Get the picked Percentage. This needs to be assigned to the subGoalPassedSubGoalPositionForProgressReport

                        int pickedPercentage = result.getData().getIntExtra("pickedPercentage",0);

                        //This assigns the new percentage to the correct subgoal in myGoal.getSubgoals
                        myGoal.getSubGoals().get(subGoalPassedSubGoalPositionForProgressReport).setPercentageComplete(pickedPercentage);
                        Log.d("NewCompletePercentage", myGoal.getSubGoals().get(subGoalPassedSubGoalPositionForProgressReport).getSubGoalName() + " " + String.valueOf(myGoal.getSubGoals().get(subGoalPassedSubGoalPositionForProgressReport).getPercentageComplete()));

                        //Now remove the subgoal that is in the reportProgress Recyclerview
                        Log.d("Before remove", "onActivityResult: " + myGoal.getSubGoals());

                        //Check to see if we have completed the goal that we just recorded progress too
                            //If goal is complete then remove it from the reportProgress recycler view
                        if(myGoal.getSubGoals().get(subGoalPassedSubGoalPositionForProgressReport).getPercentageComplete()==100){
                            //If there are still more subgoals that are incomplete after the current one that was just changed
                            if(reportProgressAdapter.getItemCount()>1){
                                //Remove the subgoal at the subGoal PositionFor Progress Report
                                reportProgressAdapter.remove(subGoalPositionForProgressReport);
                                Log.d("removed!!!", "completion rate was 100%: RUNNING");
                                Log.d("after if remove", "onActivityResult: " + myGoal.getSubGoals());
                                reportProgressAdapter.notifyDataSetChanged();
                            }
                            else{
                                //If all subgoals are complete after the current one that was just changed
                                Log.d("Need to remove last", "completion rate was 100%: RUNNING");
                                reportProgressAdapter = new reportProgressFullAdapter();
                                startWorkingRecyclerView.setAdapter(reportProgressAdapter);
                                startWorkingRecyclerView.setHasFixedSize(true);
                                Log.d("after Else remove", "onActivityResult: " + myGoal.getSubGoals());


                            }

                        }
                        //If it was not complete then do nothing. No need to remove anything.
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groupgoalsinglegoalfulldisplay_view);

        //Catch original goal from mainActivity that is used to initalize the view
        Bundle bundle = getIntent().getExtras();
        originalGoal = (GoalModel) bundle.getSerializable("bigGoal");
        myGoal = originalGoal;
        Log.d("OriginGoalFromMain", String.valueOf(originalGoal));


        //Start Working Cancel Button
        startWorkingCancelBtn = findViewById(R.id.startWorkingBtnCancel);
        startWorkingCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeStartWorkingPopup();
            }
        });
        //Start Working RecyclerView
        startWorkingRecyclerView = findViewById(R.id.startWorkingRecyclerView);
        closeStartWorkingPopup();

        setReminderPicker = findViewById(R.id.setReminderPicker);
        cancelSetReminderBtn = findViewById(R.id.cancelSetReminderBtn);
        doneSetReminderBtn = findViewById(R.id.doneSetReminderBtn);
        setReminderLabel = findViewById(R.id.setReminderLabel);

        setReminderPicker.setVisibility(View.INVISIBLE);
        setReminderPicker.setMustBeOnFuture(true);
        cancelSetReminderBtn.setVisibility(View.INVISIBLE);
        doneSetReminderBtn.setVisibility(View.INVISIBLE);
        setReminderLabel.setVisibility(View.INVISIBLE);




        cancelSetReminderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelSetReminderBtn.setVisibility(View.INVISIBLE);
                doneSetReminderBtn.setVisibility(View.INVISIBLE);
                setReminderPicker.setVisibility(View.INVISIBLE);
                setReminderLabel.setVisibility(View.INVISIBLE);

            }
        });

        doneSetReminderBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                Log.d("setReminder", "onClick: I selected this date" + setReminderPicker.getDate().toString());
                cancelSetReminderBtn.setVisibility(View.INVISIBLE);
                doneSetReminderBtn.setVisibility(View.INVISIBLE);
                setReminderPicker.setVisibility(View.INVISIBLE);
                setReminderLabel.setVisibility(View.INVISIBLE);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    notificationChannel = new NotificationChannel("12345", "Proccoli", NotificationManager.IMPORTANCE_HIGH);
                    notificationChannel.enableLights(true);
                    notificationChannel.enableVibration(true);
                    notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                    notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.createNotificationChannel(notificationChannel);
                }


                notificationChannel = new NotificationChannel("12345", "Proccoli", NotificationManager.IMPORTANCE_HIGH);
                notificationChannel.enableLights(true);
                notificationChannel.enableVibration(true);
                notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.createNotificationChannel(notificationChannel);


                //Alarm approach
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(groupgoalsingleGoalView.this, NotificationPublisher.class);
                intent.putExtra("bigGoal",myGoal.getBigGoal());
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


                //Get the time of when to set the alarm
                Calendar cal = Calendar.getInstance();
                Date picked = setReminderPicker.getDate();
                Log.d("calSet", "onClick: " + setReminderPicker.getDate().getYear());
                Log.d("calSet", "onClick: " + setReminderPicker.getDate().getMonth());
                Log.d("calSet", "onClick: " + setReminderPicker.getDate().getDate());
                Log.d("calSet", "onClick: " + setReminderPicker.getDate().getHours());
                Log.d("calSet", "onClick: " + setReminderPicker.getDate().getMinutes());
                Log.d("calSet", "onClick: " + setReminderPicker.getDate().getSeconds());


                //Need to had 1900 snce the year returned from setReminder is picked-1900  = year
                //but in order for the alarm to properly go off, it needs have +1900 to have the actual year
                int year = setReminderPicker.getDate().getYear();
                cal.set(Calendar.YEAR,year +1900);
                cal.set(Calendar.MONTH,setReminderPicker.getDate().getMonth());
                cal.set(Calendar.DATE, setReminderPicker.getDate().getDate());
                cal.set(Calendar.HOUR_OF_DAY, setReminderPicker.getDate().getHours());  // set hour
                cal.set(Calendar.MINUTE, setReminderPicker.getDate().getMinutes());          // set minute
                cal.set(Calendar.SECOND, setReminderPicker.getDate().getSeconds());               // set seconds

                //Create the alarm
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pendingIntent);


                //Alert popup
                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setTitle("Success")
                        .setMessage("You have set a reminder")
                        .setNegativeButton("Ok", null)
                        .create();
                dialog.show();
            }
        });

        setReminderBtn = findViewById(R.id.setReminderBtnGG);
        setReminderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelSetReminderBtn.setVisibility(View.VISIBLE);
                doneSetReminderBtn.setVisibility(View.VISIBLE);
                setReminderPicker.setVisibility(View.VISIBLE);
                setReminderLabel.setVisibility(View.VISIBLE);
            }
        });

        //Set up toolbar navigation
        toolbar =findViewById(R.id.toolbarSingleGoalFullDisplayGG);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If back arrow is tapped, go back to mainActivity and update the changes to myGoal
                Log.d("back clicked", "onMenuItemClick: Clicked back");
                Log.d("passBackGoal", "onMenuItemClick: " + myGoal);
                Log.d("passBackNotes", "onMenuItemClick: " + myGoal.getPersonalNotes());
                Intent i = new Intent(groupgoalsingleGoalView.this, MainActivity.class);
                i.putExtra("bigGoal",myGoal);
                Log.d("Updated myGoal Goal", "onMenuItemClick: Passed" + myGoal);
                setResult(RESULT_OK,i);
                finish();
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemID = item.getItemId();
                //If group Member Btn was clicked
                if (itemID == R.id.groupMemberBtn){
                    Log.d("Clicked", "onMenuItemClick: groupMembers");
                }

                //IF invite user was clicked
                if(itemID == R.id.inviteUserBtn){
                    Log.d("Clicked", "onMenuItemClick: inviteUSer");
                    Intent myIntent = new Intent(groupgoalsingleGoalView.this, searchforfreinds_view.class);
                    startActivity(myIntent);

                }

                //If admin Plus btn was clicked
                if(itemID == R.id.adminPlusBtn){
                    Log.d("Clicked", "onMenuItemClick: adminPlusBtn");

                    Intent i = new Intent(groupgoalsingleGoalView.this , groupgoalsubGoalView.class);
                    i.putExtra("bigGoal",myGoal);
                    Log.d("goalModelPutForSingle", "onClick: " + myGoal);
                    activityResultLaunch3.launch(i);

                }
                return false;
            }
        });

        //Set up startWorking button and recycler view
        //0 subgoals assigned to that person that are incomplete: show alert
        //1 subgoal assigned to that person that is incomplete: direct to timer
        //More than 1 subgoal assigned to that person: Recycler view
        //Note to self: Need to add a check to make sure that subgoals are counted only if they are assigned to that person
        startWorking = findViewById(R.id.startWorkingBtnGG);
        startWorking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Determines how many goals need to be added to startWorking (aka what goals in myGoals.getSubgoals are incomplete)
                int numOfSubGoalsIncomplete = 0;
                for(int i = 0; i < myGoal.getSubGoals().size(); i++){
                    if(myGoal.getSubGoals().get(i).getPercentageComplete()!=100){
                        numOfSubGoalsIncomplete = numOfSubGoalsIncomplete + 1;
                        positionForSingleGoalReport = i;
                    }
                }

                //No goals are incomplete
                if(numOfSubGoalsIncomplete == 0){
                    //Alert popup
                    AlertDialog dialog = new AlertDialog.Builder(context)
                            .setTitle("Start Working...")
                            .setMessage("Please claim a subgoal before starting to work on this project!")
                            .setNegativeButton("Ok", null)
                            .create();
                    dialog.show();
                }

                //More than 1 subgoal: set up recycler view
                else if(myGoal.getSubGoals().size()>1)
                {
                    //Change the ui
                    openStartWorkingPopup();
                    //Add the appropriate goals
                    setUpStartWorkingRecyclerView();
                }
                //1 subgoal: open timer
                else{
                    positionToUpdateStudiedTime = positionForSingleGoalReport;
                    Intent i = new Intent(groupgoalsingleGoalView.this, timerViewGroupGoal.class);
                    activityResultLaunchTimer.launch(i);


                }

            }
        });

        //Code Section that shows the report progress recycler view
        //Recycler view contains the subgoal item with a report button but only for items that are incomplete (completePercentage!=100)
        //0 subgoals: Alert Notification saying need to add a subgoal
        //1 subgoal: Direct reporting with picker and submit, do not need to show recycler view
        //More than 1 subgoal: Recycler view with only items that are incomplete that have report button that when clicked will start report activity
        reportProgressBtn = findViewById(R.id.reportProgressBtnGG);
        reportProgressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Counts the number of goals that are incomplete in myGoal.getSubGoals()
                int numOfSubGoalsIncomplete = 0;
                int positionForSingleGoalReport = -1; //keeps track of the position of the single incomplete subgoal in myGoal.getSubgoals which is used in 1 subgoal case
                for(int i = 0; i < myGoal.getSubGoals().size(); i++){
                    if(myGoal.getSubGoals().get(i).getPercentageComplete()!=100){
                        numOfSubGoalsIncomplete = numOfSubGoalsIncomplete + 1;
                        positionForSingleGoalReport = i;
                    }
                }
                if(numOfSubGoalsIncomplete == 0){
                    //Alert popup
                    AlertDialog dialog = new AlertDialog.Builder(context)
                            .setTitle("No available subgoal")
                            .setMessage("All subgoals assinged to you have been completed!")
                            .setNegativeButton("Ok", null)
                            .create();
                    dialog.show();
                }
                //1 subgoal
                //Fails when you submit the data
                else if (numOfSubGoalsIncomplete==1){
                    Bundle bundle = new Bundle();
                    bundle.putString("subgoalText", myGoal.getSubGoals().get(positionForSingleGoalReport).getSubGoalName());
                    bundle.putString("currentPercentageComplete",String.valueOf(myGoal.getSubGoals().get(positionForSingleGoalReport).getPercentageComplete()));
                    Intent i = new Intent(groupgoalsingleGoalView.this, progressReportView.class);
                    i.putExtras(bundle);
                    subGoalPositionForProgressReport = positionForSingleGoalReport;
                    activityResultLaunchProgressReport.launch(i);
                }

                //More than 1 subgoal
                else{
                    //Change the UI
                    openStartWorkingPopup();
                    //Add the appropriate goals
                    setUpReportProgressRecyclerView();
                }
            }
        });

        //Code Section that shows the see progress recycler view
        //Recycler view contains the progress bars with the subgoal percentage and name
        //0 subgoals: do nothing
        //1 or more subgoals: Show recycler view
        seeProgressBtn = findViewById(R.id.seeProgressBtnGG);
        seeProgressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myGoal.getSubGoals().size()>0){
                    //Change the UI
                    openStartWorkingPopup();
                    //Add the appropriate goals (in this case it should show all the goals in myGoals.getSubgoals())
                    setUpSeeProgressRecyclerView();
                }
            }
        });

        goalInfo = (TextView) findViewById(R.id.goalInfoGG);
        goalInfo.setText("\n" + myGoal.getBigGoal() + "\n\nDue Date" + controller.unixToString(myGoal.getDeadline()));
        subGoalLabel = findViewById(R.id.SubGoalRecyclerLabelGG);
        subGoalLabel.setText("\t\tSubgoals");



        chatLabel = findViewById(R.id.ChatRecyclerLabelGG);
        chatLabel.setText("\t\tGroup Chat");

        subGoalLabel.setVisibility(View.INVISIBLE);
        subGoalRecyclerView = findViewById(R.id.subGoalRecyclerViewFullGG);
        setUpSubgoalRecyclerView();

        chatRecyclerView = findViewById(R.id.ChatRecyclerViewGG);
        setUpChatRecyclerView();

        chatRecyclerView.setVisibility(View.VISIBLE);

        if(myGoal.getSubGoals().size()!= 0){
            subGoalLabel.setVisibility(View.VISIBLE);
            subGoalRecyclerView.setVisibility(View.VISIBLE);
        }

        noteInput = findViewById(R.id.chatInputGG);

        uploadChatBtn = findViewById(R.id.uploadChatGG);

        uploadChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("clickedNote", "onClick: Clicked upload note");
                long currentUnix = System.currentTimeMillis() / 1000L;
                String noteID = "noteID0001";
                PersonalNoteModel note = new PersonalNoteModel(noteInput.getText().toString(),(int)currentUnix,noteID);

                //Add note to the goalModel
                myGoal.getPersonalNotes().add(note);
                NotesAdapter adapterNotes = (NotesAdapter) chatRecyclerView.getAdapter();
                Log.d("personalNotes", "onClick: " + myGoal.getPersonalNotes());

                //Update the recycler
                adapterNotes.addItems();
                Log.d("added", "onClick: added PersonalNotes" + myGoal.getPersonalNotes().get(myGoal.getPersonalNotes().size()-1));
                noteInput.getText().clear();
                closeKeyboard(view);
            }
        });
    }

    /**
     * Class for displaying the all of the subgaols that are associated with this single goal
     * The subgoal view holder contains the availableToClaim Btn if the goal is unclaimed
     */
    class SubGoalFullAdapter extends RecyclerView.Adapter {
        List<SubGoalModel> items;

        public SubGoalFullAdapter() {
            items = new ArrayList<>();

            for(int i = 0; i < myGoal.getSubGoals().size(); i++){
                Log.d("trying to add", "setUpRecyclerView: " + myGoal.getSubGoals().get(i));
                items.add(myGoal.getSubGoals().get(i));
                Log.d("Added", "setUpRecyclerView: " + myGoal.getSubGoals().get(i));
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SubGoalFullViewHolder(parent);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Log.d("Position", "onBindViewHolder: position" + position);
            SubGoalFullViewHolder viewHolder = (SubGoalFullViewHolder) holder;
            final SubGoalModel item = items.get(position);
            Log.d("Position", "onBindViewHolder: position" + item);
            viewHolder.subGoalText.setText(items.get(position).getSubGoalName() + "\nDeadline: "+ controller.unixToStringDateTime(items.get(position).getDeadline()));
            viewHolder.setSubGoalPosition(position);
            if(items.get(position).getSubGoalAssignerID().equals("selfID")==true){
                Log.d("subgoalCompletedStatus", "onBindViewHolder: " + items.get(position).getIsChecked());
                viewHolder.createrInfo.setText(items.get(position).getSubGoalAssignerID());
                viewHolder.availableToClaimBtn.setClickable(false);
                viewHolder.availableToClaimBtn.setVisibility(View.INVISIBLE);
            }
            else{
                viewHolder.availableToClaimBtn.setClickable(true);
                viewHolder.availableToClaimBtn.setVisibility(View.VISIBLE);
                viewHolder.createrInfo.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public void remove(int position) {
            SubGoalModel item = items.get(position);
            if (items.contains(item)) {
                items.remove(position);
                notifyItemRemoved(position);
                for(int i = 0; i<myGoal.getSubGoals().size()-1;i++){
                    if(myGoal.getSubGoals().get(i) == item){
                        myGoal.getSubGoals().remove(item);
                    }
                }
            }
        }

        public void addInitialItems(){
            for(int i = 0; i < myGoal.getSubGoals().size(); i++){
                Log.d("trying to add", "setUpRecyclerView: " + myGoal.getSubGoals().get(i));
                items.add(myGoal.getSubGoals().get(i));
                Log.d("Added", "setUpRecyclerView: " + myGoal.getSubGoals().get(i));
            }
        }

        public void addItem(SubGoalModel subgoal){
            items.add(subgoal);
            notifyItemInserted(items.size() - 1);
            Log.d("AfterItemsAdd", "addItems: " + items);
        }
    }

    class SubGoalFullViewHolder extends RecyclerView.ViewHolder {

        TextView subGoalText,createrInfo;
        Button availableToClaimBtn;
        int subGoalPosition;
        String makeAvailableHowLong;

        public void setMakeAvailableHowLong(String makeAvailableHowLong){
            this.makeAvailableHowLong = makeAvailableHowLong;
        }

        public String getMakeAvailableHowLong(){
            return makeAvailableHowLong;
        }

        public void setSubGoalPosition(int subGoalPosition) {
            this.subGoalPosition = subGoalPosition;
        }

        public int getSubGoalPosition() {
            return subGoalPosition;
        }

        public SubGoalFullViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.groupgoalsubgoal_item, parent, false));
            subGoalText = (TextView) itemView.findViewById(R.id.subGoalItemInfoGG);
            createrInfo = (TextView) itemView.findViewById(R.id.creatorInfoGG);
            availableToClaimBtn = itemView.findViewById(R.id.availableToClaimBtn);

            availableToClaimBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //Inflate the layout of the popup window
                    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                    View availableToClaimView = inflater.inflate(R.layout.makeavailable_popup,null);

                    //Create the popup window
                    int width = LinearLayout.LayoutParams.MATCH_PARENT;
                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    boolean focusable = true; // lets taps outside the popup also dismiss it
                    final PopupWindow popupWindow = new PopupWindow(availableToClaimView, width, height, focusable);

                    //Show the popup window
                    popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                    TextView howLongLabel = availableToClaimView.findViewById(R.id.assignToSelfLabel);


                    TextView howLongPicked = availableToClaimView.findViewById(R.id.howLongAssignToSelf);

                    //Set up how long picker
                    final NumberPicker hourPicker = (NumberPicker) availableToClaimView.findViewById(R.id.hourPickerAssignToSelf);
                    hourPicker.setMinValue(0);
                    hourPicker.setMaxValue(subGoalHowLongHours.length-1);
                    hourPicker.setDisplayedValues(subGoalHowLongHours);

                    final NumberPicker minutePicker = (NumberPicker) availableToClaimView.findViewById(R.id.minutePickerAssignToSelf);
                    minutePicker.setMinValue(0);
                    minutePicker.setMaxValue(subGoalHowLongMinutes.length-1);
                    minutePicker.setDisplayedValues(subGoalHowLongMinutes);

                    hourPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                            if (subGoalHowLongHours[hourPicker.getValue()].equals("hours") == true && subGoalHowLongMinutes[minutePicker.getValue()].equals("minutes") == true) {
                                Toast toast = Toast.makeText(context, "Please select a time duration", Toast.LENGTH_LONG);
                                toast.show();
                            } else {
                                //If "hours is selected" then only minutes should show
                                if (subGoalHowLongHours[hourPicker.getValue()].equals("hours") == true) {
                                    howLongPicked.setText(subGoalHowLongMinutes[minutePicker.getValue()]);

                                }
                                //If "minutes is selected" then only hours should show
                                else if (subGoalHowLongMinutes[minutePicker.getValue()].equals("minutes") == true) {
                                    howLongPicked.setText(subGoalHowLongHours[hourPicker.getValue()]);
                                } else {
                                    howLongPicked.setText(subGoalHowLongHours[hourPicker.getValue()] + " " + subGoalHowLongMinutes[minutePicker.getValue()]);
                                }
                            }
                        }
                    });



                    //Sets up Minute Picker
                    minutePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                            if(subGoalHowLongHours[hourPicker.getValue()].equals("hours") == true && subGoalHowLongMinutes[minutePicker.getValue()].equals("minutes") == true ){
                                Toast toast = Toast.makeText(context, "Please select a time duration", Toast.LENGTH_LONG);
                                toast.show();
                            }
                            else {
                                //If "hours is selected" then only minutes should show
                                if (subGoalHowLongHours[hourPicker.getValue()].equals("hours") == true) {
                                    howLongPicked.setText(subGoalHowLongMinutes[minutePicker.getValue()]);

                                }
                                //If "minutes is selected" then only hours should show
                                else if (subGoalHowLongMinutes[minutePicker.getValue()].equals("minutes") == true) {
                                    howLongPicked.setText(subGoalHowLongHours[hourPicker.getValue()]);
                                } else {
                                    howLongPicked.setText(subGoalHowLongHours[hourPicker.getValue()] + " " + subGoalHowLongMinutes[minutePicker.getValue()]);
                                }
                            }
                        }
                    });

                    Button submitHowLongPopupBtn = (Button) availableToClaimView.findViewById(R.id.submitAssignToSelfBtn);
                    submitHowLongPopupBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(howLongLabel.getText().toString().length()>0){
                                //Need to save the how long in the picker
                                makeAvailableHowLong = howLongLabel.getText().toString();
                                Log.d("before assigned updated", "onClick: " +  passedGoal.getSubGoals().get(subGoalPosition).getHowLongHours());

                                passedGoal.getSubGoals().get(subGoalPosition).setHowLongHours(howLongLabel.getText().toString());
                                Log.d("newly updated", "onClick: " +  passedGoal.getSubGoals().get(subGoalPosition).getHowLongHours());
                                popupWindow.dismiss();
                                availableToClaimBtn.setVisibility(View.INVISIBLE);

                                //NEED TO INSERT THE NAME OF THE ONE WHO CLICKED AVAILABLE TO CLAIM AND HIT SUBMIT
                                //SELF ID IS JUST A PLACE HOLDER
                                createrInfo.setText("SelfID");
                                createrInfo.setVisibility(View.VISIBLE);

                            }
                            else{
                                Toast toast = Toast.makeText(getBaseContext(), "Please enter a valid time length", Toast.LENGTH_LONG);
                                toast.show();
                                howLongPicked.setHintTextColor(Color.RED);
                            }
                        }
                    });

                    //Close out of popup when cancel is clicked
                    Button cancelHowLongPopupBtn = (Button) availableToClaimView.findViewById(R.id.cancelAssignToSelfBtn);
                    cancelHowLongPopupBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });
                }
            });
        }
    }

    private void setUpSubgoalRecyclerView() {
        subGoalRecyclerView.setVisibility(View.VISIBLE);
        subGoalLabel.setVisibility(View.VISIBLE);
        subGoalRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SubGoalFullAdapter();
        subGoalRecyclerView.setAdapter(adapter);
        subGoalRecyclerView.setHasFixedSize(true);

    }

    ///////////////////Notes Recycler and Adapter/////////////////////////////////////////////////////////////////

    class NotesAdapter extends RecyclerView.Adapter {
        List<PersonalNoteModel> items;

        public NotesAdapter() {
            items = new ArrayList<>();

            for(int i = 0; i < myGoal.getPersonalNotes().size(); i++){
                Log.d("trying to add", "personalNotes " +myGoal.getPersonalNotes().get(i));
                items.add(myGoal.getPersonalNotes().get(i));
                Log.d("Added", "personalNotes: " + myGoal.getPersonalNotes().get(i));
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new NotesViewHolder(parent);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Log.d("Position", "onBindViewHolder: position" + position);
            NotesViewHolder viewHolder = (NotesViewHolder) holder;
            final PersonalNoteModel item = items.get(position);
            Log.d("Position", "onBindViewHolder: position" + item);
            viewHolder.noteText.setText(items.get(position).getNote());
            viewHolder.createdAt.setText(controller.calculateMinutesAgo(items.get(position).getCreatedAt()));

        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public void addItems(){
            items.add(myGoal.getPersonalNotes().get(myGoal.getPersonalNotes().size()-1));
            notifyItemInserted(items.size() - 1);
        }
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {

        TextView noteText;
        TextView createdAt;

        public NotesViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_item, parent, false));
            noteText = (TextView) itemView.findViewById(R.id.notesText);
            createdAt = itemView.findViewById(R.id.notesCreatedAt);

        }
    }

    private void setUpChatRecyclerView() {
        chatRecyclerView.setVisibility(View.INVISIBLE);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatRecyclerView.setAdapter(new NotesAdapter());
        chatRecyclerView.setHasFixedSize(true);
        Log.d("finishedSetup", "setUpNotesRecyclerView: finished setUpNotesRecyclerView");
    }

    public void closeKeyboard(View view){
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            noteInput.setCursorVisible(false);

        }
    }

    private void openStartWorkingPopup(){
        startWorkingRecyclerView.setVisibility(View.VISIBLE);
        startWorkingRecyclerView.bringToFront();
        startWorkingCancelBtn.setVisibility(View.VISIBLE);
        startWorkingCancelBtn.bringToFront();
    }

    private  void closeStartWorkingPopup(){
        startWorkingCancelBtn.setVisibility(View.INVISIBLE);
        startWorkingRecyclerView.setVisibility(View.INVISIBLE);

    }

    ///////////////////StartWorking/////////////////////////////////////////////////////////////////

    class startWorkingFullAdapter extends RecyclerView.Adapter {
        List<SubGoalModel> startWorkingItems;

        public startWorkingFullAdapter() {
            startWorkingItems = new ArrayList<>();
            Log.d("subgoals toFilter", "startWorkingFullAdapter: " + myGoal.getSubGoals());

            for(int i = 0; i < myGoal.getSubGoals().size(); i++){
                if(myGoal.getSubGoals().get(i).getPercentageComplete()!=100){
                    startWorkingItems.add(myGoal.getSubGoals().get(i));
                    Log.d("Added", "setUpRecyclerView: " + myGoal.getSubGoals().get(i));
                }
            }

        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new startWorkingSubGoalViewHolder(parent);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            startWorkingSubGoalViewHolder viewHolder = (startWorkingSubGoalViewHolder) holder;
            final SubGoalModel item = startWorkingItems.get(position);
            Log.d("Position", "onBindViewHolder: position" + item);
            viewHolder.subGoalText.setText("\n" + startWorkingItems.get(position).getSubGoalName() + "\nDeadline: "+ controller.unixToStringDateTime(startWorkingItems.get(position).getDeadline()));
            viewHolder.setSubGoalPosition(position);
        }

        @Override
        public int getItemCount() {
            return startWorkingItems.size();
        }

        public void remove(int position) {
            SubGoalModel item = startWorkingItems.get(position);
            if (startWorkingItems.contains(item)) {
                startWorkingItems.remove(position);
                notifyItemRemoved(position);
                for(int i = 0; i<myGoal.getSubGoals().size()-1;i++){
                    if(myGoal.getSubGoals().get(i) == item){
                        myGoal.getSubGoals().remove(item);
                    }
                }
            }
        }

        public void addInitialItems(){
            for(int i = 0; i < myGoal.getSubGoals().size(); i++){
                Log.d("trying to add", "setUpRecyclerView: " + myGoal.getSubGoals().get(i));
                startWorkingItems.add(myGoal.getSubGoals().get(i));
                Log.d("Added", "setUpRecyclerView: " + myGoal.getSubGoals().get(i));
            }
        }

        public void addItem(SubGoalModel subgoal){
            startWorkingItems.add(subgoal);
            notifyItemInserted(startWorkingItems.size() - 1);
            Log.d("AfterItemsAdd", "addItems: " + startWorkingItems);
        }
    }

    class startWorkingSubGoalViewHolder extends RecyclerView.ViewHolder {

        TextView subGoalText,creatorInfo;
        Button availableToClaim;
        int subGoalPosition; //Position from startWorkingItems

        public void setSubGoalPosition(int subGoalPosition) {
            this.subGoalPosition = subGoalPosition;
        }

        public int getSubGoalPosition() {
            return subGoalPosition;
        }

        public startWorkingSubGoalViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.groupgoalsubgoalsstartworking_item, parent, false));
            subGoalText = (TextView) itemView.findViewById(R.id.subgoalItemInfoSWGG);

            subGoalText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    positionToUpdateStudiedTime = myGoal.getSubGoals().indexOf(startAdapter.startWorkingItems.get(subGoalPosition));
                    Intent i = new Intent(groupgoalsingleGoalView.this, timerViewGroupGoal.class);
                    activityResultLaunchTimer.launch(i);
                }
            });
        }
    }

    private void setUpStartWorkingRecyclerView() {
        startWorkingRecyclerView.setVisibility(View.VISIBLE);
        startWorkingRecyclerView.bringToFront();
        startWorkingRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        startAdapter = new startWorkingFullAdapter();
        startWorkingRecyclerView.setAdapter(startAdapter);
        startWorkingRecyclerView.setHasFixedSize(true);

    }


    /////////////////Everything below is for seeProgress Popup///////////
    class seeProgressFullAdapter extends RecyclerView.Adapter {

        public seeProgressFullAdapter() {
            seeProgressItems = new ArrayList<>();
            Log.d("subgoals toFilter", "seeProgressFullAdapter: " + myGoal.getSubGoals());

            for(int i = 0; i < myGoal.getSubGoals().size(); i++){
                seeProgressItems.add(myGoal.getSubGoals().get(i));
                Log.d("Added", "setUpRecyclerView: " + myGoal.getSubGoals().get(i));
            }

            Collections.sort(seeProgressItems,compareByProgressComplete);

        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new seeProgressSubGoalViewHolder(parent);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Log.d("Position", "onBindViewHolder: position" + position);
            seeProgressSubGoalViewHolder viewHolder = (seeProgressSubGoalViewHolder) holder;
            final SubGoalModel item = seeProgressItems.get(position);
            //This recyvler view should match the order and contain everyting in myGoals.getSubgoals()

            Log.d("Position", "onBindViewHolder: position" + item);
            viewHolder.subGoalText.setText("\t" + seeProgressItems.get(position).getSubGoalName());
            viewHolder.percentageText.setText(String.valueOf(seeProgressItems.get(position).getPercentageComplete()) + "%");
            viewHolder.creatorText.setText(seeProgressItems.get(position).getSubGoalAssignerID() + "\t");
            viewHolder.progressBar.setProgress(seeProgressItems.get(position).getPercentageComplete(),true);
            viewHolder.progressBar.setScaleY(2f);

            //Sets colors for progress bar
            int percentForProgress = seeProgressItems.get(position).getPercentageComplete();
            if(percentForProgress < 25){
                viewHolder.progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.darkredprogress));

            }
            else if(percentForProgress >=25 && percentForProgress<50){
                viewHolder.progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.redprogress));

            }
            else if(percentForProgress >=50 && percentForProgress<75){
                viewHolder.progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.greenprogress));

            }
            else
                viewHolder.progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.darkgreenprogress));

        }

        @Override
        public int getItemCount() {
            return seeProgressItems.size();
        }


        public void remove(int position) {
            SubGoalModel item = seeProgressItems.get(position);
            if (seeProgressItems.contains(item)) {
                seeProgressItems.remove(position);
                notifyItemRemoved(position);
                for(int i = 0; i<myGoal.getSubGoals().size()-1;i++){
                    if(myGoal.getSubGoals().get(i) == item){
                        myGoal.getSubGoals().remove(item);
                    }
                }
            }
        }

        public void addInitialItems(){
            for(int i = 0; i < myGoal.getSubGoals().size(); i++){
                Log.d("trying to add", "setUpRecyclerView: " + myGoal.getSubGoals().get(i));
                seeProgressItems.add(myGoal.getSubGoals().get(i));
                Log.d("Added", "setUpRecyclerView: " + myGoal.getSubGoals().get(i));
            }
        }

        public void addItem(SubGoalModel subgoal){
            seeProgressItems.add(subgoal);
            notifyItemInserted(seeProgressItems.size() - 1);
            Log.d("AfterItemsAdd", "addItems: " + seeProgressItems);
        }
    }


    class seeProgressSubGoalViewHolder extends RecyclerView.ViewHolder {

        TextView subGoalText, percentageText, creatorText;
        ProgressBar progressBar;

        public seeProgressSubGoalViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.seeprogressitem_view, parent, false));
            subGoalText = (TextView) itemView.findViewById(R.id.subGoalNameSeeProgress);

            percentageText= (TextView) itemView.findViewById(R.id.percentCompleteSeeProgress);

            creatorText =  (TextView) itemView.findViewById(R.id.subgoalCreatorSeeProgress);

            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);

        }
    }

    private void setUpSeeProgressRecyclerView() {
        startWorkingRecyclerView.setVisibility(View.VISIBLE);
        startWorkingRecyclerView.bringToFront();
        startWorkingRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        seeProgressAdapter = new seeProgressFullAdapter();
        startWorkingRecyclerView.setAdapter(seeProgressAdapter);
        startWorkingRecyclerView.setHasFixedSize(true);

    }


    /**
     * Used to compare the progressCompleteBy to sort the progress bars in the see Progress popup
     */
    Comparator<SubGoalModel> compareByProgressComplete = new Comparator<SubGoalModel>() {
        @Override
        public int compare(SubGoalModel subgoal1, SubGoalModel subgoal2) {
            return Integer.compare(subgoal1.getPercentageComplete(),subgoal2.getPercentageComplete());
        }
    };

    /////////////////////Everything below is for report progress/////////////////

    class reportProgressFullAdapter extends RecyclerView.Adapter {


        public reportProgressFullAdapter() {
            reportProgressItems = new ArrayList<>();
            //Order of loaded in will be the same as myGoal.getSubGoals.size(), but the indexes will be reindexed
            for(int i = 0; i < myGoal.getSubGoals().size(); i++){
                if(myGoal.getSubGoals().get(i).getPercentageComplete()!=100){
                    reportProgressItems.add(myGoal.getSubGoals().get(i));
                    Log.d("Added", "setUpRecyclerView: " + myGoal.getSubGoals().get(i));
                }
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new reportProgressSubGoalViewHolder(parent);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            //Position = position in recycler view
            Log.d("Position", "onBindViewHolder: position" + position);
            reportProgressSubGoalViewHolder viewHolder = (reportProgressSubGoalViewHolder) holder;
            final SubGoalModel item = reportProgressItems.get(position);
            Log.d("Subgoal position", "onBindViewHolder: " + myGoal.getSubGoals().get(position));
            Log.d("Position", "onBindViewHolder: position" + item);
            viewHolder.subGoalText.setText("\n" + reportProgressItems.get(position).getSubGoalName() + "\ndeadline: " + controller.unixToStringDateTime(reportProgressItems.get(position).getDeadline()));
            viewHolder.setSubGoalPosition(position);

            //This should return the true index found in myGoal.getSubGoals()
            viewHolder.setSubGoalPassedGoalPosition(myGoal.getSubGoals().indexOf(reportProgressItems.get(position)));
            Log.d("True MyGoalsIndex", "onBindViewHolder: True Index in myGoals: " + viewHolder.getSubGoalPassedGoalPosition());
            Log.d("Recycler Index", "onBindViewHolder: Recycler Index: " + viewHolder.getSubGoalPosition());
        }

        @Override
        public int getItemCount() {
            return reportProgressItems.size();
        }

        public void remove(int position) {
            SubGoalModel item = reportProgressItems.get(position);
            if (reportProgressItems.contains(item)) {
                reportProgressItems.remove(position);
                notifyItemRemoved(position);
            }
        }

        public void addInitialItems(){
            for(int i = 0; i < myGoal.getSubGoals().size(); i++){
                Log.d("trying to add", "setUpRecyclerView: " + myGoal.getSubGoals().get(i));
                reportProgressItems.add(myGoal.getSubGoals().get(i));
                Log.d("Added", "setUpRecyclerView: " + myGoal.getSubGoals().get(i));
            }
        }

        public void addItem(SubGoalModel subgoal){
            reportProgressItems.add(subgoal);
            notifyItemInserted(reportProgressItems.size() - 1);
            Log.d("AfterItemsAdd", "addItems: " + reportProgressItems);
        }
    }

    class reportProgressSubGoalViewHolder extends RecyclerView.ViewHolder {

        TextView subGoalText;
        Button reportBtn;
        int subGoalPosition; //Keeps track of position in reportProgressRecyclerList
        int subGoalPassedGoalPosition; //Keeps track of the position in myGoal.getSubgoals()

        public void setSubGoalPassedGoalPosition(int subGoalPassedGoalPosition){
            this.subGoalPassedGoalPosition = subGoalPassedGoalPosition;
        }

        public int getSubGoalPassedGoalPosition(){
            return subGoalPassedGoalPosition;
        }
        public void setSubGoalPosition(int subGoalPosition) {
            this.subGoalPosition = subGoalPosition;
        }

        public int getSubGoalPosition() {
            return subGoalPosition;
        }

        public reportProgressSubGoalViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.reportprogressitem_view, parent, false));
            subGoalText = (TextView) itemView.findViewById(R.id.subGoalInfoSingleViewGG);

            reportBtn = (Button) itemView.findViewById(R.id.reportBtn);
            reportBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("reportBTnClicked", "onClick: ReportingBtn clicked");


                    //Globally assigns positions
                    subGoalPassedSubGoalPositionForProgressReport = subGoalPassedGoalPosition; //For position in myGoals.getSubgoals
                    subGoalPositionForProgressReport = subGoalPosition; //For position in startWorkingRecyclerView
                    Bundle bundle = new Bundle();
                    bundle.putString("subgoalText", subGoalText.getText().toString());

                    //Sends the currentPercentageComplete of the recycler view item that was clicked on
                    bundle.putString("currentPercentageComplete",String.valueOf(reportProgressItems.get(subGoalPosition).getPercentageComplete()));
                    Intent i = new Intent(groupgoalsingleGoalView.this,progressReportView.class);
                    i.putExtras(bundle);
                    activityResultLaunchProgressReport.launch(i);
                }
            });
        }
    }

    private void setUpReportProgressRecyclerView() {
        startWorkingRecyclerView.setVisibility(View.VISIBLE);
        startWorkingRecyclerView.bringToFront();
        startWorkingRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reportProgressAdapter = new reportProgressFullAdapter();
        startWorkingRecyclerView.setAdapter(reportProgressAdapter);
        startWorkingRecyclerView.setHasFixedSize(true);

    }


}
