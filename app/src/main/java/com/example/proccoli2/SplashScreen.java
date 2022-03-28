package com.example.proccoli2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proccoli2.NewModels.DataServices;
import com.example.proccoli2.ui.login.loginView;
import com.example.proccoli2.ui.mainActivity.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Copyright © 2022 Le Jie Bennett. All rights reserved.
 * Used to create spalsh screen
 */
public class SplashScreen extends AppCompatActivity {

    //Catch data from profileAvatarPage
    ActivityResultLauncher<Intent> activityResultLaunchLogin = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Log.d("splashscreen", "onActivityResult: recieved login data");
                        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(FirebaseAuth.getInstance().getCurrentUser()==null||FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()==false){
            Log.d("SplashScreen", "onCreate: login page display");
            Intent intent = new Intent(this, loginView.class);
            activityResultLaunchLogin.launch(intent);
        }
        else{
            DataServices.getInstance().setUID(FirebaseAuth.getInstance().getUid());
            DataServices.getInstance().setEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
            Log.d("checkSplash", "onCreate: " + DataServices.getInstance().getUID() + DataServices.getInstance().getEMAIL());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
