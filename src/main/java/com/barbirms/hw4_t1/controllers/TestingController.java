package com.barbirms.hw4_t1.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestingController {
    @RequestMapping("/test/all")
    public String testAll() {
        return "all";
    }
}
