package com.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Console;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        System.out.println("home() asdasdd");
        return "main";
    }
}
