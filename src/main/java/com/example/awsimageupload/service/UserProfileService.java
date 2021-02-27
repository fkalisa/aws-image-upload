package com.example.awsimageupload.service;

import com.amazonaws.services.s3.model.Bucket;
import com.example.awsimageupload.backet.BacketName;
import com.example.awsimageupload.exception.FileStoreException;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.awsimageupload.profile.UserProfile;
import com.example.awsimageupload.repository.FakeUserProfileRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class UserProfileService {

    private final FakeUserProfileRepository fakeUserProfileRepository;
    private final FileStoreService fileStoreService;
    @Autowired
    public UserProfileService(FakeUserProfileRepository fakeUserProfileRepository, FileStoreService fileStoreService) {
        this.fakeUserProfileRepository = fakeUserProfileRepository;
        this.fileStoreService = fileStoreService;
    }

    public List<UserProfile> getUserProfileList() {
        return fakeUserProfileRepository.getUserProfileList();
    }

    public void uploadUserProfileImage(UUID userProfileId, MultipartFile file){
        if (file == null || file.isEmpty()){
            throw new IllegalStateException("file cannot be empty"+file.getSize());
        }
        if(!Arrays.asList(ContentType.IMAGE_JPEG.getMimeType(), ContentType.IMAGE_PNG.getMimeType(),ContentType.IMAGE_GIF.getMimeType()).contains(file.getContentType())){
            throw new IllegalStateException("file type is wrong"+file.getContentType());
        }
        UserProfile userProfile = fakeUserProfileRepository.getUserByUserProfileId(userProfileId);

        Map<String, String> metadata = new HashMap<>();

        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));

        String path = String.format("%s/%s", BacketName.PROFILE_IMAGE.getBacketName(), userProfileId);
        String fileName = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());

        userProfile.setUserProfileImageLink(fileName);
        try {
            fileStoreService.save(path, fileName, Optional.of(metadata), file.getInputStream());
        } catch (IOException e) {
             throw new FileStoreException("Error while storing the file", e);
        }
    }

    public byte[] downloadUserProfileImage(UUID userProfileId) {
        UserProfile userProfile = fakeUserProfileRepository.getUserByUserProfileId(userProfileId);

        String path = String.format("%s/%s", BacketName.PROFILE_IMAGE.getBacketName(), userProfileId);

        return userProfile.getUserProfileImageLink()
                .map(key ->  fileStoreService.download(path, key))
                .orElse(new byte[0]);
    }
}
