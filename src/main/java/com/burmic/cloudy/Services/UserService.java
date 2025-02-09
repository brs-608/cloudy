package com.burmic.cloudy.Services;


import com.burmic.cloudy.Enums.Gender;
import com.burmic.cloudy.Enums.Role;
import com.burmic.cloudy.Entities.User;
import com.burmic.cloudy.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    UserService (PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }
    public void createUser(String email_id, String first_name, String last_name,  Gender gender, Role role, String phone_number, Date date_of_birth,String password){
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User();
        user.setDate_of_birth(date_of_birth);
        user.setEmail_id(email_id);
        user.setGender(gender);
        user.setFirst_name(first_name);
        user.setLast_name(last_name);
        user.setPhone_number(phone_number);
        user.setRole(role);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    public User getUser(Long id){
        return userRepository.findById(id).orElse(null);
    }


}
