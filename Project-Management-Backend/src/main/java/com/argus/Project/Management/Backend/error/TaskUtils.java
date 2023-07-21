package com.argus.Project.Management.Backend.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class TaskUtils {

    public static ResponseEntity<String> getResponseEntity(String response, HttpStatus httpStatus){
        return new ResponseEntity<String>("{\"message\":\""+response+"\"}",httpStatus);
    }
}
