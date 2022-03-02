package com.questionsandanswers.questionsandanswers.services.enums;

public enum TimeMeasurementsEnum {

    YEAR(365L),
    MONTH(30L),
    WEEK(7L);

    private Long days;

    TimeMeasurementsEnum(Long days) {
        this.days = days;
    }

    @Override
    public String toString() {
        return "QuestionEnum{" +
                "days=" + days +
                '}';
    }

    public Long getDays(){
        return days;
    }
}
