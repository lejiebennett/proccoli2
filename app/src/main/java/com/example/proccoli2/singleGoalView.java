package com.example.proccoli2;

import android.app.AlertDialog;
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
import android.widget.ProgressBar;
import android.widget.TextView;

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


import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class singleGoalView extends AppCompatActivity {

    GoalModel passedGoal;
    GoalModel myGoal;
    GoalModel originalGoal;
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

    //Start Working popup
    startWorkingFullAdapter startAdapter;
    RecyclerView startWorkingRecyclerView;

    //Report Progress popup
    reportProgressFullAdapter reportProgressAdapter;
    RecyclerView reportProgressRecyclerView;
    List<SubGoalModel> reportProgressItems;
    int subGoalPositionForProgressReport;

    //See Progress popup
    List<SubGoalModel> seeProgressItems;
    seeProgressFullAdapter seeProgressAdapter;
    RecyclerView seeProgressRecyclerView;


    //Set Reminder
    SingleDateAndTimePicker setReminderPicker;
    TextView cancelSetReminderBtn, doneSetReminderBtn, setReminderLabel;




    /***
     * Used to catch data from the reportProgressGroupGoal where users can the report their percentage of progress
     */
    ActivityResultLauncher<Intent> activityResultLaunchProgressReport = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Log.d("onActivityResult", "ActivityResultLaunchprogressReport: RUNNING");
                        int pickedPercentage = result.getData().getIntExtra("pickedPercentage",0);
                        myGoal.getSubGoals().get(subGoalPositionForProgressReport).setPercentageComplete(pickedPercentage);
                        Log.d("NewCompletePercentage", myGoal.getSubGoals().get(subGoalPositionForProgressReport).getSubGoalName() + " " + String.valueOf(myGoal.getSubGoals().get(subGoalPositionForProgressReport).getPercentageComplete()));
                        if(myGoal.getSubGoals().get(subGoalPositionForProgressReport).getPercentageComplete()==100){
                            reportProgressAdapter.remove(subGoalPositionForProgressReport);
                            Log.d("removed!!!", "completion rate was 100%: RUNNING");
                        }
                    }
                }
            });

    /***
     * Used to catch data from the smileFace survey where users can rate their satisfaction with the goal they just completed
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
                        goalInfo.setText("\n" + myGoal.getBigGoal() + "\n\n" + controller.unixToString(myGoal.getDeadline()));
                        myGoal.setSubgoals(passedGoal.getSubGoals());
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
        originalGoal = (GoalModel) bundle.getSerializable("bigGoal");
        myGoal = originalGoal;

        Log.d("OriginGoalFromMain", String.valueOf(originalGoal));


        setReminderPicker = findViewById(R.id.setReminderPickerS);
        cancelSetReminderBtn = findViewById(R.id.cancelSetReminderBtnS);
        doneSetReminderBtn = findViewById(R.id.doneSetReminderBtnS);
        setReminderLabel = findViewById(R.id.setReminderLabelS);

        setReminderPicker.setVisibility(View.INVISIBLE);
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
            @Override
            public void onClick(View view) {
                Log.d("setReminder", "onClick: I selected this date" + setReminderPicker.getDate().toString());
                cancelSetReminderBtn.setVisibility(View.INVISIBLE);
                doneSetReminderBtn.setVisibility(View.INVISIBLE);
                setReminderPicker.setVisibility(View.INVISIBLE);
                setReminderLabel.setVisibility(View.INVISIBLE);
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
                Log.d("passBackNotes", "onMenuItemClick: " + myGoal.getPersonalNotes());
                Intent i = new Intent(singleGoalView.this,MainActivity.class);
                i.putExtra("bigGoal",myGoal);
                Log.d("Updated myGoal Goal", "onMenuItemClick: Passed" + myGoal);
                setResult(RESULT_OK,i);
                finish();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(myGoal.getIsCompleted()==false){
                    int itemId = item.getItemId();
                    if (itemId == R.id.settingBtn) {
                        Intent i = new Intent(singleGoalView.this,goalSettingView.class);

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
               // openInviteUserPopUp();
               // openReportProgressPopUp();
                openSeeProgressPopUp();
                /*
                Intent i = new Intent(singleGoalView.this, timerView.class);
                startActivity(i);

                 */
            }
        });

        goalProgressBtn = findViewById(R.id.goalProgressBtn);
        goalProgressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 openReportProgressPopUp();

                /*
                //Actual code for goalProgressBtn
                Intent myIntent = new Intent(singleGoalView.this, goalProgressView.class);
                startActivity(myIntent);

                 */
            }
        });

        goalInfo = (TextView) findViewById(R.id.goalInfo);
        goalInfo.setText("\n" + myGoal.getBigGoal() + "\n\n" + controller.unixToString(myGoal.getDeadline()));
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
                String noteID = "noteID0001";
                PersonalNoteModel note = new PersonalNoteModel(noteInput.getText().toString(),(int)currentUnix,noteID);

                //Add note to the goalModel
                myGoal.getPersonalNotes().add(note);
                NotesAdapter adapterNotes = (NotesAdapter) notesRecyclerView.getAdapter();
                Log.d("personalNotes", "onClick: " + myGoal.getPersonalNotes());

                //Update the recycler
                adapterNotes.addItems();
                Log.d("added", "onClick: added PersonalNotes" + myGoal.getPersonalNotes().get(myGoal.getPersonalNotes().size()-1));
                noteInput.getText().clear();
                closeKeyboard(view);
            }
        });

        completeBtn = findViewById(R.id.completeBtn);
        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myGoal.setCompleted(true);
                completeBtn.setBackgroundColor(Color.GREEN);
                checkIfGoalComplete(myGoal.getIsCompleted());

                //Launch activity to get smileFace Survey
                Intent intent = new Intent(singleGoalView.this,smileyFaceSurveyView.class);
                activityResultLaunchSmileyRating.launch(intent);

            }
        });

        checkIfGoalComplete(myGoal.getIsCompleted());


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
            if(items.get(position).getIsChecked()==true){
                Log.d("subgoalCompletedStatus", "onBindViewHolder: " + items.get(position).getIsChecked());
                viewHolder.completeCheckbox.setChecked(true);
            }
            else{
                Log.d("subgoalCompletedStatus", "onBindViewHolder: " + items.get(position).getIsChecked());
                viewHolder.completeCheckbox.setChecked(false);

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
                    myGoal.getSubGoals().get(getSubGoalPosition()).setIsChecked(b);
                    Log.d("newIsCompletedValue", "onCheckedChanged: " + myGoal.getSubGoals().get(getSubGoalPosition()).getIsChecked());
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

    private void setUpNotesRecyclerView() {
        notesRecyclerView.setVisibility(View.INVISIBLE);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        notesRecyclerView.setAdapter(new NotesAdapter());
        notesRecyclerView.setHasFixedSize(true);
        Log.d("finishedSetup", "setUpNotesRecyclerView: finished setUpNotesRecyclerView");
    }

    public void closeKeyboard(View view){
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            noteInput.setCursorVisible(false);

        }
    }


    /////////////////Everything below is for startWorking Popup///////////

    class startWorkingFullAdapter extends RecyclerView.Adapter {
        List<SubGoalModel> startWorkingItems;

        public startWorkingFullAdapter() {
            startWorkingItems = new ArrayList<>();

            for(int i = 0; i < myGoal.getSubGoals().size(); i++){
                Log.d("trying to add", "setUpRecyclerView: " + myGoal.getSubGoals().get(i));
                startWorkingItems.add(myGoal.getSubGoals().get(i));
                Log.d("Added", "setUpRecyclerView: " + myGoal.getSubGoals().get(i));
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new startWorkingSubGoalViewHolder(parent);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Log.d("Position", "onBindViewHolder: position" + position);
            startWorkingSubGoalViewHolder viewHolder = (startWorkingSubGoalViewHolder) holder;
            final SubGoalModel item = startWorkingItems.get(position);
            Log.d("Position", "onBindViewHolder: position" + item);
            viewHolder.subGoalText.setText("\n" + startWorkingItems.get(position).getSubGoalName() + "\nDeadline: "+ controller.unixToStringDateTime(startWorkingItems.get(position).getDeadline()));
            if(startWorkingItems.get(position).getSubGoalAssignerID()!= null && startWorkingItems.get(position).getSubGoalAssignerID().equals("available")){
                viewHolder.availableToClaim.setVisibility(View.VISIBLE);
                viewHolder.availableToClaim.setClickable(true);
            }
            else
            {
                viewHolder.availableToClaim.setVisibility(View.INVISIBLE);
                viewHolder.availableToClaim.setClickable(false);
                viewHolder.creatorInfo.setText(startWorkingItems.get(position).getSubGoalAssignerID());
            }
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
        int subGoalPosition;

        public void setSubGoalPosition(int subGoalPosition) {
            this.subGoalPosition = subGoalPosition;
        }

        public int getSubGoalPosition() {
            return subGoalPosition;
        }

        public startWorkingSubGoalViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.groupgoalsubgoal_item, parent, false));
            subGoalText = (TextView) itemView.findViewById(R.id.subGoalItemInfoGG);

            subGoalText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(singleGoalView.this, timerView.class);
                    startActivity(i);
                }
            });

            creatorInfo = (TextView) itemView.findViewById(R.id.creatorInfoGG);
            creatorInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(singleGoalView.this, timerView.class);
                    startActivity(i);
                }
            });

            availableToClaim = (Button) itemView.findViewById(R.id.availableToClaimBtn);
            availableToClaim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("available", "onClick: Clicked available to claim");
                }
            });

            availableToClaim.setVisibility(View.INVISIBLE);
        }
    }

    private void setUpStartWorkingRecyclerView() {
        startWorkingRecyclerView.setVisibility(View.VISIBLE);
        startWorkingRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        startAdapter = new startWorkingFullAdapter();
        startWorkingRecyclerView.setAdapter(startAdapter);
        startWorkingRecyclerView.setHasFixedSize(true);

    }

    private void openInviteUserPopUp(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View viewPopup = this.getLayoutInflater().inflate(R.layout.startworking_popup, null);
        builder.setView(viewPopup);
        ImageView cancelStartWorking;

        startWorkingRecyclerView = (RecyclerView) viewPopup.findViewById(R.id.startWorkingRecyclerView);
        setUpStartWorkingRecyclerView();
        cancelStartWorking = (ImageView) viewPopup.findViewById(R.id.startWorkingBtnCancel);
        cancelStartWorking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        builder.setNegativeButton("Exit", null);
        builder.create().show();
    }

/////////////////Everything below is for reportProgress Popup///////////

    class reportProgressFullAdapter extends RecyclerView.Adapter {


        public reportProgressFullAdapter() {
            reportProgressItems = new ArrayList<>();

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
            Log.d("Position", "onBindViewHolder: position" + position);
            reportProgressSubGoalViewHolder viewHolder = (reportProgressSubGoalViewHolder) holder;
            final SubGoalModel item = reportProgressItems.get(position);
            Log.d("Position", "onBindViewHolder: position" + item);
            viewHolder.subGoalText.setText("\n" + reportProgressItems.get(position).getSubGoalName());
            viewHolder.setSubGoalPosition(position);
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
        int subGoalPosition;

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
                        subGoalPositionForProgressReport = subGoalPosition;
                        Bundle bundle = new Bundle();
                        bundle.putString("subgoalText", subGoalText.getText().toString());
                        bundle.putString("currentPercentageComplete",String.valueOf(reportProgressItems.get(subGoalPosition).getPercentageComplete()));
                        Intent i = new Intent(singleGoalView.this,progressReportView.class);
                        i.putExtras(bundle);
                        activityResultLaunchProgressReport.launch(i);
                   //     startActivity(i);
                }
            });
        }
    }

    private void setUpReportProgressRecyclerView() {
        reportProgressRecyclerView.setVisibility(View.VISIBLE);
        reportProgressRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reportProgressAdapter = new reportProgressFullAdapter();
        reportProgressRecyclerView.setAdapter(reportProgressAdapter);
        reportProgressRecyclerView.setHasFixedSize(true);

    }


    private void openReportProgressPopUp(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View viewPopup = this.getLayoutInflater().inflate(R.layout.reportprogress_popup, null);
        builder.setView(viewPopup);
        reportProgressRecyclerView = (RecyclerView) viewPopup.findViewById(R.id.reportProgressRecyclerView);
        setUpReportProgressRecyclerView();
        builder.setNegativeButton("Exit", null);
        builder.create().show();
    }

    /////////////////Everything below is for reportProgress Popup///////////
    class seeProgressFullAdapter extends RecyclerView.Adapter {

        public seeProgressFullAdapter() {
            seeProgressItems = new ArrayList<>();

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
            Log.d("Position", "onBindViewHolder: position" + item);
            viewHolder.subGoalText.setText(seeProgressItems.get(position).getSubGoalName());
            viewHolder.percentageText.setText(String.valueOf(seeProgressItems.get(position).getPercentageComplete()) + "%");
            viewHolder.creatorText.setText(seeProgressItems.get(position).getSubGoalAssignerID());
            viewHolder.progressBar.setProgress(seeProgressItems.get(position).getPercentageComplete(),true);
            viewHolder.progressBar.setScaleY(3f);

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
        seeProgressRecyclerView.setVisibility(View.VISIBLE);
        seeProgressRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        seeProgressAdapter = new seeProgressFullAdapter();
        seeProgressRecyclerView.setAdapter(seeProgressAdapter);
        seeProgressRecyclerView.setHasFixedSize(true);

    }


    private void openSeeProgressPopUp(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View viewPopup = this.getLayoutInflater().inflate(R.layout.seeprogress_popup, null);
        builder.setView(viewPopup);
        seeProgressRecyclerView = (RecyclerView) viewPopup.findViewById(R.id.seeProgressRecyclerView);
        setUpSeeProgressRecyclerView();
        builder.setNegativeButton("Exit", null);
        builder.create().show();
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


}
