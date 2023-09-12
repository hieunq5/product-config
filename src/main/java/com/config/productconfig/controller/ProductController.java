package com.config.productconfig.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

@Controller
public class ProductController {

    @Value("${test.example}")
    private String testExample;

    @PostConstruct
    public void init() {
        System.out.println(testExample);
    }
}
