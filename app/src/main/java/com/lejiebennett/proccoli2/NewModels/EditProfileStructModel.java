package com.lejiebennett.proccoli2.NewModels;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * What is completion? What is the function signature for checkIFSomethingChanged
 */
public class EditProfileStructModel {
    HashMap<String,String> POFILE_IMG_WITH_COLOR_REF;
    String FULL_NAME_REF = null;
    String OCCUPATION_REF = null;
    String HIGHEST_LEVEL_OF_EDUCATION_REF;
    Date BIRTHDAY_REF;
    ArrayList<String> changedFields = new ArrayList<>();

    public EditProfileStructModel(HashMap<String,String> profileImg, String fullName, String occupation, String highestLevelEducation, Date birthday){
        this.POFILE_IMG_WITH_COLOR_REF = profileImg;
        this.FULL_NAME_REF = fullName;
        this.OCCUPATION_REF =occupation;
        this.HIGHEST_LEVEL_OF_EDUCATION_REF = highestLevelEducation;
        this.BIRTHDAY_REF = birthday;
    }

    public void checkIFSomethingChanged(){
        if(this.changedFields.size() == 0){
            //Completition(false,null)
        }
        else{
            HashMap<String,Object> data = new HashMap<>();
            for(String field:changedFields){
                data.put(field,getData(field));
            }
            //completion(true,data);
        }


    }

    public Object getData(String field){
        if(field.equals("profileImgWithHex")) {
            return this.POFILE_IMG_WITH_COLOR_REF;
        }
		else if(field.equals("fullName")){
            return this.FULL_NAME_REF;
        }
		else if(field.equals("occupation")){
            return this.OCCUPATION_REF;
        }
		else if(field.equals("highestLevelOfEducation")){
            return this.HIGHEST_LEVEL_OF_EDUCATION_REF;

        }
		else if(field.equals("birthday")) {
            return this.BIRTHDAY_REF;
        }
		else {
            return null;
            //return "nil"
        }
    }
}
