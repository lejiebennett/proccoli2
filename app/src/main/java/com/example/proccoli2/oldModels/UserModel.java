package com.example.proccoli2.oldModels;

public class UserModel {

    private String email;
    private String password;
    private String fullName;
    private String occupation;
    private String highestEducation;
    private int birthdate;
    private static UserModel user;
    //Once you create a user, I want to point back to the static user model so the user never dies


    public UserModel() {
    }

    public UserModel(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }

    public void signUp(String email, String password){
        this.email = email;
        this.password = password;
    }

    public void editProfile(String fullName, String occupation, String highestEducation){
        this.fullName = fullName;
        this.occupation = occupation;
        this.highestEducation = highestEducation;

    }

    public int getBirthdate() {
        return birthdate;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getHighestEducation() {
        return highestEducation;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getPassword() {
        return password;
    }

    public void setBirthdate(int birthdate) {
        this.birthdate = birthdate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setHighestEducation(String highestEducation) {
        this.highestEducation = highestEducation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User: " + email + " password: "  + password;
    }
}
