package com.burmic.cloudy.Controllers;


import com.burmic.cloudy.Entities.User;
import com.burmic.cloudy.Services.UserService;
import com.burmic.cloudy.Utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    UserService userService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String,String>  loginRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.get("email"),loginRequest.get("password"))
        );
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.get("email"));
        String token = jwtUtil.generateToken(loginRequest.get("email"));
        Map<String,String> response = new HashMap<>();
        response.put("token" ,token);
        return ResponseEntity.ok(response);

    }
    @PostMapping("/create-user")
    public void createUser(@RequestBody User user){
        try{
            userService.createUser(user.getEmailId(),user.getFirst_name(),user.getLast_name(),user.getGender(),user.getRole(), user.getPhone_number(),user.getDate_of_birth(),user.getPassword());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
