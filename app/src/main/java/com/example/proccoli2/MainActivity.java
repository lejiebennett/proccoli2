package com.example.proccoli2;

import static android.graphics.Color.*;
import static com.example.proccoli2.R.drawable.avatar_background;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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

import com.example.proccoli2.NewModels.DataServices;
import com.example.proccoli2.NewModels.IndividualGoalModel;
import com.example.proccoli2.ui.dashboard.DashboardFragment;
import com.example.proccoli2.ui.groupGoalCreation.groupgoalView;
import com.example.proccoli2.ui.home.HomeFragment;
import com.example.proccoli2.ui.individualGoalCreation.goalView2;
import com.example.proccoli2.ui.individualWall.singleGoalView;
import com.example.proccoli2.ui.login.loginView;
import com.example.proccoli2.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proccoli2.databinding.ActivityMainBinding;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    Button createLoginBtn, editProfileBtn;
    private ActivityMainBinding binding;
    RecyclerView recyclerView;
    ImageView avatarHome;
    TextView goalBoard;
    int goalSelected;
    int completedCount = 0;
    String colorCode;
    int passedAvatar = 6;
    DataServices ss = DataServices.getInstance();
    FirebaseAuth auth;


    private static final MainActivity sharedInstance = new MainActivity();
    public static MainActivity getInstance() {
        return sharedInstance;
    }
    ArrayList<IndividualGoalModel> goalList = new ArrayList<>(); //List used to keep track of all Users' goals
    ArrayList<IndividualGoalModel> recyclerList = new ArrayList<>(); //List that as a container to display the goals in all of the recycler views

    //Used to sort and order the goals in each respective category
    ArrayList<IndividualGoalModel> activePersonal = new ArrayList<>();
    ArrayList<IndividualGoalModel> activeDue = new ArrayList<>();
    ArrayList<IndividualGoalModel> expiredPersonal = new ArrayList<>();
    ArrayList<IndividualGoalModel> expiredDue = new ArrayList<>();
    ArrayList<IndividualGoalModel> finishedPersonal = new ArrayList<>();
    ArrayList<IndividualGoalModel> finishedDue = new ArrayList<>();


    MainActivity_VC controller = new MainActivity_VC(this);

    //Buttons to specify what recycler view to show, what order they should be sorted by
    ImageButton personalDeadlineBtn, dueDateBtn;
    Button activeBtn, expiredBtn, finishedBtn;
    boolean personalSelected = true;
    boolean dueDateSelected = false;
    MaterialButtonToggleGroup toggleGroup;

    //Collect goal from goal creation pages (both indivudal and grou goals)
    IndividualGoalModel passedGoal;
    Button mainProgressBtn;
    TextView texthome; //Default text created by android studio
    Toolbar toolbar;

    //Used for bottom navigation
    AppBarConfiguration appBarConfiguration;
    NavController navController;
    androidx.fragment.app.FragmentManager fm;
    androidx.fragment.app.FragmentTransaction transaction;

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

                        //Get rid of the goal that was just selected from the goal list and update it with the newly passed updated goal
                        controller.removeGoalFromList(goalSelected,goalList);
                        goalList.add(passedGoal);
                        Log.d("goalList", "Just added onActivityResult: goalLst" + goalList);

                        //Update the recyclers lists by removing the old goal version
                        controller.findOldAndRemoveFromRecyclers(passedGoal);

                        //Update the recycler lists by adding it to the correct lists
                        controller.assignGoal(passedGoal);
                        Log.d("activePersonal", "activePersonal: "+activePersonal);
                        Log.d("AD", "AD: "+activeDue );
                        Log.d("eP", "EP: "+expiredPersonal );
                        Log.d("ED", "ED: "+expiredDue );
                        Log.d("FP", "FP: "+finishedPersonal );
                        Log.d("FD", "FD: "+ finishedDue );


                        //Show recycler view with update list based on button selection
                        recyclerView.setVisibility(View.VISIBLE);
                        switch(controller.setRecyclerViewList(MainActivity.this)){
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


                        //Update goal board counts
                        //Used to get a list of all of the group goals, individual goals to get counts
                        ArrayList<IndividualGoalModel> sumArray=controller.combineArraysForCount(activeDue,expiredDue,finishedDue);
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


    final androidx.fragment.app.Fragment homeFragment = new HomeFragment();
    final androidx.fragment.app.Fragment dashboardFragment = new DashboardFragment();
    final androidx.fragment.app.Fragment notificationsFragment = new NotificationsFragment();

    androidx.fragment.app.Fragment activeFragment = homeFragment;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        auth= FirebaseAuth.getInstance();
        if(auth.getCurrentUser()==null || auth.getCurrentUser().isEmailVerified()==false){
            Intent intent = new Intent(MainActivity.this, loginView.class);
            startActivity(intent);
        }

        /*
        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null || firebaseAuth.getCurrentUser().isEmailVerified()==false){
                    firebaseAuth.removeAuthStateListener(this);
                    Intent intent = new Intent(MainActivity.this,loginView.class);
                    startActivity(intent);
                }
                else{
                    Log.d("UserLoggedINV", "onAuthStateChanged: load data for main" );
                    ss.callUserInfo(new ResultHandler<Object>() {
                        @Override
                        public void onSuccess(Object data) {
                            HashMap<String,Object> results = (HashMap<String, Object>) data;
                            if((boolean) results.get("_status")==true){
                               // this.addAppStatusNotifications();
                                ss.requestPersonalGoals(new ResultHandler<Object>() {
                                    @Override
                                    public void onSuccess(Object data) {
                                        HashMap<String,Object> results = (HashMap<String, Object>) data;
                                        if( results.get("_response")!=null){
                                            ArrayList<com.example.proccoli2.NewModels.GoalModel> rawData = (ArrayList<com.example.proccoli2.NewModels.GoalModel>) results.get("_response");
                                            UserDataModel.sharedInstance.setRawGoalsData(rawData);
                                           // ss.notificationCallWithListener();
                                        }
                                    }
                                    @Override
                                    public void onFailure(Exception e) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Exception e) {
                            //problem with fecthin data
                            //self.alertView(message: error?.localizedDescription ?? "connection err", colorPereferences: alertColor)
                            Toast.makeText(getBaseContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                        }
                    });
                }
            }
        });

        */


        Log.d("New main", "onCreate: Creating new view");
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications,R.id.navigation_invitations)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        fm = getSupportFragmentManager();
        transaction = fm.beginTransaction();
        transaction.setReorderingAllowed(true);

        transaction.add(R.id.nav_host_fragment_activity_main,notificationsFragment).hide(notificationsFragment);
        transaction.add(R.id.nav_host_fragment_activity_main,dashboardFragment).hide(dashboardFragment);
        transaction.add(R.id.nav_host_fragment_activity_main,homeFragment).commit();


        /*
        transaction.add(notificationsFragment,null).hide(notificationsFragment);
        transaction.add(dashboardFragment,null).hide(dashboardFragment);
        transaction.add(homeFragment,null).show(homeFragment).commit();

         */
        activeFragment = homeFragment;

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if(destination.getId()==R.id.navigation_notifications){
                    Log.d("notification", "onDestinationChanged: NOTIFICATIONS");

                    //  transaction.add(notificationsFragment,null);

                    /*
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("goalList",goalList);
                    Log.d("bundlePut", "onDestinationChanged: " + bundle.getSerializable("goalList"));
                    NotificationsFragment notificationsFragment = new NotificationsFragment();
                    notificationsFragment.setArguments(bundle);
                  //  NotificationsFragment notificationsFragment = NotificationsFragment.newInstance(goalList);
                  //  transaction.add(notificationsFragment,null).commit();
                   transaction.replace(R.id.nav_host_fragment_container, notificationsFragment)
                            .commit();
                    Log.d("getArgument", "onDestinationChanged: " + notificationsFragment.getArguments());

                    */


                    /*
                    kinda works
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("goalList",goalList);
                    Intent intent = new Intent(MainActivity.this, selfreport_activity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);

                    */


                    //selfreport_fragment fragment_selfReport = new selfreport_fragment();

                    /*
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("goalList",goalList);
                    notificationsFragment.setArguments(bundle);


                     */
                    fm.beginTransaction().hide(activeFragment).show(notificationsFragment).commit();
                    activeFragment = notificationsFragment;

                }
                else if(destination.getId()==R.id.navigation_home){
                    Log.d("home", "onDestinationChanged: Home");
                    //  transaction.show(homeFragment);

                    fm.beginTransaction().hide(activeFragment).show(homeFragment).commit();
                    activeFragment = homeFragment;

                }
                else if(destination.getId()==R.id.navigation_dashboard){
                    Log.d("Dashboard","onDestinationChanged: dashboard");
                    //   DashboardFragment dashboardFragment = new DashboardFragment();
                    //  NotificationsFragment notificationsFragment = NotificationsFragment.newInstance(goalList);
                    //   transaction.add(dashboardFragment,null);
                    //transaction.replace(R.id.nav_host_fragment_container, dashboardFragment)
                    //        .commit();


                    fm.beginTransaction().hide(activeFragment).show(dashboardFragment).commit();


                    //transaction.show(dashboardFragment);
                    activeFragment = dashboardFragment;

                }
                else if(destination.getId()==R.id.navigation_invitations){
                    Log.d("Invitations","onDestinationChanged: invitations");
                }
                else
                    Log.d("ERROR", "onDestinationChanged: ERROR");

            }
        });

        texthome = findViewById(R.id.text_home);
        texthome.setVisibility(View.INVISIBLE);

        groupBtn = findViewById(R.id.groupBtn2);
        individualBtn = findViewById(R.id.individualBtn2);
        selectType = findViewById(R.id.selectGoalTypeLabel2);
        cancelBtn = findViewById(R.id.cancelCreateGoalBtn2);


        toolbar=findViewById(R.id.toolbarMainActivity);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                if(itemId == R.id.logoutBtn){
                    ss.signOutUser();
                    Intent intent = new Intent(MainActivity.this,loginView.class);
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });

        //Set up recycler view button options
        personalDeadlineBtn = findViewById(R.id.personalDeadlineBtn);
        dueDateBtn = findViewById(R.id.dueDateBtn);
        activeBtn = findViewById(R.id.activeBtn);
        expiredBtn = findViewById(R.id.expiredBtn);
        finishedBtn  = findViewById(R.id.finishedBtn);
        toggleGroup = findViewById(R.id.toggleButtonGroup);
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
        recyclerView = findViewById(R.id.goalList);
        //setUpRecyclerView(); //Need to comment this out since we are actually using HomeFragment instead
        //Data from createGoal Page


        ActivityResultLauncher<Intent> activityResultLaunch = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {


/*
                            Log.d("Result_OK", "onActivityResult: HERE MAIN ACTIVITYY");
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
                            switch(controller.setRecyclerViewList(MainActivity.this)){
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
                         //   CustomGoalAdapter adapter = (CustomGoalAdapter) recyclerView.getAdapter();
                            //   adapter.addItems();
                            CustomGoalAdapter adapter = new CustomGoalAdapter();
                            recyclerView.setAdapter(adapter);
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


 */
                        }
                    }
                });




        mainProgressBtn = findViewById(R.id.mainProgressBtn);
        mainProgressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, mainProgressView.class);
                startActivity(myIntent);
            }
        });

        //Create Goal Btn
        createLoginBtn = findViewById(R.id.createGoalBtnHome);
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
                        Intent i = new Intent(MainActivity.this, groupgoalView.class);
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
                        Intent i = new Intent(MainActivity.this, goalView2.class);
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
        editProfileBtn = findViewById(R.id.editProfileBtn);
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


                /*
                //Actual edit profile btn code
                Log.d("I was clicked", "onClick: ");
                Intent myIntent = new Intent(MainActivity.this, profileView.class);


                Bundle bundle = new Bundle();

                bundle.putString("avatarImage", Integer.toString(passedAvatar));
                bundle.putString("colorCode", colorCode);
                myIntent.putExtras(bundle);
                Log.d("sendToProfile", "onClick: " + Integer.toString(passedAvatar));
                activityResultLaunchAvatar.launch(myIntent);

                 */

            }
        });


        completedCount = controller.countCompletedGoals(goalList); //Probably wont need and can delete
        goalBoard = findViewById(R.id.goalBoard);

        Log.d("FIVES", "onActivityResult: " + goalList);
        Log.d("SIX", "onActivityResult: " + finishedDue);
        Log.d("SIX", "onActivityResult: " + finishedPersonal);

        //Get Counts of group goal and individual goals
        ArrayList<IndividualGoalModel> sumArray=controller.combineArraysForCount(activeDue,expiredDue,finishedDue);
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

        ImageView avatarOnProfile = findViewById(R.id.avatarHome);
        Drawable[] layers = new Drawable[2];
        layers[1] = getDrawable(myImageListForeground[i]);
        layers[1].setTint(parseColor(colorCode));
        LayerDrawable layerDrawable = new LayerDrawable(layers);
        avatarOnProfile.setImageDrawable(layerDrawable);
        avatarOnProfile.setBackgroundResource(avatar_background);

    }


    class CustomGoalAdapter extends RecyclerView.Adapter {
        List<IndividualGoalModel> items;


        public CustomGoalAdapter() {
            Log.d("customGoalAdapter", "CustomGoalAdapter: MainActivity");
            items = new ArrayList<>();
            /*
            for(int i = 0; i < goalList.size(); i++){
                Log.d("trying to add", "setUpRecyclerView: " + goalList.get(i));
                items.add(goalList.get(i));
                Log.d("Added", "setUpRecyclerView: " + goalList.get(i));
            }
            */
            for(int i = 0; i < recyclerList.size(); i++){
                Log.d("trying to add", "setUpRecyclerViewM: " + recyclerList.get(i));
                items.add(recyclerList.get(i));
                Log.d("Added", "setUpRecyclerViewM: " + recyclerList.get(i));
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
            Log.d("Adding itemsMA", "addItems: " );
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
            Log.d("clearedItems", "clearItems: " + items);

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
                        Intent i = new Intent(MainActivity.this, singleGoalView.class);
                        i.putExtra("bigGoal",goalModel);
                        goalSelected = itemView.getVerticalScrollbarPosition();
                        Log.d("goalModelPut", "onClick: " +goalModel);
                        activityResultLaunch2.launch(i);
                    }
                    else{ //Group goal
                        Intent i = new Intent(MainActivity.this,groupgoalsingleGoalView.class);
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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CustomGoalAdapter());
        recyclerView.setHasFixedSize(true);
    }

    private void loadFragment(Fragment fragment) {
// create a FragmentManager
        FragmentManager fm = getFragmentManager();
// create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.navigation_home, fragment);
        fragmentTransaction.commit(); // save the changes
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