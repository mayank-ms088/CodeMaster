package com.codeMaster.codeMasterAPI.service;

import com.codeMaster.codeMasterAPI.dto.user.UserResponse;
import com.codeMaster.codeMasterAPI.models.User;
import com.codeMaster.codeMasterAPI.repository.UserRepository;
import com.codeMaster.codeMasterAPI.service.auth.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final UserDetailsServiceImpl userDetailsService;

    public UserResponse getUser(String email){
        UserResponse.UserResponseBuilder userResponseBuilder=UserResponse.builder();
        Optional<User> user= userRepository.findByEmail(email);
        user.ifPresent(user1 -> {
            userResponseBuilder.email(user1.getEmail());
            userResponseBuilder.fullName(user1.getFullName());
        });
        return userResponseBuilder.build();
    }

    public void saveUser(User user){
        Optional<User> userOptional = userDetailsService.getActiveUser();
        userOptional.ifPresent(user1 -> {
            user1.setFullName(user.getFullName());
            userRepository.saveAndFlush(user1);
        });
    }

    public UserResponse getUser() {
        return getUser(userDetailsService.getActiveUser().get().getEmail());
    }


}
