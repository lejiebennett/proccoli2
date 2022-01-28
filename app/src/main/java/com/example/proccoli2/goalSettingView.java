package com.example.proccoli2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    GoalModel passedGoal, passedGoal2;
    TextView completeBy, dueDate;
    Button revisePersonalDeadlineBtn, reviseDueDateBtn;
    RecyclerView subGoalRecyclerView;
    Context context = this;
    goalSetting_VC controller = new goalSetting_VC(this);
    int revisedPersonal;
    int revisedDue;
    ActivityResultLauncher<Intent> activityResultLaunchEditSubGoal;


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
                            passedGoal2 = (GoalModel) result.getData().getSerializableExtra("bigGoal");
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
                            passedGoal2 = (GoalModel) result.getData().getSerializableExtra("bigGoal");
                            int editedSubGoalPosition = (int) result.getData().getSerializableExtra("subGoalPosition");
                            Log.d("HERE PassedGoal", String.valueOf(passedGoal2));

                            SubGoalFullAdapter adapter = (SubGoalFullAdapter) subGoalRecyclerView.getAdapter();
                            passedGoal.getSubGoals().remove(editedSubGoalPosition);
                            adapter.remove(editedSubGoalPosition);
                            passedGoal.getSubGoals().add(passedGoal2.getSubGoals().get(passedGoal2.getSubGoals().size()-1));
                            Log.d("HERE PassedGoal w/Edit", String.valueOf(passedGoal));
                            adapter.add(passedGoal.getSubGoals().get(passedGoal.getSubGoals().size()-1));
                            subGoalRecyclerView.setVisibility(View.VISIBLE);
                        }
                    }
                });


        Bundle bundle = getIntent().getExtras();
        passedGoal = (GoalModel) bundle.getSerializable("bigGoal");
        Log.d("goalSettingGoalRecieve", "onCreate: " + passedGoal);
        revisedPersonal= passedGoal.getCompletedBy();
        revisedDue = passedGoal.getDeadline();

        completeBy.setText(controller.unixToStringDateTime(passedGoal.getCompletedBy()));
        dueDate.setText(controller.unixToStringDateTime(passedGoal.getDeadline()));

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
                    if(controller.compareDates(passedGoal.getStartDate(),revisedPersonal,revisedDue) == true){
                        passedGoal.setDeadline(revisedDue);
                        passedGoal.setCompletedBy(revisedPersonal);
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
                        .title("Goal Complete By")
                        .listener(new SingleDateAndTimePickerDialog.Listener() {
                            @Override
                            public void onDateSelected(Date date) {
                                Log.d("NewDate", "onDateSelected: I selected this date" + date );
                                revisedPersonal = (controller.dateStrToUnix(controller.dateToStr(date)));
                                completeBy.setText(controller.unixToStringDateTime(revisedPersonal));

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
                        .title("Goal Complete By")
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
        List<SubGoalModel> items;

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
            final SubGoalModel item = items.get(position);
            Log.d("Position", "onBindViewHolder: position" + item);
            viewHolder.subGoalTextGoalSettingView.setText(items.get(position).getSubGoalName() + "\nDeadline: "+ controller.unixToStringDateTime(items.get(position).getDeadline()) + "\nDifficulty Level: " + String.valueOf(items.get(position).getDifficultyLevel()));
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
                for(int i = 0; i<passedGoal.getSubGoals().size()-1;i++){
                    if(passedGoal.getSubGoals().get(i) == item){
                        passedGoal.getSubGoals().remove(item);
                    }
                }
            }



        }

        public void add(SubGoalModel subGoalModel) {
            items.add(subGoalModel);
            notifyItemInserted(items.size()-1);
        }

    }

    class SubGoalFullViewHolder extends RecyclerView.ViewHolder {
        ActivityResultLauncher activityResultLaunchEditSubgoal;
        TextView subGoalTextGoalSettingView;
        ImageButton editBtn, deleteBtn;

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
                    int swipedPosition = view.getVerticalScrollbarPosition();
                    SubGoalFullAdapter adapter = (SubGoalFullAdapter) subGoalRecyclerView.getAdapter();
                    adapter.remove(swipedPosition);
                }
            });

            editBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(goalSettingView.this, subGoalView_goalSetting_edit.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("completeBy", controller.unixToStringDateTime(revisedPersonal));
                    i.putExtras(bundle);
                    i.putExtra("subGoalPosition",view.getVerticalScrollbarPosition());
                    i.putExtra("subgoal", passedGoal.getSubGoals().get(view.getVerticalScrollbarPosition()));
                    //Passes bigGoal info to keep working list of subgoals
                    i.putExtra("bigGoal", passedGoal);
                    Log.d("subGoalEdit", "onClick: subGoalToEdit" +passedGoal.getSubGoals().get(view.getVerticalScrollbarPosition()));
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
