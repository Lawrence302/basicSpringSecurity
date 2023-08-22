package com.example.springsecuritycourse.controller;

import com.example.springsecuritycourse.entity.UserInfo;
import com.example.springsecuritycourse.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/greetings")
public class GreetingsController {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello, World!");
    }

    @GetMapping("/goodbye")
    public ResponseEntity<String> sayGoodbye() {
        return ResponseEntity.ok("Goodbye, World!");
    }

    @GetMapping("/hi")
    public ResponseEntity<String> sayHi() {
        return ResponseEntity.ok("Hi, World!");
    }

    @GetMapping("/bye")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> sayBye() {
        return ResponseEntity.ok("Bye, World!");
    }

    @GetMapping("/hola")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> sayHola() {
        return ResponseEntity.ok("Hola, World!");
    }

    @PostMapping("/add")
    public ResponseEntity<UserInfo> addUser(@RequestBody UserInfo userInfo){
        return userInfoService.addUser(userInfo);

    }
}
