package com.example.proccoli2.NewModels;


import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.common.api.Batch;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.google.firestore.v1.Write;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class DataServices {


    //colelction references
    final String USER_COLLECTION_REF = "users";
    final String GOALS_COLLECTION_REF  = "goals";
    final String REVISION_COLLECTION_REF = "revisions";
    final String PERSONAL_NOTE_COLLEECTION_REF = "personalNote";
    final String REMINDERS_COLLECTIONS_REF = "reminders";
    final String PERSONAL_GOALS_COLLECTION_REF = "personalGoals";
    final String INDIVIDUAL_PROGRESS_DATA_COLLECTION_REF = "progressData";
    final String LOG_DATA_COLLECTION_REF = "logData";


    //login
    final String INDIVIDUAL_TOTAL_GOAL_NUMBER_REF = "indvidualGoalTotal";
    final String GROUP_TOTAL_GOAL_NUMBER_REF = "groupGoalTotal";
    final String COMPLETED_TOTAL_GOAL_NUMBER_REF = "compfinal StringedGoalNumber";

    //PROFILE VC SINGLETONS
    final String SEGMENTED_CONTROL_ACTIVE_REF = "active";
    final String SEGMENTED_CONTROL_EXPIRED_REF = "expired";
    final String SEGMENTED_CONTROL_FINISHED_REF = "finished";
    final String SORT_BY_PERSONAL_DEADLINE_REF = "personal";
    final String SORT_BY_HARD_DEADLINE_REF = "hard";

    final String UID = "uid";
    final String EMAIL = "email";
    final String CREATED_AT = "createdAt";
    final String INDIVIDUAL_GOAL_TOTAL_REF = "indvidualGoalNumber";
    final String COMPLETED_GOAL_NUMBERS_REF = "compfinal StringedGoalNumber";



    final String GOAL_ID_REF = "goalId";
    final String GOAL_TYPE_REF = "goalType";
    final String BIG_GOAL_REF = "bigGoal";
    final String PERSONAL_DEADLINE_REF = "personalDeadline";
    final String INDIVIDUAL_REF = "individual";
    final String GOAL_CREATER_UID_REF = "goalCreaterUid";
    final String SUB_GOALS_REF = "subGoals";
    final String SUB_GOAL_NAME_REF = "subgoalName";
    final String POFILE_IMG_WITH_COLOR_REF = "profileImgWithHex";
    final String FULL_NAME_REF = "fullName";
    final String OCCUPATION_REF = "occupation";
    final String HIGHEST_LEVEL_OF_EDUCATION_REF = "highestLevelOfEducation";
    final String BIRTHDAY_REF = "birthday";
    final String DIFFICULTY_LEVEL_REF = "difficultyLevel";
    final String HOW_LONG_REF = "howLong";
    final String SUB_DEADLINE_REF = "subDeadline";
    final String IS_CHECKED_REF = "isChecked";
    final String COMPLETED_DATE_REF = "compfinal StringedDate";
    final String PROPOSED_START_TIME_REF = "proposedStartDate";
    final String IS_DELETED_REF = "isDefinal Stringed";
    final String SUB_GOAL_DELETE_TIME = "definal StringeTime";
    final String INVITED_USER_UIDS_REF = "invitedUserUids";
    final String GOAL_CREATER_EMAIL_REF = "goalCreaterEmail";
    final String IS_GOAL_COMPLETED_REF = "isGoalCompfinal Stringed";
    final String TASK_TYPE_REF = "taskType";
    final String WHEN_IS_IT_DUE_REF = "whenIsItDue";
    final String SUB_GOAL_PACK_REF = "subGoalPack";
    final String TOTAL_STUDIED_TIME_REF = "totalStudiedTime";
    final String NO_SUB_GOAL_REF = "noSubGoal";
    final String SUB_GOAL_ID_REF = "subgoalId";
    final String SUB_GOAL_CHECK_TIME_REF = "subGoalCheckTime";
    final String SUB_GOAL_UN_CHECK_TIME_REF = "subGoalUnCheckTime";


    final String NOTE_REF = "note";
    final String NOTE_ID_REF = "noteId";
    final String STUDY_TIME_REF = "studyTime";
    final String STUDY_TIME_DOC_ID_REF = "KDFJNSDKjiew32mdsdsf03D";

    final String TOTAL_BREAK_TIMES_REF = "totalBreakTimes";
    final String IS_FINISHED_REF = "isFinished";
    final String PROPOSED_STUDY_TIME_REF = "proposedStudyTime";
    final String TOTAL_PROPOSED_STUDY_TIME_REF = "totalProposedStudyTime";

    final String BREAKS_REF = "breaks";
    final String RESUMES_REF = "resumes";
    final String BREAK_TIME_REF = "breakTime";
    final String STOP_TIME_REF = "stopTime";
    final String STUDIED_TIME_REF = "studiedTime";
    final String STOPED_STUYD_TIME_REF = "stopedStudyTime";
    final String START_LOCATION_REF = "startLocation";
    final String BREAK_LOCATION_REF = "breakLocation";
    final String STOP_LOCATION_REF = "stopLocation";
    final String FINISH_LOCATION_REF = "stopLocation";
    final String FINISH_TIME_REF = "finishTime";
    final String RESUME_TIME_REF = "resumeTime";
    final String RESUME_LOCATION_REF = "resumeLocation";
    final String INDIVIDUAL_WALL_TIMER_LBL_REF = "individualWall";
    final String GROUP_WALL_TIMER_LBL_REF = "groupWall";
    final String PROFILE_PAGE_TIMER_LBL_REF = "profileVC";
    final String TIMER_VIEWS_TIMER_LBL_REF = "timerViewsLbl";
    final String REMINDER_TIME_REF = "reminderTime";
    final String LOCATION_REF = "location";

    //tokes
    final String TOKENS_TABLE_REF = "tokens";
    final String FCT_REF = "FCT";
    final String DEVICE_TOKEN_REF = "deviceToken";

    //SURVEY question types
    final String RADIO_BTN_REF = "radioBtn";
    final String SQUARE_BTN_REF = "squareBtn";


    final String NOTIFICATION_TABLE_REF = "notification";
    final String SURVEYS_REF = "surveys";
    final String TOTAL_WAITING_SURVEY_REF = "totalWaitingSurvey";

    //survey questions
    final String UNCOMPLETED_QUESTIONS_REF = "UnCompfinal Stringed_Questions";
    final String COMPLETED_QUESTIONS_REF = "Compfinal Stringed_Questions";
    final String SURVEY_QUESTIONS_REF = "survey_questions";
    final String FIRST_PATAMETER_REF = "first_parameter";
    final String SECOND_PATAMETER_REF = "second_parameter";
    final String THIRD_PATAMETER_REF = "third_parameter";
    final String FORTH_PATAMETER_REF = "forth_parameter";
    final String FIFTH_PATAMETER_REF = "fifth_parameter";
    final String QUESTION_ID_REF = "questionId";
    final String QUESTION_REF = "question";
    final String SURVEY_ANSWERS_REF = "survey_answers";
    final String ANSWERS_REF = "answers";
    final String QUESTION_SORT_REF = "sort_question";
    final String SURVEY_ID_REF = "surveyId";


    final String QUESTION_TYPE_REF = "questionType";
    final String RADIO_CHOICES_REF = "radioChoices";
    final String QUESTION_NUMBER_REF = "questionNumber";
    final String DESCRIPTION_LBL_1_REF = "descriptionLbl1";
    final String DESCRIPTION_LBL_2_REF = "descriptionLbl2";
    final String DESCRIPTION_LBL_3_REF = "descriptionLbl3";
    final String SQURED_BTN_REF = "squredBtnNumber";
    final String TOTAL_QUESTION_REF = "totalQuestion";

    final String WAITING_SURVEY_EVENT_IDS_AND_NAMES_REF = "waitingSurveyEventIds";
    final String SURVEY_POOL_REF = "surveyPool";
    final String SURVEY_DISPLAY_NAME_REF = "surveyDisplayName";

    final String CONTENT_HEIGHT_REF = "contentHeight";;
    final String Y_POSITION_REF = "yPosition";
    final String SURVEY_RESULTS_REF = "surveyResults";
    final String IS_MANDATORY_REF = "isMandatory";
    final String SURVEY_DESCRIPTION_REF = "surveyDescription";

    final String START_TIME_REF = "startTime";

    final String MULTIPLE_CHECK_BOX_TYPE_REF = "multipleCheckBox";
    final String MULTIPLE_CHECH_BOX_CHOICES_REF = "checkBoxChoices";

    final String OPEN_ENDED_TYPE_REF = "openEnded";
    final String QUESTION_LBL_CONTENT_HEIGHT = "questionLblContentHeight";
    final String DEADLINE_REF = "deadline";

    final int TABBAR_SURVEY_INDEX_REF = 1;
    final int TABBAR_INVITATION_INDEX_REF = 3;


    //main progress var
    final String xAxis_REF = "xAxis";

    final String yAxis_PROPOSED_STUDY_TIME_REF = "yProposedStudyTime";
    final String yAxis_TOTAL_STUDIED_TIME_REF = "yTotalStudiedTime";

    final String EXIST_REF = "exist";



//Graph Button Names

    final String ROBUST_DATA_REF = "robust";
    final String BLANK_DATA_REF = "blank";


    final String CHART_VIEW_BTN_CLICK_REF = "chartViewBtnClick";
    final String EXIT_TIME_REF = "exitTime";
    final String CLIK_TIME_REF = "clickTime";
    final String TIME_ZONE_REF = "timeZone";
    final String GO_TO_DATE_REF = "goToDate";
    final String CALENDER_BTN_CLICK_REF = "calenderBtnClick";
    final String GRAPH_SWICTH_BTN_CLICKED_REF = "graphSwitch";

    final String SWICTH_TO_REF = "switchTo";

    //alert type survey questions
    final String RELATED_COURSE_REF = "relatedCourse";
    final String FACE_QUESTION_TYPE_REF_FOR_TIMER = "faceQuestionForTimer";
    final String FACE_QUESTION_TYPE_REF_FOR_COMPLETION = "faceQuestionForComplation";
    final String LATE_PROPOSED_START_TIME_QUESTION_REF = "lateStartQuestion";
    final String SELF_EVALUATION_REF = "selfEvaluation";
    final String Evaluation_REF = "evaluation";
    final String SELF_EVALUATION_LATE_START_REF = "IstartedLateBecause";

    final String IS_GRADED_REF = "grade";


//Group Wall

    final String GROUP_MEMBERS_REF = "groupMembers";
    final String ASSIGNED_TO_EMAIL_REF = "assignedToEmail";
    final String ASSIGNED_TO_UID_REF = "assignedToUid";
    final String ASSIGNED_TO_USER_NAME_REF = "assignedToUserName";
    final String GROUP_REF = "group";
    final String STATUS_REF = "status";
    final String WAITING_REF = "waiting";
    final String ACCEPTED_REF = "accepted";
    final String DECLINED_REF = "declined";
    final String GROUP_CHAT_COLLECTION_REF = "groupChat";
    final String ONLINE_CHAT_STATUS_COLLECTION_REF = "onlineStatusForChat";

    final String TAKEN_SUB_GOAL_REF = "taken";
    final String AVAILABLE_SUB_GOAL_REF = "available";

    final String SUBGOAL_STATUS_REF = "subgoalStatus";

    final String IN_PROGRESS_GROUP_SUBGOAL_REF = "inProgress";
    final String COMPLETED_GROUP_SUBGOAL_REF = "compfinal Stringed";

    final String ASSIGNED_SUBGOAL_REF = "assignedSubgoals";

    final String INVITED_GOAL_ID_REF = "invitedGoalId";
    final String INVITED_EMAIL_REF = "invitedEmail";
    final String INVITED_BY_EMAIL_REF = "invitedByEmail";
    final String INVITED_GOAL_NAME_REF = "invitedGoalName";
    final String INVITED_GOAL_DEADLINE_REF = "invitedGoalDeadline";
    final String INVITED_BY_UID_REF = "invitedByUid";
    final String INVITED_GOAl_TASK_TYPE = "invitedGoalTaskType";
    final String ERROR_REF = "error";

    final String TOTAL_WAITING_GOAL_INVITATIONS_REF = "totalWaitingGoalInvitations";
    final String GOAL_INVITATION_PACKS_REF = "goalInvitationPacks";

    final String IN_APP_INVITATION_LOG_REF = "inAppInvitationLog";
    final String INVITATION_TIME_REF = "invitationTime";
    final String LOG_TYPE_REF = "logType";
    final String IN_APP_INVITATION_REF = "inAppInvitation";
    final String STATUS_SELECTION_REF = "statusSelection";
    final String SELECTION_TIME_REF = "selectionTime";

    final String ONLINE_REF = "online";
    final String OFFLINE_REF = "offline";;

    final String ONLINE_STATUS_DIC_REF = "onlineStatusDic";
    final String SENDER_UID_REF = "senderUid";
    final String SENDER_EMAIL_REF = "senderEmail";
    final String GROUP_CHAT_PLACE_HOLDER_TEXT_REF = ". . .";
    final String CHAT_GOAL_ID_FOR_NOTIFICATION_REF = "chatGoalId";

    final String USER_NAME_REF = "userName";


    final String MAIL_COLLECTION_REF = "mail";
    final String WAITING_FOR_EMAIL_RESPONSE_REF = "waitingForEmailInvitation";


    final String TEMPRORARY_NOTIFICATION_COLLECTION_REF = "temproraryNotification";
    final String TEMP_NOTIF_INFO_REF = "notificationInfo";
    final String PROGRESS_PERCENTAGE_REF = "progressPercentage";;
    final String CHARTS_TIME_SPEND_REF = "chartsTimeSpend";
    final String TIME_SPEND_REF = "timeSpend";
    final String MAIN_PROGRESS_REF = "mainProgress";


    final String GOOGLE_FORM_REF = "google_form";
    final String APP_STORE_FIREBASE_REF = "app_store_firebase";
    final String LINK_DOC_ID = "invitationEmailLinks";
    final String LINKS_COLLECTION_REF = "links";



//Activity Types

    final String APP_OPEN_REF = "appOpen";
    final String APP_CLOSE_REF = "appClose";
    final String LOG_ACTIVITY_COLLECTION_REF = "logActivity";
    final String ACTIVE_PERSONAL_GOAL_TABLE_PROFILE_PAGE_REF = "activePersonal_goalTable_profilePage";
    final String ACTIVE_HARD_GOAL_TABLE_PROFILE_PAGE_REF = "activeHard_goalTable_profilePage";
    final String EXPIRED_PERSONAL_GOAL_TABLE_PROFILE_PAGE_REF = "expiredPersonal_goalTable_profilePage";
    final String EXPIRED_HARD_GOAL_TABLE_PROFILE_PAGE_REF = "expiredHard_goalTable_profilePage";
    final String FINISHED_PERSONAL_GOAL_TABLE_PROFILE_PAGE_REF = "finishedPersonal_goalTable_profilePage";
    final String FINISHED_HARD_GOAL_TABLE_PROFILE_PAGE_REF = "finishedHard_goalTable_profilePage";
    final String GO_TO_GOAL_REF = "goToGoal";
    final String GROUP_GOAL_CREATE_REF = "groupGoalCreate";
    final String INDIVIDUAL_GOAL_CREATE_REF = "individualGoalCreate";
    final String GO_TO_EDIT_PROFILE_REF = "goToEditProfile";;
    final String GO_TO_MAIN_PROGRESS_REF = "goToMainProgress";
    final String LOG_OUT_REF = "logOut";

    final String ACTIVE_SEGMENTED_CONTROL_REF = "activeSegmentedControl";
    final String FINISHED_SEGMENTED_CONTROL_REF = "finishedSegmentedControl";
    final String EXPIRED_SEGMENTED_CONTROL_REF = "expiredSegmentedControl";

    final String INDIVIDUAL_GOAL_CREATE_BACK_BTN_TAPPED_REF = "backBtn_individualGoal_Create";;
    final String INDIVIDUAL_GOAL_CREATED_REF = "individualGoal_created";
    final String GROUP_GOAL_CREATED_REF = "groupGoal_created";
    final String GROUP_GOAL_CREATE_BACK_BTN_TAPPED_REF = "backBtn_groupGoal_Create";
    final String EDIT_PROFILE_BACK_BTN_TAPPED_REF = "backBtn_editProfile";
    final String PROFILE_DATA_EDITTED_REF = "profileEdited";


    final String SURVEY_OPEN_FROM_NOTIFICATION_REF = "surveyOpen_from_notification";
    final String SURVEY_OPEN_REF = "surveyOpen";
    final String SURVEY_BACK_REF = "backBtn_survey";
    final String SURVEY_ASNWERED_REF = "surveyAnswered";

    final String SELF_TIME_REPORT_REF = "btnTap_selfTimeReport";
    final String SELF_GRADE_REPORT_REF = "btnTap_selfGradeReport";
    final String SELF_TIME_REPORT_SUBMIT_REF = "submit_selfTimeReport";
    final String SELF_GRADE_REPORT_SUBMIT_REF = "submit_selfGradeReport";

    final String CANCEL_FOR_GRADE_SUBMIT_REF = "cancelBtn_selfGradeReport";
    final String CANCEL_FOR_TIME_SUBMIT_REF = "cancelBtn_selfTimeReport";

    final String GROUP_GOAL_INVITATION_ACCEPT_REF = "accept_groupGoal";
    final String GROUP_GOAL_INVITATION_DECLINE_REF = "decline_groupGoal";


    final String PROGRESS_VIEW_INDIVIDUAL_WALL_REF = "progressViewBtn_IndividualWall";
    final String COMPLETE_GOAL_BTN_TAPPED_REF = "compfinal StringeGoalBtn";
    final String SET_TIMER_BTN_TAPPED_REF = "setTimerBtn";
    final String BACK_BTN_INDIVIDUAL_WALL_TAPPED_REF = "backBtn_individualWall";
    final String SETTINGS_INDIVIDUAL_WALL_TAPPED_REF = "settings_individualWall";
    final String REMOVE_REMINDER_DATA_PICKER_REF = "remove_reminder_datePicker";
    final String SET_REMINDER_BTN_TAPPED_REF = "setReminder";
    final String SUBGOAL_TIMER_SET_REF = "subgoalTimerSet";
    final String STOP_TIMER_REF = "timer_stopBTN";
    final String TIMER_BACK_BTN_TAPPED_REF = "backBtn_timer";
    final String TIMER_RESUME_BTN_TAPPED_REF = "resumeBtn_timer";
    final String START_TIMER_BTN_TAPPED_REF = "startBtn_timer";
    final String BREAK_TIMER_BTN_TAPPED_REF = "breakBtn_timer";
    final String REVISE_DEADLINE_REF = "reviseDeadline";
    final String GO_TO_DATE_BTN_REF = "goToDate_individualProgress";
    final String PROGRESS_LOOK_REF = "_progressLook";
    final String BACK_BTN_INDIVIDUAL_PROGRESS_REF = "backBtn_individualProgress";
    final String GO_TO_SELECTED_DATE_INDIVIDUAL_PROGRESS_REF = "goToSelectedDate_IndividualProgress";
    final String ADD_SUBGOAL_BTN_TAP_INDIVIDUAL_WALL_REF = "addSubgoal_btnTap_individualWall";


    final String SEND_MESSAGE_REF = "sendMessage_groupChat";
    final String SUBGOAL_CLAIM_REF = "subGoal_claim";
    final String SHOW_GROUP_MEMBERS_BTN_REF = "showGroupMembers";
    final String INVITE_USER_BTN_REF = "inviteUser_btnTap";
    final String ADD_SUBGOAL_BTN_TAPPED_REF = "addSubgoal_btnTap";
    final String PROGRESS_TAPPED_GROUP_GOAL_REF = "progressViewBtn_GroupWall";
    final String REPORT_PROGRESS_GROUP_WALL_BTNtaped_REF = "reportProgress_btnTap_groupWall";
    final String SET_TIMER_GROUP_WALL_REF = "setTimer_btnTap_groupWall";
    final String BACK_BTN_TAPPED_GROUP_WALL_REF = "backBtn_groupWall";
    final String REMINDER_SET_GROUP_WALL_REF = "reminderSet_groupWall";
    final String SUBGOAL_ADDED_GROUP_WALL_REF = "subGoal_added_groupWall";
    final String GROUP_MEMBER_INVITED_REF = "groupMember_invited";
    final String INVITE_BACK_BTN_TAPPED_REF = "inviteBackBtnTapped_groupWall";
    final String PERCENTAGE_REPORT_CANCEL_BTN_TAPPED_REF = "percentageReportView_cancelBtntap_groupWall";
    final String PROGRESS_SUBMIT_GROUP_WALL_REF = "progressSubmit_groupWall";
    final String PROGRESS_REPORT_SUBGOAL_SELECTED_REF = "progressReport_subgoalSelected_groupWall";
    final String SUBGOAL_SELECTED_FOR_TIMER_REF = "subgoalSelected_for_timer_groupWall";
    final String PROGRESS_VIEW_CLOSE_BTN_TAPPED_REF = "progressView_closeBtnTap_groupWall";
    final String EXPIRED_GOAL_NOTIFICATION_SEND_REF = "expired_goal_notification_send";
    final String EXPIRED_GOAL_NOTIFICATION_READ_REF = "expired_goal_notification_read";


    final String NO_LOCATION_REF = "no_location";
    
    
    
    
    
    private FirebaseAuth Auth;
    static String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();


    ///Mark: Edit Individual goal VC

    /*
    public void classsaveReminder(String goalId, String reminderId, HashMap<String, Object> reminderData) {
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put(reminderId,reminderData);
        FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(REMINDERS_COLLECTIONS_REF).document(shapeSize.REMINDERS_DOC_ID).update(hashMap);
    }

     */

    public static <T> T getValueOrDefault(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }

    public HashMap<String,Object> createHashmap(String key, Object value){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put(key,value);
        return hashMap;
    }

    public String getAlphaNumericString(int n)
    {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }


    ////Mark: Edit profile funcs
    public void resetPassword(String email) {
        Auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.d("resetPassword", "onComplete: " + "Password reset email successfully send.\nYou need to go through the password reset link");
                }
                else{
                    Log.d("resetPasswordFailed", "onComplete: " + task.getException());
                    //completion(true, err, nil);

                }
            }
        });
    }

    /*
    public resetPassword(String email, completion:@escaping(_ status:Bool, _ error:Error?, _ responseString:String?)->()) {
        FirebaseAuth.getInstance().sendPasswordReset(withEmail: email) { (error) in
            guard let err = error else {
                completion(true, nil,  "Password reset email successfully send.\nYou need to go through the password reset link")
                return
            }
            completion(true, err, nil)
        }
    }



    public void resetPassword(String email, ResultHandler<Object> handler) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(responseString->{
                    if(responseString.isSuccessful()){
                        try{
                            handler.onSuccess(responseString);
                            //completion(true, null,  "Password reset email successfully send.\nYou need to go through the password reset link");

                        }
                        catch(Exception e){
                            handler.onFailure(responseString.getException());
                        }
                    }
                    else{
                        handler.onFailure(responseString.getException());
                    }
                });

    }

     */
    


    public void signOutUser() {
        FirebaseAuth.getInstance().signOut();
            try {
                FirebaseAuth.getInstance().signOut();
            } catch(Exception e){
                Log.d("signOutUserError", "signOutUser: " + e);
            }
    }

   // public void loginUser(String email, String pass, ResultHandler<Object> handler) {
    public void loginUser(String email, String pass, ResultHandler<Object> handler) {
        Log.d("loginUser", "instance initializer: " + "checking Database statics "+ this.uid + " " + this.email);
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    if(FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()==true){
                        //Signed in and verified
                        Log.d("SignedInNotV", "onComplete: singed in, not verified");
                        HashMap<String,Object> handlerData = new HashMap<>();
                        handlerData.put("_status",true);
                        handlerData.put("_error",null);
                        handlerData.put("isLoginVerified", true);
                        handler.onSuccess(handlerData);
                    }
                    else{
                        //Signed in but not verified
                        Log.w("Not Verified", "signInWithEmail:failure", task.getException());
                        HashMap<String,Object> handlerData = new HashMap<>();
                        handlerData.put("_status",true);
                        handlerData.put("_error",null);
                        handlerData.put("isLoginVerified", false);
                        handler.onSuccess(handlerData);
                    }
                    return;
                }
                else{
                    HashMap<String,Object> handlerData = new HashMap<>();
                    handlerData.put("_status",true);
                    handlerData.put("_error",task.getException());
                    handlerData.put("isLoginVerified", false);
                    handler.onSuccess(handlerData);
                }
            }
        });
    }

    /*
    public void loginUser(String email, String pass, ResultHandler<Object> handler) {
        Log.d("loginUser", "instance initializer: " + "checking Database statics \(this.uid)\n \(DatabaseService.email)")
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,pass).addOnCompleteListener(user->{
            if(user.isSuccessful()){
                handler.onSuccess(user);

            }else{
                handler.onFailure(user.getException());
                if(FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()==true){
                    //signed in and verified
                }
                else{
                    //signed in but no verification
                    handler.onFailure(user.getException());
                    //
                }
            }
        });
    }


    public loginUser(String email, String pass, completion:@escaping(_ status:Bool, _ error:Error?, _ isLoginVerified:Bool)->()) {
        Log.d("loginUser", "instance initializer: " + "checking Database statics \(this.uid)\n \(DatabaseService.email)")
        FirebaseAuth.getInstance().signIn(email, pass) { (user, error) in
            if( error != null) {
                completion(true, error, false);
            }
			else {
                if(FirebaseAuth.getInstance().getCurrentUser().isEmailVerified() == true) {
                    //signed in and verified
                    completion(true, null, true);
                }
				else {
                    //signed in but no verification
                    completion(true, null, false);
                }
            }
        }
    }

     */

    public void sendVerificationEmail(String email,ResultHandler<Object> handler){
        FirebaseUser currentUser = Auth.getCurrentUser();
        currentUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    verificationEmailCheck(false,null);
                   // completion(true, error, nil)
                    handler.onSuccess(task.getResult());


                }
                else{
                    verificationEmailCheck(false,task.getException());
                    Log.d("sendVerificationEmail", "onComplete: " + "Please check your e-mail for a verification email.\nThe email might be in your SPAM filder.\n");
                    //completion(true, nil, "Please check your e-mail for a verification email.\nThe email might be in your SPAM folder.\n")
                    handler.onFailure(task.getException());
                }
            }
        });
    }

    /*
    public sendVerificationEmail(String email, completion:@escaping(_ status:Bool, _ error:Error? , _ responseText:String?)->()){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        currentUser.sendEmailVerification { (error) in
            if(error != null){
                verificationEmailCheck(false, error);
                completion(true, error, null);
            }
			else {
                verificationEmailCheck(true, null);
                completion(true, null, "Please check your e-mail for a verification email.\nThe email might be in your SPAM folder.\n");
            }
        }
    }

     */

    public void verificationEmailCheck(boolean isSuccess, Exception e) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(isSuccess){
            HashMap<String,Object> hashMap = createHashmap("uid", currentUser.getUid());
            hashMap.put("status", "success");
            FirebaseFirestore.getInstance().collection("verificationEmail").document(getValueOrDefault(currentUser.getUid(),"uidErr")).set(hashMap);
        }
		else {
		    HashMap<String,Object> hashMap = createHashmap("uid", currentUser.getUid());
		    hashMap.put("error" , e);
            FirebaseFirestore.getInstance().collection("verificationEmail").document(getValueOrDefault(currentUser.getUid(),"uidErr")).set(hashMap);
        }
    }

    public void createNewUserWithAuth(String email,String password,String userName, ResultHandler<Object> handler) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword( email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //Sign in success
                    Log.d("CreateNewUser", "onComplete: " + "sign in success");
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    //completion(true, nil, nil);
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(email)
                            .build();

                    currentUser.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("userProfileUpdated", "User profile updated.");
                                        //createNewUserCollection(currentUser.getUid(),email,userName);
                                        currentUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    verificationEmailCheck(true,null);
                                                   // completion(true, nil, "Please check your e-mail.\nYou can log-in after\nverification process!\nThe email might be in your SPAM folder.\n")
                                                    HashMap<String,Object> handlerData = new HashMap<>();
                                                    handlerData.put("_status",true);
                                                    handlerData.put("_error", task.getException());
                                                    handlerData.put("_responseMessage","Please check your e-mail.\nYou can log-in after\nverification process!\nThe email might be in your SPAM folder.\n");
                                                    handler.onSuccess(handlerData);
                                                }
                                                else {
                                                    verificationEmailCheck(false, task.getException());
                                                    //completion(true, error, nil)
                                                    HashMap<String,Object> handlerData = new HashMap<>();
                                                    handlerData.put("_status",true);
                                                    handlerData.put("_error", task.getException());
                                                    handlerData.put("_responseMessage",null);
                                                    handler.onSuccess(handlerData);
                                                }
                                        }
                                    });
                                    } else {
                                        Log.d("UserProfiledNotUpdated", "onComplete: Failed to update user profile" + task.getException());
                                        //completion(true, error, nil)
                                        HashMap<String,Object> handlerData = new HashMap<>();
                                        handlerData.put("_status",true);
                                        handlerData.put("_error", task.getException());
                                        handlerData.put("_responseMessage","User profile failed to update");
                                        handler.onSuccess(handlerData);
                                    }
                                }
                            });
                }
                else{
                   // completion(true, error, nil)
                    Log.d("FailredCreateUser", "onComplete: " + task.getException());
                    HashMap<String,Object> handlerData = new HashMap<>();
                    handlerData.put("_status",true);
                    handlerData.put("_error", task.getException());
                    handlerData.put("_responseMessage",null);
                    handler.onSuccess(handlerData);
                }
            }
        });
    }

    /*
    public createNewUserWithAuth(String email,String password,String userName, completion:@escaping(_ status:Bool, _ error:Error?, _ responseMessage:String?)->()) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword( email, password) { (user, error) in
            if( error != null) {
                completion(true, error, nil);
            }
            else {
                guard let currentUser = FirebaseAuth.getInstance().currentUser else {
                    completion(true, nil, nil);
                    return;
                }
                let request = currentUser.createProfileChangeRequest();
                request.displayName = email;
                request.commitChanges(completion: nil)
                createNewUserCollection(uid: currentUser.uid,  email,  userName);
                currentUser.sendEmailVerification { (error) in
                    if(error != null ){
                        verificationEmailCheck(false, error);
                        completion(true, error, null);
                    }
                    else {
                        verificationEmailCheck(true, null);
                        completion(true, null, "Please check your e-mail.\nYou can log-in after\nverification process!\nThe email might be in your SPAM folder.\n");
                    }
                }
            }
        }
    }
     */
    /*
    public void createNewUserWithAuth(String email,String password,String userName, ResultHandler<Object> handler) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(user -> {
            //Creates new user with email and password
            if (user.isSuccessful()) {
                handler.onSuccess(user);
                return;
                //Error creating new user with authentication
            } else {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser == null) {
                    return;
                }
                UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                        .setDisplayName(email)
                        .build();
                currentUser.updateProfile(request)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    createNewUserCollection(currentUser, this.uid, email, userName);
                                    currentUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                verificationEmailCheck(true, null);
                                                handler.onSuccess("Please check your e-mail.\nYou can log-in after\nverification process!\nThe email might be in your SPAM folder");
                                            } else {
                                                verificationEmailCheck(false, task.getException());
                                                handler.onSuccess(task.getException());
                                            }
                                        }
                                    });

                                } else {
                                    handler.onFailure(task.getException());
                                }
                            }
                        });
            }
        });
    }

     */

    /*
    private void createNewUserCollection(String uid, String email, String userName) {
        HashMap<String,Object> userData = new HashMap<>();
        userData.put(USER_NAME_REF,userName);
        userData.put(EMAIL,email);
        userData.put(INDIVIDUAL_TOTAL_GOAL_NUMBER_REF,0);
        userData.put(GROUP_TOTAL_GOAL_NUMBER_REF,0);
        userData.put(COMPLETED_TOTAL_GOAL_NUMBER_REF,0);
        userData.put(CREATED_AT,System.currentTimeMillis());

        guard let delegate = UIApplication.shared.delegate as? AppDelegate else {return}
        DocumentReference refForToken = FirebaseFirestore.getInstance().collection(TOKENS_TABLE_REF).document(this.uid);
        WriteBatch batch = FirebaseFirestore.getInstance().batch();
        batch.set(userData,  FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(uid));
        HashMap<String,Object> hashMap = createHashmap(TOTAL_WAITING_SURVEY_REF,0);
        hashMap.put( TOTAL_WAITING_GOAL_INVITATIONS_REF,0);
        batch.set(hashMap,FirebaseFirestore.getInstance().collection(NOTIFICATION_TABLE_REF).document(uid));
        batch.set([
                DEVICE_TOKEN_REF: delegate.sharedTokenData[DEVICE_TOKEN_REF] as Any,
        FCT_REF: delegate.sharedTokenData[FCT_REF] as Any
		], forDocument: refForToken)
        batch.commit();
        UserDataModel.sharedInstance.userName = userName;
        checkIfThereIsWaitingGroupGoalInvitation(email,uid);
    }
    */
   /*
    public void checkIfThereIsWaitingGroupGoalInvitation(String email,String newUid) {
        DocumentReference tempNotifRef = FirebaseFirestore.getInstance().collection(TEMPRORARY_NOTIFICATION_COLLECTION_REF).document(shapeSize.TEMP_NOTIFICATION_DOC_ID_REF);
        tempNotifRef.getDocument { (docSnap, error) in
            if error == nil {
                guard let snap = docSnap else {return}
                do {
                    let data = try snap.data(as: temporaryNotificationModel.self)
                    guard let notifs = data?.notificationInfo else {return}
                    let check = notifs.filter({$0.value.invitedEmail == email}).first
                    guard let notifCatch = check else {
                        print("there no match on  waiting temp notif")
                        return
                    }
                    //there is match
                    updateTempraryUIDs( notifCatch.key, notifCatch.value.invitedGoalId,  notifCatch.value.invitedGoalDeadline,  notifCatch.value.invitedByEmail, notifCatch.value.invitedGoalName, notifCatch.value.invitedGoalTaskType);

                }catch {
                    //err handle here
                }
            }
        }
    }
*/

    /*
    public checkEmailIsInvitedBefore(invitedEmail:String, completion:@escaping(_ isInvited:Bool)->()) {
        FirebaseFirestore.getInstance().collection(TEMPRORARY_NOTIFICATION_COLLECTION_REF).document(shapeSize.TEMP_NOTIFICATION_DOC_ID_REF).getDocument { (docSnap, error) in
            guard let docSnap = docSnap else {
                completion(false)
                return}
            do {
                let data = try docSnap.data(as: temporaryNotificationModel.self)
                guard let notifData = data?.notificationInfo else {
                    completion(false)
                    return
                }
                let chechUser = notifData.filter({$0.value.invitedEmail == invitedEmail}).first
                guard let _ = chechUser else {
                    completion(false);
                    return;
                }
                completion(true);

            }catch {
                completion(false);
            }
        }
    }

     */

    ///End of Edit profile funcs

}

