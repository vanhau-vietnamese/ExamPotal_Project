package com.exam.repository;

import com.exam.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Query("SELECT ans.id FROM Answer ans WHERE ans.question.id =: questionId AND ans.isCorrect = true")
    public List<Long> getCorrectAnswerFromQuestion(@Param("questionId") Long questionId);

}
