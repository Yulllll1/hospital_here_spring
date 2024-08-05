package com.team5.hospital_here.board.controller;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/upload")
public class FileUploadController {

    private final Storage storage = StorageOptions.getDefaultInstance().getService();
    private final String bucketName = "myproject-2f867.appspot.com";

    @PostMapping
    public Map<String, String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
        Blob blob = storage.create(Blob.newBuilder(bucketName, fileName).build(), file.getBytes());
        String fileUrl = "https://storage.googleapis.com/" + bucketName + "/" + fileName;
        return Collections.singletonMap("url", fileUrl);
    }
}
