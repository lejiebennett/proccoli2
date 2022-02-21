package com.example.proccoli2.NewModels;

import com.example.proccoli2.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class UserDataModel {

    static UserDataModel sharedInstance = UserDataModel();
    String email;
    String userName;
    String fullName;
    String occupation;
    String highestLevelOfEducation;
    Date birthday;
    int groupGoalTotal;
    int individualGoalTotal;
    int completedGoalTotal;
    HashMap<String,String> profileImg;
    ArrayList<GoalModel> rawGoalsData;

    public UserDataModel(String email, String userName, String fullName, String occupation, String highestLevelOfEducation, Date birthday){
        this.email = email;
        this.userName = userName;
        this.fullName = fullName;
        this.occupation = occupation;
        this.highestLevelOfEducation = highestLevelOfEducation;
        this.birthday = birthday;
    }


    public void setGroupGoalTotal(int newValue){
        if((Integer) newValue!=null){
            return;
        }
        else{
            int newV = newValue;
            UpperDashViewForIndividual.sharedInstnace.groupGoalCounter.text = newV;
        }

    }

    public void setIndividualGoalTotal(int newValue){
        if((Integer)newValue!=null){
            return;
        }
        else{
            int newV = newValue;
            UpperDashViewForIndividual.sharedInstnace.individualGoalCounter.text = newV;
        }
    }

    public void setCompletedGoalTotal(int newValue){
        if((Integer)newValue!=null){
            return;
        }
        else{
            int newV = newValue;
            UpperDashViewForIndividual.sharedInstnace.completedGoalCounter.text = newV;
        }
    }

    /*
    // completedGoal number bug fix
    // remove this code later
    private func checkCompletedGoalNumber(finishedGoalCount:Int) {
        guard let completedGoalTotal = self.completedGoalTotal else {
            return
        }
        if completedGoalTotal != finishedGoalCount {
            //update completedGoal number
            DatabaseService.fixCompletedGoalNumber(number: finishedGoalCount)
            self.completedGoalTotal = finishedGoalCount
        }
    }
     */
    public checkCompletedGoalNumber(int finishedGoalCount){
        if((Integer)this.completedGoalTotal == null){
            return;
        }
        else{
            int completedGoalTotal = this.completedGoalTotal;
            if(completedGoalTotal!=finishedGoalCount){
                //update completedGoal number
                DatabaseService.fixCompletedGoalNumber(finishedGoalCount);
                this.completedGoalTotal = finishedGoalCount;
            }

        }
    }

    public HashMap<String,String> setProfileImg(String newValue){
        HashMap<String,String> hashMap = new HashMap<>();
        /*
        UpperDashViewForIndividual.sharedInstance.profileImageView.image = UIImage(named: newValue?.keys.first ?? "light0")
        UpperDashViewForIndividual.sharedInstance.profileImageView.changeImageColor(color: UIColor.colorFromHexString(hexCode: newValue?.values.first ?? "#000"))

         */
        String value = UserDashViewForIndividual.sharedInstance.profileImageView.image = getValueOrDefault(newValue.keys.first,"light0");
        String key = UserDashViewForIndividual.sharedInstance.profileImageView.changeImageColor(UIColor.colorFromHexString(getValueOrDefault(newValue.values.first,"#000"));
    }

    public static <T> T getValueOrDefault(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }

    public void setRawGoalsData(ArrayList<GoalModel> newValue) {
        if(newValue==null){
            return;
        }
        else{
            ArrayList<GoalModel> newV = newValue;
            ArrayList<GoalModel> activeGoals;
            ArrayList<GoalModel> expiredGoals;
            ArrayList<GoalModel> finishedGoals;

            checkCompletedGoalNumber(finishedGoals.count);

            /*
            let activeGoals = new.filter({$0.personalDeadline > Date().timeIntervalSince1970 && $0.isCompleted == false})
            let expiredGoals = new.filter({$0.personalDeadline <= Date().timeIntervalSince1970 && $0.isCompleted == false})
            let finishedGoals = new.filter({$0.isCompleted == true})
            TabbarVC.sharedInstance?.profileVCInstance?.activePersonal = activeGoals.sorted(by: {$0.personalDeadline < $1.personalDeadline})
            TabbarVC.sharedInstance?.profileVCInstance?.activeHard = activeGoals.sorted(by: {$0.whenIsDue < $1.whenIsDue})
            TabbarVC.sharedInstance?.profileVCInstance?.expiredPersonal = expiredGoals.sorted(by: {$0.personalDeadline < $1.personalDeadline})
            TabbarVC.sharedInstance?.profileVCInstance?.expiredHard = expiredGoals.sorted(by: {$0.whenIsDue < $1.whenIsDue})
            TabbarVC.sharedInstance?.profileVCInstance?.finishedPersonal = finishedGoals.sorted(by: {$0.personalDeadline < $1.personalDeadline})
            TabbarVC.sharedInstance?.profileVCInstance?.finishedHard = finishedGoals.sorted(by: {$0.whenIsDue < $1.whenIsDue})

            self.checkCompletedGoalNumber(finishedGoalCount: finishedGoals.count)

             */
        }
    }

    public void parseData(DocumentSnapshot snapshots){
        if(snapshots== null){
            return;
        }
        else{
            DocumentSnapshot snaps = snapshots;
            data = snaps.data();
            UserDataModel.sharedInstance.email = DatabaseService.email;
            UserDataModel.sharedInstance.userName = (String) getValueOrDefault(data.get(USER_NAME_REF),"unknownUser");
            UserDataModel.sharedInstance.fullName = (String) data.get(FULL_NAME_REF);
            HashMap<String,String> profileImgDefault = new HashMap<>();
            profileImgDefault.put("light0","#000");
            UserDataModel.sharedInstance.profileImg = (HashMap<String, String>) getValueOrDefault(data.get(POFILE_IMG_WITH_COLOR_REF),profileImgDefault);
            UserDataModel.sharedInstance.occupation = (String) data.get(OCCUPATION_REF);
            UserDataModel.sharedInstance.highestLevelOfEducation = (String) data.get(HIGHEST_LEVEL_OF_EDUCATION_REF);
            UserDataModel.sharedInstance.birthday = (Date) data.get(BIRTHDAY_REF);
            UserDataModel.sharedInstance.individualGoalTotal = (int) getValueOrDefault(data.get(INDIVIDUAL_GOAL_NUMBER),0);
            UserDataModel.sharedInstance.groupGoalTotal = (int) getValueOrDefault(data.get(GROUP_TOTAL_GOAL_NUMBER_REF),0);
            UserDataModel.sharedInstance.completedGoalTotal = (int) getValueOrDefault(data.get(COMPLETED_TOTAL_GOAL_NUMBER_REF),0);
        }
    }

    public void refreshLocalActiveData(ArrayList<GoalModel> activePersonal, ArrayList<GoalModel> activeHard, ArrayList<GoalModel> expiredPersonal,ArrayList<GoalModel> expiredHard,)
}
