package com.example.proccoli2.ui.goalSetting;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proccoli2.NewModels.DataServices;
import com.example.proccoli2.NewModels.IndividualGoalModel;
import com.example.proccoli2.NewModels.IndividualSubGoalStructModel;
import com.example.proccoli2.R;
import com.example.proccoli2.ui.subGoalView_goalSetting.subGoalView_goalSetting;
import com.example.proccoli2.ui.subGoalView_goalSetting_edit.subGoalView_goalSetting_edit;
import com.example.proccoli2.ui.individualWall.singleGoalView;
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class goalSettingView extends AppCompatActivity {

    ImageButton exitGoalSettingBtn;
    Button addSubgoalsBtn;
    IndividualGoalModel passedGoal, passedGoal2;
    TextView completeBy, dueDate;
    Button revisePersonalDeadlineBtn, reviseDueDateBtn;
    RecyclerView subGoalRecyclerView;
    Context context = this;
    goalSetting_VC controller = new goalSetting_VC(this);
    int revisedPersonal;
    int revisedDue;
    ActivityResultLauncher<Intent> activityResultLaunchEditSubGoal;
    int indexChange = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goalsetting_view);
        completeBy = findViewById(R.id.completeByGoalSetting);
        dueDate = findViewById(R.id.dueDateGoalSetting);

        //Catches data back from create a new subgoal
        ActivityResultLauncher<Intent> activityResultLaunch = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            passedGoal2 = (IndividualGoalModel) result.getData().getSerializableExtra("bigGoal");
                            Log.d("HERE PassedGoal", String.valueOf(passedGoal2));

                            SubGoalFullAdapter adapter = (SubGoalFullAdapter) subGoalRecyclerView.getAdapter();
                            passedGoal.getSubGoals().add(passedGoal2.getSubGoals().get(passedGoal2.getSubGoals().size()-1));
                            adapter.add(passedGoal.getSubGoals().get(passedGoal.getSubGoals().size()-1));
                            subGoalRecyclerView.setVisibility(View.VISIBLE);
                        }
                    }
                });

        //Catches data back from edit subgoal
        activityResultLaunchEditSubGoal = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Log.d("CATCHINGEditSub", "onActivityResult: ");
                        if (result.getResultCode() == RESULT_OK) {
                            passedGoal2 = (IndividualGoalModel) result.getData().getSerializableExtra("bigGoal");
                            int editedSubGoalPosition = (int) result.getData().getSerializableExtra("subGoalPosition");
                            SubGoalFullAdapter adapter = (SubGoalFullAdapter) subGoalRecyclerView.getAdapter();
                            Log.d("editted", "onActivityResult: " + adapter.items);
                            Log.d("editted", "onActivityResult: " + editedSubGoalPosition);
                            passedGoal.getSubGoals().set(editedSubGoalPosition,passedGoal2.getSubGoals().get(passedGoal2.getSubGoals().size()-1));

                                    Log.d("MATCH", "onActivityResult: ");
                                    adapter.items.set(editedSubGoalPosition, passedGoal2.getSubGoals().get(passedGoal2.getSubGoals().size() - 1));
                                    adapter.notifyItemChanged(editedSubGoalPosition);
                            Log.d("HERE PassedGoal w/Edit", String.valueOf(passedGoal));
                            subGoalRecyclerView.setVisibility(View.VISIBLE);
                        }
                    }
                });


        Bundle bundle = getIntent().getExtras();
        passedGoal = (IndividualGoalModel) bundle.getSerializable("bigGoal");
        Log.d("goalSettingGoalRecieve", "onCreate: " + passedGoal);
        Log.d("goalSettingGoalRecieve", "onCreate: " + passedGoal.getSubGoals());

        //Makes it so the subgoals marked deleted in the server do not appear in the recycler view
        int numOriginalSubGoals = passedGoal.getSubGoals().size();
        ArrayList<IndividualSubGoalStructModel> actualSubgoals = new ArrayList<>();
        for (int i = 0; i<numOriginalSubGoals; i++) {
            if(passedGoal.getSubGoals().get(i).is_isDeleted()==false){
                actualSubgoals.add(passedGoal.getSubGoals().get(i));
            }
        }

        passedGoal.setSubGoals(actualSubgoals);


        revisedPersonal= (int)passedGoal.getPersonalDeadline();
        revisedDue =(int) passedGoal.getWhenIsDue();
        completeBy.setText(controller.unixToStringDateTime((int)passedGoal.getPersonalDeadline()));
        dueDate.setText(controller.unixToStringDateTime((int)passedGoal.getWhenIsDue()));

        addSubgoalsBtn = findViewById(R.id.addSubGoalSettingPage);
        addSubgoalsBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(goalSettingView.this, subGoalView_goalSetting.class);
                Bundle bundle = new Bundle();
                bundle.putString("completeBy", controller.unixToStringDateTime(revisedPersonal));
                i.putExtras(bundle);
                //Passes bigGoal info to keep working list of subgoals
                i.putExtra("bigGoal", passedGoal);
                //  startActivity(i);
                activityResultLaunch.launch(i);
            }
        });

        exitGoalSettingBtn = findViewById(R.id.exitGoalSettingBtn);
        exitGoalSettingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(controller.compareDates((int)passedGoal.getProposedStartDate(),revisedPersonal,revisedDue) == true){

                        if(passedGoal.getPersonalDeadline()!=revisedPersonal){
                            DataServices.getInstance().revisePersonalOrHardDeadline(revisedPersonal,passedGoal.getPersonalDeadline(),true,passedGoal.getGoalId());

                        }
                        if(passedGoal.getWhenIsDue()!=revisedDue){
                            DataServices.getInstance().revisePersonalOrHardDeadline(revisedDue,passedGoal.getWhenIsDue(),false,passedGoal.getGoalId());

                        }

                        passedGoal.setWhenIsDue(revisedDue);
                        passedGoal.setPersonalDeadline(revisedPersonal);

                        Intent i = new Intent(goalSettingView.this, singleGoalView.class);
                        i.putExtra("bigGoal",passedGoal);
                        Log.d("putExtras", "onClick: " + passedGoal);
                        setResult(RESULT_OK,i);
                        Log.d("setExtras", "onClick: " + passedGoal);
                        finish();
                    }

                    else{
                        dueDate.setTextColor(Color.RED);
                        completeBy.setTextColor(Color.RED);
                        Toast toast = Toast.makeText(getBaseContext(), "At least one date value is invalid.", Toast.LENGTH_LONG);
                        toast.show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });



        revisePersonalDeadlineBtn = findViewById(R.id.reviseCompleteByBtn);
        revisePersonalDeadlineBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Log.d("GoalSetting", "onClick: goalCompleteBy");
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
                        .title("Personal Deadline")
                        .listener(new SingleDateAndTimePickerDialog.Listener() {
                            @Override
                            public void onDateSelected(Date date) {
                                Log.d("NewDate", "onDateSelected: I selected this date" + date );
                                revisedPersonal = (controller.dateStrToUnix(controller.dateToStr(date)));
                                completeBy.setText(controller.unixToStringDateTime(revisedPersonal));
                            //    DataServices.getInstance().revisePersonalOrHardDeadline(revisedPersonal,passedGoal.getPersonalDeadline(),true,passedGoal.getGoalId());

                                Log.d("NewDate", "revised: " + revisedPersonal  + "completeBy: " + completeBy.getText());
                            }
                        }).display();

            }
        });


        reviseDueDateBtn = findViewById(R.id.reviseDueDateBtn);
        reviseDueDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("GoalSetting", "onClick: goalCompleteBy");
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
                        .title("Hard Deadline")
                        .listener(new SingleDateAndTimePickerDialog.Listener() {
                            @Override
                            public void onDateSelected(Date date) {
                                revisedDue = (controller.dateStrToUnix(controller.dateToStr(date)));

                                dueDate.setText(controller.unixToStringDateTime(revisedDue));
                            }
                        }).display();
            }
        });

        subGoalRecyclerView = findViewById(R.id.subGoalRecyclerViewGoalSetting);
        setUpSubgoalRecyclerView();


    }

    //https://stackoverflow.com/questions/7784421/getting-unix-timestamp-from-date
    public int dateStrToUnix(String time) {
        long unixTime = 0;
     //   SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");//Specify your timezone
        SimpleDateFormat sf = new SimpleDateFormat("MMM, dd, yyyy --hh:mm aa");

        try {
            unixTime = sf.parse(time).getTime();
            unixTime = unixTime / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (int)unixTime;
    }

    class SubGoalFullAdapter extends RecyclerView.Adapter {
        List<IndividualSubGoalStructModel> items;

        public SubGoalFullAdapter() {
            items = new ArrayList<>();

            for(int i = 0; i < passedGoal.getSubGoals().size(); i++){
                Log.d("trying to add", "setUpRecyclerView: " + passedGoal.getSubGoals().get(i));
                items.add(passedGoal.getSubGoals().get(i));
                Log.d("Added", "setUpRecyclerView: " + passedGoal.getSubGoals().get(i));
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            SubGoalFullViewHolder holder = new SubGoalFullViewHolder(parent);
            holder.setActivityResultLaunchEditSubgoal(activityResultLaunchEditSubGoal);
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Log.d("Position", "onBindViewHolder: position" + position);
            SubGoalFullViewHolder viewHolder = (SubGoalFullViewHolder) holder;
            final IndividualSubGoalStructModel item = items.get(position);
            Log.d("Position", "onBindViewHolder: position" + item);
            viewHolder.subGoalTextGoalSettingView.setText(items.get(position).get_subGoalName() + "\nDeadline: "+ controller.unixToStringDateTime((int) items.get(position).get_deadline()) + "\nDifficulty Level: " + String.valueOf(items.get(position).get_difficultyLevel()));
            viewHolder.setSubGoalPosition(position);
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
                for(int i = 0; i<passedGoal.getSubGoals().size();i++){
                    if(passedGoal.getSubGoals().get(i) == item){
                        passedGoal.getSubGoals().remove(item);
                        Log.d("mark delete", "remove: " + item.get_subGoalId());
                        DataServices.getInstance().deleteSubGoal(item.get_subGoalId(),passedGoal.getGoalId());
                        indexChange = indexChange-1;
                    }
                }
            }



        }

        public void add(IndividualSubGoalStructModel subGoalModel) {
            items.add(subGoalModel);
            notifyItemInserted(items.size()-1);
        }

    }

    class SubGoalFullViewHolder extends RecyclerView.ViewHolder {
        ActivityResultLauncher activityResultLaunchEditSubgoal;
        TextView subGoalTextGoalSettingView;
        ImageButton editBtn, deleteBtn;
        int subGoalPosition;

        public void setSubGoalPosition(int subGoalPosition) {
            this.subGoalPosition = subGoalPosition;
        }

        public int getSubGoalPosition() {
            return subGoalPosition;
        }


        public void setActivityResultLaunchEditSubgoal(ActivityResultLauncher activityResultLaunchEditSubgoal){
            this.activityResultLaunchEditSubgoal = activityResultLaunchEditSubgoal;
        }

        public SubGoalFullViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.subgoalitem_goalsetting_view, parent, false));
            subGoalTextGoalSettingView = (TextView) itemView.findViewById(R.id.subGoalTextGoalSettingView);
            editBtn = (ImageButton) itemView.findViewById(R.id.editSubGoalGoalSettingView);
            deleteBtn = (ImageButton) itemView.findViewById(R.id.deleteSubGoalGoalSettingView);

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("deleteMe", "onClick: " + passedGoal.getSubGoals());
                    Log.d("editted", "onClick: "+ passedGoal.getSubGoals().get(getSubGoalPosition()+indexChange));
                    passedGoal.getSubGoals().get(getSubGoalPosition()+indexChange).set_isDeleted(true);
                    DataServices.getInstance().deleteSubGoal(passedGoal.getSubGoals().get(getSubGoalPosition()+indexChange).get_subGoalId(),passedGoal.getGoalId());

                    int swipedPosition = getSubGoalPosition()+indexChange;
                    SubGoalFullAdapter adapter = (SubGoalFullAdapter) subGoalRecyclerView.getAdapter();

                    adapter.items.set(swipedPosition, passedGoal.getSubGoals().get(getSubGoalPosition()+indexChange));
                    adapter.notifyItemChanged(swipedPosition);
                    int numOriginalSubGoals = passedGoal.getSubGoals().size();
                    ArrayList<IndividualSubGoalStructModel> actualSubgoals = new ArrayList<>();
                    for (int i = 0; i<numOriginalSubGoals; i++) {
                        if(passedGoal.getSubGoals().get(i).is_isDeleted()==false){
                            actualSubgoals.add(passedGoal.getSubGoals().get(i));
                        }
                    }

                    passedGoal.setSubGoals(actualSubgoals);
                    setUpSubgoalRecyclerView();
                    //adapter.remove(swipedPosition);



                }
            });

            editBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Log.d("editted", "onClick: "+ passedGoal.getSubGoals().get(getSubGoalPosition()+indexChange));

                    Intent i = new Intent(goalSettingView.this, subGoalView_goalSetting_edit.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("completeBy", controller.unixToStringDateTime(revisedPersonal));
                    i.putExtras(bundle);
                    i.putExtra("subGoalPosition",getSubGoalPosition());
                    i.putExtra("subgoal", passedGoal.getSubGoals().get(getSubGoalPosition()));
                    //Passes bigGoal info to keep working list of subgoals
                    i.putExtra("bigGoal", passedGoal);
                    Log.d("subGoalEdit", "onClick: subGoalToEdit" +passedGoal.getSubGoals().get(getSubGoalPosition()));
                    activityResultLaunchEditSubgoal.launch(i);


                }
            });


        }
    }

    private void setUpSubgoalRecyclerView() {
        subGoalRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        subGoalRecyclerView.setAdapter(new SubGoalFullAdapter());
        subGoalRecyclerView.setHasFixedSize(true);

    }

}
