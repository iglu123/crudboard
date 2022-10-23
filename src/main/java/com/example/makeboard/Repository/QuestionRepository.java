package com.example.makeboard.Repository;

import com.example.makeboard.Domain.Question.question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository <question, Integer > {


    Page<question> findAll(Pageable pageable);

    Page<question> findBySubjectContaining(String keyword, Pageable pageable);
}
