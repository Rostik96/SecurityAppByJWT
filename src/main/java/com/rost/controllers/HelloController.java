package com.rost.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rost.security.UserDetailsImpl;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, world!";
    }

    @GetMapping("/show-user-info")
    public ResponseEntity<String> showUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails personDetails = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok(personDetails.toString());
    }
}
