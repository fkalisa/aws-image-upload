package com.example.awsimageupload.backet;

public enum BacketName {

    PROFILE_IMAGE("images-upload-123");

    private String backetName;

    BacketName(String backetName) {
        this.backetName = backetName;
    }

    public String getBacketName() {
        return backetName;
    }
}
