package com.example.awsimageupload.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

@Service
public class FileStoreService {
    private final AmazonS3 amazonS3;

    @Autowired
    public FileStoreService(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public void save(String path, String fileName, Optional<Map<String, String>> metadata, InputStream inputStream){
        ObjectMetadata objectMetadata = new ObjectMetadata();

        metadata.ifPresent(map -> {
            if (!map.isEmpty()){
                map.forEach(objectMetadata::addUserMetadata);
            }
        });

        try {
            amazonS3.putObject(path, fileName, inputStream, objectMetadata);
        }catch (AmazonServiceException e){
            throw new IllegalStateException("Unable to upload a file ", e);
        }
    }

    public byte[] download(String path, String key) {

        try {
            S3Object object = amazonS3.getObject(path, key);
            S3ObjectInputStream content = object.getObjectContent();
           return IOUtils.toByteArray(content);
        }catch (AmazonServiceException | IOException e){
            throw new IllegalStateException("Unable to upload a file ", e);
        }
    }
}
