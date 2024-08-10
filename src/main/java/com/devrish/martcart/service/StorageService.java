package com.devrish.martcart.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;

@Component
@Slf4j
public class StorageService {

    @Autowired
    private S3Client s3Client;

    @Value("${AWS_S3_BUCKET_NAME}")
    private String bucketName;

    public void uploadFile(String keyName, MultipartFile file) throws IOException {
        PutObjectResponse response = s3Client.putObject(
                PutObjectRequest.builder().bucket(bucketName).key(keyName).build(),
                RequestBody.fromInputStream(file.getInputStream(), file.getSize())
        );
        log.info("Metadata of file uploaded to S3: {}", response.responseMetadata());
    }

    public Resource getFile(String keyName) {
        ResponseInputStream<GetObjectResponse> inputStream = s3Client.getObject(
                GetObjectRequest.builder().bucket(bucketName).key(keyName).build()
        );
        Resource resource = new InputStreamResource(inputStream);
        return resource;
    }

}
