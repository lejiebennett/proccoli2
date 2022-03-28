package com.example.proccoli2.UnusedFiles;

import static com.example.proccoli2.NewModels.SingletonStrings.EDIT_PROFILE_BACK_BTN_TAPPED_REF;
import static com.example.proccoli2.NewModels.SingletonStrings.PROFILE_DATA_EDITTED_REF;

import android.util.Log;

import com.example.proccoli2.NewModels.DataServices;
import com.example.proccoli2.NewModels.LogActivityModel;
import com.example.proccoli2.NewModels.SingletonStrings;
import com.example.proccoli2.NewModels.UserDataModel;
import com.example.proccoli2.ui.profile.profileView;

import java.util.HashMap;

/**
 *
 */
public class edit_profile_VC {
    SingletonStrings ss = new SingletonStrings();
    private com.example.proccoli2.ui.profile.profileView profileView; //View
    private UserDataModel model = UserDataModel.sharedInstance;

    public edit_profile_VC(profileView profileView){
        this.profileView = profileView;
    }

    public void updateProfileInfo(HashMap<String,Object> updateData) {
        Log.d("UpdateProfileInfo", "setAvatarIntent: " + updateData);

        for(String field: updateData.keySet()){
            handleUserdataModelLocaly(field,updateData.get(field));
        }
        DataServices.getInstance().updateUserInfo(updateData);
        //EditProfileViews.sharedInstance?.preapreForRemove();
        //dismiss(animated: true, completion: nil)
        LogActivityModel.getActivityChain().addActivity(PROFILE_DATA_EDITTED_REF);
    }

    public void backBtnTapped() {
        LogActivityModel.getActivityChain().addActivity(EDIT_PROFILE_BACK_BTN_TAPPED_REF);
        //EditProfileViews.sharedInstance?.preapreForRemove()
        //dismiss(animated: true, completion: nil)
    }



    public void handleUserdataModelLocaly(String field, Object newData){

        if(field ==ss.POFILE_IMG_WITH_COLOR_REF) {
            UserDataModel.sharedInstance.profileImg((HashMap<String,String>) newData);
        }
		else if(field == ss.FULL_NAME_REF){
            UserDataModel.sharedInstance.setFullName((String) newData);
        }
		else if(field == ss.OCCUPATION_REF){
            UserDataModel.sharedInstance.setOccupation((String) newData);
        }
		else if(field == ss.HIGHEST_LEVEL_OF_EDUCATION_REF){
            UserDataModel.sharedInstance.setHighestLevelOfEducation((String) newData);

        }
		else if(field == ss.BIRTHDAY_REF){
            UserDataModel.sharedInstance.setBirthday(Integer.valueOf((int)newData).longValue());
        }

        Log.d("handleUser", "handleUserdataModelLocaly: " + UserDataModel.sharedInstance.toString());
    }
}
