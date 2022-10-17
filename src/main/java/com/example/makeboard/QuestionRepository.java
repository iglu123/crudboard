package com.example.makeboard;

import com.example.makeboard.Domain.Question.question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface QuestionRepository extends JpaRepository <question, Integer > {
    Page<question> findAll(Pageable pageable);
    Page<question> findAll(Specification<question> spec, Pageable pageable);

    Page<question> findBySubjectContaining(String keyword, Pageable pageable);
}
