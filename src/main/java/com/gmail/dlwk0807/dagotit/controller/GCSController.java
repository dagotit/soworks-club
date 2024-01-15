package com.gmail.dlwk0807.dagotit.controller;

import com.gmail.dlwk0807.dagotit.dto.cloud.UploadReqDto;
import com.gmail.dlwk0807.dagotit.service.GCSService;
import com.google.cloud.storage.BlobInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gcs")
public class GCSController {

    private final GCSService gcsService;

    @PostMapping("/upload")
    public ResponseEntity localUploadToStorage(@RequestBody UploadReqDto uploadReqDto) throws IOException {
        BlobInfo fileFromGCS = gcsService.uploadFileToGCS(uploadReqDto);
        return ResponseEntity.ok(fileFromGCS.toString());
    }
}