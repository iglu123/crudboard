package com.example.makeboard.Repository;

import com.example.makeboard.Domain.Question.question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository <question, Integer > {


    List<question> findAllByAuthor_Id(Long author_id);
    Page<question> findAll(Pageable pageable);

    Page<question> findBySubjectContaining(String keyword, Pageable pageable);
}
