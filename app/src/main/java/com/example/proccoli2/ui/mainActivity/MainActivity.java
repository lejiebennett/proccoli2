package com.example.proccoli2.ui.mainActivity;
/**
 * Copyright © 2022 Le Jie Bennett. All rights reserved.
 * Main Activity, control the different fragment screens
 */

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proccoli2.NewModels.GoalModel;
import com.example.proccoli2.NewModels.IndividualGoalModel;
import com.example.proccoli2.R;
import com.example.proccoli2.ui.home.HomeFragment;
import com.example.proccoli2.ui.login.loginView;
import com.example.proccoli2.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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

import com.example.proccoli2.databinding.ActivityMainBinding;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    private ActivityMainBinding binding;
    FirebaseAuth auth;


    private static final MainActivity sharedInstance = new MainActivity();
    public static MainActivity getInstance() {
        return sharedInstance;
    }
    ArrayList<GoalModel> goalList = new ArrayList<>(); //List used to keep track of all Users' goals

    MainActivity_Controller controller = new MainActivity_Controller(this);
    TextView texthome; //Default text created by android studio

    //Used for bottom navigation
    AppBarConfiguration appBarConfiguration;
    NavController navController;
    androidx.fragment.app.FragmentManager fm;
    androidx.fragment.app.FragmentTransaction transaction;

    //Used for create goal btn popup

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
       // activeFragment = homeFragment;

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