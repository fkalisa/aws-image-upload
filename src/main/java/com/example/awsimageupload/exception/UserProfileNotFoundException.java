package com.example.awsimageupload.exception;

public class UserProfileNotFoundException extends RuntimeException{

    public  UserProfileNotFoundException(String mesage, Throwable e){
        super(mesage, e);
    }

    public  UserProfileNotFoundException(String mesage){
        super(mesage);
    }
}
