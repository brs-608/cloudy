package com.burmic.cloudy.Entities;


import com.burmic.cloudy.Enums.Gender;
import com.burmic.cloudy.Enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "users")
//@Getter
//@Setter
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String first_name;
    @Column(nullable = false)
    private String last_name;
    @Column(nullable = false)
    private String phone_number;
    @Column(nullable = false,unique = true,name = "email_id")
    private String emailId;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date_of_birth;
    @Column(nullable = false)
    private boolean enabled = true;

    public User() {
    }

    public User(long id, String first_name, String last_name, String phone_number, String emailId, String password, Gender gender, Role role, Date date_of_birth, boolean enabled) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone_number = phone_number;
        this.emailId = emailId;
        this.password = password;
        this.gender = gender;
        this.role = role;
        this.date_of_birth = date_of_birth;
        this.enabled = enabled;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmailId(){
        return this.emailId;
    }

    public void setEmailId(String email_id) {
        this.emailId = email_id;
    }


    public String getPassword() {
        return password;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }
}
