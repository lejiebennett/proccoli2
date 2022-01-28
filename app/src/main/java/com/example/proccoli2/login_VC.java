package com.example.proccoli2;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class login_VC extends AppCompatActivity {

    private loginView loginView; //View
    private UserModel model;

    public login_VC(loginView loginView){
        this.loginView = loginView;
        model = new UserModel();
    }

    public boolean nullFieldCheck(String input, String errorMessage, Context context, TextInputEditText textView) {
        if (input.length() == 0) {
            CharSequence text = errorMessage;
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            textView.setBackgroundColor(Color.RED);
            return false;
        }
        return true;
    }

    public void forgotPassword(){
        Log.d("forgotPass called on VC", "forgotPassword: on VC");
    }

    public void loginAgain(){
        Log.d("loginAgain called on VC", "loginAgain: Log in");
    }

    public loginView getLoginView() {
        return loginView;
    }

}
