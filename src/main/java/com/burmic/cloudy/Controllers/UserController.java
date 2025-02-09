package com.burmic.cloudy.Controllers;


import com.burmic.cloudy.Entities.User;
import com.burmic.cloudy.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/users")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("user/{id}")
    public User getUser(@PathVariable("id") Long userId){
        return userService.getUser(userId);
    }

    @PostMapping("create-user")
    public void createUser(@RequestBody User user){
        userService.createUser(user.getEmail_id(),user.getFirst_name(),user.getLast_name(),user.getGender(),user.getRole(), user.getPhone_number(),user.getDate_of_birth(),user.getPassword());
    }
}
