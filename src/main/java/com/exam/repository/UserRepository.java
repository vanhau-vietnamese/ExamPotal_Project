package com.exam.repository;

import com.exam.dto.response.UserResponse;
import com.exam.enums.ERole;
import com.exam.enums.EStatus;
import com.exam.model.User;
import com.exam.model.UserQuizResult;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query("SELECT result FROM UserQuizResult result WHERE result.user.id= :user_id")
    Set<UserQuizResult> getQuizResultOfUser(@Param("user_id") String user_id);

    @Query("SELECT u FROM User u WHERE u.email= :email AND u.status = 'Active'")
    User findByEmail(@Param("email") String email);
    boolean existsByEmail(String email);
    User findByEmailAndFirebaseId(String email, String firebaseId);

    @Query("SELECT new com.exam.dto.response.UserResponse(u.id, u.fullName, u.email, u.role, u.firebaseId, u.createdAt, u.createdBy.fullName) FROM User u WHERE u.id = :user_id AND u.status = 'Active'")
    UserResponse getUserById(@Param("user_id")String userId);

    @Query("SELECT u FROM User u WHERE u.role = 'admin' AND u.id != :user_id AND u.status = 'Active'")
    List<User> getAllAdminUsers(@Param("user_id") String userId);

    @Query("SELECT u FROM User u WHERE u.role = 'user' AND u.status = 'Active'")
    List<User> getAllStudent();

    int countUsersByStatusAndRole(EStatus status, ERole role);

    @Transactional
    @Modifying
    @Query("UPDATE User u set u.password = ?2 WHERE u.email = ?1")
    void updatePassword(String email, String password);

    @Query("SELECT COUNT(u) FROM User u WHERE YEAR(u.createdAt) = :year AND MONTH (u.createdAt) = :month AND u.role = 'student' AND u.status = 'Active'")
    int countUsersByMonthAndYear(@Param("month") int month, @Param("year") int year);
}
