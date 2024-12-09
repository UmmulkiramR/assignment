package com.example.assignment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/assignment/")
@RequiredArgsConstructor
public class BaseController {
    @GetMapping(value="/alive")
    public String isAlive(){
        return "Assignment app is up and running";
    }
}
