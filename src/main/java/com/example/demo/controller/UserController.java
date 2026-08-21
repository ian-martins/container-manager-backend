package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:5173/")
@RequiredArgsConstructor
public class UserController{

    private final UserController userController;

}