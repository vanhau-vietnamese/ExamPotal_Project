package com.exam.repository;

import com.exam.model.UserQuestionChoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserQuestionChoiceRepository extends JpaRepository<UserQuestionChoice,Long> {
}
