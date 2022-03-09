package com.example.proccoli2;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proccoli2.databinding.LoginViewBinding;
import com.example.proccoli2.ui.login.loginView;

/**
 * Used to create spalsh screen
 */
public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // Intent intent = new Intent(this,MainActivity.class);
        Intent intent = new Intent(this,MainActivity.class);
       // Intent intent = new Intent(this, loginView.class);

        startActivity(intent);
        finish();
    }
}
