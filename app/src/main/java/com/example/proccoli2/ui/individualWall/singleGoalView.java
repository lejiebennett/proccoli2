package com.example.proccoli2.ui.individualWall;

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


import com.example.proccoli2.MainActivity;
import com.example.proccoli2.NewModels.IndividualGoalModel;
import com.example.proccoli2.NewModels.IndividualSubGoalStructModel;
import com.example.proccoli2.NotificationPublisher;
import com.example.proccoli2.R;
import com.example.proccoli2.goalProgressView;
import com.example.proccoli2.ui.goalSetting.goalSettingView;
import com.example.proccoli2.smileyFaceSurveyView;
import com.example.proccoli2.timerView;
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Class for displaying an individual single goal view
 * Current controller is called singleGoalView_VC
 * Other notes: See scratch_singleGoalView to see popup versions of group goal start working, report progress etc
 */
public class singleGoalView extends AppCompatActivity {

    IndividualGoalModel passedGoal;
    IndividualGoalModel myGoal;
    IndividualGoalModel originalGoal;
    TextView goalInfo, subGoalLabel,notesLabel;
    RecyclerView subGoalRecyclerView, notesRecyclerView;
    singleGoalView_VC controller = new singleGoalView_VC(this);
    ImageButton uploadNoteBtn;
    TextInputEditText noteInput;
    Button startWorking;
    //MaterialToolbar toolbar;
    androidx.appcompat.widget.Toolbar toolbar;
    Button completeBtn;
    SubGoalFullAdapter adapter;
    Button setReminderBtn;
    Context context = this;
    Button goalProgressBtn;
    int smileRating;
    int studiedFromTimer;

    //Set Reminder
    SingleDateAndTimePicker setReminderPicker;
    TextView cancelSetReminderBtn, doneSetReminderBtn, setReminderLabel;
    NotificationChannel notificationChannel;
    NotificationManager notificationManager;

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
                        Log.d("newItems", "onActivityResult: " + myGoal.getSubGoals());
                        //Log.d("ResultFromTimer", "onActivityResult: old studied" +myGoal.getStudiedTime());
                        //Need to divide by 60000 since studied time is sent in milliseconds not minutes
                       // myGoal.setStudiedTime(myGoal.getStudiedTime() + (Double.valueOf(studiedFromTimer)/60000));
                       // Log.d("ResultFromTimer", "onActivityResult: new studied" +myGoal.getStudiedTime());

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
                        //NEED TO ADD A NEW ATTRIBUTE TO GOAL MODAL TO HOLD SMILE RATING
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

        Log.d("OriginGoalFromMain", String.valueOf(originalGoal));


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
                //INSERT THE CODE TO SET A LOCAL NOTIFICATION HERE//
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

                //Create notification channel
                notificationChannel = new NotificationChannel("12345", "Proccoli", NotificationManager.IMPORTANCE_HIGH);
                notificationChannel.enableLights(true);
                notificationChannel.enableVibration(true);
                notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.createNotificationChannel(notificationChannel);


                //Alarm approach
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(singleGoalView.this, NotificationPublisher.class);
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

        /*
        toolbar = findViewById(R.id.topAppBar);
        toolbar.setTitle("proccoli2");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        */
                /*
        toolbar.setOnMenuItemClickListener(new MaterialToolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.backBtn) {
                    Log.d("back clicked", "onMenuItemClick: Clicked back");
                    Log.d("passBackGoal", "onMenuItemClick: " + myGoal);
                    Log.d("passBackNotes", "onMenuItemClick: " + myGoal.getPersonalNotes());
                    Intent i = new Intent(singleGoalView.this,MainActivity.class);
                    i.putExtra("bigGoal",myGoal);
                    Log.d("Updated myGoal Goal", "onMenuItemClick: Passed" + myGoal);
                    setResult(RESULT_OK,i);
                    finish();
                } else if (itemId == R.id.settingBtn) {
                    Intent i = new Intent(singleGoalView.this,goalSettingView.class);
                    i.putExtra("bigGoal",myGoal);
                    Log.d("goalModelPutForSingle", "onClick: " + myGoal);
                    activityResultLaunch3.launch(i);
                }
                return false;
            }
        });
         */
        toolbar =findViewById(R.id.toolbarSingleGoalFullDisplay);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("back clicked", "onMenuItemClick: Clicked back");
                Log.d("passBackGoal", "onMenuItemClick: " + myGoal);
              //  Log.d("passBackNotes", "onMenuItemClick: " + myGoal.getPersonalNotes());
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
                /*
                Intent i = new Intent(singleGoalView.this, timerView.class);
                startActivity(i);

                 */
                Intent i = new Intent(singleGoalView.this, timerView.class);
                activityResultLaunchTimer.launch(i);

            }
        });

        goalProgressBtn = findViewById(R.id.goalProgressBtn);
        goalProgressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Actual code for goalProgressBtn
                Intent myIntent = new Intent(singleGoalView.this, goalProgressView.class);
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
        //setUpNotesRecyclerView();

        notesRecyclerView.setVisibility(View.VISIBLE);

        //If there are subgaols then show them
        if(myGoal.getSubGoals().size()!= 0){
            subGoalLabel.setVisibility(View.VISIBLE);
            subGoalRecyclerView.setVisibility(View.VISIBLE);
        }

        noteInput = findViewById(R.id.noteInput);

        uploadNoteBtn = findViewById(R.id.uploadNote);
        /*
        uploadNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("clickedNote", "onClick: Clicked upload note");
                long currentUnix = System.currentTimeMillis() / 1000L;
                String noteID = "noteID0001";
                PersonalNoteModel note = new PersonalNoteModel(noteInput.getText().toString(),(int)currentUnix,noteID);

                //Add note to the goalModel
                //myGoal.getPersonalNotes().add(note);
                NotesAdapter adapterNotes = (NotesAdapter) notesRecyclerView.getAdapter();
                //Log.d("personalNotes", "onClick: " + myGoal.getPersonalNotes());

                //Update the recycler
                adapterNotes.addItems();
               // Log.d("added", "onClick: added PersonalNotes" + myGoal.getPersonalNotes().get(myGoal.getPersonalNotes().size()-1));
                noteInput.getText().clear();
                closeKeyboard(view);
            }
        });

         */

        completeBtn = findViewById(R.id.completeBtn);
        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Mark goal as complete
                myGoal.setCompleted(true);
                completeBtn.setBackgroundColor(Color.GREEN);
                checkIfGoalComplete(myGoal.isCompleted());

                //Launch activity to get smileFace Survey
                Intent intent = new Intent(singleGoalView.this, smileyFaceSurveyView.class);
                activityResultLaunchSmileyRating.launch(intent);

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
                    myGoal.getSubGoals().get(getSubGoalPosition()).set_isChecked(b);
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

/*
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

 */

    /**
     * Set up Notes Recycler view
     */
    private void setUpNotesRecyclerView() {
        notesRecyclerView.setVisibility(View.INVISIBLE);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
     //   notesRecyclerView.setAdapter(new NotesAdapter());
        notesRecyclerView.setHasFixedSize(true);
        Log.d("finishedSetup", "setUpNotesRecyclerView: finished setUpNotesRecyclerView");
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


}

