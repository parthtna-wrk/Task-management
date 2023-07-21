package com.argus.Project.Management.Backend.controllers;

import com.argus.Project.Management.Backend.DTO.UserDTO;
import com.argus.Project.Management.Backend.error.TaskUtils;
import com.argus.Project.Management.Backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin
@RestController
public class UserController {
    private final UserService userService;
    @PostMapping("/auth/register")
    public ResponseEntity<String> signup(@RequestBody Map<String, String> requestMap) {
        try{
            return userService.signup(requestMap);
        }catch(Exception ex){
            ex.printStackTrace();

        }
        return TaskUtils.getResponseEntity("Something Went Wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/auth/authenticate")
    public ResponseEntity<String> login(@RequestBody Map<String, String> requestMap) {
        try{
            return userService.login(requestMap);
        }catch(Exception exception){
            exception.printStackTrace();
        }
        return TaskUtils.getResponseEntity("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }



    @GetMapping("/userDetails")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        try{
            return userService.getAllUser();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
