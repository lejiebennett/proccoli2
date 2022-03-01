package com.example.proccoli2.NewModels;

import com.example.proccoli2.R;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class UserDataModel {

    public static UserDataModel sharedInstance = new UserDataModel();
    DataServices ss = DataServices.getInstance();
    String email;
    String userName;
    String fullName;
    String occupation;
    String highestLevelOfEducation;
    long birthday;
    int groupGoalTotal;
    int individualGoalTotal;
    int completedGoalTotal;
    HashMap<String,String> profileImg;
    ArrayList<GoalModel> rawGoalsData;

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getHighestLevelOfEducation() {
        return highestLevelOfEducation;
    }

    public long getBirthday() {
        return birthday;
    }

    public int getGroupGoalTotal() {
        return groupGoalTotal;
    }

    public int getIndividualGoalTotal() {
        return individualGoalTotal;
    }

    public int getCompletedGoalTotal() {
        return completedGoalTotal;
    }

    public ArrayList<GoalModel> getRawGoalsData() {
        return rawGoalsData;
    }



    public UserDataModel(String email, String userName, String fullName, String occupation, String highestLevelOfEducation, long birthday){
        this.email = email;
        this.userName = userName;
        this.fullName = fullName;
        this.occupation = occupation;
        this.highestLevelOfEducation = highestLevelOfEducation;
        this.birthday = birthday;
    }

    public UserDataModel() {

    }


    public void setGroupGoalTotal(int newValue){
        if((Integer) newValue!=null){
            return;
        }
        else{
            int newV = newValue;
           // UpperDashViewForIndividual.sharedInstnace.groupGoalCounter.text = newV;
        }

    }

    public void setIndividualGoalTotal(int newValue){
        if((Integer)newValue!=null){
            return;
        }
        else{
            int newV = newValue;
           // UpperDashViewForIndividual.sharedInstnace.individualGoalCounter.text = newV;
        }
    }

    public void setCompletedGoalTotal(int newValue){
        if((Integer)newValue!=null){
            return;
        }
        else{
            int newV = newValue;
         //   UpperDashViewForIndividual.sharedInstnace.completedGoalCounter.text = newV;
        }
    }




    // completedGoal number bug fix
    // remove this code later
    public void checkCompletedGoalNumber(int finishedGoalCount){
        if((Integer)this.completedGoalTotal == null){
            return;
        }
        else{
            int completedGoalTotal = this.completedGoalTotal;
            if(completedGoalTotal!=finishedGoalCount){
                //update completedGoal number
                ss.fixCompletedGoalNumber(finishedGoalCount);
                this.completedGoalTotal = finishedGoalCount;
            }

        }
    }


    public void profileImg(HashMap<String,String> hashMap){

        //UpperDashViewForIndividual.sharedInstance.profileImageView.image = UIImage(named: newValue?.keys.first ?? "light0")
        //UpperDashViewForIndividual.sharedInstance.profileImageView.changeImageColor(color: UIColor.colorFromHexString(hexCode: newValue?.values.first ?? "#000"))


        //String value = UserDashViewForIndividual.sharedInstance.profileImageView.image = getValueOrDefault(newValue.keys.first,"light0");
        //String key = UserDashViewForIndividual.sharedInstance.profileImageView.changeImageColor(UIColor.colorFromHexString(getValueOrDefault(newValue.values.first,"#000"));
    }


    public static <T> T getValueOrDefault(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }


    public void setRawGoalsData(ArrayList<GoalModel> newValue) {
        this.rawGoalsData = newValue;
        if(newValue==null){
            return;
        }
        else{
            /*
            ArrayList<GoalModel> newV = newValue;
            ArrayList<GoalModel> activeGoals = newV;
            ArrayList<GoalModel> expiredGoals = newV;
            ArrayList<GoalModel> finishedGoals = newV;



            let activeGoals = new.filter({$0.personalDeadline > Date().timeIntervalSince1970 && $0.isCompleted == false})
            let expiredGoals = new.filter({$0.personalDeadline <= Date().timeIntervalSince1970 && $0.isCompleted == false})
            let finishedGoals = new.filter({$0.isCompleted == true})

            TabbarVC.sharedInstance?.profileVCInstance?.activePersonal = activeGoals.sorted(by: {$0.personalDeadline < $1.personalDeadline})
            TabbarVC.sharedInstance?.profileVCInstance?.activeHard = activeGoals.sorted(by: {$0.whenIsDue < $1.whenIsDue})
            TabbarVC.sharedInstance?.profileVCInstance?.expiredPersonal = expiredGoals.sorted(by: {$0.personalDeadline < $1.personalDeadline})
            TabbarVC.sharedInstance?.profileVCInstance?.expiredHard = expiredGoals.sorted(by: {$0.whenIsDue < $1.whenIsDue})
            TabbarVC.sharedInstance?.profileVCInstance?.finishedPersonal = finishedGoals.sorted(by: {$0.personalDeadline < $1.personalDeadline})
            TabbarVC.sharedInstance?.profileVCInstance?.finishedHard = finishedGoals.sorted(by: {$0.whenIsDue < $1.whenIsDue})



            this.checkCompletedGoalNumber(finishedGoals.size());

             */

        }
    }



    public static void parseData(DocumentSnapshot snapshots){
        if(snapshots== null){
            return;
        }
        else{
            DocumentSnapshot snaps = snapshots;
            DocumentSnapshot data = (DocumentSnapshot) snaps.getData();
            UserDataModel.sharedInstance.email = DataServices.getInstance().email;
            UserDataModel.sharedInstance.userName = (String) getValueOrDefault(data.get(DataServices.getInstance().USER_NAME_REF),"unknownUser");
            UserDataModel.sharedInstance.fullName = (String) data.get(DataServices.getInstance().FULL_NAME_REF);
            HashMap<String,String> profileImgDefault = new HashMap<>();
            profileImgDefault.put("light0","#000");
            UserDataModel.sharedInstance.profileImg = (HashMap<String, String>) getValueOrDefault(data.get(DataServices.getInstance().POFILE_IMG_WITH_COLOR_REF),profileImgDefault);
            UserDataModel.sharedInstance.occupation = (String) data.get(DataServices.getInstance().OCCUPATION_REF);
            UserDataModel.sharedInstance.highestLevelOfEducation = (String) data.get(DataServices.getInstance().HIGHEST_LEVEL_OF_EDUCATION_REF);
            UserDataModel.sharedInstance.birthday = (long) data.get(DataServices.getInstance().BIRTHDAY_REF);
            UserDataModel.sharedInstance.individualGoalTotal = (int) getValueOrDefault(data.get(DataServices.getInstance().INDIVIDUAL_TOTAL_GOAL_NUMBER_REF),0);
            UserDataModel.sharedInstance.groupGoalTotal = (int) getValueOrDefault(data.get(DataServices.getInstance().GROUP_TOTAL_GOAL_NUMBER_REF),0);
            UserDataModel.sharedInstance.completedGoalTotal = (int) getValueOrDefault(data.get(DataServices.getInstance().COMPLETED_TOTAL_GOAL_NUMBER_REF),0);
        }
    }

    /*
    public void refreshLocalActiveData(ArrayList<GoalModel> activePersonal, ArrayList<GoalModel> activeHard, ArrayList<GoalModel> expiredPersonal,ArrayList<GoalModel> expiredHard,)
    */

}
