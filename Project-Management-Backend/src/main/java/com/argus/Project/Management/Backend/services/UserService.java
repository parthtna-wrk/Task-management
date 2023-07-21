package com.argus.Project.Management.Backend.services;


import com.argus.Project.Management.Backend.DTO.UserDTO;
import com.argus.Project.Management.Backend.error.TaskUtils;
import com.argus.Project.Management.Backend.jwt.CustomerUserDetailsService;
import com.argus.Project.Management.Backend.jwt.JwtFilter;
import com.argus.Project.Management.Backend.jwt.JwtUtil;
import com.argus.Project.Management.Backend.model.User;
import com.argus.Project.Management.Backend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService{
    //Business logic for signup
    /*
    ->Validating request
    ->Get tuple for that email from database via Repo
    ->If it is null than save new user im database
    ->If tuple already exist than return message already exist
    //Business logic for login
    ->Create a new UserNamePasswordAuthentication token with "new" as keyword and email and password of request as parameter
    ->Authenticate that token using AuthenticationManger and value which is returned is of type "Authentication"
    ->check for .isAuthenticated() method on above Authentication object.
    ->after above step,if status is true than generate token(token is generated using details from database and not from request) and return it, if not than return message "Need Approval"
    *
    * */
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    CustomerUserDetailsService customerUserDetailsService;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    JwtFilter jwtFilter;
    @Transactional
    public ResponseEntity<String> signup(Map<String, String> requestMap) {
        try{
            if(this.validateSignupMap(requestMap)){
                User user= userRepository.findByEmailId(requestMap.get("email"));
                if(Objects.isNull(user)){
                    userRepository.save(this.getUserFromMap(requestMap));
                    return TaskUtils.getResponseEntity("Successfully Registered", HttpStatus.OK);
                }else{
                    return TaskUtils.getResponseEntity("Email exists",HttpStatus.BAD_REQUEST);
                }
            }else {
                return  TaskUtils.getResponseEntity("Invalid Data",HttpStatus.BAD_REQUEST);
            }
        }catch (Exception exception) {
            exception.printStackTrace();
        }
        return  TaskUtils.getResponseEntity("Something Went Wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }



    private boolean validateSignupMap(Map<String, String> requestMap) {
        if(requestMap.containsKey("firstName")
                && requestMap.containsKey("lastName")
                && requestMap.containsKey("userName")
                && requestMap.containsKey("email")
                && requestMap.containsKey("password")){
            return true;
        }
        return  false;
    }
    private User getUserFromMap(Map<String, String> requestMap) {
        User user= new User();
        user.setFirstName(requestMap.get("firstName"));
        user.setLastName(requestMap.get("lastName"));
        user.setUserName(requestMap.get("userName"));
        user.setEmail(requestMap.get("email"));
//        user.setPassword(requestMap.get("password"));
        user.setPassword(passwordEncoder.encode(requestMap.get("password")));
        user.setStatus("true");
        user.setRole("user");
        return user;

    }
    @Transactional
    public ResponseEntity<String> login (Map<String,String>requestMap){
        log.info("Inside login");
        try{
            Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                    (requestMap.get("email"),requestMap.get("password")));
            if(authentication.isAuthenticated()){
                if(customerUserDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")){
//                    return  new ResponseEntity<String>("{\"token\":\""+
//                            jwtUtil.generateToken(customerUserDetailsService.getUserDetail().getEmail(),
//                                    customerUserDetailsService.getUserDetail().getRole())+"\"}",HttpStatus.OK);
                    return  new ResponseEntity<String>("{\"token\":\""+
                            jwtUtil.generateToken(customerUserDetailsService.getUserDetail().getEmail(),
                                    customerUserDetailsService.getUserDetail().getRole())+"\",\"role\":\""+customerUserDetailsService.getUserDetail().getRole()+"\"}",HttpStatus.OK);

                }
                else{
                    return new ResponseEntity<String>("{\"message\":\""+"Wait for admin approval"+"\"}",HttpStatus.BAD_REQUEST );
                }
            }
        }catch (Exception exception){
            log.error("{}",exception);
        }
        return new ResponseEntity<String>("{\"message\":\""+"Bad credentials"+"\"}",HttpStatus.BAD_REQUEST);
    }

    @Transactional
    public ResponseEntity<List<UserDTO>> getAllUser() {
        try{
            if(jwtFilter.isAdmin()){
                return new ResponseEntity<>(userRepository.getAllUser(),HttpStatus.OK);
            }else
                return new ResponseEntity<>(userRepository.getByUsername(jwtFilter.getCurrentUser()),HttpStatus.OK);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
