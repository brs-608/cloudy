package com.burmic.cloudy.Controllers;


import com.burmic.cloudy.Entities.User;
import com.burmic.cloudy.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/users")
@RestController
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("user/{id}")
    public User getUser(@PathVariable("id") Long userId){
        return userService.getUser(userId);
    }
    @GetMapping("get-all")
    public List<User> getAll(){
        return userService.getAll();
    }

    @GetMapping("/get-user/{email}")
    public ResponseEntity<Map<String,Object>> getUserData(@PathVariable("email" )String email_id){
        User user = userService.getUserByEmail(email_id);
        Map<String,Object> response = new HashMap<>();
        response.put("first_name" , user.getFirst_name());
        response.put("last_name" , user.getLast_name());
        response.put("email_id" , user.getEmailId());
        response.put("date_of_birth" , user.getDate_of_birth());
        response.put("gender" , user.getGender());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/email")
    public String getCurrentUserEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            String email = authentication.getName();
            return email;
        }else{
            return "";
        }
    }
}
