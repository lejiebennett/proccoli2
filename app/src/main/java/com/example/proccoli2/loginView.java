package com.example.proccoli2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;


public class loginView extends AppCompatActivity {
    TextInputEditText emailInput, passwordInput;
    Button signUpBtn, forgotPasswordBtn, loginBtn;

    login_VC login_VC = new login_VC(this);


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);

        emailInput = findViewById(R.id.newEmailInput);
        passwordInput = findViewById(R.id.newPasswordInput);

        signUpBtn = findViewById(R.id.signUpBtn);
        signUpBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String emailText = emailInput.getText().toString();
                String passwordText = passwordInput.getText().toString();

                while(checkFields(emailText,passwordText)==false)
                    Log.d("Clicked Signup", "onClick: I clicked Signup Button w email: " + emailText + " and password: " + passwordText);

            }
        });

        forgotPasswordBtn = findViewById(R.id.forgotPasswordBtn);
        forgotPasswordBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.d("Clicked forgotPassword", "onClick: I clicked forgotPassword Button");
             //   login_VC.forgotPassword();
            }
        });

        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.d("Clicked login", "onClick: I clicked loginBtn");
             //   login_VC.loginAgain();
            }
        });
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
