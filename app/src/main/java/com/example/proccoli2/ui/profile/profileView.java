package com.example.proccoli2.ui.profile;

import static com.example.proccoli2.R.drawable.avatar_background;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.proccoli2.MainActivity;
import com.example.proccoli2.NewModels.SingletonStrings;
import com.example.proccoli2.R;
import com.example.proccoli2.ui.avatarCollection.avatarView;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

/**
 * Used to collect, change, update user profile data
 */
public class profileView extends AppCompatActivity {

    SingletonStrings ss = new SingletonStrings();
    TextInputEditText fullNameInput, occupationInput, highestEducationInput;
    Button saveBtn, editAvatarBtn;
    Button dateButtonBirthdate;
    DatePickerDialog datePickerDialogBirthdate;
    String colorCode; //Color Code to send back to main
    String newColor; //Color from Avatar Collection
    String originalColor; //Color from Main
    String avatarImageO;
    Toolbar toolbar;
    edit_profile_VC edit_controller = new edit_profile_VC(this);
    profile_VC profile_controller = new profile_VC(this);

    //get data from the avatar collection
    ActivityResultLauncher<Intent> activityResultLaunchAvatarCollection = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        //Get image number
                        avatarImageO = result.getData().getStringExtra("avatarImage");
                        //Get color
                        newColor= result.getData().getStringExtra("colorCode");
                        Log.d("ReceivedImage", "onActivityResult: " + avatarImageO);
                        Log.d("ReceivedColorCode", "onActivityResult: " + colorCode);
                        setAvatar(Integer.parseInt(avatarImageO));
                    }
                }
            });



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_view);


        int[] toolBarImageList= new int[]{R.drawable.gear_foreground};
        Drawable[] toolBarDrawableIcon = new Drawable[1];
        toolBarDrawableIcon[0] = getDrawable(toolBarImageList[0]);

        toolbar = findViewById(R.id.toolbarProfile);
        toolbar.setOverflowIcon(toolBarDrawableIcon[0]);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("toolbar clicked", "onClick: I was clicked");
                finish();
            }
        });

        fullNameInput = findViewById(R.id.fullNameInput);
        occupationInput = findViewById(R.id.occupationInput);
        highestEducationInput = findViewById(R.id.highestEducationInput);

        //This gets data from the main activity page and then sets the avatar image on the profile page
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            String avatarImage = bundle.getString("avatarImage");

            originalColor = bundle.getString("colorCode");
            Log.d("COLOR", "onCreate: " + originalColor);
            setAvatar(Integer.parseInt(avatarImage));
        }


        saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("go backtoMain", "onClick: I am here with id" + Integer.parseInt(avatarImageO));
                setAvatarIntent(Integer.parseInt(avatarImageO));

            }
        });

        editAvatarBtn = findViewById(R.id.avatarBtn);
        editAvatarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Clicked edit Avatar", "onClick: I clicked edit avatar");
                Intent i = new Intent(profileView.this, avatarView.class);
                activityResultLaunchAvatarCollection.launch(i);
                //startActivity(i);
            }
        });

        initiatePickerBirthdate();
        dateButtonBirthdate = findViewById(R.id.datePickerButtonBirthdate);
        dateButtonBirthdate.setText(getTodaysDate());
        profile_controller.loadCurrentUserInfoIntoView();
    }

    /**
     * Creates a datepicker dialog to select a date for birthdate
     */
    public void initiatePickerBirthdate(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButtonBirthdate.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialogBirthdate = new DatePickerDialog(this, style, dateSetListener, year, month, day);

    }

    /**
     * Used to convert date picker dialog format into a Birthdate String
     * @param day Number
     * @param month Number
     * @param year Number
     * @return String verison of that birthdate in the Form MMM DD YYYY
     */
    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    /**
     * Convert int to MMM Month
     * @param month int
     * @return Month String MMM
     */
    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    public void openDatePickerDeadline(View view)
    {
        datePickerDialogBirthdate.show();
    }

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }


    public int strToDateToUnix(String strDate){
        try {
            Date dateDate = new SimpleDateFormat("MMMM dd yyyy").parse(strDate);
            return (int) dateDate.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return -1;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Used to set the avatar profile picture based on the image number and the color from avatarCollection
     * @param i Avatar Image Number
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setAvatar(int i) {
        Log.d("set avatarFun", "setAvatar: " + "org:" +  originalColor + "new: " +  newColor);
        int[] myImageListForeground = new int[]{R.drawable.light0_foreground, R.drawable.light1_foreground, R.drawable.light2_foreground, R.drawable.light3_foreground, R.drawable.light4_foreground,
                R.drawable.light5_foreground, R.drawable.light6_foreground, R.drawable.light7_foreground, R.drawable.light8_foreground, R.drawable.light9_foreground, R.drawable.light10_foreground,
                R.drawable.light11_foreground, R.drawable.light12_foreground, R.drawable.light13_foreground, R.drawable.light14_foreground, R.drawable.light15_foreground,
                R.drawable.light16_foreground, R.drawable.light17_foreground, R.drawable.light6, avatar_background};

        ImageView avatarOnProfile = findViewById(R.id.avatar);
        Random random = new Random();
        //If we are using the default color and image
        if(i == 6 && newColor==null){
            colorCode = originalColor;
            Drawable[] layers = new Drawable[2];
            layers[1] = getDrawable(myImageListForeground[6]);
            LayerDrawable layerDrawable = new LayerDrawable(layers);
            avatarOnProfile.setImageDrawable(layerDrawable);
            avatarOnProfile.setBackgroundResource(avatar_background);
        }
        else {
            //Avatar image was not changed
            if(newColor == null)
                colorCode = originalColor;
            //Avatar image and color changed from avatar collection
            else
                colorCode = newColor;
            Drawable[] layers = new Drawable[2];
            layers[1] = getDrawable(myImageListForeground[i]);
            layers[1].setTint(Color.parseColor(colorCode));
            LayerDrawable layerDrawable = new LayerDrawable(layers);
            avatarOnProfile.setImageDrawable(layerDrawable);
            avatarOnProfile.setBackgroundResource(avatar_background);
        }
    }

    //Sends the displayed avatar information to main activity

    /**
     * Send the dispalyed avatar information ot the main activity
     * @param i Avatar Image Number
     */
    public void setAvatarIntent(int i){

        Intent myIntent = new Intent(this, MainActivity.class);

        HashMap<String,String> profileImageHashmap = new HashMap<>();
        profileImageHashmap.put(colorCode,Integer.toString(i));
        myIntent.putExtra("avatarImage", Integer.toString(i));
        myIntent.putExtra("colorCode",colorCode);
        Log.d("IMAGEID", "setAvatar: " + Integer.toString(i));
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put(ss.POFILE_IMG_WITH_COLOR_REF,profileImageHashmap);
        hashMap.put(ss.FULL_NAME_REF, fullNameInput.getText().toString());
        hashMap.put(ss.OCCUPATION_REF,occupationInput.getText().toString());
        hashMap.put(ss.HIGHEST_LEVEL_OF_EDUCATION_REF,highestEducationInput.getText().toString());
        Log.d("Birthdate", "setAvatarIntent: " + dateButtonBirthdate.getText().toString());
        hashMap.put(ss.BIRTHDAY_REF,profile_controller.dateStrToUnix2(dateButtonBirthdate.getText().toString()));
        Log.d("UpdateProfileInfo", "setAvatarIntent: " + hashMap);
        edit_controller.updateProfileInfo(hashMap);
        setResult(RESULT_OK,myIntent);
        finish();
    }


}
