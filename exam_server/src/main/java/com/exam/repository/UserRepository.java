package com.exam.repository;

import com.exam.dto.response.UserResponse;
import com.exam.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String userName);
    boolean existsByUsername(String username);
    boolean existsByEmail(String username);
    User findByEmail(String email);

//    List<UserResponse> findAllBy();
    List<UserResponse> getAllUser();
}

