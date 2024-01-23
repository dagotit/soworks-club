package com.gmail.dlwk0807.dagachi.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
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
        String bucketName = "dagachi-image-bucket";
        String objectName = "https://storage.googleapis.com/dagachi-image-bucket/local/profileImages/fd439c25-ff55-42b6-aa30-542e5efd051fnoname3.png";
        int start = objectName.indexOf(bucketName + "/");

        String filename = objectName.substring(start + "dagachi-image-bucket/".length());

        if (filename.startsWith("default/")) {
            System.out.println("default 이미지 삭제 제외");
        }
        System.out.println("filename = " + filename);
//        log.info("filename : {}", filename);
    }


}