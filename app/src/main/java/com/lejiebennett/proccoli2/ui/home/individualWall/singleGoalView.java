package com.lejiebennett.proccoli2.ui.home.individualWall;

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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.lejiebennett.proccoli2.ui.mainActivity.MainActivity;
import com.lejiebennett.proccoli2.NewModels.DataServices;
import com.lejiebennett.proccoli2.NewModels.IndividualGoalModel;
import com.lejiebennett.proccoli2.NewModels.IndividualSubGoalStructModel;
import com.lejiebennett.proccoli2.NewModels.PersonalNoteModel;
import com.lejiebennett.proccoli2.NewModels.ResultHandler;
import com.lejiebennett.proccoli2.NewModels.SingletonStrings;
import com.lejiebennett.proccoli2.ui.notificationPublisher.NotificationPublisher;
import com.lejiebennett.proccoli2.R;
import com.lejiebennett.proccoli2.ui.home.individualWall.individualChart.goalProgressView;
import com.lejiebennett.proccoli2.ui.home.individualWall.goalSetting.goalSettingView;
import com.lejiebennett.proccoli2.smileyFaceSurveyView;
import com.lejiebennett.proccoli2.ui.home.individualWall.timerViewIndividualGoal.timerView;
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Copyright © 2022 Le Jie Bennett. All rights reserved.
 * Class for displaying an individual single goal view
 */
public class singleGoalView extends AppCompatActivity {

    IndividualGoalModel passedGoal;
    IndividualGoalModel myGoal;
    IndividualGoalModel originalGoal;
    TextView goalInfo, subGoalLabel,notesLabel;
    RecyclerView subGoalRecyclerView, notesRecyclerView;
    singleGoalView_Controller controller = new singleGoalView_Controller(this);
    ImageButton uploadNoteBtn;
    TextInputEditText noteInput;
    Button startWorking;
    androidx.appcompat.widget.Toolbar toolbar;
    Button completeBtn;
    SubGoalFullAdapter adapter;
    Button setReminderBtn;
    Context context = this;
    Button goalProgressBtn;
    int smileRating;
    int studiedFromTimer;
    SingletonStrings ss = new SingletonStrings();

    //Set Reminder
    SingleDateAndTimePicker setReminderPicker;
    TextView cancelSetReminderBtn, doneSetReminderBtn, setReminderLabel;
    NotificationChannel notificationChannel;
    NotificationManager notificationManager;

    //Personal Notes
    ArrayList<PersonalNoteModel> itemNotesFromServerOriginaly = new ArrayList<>();

    //Start Working
    ImageView startWorkingCancelBtn;
    startWorkingFullAdapter startAdapter;
    RecyclerView startWorkingRecyclerView; //Recycler view for all popups
    int positionForSingleGoalReport = -1;
    int positionToUpdateStudiedTime = -1;


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
                        Log.d("newItems", "onActivityResult: " + myGoal.getSubGoals());

                    }
                }
            });

    /***
     * Used to catch data from the smileyFace survey where users can rate their satisfaction with the goal they just completed
     */
    ActivityResultLauncher<Intent> activityResultLaunchSmileyRating = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Log.d("onActivityResult", "ActivityResultLaunchSmileyRating: RUNNING");
                        smileRating = Integer.parseInt(result.getData().getStringExtra("smileRating"));
                        Log.d("RESULTSFROMsetSmile", String.valueOf(smileRating));
                        Log.d("newItems", "onActivityResult: " + myGoal.getSubGoals());
                    }
                }
            });


    /**
     * Used to catch data from goal setting page
     * Will need to further update since it is only manually setting a few fields. I will need all of the fields later on
     */
    ActivityResultLauncher<Intent> activityResultLaunch3 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Log.d("ActivityResultLaunch3", "onActivityResult: RUNNING");
                        passedGoal = (IndividualGoalModel) result.getData().getSerializableExtra("bigGoal");
                        Log.d("RESULTSFROMSetSub", String.valueOf(passedGoal));
                        myGoal.setBigGoal(passedGoal.getBigGoal());
                        myGoal.setWhenIsDue(passedGoal.getWhenIsDue());
                        goalInfo.setText("\n" + myGoal.getBigGoal() + "\n\n" + controller.unixToString((int)myGoal.getWhenIsDue()));
                        myGoal.setSubGoals(passedGoal.getSubGoals());
                        adapter = new SubGoalFullAdapter();
                        subGoalRecyclerView.setAdapter(adapter);
                        subGoalLabel.setVisibility(View.VISIBLE);

                        int numOriginalSubGoals = myGoal.getSubGoals().size();
                        ArrayList<IndividualSubGoalStructModel> actualSubgoals = new ArrayList<>();
                        for (int i = 0; i<numOriginalSubGoals; i++) {
                            if(myGoal.getSubGoals().get(i).is_isDeleted()==false){
                                actualSubgoals.add(myGoal.getSubGoals().get(i));
                            }
                        }

                        myGoal.setSubGoals(actualSubgoals);

                        Log.d("newItems", "onActivityResult: " + adapter.items);
                        Log.d("newItems", "onActivityResult: " + myGoal.getSubGoals());
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singlegoalfulldisplay_view);

        //Catch original goal from mainActivity that is used to initalize the view
        Bundle bundle = getIntent().getExtras();
        originalGoal = (IndividualGoalModel) bundle.getSerializable("bigGoal");
        myGoal = originalGoal;

        //Makes it so the subgoals marked deleted in the server do not appear in the recycler view
        int numOriginalSubGoals = myGoal.getSubGoals().size();
        ArrayList<IndividualSubGoalStructModel> actualSubgoals = new ArrayList<>();
        for (int i = 0; i<numOriginalSubGoals; i++) {
            if(myGoal.getSubGoals().get(i).is_isDeleted()==false){
                actualSubgoals.add(myGoal.getSubGoals().get(i));
            }
        }

        myGoal.setSubGoals(actualSubgoals);
        //Start Working Cancel Button
        startWorkingCancelBtn = findViewById(R.id.startWorkingBtnCancelSingle);
        startWorkingCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeStartWorkingPopup();
            }
        });
        //Start Working RecyclerView
        startWorkingRecyclerView = findViewById(R.id.startWorkingRecyclerViewSingle);
        closeStartWorkingPopup();


        //Initalize Reminder views
        setReminderPicker = findViewById(R.id.setReminderPickerS);
        cancelSetReminderBtn = findViewById(R.id.cancelSetReminderBtnS);
        doneSetReminderBtn = findViewById(R.id.doneSetReminderBtnS);
        setReminderLabel = findViewById(R.id.setReminderLabelS);


        //Hide Reminder Views
        setReminderPicker.setVisibility(View.INVISIBLE);
        setReminderPicker.setMustBeOnFuture(true);
        cancelSetReminderBtn.setVisibility(View.INVISIBLE);
        doneSetReminderBtn.setVisibility(View.INVISIBLE);
        setReminderLabel.setVisibility(View.INVISIBLE);


        cancelSetReminderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Hide reminder views
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


                //Verifies that the date selected is valid
                if(controller.setReminder(myGoal,setReminderPicker.getDate().getTime())==false){
                    return;
                }



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

                //Get the time of when to set the alarm
                Calendar cal = Calendar.getInstance();

                Calendar reminderCal = Calendar.getInstance();
                reminderCal.setTime(setReminderPicker.getDate());
                Log.d("calSet", "onClick: " + reminderCal.get(Calendar.YEAR));
                Log.d("calSet", "onClick: " + reminderCal.get(Calendar.MONTH));
                Log.d("calSet", "onClick: " + reminderCal.get(Calendar.DATE));
                Log.d("calSet", "onClick: " + reminderCal.get(Calendar.HOUR));
                Log.d("calSet", "onClick: " + reminderCal.get(Calendar.MINUTE));
                Log.d("calSet", "onClick: " + reminderCal.get(Calendar.SECOND));


                //Need to had 1900 snce the year returned from setReminder is picked-1900  = year
                //but in order for the alarm to properly go off, it needs have +1900 to have the actual year
                int year =reminderCal.get(Calendar.YEAR);
                cal.set(Calendar.YEAR,year +1900);
                cal.set(Calendar.MONTH,reminderCal.get(Calendar.MONTH));
                cal.set(Calendar.DATE, reminderCal.get(Calendar.DATE));
                cal.set(Calendar.HOUR_OF_DAY,reminderCal.get(Calendar.HOUR));  // set hour
                cal.set(Calendar.MINUTE, reminderCal.get(Calendar.MINUTE));          // set minute
                cal.set(Calendar.SECOND, reminderCal.get(Calendar.SECOND));               // set seconds

                //Alarm approach
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(singleGoalView.this, NotificationPublisher.class);
                intent.putExtra("bigGoalName",myGoal.getBigGoal());
                String notificationID = DataServices.getInstance().getAlphaNumericString(11);
                intent.putExtra("notification_id",notificationID);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                DataServices.getInstance().saveReminder(myGoal.getGoalId(),notificationID,DataServices.getInstance().prepareReminderData(cal.getTimeInMillis()/100L));



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

        setReminderBtn = findViewById(R.id.setReminderBtn);
        setReminderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("I was clicked", "onClick: ");
                cancelSetReminderBtn.setVisibility(View.VISIBLE);
                doneSetReminderBtn.setVisibility(View.VISIBLE);
                setReminderPicker.setVisibility(View.VISIBLE);
                setReminderLabel.setVisibility(View.VISIBLE);
            }
        });

        toolbar =findViewById(R.id.toolbarSingleGoalFullDisplay);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("back clicked", "onMenuItemClick: Clicked back");
                Log.d("passBackGoal", "onMenuItemClick: " + myGoal);
                Intent i = new Intent(singleGoalView.this, MainActivity.class);
                i.putExtra("bigGoal",myGoal);
                Log.d("Updated myGoal Goal", "onMenuItemClick: Passed" + myGoal);
                setResult(RESULT_OK,i);
                finish();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(myGoal.isCompleted()==false){
                    int itemId = item.getItemId();
                    if (itemId == R.id.settingBtn) {
                        Intent i = new Intent(singleGoalView.this, goalSettingView.class);

                        i.putExtra("bigGoal",myGoal);
                        Log.d("goalModelPutForSingle", "onClick: " + myGoal);
                        activityResultLaunch3.launch(i);
                    }
                }
                return false;
            }
        });

        startWorking = findViewById(R.id.startWorkingBtn);
        startWorking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Determines how many goals need to be added to startWorking (aka what goals in myGoals.getSubgoals are incomplete)
                int numOfSubGoalsIncomplete = 0;
                for(int i = 0; i < myGoal.getSubGoals().size(); i++){
                    if(myGoal.getSubGoals().get(i).is_isChecked()!=true){
                        numOfSubGoalsIncomplete = numOfSubGoalsIncomplete + 1;
                        positionForSingleGoalReport = i;
                    }
                }

                //No goals are incomplete
                if(numOfSubGoalsIncomplete == 0){
                    Intent i = new Intent(singleGoalView.this, timerView.class);
                    i.putExtra("bigGoal",myGoal);

                    activityResultLaunchTimer.launch(i);

                }
                //More than 1 subgoal: set up recycler view
                else
                {
                    //Change the ui
                    openStartWorkingPopup();
                    //Add the appropriate goals
                    setUpStartWorkingRecyclerView();
                }

            }
        });

        goalProgressBtn = findViewById(R.id.goalProgressBtn);
        goalProgressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Actual code for goalProgressBtn
                Intent myIntent = new Intent(singleGoalView.this, goalProgressView.class);
                myIntent.putExtra("bigGoal",myGoal);
                startActivity(myIntent);
            }
        });

        //Initalize the text on the single goal view screen
        goalInfo = (TextView) findViewById(R.id.goalInfo);
        goalInfo.setText("\n" + myGoal.getBigGoal() + "\n\n" + controller.unixToString((int) myGoal.getWhenIsDue()));
        subGoalLabel = findViewById(R.id.SubGoalRecyclerLabel);
        subGoalLabel.setText("\t\tSubgoals");

        notesLabel = findViewById(R.id.NotesRecyclerLabel);
        notesLabel.setText("\t\tNotes");

        subGoalLabel.setVisibility(View.INVISIBLE);
        subGoalRecyclerView = findViewById(R.id.subGoalRecyclerViewFull);
        setUpSubgoalRecyclerView();

        notesRecyclerView = findViewById(R.id.NotesRecyclerView);
        setUpNotesRecyclerView();

        notesRecyclerView.setVisibility(View.VISIBLE);

        //If there are subgaols then show them
        if(myGoal.getSubGoals().size()!= 0){
            subGoalLabel.setVisibility(View.VISIBLE);
            subGoalRecyclerView.setVisibility(View.VISIBLE);
        }

        noteInput = findViewById(R.id.noteInput);

        uploadNoteBtn = findViewById(R.id.uploadNote);

        uploadNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("clickedNote", "onClick: Clicked upload note");
                long currentUnix = System.currentTimeMillis() / 1000L;

                //Create note and send to server
                String noteID = controller.getAlphaNumericString(11);
                PersonalNoteModel note = new PersonalNoteModel(noteID,noteInput.getText().toString(),currentUnix);

                DataServices.getInstance().savePersonalNote(myGoal.getGoalId(),note);

                //Update recycler notes view
                NotesAdapter adapterNotes = (NotesAdapter) notesRecyclerView.getAdapter();
                adapterNotes.addItem(note);
                noteInput.getText().clear();
                closeKeyboard(view);
            }
        });



        completeBtn = findViewById(R.id.completeBtn);
        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.completeBtnTapped(myGoal, new ResultHandler<Object>() {

                    @Override
                    public void onSuccess(Object data) {
                        HashMap<String,Object> hashMap = (HashMap<String, Object>) data;
                        if((boolean)hashMap.get("proceedToComplete") == true){
                            completeBtn.setBackgroundColor(Color.GREEN);
                            checkIfGoalComplete(myGoal.isCompleted());

                            //Launch activity to get smileFace Survey
                            Intent intent = new Intent(singleGoalView.this, smileyFaceSurveyView.class);
                            intent.putExtra("questionType",ss.FACE_QUESTION_TYPE_REF_FOR_COMPLETION);
                            intent.putExtra("isGroupStudy",false);
                            intent.putExtra("goalId",myGoal.getGoalId());

                            activityResultLaunchSmileyRating.launch(intent);
                        }

                    }

                    @Override
                    public void onFailure(Exception e) {

                    }
                });


            }
        });

        checkIfGoalComplete(myGoal.isCompleted());


    }

    /**
     * Verifies if the goal was completed or not and appropriately sets the color of the completed Btn
     * and makes the startWorking, setReminderBtn clickable or not clickable
     * @param goalComplete
     */
    public void checkIfGoalComplete(boolean goalComplete){
        if(goalComplete == true){
            startWorking.setClickable(false);
            setReminderBtn.setClickable(false);
            startWorking.setBackgroundColor(Color.GRAY);
            setReminderBtn.setBackgroundColor(Color.GRAY);
            completeBtn.setClickable(false);
            completeBtn.setBackgroundColor(Color.GREEN);
        }
    }

    class SubGoalFullAdapter extends RecyclerView.Adapter {
        List<IndividualSubGoalStructModel> items;

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
            final IndividualSubGoalStructModel item = items.get(position);
            Log.d("Position", "onBindViewHolder: position" + item);
            viewHolder.subGoalText.setText(items.get(position).get_subGoalName() + "\nDeadline: "+ controller.unixToStringDateTime((int)items.get(position).get_deadline()));
            viewHolder.setSubGoalPosition(position);
            if(items.get(position).is_isChecked()==true){
                Log.d("subgoalCompletedStatus", "onBindViewHolder: " + items.get(position).is_isChecked());
                viewHolder.completeCheckbox.setChecked(true);
            }
            else{
                Log.d("subgoalCompletedStatus", "onBindViewHolder: " + items.get(position).is_isChecked());
                viewHolder.completeCheckbox.setChecked(false);
            }
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public void remove(int position) {
            IndividualSubGoalStructModel item = items.get(position);
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

        public void addItem(IndividualSubGoalStructModel subgoal){
            items.add(subgoal);
            notifyItemInserted(items.size() - 1);
            Log.d("AfterItemsAdd", "addItems: " + items);
        }
    }


    class SubGoalFullViewHolder extends RecyclerView.ViewHolder {

        TextView subGoalText;
        CheckBox completeCheckbox;
        int subGoalPosition;

        public void setSubGoalPosition(int subGoalPosition) {
            this.subGoalPosition = subGoalPosition;
        }

        public int getSubGoalPosition() {
            return subGoalPosition;
        }

        public SubGoalFullViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.subgoal_checkbox_item, parent, false));
            subGoalText = (TextView) itemView.findViewById(R.id.subGoal_checkbox_text);
            completeCheckbox = itemView.findViewById(R.id.checkBox);

            completeCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    compoundButton.setChecked(b);
                    Log.d("SubGoalIndex", "onCheckedChanged: " + getSubGoalPosition());
                    //Updates the database and marks subgoal complete
                    myGoal.getSubGoals().get(getSubGoalPosition()).set_isChecked(b);
                    DataServices.getInstance().completeSubGoal(myGoal.getGoalId(),myGoal.getSubGoals().get(getSubGoalPosition()).get_subGoalId(),myGoal.getSubGoals().get(getSubGoalPosition()).is_isChecked());
                    Log.d("newIsCompletedValue", "onCheckedChanged: " + myGoal.getSubGoals().get(getSubGoalPosition()).is_isChecked());
                    Log.d("newCheckCount", "onCheckedChanged: " + myGoal);
                }
            });

            subGoalText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(singleGoalView.this, timerView.class);
                    startActivity(i);
                }
            });

        }
    }

    /**
     * Set up the subgoal recycler viewer
     */
    private void setUpSubgoalRecyclerView() {
        subGoalRecyclerView.setVisibility(View.VISIBLE);
        subGoalLabel.setVisibility(View.VISIBLE);
        subGoalRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SubGoalFullAdapter();
        subGoalRecyclerView.setAdapter(adapter);
        subGoalRecyclerView.setHasFixedSize(true);
    }


    class NotesAdapter extends RecyclerView.Adapter {
        List<PersonalNoteModel> items;

        public NotesAdapter() {
            items = new ArrayList<>();
            Log.d("NotesAdapter", "NotesAdapter: ");

            for(int i = 0; i < itemNotesFromServerOriginaly.size(); i++){
                Log.d("trying to add", "setUpRecyclerView: " +itemNotesFromServerOriginaly.get(i));
                items.add(itemNotesFromServerOriginaly.get(i));
                Log.d("Added", "setUpRecyclerView: " + items.get(i));
            }
            notifyDataSetChanged();



        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new NotesViewHolder(parent);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Log.d("PositionNotes", "onBindViewHolder: position" + position);
            NotesViewHolder viewHolder = (NotesViewHolder) holder;
            final PersonalNoteModel item = items.get(position);
            Log.d("PositionNotes", "onBindViewHolder: position" + item);
            viewHolder.noteText.setText(items.get(position).getNote());
            viewHolder.createdAt.setText(controller.calculateMinutesAgo((int)items.get(position).getCreatedAt()));
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public void addItem(PersonalNoteModel note){
            items.add(note);
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

    /**
     * Set up Notes Recycler view
     */
    private void setUpNotesRecyclerView() {
        DataServices.getInstance().requestPersonalNotes(myGoal.getGoalId(), new ResultHandler<Object>() {

            @Override
            public void onSuccess(Object data) {
                HashMap<String,Object> hashMap = (HashMap<String, Object>) data;
                if(hashMap.get("personalNotes")!=null){
                    ArrayList<PersonalNoteModel> personalNotes = (ArrayList<PersonalNoteModel>) hashMap.get("personalNotes");
                    for(int i = 0; i <personalNotes.size();i++){
                        Log.d("trying to add ItemsTemp", "personalNotes " +personalNotes.get(i));
                        itemNotesFromServerOriginaly.add(personalNotes.get(i));
                        Log.d("Added to ItemsTemp", "personalNotes: " +personalNotes.get(i));
                    }
                }
                Log.d("NotesItems", "onSuccess: " + itemNotesFromServerOriginaly);
                Collections.reverse(itemNotesFromServerOriginaly);
                notesRecyclerView.setVisibility(View.VISIBLE);
                LinearLayoutManager notesLinearLayoutManager = new LinearLayoutManager(context);
                notesRecyclerView.setLayoutManager(notesLinearLayoutManager);
                NotesAdapter notesAdapter= new NotesAdapter();
                notesRecyclerView.setAdapter(notesAdapter);
                notesRecyclerView.setHasFixedSize(true);
                Log.d("finishedSetup", "setUpNotesRecyclerView: finished setUpNotesRecyclerView");
            }

            @Override
            public void onFailure(Exception e) {

            }
        });


    }



    /**
     * Hides keyboard from view
     * @param view
     */
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
        List<IndividualSubGoalStructModel> startWorkingItems;

        public startWorkingFullAdapter() {
            startWorkingItems = new ArrayList<>();
            Log.d("subgoals toFilter", "startWorkingFullAdapter: " + myGoal.getSubGoals());

            for(int i = 0; i < myGoal.getSubGoals().size(); i++){
                if(myGoal.getSubGoals().get(i).is_isChecked()!=true){
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
            final IndividualSubGoalStructModel item = startWorkingItems.get(position);
            Log.d("Position", "onBindViewHolder: position" + item);
            viewHolder.subGoalText.setText("\n" + startWorkingItems.get(position).get_subGoalName() + "\nDeadline: "+ controller.unixToStringDateTime((int)startWorkingItems.get(position).get_deadline()));
            viewHolder.setSubGoalPosition(position);
        }

        @Override
        public int getItemCount() {
            return startWorkingItems.size();
        }

        public void remove(int position) {
            IndividualSubGoalStructModel item = startWorkingItems.get(position);
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

    }

    class startWorkingSubGoalViewHolder extends RecyclerView.ViewHolder {

        TextView subGoalText;
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

                    Intent i = new Intent(singleGoalView.this, timerView.class);
                    i.putExtra("bigGoal",myGoal);
                    i.putExtra("subGoal_selected",startAdapter.startWorkingItems.get(subGoalPosition));
                    i.putExtra("positionToUpdateStudiedTime",positionToUpdateStudiedTime);
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


}


