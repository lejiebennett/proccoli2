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
    public void classsaveReminder(String goalId, String reminderId, HashMap<String, Object> reminderData) {
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put(reminderId,reminderData);
        FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(REMINDERS_COLLECTIONS_REF).document(shapeSize.REMINDERS_DOC_ID).update(hashMap);
    }

    public static <T> T getValueOrDefault(T value, T defaultValue) {
        return value == null ? defaultValue : value;
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

    


    public void savePersonalNote(String goalId, personalNoteModel note) {
        FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(PERSONAL_NOTE_COLLEECTION_REF).document(shapeSize.PERSONAL_NOTE_DOC_ID_REF).update(personalNoteModel.jsonConverter(note));
    }




    //public void requestPersonalNotes(String goalId) {
    public void requestPersonalNotes(String goalId, ResultHandler<Object> handler){
       // FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(PERSONAL_NOTE_COLLEECTION_REF).document(shapeSize.PERSONAL_NOTE_DOC_ID_REF).getDocument { (docSnap, error) in
        /*
         FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(PERSONAL_NOTE_COLLEECTION_REF).document(shapeSize.PERSONAL_NOTE_DOC_ID_REF).getDocument { (docSnap, error) in


        guard error != nil else {
                PersonalNoteViews.sharedInstance.set(personalNotes: personalNoteModel.parseData(documentSnap: docSnap), mainGoalId: goalId)
                return;
            }
        }

         */

         FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(PERSONAL_NOTE_COLLEECTION_REF).document(shapeSize.PERSONAL_NOTE_DOC_ID_REF).get()
                        .addOnCompleteListener(docSnap -> {
                            if (docSnap.isSuccessful()) {
                                try {
                                    handler.onSuccess(docSnap);
                                } catch (Exception e) {
                                    handler.onFailure(e);
                                    PersonalNoteViews.sharedInstance.set( personalNoteModel.parseData( docSnap), mainGoalId: goalId);
                                    return;
                                }
                            } else {
                                handler.onFailure(docSnap.getException());
                                PersonalNoteViews.sharedInstance.set(personalNoteModel.parseData( docSnap), mainGoalId: goalId);
                                return;
                            }
                        });

    }





    public HashMap<String,Object> createHashmap(String key, Object value){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put(key,value);
        return hashMap;
    }
    public void revisePersonalOrHardDeadline(double newDeadline, double oldDeadline, boolean isPersonalDeadline,String goalId) {
        WriteBatch batch = FirebaseFirestore.getInstance().batch();
        DocumentReference revisionCollectionRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(REVISION_COLLECTION_REF).document();
        DocumentReference goalInUserCollection = FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(this.uid).collection(PERSONAL_GOALS_COLLECTION_REF).document(goalId);
        DocumentReference goalInGeneralGoalCollection = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId);
        if(isPersonalDeadline == true) {

            batch.update(goalInUserCollection, createHashmap(PERSONAL_DEADLINE_REF,newDeadline));
            batch.update(goalInGeneralGoalCollection, createHashmap(PERSONAL_DEADLINE_REF, newDeadline));
            HashMap<String,Object> hashMap = createHashmap(CREATED_AT,System.currentTimeMillis());
            hashMap.put("revisionType","personalDeadline");
            hashMap.put("oldPersonalDeadline",oldDeadline);
            hashMap.put("newPersonalDeadline", newDeadline);
            batch.set(revisionCollectionRef,hashMap);
        }
		else {
            //update when is due
            batch.update(goalInUserCollection, createHashmap(WHEN_IS_IT_DUE_REF,newDeadline));
            batch.update(goalInGeneralGoalCollection, createHashmap(WHEN_IS_IT_DUE_REF, newDeadline));
            HashMap<String,Object> hashMap = createHashmap(CREATED_AT,System.currentTimeMillis());
            hashMap.put("revisionType", "hardDeadline");
            hashMap.put("oldPersonalDeadline", oldDeadline,);
            hashMap.put( "newPersonalDeadline",newDeadline);
            batch.set(revisionCollectionRef,hashMap);
        }

        batch.commit();

    }


    public void addNewSubGoal(IndividualSubGoalStructModel data, String goalId) {
        //
        WriteBatch batch = FirebaseFirestore.getInstance().batch();
        DocumentReference generalGoalDocRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId);
        DocumentReference studyTimeDocRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(STUDY_TIME_REF).document(data._subGoalId);
        batch.update(createHashmap(SUB_GOAL_PACK_REF + "." + data._subGoalId,IndividualSubGoalStructModel.jsonFormatterSingleIndividualSubGoal(data)), generalGoalDocRef);
        batch.set(studyTimeDocRef,createHashmap(EXIST_REF,true));

        batch.commit();
    }
    public void deleteSubGoal(String subgoalId,String goalId) {
        HashMap<String,Object> hashMap = createHashmap(SUB_GOAL_PACK_REF + "." + subgoalId + "." + IS_DELETED_REF,true);
        hashMap.put(SUB_GOAL_PACK_REF +"." + subgoalId + "." + SUB_GOAL_DELETE_TIME,System.currentTimeMillis());
        FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).update(hashMap);
    }


    /*
    public void saveSubGoalRevisions(HashMap<String,Object> revisionData,String goalId,String subgoalId, HashMap<String,Object> newSubGoaldata) {
        WriteBatch batch = FirebaseFirestore.getInstance().batch();
        DocumentReference revisionCollectionRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(REVISION_COLLECTION_REF).document();
        DocumentReference goalInGeneralGoalCollection = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId);
        batch.set(revisionData, revisionCollectionRef);

        for(String key : newSubGoaldata.keySet()) {
            batch.update(["\(SUB_GOAL_PACK_REF+ "." + subgoalId+ "." + key)": newSubGoaldata[key] ?? ["err":"err"]], forDocument: goalInGeneralGoalCollection);
        }

        batch.commit()
    }

     */


    ///End of Edit Individual goal VC

    ///Individual Chart VC log data
    public void chartOpenBtnClickIndividualWall(String goalId,String clickEventId) {
        HashMap<String,Object> hashMap1 = createHashmap(CLIK_TIME_REF,System.currentTimeMillis());
        createHashmap(EXIST_REF,true),
                hashMap1.put(TIME_ZONE_REF, );
        HashMap<String,Object> hashMap = createHashmap(CHART_VIEW_BTN_CLICK_REF + "." + clickEventId,hashMap1);
        FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(INDIVIDUAL_PROGRESS_DATA_COLLECTION_REF).document(shapeSize.individualProgressDocID).collection(LOG_DATA_COLLECTION_REF).document(CHART_VIEW_BTN_CLICK_REF).
                update(hashMap);
    }
    public void chartClosedIndividualWall(String goalId,String clickEventId) {
        FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(INDIVIDUAL_PROGRESS_DATA_COLLECTION_REF).document(shapeSize.individualProgressDocID).collection(LOG_DATA_COLLECTION_REF).document(CHART_VIEW_BTN_CLICK_REF).update(createHashmap(CHART_VIEW_BTN_CLICK_REF + "." + clickEventId + "." + EXIT_TIME_REF, System.currentTimeMillis());
    }

    public void chartGotoDateLog(String goalId,String gotoDataDate) {

        HashMap<String,Object> hashMap1 = createHashmap(CLIK_TIME_REF,System.currentTimeMillis());
        hashMap1.put(GO_TO_DATE_REF,gotoDataDate);
        HashMap<String,Object> hashMap = createHashmap(CHART_VIEW_BTN_CLICK_REF + "." +getAlphaNumericString(11),hashMap1);

        FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(INDIVIDUAL_PROGRESS_DATA_COLLECTION_REF).document(shapeSize.individualProgressDocID).collection(LOG_DATA_COLLECTION_REF).document(CHART_VIEW_BTN_CLICK_REF).
                update(hashMap);
    }

    public void graphSwitchBtnClick(String goalId,String switchTo) {
        HashMap<String,Object> hashMap1 = createHashmap(CLIK_TIME_REF,System.currentTimeMillis());
        hashMap1.put(SWICTH_TO_REF,switchTo);
        HashMap<String,Object> hashMap = createHashmap(GRAPH_SWICTH_BTN_CLICKED_REF + "." +getAlphaNumericString(11),hashMap1);

        FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(INDIVIDUAL_PROGRESS_DATA_COLLECTION_REF).document(shapeSize.individualProgressDocID).collection(LOG_DATA_COLLECTION_REF).document(CHART_VIEW_BTN_CLICK_REF)
                .update(hashMap);


    }

    ///Mark: IndividualWall VC
   // public requestStudiedTimeDetailsForIndividualWallProgress(String goalId, completion:@escaping(_ status:Bool, _ response:DocumentSnapshot?)->()) {

    public void requestStudiedTimeDetailsForIndividualWallProgress(String goalId,ResultHandler<Object> handler) {
        FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(INDIVIDUAL_PROGRESS_DATA_COLLECTION_REF).document(shapeSize.individualProgressDocID).get()
                .addOnCompleteListener(docSnap -> {
                    if (docSnap.isSuccessful()) {
                        try {
                            //Failable Constructor
                            //               UserInfo info = new UserInfo(snap.getResult().getId(), snap.getResult().getData());
                            handler.onSuccess(info);
                        } catch (Exception e) {
                            handler.onFailure(e);
                        }
                    } else {
                        handler.onFailure(docSnap.getException());
                    }
                });
    }


   // public void requestIndividualGoal(String goalId) {
    public void requestIndividualGoal(String goalId, ResultHandler<Object> handler){
           FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).get()
                   .addOnCompleteListener(docSnap -> {
                       if (docSnap.isSuccessful()) {
                           try {
                               docSnap = docSnap;
                               //IndividualWallVC.sharedInstance.data = IndividualGoalModel.parseData(docSnap);
                               handler.onSuccess(docSnap);
                           } catch (Exception e) {
                               handler.onFailure(e);
                           }
                       } else {
                           handler.onFailure(docSnap.getException());
                       }
                   });
    }



    public void completeGoal(String goalId) {
        WriteBatch batch = FirebaseFirestore.getInstance().batch();
        DocumentReference goalInUserCollection = FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(this.uid).collection(PERSONAL_GOALS_COLLECTION_REF).document(goalId);
        DocumentReference goalInGeneralGoalCollection = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId);
        DocumentReference userDocRef = FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(this.uid);

        HashMap<String,Object> hashMap = createHashmap(IS_GOAL_COMPLETED_REF, true);
        hashMap.put( COMPLETED_DATE_REF,System.currentTimeMillis());
        batch.update(goalInUserCollection,hashMap);

        HashMap<String,Object> hashMap = createHashmap(IS_GOAL_COMPLETED_REF, true);
        hashMap.put(COMPLETED_DATE_REF,System.currentTimeMillis());

        batch.update(goalInGeneralGoalCollection,hashMap);
        batch.update(createHashmap(COMPLETED_TOTAL_GOAL_NUMBER_REF, FieldValue.increment(Int64(1))), userDocRef);

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
		hashMap.put(SUB_GOAL_PACK_REF + "." +subgoalId + "." +checkField,System.currentTimeMillis());
        FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).update(hashMap);

    }

    //timer APIS for individual goal

    public void startTimerIndividualGoal(String goalId, String subgoalId, String studyId,TimerDataModel startData) {
        //create start
        // crate nested breake / resume / stop fields later
        FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(STUDY_TIME_REF).document(subgoalId).update(timerDataModel.jsonFormatter(startData));

    }

    public void resumeTimerForIndividualGoal(String goalId,String subgoalId, String studyId,HashMap<String,Object> resumeData) {
        FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(STUDY_TIME_REF).document(subgoalId).update(createHashmap(studyId + "." + RESUMES_REF) + "." + getAlphaNumericString(11),resumeData);


    }

    public void breakTimerForIndividualGoal(String goalId, String subgoalId,String studyId,HashMap<String,Object> breakData) {

        HashMap<String,Object> hashMap = createHashmap(studyId + "." + BREAKS_REF + "." + getAlphaNumericString(11),breakData);
        hashMap.put(studyId+ "." + TOTAL_BREAK_TIMES_REF,FieldValue.increment(Int64(1)));
        FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(STUDY_TIME_REF).document(subgoalId).update(hashMap);

    }

    public void stopTimerForIndividualGoal(String goalId, String subgoalId, String studyId,HashMap<String,Object> stopData, int studiedTime) {
        //stop means users did not finish the whole time that he proposed to study
        WriteBatch batch = FirebaseFirestore.getInstance().batch();
        //update studied collection
        DocumentReference studyCollectionRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(STUDY_TIME_REF).document(subgoalId);
        //update main goal doc //sub goal field // total stuided time;
        DocumentReference mainGoalDoc = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId);
        batch.update(createHashmap(SUB_GOAL_PACK_REF + "." + subgoalId + "." + TOTAL_STUDIED_TIME_REF, FieldValue.increment(Int64(studiedTime)),  mainGoalDoc));

        HashMap<String,Object> hashMap = createHashmap(studyId + "." + STOP_TIME_REF, stopData);
        hashMap.put(studyId + "." + STUDIED_TIME_REF,studiedTime);
        batch.update(studyCollectionRef,hashMap);
        updateChartDataStuidedTimes( batch, goalId,  subgoalId,  studiedTime);

        batch.commit();

        //TabbarVC.sharedInstance.profileVCInstance.isProgressBtnAnimationOn = true;
    }

    private void updateChartDataStuidedTimes(WriteBatch batch, String goalId, String subgoalId,int studiedTime) {
        //update profile chart data
        DocumentReference userPersonalGoalRef = FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(this.uid).collection(PERSONAL_GOALS_COLLECTION_REF).document(goalId);

        batch.update(createHashmap(TOTAL_STUDIED_TIME_REF,FieldValue.increment(Int64(studiedTime))), userPersonalGoalRef);
        //update individual wall progress data
        DocumentReference individualWallProgressDocRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(INDIVIDUAL_PROGRESS_DATA_COLLECTION_REF).document(shapeSize.individualProgressDocID);
        batch.update(createHashmap(Date.dailyBaseTimeInterval(Date()) + "." + subgoalId, FieldValue.increment(Int64(studiedTime)), individualWallProgressDocRef);

    }
    private void updateChartDataStuidedTimesForTimeReport(WriteBatch batch, String goalId, String subgoalId: int studiedTime, double startTime) {
        //update profile chart data
        DocumentReference userPersonalGoalRef = FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(this.uid).collection(PERSONAL_GOALS_COLLECTION_REF).document(goalId);
        batch.update(createHashmap(TOTAL_STUDIED_TIME_REF,FieldValue.increment(Int64(studiedTime))) ,userPersonalGoalRef);
        //update individual wall progress data
        DocumentReference individualWallProgressDocRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(INDIVIDUAL_PROGRESS_DATA_COLLECTION_REF).document(shapeSize.individualProgressDocID);
        batch.update(createHashmap(Date.dailyBaseTimeInterval(Date(startTime)) + "." +subgoalId,FieldValue.increment(Int64(studiedTime)), individualWallProgressDocRef);
    }


    public void finishTimerForIndividual(String goalId, String subgoalId, String studyId, int studiedTime, HashMap<String,Object> finishData) {
        //finish means users finished the whole time that he proposed to study
        //first update the main goal sub pack/subgoal/ total studied Time
        DocumentReference mainGoalDoc = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId);
        //second update studyCollection/subgoal/studyid
        DocumentReference studyCollectionRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(STUDY_TIME_REF).document(subgoalId);
        WriteBatch batch = FirebaseFirestore.getInstance().batch();
        batch.update(createHashmap((SUB_GOAL_PACK_REF + "." + subgoalId+ "." + TOTAL_STUDIED_TIME_REF,FieldValue.increment(Int64(studiedTime)), mainGoalDoc);
        HashMap<String,Object> hashMap = createHashmap(studyId + "." + FINISH_TIME_REF,finishData);
        hashMap.put(studyId + "." + STUDIED_TIME_REF,studiedTime);
        hashMap.put(studyId + "." + IS_FINISHED_REF, true);
        batch.update(studyCollectionRef, hashMap);

        updateChartDataStuidedTimes(batch, goalId,subgoalId, studiedTime);

        batch.commit();

        TabbarVC.sharedInstance.profileVCInstance.isProgressBtnAnimationOn = true;
    }

    public void sendEvaluationDataAfterIndividualStudy(String goalId, String selectedSubgoalId, String studyId, HashMap<String,Object> evaluationData) {
        FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(STUDY_TIME_REF).document(selectedSubgoalId).update(createHashmap(studyId + "." + SELF_EVALUATION_REF,evaluationData));
    }
    public void sendEvaluationDataAfterGroupStudy(String goalId,String selectedSubgoalId, String studyId, HashMap<String,Object> evaluationData) {
        FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(STUDY_TIME_REF).document(STUDY_TIME_DOC_ID_REF).update(createHashmap(this.uid +  "." + selectedSubgoalId + "." + studyId + "." + SELF_EVALUATION_REF,evaluationData);
    }
    public void sendEvaluationDataAfterIndividialGoalCompletion(String goalId, HashMap<String,Object> evaluationData) {
        FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).update(createHashmap(SELF_EVALUATION_REF,evaluationData);
    }

    //end of timer APIS for individual goal

    ///End of individual Wall VC



    ///Mark: CreateIndividualGoal VC
    public IndividualGoalModel saveIndividualGoal(IndividualGoalModel data){
        WriteBatch batch = FirebaseFirestore.getInstance().batch();

        DocumentReference generalGoalsCollectionRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document();
        DocumentReference userPersonalGoalsRef = FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(this.uid).collection(PERSONAL_GOALS_COLLECTION_REF).document(generalGoalsCollectionRef.documentID);
        DocumentReference userDocRef = FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(this.uid);
        DocumentReference personalNoteDocRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(generalGoalsCollectionRef.documentID).collection(PERSONAL_NOTE_COLLEECTION_REF).document(shapeSize.PERSONAL_NOTE_DOC_ID_REF);
        DocumentReference studyTimeCollection = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(generalGoalsCollectionRef.documentID).collection(STUDY_TIME_REF);
        DocumentReference noSubGoalDocReference = studyTimeCollection.document(NO_SUB_GOAL_REF);
        DocumentReference goalReminderDocRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(generalGoalsCollectionRef.documentID).collection(REMINDERS_COLLECTIONS_REF).document(shapeSize.REMINDERS_DOC_ID);

        DocumentReference individualWallProgressDocRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(generalGoalsCollectionRef.documentID).collection(INDIVIDUAL_PROGRESS_DATA_COLLECTION_REF).document(shapeSize.individualProgressDocID);
        DocumentReference individualWallProgressLogDataDocRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(generalGoalsCollectionRef.documentID).collection(INDIVIDUAL_PROGRESS_DATA_COLLECTION_REF).document(shapeSize.individualProgressDocID).collection(LOG_DATA_COLLECTION_REF).document(CHART_VIEW_BTN_CLICK_REF);

        data.goalId = generalGoalsCollectionRef.getId();
        batch.set(generalGoalsCollectionRef,IndividualGoalModel.jsonFormatterForIndividualEvent(data));
        batch.update(createHashmap(INDIVIDUAL_TOTAL_GOAL_NUMBER_REF,FieldValue.increment(Int64(1))), userDocRef);
        batch.set(personalNoteDocRef,createHashmap(CREATED_AT,System.currentTimeMillis()));
        batch.set(goalReminderDocRef,createHashmap(EXIST_REF,true));
        batch.set(userPersonalGoalsRef,Goals.jsonFormatterForGoals(IndividualGoalModel.goalsModelConverterForDataWrite(data)));
        batch.set(noSubGoalDocReference,createHashmap(EXIST_REF,true));
        batch.set(individualWallProgressDocRef,createHashmap(EXIST_REF,true));
        batch.set( individualWallProgressLogDataDocRef,createHashmap(EXIST_REF,true));
        ArrayList<IndividualSubGoalStructModel> subGoals = data.subGoals;
        if(subGoals!=null) {
            for(IndividualSubGoalStructModel subGoal : subGoals){
                batch.set(studyTimeCollection.document(subGoal._subGoalId),createHashmap(EXIST_REF,true));
            }
        }

        batch.commit();
        return data;

    }

    /// End of CreateIndividualVC

    ////Mark: Profile VC funcs
    /*
    public void callUserInfo(ResultHandler<Object> handlercompletion:@escaping(_ status:Bool, _ error:Error?)->()) {
        FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(this.uid).getDocument { (docSnap, error) in
            guard let err = error else {
                DatabaseService.checkFCM()
                UserDataModel.parseData(snapshots: docSnap)
                completion(true, nil)
                return
            }
            completion(false, err)
        }
    }
    */

    public void callUserInfo(ResultHandler<Object> handler) {
        FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(this.uid).get()
                .addOnCompleteListener(docSnap->{
                    if(docSnap.isSuccessful()) {
                        try {
                            handler.onSuccess(docSnap);
                        }
                        catch(Exception e) {
                            handler.onFailure(e);
                            //this.checkFCM();
                            //UserDataModel.parseData(docSnap);
                            return;
                        }
                    }else{
                            handler.onFailure(docSnap.getException());
                            return;
                        }
                });
    }




    
    public void updateUserInfo(HashMap<String,Object> update) {
        FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(this.uid).update(update);
    }



 /*
    public requestPersonalGoals(completion:@escaping(boolean _status,ArrayList<GoalModel> _response, Exception error)->()){
        FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(this.uid).collection(PERSONAL_GOALS_COLLECTION_REF).getDocuments { (querySnaps, error) in
            guard let err = error else {
                //query success
                completion(true, GoalModel.parseGoalsData(QuerySnapshot snapshots), null);
                return;
            }
            //query failed
            completion(true, null, err);
        }
    }

  */
    public ArrayList<GoalModel> requestPersonalGoals(ResultHandler<Object> handler){
        FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(this.uid).collection(PERSONAL_GOALS_COLLECTION_REF).get()
    .addOnCompleteListener(querySnaps->{
        if(querySnaps.isSuccessful()){
            try{
                handler.onSuccess(querySnaps);
                //completion(true, GoalModel.parseGoalsData(QuerySnapshot snapshots), null);
                GoalModel.parseGoalsData(QuerySnapshot snapshots);
                return;
            }
            catch(Exception e){
                handler.onFailure(e);
            }
        } else{
          handler.onFailure(querySnaps.getException());
            }
        });
    }

    //End of profileVC funcs
    


    ////Mark: Edit profile funcs
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

     */

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
    


    public void signOutUser() {
        FirebaseAuth.getInstance().signOut();
            try {
                FirebaseAuth.getInstance().signOut();
            } catch(Exception e){
                Log.d("signOutUserError", "signOutUser: " + e);
            }
    }


    public void loginUser(String email, String pass, ResultHandler<Object> handler) {
        Log.d("loginUser", "instance initializer: " + "checking Database statics \(this.uid)\n \(DatabaseService.email)");
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    //UpdateUI
                }
                else{
                    if(FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()==true){
                        //Signed in and verified
                    }
                    else{
                        //Signed in but not verified
                        Log.w("Not Verified", "signInWithEmail:failure", task.getException());
                    }
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

    public void sendVerificationEmail(String email){
        FirebaseUser currentUser = Auth.getCurrentUser();
        currentUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    verificationEmailCheck(false,null);
                   // completion(true, error, nil)


                }
                else{
                    verificationEmailCheck(false,task.getException());
                    Log.d("sendVerificationEmail", "onComplete: " + "Please check your e-mail for a verification email.\nThe email might be in your SPAM filder.\n");
                    //completion(true, nil, "Please check your e-mail for a verification email.\nThe email might be in your SPAM folder.\n")

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

    ///End of Edit profile funcs

    //pushNotification
    public getDeviceTOkens(uid:String, completion:@escaping(_ status:Bool, _ result:String)->()) {
        let ref = FirebaseFirestore.getInstance().collection(TOKENS_TABLE_REF).document(uid)
        ref.getDocument { (documentSnap, error) in
            if error == nil {
                guard let data = documentSnap?.data() else {
                    //first time token doc creation here
                    DatabaseService.sendTokens()
                    return}
                let token = data[FCT_REF] as? String ?? "getDeviceTOkens err"
                completion(true, token)
            }
        }

    }

    /*
    public checkFCM() {
        guard let delegate = UIApplication.shared.delegate as? AppDelegate else {return}
        DatabaseService.getDeviceTOkens(uid: this.uid) { (status, result) in
            if status {
                if result != delegate.sharedTokenData[FCT_REF] && delegate.sharedTokenData[FCT_REF] != "" {
                    //refresh the fcm here
                    DatabaseService.sendTokens()
                }
            }
        }
    }

    public sendTokens() {
        guard let delegate = UIApplication.shared.delegate as? AppDelegate else {return}
        let ref = FirebaseFirestore.getInstance().collection(TOKENS_TABLE_REF).document(this.uid)
        ref.set([
                DEVICE_TOKEN_REF: delegate.sharedTokenData[DEVICE_TOKEN_REF] as Any,
        FCT_REF: delegate.sharedTokenData[FCT_REF] as Any
			   ])
    }


    public notificationCallWithListener() {
        notificationListener  =  FirebaseFirestore.getInstance().collection(NOTIFICATION_TABLE_REF).document(this.uid).addSnapshotListener({ (docSnap, error) in

        do {
            NotificationTable.sharedInstance.data = try docSnap?.data(as: notificationsStruct.self)
        }
			catch let error{
            //handle err here
            print("notif listener err database services \(error.localizedDescription)")
        }
		})
    }
    public surveyNotificationCheckForNotificationComing(eventKey:String, completion:@escaping(_ status:Bool, _ error: Error?)->()) {
        FirebaseFirestore.getInstance().collection(NOTIFICATION_TABLE_REF).document(this.uid).getDocument { (docSnap, error) in
            if error != nil {
                //handle err here
                completion(false, error)
                return
            }
			else {
                do {
                    let data = try docSnap?.data(as: notificationsStruct.self)
                    guard let waitingSurveyEventIds = data?.waitingSurveyEventIds else {
                        completion(false, "No waiting survey!" as? Error)
                        return
                    }
                    var events = [waitingSurveyEventIdsFields.parsedSurveyEventsStruct]()
                    if waitingSurveyEventIds.count == 0 {
                        completion(false, "No waiting survey!" as? Error)
                        return
                    }
                    for key in waitingSurveyEventIds.keys {
                        guard let value = waitingSurveyEventIds[key] else {
                            completion(false, "No waiting survey!" as? Error)
                            return}
                        events.append(waitingSurveyEventIdsFields.parsedSurveyEventsStruct(key: key, waitingSurveyEventIdAfterKey: value))
                    }
                    print("beklenen keyler \(events)")
                    print("aranan key \(eventKey)")
                    completion(((events.filter({$0.eventId == eventKey}).first) != nil), nil)
                    return
                }
				catch let error{
                    //handle err here
                    completion(false, error)
                    return
                }
            }
        }
    }
    //end push notif
    */

    //survey Apis
//	public pushGenericProcrastinationSurveyToYourself(surveyDispalyName:String, surveyEventId:String, goalId:String) {
//		let data:[String:Any] = [
//			 "surveyId" : shapeSize.genericProcrastinationSurveyID,
//			 "surveyDisplayName" : surveyDispalyName,
//			 "deadline" : 00.0
//		]
//		WriteBatch batch = FirebaseFirestore.getInstance().batch()
//		let notifTableRef = FirebaseFirestore.getInstance().collection(NOTIFICATION_TABLE_REF).document(uid)
//		let surveyResultRef = notifTableRef.collection(SURVEY_RESULTS_REF).document(surveyEventId)
//
//		batch.update([
//			TOTAL_WAITING_SURVEY_REF : FieldValue.increment(Int64(1)),
//			"\(WAITING_SURVEY_EVENT_IDS_AND_NAMES_REF+ "." + surveyEventId)" : data
//		], forDocument: notifTableRef)
//		batch.set([
//			"deadline" : 00.0,
//			"selfTriggeredSurvey" : System.currentTimeMillis(),
//			"relatedGoalId" : goalId
//		], forDocument: surveyResultRef)
//
//		batch.commit()
//	}
//
    public void deleteExpiredSurvey(String surveyEventId) {
        DocumentReference ref = FirebaseFirestore.getInstance().collection(NOTIFICATION_TABLE_REF).document(uid).collection(SURVEY_RESULTS_REF).document(surveyEventId);
        ref.update(createHashmap("status","expired"));
        DocumentReference ref2 = FirebaseFirestore.getInstance().collection(NOTIFICATION_TABLE_REF).document(this.uid);

        HashMap<String,Object> hashMap = createHashmap(TOTAL_WAITING_SURVEY_REF,FieldValue.increment(Int64(-1)));
        hashMap.put(WAITING_SURVEY_EVENT_IDS_AND_NAMES_REF + "." + surveyEventId ,FieldValue.delete());
        ref2.update(hashMap);
    }

    /*
    public requestSurveyQuestions(surveyId:String, completion:@escaping(_ status:Bool, _ error:Error?, _ response:SurveyDataModel?)->()) {
        let ref = FirebaseFirestore.getInstance().collection(SURVEY_POOL_REF).document(surveyId)
        ref.getDocument { (documentSnap, error) in
            if let err = error {
                completion(true, err, nil)
            }
			else {
                guard let snap = documentSnap else {
                    completion(true, "err" as? Error, nil)
                    return}
                let response = SurveyDataModel.parseData(snap:snap)
                completion(true , nil , response)
            }
        }

    }
    */

    public void sendSurveyResults(String surveyEventId,HashMap<String,Object> results) {
        WriteBatch batch = FirebaseFirestore.getInstance().batch();
        DocumentReference ref = FirebaseFirestore.getInstance().collection(NOTIFICATION_TABLE_REF).document(uid).collection(SURVEY_RESULTS_REF).document(surveyEventId);
        DocumentReference ref2 = FirebaseFirestore.getInstance().collection(NOTIFICATION_TABLE_REF).document(this.uid);

        batch.update(ref,results);
        HashMap<String,Object> hashMap = createHashmap(TOTAL_WAITING_SURVEY_REF,FieldValue.increment(Int64(-1)));
        hashMap.put(WAITING_SURVEY_EVENT_IDS_AND_NAMES_REF + "." + surveyEventId,FieldValue.delete());
        batch.update(ref2,hashMap);
        batch.commit();

    }


    //end survey

    ////Mark:  SelfReport VC
    public void sendGradeReportData(String grade, String goalId) {
        FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(this.uid).collection(PERSONAL_GOALS_COLLECTION_REF).document(goalId)
                .update(createHashmap(IS_GRADED_REF,grade));

        activityChain.addActivityForGoal(SELF_GRADE_REPORT_SUBMIT_REF, goalId);
    }

    /*
    public requestIndividualGoalForSubGoalCheck(goalId:String, completion:@escaping(_ status:Bool, _ response:individualGoalStructForSelfTimeReport?, _ error:Error?)->()) {
        FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).getDocument { (docSnap, error) in
            guard let err = error else {
                do {
                    let data = try docSnap?.data(as: individualGoalStructForSelfTimeReport.self)
                    completion(true, data, nil)
                }catch let err{
                    completion(true,nil, err)
                }


                return
            }
            completion(true,nil, err)
        }
    }

    public requestGroupGoalForSubGoalCheck(goalId:String, completion:@escaping(_ status:Bool, _ response: groupGoalStructureForSelfTimeReport?, _ error:Error?)->()) {
        FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).getDocument { (docSnap, error) in
            guard let err = error else {
                do {
                    let data = try docSnap?.data(as: groupGoalStructureForSelfTimeReport.self)
                    completion(true, data, nil)
                }catch let err{
                    completion(true,nil, err)
                }
                return
            }
            completion(true,nil, err)
        }
    }
    */

    public void sendSelfTimeReportForIndividual(String goalId, String subgoalId, int studiedTime,double startDate,double finishDate) {
        String studyId = getAlphaNumericString(11);
         HashMap<String,Object> timerStartData = TimerDataModel.jsonFormatterForSelfTimeReportForStartTimerIndividual(studyId, startDate, finishDate,  studiedTime);

        //first update the main goal sub pack/subgoal/ total studied Time
        DocumentReference mainGoalDoc = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId);
        //second update studyCollection/subgoal/studyid
        DocumentReference studyCollectionRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(STUDY_TIME_REF).document(subgoalId)
        //third studyStart for timer page
        DocumentReference studyStartRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(STUDY_TIME_REF).document(subgoalId)
        WriteBatch batch = FirebaseFirestore.getInstance().batch();
        batch.update(studyStartRef,timerStartData);
        batch.update(mainGoalDoc,createHashmap(SUB_GOAL_PACK_REF + "." + subgoalId + "." + TOTAL_STUDIED_TIME_REF,FieldValue.increment(Int64(studiedTime))));
        HashMap<String,Object> hashMap1 = createHashmap(CREATED_AT,System.currentTimeMillis());
        hashMap1.put(reportedFinishTime, finishDate);
        hashMap1.put(reportedStartTime,startDate);
        hashMap1.put( FINISH_LOCATION_REF,NO_LOCATION_REF);
        HashMap<String,Object> hashMap = createHashmap(studyId + "." + FINISH_TIME_REF,hashMap1);
        hashMap.put(studyId + "." + STUDIED_TIME_REF, studiedTime);
        hashMap.put(studyId + "." + IS_FINISHED_REF, true);
        batch.update(studyCollectionRef,hashMap);
        
        batch.update(studyCollectionRef,hashMap);
        updateChartDataStuidedTimesForTimeReport( batch,  goalId, subgoalId, studiedTime,  startDate);
        batch.commit();

        TabbarVC.sharedInstance.profileVCInstance.isProgressBtnAnimationOn = true;
    }
    public void sendSelfTimeReportForGroupGoal(String goalId, String subgoalId, int studiedTime, double startDate,double finishDate) {
        String studyId = getAlphaNumericString(11);
        HashMap<String,Object> timerStartData = TimerDataModel.jsonFormatterForSelfTimeReportForStartTimerGroup(this.uid,  subgoalId,studyId, startDate,  finishDate, studiedTime);

        DocumentReference mainGoalDoc = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId);
        DocumentReference studyCollectionRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(STUDY_TIME_REF).document(STUDY_TIME_DOC_ID_REF);
        DocumentReference studyStartRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(STUDY_TIME_REF).document(STUDY_TIME_DOC_ID_REF);
        WriteBatch batch = FirebaseFirestore.getInstance().batch();
        batch.update(studyStartRef,timerStartData);
        batch.update(createHashmap(SUB_GOAL_PACK_REF + "." + subgoalId + "." + TOTAL_STUDIED_TIME_REF,FieldValue.increment(Int64(studiedTime))),  mainGoalDoc);

        HashMap<String,Object> hashMap1 = createHashmap(CREATED_AT,System.currentTimeMillis());
        hashMap1.put(reportedFinishTime,finishDate);
        hashMap1.put(reportedStartTime,startDate);
        hashMap1.put(FINISH_LOCATION_REF ,NO_LOCATION_REF]);
        HashMap<String,Object> hashMap = createHashmap(this.uid + "." + subgoalId + "." + studyIdFINISH_TIME_REF,hashMap1);
        hashMap.put(this.uid + "." + subgoalId + "." + studyId + "." + STUDIED_TIME_REF,studiedTime);
        hashMap.put(this.uid + "." + subgoalId + "." + studyId + "." + IS_FINISHED_REF,true);

        batch.update(studyCollectionRef,hashMap);

        DoucmentReference userPersonalGoalRef = FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(this.uid).collection(PERSONAL_GOALS_COLLECTION_REF).document(goalId);
        batch.update(userPersonalGoalRef,createHashmap(TOTAL_STUDIED_TIME_REF,FieldValue.increment(Int64(studiedTime))));


        batch.commit();

        TabbarVC.sharedInstance.profileVCInstance.isProgressBtnAnimationOn = true;
    }
    ////end SelfReport

    //GROUP WALL PAGE
    public void finishTimerForGroupGoal(String goalId,String subgoalId,String studyId, int studiedTime,HashMap<String,Object> finishData) {
        //finish means users finished the whole time that he proposed to study
        //first update the main goal sub pack/subgoal/ total studied Time
        WriteBatch batch = FirebaseFirestore.getInstance().batch();
        DocumentReference mainGoalDoc = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId);
        //second update studyCollection/subgoal/studyid
        batch.update( mainGoalDoc,createHashmap(SUB_GOAL_PACK_REF + "." + subgoalId + "." + TOTAL_STUDIED_TIME_REF, FieldValue.increment(Int64(studiedTime))));

        DocumentReference studyCollectionRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(STUDY_TIME_REF).document(STUDY_TIME_DOC_ID_REF);

        HashMap<String,Object> hashMap = createHashmap(this.uid + "." + subgoalId + "." + studyId + "." + FINISH_TIME_REF,finishData);
        hashMap.put(this.uid+ "." + subgoalId+"."+studyId +"." + STUDIED_TIME_REF,studiedTime);
        hashMap.put(this.uid + "." + subgoalId "." + studyId + "." + IS_FINISHED_REF,true);
        batch.update(studyCollectionRef,hashMap);

        DocumentReference personalCollectionGoalRef = FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(this.uid).collection(PERSONAL_GOALS_COLLECTION_REF).document(goalId);
        batch.update(personalCollectionGoalRef,createHashmap(TOTAL_STUDIED_TIME_REF ,FieldValue.increment(Int64(studiedTime)));

        batch.commit();

        TabbarVC.sharedInstance?.profileVCInstance.isProgressBtnAnimationOn = true;
    }
    public void resumeTimerForGroupGoal(String goalId, String subgoalId,String studyId,HashMap<String,Object> resumeData) {
        FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(STUDY_TIME_REF).document(STUDY_TIME_DOC_ID_REF).update(createHashmap(this.uid + "." + subgoalId + "." + studyId + "." + RESUMES_REF + "." + getAlphaNumericString(11), resumeData));


    }
    public void breakTimerForGroupGoal(String goalId, String subgoalId, String studyId, HashMap<String,Object> breakData) {
        HashMap<String,Object> hashMap = createHashmap(this.uid + "." + subgoalId + "." + studyId + "." + BREAKS_REF + "." + getAlphaNumericString(11) ,breakData);
        hashMap.put(this.uid + "." + subgoalId + "." + studyId + "." + TOTAL_BREAK_TIMES_REF,FieldValue.increment(Int64(1)));
        FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(STUDY_TIME_REF).document(STUDY_TIME_DOC_ID_REF).
                update(hashMap);

    }

    public void startTimerForGroupGoal(String goalId, String subgoalId, String studyId, Date proposedStudyTime) {
        TimerDataModel startData = TimerDataModel(studyId,  System.currentTimeMillis(),  0,  false, proposedStudyTime);
        FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(STUDY_TIME_REF).document(STUDY_TIME_DOC_ID_REF).update(timerDataModel.jsonFormatterForGroupGoal(this.uid, subgoalId, startData));
    }
    public void sendTotalStudiedTimeForGroupGoalStopTimer(String goalId,String subgoalId,double totalStudiedTime,HashMap<String,Object> stopData,String studyId) {
        WriteBatch batch = FirebaseFirestore.getInstance().batch();
        DocumentReference goalRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId);
        DocumentReference personalCollectionGoalRef = FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(this.uid).collection(PERSONAL_GOALS_COLLECTION_REF).document(goalId);
        DocumentReference studyCollectionRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(STUDY_TIME_REF).document(STUDY_TIME_DOC_ID_REF);
        batch.update(goalRef,createHashmap(SUB_GOAL_PACK_REF +"." + subgoalId + "." + TOTAL_STUDIED_TIME_REF, FieldValue.increment(Int64(totalStudiedTime))));
        batch.update(personalCollectionGoalRef,createHashmap(TOTAL_STUDIED_TIME_REF,FieldValue.increment(Int64(totalStudiedTime))));

        HashMap<String,Object> hashMap = createHashmap(this.uid + "." + subgoalId + "." + studyId + "." + STOP_TIME_REF, stopData);
        hashMap.put(this.uid + "." + subgoalId + "." + studyId + "." + STUDIED_TIME_REF,totalStudiedTime);
        batch.update(studyCollectionRef,hashMap);

        batch.commit();

        TabbarVC.sharedInstance.profileVCInstance.isProgressBtnAnimationOn = true;
    }

    /*
    public createGroupGoal(bigGoal: String, goalType:String, isGoalCompleted: Bool, taskType: String, goalCreaterUid: String, groupMembers: [String:groupMembersPack], relatedCourse: String, whenIsItDue: Double, createdAt: Double, completion:@escaping(_ success:Bool, _ response:groupGoalModel?, _ error:Error?)->()) {
        do {
            WriteBatch batch = FirebaseFirestore.getInstance().batch()
            let docForGoal = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document()
            let groupGoal = groupGoalModel(goalId: docForGoal.documentID, bigGoal: bigGoal, goalType: goalType, isGoalCompleted: isGoalCompleted, taskType: taskType, goalCreaterUid: goalCreaterUid, groupMembers: groupMembers, relatedCourse: relatedCourse, whenIsItDue: whenIsItDue, createdAt: createdAt, subGoalPack: nil)
            //let chatDocRef = docForGoal.collection(GROUP_CHAT_COLLECTION_REF).document(shapeSize.groupChatDocID)
            let studyTimerREf = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(docForGoal.documentID).collection(STUDY_TIME_REF).document(STUDY_TIME_DOC_ID_REF)
            let chatOnlineOfflineRef = docForGoal.collection(ONLINE_CHAT_STATUS_COLLECTION_REF).document(shapeSize.USER_ONLINE_OFFLINE_STATUS_DOC_ID_REF)
            let reminderDocRef = docForGoal.collection(REMINDERS_COLLECTIONS_REF).document(shapeSize.REMINDERS_DOC_ID)

            let userDocRef = FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(this.uid)
            let personalCollectionRef = userDocRef.collection(PERSONAL_GOALS_COLLECTION_REF).document(docForGoal.documentID)
            let dataForPersonalCollection = groupGoalForPersonalCollection(goalId: docForGoal.documentID, bigGoal: groupGoal.bigGoal, goalType: groupGoal.goalType, isGoalCompleted: groupGoal.isGoalCompleted, taskType: groupGoal.taskType, goalCreaterUid: groupGoal.goalCreaterUid, whenIsItDue: groupGoal.whenIsItDue, createdAt: groupGoal.createdAt, totalProposedStudyTime: 0.0, totalStudiedTime: 0.0, personalDeadline: groupGoal.whenIsItDue)

            try batch.set(from: groupGoal, forDocument: docForGoal)
            try batch.set(from: dataForPersonalCollection, forDocument: personalCollectionRef)
            batch.update([GROUP_TOTAL_GOAL_NUMBER_REF: FieldValue.increment(Int64(1))], forDocument: userDocRef)
            //batch.set(["exist":true], forDocument: chatDocRef)
            batch.set(["exist":true], forDocument: reminderDocRef)
            batch.set([ONLINE_STATUS_DIC_REF:[this.uid:userOnlineStatus.prepareJsonForWritingCurrentUser()]], forDocument: chatOnlineOfflineRef)
            batch.set(["exist":true], forDocument: studyTimerREf)
            batch.commit()
            completion(true, groupGoal, nil)
        }
		catch {
            completion(false, nil, error)
        }
    }
    */
    public void completeGroupGoal(String goalId, ArrayList<String> uids){
        HashMap<String,Object> hashMap = createHashmap(IS_GOAL_COMPLETED_REF,true);
        hashMap.put(COMPLETED_DATE_REF,System.currentTimeMillis());
        FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).update(hashMap);
        for(String  uid : uids) {
            WriteBatch batch = FirebaseFirestore.getInstance().batch();
            batch.update(FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(uid),createHashmap(COMPLETED_GOAL_NUMBERS_REF,FieldValue.increment(Int64(1))));
            batch.update(FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(uid).collection(PERSONAL_GOALS_COLLECTION_REF).document(goalId),createHashmap( IS_GOAL_COMPLETED_REF , true));
            batch.commit();
        }
    }
    /*
    public void requestGroupGoalDataWithListener(String goalId) {
        //secure current listeners temp bug solution
        removeGroupWallRelatedListeners(false);
        this.turnGroupChatStatusOfflineOrOnline(goalId, ONLINE_REF);
        groupGoalWallListener = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).addSnapshotListener { (docSnap, error) in
            guard let docSnap = docSnap else {return}
            do {
                GroupWallVC.sharedInstance?.data = try docSnap.data(as: groupGoalModel.self)
            }catch let error{
                //will handle date fetch error here
                GroupWallVC.sharedInstance?.alertB(title: "Data fetch Err", message: error.localizedDescription)
            }
        }
        groupChatUserOnlineStatusListener = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(ONLINE_CHAT_STATUS_COLLECTION_REF).document(shapeSize.USER_ONLINE_OFFLINE_STATUS_DOC_ID_REF).addSnapshotListener { (docSnap, error) in
            guard let docSnap = docSnap else {return}
            do {
                GroupWallVC.sharedInstance?.userOnlineStatusData = try docSnap.data(as: userOnlineStatus.self)
            }catch let error{
                //will handle date fetch error here
                GroupWallVC.sharedInstance?.alertB(title: "Data fetch Err", message: error.localizedDescription)
            }
        }
        //request chat data
        let chatCollectionRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(GROUP_CHAT_COLLECTION_REF).order(by: CREATED_AT, descending: true)
        chatCollectionRef.getDocuments { (querSnap, error) in
            guard let docs = querSnap?.documents else {return}
            do {
                GroupChatViews.sharedInstance.dataDispatcher = try docs.compactMap({ (snaps) -> groupChatMessageModel? in
                return try snaps.data(as: groupChatMessageModel.self)
				})
            }catch let error {
                GroupWallVC.sharedInstance?.alertB(title: "Data fecth err", message: error.localizedDescription)
                print(error)
            }
        }

        //add listener chat data
        groupChatMessagesListener = chatCollectionRef
                .whereField(CREATED_AT, isGreaterThan: System.currentTimeMillis())
			.addSnapshotListener({ (querySnap, error) in
        guard let querySnap = querySnap else {return}
        querySnap.documentChanges.reversed().forEach({ (change) in
                switch change.type {
        case .added:
        do {
            guard let parsedData = try change.document.data(as: groupChatMessageModel.self) else {return}
            GroupChatViews.sharedInstance.chatData.insert(parsedData, at: 0)
            GroupWallVC.sharedInstance?.insertDataChatTable()
        }catch let error {
            GroupWallVC.sharedInstance?.alertB(title: "Data fetch Err", message: error.localizedDescription)
        }
        case .modified:
        print("modified")
        case .removed:
        print("removed")
					}
				})
		})
    }
    */
    public void saveGroupSubgoal(String goalId, String subgoalName, double deadline, boolean selfAssigned, double howLong) {
        String subGoalID = getAlphaNumericString(14);

        if(selfAssigned) {
            WriteBatch batch = FirebaseFirestore.getInstance().batch();
            groupSubgoalPack.groupSubGoalFields subgoalData = new groupSubgoalPack.groupSubGoalFields(TAKEN_SUB_GOAL_REF, false, false, deadline,  subgoalName,0, (String) getValueOrDefault(UserDataModel.sharedInstance.userName,"unknown2"),  this.uid,subGoalID,  0);
            let assignedSubgoalData = groupMembersPack.assignedSubgoalsField( subgoalName, subGoalID, IN_PROGRESS_GROUP_SUBGOAL_REF);

            DocumentReference ref1 = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId);
            HashMap<String,Object> hashMap = createHashmap(GROUP_MEMBERS_REF + "." + this.uid + "." + ASSIGNED_SUBGOAL_REF + "." + subGoalID,groupMembersPack.assignedSubgoalsField.jsonConverter(assignedSubgoalData));
            hashMap.put(SUB_GOAL_PACK_REF + "." + subGoalID, groupSubgoalPack.groupSubGoalFields.jsonConverter(subgoalData));
            batch.update( ref1,hashMap);
            DocumentReference ref2 = FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(this.uid).collection(PERSONAL_GOALS_COLLECTION_REF).document(goalId)
            batch.update(ref2,createHashmap(TOTAL_PROPOSED_STUDY_TIME_REF,FieldValue.increment((double)howLong)));
            batch.commit();
        }
		else {
            groupSubgoalPack.groupSubGoalFields subgoalData  = new groupSubgoalPack.groupSubGoalFields(AVAILABLE_SUB_GOAL_REF, false,  false, deadline,subgoalName,  0,  null,  null,  subGoalID,  0);
            FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).update(createHashmap(SUB_GOAL_PACK_REF + "." + subGoalID, groupSubgoalPack.groupSubGoalFields.jsonConverter(subgoalData)));
        }
        //log actiivty
        activityChain.addActivityForSubGoal(SUBGOAL_ADDED_GROUP_WALL_REF,goalId, subGoalID);
        // log activity end

    }
    public void claimGroupSubgoal(String goalId, groupSubgoalPack.groupSubGoalFields subgoalData, double howLong) {
        WriteBatch batch = FirebaseFirestore.getInstance().batch();

        DocumentReference goalDocRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId);
        groupSubgoalPack.groupSubGoalFields assignedSubgoalData = groupMembersPack.assignedSubgoalsField(subgoalData.subgoalName,subgoalData.subgoalId, IN_PROGRESS_GROUP_SUBGOAL_REF);
        DocumentReference ref2 = FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(this.uid).collection(PERSONAL_GOALS_COLLECTION_REF).document(goalId);
        batch.update(ref2,createHashmap(TOTAL_PROPOSED_STUDY_TIME_REF,FieldValue.increment((double)howLong)));

        HashMap<String,Object> hashMap = createHashmap(SUB_GOAL_PACK_REF + "." + subgoalData.subgoalId + "." +ASSIGNED_TO_EMAIL_REF,this.email);
        hashMap.put(SUB_GOAL_PACK_REF + "." + subgoalData.subgoalId + "." + ASSIGNED_TO_UID_REF,this.uid);
        hashMap.put(SUB_GOAL_PACK_REF + "." + subgoalData.subgoalId + STATUS_REF,TAKEN_SUB_GOAL_REF);
        hashMap.put(SUB_GOAL_PACK_REF + "." + subgoalData.subgoalId + "." + ASSIGNED_TO_USER_NAME_REF,(String) getValueOrDefault(UserDataModel.sharedInstance.userName,"nil"));
        hashMap.put(GROUP_MEMBERS_REF + "." + this.uid+ "." + ASSIGNED_SUBGOAL_REF+ "." + subgoalData.subgoalId,groupMembersPack.assignedSubgoalsField.jsonConverter(assignedSubgoalData);

        batch.update(goalDocRef,hashMap);
        batch.commit();
    }
    /*
    public void searchUserForGroupGoalInvitation(String searchEmail) {
        FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).whereField(EMAIL, isGreaterThanOrEqualTo: searchEmail.lowercased()).limit(to: 7).getDocuments { (querySnap, error) in
            guard let snaps = querySnap?.documents else {
                //err handle here
                return
            }
            let rawSearchData = snaps.compactMap { (querySnap) -> searchUserModel? in
                return try? querySnap.data(as: searchUserModel.self)
            }
            rawSearchData.forEach { (s) in
                print(s.email)
            }
            guard let alreadyInvitedMembers = GroupWallVC.sharedInstance?.data?.groupMembers else {return}
            let members = alreadyInvitedMembers.filter({$0.value.status != DECLINED_REF})
            InviteUserGropWallViews.sharedInstance?.searchUserData = rawSearchData.filter({!members.keys.contains($0.uid ?? "nil")})
        }
    }
    public isUserEmailExistInTheDataBaseForEmailInvitation(searchEmail:String, completion:@escaping(_ result:Bool)->()) {
        FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).whereField(EMAIL, isEqualTo:searchEmail.lowercased()).limit(to: 1).getDocuments { (querySnap, error) in
            guard let snaps = querySnap?.documents else {
                //err handle here
                completion(false)
                return
            }
            let rawSearchData = snaps.compactMap { (querySnap) -> searchUserModel? in
                return try? querySnap.data(as: searchUserModel.self)
            }
            if rawSearchData.count > 0 {
                guard let alreadyInvitedMembers = GroupWallVC.sharedInstance?.data?.groupMembers else {
                    completion(false)
                    return}
                if alreadyInvitedMembers.filter({$0.value.email == searchEmail}).first == nil {
                    InviteUserGropWallViews.sharedInstance?.searchUserData = rawSearchData
                }
                completion(true)
            }
			else {
                completion(false)
            }
        }
    }

*/
    public void inviteUserForGroupGoal(searchUserModel userData,String goalId,double goalDeadline,String goalName,String taskType) {
        //update goal doc
        if(userData.uid == null) {
            return;
        }
        String invitedUserUid = userData.uid;
        WriteBatch batch = FirebaseFirestore.getInstance().batch();
        DocumentReference goalDocRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId);
        groupMembersPack memberData = new groupMembersPack(userData.userName, invitedUserUid, userData.email, WAITING_REF, null);
        //update user notif table
        DocumentReference notifTableRef = FirebaseFirestore.getInstance().collection(NOTIFICATION_TABLE_REF).document(invitedUserUid);
        DocumentReference invitationLogDataRef = goalDocRef.collection(IN_APP_INVITATION_LOG_REF).document();

        batch.update(createHashmap(GROUP_MEMBERS_REF+ "." + invitedUserUid,groupMembersPack.jsonConverter(memberData)),goalDocRef);

        HashMap<String,Object> hashMap1 = createHashmap(INVITED_GOAL_ID_REF,goalId);
        hashMap1.put(INVITED_GOAL_DEADLINE_REF, goalDeadline);
        hashMap1.put(INVITED_BY_EMAIL_REF, this.email);
        hashMap1.put(CREATED_AT , System.currentTimeMillis());
        hashMap1.put( INVITED_GOAL_NAME_REF ,goalName);
        hashMap1.put(GOAL_CREATER_UID_REF ,this.uid);
        hashMap1.put(TASK_TYPE_REF ,taskType);

)


        HashMap<String,Object> hashMap = createHashmap(TOTAL_WAITING_GOAL_INVITATIONS_REF,FieldValue.increment(Int64(1));
        hashMap.put(GOAL_INVITATION_PACKS_REF+ "." + goalId,hashMap1);
        batch.update(notifTableRef,hashMap);

        HashMap<String,Object> hashMap2 = createHashmap(INVITED_BY_EMAIL_REF ,this.email);
        hashMap2.put(INVITATION_TIME_REF , System.currentTimeMillis());
        hashMap2.put(INVITED_USER_UIDS_REF : invitedUserUid);
        hashMap2.put(LOG_TYPE_REF,IN_APP_INVITATION_REF);

        batch.set( invitationLogDataRef,hashMap2);
        batch.commit();

        //send push notif here
        callhttpsNotification(goalId, goalDeadline, invitedUserUid, goalName);
    }
    /*
    public void callhttpsNotification(String goalId,double deadline,String invitedUid,String goalName) {
        HashMap<String,Object> data = createHashmap("invitedUid",invitedUid);
        data.put("title", "Group Project Invitation");
        data.put("body",DatabaseService.email + " is invited to you to work together on " + goalName + ". The goal deadline is " + Date.convertDateToString(date: Date(timeIntervalSince1970: deadline))).",

                GOAL_ID_REF : goalId
				]
        DatabaseService.functions.httpsCallable("groupMemberInvitationNotification").call(["data": data]) { (result, error) in
            if error != nil {
                //handle err here
            }
        }
    }
    */

    public void updateTempraryUIDs(String tempraryUID,String goalId, double goalDeadline,String invitedByEmail,String goalName, String taskType) {
        WriteBatch batch = FirebaseFirestore.getInstance().batch();
        DocumentReference tempNotif = FirebaseFirestore.getInstance().collection(TEMPRORARY_NOTIFICATION_COLLECTION_REF).document(shapeSize.TEMP_NOTIFICATION_DOC_ID_REF);
        DocumentReference goalDocRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId);
        DocumentReference notifTableRef = FirebaseFirestore.getInstance().collection(NOTIFICATION_TABLE_REF).document(this.uid);
        groupMembersPack memberData = new groupMembersPack(UserDataModel.sharedInstance.userName, this.uid,email, WAITING_REF, null);
        HashMap<String,Object> hashMap = createHashmap(GROUP_MEMBERS_REF+ "." + this.uid, groupMembersPack.jsonConverter(memberData));
        hashMap.put(GROUP_MEMBERS_REF+ "." + tempraryUID, FieldValue.delete());
        batch.update(goalDocRef,hashMap);

        HashMap<String,Object> hashMap = createHashmap(TOTAL_WAITING_GOAL_INVITATIONS_REF,FieldValue.increment(Int64(1)));
        HashMap<String, Object> hashMap2 = createHashmap(INVITED_GOAL_ID_REF , goalId);
        hashMap2.put(INVITED_GOAL_DEADLINE_REF, goalDeadline);
        hashMap2.put(INVITED_BY_EMAIL_REF , invitedByEmail);
        hashMap2.put(CREATED_AT , System.currentTimeMillis());
        hashMap2.put(INVITED_GOAL_NAME_REF,goalName);
        hashMap2.put(GOAL_CREATER_UID_REF , "nil");
        hashMap2.put(TASK_TYPE_REF ,taskType);
        hashMap.put(GOAL_INVITATION_PACKS_REF+ "." + goalId,hashMap2);

        batch.update(notifTableRef,hashMap);
        batch.update(createHashmap(TEMP_NOTIF_INFO_REF+ "." + tempraryUID,FieldValue.delete(),tempNotif);
        batch.commit();
    }
    public void deleteEmailInvitation(String tempUId, String goalId) {
        DocumentReference tempNotif = FirebaseFirestore.getInstance().collection(TEMPRORARY_NOTIFICATION_COLLECTION_REF).document(shapeSize.TEMP_NOTIFICATION_DOC_ID_REF);
        DocumentReference goalDocRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId);
        WriteBatch batch = FirebaseFirestore.getInstance().batch()
        batch.update(tempNotif,createHashmap(TEMP_NOTIF_INFO_REF+ "." + tempUId,FieldValue.delete()));
        batch.update(goalDocRef,createHashmap(GROUP_MEMBERS_REF+ "." + tempUId, FieldValue.delete()));
        batch.commit();
    }

    public void sendSignInMail(goal:groupGoalModel?,email:String) {
        guard let goal = goal else {return}
        //get links first
        FirebaseFirestore.getInstance().collection(LINKS_COLLECTION_REF).document(LINK_DOC_ID).getDocument { (docSnap, error) in
            guard let doc = docSnap?.data() else {return}
            let googleForm = doc[GOOGLE_FORM_REF] ?? "nil"
            let appLink = doc[APP_STORE_FIREBASE_REF] ?? "nil"
            let tempraryUID = "temp" + String.randomString(length: 24)
            WriteBatch batch = FirebaseFirestore.getInstance().batch()
            let mailRef = FirebaseFirestore.getInstance().collection(MAIL_COLLECTION_REF).document()
            let tempNotif = FirebaseFirestore.getInstance().collection(TEMPRORARY_NOTIFICATION_COLLECTION_REF).document(shapeSize.TEMP_NOTIFICATION_DOC_ID_REF)
            let mailData:[String:Any] = [
            "to" : email,
                    "message": [
            "subject" : "Invitation to work on group project",
                    "html" : "Hello,<br><br>You are invited by \(DatabaseService.email) to work on \(goal.bigGoal) group project using Proccoli app.<br><br><strong>[IF YOU ARE A UAlbany Student]</strong> First, please click on this link to view the Consent form for a research study conducted by a UAlbany research team at the School of Education. The purpose of this project is to learn about the processes behind academic procrastination and ways to avoid putting off academic work. Please note that you will be <strong>compensated $50 at the end of this semester</strong> for taking part in our study. Please see the consent form for more information.<br><br><a>\(googleForm)</a><br><br>As the next step, please click on this link to download Proccoli from Apple App Store on your iPhone or iPad.<br><a>\(appLink)</a><br><br>Best of luck,<br>Proccoli Research Team"
				],
            INVITED_GOAL_ID_REF : goal.goalId,
                    INVITED_BY_UID_REF : this.uid
			]
            let goalDocRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goal.goalId)
            let invitationLogDataRef = goalDocRef.collection(IN_APP_INVITATION_LOG_REF).document()
            let memberData = groupMembersPack(userName: nil, uid: tempraryUID, email: email, status: WAITING_FOR_EMAIL_RESPONSE_REF, assignedSubgoals: nil)
            //update user notif table
            batch.update([
                    "\(GROUP_MEMBERS_REF+ "." + tempraryUID)" : groupMembersPack.jsonConverter(data: memberData)
			], forDocument: goalDocRef)
            batch.set([
                    INVITED_EMAIL_REF : email,
                    INVITED_BY_EMAIL_REF : DatabaseService.email,
                    INVITATION_TIME_REF : System.currentTimeMillis(),
                    "temp_" + "\(INVITED_USER_UIDS_REF)" : tempraryUID,
                    LOG_TYPE_REF : "email invitation"
			], forDocument: invitationLogDataRef)
            batch.set(mailData, forDocument: mailRef)
            batch.update([
                    "\(TEMP_NOTIF_INFO_REF+ "." + tempraryUID)" : [
            INVITED_EMAIL_REF : email,
                    INVITED_GOAL_ID_REF: goal.goalId,
                    INVITED_GOAL_NAME_REF: goal.bigGoal,
                    INVITED_GOAL_DEADLINE_REF: goal.whenIsItDue,
                    INVITED_GOAl_TASK_TYPE: goal.taskType,
                    INVITED_BY_EMAIL_REF : DatabaseService.email
				]
			], forDocument: tempNotif)
            batch.commit()
        }






//		let action = ActionCodeSettings.init()
//		action.handleCodeInApp = true
//		action.url = URL.init(string: "https://proccoli.page.link/bjYi")
//		FirebaseAuth.getInstance().sendSignInLink(toEmail: email, actionCodeSettings: action) { (error) in
//			completion(true, error)
//			//save log data here
//			
//		}
    }

    public void groupSubgoalPercentageUpdate(String goalId, String subgoalId, int newPercentage) {
        if(newPercentage == 100) {
            HashMap<String, Object> hashMap = createHashmap(SUB_GOAL_PACK_REF+ "." + subgoalId+ "." + IS_CHECKED_REF, true);
            hashMap.put(SUB_GOAL_PACK_REF+ "." + subgoalId+ "." + PROGRESS_PERCENTAGE_REF,newPercentage);
            hashMap.put(GROUP_MEMBERS_REF+ "." + this.uid+ "." + ASSIGNED_SUBGOAL_REF+ "." + subgoalId+ "." + SUBGOAL_STATUS_REF,COMPLETED_GROUP_SUBGOAL_REF);
            FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).update(hashMap);

        } else if( newPercentage > 100){
            Log.d("groupSubgoalPercentageUpdate", "groupSubgoalPercentageUpdate: " + "something went wrong!");
        }else {
            FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).update(createHashmap(SUB_GOAL_PACK_REF+ "." + subgoalId+ "." + PROGRESS_PERCENTAGE_REF, newPercentage));
        }

    }

    public void sendProgressViewTimeSpend(String goalId,double timeSpend, String goalType) {

        HashMap<String,Object> hashMap = createHashmap(GOAL_ID_REF,goalId);
        hashMap.put(TIME_SPEND_REF,timeSpend);
        hashMap.put(CREATED_AT,System.currentTimeMillis());
        hashMap.put(GOAL_TYPE_REF , goalType);
        //			LOCATION_REF : Locator.sharedInstance?.getCurrentLocation() as Any

        FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(this.uid).collection(CHARTS_TIME_SPEND_REF).document().set(hashMap);
    }
    public void sendMainProgressViewTimeSpend(double timeSpend) {
        HashMap<String,Object> hashMap = createHashmap(TIME_SPEND_REF,timeSpend);
        hashMap.put(CREATED_AT,System.currentTimeMillis());
        hashMap.put(GOAL_TYPE_REF , MAIN_PROGRESS_REF);

//			LOCATION_REF : Locator.sharedInstance?.getCurrentLocation() as Any
        FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(this.uid).collection(CHARTS_TIME_SPEND_REF).document().set(hashMap);
    }
    //END GROUP WALL PAGE
    //Group invitation Page
    public void groupInvitationDecline(NotificationTableDataModel.goalInvitationFields data) {
        WriteBatch batch = FirebaseFirestore.getInstance().batch();
        DocumentReference goalDocRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(data.invitedGoalId);
        DocumentReference notificationDocRef = FirebaseFirestore.getInstance().collection(NOTIFICATION_TABLE_REF).document(this.uid);
        DocumentReference invitationLogDataRef = goalDocRef.collection(IN_APP_INVITATION_LOG_REF).document();


        batch.update(createHashmap(GROUP_MEMBERS_REF+ "." + this.uid+ "." + STATUS_REF, DECLINED_REF), goalDocRef);

        HashMap<String,Object> hashMap = createHashmap(LOG_TYPE_REF, STATUS_SELECTION_REF);
        hashMap.put(SELECTION_TIME_REF ,System.currentTimeMillis());
        hashMap.put(INVITED_USER_UIDS_REF , this.uid);
        hashMap.put(STATUS_REF , DECLINED_REF);
        batch.set( invitationLogDataRef, hashMap);

        HashMap<String, Object> hashMap = createHashmap(TOTAL_WAITING_GOAL_INVITATIONS_REF,FieldValue.increment(Int64(-1)));
        hashMap.put(GOAL_INVITATION_PACKS_REF+ "." + data.invitedGoalId,FieldValue.delete());
        batch.update( notificationDocRef, hashMap);
        batch.commit();

        //might need to send notification other group member later here
    }


    public void groupInvitationAccepted(NotificationTableDataModel.goalInvitationFields data) {
        WriteBatch batch = FirebaseFirestore.getInstance().batch();
        DocumentReference goalDocRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(data.invitedGoalId);
        DocumentReference notificationDocRef = FirebaseFirestore.getInstance().collection(NOTIFICATION_TABLE_REF).document(this.uid);
        DocumentReference invitationLogDataRef = goalDocRef.collection(IN_APP_INVITATION_LOG_REF).document();

        DocumentReference userDocRef = FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(this.uid);
        DocumentReference personalCollectionRef = userDocRef.collection(PERSONAL_GOALS_COLLECTION_REF).document(data.invitedGoalId);
        DocumentReference dataForPersonalCollection = groupGoalForPersonalCollection( data.invitedGoalId,  data.invitedGoalName, GROUP_REF, false,  data.taskType, data.goalCreaterUid,  data.invitedGoalDeadline, System.currentTimeMillis(),  0.0, 0.0, data.invitedGoalDeadline);
        DocumentReference chatOnlineOfflineRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(data.invitedGoalId).collection(ONLINE_CHAT_STATUS_COLLECTION_REF).document(shapeSize.USER_ONLINE_OFFLINE_STATUS_DOC_ID_REF);

        do {
            try {
                batch.set(dataForPersonalCollection, personalCollectionRef);
                batch.update(userDocRef, createHashmap(GROUP_TOTAL_GOAL_NUMBER_REF, FieldValue.increment(Int64(1))));
                batch.update(goalDocRef, createHashmap(GROUP_MEMBERS_REF + "." + this.uid + "." + STATUS_REF, ACCEPTED_REF));

                HashMap<String, Object> hashMap = createHashmap(LOG_TYPE_REF, STATUS_SELECTION_REF);
                hashMap.put(SELECTION_TIME_REF, System.currentTimeMillis());
                hashMap.put(INVITED_USER_UIDS_REF, this.uid);
                hashMap.put(STATUS_REF, ACCEPTED_REF);
                batch.set(invitationLogDataRef, hashMap);
                HashMap<String, Object> hashMap = createHashmap(TOTAL_WAITING_GOAL_INVITATIONS_REF, FieldValue.increment(Int64(-1)));
                hashMap.put(GOAL_INVITATION_PACKS_REF + "." + data.invitedGoalId, FieldValue.delete());
                batch.update(notificationDocRef, hashMap);
                batch.update(createHashmap(ONLINE_STATUS_DIC_REF + "." + this.uid, userOnlineStatus.prepareJsonForWritingCurrentUser()), chatOnlineOfflineRef);

                batch.commit();
                //local update
                //update userdata model here
                UserDataModel.sharedInstance.groupGoalTotal = (int) getValueOrDefault(UserDataModel.sharedInstance.groupGoalTotal, 0) + 1;
                GoalModel newGoal = new GoalModel(data.invitedGoalName, data.invitedGoalDeadline, data.taskType, data.invitedGoalId, System.currentTimeMillis(), data.invitedByEmail, false, GROUP_REF, data.invitedGoalDeadline, data.goalCreaterUid, 0, 0, false);
                if (UserDataModel.sharedInstance.rawGoalsData != null) {
                    UserDataModel.sharedInstance.rawGoalsData.add(newGoal);
                } else {
                    ArrayList<GoalModel> newGoalList = new ArrayList<>();
                    newGoalList.add(newGoal);
                    UserDataModel.sharedInstance.rawGoalsData = newGoalList;
                }

                //might need to send notification other group member later here
            } catch (let error) {
                //err handle here
                DispatchQueue.main.async {
                    TabbarVC.sharedInstance.invitationVCInstance.alertView(error.localizedDescription, alertColor);
                }
            }
        }
    }


    public void groupSubgoalCheckUpdate(String goalId,String subGoalId,boolean isChecked) {
        if(isChecked){
            HashMap<String,Object> hashMap = createHashmap(GROUP_MEMBERS_REF+ "." + this.uid+ "." + ASSIGNED_SUBGOAL_REF+ "." + subGoalId+ "." + SUBGOAL_STATUS_REF,COMPLETED_GROUP_SUBGOAL_REF);
            hashMap.put(SUB_GOAL_PACK_REF+ "." + subGoalId+ "." + SUB_GOAL_CHECK_TIME_REF,System.currentTimeMillis());
            hashMap.put(SUB_GOAL_PACK_REF+ "." + subGoalId+ "." + IS_CHECKED_REF,isChecked);
            FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).update(hashMap);
        }
		else {
		    HashMap<String,Object> hashMap = createHashmap(GROUP_MEMBERS_REF+ "." + this.uid+ "." + ASSIGNED_SUBGOAL_REF+ "." + subGoalId+ "." + SUBGOAL_STATUS_REF,IN_PROGRESS_GROUP_SUBGOAL_REF);
            hashMap.put(SUB_GOAL_PACK_REF+ "." + subGoalId+ "." + SUB_GOAL_CHECK_TIME_REF,FieldValue.delete());
            hashMap.put(SUB_GOAL_PACK_REF+ "." + subGoalId+ "." + IS_CHECKED_REF,isChecked);
		    FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).update(hashMap);
        }
    }
    public void turnGroupChatStatusOfflineOrOnline(String goalId,String status) {
        DocumentReference chatOnlineOfflineRef = FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(ONLINE_CHAT_STATUS_COLLECTION_REF).document(shapeSize.USER_ONLINE_OFFLINE_STATUS_DOC_ID_REF);
        chatOnlineOfflineRef.update(createHashmap(ONLINE_STATUS_DIC_REF+ "." + this.uid+ "." + STATUS_REF,status);
    }

    public void sendMessageToGroupChat(String goalName, String goalId, String message, ArrayList<String> offlineUids) {
        GroupChatMessageModel messagePAck = new GroupChatMessageModel(null,message,  System.currentTimeMillis(),  this.uid, DatabaseService.email);
         try{
             FirebaseFirestore.getInstance().collection(GOALS_COLLECTION_REF).document(goalId).collection(GROUP_CHAT_COLLECTION_REF).document().set( messagePAck);
            if(offlineUids==null) {
                return;
            }
            else {
                ArrayList<String> offlineuids = offlineUids;
                if (offlineuids.size() > 0) {
                    this.callhttpsForChatNotification(offlineuids, goalName, message, goalId);
                }
            }
        }catch(Exception e){
            //err handle here
            GroupWallVC.sharedInstance.alertB("Message Send Error",  error.localizedDescription);
        }

    }


    public void callhttpsForChatNotification(ArrayList<String> uids, String goalName,String message, String goalId) {
        let data:[String:Any] = [
        "memberUids":uids,
                "title": "\(goalName) - \(String(DatabaseService.email.split(separator: "@").first ?? ""))",
                "body" : message,
                GOAL_ID_REF : goalId
				]
        DatabaseService.functions.httpsCallable("groupChatNotificationTrigger").call(["data": data]) { (result, error) in
            if error != nil {
                //callhttpsNotification(goalId: goalId, deadline: deadline, invitedUid: invitedUid)
            }
        }
    }


    ///end of Group invitation Page
    //Activity Log Data Start

    public void saveActivityLogData() {
        activityChain.addActivity(APP_CLOSE_REF);
        guard let activityLog = activityChain else {
            print("log data write nil de kaldi")
            return}

        do {
            try FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(this.uid).collection(LOG_ACTIVITY_COLLECTION_REF).document().set(from: activityLog, encoder: Firestore.Encoder(), completion: { (error) in
                if error == nil {
                    activityChain = nil
                }
            })

        }
		catch {
            print("err in catch")
        }
    }


    //Activity Log Data End

    //bug fix APIs

    public void fixCompletedGoalNumber(int number) {
        FirebaseFirestore.getInstance().collection(USER_COLLECTION_REF).document(this.uid).update(createHashmap(COMPLETED_GOAL_NUMBERS_REF,number);
    }

}

 interface ResultHandler<T> {
    void onSuccess(T data);
    void onFailure(Exception e);
}
