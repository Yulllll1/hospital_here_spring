package com.team5.hospital_here.board.service.Impl;

import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FirebaseStorageService {

    private final Storage storage;
    private final String bucketName = "myproject-2f867.appspot.com"; // 여기에 실제 버킷 이름을 입력하세요

    public FirebaseStorageService() {
        this.storage = StorageOptions.getDefaultInstance().getService();
    }

    public String uploadImage(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path path = Paths.get("temp", fileName);
        Files.write(path, file.getBytes());
        Bucket bucket = storage.get(bucketName);
        bucket.create(fileName, Files.readAllBytes(path));
        Files.delete(path);
        return "https://storage.googleapis.com/" + bucketName + "/" + fileName;
    }
}