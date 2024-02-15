package com.gmail.dlwk0807.dagachi.entity;

public enum CmnScore {

    MAKE_GROUP(1),
    COMPLETE_GROUP(10),
    GET_TITLE(100),
    DAILY_ATTENDANCE(10),
    FAIL_GROUP(-1)
    ;

    private final int score;

    CmnScore(int value) {
        this.score = value;
    }

    public int getScore() { // Enum 상수 값을 불러오기 위한 메소드
        return score;
    }
}
