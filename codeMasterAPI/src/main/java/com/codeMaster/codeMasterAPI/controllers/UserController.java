package com.codeMaster.codeMasterAPI.controllers;


import com.codeMaster.codeMasterAPI.dto.user.UserResponse;
import com.codeMaster.codeMasterAPI.models.User;
import com.codeMaster.codeMasterAPI.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{email}")
    public UserResponse getUser(@PathVariable String email){

        return userService.getUser();
    }

//    @GetMapping("/{email}/update/")
//    public UserResponse updateUser(@PathVariable String email, @RequestBody User user){
//        return userService.updateUser();
//    }

}
