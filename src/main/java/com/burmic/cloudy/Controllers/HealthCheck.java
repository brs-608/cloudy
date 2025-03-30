package com.burmic.cloudy.Controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class HealthCheck {
    @GetMapping("")
    public String sayHello(){
        return "hello the app is working";
    }

    @GetMapping("/admin")
    public String forAdmin(){
        return "hi admin";
    }
    @GetMapping("/user")
    public String forUser(){
        return "hi user";
    }
}
