package com.CMPE202.healthclub.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    public HomeController() {
    }
    @GetMapping({"/"})
    public String home() {
        return "<h1>Welcome to Health Club</h1>";
    }
    @GetMapping({"/admin"})
    public String admin() {
        return "<h1>Welcome Admin</h1>";
    }
    @GetMapping({"/user"})
    public String user() {
        return "<h1>Welcome User</h1>";
    }
}
