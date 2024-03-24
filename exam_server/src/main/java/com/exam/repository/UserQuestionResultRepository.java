package com.exam.repository;

import com.exam.model.UserQuestionResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserQuestionResultRepository extends JpaRepository<UserQuestionResult, Long> {
}
