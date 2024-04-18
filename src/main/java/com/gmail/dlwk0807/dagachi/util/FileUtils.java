package com.gmail.dlwk0807.dagachi.util;

import com.gmail.dlwk0807.dagachi.core.exception.CustomRespBodyException;
import com.gmail.dlwk0807.dagachi.entity.Group;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Slf4j
public class FileUtils {

    private static final Tika tika = new Tika();

    private static void validateImageMimeType(MultipartFile file) {
        List<String> allowedImageMimeTypes = Arrays.asList(
                "image/jpeg", "image/pjpeg", "image/png", "image/gif", "image/bmp", "image/x-windows-bmp"
        );

        try {
            String mimeType = tika.detect(file.getInputStream());
            if (!allowedImageMimeTypes.contains(mimeType)) {
                throw new CustomRespBodyException(String.format("파일 형식이 맞지 않습니다. [%s (%s)]", file.getOriginalFilename(), mimeType));
            }
        } catch (IOException e) {
            throw new CustomRespBodyException("파일 형식 검사 중 오류가 발생했습니다.", e);
        }
    }

    public static void validateImageFiles(List<MultipartFile> files) {
        for (MultipartFile file : files) {
            validateImageMimeType(file);
        }
    }

    public static void validateImageFile(MultipartFile file) {
        validateImageMimeType(file);
    }

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
}