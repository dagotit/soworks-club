package com.gmail.dlwk0807.dagotit.util;

import com.gmail.dlwk0807.dagotit.entity.Group;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Slf4j
public class FileUtil {

    public static String getImage(String imagePath) throws Exception {
        FileInputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        log.info("imagePath : {}", imagePath);

        try {
            inputStream = new FileInputStream(imagePath);
        }
        catch (FileNotFoundException e) {
            throw new Exception("해당 파일을 찾을 수 없습니다.");
        }

        int readCount = 0;
        byte[] buffer = new byte[1024];
        byte[] fileArray = null;

        try {
            while((readCount = inputStream.read(buffer)) != -1){
                outputStream.write(buffer, 0, readCount);
            }
            fileArray = outputStream.toByteArray();
            inputStream.close();
            outputStream.close();

        }
        catch (IOException e) {
            throw new Exception("파일을 변환하는데 문제가 발생했습니다.");
        }

        return Base64.getEncoder().withoutPadding().encodeToString(fileArray);
    }

    public static String deleteFile(String imagePath) {
        Path filePath = Paths.get(imagePath);

        // 파일을 삭제합니다.
        try {
            Files.delete(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "ok";
    }

    public static void deleteGroupImage(Group group) {
//        String oldFileName = group.getGroupImgName();
//        //기본이미지일 경우 업데이트만하고 삭제하지 않는다.
//        if (!"anonymous.png".equals(oldFileName)) {
//            // 기존 파일 삭제
//            deleteFile(uploadFolder + oldFileName);
//        }
    }
}