package com.example.proccoli2.UnusedFiles;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proccoli2.MainActivity;

public class SplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
       // startActivity(new Intent(SplashScreenActivity.this, loginView.class));

        finish();
    }
}

