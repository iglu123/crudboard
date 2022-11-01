package com.example.makeboard.Repository;

import com.example.makeboard.Domain.Answer.answer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AnswerRepository extends JpaRepository<answer, Integer> {

}
