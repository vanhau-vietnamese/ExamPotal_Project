package com.exam.repository;

import com.exam.dto.response.UserInfoResponse;
import com.exam.enums.ERole;
import com.exam.model.User;
import com.exam.model.UserQuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query("SELECT result FROM UserQuizResult result WHERE result.user.id= :user_id")
    public Set<UserQuizResult> getQuizResultOfUser(@Param("user_id") String user_id);
    public User findByEmail(String email);
    public boolean existsByEmail(String email);
    public User findByEmailAndFirebaseId(String email, String firebaseId);

    @Query("SELECT new com.exam.dto.response.UserInfoResponse(u.id, u.fullName, u.email, u.role, u.firebaseId, u.createdAt, u.createdBy.fullName) FROM User u WHERE u.id = :user_id AND u.status = 'Active'")
    UserInfoResponse getUserById(@Param("user_id")String userId);

//    @Query("SELECT new com.exam.dto.response.UserInfoResponse(u.id, u.fullName, u.email, u.role, u.firebaseId, u.createdAt) FROM User u WHERE u.role = 'admin' AND u.id != :user_id AND u.status = 'Active'")

    @Query("SELECT u FROM User u WHERE u.role = 'admin' AND u.id != :user_id AND u.status = 'Active'")
    List<User> getAllAdminUsers(@Param("user_id") String userId);

}
