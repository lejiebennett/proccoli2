package com.lejiebennett.proccoli2.ui.login;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lejiebennett.proccoli2.NewModels.DataServices;
import com.lejiebennett.proccoli2.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Copyright © 2022 Le Jie Bennett. All rights reserved.
 * Activity for logging in
 */
public class loginView extends AppCompatActivity {
    DataServices dataServices = new DataServices();

    TextInputEditText emailInput, passwordInput;
    Button signUpShowBtn, signUpBtn, forgotPasswordBtn, loginBtn,loginShowBtn;
    ProgressBar loading;
    FirebaseAuth auth;


    login_Controller login_Controller = new login_Controller(this);


    @Override
    protected void onCreate(Bundle savedInstanceState){

        Log.d("loginView", "onCreate: " + "Login page");
        auth = FirebaseAuth.getInstance();
        Log.d("MainActivity", "onCreate: " + FirebaseAuth.getInstance().getCurrentUser());
        if (auth.getCurrentUser() == null || auth.getCurrentUser().isEmailVerified() == false) {
            Log.d("loginView", "onCreate: login user");
        }
        else{
            Log.d("loginView", "onCreate: userloggedin");

        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);

        emailInput = findViewById(R.id.newEmailInput);
        passwordInput = findViewById(R.id.newPasswordInput);
        loading = findViewById(R.id.loading);

        signUpShowBtn = findViewById(R.id.signUpBtn);
        signUpShowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpShowBtn.setVisibility(View.INVISIBLE);
                loginBtn.setVisibility(View.INVISIBLE);
                signUpBtn.setVisibility(View.VISIBLE);
                loginShowBtn.setVisibility(View.VISIBLE);
            }
        });

        signUpBtn = findViewById(R.id.signUpActionBtn);
        signUpBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String emailText = emailInput.getText().toString();
                String passwordText = passwordInput.getText().toString();

                while(checkFields(emailText,passwordText)==false)
                    Log.d("Clicked Signup", "onClick: I clicked Signup Button w email: " + emailText + " and password: " + passwordText);
                Log.d("Clicked Signup", "onClick: I clicked Signup Button w email: " + emailText + " and password: " + passwordText);
                login_Controller.signUp(emailInput.getText().toString(),passwordInput.getText().toString(),emailInput.getText().toString());
            }
        });

        forgotPasswordBtn = findViewById(R.id.forgotPasswordBtn);
        forgotPasswordBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.d("Clicked forgotPassword", "onClick: I clicked forgotPassword Button");
                dataServices.resetPassword(emailInput.toString());
            }
        });


        loginBtn = findViewById(R.id.loginActionBtn);
        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.d("Clicked login", "onClick: I clicked loginBtn");
                login_Controller.login(emailInput.getText().toString(),passwordInput.getText().toString());
                Log.d("fields", "onClick: " + emailInput.getText().toString() + passwordInput.getText().toString());

            }
        });
        loginShowBtn = findViewById(R.id.loginBtn);
        loginShowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActionBtnAsLogin();
            }
        });
    }

    public void displayLoading(){
        loading.setVisibility(View.VISIBLE);
    }

    public void hideLoading(){
        loading.setVisibility(View.INVISIBLE);
    }

    public void changeActionBtnAsLogin(){
        loginBtn.setVisibility(View.VISIBLE);
        signUpBtn.setVisibility(View.INVISIBLE);
        signUpShowBtn.setVisibility(View.VISIBLE);
        loginShowBtn.setVisibility(View.INVISIBLE);

    }

    public boolean checkFields(String email, String password){
        Context context = getApplicationContext();
        CharSequence text;
        int duration;
        Toast toast;
        if(email!=null){
            if(password!=null)
                return true;
            else{
                text = "Please enter a password!";
                duration = Toast.LENGTH_LONG;

                toast = Toast.makeText(context, text, duration);
                toast.show();
                passwordInput.setBackgroundColor(Color.RED);
                return false;
            }
        }
        else{
            text = "Please enter an email!";
            duration = Toast.LENGTH_LONG;
            toast = Toast.makeText(context, text, duration);
            toast.show();
            emailInput.setBackgroundColor(Color.RED);
            if(passwordInput==null){
                context = getApplicationContext();
                text = "Please enter a password!";
                toast = Toast.makeText(context, text, duration);
                toast.show();
                passwordInput.setBackgroundColor(Color.RED);
                }
            return false;
        }

    }


}
