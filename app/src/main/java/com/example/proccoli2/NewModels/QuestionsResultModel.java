package com.example.proccoli2.NewModels;

import java.util.ArrayList;

public class QuestionsResultModel {
    String questionId;

    boolean isChecked;
    boolean isMandatory;

    ArrayList<String> mutipleResults;
    String result;

    public QuestionsResultModel(String questionId,String result, boolean isMandatory){
        this.questionId = questionId;
        this.result = result;
        this.isChecked = false;
        this.isMandatory = isMandatory;
    }

    public QuestionsResultModel(String questionId, ArrayList<String> multipleResult, boolean isMandatory){
        this.questionId = questionId;
        this.mutipleResults = multipleResult;
        this.isChecked = false;
        this.isMandatory = isMandatory;
    }

}
