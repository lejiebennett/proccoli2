package com.lejiebennett.proccoli2.NewModels;

public class SurveyDataModel {
    /*
    ArrayList<questionStruct> questions;
    String surveyId;
    CGFloat contentHeight;
    Date startTime;
    String surveyDisplayName;
    surveyDescriptionStruct surveyDescription;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public SurveyDataModel(String surveyId, ArrayList<questionStruct> questions, int contentHeight, Date startTime, String surveyDisplayName, surveyDescriptionStruct surveyDescription) {
        this.questions = questions;
        this.surveyId = surveyId;
        this.contentHeight = CGSSFloat(contentHeight);
        this.startTime = startTime;
        this.surveyDisplayName = surveyDisplayName;
        this.surveyDescription = surveyDescription;
    }


    public SurveyDataModel parseData(DocumentSnapshot snap){
            SurveyDataModel parsedData;
            ArrayList<questionStruct> parsedQuestions = new ArrayList<questionStruct>();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public HashMap<String,Object> resultFormatter(ArrayList<questionResult> answers, String surveyId, Date startTime){
        HashMap<String,Object> formattedData = new HashMap<>();
        HashMap<String,Object> formatedAnswersData = new HashMap<>();
        formattedData.replace(SURVEY_ID_REF,surveyId);
        formattedData.replace(FINISH_TIME_REF,System.currentTimeMillis());
        formattedData.replace((START_TIME_REF),startTime);
        for(questionResult data: answers){
            if(data.result!=null){
                formatedAnswersData.replace(data.questionId) = data.result;
            }
            else if(data.mutipleResults!=null){
                results = data.multipleREsults.filter{$0!=""};
                formatedAnswersData.replace(data.questionId,results);
            }
        }
        formattedData.replace("answers",formatedAnswersData);
        return formattedData;
    }

    }
    class surveyDescriptionStruct {
        String description;
        int contentHeight;

        public surveyDescriptionStruct(String description, int contentHeight) {
            this.description = description;
            this.contentHeight = contentHeight;
        }
    }

    class questionStruct{
        String question;
        String questionId;
        String questionType;
        CGFloat contentHeight;
        CGFloat yPosition;
        ArrayList<String> radioChoices;
        int questionNumber;
        boolean isMandatory;
        CGFloat questionLblHeight;

        //squire questions
        int squreNumber;
        String label1;
        String label2;
        String label3;

        //multiple checkbox
        ArrayList<String> multipleCheckBoxChoices;

        public questionStruct(String question, String questionId, String questionType,int questionNumber,ArrayList<String> radioChoices,int yPosition, int contentHeight, boolean isMandatory, int questionLblHeight){
            this.question = question;
            this.questionId = questionId;
            this.questionType = questionType;
            this.radioChoices = radioChoices;
            this.questionNumber = questionNumber;
            this.contentHeight = CGFloat(contentHeight);
            this.yPosition = CGFloat(yPosition);
            this.isMandatory = isMandatory;
            this.questionLblHeight = CGFloat(questionLblHeight);
        }

        public questionStruct(String question, String questionId, String questionType, int questionNumber,int squreNumbert, String label1,String label2, String label3, int yPosition, int contentHeight,boolean isMandatory,int questionLblHeight){
            this.question = question;
            this.questionId = questionId;
            this.questionType = questionType;
            this.questionNumber = questionNumber;
            this.yPosition = CGFloat(yPosition);
            this.contentHeight = CGFloat(contentHeight);
            this.isMandatory = isMandatory;
            this.questionLblHeight = CGFloat(questionLblHeight);

            this.squreNumber = squreNumber;
            this.label1 = label1;
            this.label2 = label2;
            this.label3 = label3;
        }

        public questionStruct(String question,String questionId, String questionType,int questionNumber, ArrayList<String>multipleChoices, int yPosition, int contentHeight, boolean isMandatory,int questionLblHeight) {
            this.question = question;
            this.questionId = questionId;
            this.questionType = questionType;
            this.multipleCheckBoxChoices = multipleChoices;
            this.questionNumber = questionNumber;
            this.contentHeight = CGFloat(contentHeight);
            this.yPosition = CGFloat(yPosition);
            this.isMandatory = isMandatory;
            this.questionLblHeight =  CGFloat(questionLblHeight);
        }

        public questionStruct(String question, String questionId, String questionType, int questionNumber, int yPosition,boolean isMandatory, int contentHeight,int questionLblHeight) {
            this.question = question;
            this.questionId = questionId;
            this.questionType = questionType;
            this.questionNumber = questionNumber;
            this.yPosition = CGFloat(yPosition);
            this.isMandatory = isMandatory;
            this.questionLblHeight = CGFloat(questionLblHeight);
            //100 for uitext view
            this.contentHeight =  CGFloat(contentHeight);
        }

)

     */


    }




