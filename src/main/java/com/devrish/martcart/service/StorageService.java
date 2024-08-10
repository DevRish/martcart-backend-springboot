package com.devrish.martcart.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
@Slf4j
public class StorageService {

    @Autowired
    private AmazonS3 s3client;

    @Value("${AWS_S3_BUCKET_NAME}")
    private String bucketName;

    public void uploadFile(String keyName, MultipartFile file) throws IOException {
        var putObjectResult = s3client.putObject(bucketName, keyName, file.getInputStream(), null);
        log.info("Metadata of file uploaded to S3: {}", String.valueOf(putObjectResult.getMetadata()));
    }

    public S3Object getFile(String keyName) {
        return s3client.getObject(bucketName, keyName);
    }

}
