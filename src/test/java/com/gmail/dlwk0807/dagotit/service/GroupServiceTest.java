package com.gmail.dlwk0807.dagotit.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class GroupServiceTest {
    @Test
    @DisplayName("데이트변환테스트")
    void 데이트변환테스트() throws Exception {
        //given
        String dateStr = "202401041500";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

        LocalDateTime dateTime = LocalDateTime.parse(dateStr, formatter);

        System.out.println(dateTime); // 2024-01-04T15:00
        //when

        //then
    }

    @Test
    @DisplayName("데이트변환테스트2")
    void 데이트변환테스트2() throws Exception {
        //given

        //when

        //then
    }


}