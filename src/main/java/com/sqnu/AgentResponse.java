package com.sqnu;

public class AgentResponse {
    //必须和ai返回的json里的key一样
    private String field;
    private String evaluation;
    private String nextQuestion;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public String getNextQuestion() {
        return nextQuestion;
    }

    public void setNextQuestion(String nextQuestion) {
        this.nextQuestion = nextQuestion;
    }

}
