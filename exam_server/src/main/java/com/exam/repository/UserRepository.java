package com.exam.repository;

import com.exam.model.User;
import com.exam.model.UserQuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT result FROM UserQuizResult result WHERE result.user.id= :user_id")
    public Set<UserQuizResult> getQuizResultOfUser(@Param("user_id") Long user_id);
    public User findByEmail(String email);
    public boolean existsByEmail(String email);
}
