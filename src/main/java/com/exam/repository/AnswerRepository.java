package com.exam.repository;

import com.exam.model.Answer;
import com.exam.model.Question;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Query("SELECT ans.id FROM Answer ans WHERE ans.question.id = :questionId AND ans.isCorrect = true")
    List<Long> getCorrectAnswerFromQuestion(@Param("questionId") Long questionId);

    Set<Answer> findAllByQuestion(Question question);

    @Transactional
    @Modifying
    @Query("DELETE FROM Answer a WHERE a.question.id = :questionId")
    void deleteByQuestionId(@Param("questionId") Long questionId);
}
