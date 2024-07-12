package com.wilterson.controller;

import com.wilterson.model.JobParam;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService implements com.wilterson.controller.FileUploadApiDelegate {

    @Override
    public ResponseEntity<String> handleFileUpload(MultipartFile file, List<JobParam> params) {

        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File %s is empty".formatted(file.getOriginalFilename()));
        }

        try {
            // Get the file and save it somewhere
            File uploadedFile = new File("/home/francow/Shared/received/%s".formatted(file.getOriginalFilename()));
            file.transferTo(uploadedFile);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file");
        }

        params.forEach(param -> {
            System.out.printf("Param name: %s%n", param.getName());
            System.out.printf("Param value: %s%n", param.getValue());
            System.out.printf("Param type: %s%n", param.getType());
        });

        return ResponseEntity.status(HttpStatus.OK).body("Files %s uploaded successfully. Params %s".formatted(file.getOriginalFilename(), params));
    }
}