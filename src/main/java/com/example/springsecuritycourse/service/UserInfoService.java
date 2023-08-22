package com.example.springsecuritycourse.service;

import com.example.springsecuritycourse.entity.UserInfo;
import com.example.springsecuritycourse.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // this is given to us by the UserDetailService
    @Override
    public UserInfo loadUserByUsername(String username) throws UsernameNotFoundException {
     Optional<UserInfo> user =  userInfoRepository.findByName(username);
        return user.get();
    }

    // adding new user
    public ResponseEntity<UserInfo> addUser(UserInfo userInfo){

        // Hash the user's password using BCrypt
        String hashedPassword = passwordEncoder.encode(userInfo.getPassword());
        userInfo.setPassword(hashedPassword);

        // saving the user
        UserInfo user =  userInfoRepository.save(userInfo);


        return ResponseEntity.ok(user);
    }
}
