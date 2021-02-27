package com.example.awsimageupload.resource;

import com.example.awsimageupload.profile.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.example.awsimageupload.service.UserProfileService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/vi/user-profile")
public class UserProfileController {

    private final String BASE_PATH = null;

    private final UserProfileService userProfileService;

    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }
    @GetMapping
    public List<UserProfile> getUserProfileList(){
        return userProfileService.getUserProfileList();
    }


    @PostMapping(
            path = "{userProfileId}/image/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void uploadUserProfileImage(@PathVariable("userProfileId") UUID userProfileId,
                                       @RequestParam("file")MultipartFile file){

        userProfileService.uploadUserProfileImage( userProfileId, file);
    }
    @GetMapping("{userProfileId}/image/download")
    public byte[] downloadUserProfileImage(@PathVariable("userProfileId") UUID userProfileId ){
        return userProfileService.downloadUserProfileImage(userProfileId);
    }
}
