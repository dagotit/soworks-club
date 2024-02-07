package com.gmail.dlwk0807.dagachi.entity;

public enum CmnScore {

    MAKE_GROUP(1),
    COMPLETE_GROUP(1),
    ATTENDANCE(1),
    GET_TITLE(1),

    ;

    private double score;

    CmnScore(double i) {
        score = i;
    }

    public double getScore() { // Enum 상수 값을 불러오기 위한 메소드
        return score;
    }
}
