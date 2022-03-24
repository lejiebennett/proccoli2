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
import android.opengl.Visibility;
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
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proccoli2.NewModels.DataServices;
import com.example.proccoli2.NewModels.GoalModel;
import com.example.proccoli2.NewModels.GroupGoalModel;
import com.example.proccoli2.NewModels.IndividualGoalModel;
import com.example.proccoli2.NewModels.ResultHandler;
import com.example.proccoli2.NewModels.UserDataModel;
import com.example.proccoli2.R;
import com.example.proccoli2.databinding.FragmentHomeBinding;
import com.example.proccoli2.ui.individualGoalCreation.goalView2;
import com.example.proccoli2.ui.groupGoalCreation.groupgoalView;
import com.example.proccoli2.groupgoalsingleGoalView;
import com.example.proccoli2.ui.login.loginView;
import com.example.proccoli2.mainProgressView;
import com.example.proccoli2.ui.profile.profileView;
import com.example.proccoli2.ui.individualWall.singleGoalView;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
    ArrayList<GoalModel> goalList = new ArrayList<>();
    ArrayList<GoalModel> recyclerList = new ArrayList<>();
    ArrayList<GoalModel> activePersonal = new ArrayList<>();
    ArrayList<GoalModel> activeDue = new ArrayList<>();
    ArrayList<GoalModel> expiredPersonal = new ArrayList<>();
    ArrayList<GoalModel> expiredDue = new ArrayList<>();
    ArrayList<GoalModel> finishedPersonal = new ArrayList<>();
    ArrayList<GoalModel> finishedDue = new ArrayList<>();
    HomeFragment_VC controller = new HomeFragment_VC(this);
    ImageButton personalDeadlineBtn, dueDateBtn;
    Button activeBtn, expiredBtn, finishedBtn;
    boolean personalSelected = true;
    boolean dueDateSelected = false;
    MaterialButtonToggleGroup toggleGroup;
    GoalModel passedGoal;
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
    ActivityResultLauncher<Intent> activityResultLaunch3 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if (result.getResultCode() == RESULT_OK) {
                        Log.d("FromSingleGoal", "onActivityResult: Returned from single Goal view");
                        GroupGoalModel passedIndividualGoal = (GroupGoalModel) result.getData().getSerializableExtra("bigGoal");
                        passedGoal = GroupGoalModel.goalsModelConverterForDataWrite(passedIndividualGoal);
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
                        CustomGoalAdapterH adapter = (CustomGoalAdapterH) recyclerView.getAdapter();
                        Log.d("addItems1", "onActivityResult: addItems1");
                        adapter.addItems();
                        Log.d("currentItemList", "onActivityResult: " + adapter.items);
                        adapter.notifyDataSetChanged();

                        Log.d("FIVES", "onActivityResult: " + goalList);
                        Log.d("SIX", "onActivityResult: " + finishedDue);
                        Log.d("SIX", "onActivityResult: " + finishedPersonal);


                        //Used to get a list of all of the group goals, individual goals to get counts
                        ArrayList<GoalModel> sumArray=controller.combineArraysForCount(activeDue,expiredDue,finishedDue);
                        int sumindividualCount = 0;
                        int sumgroupCount = 0;
                        for(int i=0; i<sumArray.size();i++){
                            if(sumArray.get(i).getTaskType().equals("individual"))
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

    //Catch data from singleGoalView
    ActivityResultLauncher<Intent> activityResultLaunch2 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if (result.getResultCode() == RESULT_OK) {
                        Log.d("FromSingleGoal", "onActivityResult: Returned from single Goal view");
                        IndividualGoalModel passedIndividualGoal = (IndividualGoalModel) result.getData().getSerializableExtra("bigGoal");
                        passedGoal = IndividualGoalModel.goalsModelConverterForDataWrite(passedIndividualGoal);
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
                        CustomGoalAdapterH adapter = (CustomGoalAdapterH) recyclerView.getAdapter();
                        Log.d("addItems1", "onActivityResult: addItems1");
                        adapter.addItems();
                        Log.d("currentItemList", "onActivityResult: " + adapter.items);
                        adapter.notifyDataSetChanged();

                        Log.d("FIVES", "onActivityResult: " + goalList);
                        Log.d("SIX", "onActivityResult: " + finishedDue);
                        Log.d("SIX", "onActivityResult: " + finishedPersonal);


                        //Used to get a list of all of the group goals, individual goals to get counts
                        ArrayList<GoalModel> sumArray=controller.combineArraysForCount(activeDue,expiredDue,finishedDue);
                        int sumindividualCount = 0;
                        int sumgroupCount = 0;
                        for(int i=0; i<sumArray.size();i++){
                            if(sumArray.get(i).getTaskType().equals("individual"))
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
        Log.d("hello home fragment", "onCreateView: I AM HERE IN CODE Actual");
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
                Bundle result = new Bundle();
                result.putSerializable("goalList",goalList);
                getParentFragmentManager().setFragmentResult("goalLst",result);
            }
        });

        getParentFragmentManager().setFragmentResultListener("goalList", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                ArrayList<GoalModel> newGoalList = (ArrayList<GoalModel>) result.getSerializable("goalList");
                Log.d("From search fragment", "onFragmentResult: " + newGoalList);
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
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(getActivity(), loginView.class);
                    startActivity(intent);
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
                Log.d("toggle", "onButtonChecked: "+  "toggle group");
                recyclerView.setAdapter(new CustomGoalAdapterH());

                if(isChecked){
                    if(checkedId == R.id.activeBtn){
                        if(personalSelected == true){
                            Log.d("activePersonal", "setRecyclerViewList: activePersonal" + activePersonal);
                            Collections.sort(activePersonal,controller.compareByPersonal);
                            recyclerList = activePersonal;
                            recyclerView.setAdapter(new CustomGoalAdapterH());

                        }
                        else{
                            Log.d("activeDue", "setRecyclerViewList: activeDue" + activeDue);
                            Collections.sort(activeDue,controller.compareByDeadline);
                            recyclerList = activeDue;
                            recyclerView.setAdapter(new CustomGoalAdapterH());
                        }

                    }
                    if(checkedId == R.id.expiredBtn){
                        if(personalSelected == true){
                            recyclerView.setVisibility(View.GONE);
                            recyclerView = binding.goalList;
                            recyclerView.setVisibility(View.VISIBLE);
                            Log.d("expiredPersonal", "setRecyclerViewList: expieredPersonal" + expiredPersonal);
                            Collections.sort(expiredPersonal,controller.compareByPersonal);
                            recyclerList = expiredPersonal;
                            setUpRecyclerView();
                           // recyclerView.setAdapter(new CustomGoalAdapterH());

                        }
                        else{
                            Log.d("expiredDue", "setRecyclerViewList: expiredDue" + expiredDue);
                            Collections.sort(expiredDue,controller.compareByDeadline);
                            recyclerList = expiredDue;
                            recyclerView.setAdapter(new CustomGoalAdapterH());

                        }

                    }
                    if(checkedId == R.id.finishedBtn){
                        if(personalSelected == true){
                            Log.d("finishedPersonal", "setRecyclerViewList: finishedPerosnal" + finishedPersonal);
                            Collections.sort(finishedPersonal,controller.compareByPersonal);
                            recyclerList = finishedPersonal;
                            recyclerView.setAdapter(new CustomGoalAdapterH());

                        }
                        else{
                            Log.d("finishedDue", "setRecyclerViewList: finishedDue" + finishedDue);
                            Collections.sort(finishedDue,controller.compareByDeadline);
                            recyclerList = finishedDue;
                            recyclerView.setAdapter(new CustomGoalAdapterH());


                        }
                    }
                    Log.d("toggleHERE", "onButtonChecked: " + recyclerList);
                    //setUpRecyclerView();

                }
            }
        });

        expiredBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("expiredBtn", "onClick: ");
                toggleGroup.check(R.id.expiredBtn);
                toggleGroup.uncheck(R.id.activeBtn);
                toggleGroup.uncheck(R.id.finishedBtn);
            }
        });

        finishedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("finishedBtn", "onClick: ");

                toggleGroup.check(R.id.finishedBtn);
                toggleGroup.uncheck(R.id.expiredBtn);
                toggleGroup.uncheck(R.id.activeBtn);
            }
        });

        activeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("activeBtn", "onClick: ");

                toggleGroup.check(R.id.activeBtn);
                toggleGroup.uncheck(R.id.expiredBtn);
                toggleGroup.uncheck(R.id.finishedBtn);
            }
        });

        personalDeadlineBtn.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
        personalDeadlineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("personal", "onClick: ");
                personalDeadlineBtn.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                dueDateBtn.setBackgroundTintList(ColorStateList.valueOf(GRAY));
                dueDateSelected = false;
                Log.d("prep for switch", "onClick: About to run switch statements");
                recyclerView.setAdapter(new CustomGoalAdapterH());
                int checkedId2 = toggleGroup.getCheckedButtonId();
                if(checkedId2 == R.id.activeBtn){

                    Log.d("activePersonal", "setRecyclerViewList: activePersonal" + activePersonal);
                    Collections.sort(activePersonal,controller.compareByPersonal);
                    recyclerList = activePersonal;

                }
                if(checkedId2 == R.id.expiredBtn){

                    Log.d("expiredPersonal", "setRecyclerViewList: expieredPersonal" + expiredPersonal);
                    Collections.sort(expiredPersonal,controller.compareByPersonal);
                    recyclerList = expiredPersonal;

                }
                if(checkedId2 == R.id.finishedBtn){
                    Log.d("finishedPersonal", "setRecyclerViewList: finishedPerosnal" + finishedPersonal);
                    Collections.sort(finishedPersonal,controller.compareByPersonal);
                    recyclerList = finishedPersonal;
                }
                CustomGoalAdapterH adapter = (CustomGoalAdapterH) recyclerView.getAdapter();
                Log.d("addItems3", "onActivityResult: addItems3");

                adapter.addItems();
                Log.d("currentItemList", "onActivityResult: " + adapter.items);
            }
        });

        dueDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("due", "onClick: ");
                dueDateSelected = true;
                dueDateBtn.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                personalDeadlineBtn.setBackgroundTintList(ColorStateList.valueOf(GRAY));
                personalSelected= false;
                recyclerView.setAdapter(new CustomGoalAdapterH());
                int checkedId2 = toggleGroup.getCheckedButtonId();
                if(checkedId2 == R.id.activeBtn){

                    Log.d("activePersonal", "setRecyclerViewList: activePersonal" + activePersonal);
                    Collections.sort(activePersonal,controller.compareByPersonal);
                    recyclerList = activePersonal;

                }
                if(checkedId2 == R.id.expiredBtn){
                    Log.d("expiredDue", "setRecyclerViewList: expiredDue" + expiredDue);
                    Collections.sort(expiredDue,controller.compareByDeadline);
                    recyclerList = expiredDue;

                }
                if(checkedId2 == R.id.finishedBtn){
                    Log.d("finishedDue", "setRecyclerViewList: finishedDue" + finishedDue);
                    Collections.sort(finishedDue,controller.compareByDeadline);
                    recyclerList = finishedDue;
                }
                CustomGoalAdapterH adapter = (CustomGoalAdapterH) recyclerView.getAdapter();
                Log.d("addItems4", "onActivityResult: addItems4");
                adapter.addItems();
                Log.d("currentItemList", "onActivityResult: " + adapter.items);
            }
        });

        Log.d("check", "onCreateView: " + DataServices.getInstance().getUID() + DataServices.getInstance().getEMAIL());

//        try{
        DataServices.getInstance().callUserInfo(new ResultHandler<Object>() {

            @Override
            public void onSuccess(Object data) {
                Log.d("LoadProfile", "onSuccess: " + UserDataModel.sharedInstance.getProfileImg());
                setAvatar(Integer.valueOf(String.valueOf(UserDataModel.sharedInstance.getProfileImg().keySet().toArray()[0])),UserDataModel.sharedInstance.getProfileImg().get(UserDataModel.sharedInstance.getProfileImg().keySet().toArray()[0]));
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
        //    }
        //    catch(Exception e){
        Log.d("here", "onCreateView: user not logged in");
        //   }


        //Initialize recyclerView
        recyclerView = binding.goalList;
        setUpRecyclerView();
        Log.d("Home Fragment", "onCreateView: I AM HOME FRAGMENT");
        // try{
        DataServices.getInstance().requestPersonalGoals(new ResultHandler<Object>() {

            @Override
            public void onSuccess(Object data) {
                HashMap<String,Object> hashMap = (HashMap<String, Object>) data;
                if(hashMap.get("_error")==null){
                    Log.d("personalGoals", "onSuccess: " + hashMap);
                    goalList = (ArrayList<GoalModel>) hashMap.get("_response");

                    for(GoalModel goal: goalList){
                        controller.assignGoal(goal);
                    }
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

                    Log.d("recyclerListA5", "onActivityResult: " + recyclerList);

                    CustomGoalAdapterH adapter = (CustomGoalAdapterH) recyclerView.getAdapter();
                    Log.d("addItems5", "onActivityResult: addItems5");

                    adapter.addItems();
                    Log.d("currentItemList", "onActivityResult: " + adapter.items);

                    Log.d("FIVES", "onActivityResult: " + goalList);
                    Log.d("SIX", "onActivityResult: " + finishedDue);
                    Log.d("SIX", "onActivityResult: " + finishedPersonal);


                    ArrayList<GoalModel> sumArray=controller.combineArraysForCount(activeDue,expiredDue,finishedDue);
                    int sumindividualCount = 0;
                    int sumgroupCount = 0;
                    for(int i=0; i<sumArray.size();i++){
                        if(sumArray.get(i).getTaskType().equals("individual"))
                            sumindividualCount++;
                        else
                            sumgroupCount++;
                    }

                    Log.d("summy", "onActivityResult: " + sumindividualCount + "group: " + sumgroupCount +"\n" +  sumArray);
                    completedCount = controller.countCompletedGoals(finishedDue);
                    goalBoard.setText("\nIndividual Goals: " + sumindividualCount + "\nGroup Goals: " + sumgroupCount+"\nCompleted Goals: " + completedCount);
                }
                else{
                    Log.d("requestPersonalError", "onSuccess: " + hashMap.get("_error"));
                }

            }

            @Override
            public void onFailure(Exception e) {

            }
        });
        //   }
        //    catch(Exception e){
        Log.d("homefragment", "onCreateView: user not logged in");
        //     }


        //Data from createGoal Page
        ActivityResultLauncher<Intent> activityResultLaunch = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            recyclerView.setVisibility(View.VISIBLE);

                            Log.d("Result_OK", "onActivityResult: HERE HOME FRAGMENT" + recyclerView.getAdapter().getItemCount());

                            //GoalModel passedGoal = (GoalModel) result.getData().getSerializableExtra("bigGoal");
                            IndividualGoalModel passedIndividualGoal = (IndividualGoalModel) result.getData().getSerializableExtra("bigGoal");
                            Log.d("HERE PassedGoal", passedIndividualGoal.getGoalId());
                            passedGoal = controller.convertIndividualToGoalModel(passedIndividualGoal);
                            passedGoal.setGoalId(passedIndividualGoal.getGoalId());
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

                            Log.d("recyclerList", "onActivityResult: " + recyclerList);
                            /*
                            //Had to comment this out and just reset up the recycler view because it was having issues
                            syncing with the bindviewHolder
                            CustomGoalAdapterH adapter = (CustomGoalAdapterH) recyclerView.getAdapter();
                            adapter.addItems();
                            Log.d("currentItemList", "onActivityResult: " + adapter.items);


                             */
                            setUpRecyclerView();


                            Log.d("FIVES", "onActivityResult: " + goalList);
                            Log.d("SIX", "onActivityResult: " + finishedDue);
                            Log.d("SIX", "onActivityResult: " + finishedPersonal);


                            ArrayList<GoalModel> sumArray=controller.combineArraysForCount(activeDue,expiredDue,finishedDue);
                            int sumindividualCount = 0;
                            int sumgroupCount = 0;
                            for(int i=0; i<sumArray.size();i++){
                                if(sumArray.get(i).getTaskType().equals("individual"))
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
        ArrayList<GoalModel> sumArray=controller.combineArraysForCount(activeDue,expiredDue,finishedDue);
        int sumindividualCount = 0;
        int sumgroupCount = 0;
        for(int i=0; i<sumArray.size();i++){
            if(sumArray.get(i).getTaskType().equals("individual"))
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


    class CustomGoalAdapterH extends RecyclerView.Adapter {
        List<GoalModel> items;
        public CustomGoalAdapterH() {
            items = new ArrayList<>();

            for(int i = 0; i < recyclerList.size(); i++){
                Log.d("trying to add", "setUpRecyclerViewH: " + recyclerList.get(i));
                items.add(recyclerList.get(i));
                Log.d("Added", "setUpRecyclerViewH: " + recyclerList.get(i));
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
            final GoalModel item = items.get(position);
            viewHolder.goalModel = items.get(position);
            Log.d("Position", "onBindViewHolder: position" + item);
            viewHolder.goalText.setText(items.get(position).getBigGoal());
            viewHolder.countDownDueDate.setText(controller.calculateDaysLeft((int)items.get(position).getWhenIsDue()) + "  Days Left");
            viewHolder.countDownPersonal.setText(controller.calculateDaysLeft((int)items.get(position).getPersonalDeadline()) + " Days Left");

            //if(items.get(position).getGoalType().equals("individual"))
            viewHolder.avatarGoal.setImageResource(R.drawable.individualgoal_foreground);
            //else
            //    viewHolder.avatarGoal.setImageResource(R.drawable.groupgoal_foreground);
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
                Log.d("addItems", "addItems: notify" + (items.size()-1));
                notifyItemInserted(items.size()-1);
                Log.d("addItems", "addItems: " + items.get(i));
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
        GoalModel goalModel;

        public GoalViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.goalitem_view, parent, false));
            goalText = (TextView) itemView.findViewById(R.id.goalText);
            countDownDueDate = (TextView) itemView.findViewById(R.id.countDownDueDate);
            countDownPersonal = (TextView) itemView.findViewById(R.id.countDownPersonal);
            avatarGoal = (ImageView) itemView.findViewById(R.id.avatarGoal);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Log.d("homeFragment", "onClick: item");
                    //Individual Goal
                    if(goalModel.getTaskType().equals("individual")){
                        Intent i = new Intent(getActivity(), singleGoalView.class);
                        Log.d("homeFragment", "onClick: item individual" + goalModel.getGoalId());

                        DataServices.getInstance().requestIndividualGoal(goalModel.getGoalId(), new ResultHandler<Object>() {

                            @Override
                            public void onSuccess(Object data) {
                                Log.d("dataFromRequestHome", "onSuccess: " + data);
                                Log.d("dataFromRequestHome", "onSuccess: ");
                                IndividualGoalModel parsedGoal = IndividualGoalModel.parseData((DocumentSnapshot) data);
                                Log.d("HomeFragment", "onSuccess: " + parsedGoal);
                                //i.putExtra("bigGoal", goalModel);
                                i.putExtra("bigGoal", parsedGoal);
                                goalSelected = itemView.getVerticalScrollbarPosition();
                                Log.d("goalModelPut", "onClick: " +data);
                                activityResultLaunch2.launch(i);
                            }

                            @Override
                            public void onFailure(Exception e) {

                            }
                        });
                    }
                    else{ //Group goal
                        Intent i = new Intent(getActivity(), groupgoalsingleGoalView.class);
                        i.putExtra("bigGoal",goalModel);
                        goalSelected = itemView.getVerticalScrollbarPosition();
                        Log.d("goalModelPut", "onClick: " +goalModel);
                        activityResultLaunch3.launch(i);
                    }

                }
            });

        }
    }

    private void setUpRecyclerView() {
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new CustomGoalAdapterH());
        recyclerView.setHasFixedSize(true);
        Log.d("setupRecycler", "setUpRecyclerView" + recyclerView.getAdapter().getItemCount());
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