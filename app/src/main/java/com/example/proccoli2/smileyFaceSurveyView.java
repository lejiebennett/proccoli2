package com.example.proccoli2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proccoli2.NewModels.DataServices;
import com.example.proccoli2.NewModels.SingletonStrings;
import com.example.proccoli2.ui.home.individualWall.singleGoalView;

import java.util.HashMap;

/**
 * Used to get goal feedback using smiley faces from the user once an individual goal is marked complete
 * Data will be sent back to singeGoalView so it can update that singleGoal which will then be sent to main actiivty page
 */
public class smileyFaceSurveyView extends AppCompatActivity {

    ImageView face1, face2, face3, face4, face5;
    String questionType;
    boolean isGroupStudy;
    String goalId,selectedSubGoalId,studyId;
    SingletonStrings ss = new SingletonStrings();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smileyfacesurvey_view);

        Intent intent = getIntent();
        questionType  = intent.getStringExtra("questionType");
        goalId = intent.getStringExtra("goalId");
        isGroupStudy = intent.getBooleanExtra("isGroupStudy",false);
        if(intent.getStringExtra("selectedSubGoalId")!=null)
            selectedSubGoalId = intent.getStringExtra("selectedSubGoalId");

        if(intent.getStringExtra("studyId")!=null)
            studyId = intent.getStringExtra("studyId");

        Log.d("smileyFace", "onCreate: " + questionType + " " + goalId + " "  + isGroupStudy);


        face1 = findViewById(R.id.face1);
        face2 = findViewById(R.id.face2);
        face3 = findViewById(R.id.face3);
        face4 = findViewById(R.id.face4);
        face5 = findViewById(R.id.face5);

        face1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRating(1);
            }
        });

        face2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRating(2);
            }
        });

        face3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRating(3);
            }
        });

        face4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRating(4);
            }
        });

        face5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRating(5);
            }
        });


    }

    /**
     * Sets the goal completion feedback based on the face icon that was selected and sends it back to the singleGoalView
     * @param i The corresponding face rating that is to be set
     */
    public void setRating(int i){
        Intent myIntent = new Intent(this, singleGoalView.class);
        myIntent.putExtra("smileRating",Integer.toString(i));
        Log.d("setSmileRating", "smileRating: " + Integer.toString(i));
        String result = String.valueOf(i);

        Log.d("setRating", "setRating: " + questionType + " " + isGroupStudy + " " + goalId);
        if(questionType.equals(ss.FACE_QUESTION_TYPE_REF_FOR_TIMER)){
            if(isGroupStudy==false){
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put(ss.CREATED_AT,System.currentTimeMillis()/100L);
                hashMap.put(ss.Evaluation_REF,getValueOrDefault(result,""));
                DataServices.sendEvaluationDataAfterIndividualStudy(goalId,selectedSubGoalId,studyId,hashMap);
            }
            else{
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put(ss.CREATED_AT,System.currentTimeMillis()/100L);
                hashMap.put(ss.Evaluation_REF,getValueOrDefault(result,""));
                DataServices.sendEvaluationDataAfterGroupStudy(goalId,selectedSubGoalId,studyId,hashMap);
            }
        }
        else if(questionType.equals(ss.FACE_QUESTION_TYPE_REF_FOR_COMPLETION)){
            Log.d("questionType", "setRating: face_quest_type_ref_for_completion");
            if(isGroupStudy==false){
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put(ss.CREATED_AT,System.currentTimeMillis()/100L);
                hashMap.put(ss.Evaluation_REF,getValueOrDefault(result,""));
                Log.d("smileyFaceSurvey", "setRating: FACE QUESTION TYPE REF FOR COMPLETION");
                DataServices.sendEvaluationDataAfterIndividialGoalCompletion(goalId,hashMap);
            }
            else{
                Log.d("smileyFaceSurveyView", "group completion handle here");
            }
        }

        setResult(RESULT_OK,myIntent);
        finish();

    }

    public static <T> T getValueOrDefault(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }
}
