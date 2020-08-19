package com.example.androidshaper.androidsurveyapp.RetrofitModel;

public class ObjectData {

    private  String options;
    private  String question;
    private  String type;
    private  boolean required;

    public ObjectData() {
    }

    public ObjectData(String options, String question, String type, boolean required) {
        this.options = options;
        this.question = question;
        this.type = type;
        this.required = required;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}
