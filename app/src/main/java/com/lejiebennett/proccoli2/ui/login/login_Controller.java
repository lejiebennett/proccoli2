package com.lejiebennett.proccoli2.ui.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lejiebennett.proccoli2.NewModels.DataServices;
import com.lejiebennett.proccoli2.NewModels.ResultHandler;
import com.lejiebennett.proccoli2.oldModels.UserModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;

/**
 *  Copyright © 2022 Le Jie Bennett. All rights reserved.
 *  Controller for activity for login
 */
public class login_Controller extends AppCompatActivity {

    private loginView loginView; //View
    private UserModel model;
    DataServices dataServices = new DataServices();
    String emailForDataServices;
    String uid;

    public login_Controller(loginView loginView){
        this.loginView = loginView;
        model = new UserModel();
    }

    /**
     * Used to verify that field is not empty/null
     * @param input TextEditView that acts as the input
     * @param errorMessage The error message to show in the toast
     * @param context
     * @param textView The text view to change color if errored
     * @return True if valid, else false
     */
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


    public loginView getLoginView() {
        return loginView;
    }

    public void emailVerificationAlert(String message, String email){
        //Send alert for email verification
        AlertDialog.Builder builder1 = new AlertDialog.Builder(loginView);
        builder1.setMessage("Email Verification " + message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "re-send",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        dataServices.sendVerificationEmail(email, new ResultHandler<Object>() {

                            @Override
                            public void onSuccess(Object data) {
                                Toast.makeText(loginView.getBaseContext(), "success", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(Exception e) {
                                Toast.makeText(loginView.getBaseContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                            }
                        });
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void login(String email, String pass){
        loginView.displayLoading();
        dataServices.loginUser(email,pass, new ResultHandler<Object>() {
            @Override
            public void onSuccess(Object data) {
                Log.d("Sucesslogin", "onSucess: Dataservices");

                loginView.hideLoading();
                HashMap<String,Object> result = (HashMap<String, Object>) data;
                Exception e = (Exception) result.get("_error");
                if(e!=null){
                    //Send alert for email verification
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(loginView);
                    builder1.setMessage(e.getLocalizedMessage());
                    builder1.setCancelable(true);
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
                else{
                    boolean isLoginVerified = (boolean) result.get("isLoginVerified");
                    if(isLoginVerified==true){
                        //Email has been verified
                        //Good to send to profile VC
                        Log.d("check", "onSuccess: " + loginView + result.get("email") + result.get("uid"));
                        emailForDataServices = (String) result.get("email");
                        uid = (String) result.get("uid");
                        DataServices.getInstance().setEmail(email);
                        DataServices.getInstance().setUID(uid);

                        loginView.setResult(RESULT_OK);
                        loginView.finish();
                        finish();



                    }
                    else{
                        //Email has not been verified yet
                        //resend verification
                        emailVerificationAlert("This account has not\nbeen verified\nPlease check your e-mail.", email);
                    }

                }

            }

            @Override
            public void onFailure(Exception e) {
                Log.d("failedLogin", "onFailure: Failed DataSErvices");
            }
        });
    }

    public void signUp(String email, String password,String userName){
        loginView.displayLoading();
        Log.d("poop", "signUp: " + email +  password + userName);
        dataServices.createNewUserWithAuth(email, password, userName, new ResultHandler<Object>() {

            @Override
            public void onSuccess(Object data) {
                loginView.hideLoading();
                HashMap<String,Object> result = (HashMap<String, Object>) data;
                Log.d("loginHashmap", "onSuccess: " + result);
                boolean status = (boolean) result.get("_status");
                if(status==true){
                    Exception e = (Exception) result.get("_error");
                    if(e==null){
                        //Send alert for email verification
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(loginView);
                        builder1.setMessage((String)getValueOrDefault("Success",result.get("_responseMessage")));
                        builder1.setCancelable(true);
                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                        loginView.changeActionBtnAsLogin();
                    }
                    else{
                        //Send alert for email verification
                        Log.d("error", "onSuccess: " + e);
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(loginView);
                        builder1.setMessage(e.getLocalizedMessage());
                        builder1.setCancelable(true);
                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    }
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    public static <T> T getValueOrDefault(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }

}
