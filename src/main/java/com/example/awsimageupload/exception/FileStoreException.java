package com.example.awsimageupload.exception;

public class FileStoreException extends RuntimeException{
    public  FileStoreException(String message, Throwable e){
        super(message, e);
    }
}
