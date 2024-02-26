package com.exam.repo;

import com.exam.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String userName);
    boolean existsByUsername(String username);
    boolean existsByEmail(String username);
}
