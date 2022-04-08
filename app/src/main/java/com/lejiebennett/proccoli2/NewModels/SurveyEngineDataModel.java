package com.lejiebennett.proccoli2.NewModels;

import java.util.ArrayList;

public class SurveyEngineDataModel {
    private float contentOffset;
    private ArrayList<questionDataModel> surveyQuestions;

    public SurveyEngineDataModel(float contentOffset, ArrayList<questionDataModel> surveyQuestions){
        this.contentOffset = contentOffset;
        this.surveyQuestions = surveyQuestions;
    }

    public void setContentOffset(float contentOffset) {
        this.contentOffset = contentOffset;
    }

    public void setSurveyQuestions(ArrayList<questionDataModel> surveyQuestions) {
        this.surveyQuestions = surveyQuestions;
    }

    public float getContentOffset() {
        return contentOffset;
    }

    public ArrayList<questionDataModel> getSurveyQuestions() {
        return surveyQuestions;
    }
}
class questionDataModel{
    private String questionType;
    private String question;
    private String questionId;
    private ArrayList<String> radioChoices;

    //for squre type questions
    private int squareOffset;
    private ArrayList<String> squareDescriptionLabel;

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setRadioChoices(ArrayList<String> radioChoices) {
        this.radioChoices = radioChoices;
    }

    public ArrayList<String> getRadioChoices() {
        return radioChoices;
    }

    public void setSquareOffset(int squareOffset) {
        this.squareOffset = squareOffset;
    }

    public int getSquareOffset() {
        return squareOffset;
    }

    public void setSquareDescriptionLabel(ArrayList<String> squareDescriptionLabel) {
        this.squareDescriptionLabel = squareDescriptionLabel;
    }

    public ArrayList<String> getSquareDescriptionLabel() {
        return squareDescriptionLabel;
    }

    public questionDataModel(String questionId, String questionType, String question, ArrayList<String> radioChoices){
        this.questionType = questionType;
        this.question = question;
        this.questionId = questionId;
        this.radioChoices = radioChoices;
    }

    public questionDataModel(String questionId, String questionType, String question, int squreOffSet, ArrayList<String> squareDescriptionLabel){
        this.questionType = questionType;
        this.question = question;
        this.questionId = questionId;
        this.squareOffset = squreOffSet;
        this.squareDescriptionLabel = squareDescriptionLabel;
    }
}
