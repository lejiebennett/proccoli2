package com.example.proccoli2.ui.home;

import static android.app.Activity.RESULT_OK;
import static android.graphics.Color.GRAY;
import static android.graphics.Color.parseColor;

import static com.example.proccoli2.R.drawable.avatar_background;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proccoli2.NewModels.IndividualGoalModel;
import com.example.proccoli2.R;
import com.example.proccoli2.databinding.FragmentHomeBinding;
import com.example.proccoli2.ui.individualGoalCreation.goalView2;
import com.example.proccoli2.groupgoalView;
import com.example.proccoli2.groupgoalsingleGoalView;
import com.example.proccoli2.ui.login.loginView;
import com.example.proccoli2.mainProgressView;
import com.example.proccoli2.ui.profile.profileView;
import com.example.proccoli2.singleGoalView;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment{

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    Button createLoginBtn, editProfileBtn;
    RecyclerView recyclerView;
    ImageView avatarHome;
    TextView goalBoard;
    int goalSelected;
    int completedCount = 0;
    String colorCode;
    int passedAvatar = 6;
    ArrayList<IndividualGoalModel> goalList = new ArrayList<>();
    ArrayList<IndividualGoalModel> recyclerList = new ArrayList<>();
    ArrayList<IndividualGoalModel> activePersonal = new ArrayList<>();
    ArrayList<IndividualGoalModel> activeDue = new ArrayList<>();
    ArrayList<IndividualGoalModel> expiredPersonal = new ArrayList<>();
    ArrayList<IndividualGoalModel> expiredDue = new ArrayList<>();
    ArrayList<IndividualGoalModel> finishedPersonal = new ArrayList<>();
    ArrayList<IndividualGoalModel> finishedDue = new ArrayList<>();
    HomeFragment_VC controller = new HomeFragment_VC(this);
    ImageButton personalDeadlineBtn, dueDateBtn;
    Button activeBtn, expiredBtn, finishedBtn;
    boolean personalSelected = true;
    boolean dueDateSelected = false;
    MaterialButtonToggleGroup toggleGroup;
    IndividualGoalModel passedGoal;
    Button mainProgressBtn;
    TextView texthome;
    Toolbar toolbar;

    //Used for create goal btn popup
    TextView selectType;
    ImageView cancelBtn, groupBtn, individualBtn;
    String goalCreationType;


    //Catch data from profileAvatarPage
    ActivityResultLauncher<Intent> activityResultLaunchAvatar = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        String avatarImage = result.getData().getStringExtra("avatarImage");
                        Log.d("RecievedImage", "onActivityResult: " + avatarImage);
                        colorCode = result.getData().getStringExtra("colorCode");
                        setAvatar(Integer.parseInt(avatarImage),colorCode);
                        passedAvatar = Integer.parseInt(avatarImage);
                    }
                }
            });

    //Catch data from singleGoalView
    ActivityResultLauncher<Intent> activityResultLaunch2 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if (result.getResultCode() == RESULT_OK) {
                        Log.d("FromSingleGoal", "onActivityResult: Returned from single Goal view");
                        passedGoal = (IndividualGoalModel) result.getData().getSerializableExtra("bigGoal");
                        /*
                        Log.d("Passed Notes", "onActivityResult: " + passedGoal.getPersonalNotes());
                        Log.d("HERE PassedGoal", String.valueOf(passedGoal));

                         */

                        controller.removeGoalFromList(goalSelected,goalList);
                        goalList.add(passedGoal);
                        Log.d("goalList", "Just added onActivityResult: goalLst" + goalList);

                        controller.findOldAndRemoveFromRecyclers(passedGoal);
                        controller.assignGoal(passedGoal);
                        Log.d("activePersonal", "activePersonal: "+activePersonal);
                        Log.d("AD", "AD: "+activeDue );
                        Log.d("eP", "EP: "+expiredPersonal );
                        Log.d("ED", "ED: "+expiredDue );
                        Log.d("FP", "FP: "+finishedPersonal );
                        Log.d("FD", "FD: "+ finishedDue );


                        recyclerView.setVisibility(View.VISIBLE);
                        switch(controller.setRecyclerViewList(HomeFragment.this)){
                            case "activePersonal":
                                Log.d("switch", "onActivityResult: activePerconal");
                                recyclerList = activePersonal;
                                //  recyclerList.add(activePersonal.get(activePersonal.size()-1));

                                break;
                            case "activeDue":
                                Log.d("switch", "onActivityResult: activedue");
                                recyclerList = activeDue;
                                //  recyclerList.add(activeDue.get(activeDue.size()-1));
                                break;
                            case "expiredPersonal:":
                                Log.d("switch", "onActivityResult: expiredPersonal");
                                recyclerList = expiredPersonal;
                                //  recyclerList.add(expiredPersonal.get(expiredPersonal.size()-1));
                                break;
                            case "expiredDue":
                                Log.d("switch", "onActivityResult: expiredDue");
                                recyclerList = expiredPersonal;
                                //  recyclerList.add(recyclerList.get(recyclerList.size()-1))
                                break;
                            case "finishedPersonal":
                                Log.d("switch", "onActivityResult: finishedPersonal");
                                recyclerList = finishedPersonal;
                                //   finishedPersonal.add(recyclerList.get(recyclerList.size()-1))
                                break;
                            case "finishedDue":
                                recyclerList = finishedDue;
                                Log.d("switch", "onActivityResult: finishedPersonal");
                                //   finishedDue.add(recyclerList.get(recyclerList.size()-1))
                                break;
                            default:
                                Log.d("switcherror", "onCreate: error in switch");
                        }
                        Log.d("recyclerList", "onActivityResult: " + recyclerList);


                        //  recyclerList = expiredPersonal;
                        Log.d("recyclerList", "onActivityResult: " + recyclerList);
                        CustomGoalAdapter adapter = (CustomGoalAdapter) recyclerView.getAdapter();
                        adapter.addItems();
                        Log.d("currentItemList", "onActivityResult: " + adapter.items);
                        adapter.notifyDataSetChanged();

                        Log.d("FIVES", "onActivityResult: " + goalList);
                        Log.d("SIX", "onActivityResult: " + finishedDue);
                        Log.d("SIX", "onActivityResult: " + finishedPersonal);


                        //Used to get a list of all of the group goals, individual goals to get counts
                        ArrayList<IndividualGoalModel> sumArray=controller.combineArraysForCount(activeDue,expiredDue,finishedDue);
                        int sumindividualCount = 0;
                        int sumgroupCount = 0;
                        for(int i=0; i<sumArray.size();i++){
                            if(sumArray.get(i).getGoalType().equals("individual"))
                                sumindividualCount++;
                            else
                                sumgroupCount++;
                        }

                        Log.d("summy", "onActivityResult: " + sumindividualCount + "group: " + sumgroupCount +"\n" +  sumArray);
                        completedCount = controller.countCompletedGoals(finishedDue);
                        goalBoard.setText("\nIndividual Goals: " + sumindividualCount + "\nGroup Goals: " + sumgroupCount+"\nCompleted Goals: " + completedCount);

                    }
                }
            });


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.d("hello homefragment", "onCreateView: I AM HERE IN CODE");
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        cancelBtn = binding.cancelCreateGoalBtn2;
        groupBtn = binding.groupBtn2;
        individualBtn = binding.individualBtn2;



        groupBtn = binding.groupBtn2;
        individualBtn = binding.individualBtn2;
        selectType = binding.selectGoalTypeLabel2;


        toolbar = binding.toolbarMainActivity;
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                if(itemId == R.id.logoutBtn){
                    Intent intent = new Intent(getActivity(), loginView.class);
                    startActivity(intent);
                 //   finish(); Commented this out when I tried to refactor HomeFragment
                }
                return false;
            }
        });

        personalDeadlineBtn = binding.personalDeadlineBtn;
        dueDateBtn = binding.dueDateBtn;
        activeBtn = binding.activeBtn;
        expiredBtn = binding.expiredBtn;
        finishedBtn = binding.finishedBtn;
        toggleGroup = binding.toggleButtonGroup;
        toggleGroup.check(R.id.activeBtn);
        toggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                recyclerView.setAdapter(new CustomGoalAdapter());
                if(isChecked){
                    if(checkedId == R.id.activeBtn){
                        if(personalSelected == true){

                            Log.d("activePersonal", "setRecyclerViewList: activePersonal" + activePersonal);
                            Collections.sort(activePersonal,controller.compareByPersonal);
                            recyclerList = activePersonal;
                        }
                        else{
                            Log.d("activeDue", "setRecyclerViewList: activeDue" + activeDue);
                            Collections.sort(activeDue,controller.compareByDeadline);
                            recyclerList = activeDue;
                        }

                    }
                    if(checkedId == R.id.expiredBtn){
                        if(personalSelected == true){

                            Log.d("expiredPersonal", "setRecyclerViewList: expieredPersonal" + expiredPersonal);
                            Collections.sort(expiredPersonal,controller.compareByPersonal);
                            recyclerList = expiredPersonal;
                        }
                        else{
                            Log.d("expiredDue", "setRecyclerViewList: expiredDue" + expiredDue);
                            Collections.sort(expiredDue,controller.compareByDeadline);
                            recyclerList = expiredDue;
                        }

                    }
                    if(checkedId == R.id.finishedBtn){
                        if(personalSelected == true){
                            Log.d("finishedPersonal", "setRecyclerViewList: finishedPerosnal" + finishedPersonal);
                            Collections.sort(finishedPersonal,controller.compareByPersonal);
                            recyclerList = finishedPersonal;
                        }
                        else{
                            Log.d("finishedDue", "setRecyclerViewList: finishedDue" + finishedDue);
                            Collections.sort(finishedDue,controller.compareByDeadline);
                            recyclerList = finishedDue;
                        }
                    }
                    CustomGoalAdapter adapter = (CustomGoalAdapter) recyclerView.getAdapter();
                    adapter.addItems();
                    Log.d("currentItemList", "onActivityResult: " + adapter.items);
                }
            }
        });

        personalDeadlineBtn.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
        personalDeadlineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("personal", "onClick: ");
                if(personalSelected == true){
                    personalSelected = false;
                    personalDeadlineBtn.setBackgroundTintList(ColorStateList.valueOf(GRAY));
                }
                else{
                    personalSelected = true;
                    personalDeadlineBtn.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    dueDateBtn.setBackgroundTintList(ColorStateList.valueOf(GRAY));
                    dueDateSelected = false;
                }
                Log.d("prep for switch", "onClick: About to run switch statements");
                recyclerView.setAdapter(new CustomGoalAdapter());
                int checkedId2 = toggleGroup.getCheckedButtonId();
                if(checkedId2 == R.id.activeBtn){
                    if(personalSelected == true){

                        Log.d("activePersonal", "setRecyclerViewList: activePersonal" + activePersonal);
                        Collections.sort(activePersonal,controller.compareByPersonal);
                        recyclerList = activePersonal;
                    }
                    else{
                        Log.d("activeDue", "setRecyclerViewList: activeDue" + activeDue);
                        Collections.sort(activeDue,controller.compareByDeadline);
                        recyclerList = activeDue;
                    }

                }
                if(checkedId2 == R.id.expiredBtn){
                    if(personalSelected == true){

                        Log.d("expiredPersonal", "setRecyclerViewList: expieredPersonal" + expiredPersonal);
                        Collections.sort(expiredPersonal,controller.compareByPersonal);
                        recyclerList = expiredPersonal;
                    }
                    else{
                        Log.d("expiredDue", "setRecyclerViewList: expiredDue" + expiredDue);
                        Collections.sort(expiredDue,controller.compareByDeadline);
                        recyclerList = expiredDue;
                    }

                }
                if(checkedId2 == R.id.finishedBtn){
                    if(personalSelected == true){
                        Log.d("finishedPersonal", "setRecyclerViewList: finishedPerosnal" + finishedPersonal);
                        Collections.sort(finishedPersonal,controller.compareByPersonal);
                        recyclerList = finishedPersonal;
                    }
                    else{
                        Log.d("finishedDue", "setRecyclerViewList: finishedDue" + finishedDue);
                        Collections.sort(finishedDue,controller.compareByDeadline);
                        recyclerList = finishedDue;
                    }
                }
                CustomGoalAdapter adapter = (CustomGoalAdapter) recyclerView.getAdapter();
                adapter.addItems();
                Log.d("currentItemList", "onActivityResult: " + adapter.items);
            }
        });

        dueDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("due", "onClick: ");
                if(dueDateSelected == true){
                    dueDateSelected= false;
                    dueDateBtn.setBackgroundTintList(ColorStateList.valueOf(GRAY));
                }
                else{
                    dueDateSelected = true;
                    dueDateBtn.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    personalDeadlineBtn.setBackgroundTintList(ColorStateList.valueOf(GRAY));
                    personalSelected= false;
                    recyclerView.setAdapter(new CustomGoalAdapter());
                    int checkedId2 = toggleGroup.getCheckedButtonId();
                    if(checkedId2 == R.id.activeBtn){
                        if(personalSelected == true){

                            Log.d("activePersonal", "setRecyclerViewList: activePersonal" + activePersonal);
                            Collections.sort(activePersonal,controller.compareByPersonal);
                            recyclerList = activePersonal;
                        }
                        else{
                            Log.d("activeDue", "setRecyclerViewList: activeDue" + activeDue);
                            Collections.sort(activeDue,controller.compareByDeadline);
                            recyclerList = activeDue;
                        }

                    }
                    if(checkedId2 == R.id.expiredBtn){
                        if(personalSelected == true){

                            Log.d("expiredPersonal", "setRecyclerViewList: expieredPersonal" + expiredPersonal);
                            Collections.sort(expiredPersonal,controller.compareByPersonal);
                            recyclerList = expiredPersonal;
                        }
                        else{
                            Log.d("expiredDue", "setRecyclerViewList: expiredDue" + expiredDue);
                            Collections.sort(expiredDue,controller.compareByDeadline);
                            recyclerList = expiredDue;
                        }

                    }
                    if(checkedId2 == R.id.finishedBtn){
                        if(personalSelected == true){
                            Log.d("finishedPersonal", "setRecyclerViewList: finishedPerosnal" + finishedPersonal);
                            Collections.sort(finishedPersonal,controller.compareByPersonal);
                            recyclerList = finishedPersonal;
                        }
                        else{
                            Log.d("finishedDue", "setRecyclerViewList: finishedDue" + finishedDue);
                            Collections.sort(finishedDue,controller.compareByDeadline);
                            recyclerList = finishedDue;
                        }
                    }
                    CustomGoalAdapter adapter = (CustomGoalAdapter) recyclerView.getAdapter();
                    adapter.addItems();
                    Log.d("currentItemList", "onActivityResult: " + adapter.items);
                }


            }
        });

        //Initialize recyclerView
        recyclerView = binding.goalList;
        setUpRecyclerView();
        //Data from createGoal Page
        ActivityResultLauncher<Intent> activityResultLaunch = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            recyclerView.setVisibility(View.VISIBLE);

                            Log.d("Result_OK", "onActivityResult: HERE");
                            IndividualGoalModel passedGoal = (IndividualGoalModel) result.getData().getSerializableExtra("bigGoal");
                            Log.d("HERE PassedGoal", String.valueOf(passedGoal));

                            goalList.add(passedGoal);
                            Log.d("goalList", "Just added onActivityResult: goalLst" + goalList);
                            controller.assignGoal(passedGoal);
                            Log.d("activePersonal", "activePersonal: "+activePersonal);
                            Log.d("AD", "AD: "+activeDue );
                            Log.d("eP", "EP: "+expiredPersonal );
                            Log.d("ED", "ED: "+expiredDue );
                            Log.d("FP", "FP: "+finishedPersonal );
                            Log.d("FD", "FD: "+ finishedDue );

                            recyclerView.setVisibility(View.VISIBLE);
                            switch(controller.setRecyclerViewList(HomeFragment.this)){
                                case "activePersonal":
                                    Log.d("switch", "onActivityResult: activePerconal");
                                    recyclerList = activePersonal;
                                    //  recyclerList.add(activePersonal.get(activePersonal.size()-1));

                                    break;
                                case "activeDue":
                                    Log.d("switch", "onActivityResult: activedue");
                                    recyclerList = activeDue;
                                    //  recyclerList.add(activeDue.get(activeDue.size()-1));
                                    break;
                                case "expiredPersonal:":
                                    Log.d("switch", "onActivityResult: expiredPersonal");
                                    recyclerList = expiredPersonal;
                                    //  recyclerList.add(expiredPersonal.get(expiredPersonal.size()-1));
                                    break;
                                case "expiredDue":
                                    Log.d("switch", "onActivityResult: expiredDue");
                                    recyclerList = expiredPersonal;
                                    //  recyclerList.add(recyclerList.get(recyclerList.size()-1))
                                    break;
                                case "finishedPersonal":
                                    Log.d("switch", "onActivityResult: finishedPersonal");
                                    recyclerList = finishedPersonal;
                                    //   finishedPersonal.add(recyclerList.get(recyclerList.size()-1))
                                    break;
                                case "finishedDue":
                                    recyclerList = finishedDue;
                                    Log.d("switch", "onActivityResult: finishedPersonal");
                                    //   finishedDue.add(recyclerList.get(recyclerList.size()-1))
                                    break;
                                default:
                                    Log.d("switcherror", "onCreate: error in switch");
                            }
                            Log.d("recyclerList", "onActivityResult: " + recyclerList);


                            //  recyclerList = expiredPersonal;
                            Log.d("recyclerList", "onActivityResult: " + recyclerList);
                            CustomGoalAdapter adapter = (CustomGoalAdapter) recyclerView.getAdapter();
                            adapter.addItems();
                            Log.d("currentItemList", "onActivityResult: " + adapter.items);


                            Log.d("FIVES", "onActivityResult: " + goalList);
                            Log.d("SIX", "onActivityResult: " + finishedDue);
                            Log.d("SIX", "onActivityResult: " + finishedPersonal);

                            ArrayList<IndividualGoalModel> sumArray=controller.combineArraysForCount(activeDue,expiredDue,finishedDue);
                            int sumindividualCount = 0;
                            int sumgroupCount = 0;
                            for(int i=0; i<sumArray.size();i++){
                                if(sumArray.get(i).getGoalType().equals("individual"))
                                    sumindividualCount++;
                                else
                                    sumgroupCount++;
                            }

                            Log.d("summy", "onActivityResult: " + sumindividualCount + "group: " + sumgroupCount +"\n" +  sumArray);
                            completedCount = controller.countCompletedGoals(finishedDue);
                            goalBoard.setText("\nIndividual Goals: " + sumindividualCount + "\nGroup Goals: " + sumgroupCount+"\nCompleted Goals: " + completedCount);




                        }
                    }
                });

        mainProgressBtn = binding.mainProgressBtn;
        mainProgressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getActivity(), mainProgressView.class);
                startActivity(myIntent);
            }
        });

        createLoginBtn = binding.createGoalBtnHome;
        createLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("I was clicked", "onClick: ");

                /*
                //This was the old way used before introducing group goal type
                Intent i = new Intent(MainActivity.this, goalView2.class);
               // startActivity(i);

                activityResultLaunch.launch(i);

                 */

                //Freeze all of the other elements except goal type popup which will become visible
                makeUnclickable();
                //Create group goal
                groupBtn.setVisibility(View.VISIBLE);
                groupBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        closeGoalTypePopup();
                        Log.d("CREATE GROUPGOAL", "onClick: Need to create group goal");
                        Intent i = new Intent(getActivity(), groupgoalView.class);
                        activityResultLaunch.launch(i);
                    }
                });
                //Create Individual Goal
                individualBtn.setVisibility(View.VISIBLE);
                individualBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        closeGoalTypePopup();
                        Log.d("Create Individual", "onClick: Need to create individual goal");
                        Intent i = new Intent(getActivity(), goalView2.class);
                        activityResultLaunch.launch(i);
                    }
                });
                cancelBtn.setVisibility(View.VISIBLE);
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        closeGoalTypePopup();
                    }
                });
                selectType.setVisibility(View.VISIBLE);
            }
        });

        //EditProfile Button Initialization
        editProfileBtn = binding.editProfileBtn;
        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                //Used to view searchforfriends class
                Intent myIntent = new Intent(MainActivity.this,progressReportView.class);
                startActivity(myIntent);

                 */


                // openReportPercentagePicker();
                /*
                //Used to view searchforfriends class
                Intent myIntent = new Intent(MainActivity.this,searchforfreinds_view.class);
                startActivity(myIntent);
                */


                /*
                //Used to view groupgoalsubgoal class
                Bundle bundle = new Bundle();
                bundle.putSerializable("passedGoal", new GoalModel());
                Intent myIntent = new Intent(MainActivity.this,groupgoalsubGoalView.class);
                myIntent.putExtras(bundle);
                startActivity(myIntent);

                 */



                /*
                //Uses to view goalProgressView
                Intent myIntent = new Intent(MainActivity.this, goalProgressView.class);
                startActivity(myIntent);
                */


                //Actual edit profile btn code
                Log.d("I was clicked", "onClick: ");
                Intent myIntent = new Intent(getActivity(), profileView.class);


                Bundle bundle = new Bundle();

                bundle.putString("avatarImage", Integer.toString(passedAvatar));
                bundle.putString("colorCode", colorCode);
                myIntent.putExtras(bundle);
                Log.d("sendToProfile", "onClick: " + Integer.toString(passedAvatar));
                activityResultLaunchAvatar.launch(myIntent);

            }
        });

        completedCount = controller.countCompletedGoals(goalList); //Probably wont need and can delete
        goalBoard = binding.goalBoard;
        Log.d("FIVES", "onActivityResult: " + goalList);
        Log.d("SIX", "onActivityResult: " + finishedDue);
        Log.d("SIX", "onActivityResult: " + finishedPersonal);

        //Get Counts of group goal and individual goals
        ArrayList<IndividualGoalModel> sumArray=controller.combineArraysForCount(activeDue,expiredDue,finishedDue);
        int sumindividualCount = 0;
        int sumgroupCount = 0;
        for(int i=0; i<sumArray.size();i++){
            if(sumArray.get(i).getGoalType().equals("individual"))
                sumindividualCount++;
            else
                sumgroupCount++;
        }

        Log.d("summy", "onActivityResult: " + sumindividualCount + "group: " + sumgroupCount +"\n" +  sumArray);
        completedCount = controller.countCompletedGoals(finishedDue);
        goalBoard.setText("\nIndividual Goals: " + sumindividualCount + "\nGroup Goals: " + sumgroupCount+"\nCompleted Goals: " + completedCount);

        closeGoalTypePopup();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    /**
     * Set the home avatar image
     * @param i imageListNumber
     * @param colorCode Color Code for image
     */
    public void setAvatar(int i,String colorCode ) {
        int[] myImageListForeground = new int[]{R.drawable.light0_foreground, R.drawable.light1_foreground, R.drawable.light2_foreground, R.drawable.light3_foreground, R.drawable.light4_foreground,
                R.drawable.light5_foreground, R.drawable.light6_foreground, R.drawable.light7_foreground, R.drawable.light8_foreground, R.drawable.light9_foreground, R.drawable.light10_foreground,
                R.drawable.light11_foreground, R.drawable.light12_foreground, R.drawable.light13_foreground, R.drawable.light14_foreground, R.drawable.light15_foreground,
                R.drawable.light16_foreground, R.drawable.light17_foreground};

        ImageView avatarOnProfile = binding.avatarHome;
        Drawable[] layers = new Drawable[2];
        layers[1] = getActivity().getDrawable(myImageListForeground[i]);
        layers[1].setTint(parseColor(colorCode));
        LayerDrawable layerDrawable = new LayerDrawable(layers);
        avatarOnProfile.setImageDrawable(layerDrawable);
        avatarOnProfile.setBackgroundResource(avatar_background);

    }


    class CustomGoalAdapter extends RecyclerView.Adapter {
        List<IndividualGoalModel> items;


        public CustomGoalAdapter() {
            items = new ArrayList<>();
            /*
            for(int i = 0; i < goalList.size(); i++){
                Log.d("trying to add", "setUpRecyclerView: " + goalList.get(i));
                items.add(goalList.get(i));
                Log.d("Added", "setUpRecyclerView: " + goalList.get(i));
            }
            */
            for(int i = 0; i < recyclerList.size(); i++){
                Log.d("trying to add", "setUpRecyclerView: " + recyclerList.get(i));
                items.add(recyclerList.get(i));
                Log.d("Added", "setUpRecyclerView: " + recyclerList.get(i));
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new GoalViewHolder(parent);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Log.d("Position", "onBindViewHolder: position" + position);
            GoalViewHolder viewHolder = (GoalViewHolder) holder;
            final IndividualGoalModel item = items.get(position);
            viewHolder.goalModel = items.get(position);
            Log.d("Position", "onBindViewHolder: position" + item);
            viewHolder.goalText.setText(items.get(position).getBigGoal());
            viewHolder.countDownDueDate.setText(controller.calculateDaysLeft((int)items.get(position).getWhenIsDue()) + "  Days Left");
            viewHolder.countDownPersonal.setText(controller.calculateDaysLeft((int)items.get(position).getPersonalDeadline()) + " Days Left");
            if(items.get(position).getGoalType().equals("individual"))
                viewHolder.avatarGoal.setImageResource(R.drawable.individualgoal_foreground);
            else
                viewHolder.avatarGoal.setImageResource(R.drawable.groupgoal_foreground);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public void addItems(){
            if(items.size()!=0)
                clearItems();
            items = new ArrayList<>();
            for(int i = 0; i<recyclerList.size();i++){
                items.add(recyclerList.get(i));
                notifyItemInserted(items.size());

            }
        }

        public void clearItems(){
            for(int i = 0; i<items.size(); i++){
                items.remove(i);
                notifyItemRemoved(i);
            }

        }
    }

    class GoalViewHolder extends RecyclerView.ViewHolder {

        TextView goalText;
        TextView countDownDueDate;
        TextView countDownPersonal;
        ImageView avatarGoal;
        IndividualGoalModel goalModel;

        public GoalViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.goalitem_view, parent, false));
            goalText = (TextView) itemView.findViewById(R.id.goalText);
            countDownDueDate = (TextView) itemView.findViewById(R.id.countDownDueDate);
            countDownPersonal = (TextView) itemView.findViewById(R.id.countDownPersonal);
            avatarGoal = (ImageView) itemView.findViewById(R.id.avatarGoal);
            avatarGoal.setImageResource(R.drawable.individualgoal_foreground);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {

                    //Individual Goal
                    if(goalModel.getGoalType().equals("individual")){
                        Intent i = new Intent(getActivity(), singleGoalView.class);
                        i.putExtra("bigGoal",goalModel);
                        goalSelected = itemView.getVerticalScrollbarPosition();
                        Log.d("goalModelPut", "onClick: " +goalModel);
                        activityResultLaunch2.launch(i);
                    }
                    else{ //Group goal
                        Intent i = new Intent(getActivity(), groupgoalsingleGoalView.class);
                        i.putExtra("bigGoal",goalModel);
                        goalSelected = itemView.getVerticalScrollbarPosition();
                        Log.d("goalModelPut", "onClick: " +goalModel);
                        activityResultLaunch2.launch(i);
                    }

                }
            });

        }
    }

    private void setUpRecyclerView() {
        recyclerView.setVisibility(View.INVISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new CustomGoalAdapter());
        recyclerView.setHasFixedSize(true);
    }

    /**
     * Used to open the GoalType popup that is used to determine if the user
     * wants to create a group goal or individual goal
     */
    private void closeGoalTypePopup(){
        groupBtn.setVisibility(View.INVISIBLE);
        individualBtn.setVisibility(View.INVISIBLE);
        cancelBtn.setVisibility(View.INVISIBLE);
        selectType.setVisibility(View.INVISIBLE);
        makeClickable();

    }

    /**
     * Makes all of the elements disabled making them unclickable
     */
    private void makeUnclickable(){
        setViewAndChildrenEnabled(createLoginBtn,false);
        setViewAndChildrenEnabled(editProfileBtn,false);
        setViewAndChildrenEnabled(recyclerView,false);
        setViewAndChildrenEnabled(personalDeadlineBtn,false);
        setViewAndChildrenEnabled(dueDateBtn,false);
        setViewAndChildrenEnabled(activeBtn,false);
        setViewAndChildrenEnabled(expiredBtn,false);
        setViewAndChildrenEnabled(finishedBtn,false);
        setViewAndChildrenEnabled(toggleGroup,false);
        setViewAndChildrenEnabled(mainProgressBtn,false);
        mainProgressBtn.setVisibility(View.VISIBLE);
    }

    /**
     * Makes all of the elements enabled making them clickable
     */
    private void makeClickable(){
        setViewAndChildrenEnabled(createLoginBtn,true);
        setViewAndChildrenEnabled(editProfileBtn,true);
        setViewAndChildrenEnabled(recyclerView,true);
        setViewAndChildrenEnabled(personalDeadlineBtn,true);
        setViewAndChildrenEnabled(dueDateBtn,true);
        setViewAndChildrenEnabled(activeBtn,true);
        setViewAndChildrenEnabled(expiredBtn,true);
        setViewAndChildrenEnabled(finishedBtn,true);
        setViewAndChildrenEnabled(toggleGroup,true);
        setViewAndChildrenEnabled(mainProgressBtn,true);
    }

    /**
     * Used to create the passed view, enabled or disabled based on the boolean enabled
     * @param view the view to change enabled/disabled
     * @param enabled if true, enable, else disable
     */
    private static void setViewAndChildrenEnabled(View view, boolean enabled) {
        view.setEnabled(enabled);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                setViewAndChildrenEnabled(child, enabled);
            }
        }
    }
}