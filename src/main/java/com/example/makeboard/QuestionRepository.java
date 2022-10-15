package com.example.makeboard;

import com.example.makeboard.Domain.Question.question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionRepository extends JpaRepository <question, Integer > {
    Page<question> findAllBy(Pageable pageable);

}
