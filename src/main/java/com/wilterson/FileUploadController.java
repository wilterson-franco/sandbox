package com.wilterson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class FileUploadController {

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("author") String author, @RequestParam("file") MultipartFile... files) {

        List<String> originalFileNames = new ArrayList<>();

        for (MultipartFile file : files) {

            if (file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File " + file.getOriginalFilename() + " is empty");
            }

            originalFileNames.add(file.getOriginalFilename());

            try {
                // Get the file and save it somewhere
                byte[] bytes = file.getBytes();
                File uploadedFile = new File("/home/francow/Shared/received/" + file.getOriginalFilename());
                file.transferTo(uploadedFile);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file");
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body("Files " + originalFileNames + " uploaded successfully by " + author);
    }
}
