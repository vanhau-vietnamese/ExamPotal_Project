package com.exam.repository;

import com.exam.model.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionTypeRepository extends JpaRepository<QuestionType, String> {
    public QuestionType findByAlias(String alias);
    public boolean existsByDisplayName(String displayName);
}
