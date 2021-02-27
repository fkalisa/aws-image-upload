package com.example.awsimageupload.repository;

import com.example.awsimageupload.exception.UserProfileNotFoundException;
import org.springframework.stereotype.Repository;
import com.example.awsimageupload.profile.UserProfile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FakeUserProfileRepository {

    private final static List<UserProfile> userProfileList = new ArrayList<>();

    static {
        userProfileList.add(new UserProfile(UUID.fromString("2f427f95-0833-444a-aa49-42a13f9b4dc9"), "flora", null));
        userProfileList.add(new UserProfile(UUID.fromString("bc19f0cc-ac93-4264-adeb-35d9567ef44e"), "kalisa", null));
    }


    public List<UserProfile> getUserProfileList(){
        return userProfileList;
    }

    public UserProfile getUserByUserProfileId(final UUID userProfileId){
        return  userProfileList
                .stream()
                .filter(userProfile -> userProfile.getUserProfileId().equals(userProfileId))
                .findFirst()
                .orElseThrow(() -> new UserProfileNotFoundException("User not found for " + userProfileId.toString()));
    }

}
