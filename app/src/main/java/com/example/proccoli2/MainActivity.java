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
import com.example.proccoli2.NewModels.GoalModel;
import com.example.proccoli2.NewModels.IndividualGoalModel;
import com.example.proccoli2.NewModels.ResultHandler;
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
import java.util.HashMap;
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
    FirebaseAuth auth;


    private static final MainActivity sharedInstance = new MainActivity();
    public static MainActivity getInstance() {
        return sharedInstance;
    }
    ArrayList<GoalModel> goalList = new ArrayList<>(); //List used to keep track of all Users' goals

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

    final androidx.fragment.app.Fragment homeFragment = new HomeFragment();
   // final androidx.fragment.app.Fragment dashboardFragment = new DashboardFragment();
    //final androidx.fragment.app.Fragment notificationsFragment = new NotificationsFragment();

    androidx.fragment.app.Fragment activeFragment = homeFragment;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        Log.d("check", "onCreate: " + auth.getCurrentUser());
        if (auth.getCurrentUser() == null || auth.getCurrentUser().isEmailVerified() == false) {
            Log.d("mainActivity", "onCreate: need to login");
            Intent intent = new Intent(MainActivity.this, loginView.class);
            startActivity(intent);
        }


        Log.d("New main", "onCreate: Creating new view");
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_invitations)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        fm = getSupportFragmentManager();
        transaction = fm.beginTransaction();
        transaction.setReorderingAllowed(true);

        //transaction.add(R.id.nav_host_fragment_activity_main,notificationsFragment).hide(notificationsFragment);
        //transaction.add(R.id.nav_host_fragment_activity_main,dashboardFragment).hide(dashboardFragment);
        transaction.add(R.id.nav_host_fragment_activity_main, homeFragment).commit();


        /*
        transaction.add(notificationsFragment,null).hide(notificationsFragment);
        transaction.add(dashboardFragment,null).hide(dashboardFragment);
        transaction.add(homeFragment,null).show(homeFragment).commit();

         */
        activeFragment = homeFragment;

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId() == R.id.navigation_notifications) {
                    Log.d("notification", "onDestinationChanged: NOTIFICATIONS");
                    Log.d("notification", "onDestinationChanged: about to requestPersonal Goals");
                    NotificationsFragment notificationsFragment = new NotificationsFragment();

                    fm.beginTransaction().hide(activeFragment).show(notificationsFragment).commit();
                    activeFragment = notificationsFragment;


                } else if (destination.getId() == R.id.navigation_home) {
                    Log.d("home", "onDestinationChanged: Home");
                    //  transaction.show(homeFragment);

                    fm.beginTransaction().hide(activeFragment).show(homeFragment).commit();
                    activeFragment = homeFragment;

                } else if (destination.getId() == R.id.navigation_dashboard) {
                    Log.d("Dashboard", "onDestinationChanged: dashboard");
                    //   DashboardFragment dashboardFragment = new DashboardFragment();
                    //  NotificationsFragment notificationsFragment = NotificationsFragment.newInstance(goalList);
                    //   transaction.add(dashboardFragment,null);
                    //transaction.replace(R.id.nav_host_fragment_container, dashboardFragment)
                    //        .commit();


                    // fm.beginTransaction().hide(activeFragment).show(dashboardFragment).commit();


                    //transaction.show(dashboardFragment);
                    //  activeFragment = dashboardFragment;

                } else if (destination.getId() == R.id.navigation_invitations) {
                    Log.d("Invitations", "onDestinationChanged: invitations");
                } else
                    Log.d("ERROR", "onDestinationChanged: ERROR");

            }
        });

        texthome = findViewById(R.id.text_home);
        texthome.setVisibility(View.INVISIBLE);


    }

}