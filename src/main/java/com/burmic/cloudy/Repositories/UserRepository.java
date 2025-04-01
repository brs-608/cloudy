package com.burmic.cloudy.Repositories;


import com.burmic.cloudy.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findUserByEmailId(String email_id);

    @Query("SELECT u FROM User u WHERE LOWER(u.first_name) LIKE LOWER(CONCAT(:query,'%'))" +
            "OR LOWER(u.emailId) LIKE LOWER(CONCAT(:query,'%'))" +
            "OR LOWER(u.last_name) LIKE LOWER(CONCAT(:query,'%'))"
    )
    List<User> searchUsers(String query);
}
