package com.lejiebennett.proccoli2.NewModels;


import static com.lejiebennett.proccoli2.NewModels.LogActivityModel.activityChain;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class DataServices {

    ShapeSize shapeSize = new ShapeSize();

    //colelction references
    static final String USER_COLLECTION_REF = "users";
    static final String GOALS_COLLECTION_REF  = "goals";
    final String REVISION_COLLECTION_REF = "revisions";
    final String PERSONAL_NOTE_COLLEECTION_REF = "personalNote";
    final String REMINDERS_COLLECTIONS_REF = "reminders";
    static final String PERSONAL_GOALS_COLLECTION_REF = "personalGoals";
    static final String INDIVIDUAL_PROGRESS_DATA_COLLECTION_REF = "progressData";
    static final String LOG_DATA_COLLECTION_REF = "logData";


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
    static final String CREATED_AT = "createdAt";
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
    static final String SUB_GOAL_PACK_REF = "subGoalPack";
    static final String TOTAL_STUDIED_TIME_REF = "totalStudiedTime";
    final String NO_SUB_GOAL_REF = "noSubGoal";
    final String SUB_GOAL_ID_REF = "subgoalId";
    final String SUB_GOAL_CHECK_TIME_REF = "subGoalCheckTime";
    final String SUB_GOAL_UN_CHECK_TIME_REF = "subGoalUnCheckTime";


    final String NOTE_REF = "note";
    final String NOTE_ID_REF = "noteId";
    static final String STUDY_TIME_REF = "studyTime";
    static final String STUDY_TIME_DOC_ID_REF = "KDFJNSDKjiew32mdsdsf03D";

    final String TOTAL_BREAK_TIMES_REF = "totalBreakTimes";
    static final String IS_FINISHED_REF = "isFinished";
    final String PROPOSED_STUDY_TIME_REF = "proposedStudyTime";
    final String TOTAL_PROPOSED_STUDY_TIME_REF = "totalProposedStudyTime";

    final String BREAKS_REF = "breaks";
    final String RESUMES_REF = "resumes";
    final String BREAK_TIME_REF = "breakTime";
    final String STOP_TIME_REF = "stopTime";
    static final String STUDIED_TIME_REF = "studiedTime";
    final String STOPED_STUYD_TIME_REF = "stopedStudyTime";
    final String START_LOCATION_REF = "startLocation";
    final String BREAK_LOCATION_REF = "breakLocation";
    final String STOP_LOCATION_REF = "stopLocation";
    static final String FINISH_LOCATION_REF = "stopLocation";
    static final String FINISH_TIME_REF = "finishTime";
    final String RESUME_TIME_REF = "resumeTime";
    final String RESUME_LOCATION_REF = "resumeLocation";
    final String INDIVIDUAL_WALL_TIMER_LBL_REF = "individualWall";
    final String GROUP_WALL_TIMER_LBL_REF = "groupWall";
    final String PROFILE_PAGE_TIMER_LBL_REF = "profileVC";
    final String TIMER_VIEWS_TIMER_LBL_REF = "timerViewsLbl";
    static final String REMINDER_TIME_REF = "reminderTime";
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


    static final String CHART_VIEW_BTN_CLICK_REF = "chartViewBtnClick";
    final String EXIT_TIME_REF = "exitTime";
    static final String CLIK_TIME_REF = "clickTime";
    final String TIME_ZONE_REF = "timeZone";
    static final String GO_TO_DATE_REF = "goToDate";
    final String CALENDER_BTN_CLICK_REF = "calenderBtnClick";
    final String GRAPH_SWICTH_BTN_CLICKED_REF = "graphSwitch";

    final String SWICTH_TO_REF = "switchTo";

    //alert type survey questions
    final String RELATED_COURSE_REF = "relatedCourse";
    final String FACE_QUESTION_TYPE_REF_FOR_TIMER = "faceQuestionForTimer";
    final String FACE_QUESTION_TYPE_REF_FOR_COMPLETION = "faceQuestionForComplation";
    final String LATE_PROPOSED_START_TIME_QUESTION_REF = "lateStartQuestion";
    static final String SELF_EVALUATION_REF = "selfEvaluation";
    final String Evaluation_REF = "evaluation";
    final String SELF_EVALUATION_LATE_START_REF = "IstartedLateBecause";

    static final String IS_GRADED_REF = "grade";


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
    static final String SELF_GRADE_REPORT_SUBMIT_REF = "submit_selfGradeReport";

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


    static final String NO_LOCATION_REF = "no_location";
    
    
    

    
    private static FirebaseAuth auth = FirebaseAuth.getInstance();
    static String uid;
    String email;
    FirebaseUser user;
    public void setEmail(){
        email = user.getEmail();
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String uid){
        this.uid = uid;
    }

    public void setEmail(String email){
        this.email = email;
    }

    //   static String uid = auth.getCurrentUser().getUid();
 //   String email = auth.getCurrentUser().getEmail();



    public static final DataServices sharedInstance = new DataServices();
    public static DataServices getInstance() {
        return sharedInstance;
    }


    public static <T> T getValueOrDefault(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }

    public static HashMap<String,Object> createHashmap(String key, Object value){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put(key,value);
        return hashMap;
    }

    public static String getAlphaNumericString(int n)
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
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
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
        auth.sendPasswordReset(withEmail: email) { (error) in
            guard let err = error else {
                completion(true, nil,  "Password reset email successfully send.\nYou need to go through the password reset link")
                return
            }
            completion(true, err, nil)
        }
    }



    public void resetPassword(String email, ResultHandler<Object> handler) {
        auth.sendPasswordResetEmail(email)
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
        auth.signOut();
            try {
                auth.signOut();
            } catch(Exception e){
                Log.d("signOutUserError", "signOutUser: " + e);
            }
    }

   // public void loginUser(String email, String pass, ResultHandler<Object> handler) {
    public void loginUser(String email, String pass, ResultHandler<Object> handler) {
        Log.d("loginUser", "instance initializer: " + "checking Database statics "+ this.uid + " " + this.email);
        auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    if(auth.getCurrentUser().isEmailVerified()==true){
                        //Signed in and verified
                        uid = auth.getCurrentUser().getUid();
                        user = auth.getCurrentUser();
                        setEmail();
                        Log.d("getUserInfo", "onComplete: " + email + uid);



                        Log.d("SignedInNotV", "onComplete: singed in, verified");
                        HashMap<String,Object> handlerData = new HashMap<>();
                        handlerData.put("_status",true);
                        handlerData.put("_error",null);
                        handlerData.put("isLoginVerified", true);
                        handlerData.put("email",email);
                        handlerData.put("uid",uid);
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
                    Log.d("DataServiceLoginFailed", "onComplete: Failed");
                    HashMap<String,Object> handlerData = new HashMap<>();
                    handlerData.put("_status",true);
                    handlerData.put("_error",task.getException());
                    handlerData.put("isLoginVerified", false);
                    handler.onSuccess(handlerData);
                }
            }
        });
    }

    public void sendVerificationEmail(String email,ResultHandler<Object> handler){
        FirebaseUser currentUser = auth.getCurrentUser();
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
        FirebaseUser currentUser = auth.getCurrentUser();
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
        FirebaseUser currentUser = auth.getCurrentUser();
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
        auth.createUserWithEmailAndPassword( email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //Sign in success
                    Log.d("CreateNewUser", "onComplete: " + "sign in success");
                    FirebaseUser currentUser = auth.getCurrentUser();
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
                                        createNewUserCollection(currentUser.getUid(),email,userName);
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
                    Log.d("here", "onComplete: " + handlerData);
                    handler.onSuccess(handlerData);
                }
            }
        });
    }

    /*
    public createNewUserWithAuth(String email,String password,String userName, completion:@escaping(_ status:Bool, _ error:Error?, _ responseMessage:String?)->()) {
        auth.createUserWithEmailAndPassword( email, password) { (user, error) in
            if( error != null) {
                completion(true, error, nil);
            }
            else {
                guard let currentUser = auth.currentUser else {
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
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(user -> {
            //Creates new user with email and password
            if (user.isSuccessful()) {
                handler.onSuccess(user);
                return;
                //Error creating new user with authentication
            } else {
                FirebaseUser currentUser = auth.getCurrentUser();
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

    public void createNewUserCollection(String uid,String email,String userName) {
        Log.d("createNewUser", "createNewUserCollection: start" );
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put(USER_NAME_REF,userName);
        hashMap.put(EMAIL, email);
        hashMap.put(INDIVIDUAL_TOTAL_GOAL_NUMBER_REF,0);
        hashMap.put(GROUP_TOTAL_GOAL_NUMBER_REF,0);
        hashMap.put(COMPLETED_TOTAL_GOAL_NUMBER_REF ,0);
        long current = System.currentTimeMillis()/100L;
        hashMap.put(CREATED_AT, current);
        Log.d("createNewUser", "createNewUserCollection: USER DATA" + hashMap);
        //guard let delegate = UIApplication.shared.delegate as? AppDelegate else {return}
        Log.d("createNewUser", "createNewUserCollection: " + TOKENS_TABLE_REF + "uid: " + this.uid);
        DocumentReference refForToken = FirebaseFirestore.getInstance().collection(TOKENS_TABLE_REF).document(this.uid);
        Log.d("createNewUser", "createNewUserCollection: passed");
        WriteBatch batch = FirebaseFirestore.getInstance().batch();
        batch.set(FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(uid),hashMap);
        Log.d("createNewUser", "createNewUserCollection: set passed");

        HashMap<String,Object> hashMap1 = createHashmap(TOTAL_WAITING_SURVEY_REF , 0);
        hashMap1.put(TOTAL_WAITING_GOAL_INVITATIONS_REF , 0);
        batch.set(FirebaseFirestore.getInstance().collection(NOTIFICATION_TABLE_REF).document(uid),hashMap1);
        HashMap<String,Object> hashMap2 = createHashmap(DEVICE_TOKEN_REF,"delegate.sharedTokenData[DEVICE_TOKEN_REF]");
        hashMap2.put(FCT_REF, "delegate.sharedTokenData[FCT_REF]");
        batch.set(refForToken,hashMap2);
        batch.commit();
        UserDataModel.sharedInstance.userName = userName;
      //  checkIfThereIsWaitingGroupGoalInvitation(email, uid);
    }

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
    static <K, V> HashMap<String, Object> filterByValue(Map<K, V> map, Predicate<V> predicate) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return map.entrySet()
                    .stream().filter(entry -> predicate.test(entry.getValue()))
                    .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
        }
        return null;
    }

     */

    /*
    public void checkIfThereIsWaitingGroupGoalInvitation(String email,String newUid) {
        DocumentReference tempNotifRef = FirebaseFirestore.getInstance().collection(TEMPRORARY_NOTIFICATION_COLLECTION_REF).document(shapeSize.TEMP_NOTIFICATION_DOC_ID_REF);

        tempNotifRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getException()==null){
                    DocumentSnapshot snap = task.getResult();
                    TemporaryNotificationModel data = (TemporaryNotificationModel) snap.getData();
                    HashMap<String, TemporaryNotificationModel.temporaryNotificationModelFields> notifs = data.notificationInfo;
                    if(notifs==null)
                        return;
                    HashMap<String,Object> check = filterByValue(notifs, value -> value.equals(email));
                    HashMap<String,Object> notifCatch = check;
                    if(notifCatch==null){
                        Log.d("checkIfGGInvite", "onComplete: " + "there no match on  waiting temp notif");
                        return;
                    }
                    else{
                        updateTempraryUIDs(notifCatch.keySet().toString(), notifCatch.get(notifCatch.keySet().toString()).invitedGoalId, notifCatch.value.invitedGoalDeadline, notifCatch.value.invitedByEmail, notifCatch.value.invitedGoalName, notifCatch.value.invitedGoalTaskType);
                    }

                }
                else{
                    return;
                }
            }
        });
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

    /*
    public void checkFCM() {
        guard let delegate = UIApplication.shared.delegate as? AppDelegate else {return}
        this.getDeviceTokens(this.uid) { (status, result) in
            if status {
                if result != delegate.sharedTokenData[FCT_REF] && delegate.sharedTokenData[FCT_REF] != "" {
                    //refresh the fcm here
                    this.sendTokens();
                }
            }
        }
    }
    */

    ///Mark: Edit Individual goal VC
    public void saveReminder(String goalId,String reminderId, HashMap<String,Object> reminderData) {
       FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(REMINDERS_COLLECTIONS_REF).document("shapeSize.REMINDERS_DOC_ID").update(reminderId, reminderData);
    }

    public void savePersonalNote(String goalId, PersonalNoteModel note) {
        Log.d("singleGoalView", "savePersonalNote: " + goalId);
        Log.d("singleGoalView", "savePersonalNote: " + note);
        FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(PERSONAL_NOTE_COLLEECTION_REF).document("shapeSize.PERSONAL_NOTE_DOC_ID_REF").update(PersonalNoteModel.jsonConverter(note));
    }

    public HashMap<String,Object> prepareReminderData(long date){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put(REMINDER_TIME_REF,date);
        long current = System.currentTimeMillis()/100L;
        hashMap.put(CREATED_AT, current);
        hashMap.put("createdByUid", this.uid);
        hashMap.put("createdByEmail", this.email);
        return hashMap;
    }

   // public void requestPersonalNotes(String goalId) {
    public void requestPersonalNotes(String goalId, ResultHandler<Object> handler){
        HashMap<String,Object> hashMap = new HashMap<>();
        Log.d("requestPersonalNotes", "requestPersonalNotes: ");
        FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(PERSONAL_NOTE_COLLEECTION_REF).document("shapeSize.PERSONAL_NOTE_DOC_ID_REF").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    if(task.getException()==null){

                        hashMap.put("personalNotes",PersonalNoteModel.parseData((DocumentSnapshot) task.getResult()));
                        hashMap.put("mainGoalId",goalId);
                       // PersonalNoteViews.sharedInstance.setData(personalNotes: personalNoteModel.parseData(documentSnap: docSnap), mainGoalId: goalId)
                        handler.onSuccess(hashMap);
                    }
                }
                else{
                    hashMap.put("_error",task.getException().getLocalizedMessage());
                    handler.onSuccess(hashMap);
                }
            }
        });
    }
    public void revisePersonalOrHardDeadline(long newDeadline, long oldDeadline, boolean isPersonalDeadline,String goalId) {
        WriteBatch batch = FirebaseFirestore.getInstance().batch();
        DocumentReference revisionCollectionRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(REVISION_COLLECTION_REF).document();
        DocumentReference goalInUserCollection = FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(this.uid).collection(PERSONAL_GOALS_COLLECTION_REF).document(goalId);
        DocumentReference goalInGeneralGoalCollection = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId);
        if(isPersonalDeadline == true) {

            batch.update(goalInUserCollection, createHashmap(PERSONAL_DEADLINE_REF,newDeadline));
            batch.update(goalInGeneralGoalCollection, createHashmap(PERSONAL_DEADLINE_REF, newDeadline));
            long current = System.currentTimeMillis()/100L;
            HashMap<String,Object> hashMap = createHashmap(CREATED_AT,current);
            hashMap.put("revisionType","personalDeadline");
            hashMap.put("oldPersonalDeadline",oldDeadline);
            hashMap.put("newPersonalDeadline", newDeadline);
            batch.set(revisionCollectionRef,hashMap);
            Log.d("reviseHard", "revisePersonalOrHardDeadline: "+hashMap);
        }
        else {
            //update when is due
            batch.update(goalInUserCollection, createHashmap(WHEN_IS_IT_DUE_REF,newDeadline));
            batch.update(goalInGeneralGoalCollection, createHashmap(WHEN_IS_IT_DUE_REF, newDeadline));
            long current = System.currentTimeMillis()/100L;
            HashMap<String,Object> hashMap = createHashmap(CREATED_AT,current);
            hashMap.put("revisionType", "hardDeadline");
            hashMap.put("oldPersonalDeadline", oldDeadline);
            hashMap.put( "newPersonalDeadline",newDeadline);
            batch.set(revisionCollectionRef,hashMap);
            Log.d("revisePersonal", "revisePersonalOrHardDeadline: "+hashMap);

        }

        batch.commit();

    }

    public void addNewSubGoal(IndividualSubGoalStructModel data, String goalId) {
        //
        WriteBatch batch = FirebaseFirestore.getInstance().batch();
        DocumentReference generalGoalDocRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId);
        DocumentReference studyTimeDocRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(STUDY_TIME_REF).document(data.get_subGoalId());
        batch.update(generalGoalDocRef, createHashmap(SUB_GOAL_PACK_REF + "." + data.get_subGoalId(),IndividualSubGoalStructModel.jsonFormatterSingleIndividualSubGoal(data)));
        batch.set(studyTimeDocRef,createHashmap(EXIST_REF,true));

        batch.commit();
    }
    public void deleteSubGoal(String subgoalId,String goalId) {
        HashMap<String,Object> hashMap = createHashmap(SUB_GOAL_PACK_REF + "." + subgoalId + "." + IS_DELETED_REF,true);
        long current = System.currentTimeMillis()/100L;
        hashMap.put(SUB_GOAL_PACK_REF +"." + subgoalId + "." + SUB_GOAL_DELETE_TIME,current);
        FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).update(hashMap);
    }

    public void saveSubGoalRevisions(HashMap<String,Object> revisionData,String goalId,String subgoalId, HashMap<String,Object> newSubGoaldata) {
        WriteBatch batch = FirebaseFirestore.getInstance().batch();
        DocumentReference revisionCollectionRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(REVISION_COLLECTION_REF).document();
        DocumentReference goalInGeneralGoalCollection = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId);
        batch.set(revisionCollectionRef,revisionData);

        for(String key: newSubGoaldata.keySet()){
            batch.update(goalInGeneralGoalCollection, createHashmap(SUB_GOAL_PACK_REF + "." + subgoalId + "." + key,getValueOrDefault(newSubGoaldata.get(key),createHashmap("err","err"))));
        }

        batch.commit();
    }

    ///End of Edit Individual goal VC

    ////Mark: Profile VC funcs
    public void callUserInfo(ResultHandler<Object> handler) {
        Log.d("check", "callUserInfo: " + uid + email);
        FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(this.uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    Log.d("callUserInfo", "onComplete: ");
                    DocumentSnapshot docSnap = task.getResult();


                   // this.checkFCM();

                    UserDataModel.parseData(docSnap);
                    HashMap<String, Object> hashMapHandler = new HashMap<>();
                    hashMapHandler.put("_status", true);
                    hashMapHandler.put("_error", null);
                    handler.onSuccess(hashMapHandler);
                    return;

                } else {
                    handler.onFailure(task.getException());
                }
            }
        });
    }

    public void updateUserInfo(HashMap<String,Object> updateData) {
        Log.d("updateUserInfo", "updateUserInfo: " + updateData);
        Log.d("updateUserInfoUid", "updateUserInfo: " + this.uid);
        FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(this.uid).update(updateData);
    }

    public void requestPersonalGoals(ResultHandler<Object> handler){
        FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(this.uid).collection(PERSONAL_GOALS_COLLECTION_REF).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    Log.d("requestTask", "onComplete: task Complete");
                    if(task.getException()==null) {
                        Log.d("requestTask", "onComplete: No exceptions");
                        HashMap<String,Object> handlerHashmap = new HashMap<>();
                        handlerHashmap.put("_status", true);
                        handlerHashmap.put("_response",GoalModel.parseGoalsData((QuerySnapshot)task.getResult()));
                        handlerHashmap.put("_error",null);
                        Log.d("requestPersonalGoals", "onComplete: " + handlerHashmap);
                        handler.onSuccess(handlerHashmap);
                        return;
                    }
                    else{
                        HashMap<String,Object> handlerHashmap = new HashMap<>();
                        handlerHashmap.put("_status", true);
                        handlerHashmap.put("_response",null);
                        handlerHashmap.put("_error",task.getException());
                        handler.onSuccess(handlerHashmap);
                    }
                }
            }
        });
    }
    //End of profileVC funcs


    ///Mark: CreateIndividualGoal VC
    public IndividualGoalModel saveIndividualGoal(IndividualGoalModel data){
        WriteBatch batch = FirebaseFirestore.getInstance().batch();
        DocumentReference generalGoalsCollectionRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document();
        DocumentReference userPersonalGoalsRef = FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(this.uid).collection(PERSONAL_GOALS_COLLECTION_REF).document(generalGoalsCollectionRef.getId());
        DocumentReference userDocRef = FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(this.uid);
        DocumentReference personalNoteDocRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(generalGoalsCollectionRef.getId()).collection(PERSONAL_NOTE_COLLEECTION_REF).document("shapeSize.PERSONAL_NOTE_DOC_ID_REF");
        CollectionReference studyTimeCollection = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(generalGoalsCollectionRef.getId()).collection(STUDY_TIME_REF);
        DocumentReference noSubGoalDocReference = studyTimeCollection.document(NO_SUB_GOAL_REF);
        DocumentReference goalReminderDocRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(generalGoalsCollectionRef.getId()).collection(REMINDERS_COLLECTIONS_REF).document("shapeSize.REMINDERS_DOC_ID");

        DocumentReference individualWallProgressDocRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(generalGoalsCollectionRef.getId()).collection(INDIVIDUAL_PROGRESS_DATA_COLLECTION_REF).document("shapeSize.individualProgressDocID");
        DocumentReference individualWallProgressLogDataDocRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(generalGoalsCollectionRef.getId()).collection(INDIVIDUAL_PROGRESS_DATA_COLLECTION_REF).document("shapeSize.individualProgressDocID").collection(LOG_DATA_COLLECTION_REF).document(CHART_VIEW_BTN_CLICK_REF);

        data.goalId = generalGoalsCollectionRef.getId();
        batch.set(generalGoalsCollectionRef,IndividualGoalModel.jsonFormatterForIndividualEvent(data));
        batch.update(userDocRef, createHashmap(INDIVIDUAL_TOTAL_GOAL_NUMBER_REF, FieldValue.increment(1)));
        long current = System.currentTimeMillis()/100L;
        batch.set(personalNoteDocRef,createHashmap(CREATED_AT,current));
        batch.set(goalReminderDocRef,createHashmap(EXIST_REF,true));
        batch.set(userPersonalGoalsRef,GoalModel.jsonFormatterForGoals(IndividualGoalModel.goalsModelConverterForDataWrite(data)));
        batch.set(noSubGoalDocReference,createHashmap(EXIST_REF,true));
        batch.set(individualWallProgressDocRef,createHashmap(EXIST_REF,true));
        batch.set( individualWallProgressLogDataDocRef,createHashmap(EXIST_REF,true));
        ArrayList<IndividualSubGoalStructModel> subGoals = data.subGoals;
        if(subGoals!=null) {
            for(IndividualSubGoalStructModel subGoal : subGoals){
                batch.set(studyTimeCollection.document(subGoal.get_subGoalId()),createHashmap(EXIST_REF,true));

            }
        }

        batch.commit();
        return data;

    }

    /// End of CreateIndividualVC
    ///Group Wall Page
    public void finishTimerForGroupGoal(String goalId,String subgoalId,String studyId, int studiedTime,HashMap<String,Object> finishData) {
        //finish means users finished the whole time that he proposed to study
        //first update the main goal sub pack/subgoal/ total studied Time
        WriteBatch batch = FirebaseFirestore.getInstance().batch();
        DocumentReference mainGoalDoc = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId);
        //second update studyCollection/subgoal/studyid
        batch.update( mainGoalDoc,createHashmap(SUB_GOAL_PACK_REF + "." + subgoalId + "." + TOTAL_STUDIED_TIME_REF, FieldValue.increment((int)(studiedTime))));

        DocumentReference studyCollectionRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(STUDY_TIME_REF).document(STUDY_TIME_DOC_ID_REF);

        HashMap<String,Object> hashMap = createHashmap(this.uid + "." + subgoalId + "." + studyId + "." + FINISH_TIME_REF,finishData);
        hashMap.put(this.uid+ "." + subgoalId+"."+studyId +"." + STUDIED_TIME_REF,studiedTime);
        hashMap.put(this.uid + "." + subgoalId +"." + studyId + "." + IS_FINISHED_REF,true);
        batch.update(studyCollectionRef,hashMap);

        DocumentReference personalCollectionGoalRef = FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(this.uid).collection(PERSONAL_GOALS_COLLECTION_REF).document(goalId);
        batch.update(personalCollectionGoalRef,createHashmap(TOTAL_STUDIED_TIME_REF ,FieldValue.increment((int)(studiedTime))));

        batch.commit();

        //TabbarVC.sharedInstance?.profileVCInstance.isProgressBtnAnimationOn = true;
    }
    public void resumeTimerForGroupGoal(String goalId, String subgoalId,String studyId,HashMap<String,Object> resumeData) {
        FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(STUDY_TIME_REF).document(STUDY_TIME_DOC_ID_REF).update(createHashmap(this.uid + "." + subgoalId + "." + studyId + "." + RESUMES_REF + "." + getAlphaNumericString(11), resumeData));


    }
    public void breakTimerForGroupGoal(String goalId, String subgoalId, String studyId, HashMap<String,Object> breakData) {
        HashMap<String,Object> hashMap = createHashmap(this.uid + "." + subgoalId + "." + studyId + "." + BREAKS_REF + "." + getAlphaNumericString(11) ,breakData);
        hashMap.put(this.uid + "." + subgoalId + "." + studyId + "." + TOTAL_BREAK_TIMES_REF,FieldValue.increment(1));
        FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(STUDY_TIME_REF).document(STUDY_TIME_DOC_ID_REF).
                update(hashMap);

    }

    public void startTimerForGroupGoal(String goalId, String subgoalId, String studyId, long proposedStudyTime) {
        long current = System.currentTimeMillis()/100L;
        TimerDataModel startData = new TimerDataModel(studyId,  current,  0,  false, proposedStudyTime);
        FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(STUDY_TIME_REF).document(STUDY_TIME_DOC_ID_REF).update(TimerDataModel.jsonFormatterForGroupGoal(this.uid, subgoalId, startData));
    }

    public void sendTotalStudiedTimeForGroupGoalStopTimer(String goalId,String subgoalId,double totalStudiedTime,HashMap<String,Object> stopData,String studyId) {
        WriteBatch batch = FirebaseFirestore.getInstance().batch();
        DocumentReference goalRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId);
        DocumentReference personalCollectionGoalRef = FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(this.uid).collection(PERSONAL_GOALS_COLLECTION_REF).document(goalId);
        DocumentReference studyCollectionRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(STUDY_TIME_REF).document(STUDY_TIME_DOC_ID_REF);
        batch.update(goalRef,createHashmap(SUB_GOAL_PACK_REF +"." + subgoalId + "." + TOTAL_STUDIED_TIME_REF, FieldValue.increment((int)(totalStudiedTime))));
        batch.update(personalCollectionGoalRef,createHashmap(TOTAL_STUDIED_TIME_REF,FieldValue.increment((int)(totalStudiedTime))));

        HashMap<String,Object> hashMap = createHashmap(this.uid + "." + subgoalId + "." + studyId + "." + STOP_TIME_REF, stopData);
        hashMap.put(this.uid + "." + subgoalId + "." + studyId + "." + STUDIED_TIME_REF,totalStudiedTime);
        batch.update(studyCollectionRef,hashMap);

        batch.commit();

        //TabbarVC.sharedInstance.profileVCInstance.isProgressBtnAnimationOn = true;
    }

    public GroupGoalModel createGroupGoal(String bigGoal, String goalType, boolean isGoalCompleted, String taskType, String goalCreaterUid, HashMap<String,groupMembersPack> groupMembers, String relatedCourse, long whenIsItDue, long createdAt, ResultHandler<Object> handler) {
            WriteBatch batch = FirebaseFirestore.getInstance().batch();
            DocumentReference docForGoal = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document();
            GroupGoalModel groupGoal = new GroupGoalModel(docForGoal.getId(), bigGoal, goalType, isGoalCompleted, taskType, goalCreaterUid, groupMembers, relatedCourse, whenIsItDue, createdAt, null);
            DocumentReference chatDocRef = docForGoal.collection(GROUP_CHAT_COLLECTION_REF).document(shapeSize.groupChatDocID);
            DocumentReference studyTimerREf = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(docForGoal.getId()).collection(STUDY_TIME_REF).document(STUDY_TIME_DOC_ID_REF);
            DocumentReference chatOnlineOfflineRef = docForGoal.collection(ONLINE_CHAT_STATUS_COLLECTION_REF).document(shapeSize.USER_ONLINE_OFFLINE_STATUS_DOC_ID_REF);
            DocumentReference reminderDocRef = docForGoal.collection(REMINDERS_COLLECTIONS_REF).document(shapeSize.REMINDERS_DOC_ID);

            DocumentReference userDocRef = FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(this.uid);
            DocumentReference personalCollectionRef = userDocRef.collection(PERSONAL_GOALS_COLLECTION_REF).document(docForGoal.getId());
            groupGoalForPersonalCollection dataForPersonalCollection = new groupGoalForPersonalCollection(docForGoal.getId(), groupGoal.bigGoal, groupGoal.goalType, groupGoal.isGoalCompleted, groupGoal.taskType, groupGoal.goalCreaterUid, groupGoal.whenIsItDue, groupGoal.createdAt, 0.0, 0.0, groupGoal.whenIsItDue);

            try {
                batch.set(docForGoal, groupGoal);
            } catch (Exception e) {
                HashMap<String, Object> handlerResult = new HashMap<>();
                handlerResult.put("_success", false);
                handlerResult.put("_response", null);
                handlerResult.put("_error", e);
                handler.onSuccess(handlerResult);
            }
            try {
                batch.set(personalCollectionRef, dataForPersonalCollection);
                batch.update(userDocRef, createHashmap(GROUP_TOTAL_GOAL_NUMBER_REF, FieldValue.increment((1))));
                batch.set(chatDocRef,createHashmap("exists",true));
                batch.set(reminderDocRef, createHashmap("exist", true));
                HashMap<String, Object> hashMap1 = createHashmap(this.uid, "userOnlineStatus.prepareJsonForWritingCurrentUser()");
                HashMap<String, Object> hashMap = createHashmap(ONLINE_STATUS_DIC_REF, hashMap1);
                batch.set(chatOnlineOfflineRef, hashMap);
                batch.set(studyTimerREf, createHashmap("exist", true));
                batch.commit();
                HashMap<String, Object> handlerResult = new HashMap<>();
                handlerResult.put("_success", true);
                handlerResult.put("_response", groupGoal);
                handlerResult.put("_error", null);
                handler.onSuccess(handlerResult);

            } catch (Exception e) {
                HashMap<String, Object> handlerResult = new HashMap<>();
                handlerResult.put("_success", false);
                handlerResult.put("_response", null);
                handlerResult.put("_error", e);
                handler.onSuccess(handlerResult);
            }
        return groupGoal;
    }
    public void completeGroupGoal(String goalId, ArrayList<String> uids){
        HashMap<String,Object> hashMap = createHashmap(IS_GOAL_COMPLETED_REF,true);
        long current = System.currentTimeMillis()/100L;
        hashMap.put(COMPLETED_DATE_REF,current);
        FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).update(hashMap);
        for(String  uid : uids) {
            WriteBatch batch = FirebaseFirestore.getInstance().batch();
            batch.update(FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(uid),createHashmap(COMPLETED_GOAL_NUMBERS_REF,FieldValue.increment((int)(1))));
            batch.update(FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(uid).collection(PERSONAL_GOALS_COLLECTION_REF).document(goalId),createHashmap( IS_GOAL_COMPLETED_REF , true));
            batch.commit();
        }
    }

    ///Individual Chart VC log data
    public void chartOpenBtnClickIndividualWall(String goalId, String clickEventId) {
        long current = System.currentTimeMillis()/100L;
        HashMap<String,Object> hashMap = createHashmap(CLIK_TIME_REF ,current);
        hashMap.put(TIME_ZONE_REF, Calendar.getInstance().getTimeZone().getDisplayName());

        FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(INDIVIDUAL_PROGRESS_DATA_COLLECTION_REF).document("shapeSize.individualProgressDocID").collection(LOG_DATA_COLLECTION_REF).document(CHART_VIEW_BTN_CLICK_REF).update(createHashmap(CHART_VIEW_BTN_CLICK_REF + "." + clickEventId,hashMap));
    }
    public void chartClosedIndividualWall(String goalId,String clickEventId) {
        long current = System.currentTimeMillis()/100L;
        FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(INDIVIDUAL_PROGRESS_DATA_COLLECTION_REF).document("shapeSize.individualProgressDocID").collection(LOG_DATA_COLLECTION_REF).document(CHART_VIEW_BTN_CLICK_REF).update(createHashmap(CHART_VIEW_BTN_CLICK_REF + "." + clickEventId + "." + EXIT_TIME_REF,current));
    }

    public static void chartGotoDateLog(String goalId, String gotoDataDate) {
        long current = System.currentTimeMillis()/100L;
        HashMap<String,Object> hashMap = createHashmap(CLIK_TIME_REF,current);
        hashMap.put(GO_TO_DATE_REF, gotoDataDate);
        FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(INDIVIDUAL_PROGRESS_DATA_COLLECTION_REF).document("shapeSize.individualProgressDocID").collection(LOG_DATA_COLLECTION_REF).document(CHART_VIEW_BTN_CLICK_REF).update(createHashmap(CHART_VIEW_BTN_CLICK_REF + "." + getAlphaNumericString(11),hashMap));
    }

    public void graphSwitchBtnClick(String goalId,String switchTo) {
        long current = System.currentTimeMillis()/100L;
        HashMap<String,Object> hashMap = createHashmap(CLIK_TIME_REF,current);
        hashMap.put(SWICTH_TO_REF,switchTo);
        FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(INDIVIDUAL_PROGRESS_DATA_COLLECTION_REF).document("shapeSize.individualProgressDocID").collection(LOG_DATA_COLLECTION_REF).document(CHART_VIEW_BTN_CLICK_REF).update(createHashmap(GRAPH_SWICTH_BTN_CLICKED_REF +"."+ getAlphaNumericString(11),hashMap));
    }

    ///Mark: IndividualWall VC
    public static void requestStudiedTimeDetailsForIndividualWallProgress(String goalId, ResultHandler<Object> handler) {
        SingletonStrings ss = new SingletonStrings();
        //completion:@escaping(_ status:Bool, _ response:DocumentSnapshot?)->()
        FirebaseFirestore.getInstance().collection(ss.GOALS_COLLECTION_REF).document(goalId).collection(ss.INDIVIDUAL_PROGRESS_DATA_COLLECTION_REF).document("shapeSize.individualProgressDocID").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getException()==null){
                        HashMap<String,Object> resultHashmap = new HashMap<>();
                        resultHashmap.put("_status",true);
                        resultHashmap.put("_response",(DocumentSnapshot) task.getResult());
                        handler.onSuccess(resultHashmap);
                    }
                    else{
                        HashMap<String,Object> resultHashmap = new HashMap<>();
                        resultHashmap.put("_status",true);
                        resultHashmap.put("_response",null);
                        handler.onSuccess(resultHashmap);
                    }
                }
                else{

                }
            }
        });
    }

    public void requestIndividualGoal(String goalId, ResultHandler<Object> handler) {
        FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).get()
                .addOnCompleteListener(docSnap -> {
                    if (docSnap.isSuccessful()) {
                        try {
                            docSnap = docSnap;
                            Log.d("requestIndividualGoal", "requestIndividualGoal: " + docSnap);

                            //IndividualWallVC.sharedInstance.data = IndividualGoalModel.parseData(docSnap);
                            handler.onSuccess(docSnap.getResult());
                        } catch (Exception e) {
                            handler.onFailure(e);
                        }
                    } else {
                        handler.onFailure(docSnap.getException());
                    }
                });
    }

    public static void completeGoal(String goalId) {
        SingletonStrings ss = new SingletonStrings();
        WriteBatch batch = FirebaseFirestore.getInstance().batch();
        DocumentReference goalInUserCollection = FirebaseFirestore.getInstance().collection(ss.USER_COLLECTION_REF).document(DataServices.getInstance().uid).collection(ss.PERSONAL_GOALS_COLLECTION_REF).document(goalId);
        DocumentReference goalInGeneralGoalCollection = FirebaseFirestore.getInstance().collection(ss.GOALS_COLLECTION_REF).document(goalId);
        DocumentReference userDocRef = FirebaseFirestore.getInstance().collection(ss.USER_COLLECTION_REF).document(DataServices.getInstance().uid);

        HashMap<String,Object> hashMap = createHashmap(ss.IS_GOAL_COMPLETED_REF, true);
        long current = System.currentTimeMillis()/100L;
        hashMap.put(ss.COMPLETED_DATE_REF,current);
        batch.update(goalInUserCollection,hashMap);

        HashMap<String,Object> hashMap1 = createHashmap(ss.IS_GOAL_COMPLETED_REF, true);
        hashMap1.put(ss.COMPLETED_DATE_REF,current);

        batch.update(goalInGeneralGoalCollection,hashMap1);
        batch.update(userDocRef, createHashmap(ss.COMPLETED_TOTAL_GOAL_NUMBER_REF, FieldValue.increment(1)));

        batch.commit();

    }

    public void completeSubGoal(String goalId,String subgoalId, boolean isChecked) {
        String checkField;
        if(isChecked) {
            checkField = SUB_GOAL_CHECK_TIME_REF;
        }
        else {
            checkField = SUB_GOAL_UN_CHECK_TIME_REF;
        }

        HashMap<String,Object> hashMap = createHashmap(SUB_GOAL_PACK_REF + "." + subgoalId + "." + IS_CHECKED_REF,isChecked);
        long current = System.currentTimeMillis()/100L;
        hashMap.put(SUB_GOAL_PACK_REF + "." +subgoalId + "." +checkField,current);
        FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).update(hashMap);

    }

    //timer APIS for individual goal

    public void startTimerIndividualGoal(String goalId, String subgoalId,String studyId, TimerDataModel startData) {
        //create start
        // crate nested breake / resume / stop fields later
        FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(STUDY_TIME_REF).document(subgoalId).update(TimerDataModel.jsonFormatter(startData));

    }
    public void resumeTimerForIndividualGoal(String goalId, String subgoalId,String studyId, HashMap<String,Object> resumeData) {
        FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(STUDY_TIME_REF).document(subgoalId).update(createHashmap(studyId + "." + RESUMES_REF+"." + getAlphaNumericString(11), resumeData));
    }
    public void breakTimerForIndividualGoal(String goalId,String subgoalId, String studyId, HashMap<String,Object> breakData) {
        HashMap<String,Object> hashMap = createHashmap(studyId + "." + BREAKS_REF + "." + getAlphaNumericString(11), breakData);
        hashMap.put(studyId + "." + TOTAL_BREAK_TIMES_REF,FieldValue.increment(1));
        FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(STUDY_TIME_REF).document(subgoalId).update(hashMap);

    }
    public void stopTimerForIndividualGoal(String goalId, String subgoalId, String studyId,HashMap<String,Object> stopData,int studiedTime) {
        //stop means users did not finish the whole time that he proposed to study
        WriteBatch batch = FirebaseFirestore.getInstance().batch();
        //update studied collection
        DocumentReference studyCollectionRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(STUDY_TIME_REF).document(subgoalId);
        //update main goal doc //sub goal field // total stuided time
        DocumentReference mainGoalDoc = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId);
        batch.update(mainGoalDoc, createHashmap(SUB_GOAL_PACK_REF + "." + subgoalId + "." + TOTAL_STUDIED_TIME_REF,FieldValue.increment(studiedTime)));
        HashMap<String,Object> hashMap = createHashmap(studyId + "." + STOP_TIME_REF, stopData);
        hashMap.put(studyId + "." + STUDIED_TIME_REF,studiedTime);
        batch.update(studyCollectionRef,hashMap);
        updateChartDataStuidedTimes(batch, goalId, subgoalId, studiedTime);

        batch.commit();

        //TabbarVC.sharedInstance?.profileVCInstance?.isProgressBtnAnimationOn = true
    }

    public void updateChartDataStuidedTimes(WriteBatch batch,String goalId,String subgoalId,int studiedTime) {
        //update profile chart data
        DocumentReference userPersonalGoalRef = FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(this.uid).collection(PERSONAL_GOALS_COLLECTION_REF).document(goalId);
        batch.update(userPersonalGoalRef, createHashmap(TOTAL_STUDIED_TIME_REF, FieldValue.increment((studiedTime))));
        //update individual wall progress data
        DocumentReference individualWallProgressDocRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(INDIVIDUAL_PROGRESS_DATA_COLLECTION_REF).document("shapeSize.individualProgressDocID");
       // batch.update(["\(Date.dailyBaseTimeInterval(now:Date())).\(subgoalId)" : FieldValue.increment((studiedTime))], individualWallProgressDocRef);
        String key = String.valueOf(new DateExtended().dailyBaseTimeInterval(new Date()));
        batch.update(individualWallProgressDocRef, createHashmap(key + "." + subgoalId,FieldValue.increment((studiedTime))));


    }
    public static void  updateChartDataStuidedTimesForTimeReport(WriteBatch batch, String goalId, String subgoalId, int studiedTime, double startTime) {
        //update profile chart data
        DocumentReference userPersonalGoalRef = FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(FirebaseAuth.getInstance().getUid()).collection(PERSONAL_GOALS_COLLECTION_REF).document(goalId);
        batch.update(userPersonalGoalRef, createHashmap(TOTAL_STUDIED_TIME_REF,FieldValue.increment((studiedTime))));
        //update individual wall progress data
        DocumentReference individualWallProgressDocRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(INDIVIDUAL_PROGRESS_DATA_COLLECTION_REF).document("shapeSize.individualProgressDocID");
        //Multiplied startTime by 100L since Date constructor takes in milliseconds L
        String key = String.valueOf(new DateExtended().dailyBaseTimeInterval(new Date((long) (startTime*100L))));
        batch.update(individualWallProgressDocRef, createHashmap(key + "." + subgoalId, FieldValue.increment(studiedTime)));

    }

    public void finishTimerForIndividual(String goalId, String subgoalId, String studyId,int studiedTime, HashMap<String,Object> finishData) {
        //finish means users finished the whole time that he proposed to study
        //first update the main goal sub pack/subgoal/ total studied Time
        DocumentReference mainGoalDoc = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId);
        //second update studyCollection/subgoal/studyid
        DocumentReference studyCollectionRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(STUDY_TIME_REF).document(subgoalId);
        WriteBatch batch = FirebaseFirestore.getInstance().batch();
        batch.update(mainGoalDoc, createHashmap(SUB_GOAL_PACK_REF + "." + subgoalId + "." + TOTAL_STUDIED_TIME_REF,FieldValue.increment((studiedTime))));
        HashMap<String,Object> hashMap = createHashmap(studyId + "." + FINISH_TIME_REF,finishData);
        hashMap.put(studyId + "." + STUDIED_TIME_REF, studiedTime);
        hashMap.put(studyId + "." + IS_FINISHED_REF,true);
        batch.update(studyCollectionRef,hashMap);

        updateChartDataStuidedTimes(batch,goalId,  subgoalId,studiedTime);

        batch.commit();

       // TabbarVC.sharedInstance?.profileVCInstance?.isProgressBtnAnimationOn = true
    }

    public static void sendEvaluationDataAfterIndividualStudy(String goalId, String selectedSubgoalId, String studyId, HashMap<String, Object> evaluationData) {
        FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(STUDY_TIME_REF).document(selectedSubgoalId).update(createHashmap(studyId + "." + SELF_EVALUATION_REF,evaluationData));
    }
    public static void sendEvaluationDataAfterGroupStudy(String goalId, String selectedSubgoalId, String studyId, HashMap<String, Object> evaluationData) {
        FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(STUDY_TIME_REF).document(STUDY_TIME_DOC_ID_REF).update(createHashmap(DataServices.getInstance().getUID()+ "." + selectedSubgoalId + "." + studyId + "." + SELF_EVALUATION_REF,evaluationData));
    }
    public static void sendEvaluationDataAfterIndividialGoalCompletion(String goalId, HashMap<String, Object> evaluationData) {
        FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).update(createHashmap(SELF_EVALUATION_REF,evaluationData));
    }

    //end of timer APIS for individual goal

    ///End of individual Wall VC

    ////Mark:  SelfReport VC
   public static void sendGradeReportData(String grade, String goalId) {
        FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(FirebaseAuth.getInstance().getUid()).collection(PERSONAL_GOALS_COLLECTION_REF).document(goalId).update(createHashmap(IS_GRADED_REF,grade));
        LogActivityModel.getActivityChain().addActivityForGoal(SELF_GRADE_REPORT_SUBMIT_REF,goalId);
    }

    public static void requestIndividualGoalForSubGoalCheck(String goalId, ResultHandler<Object> handler){
       // completion:@escaping(_ status:Bool, _ response:individualGoalStructForSelfTimeReport?, _ error:Error?)->()) {
            FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.getException() == null) {
                        DocumentSnapshot docSnap = (DocumentSnapshot) task.getResult();
                        try {
                            HashMap<String, Object> data = (HashMap<String, Object>) docSnap.getData();
                            HashMap<String,Object> hashMap = new HashMap<>();
                            hashMap.put("_status",true);
                            hashMap.put("_response",hashMap);
                            hashMap.put("error",null);
                            handler.onSuccess(hashMap);
                        }
                        catch(Exception e){
                            HashMap<String,Object> hashMap = new HashMap<>();
                            hashMap.put("_status",true);
                            hashMap.put("_response",null);
                            hashMap.put("error",e);
                            handler.onSuccess(hashMap);
                        }
                    }
                    else{
                        HashMap<String,Object> hashMap = new HashMap<>();
                        hashMap.put("_status",true);
                        hashMap.put("_response",null);
                        hashMap.put("error",task.getException().getLocalizedMessage());
                        handler.onSuccess(hashMap);
                    }
                }
            });
    }

    public static void requestGroupGoalForSubGoalCheck(String goalId, ResultHandler<Object> handler){
        //completion:@escaping(_ status:Bool, _ response: groupGoalStructureForSelfTimeReport?, _ error:Error?)->()) {
        FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getException() == null) {
                    DocumentSnapshot docSnap = (DocumentSnapshot) task.getResult();
                    try {
                        HashMap<String, Object> data = (HashMap<String, Object>) docSnap.getData();
                        HashMap<String,Object> hashMap = new HashMap<>();
                        hashMap.put("_status",true);
                        hashMap.put("_response",hashMap);
                        hashMap.put("error",null);
                        handler.onSuccess(hashMap);
                    }
                    catch(Exception e){
                        HashMap<String,Object> hashMap = new HashMap<>();
                        hashMap.put("_status",true);
                        hashMap.put("_response",null);
                        hashMap.put("error",e);
                        handler.onSuccess(hashMap);
                    }
                }
                else{
                    HashMap<String,Object> hashMap = new HashMap<>();
                    hashMap.put("_status",true);
                    hashMap.put("_response",null);
                    hashMap.put("error",task.getException().getLocalizedMessage());
                    handler.onSuccess(hashMap);
                }
            }
        });
    }

    public static void sendSelfTimeReportForIndividual(String goalId, String subgoalId, int studiedTime, long startDate, long finishDate) {
        String studyId = getAlphaNumericString(11);
        HashMap<String,Object> timerStartData = TimerDataModel.jsonFormatterForSelfTimeReportForStartTimerIndividual(studyId, startDate, finishDate,studiedTime);

        //first update the main goal sub pack/subgoal/ total studied Time
        DocumentReference mainGoalDoc = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId);
        //second update studyCollection/subgoal/studyid
        DocumentReference studyCollectionRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(STUDY_TIME_REF).document(subgoalId);
        //third studyStart for timer page
        DocumentReference studyStartRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(STUDY_TIME_REF).document(subgoalId);
        WriteBatch batch = FirebaseFirestore.getInstance().batch();
        batch.update(studyStartRef, timerStartData);
        batch.update(mainGoalDoc, createHashmap(SUB_GOAL_PACK_REF + "." + subgoalId + "." + TOTAL_STUDIED_TIME_REF,FieldValue.increment((int)(studiedTime))));

        HashMap<String,Object> hashMap1 = createHashmap( CREATED_AT, System.currentTimeMillis()/100L);
        hashMap1.put("reportedFinishTime",finishDate);
        hashMap1.put("reportedStartTime", startDate);
        hashMap1.put(FINISH_LOCATION_REF,NO_LOCATION_REF);
        HashMap<String,Object> hashMap = createHashmap(studyId + "." + FINISH_TIME_REF, hashMap1);
        hashMap.put(studyId + "." + STUDIED_TIME_REF,studiedTime);
        hashMap.put(studyId +"." +  IS_FINISHED_REF,true);
        batch.update(studyCollectionRef, hashMap);
        updateChartDataStuidedTimesForTimeReport(batch, goalId, subgoalId,  studiedTime,startDate);
        batch.commit();

        //TabbarVC.sharedInstance?.profileVCInstance?.isProgressBtnAnimationOn = true
    }
    public static void sendSelfTimeReportForGroupGoal(String goalId, String subgoalId, int studiedTime, long startDate, long finishDate) {
        String studyId = getAlphaNumericString(11);
        HashMap<String,Object> timerStartData = TimerDataModel.jsonFormatterForSelfTimeReportForStartTimerGroup(FirebaseAuth.getInstance().getUid(), subgoalId,  studyId,  startDate, finishDate, studiedTime);

        DocumentReference mainGoalDoc = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId);
        DocumentReference studyCollectionRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(STUDY_TIME_REF).document(STUDY_TIME_DOC_ID_REF);
        DocumentReference studyStartRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(STUDY_TIME_REF).document(STUDY_TIME_DOC_ID_REF);
        WriteBatch batch = FirebaseFirestore.getInstance().batch();
        batch.update(studyStartRef, timerStartData);
        batch.update(mainGoalDoc, createHashmap(SUB_GOAL_PACK_REF + "." + subgoalId + "." + TOTAL_STUDIED_TIME_REF, FieldValue.increment((int)studiedTime)));


        HashMap<String,Object> hashMap1 = createHashmap( CREATED_AT, System.currentTimeMillis()/100L);
        hashMap1.put("reportedFinishTime",finishDate);
        hashMap1.put("reportedStartTime", startDate);
        hashMap1.put(FINISH_LOCATION_REF,NO_LOCATION_REF);
        HashMap<String,Object> hashMap = createHashmap(FirebaseAuth.getInstance().getUid() + "." + subgoalId + "." + studyId + "." + FINISH_TIME_REF,hashMap1);
        hashMap.put(FirebaseAuth.getInstance().getUid() + "." + subgoalId + "." + studyId + "." + STUDIED_TIME_REF, studiedTime);
        hashMap.put(FirebaseAuth.getInstance().getUid() + "." + subgoalId + "." + studyId + "." + IS_FINISHED_REF, true);
        batch.update(studyCollectionRef, hashMap);

        DocumentReference userPersonalGoalRef = FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(FirebaseAuth.getInstance().getUid()).collection(PERSONAL_GOALS_COLLECTION_REF).document(goalId);
        batch.update(userPersonalGoalRef, createHashmap(TOTAL_STUDIED_TIME_REF,FieldValue.increment((int)(studiedTime))));


        batch.commit();

       // TabbarVC.sharedInstance?.profileVCInstance?.isProgressBtnAnimationOn = true
    }
    ////end SelfReport


    public void sendMainProgressViewTimeSpend(double timeSpend) {
        HashMap<String,Object> hashMap = createHashmap(TIME_SPEND_REF,timeSpend);
        hashMap.put(CREATED_AT,System.currentTimeMillis()/100L);
        hashMap.put(GOAL_TYPE_REF,MAIN_PROGRESS_REF);
        //			LOCATION_REF : Locator.sharedInstance?.getCurrentLocation() as Any

        FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(DataServices.uid).collection(CHARTS_TIME_SPEND_REF).document().set(hashMap);
    }

    //Activity Log Data Start
    public void saveActivityLogData() {
        Log.d("savng log", "saveActivityLogData: saving log");
        activityChain.addActivity(APP_CLOSE_REF);
        LogActivityModel activityLog = activityChain;
        if(activityLog==null){
            Log.d("error", "saveActivityLogData: log data write nil de kaldi");
            return;
        }

        FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(this.uid).collection(LOG_ACTIVITY_COLLECTION_REF).document().set(activityLog).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.getException()==null)
                    activityChain = null;
                else
                    Log.d("error on complete", "onComplete: " + task.getException().getLocalizedMessage());
            }
        });
    }
    //Activity Log Data End

    public void fixCompletedGoalNumber(int number) {
        FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(this.uid).update(createHashmap(COMPLETED_GOAL_NUMBERS_REF,number));
    }

}

