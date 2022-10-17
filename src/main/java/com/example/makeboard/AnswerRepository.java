package com.example.makeboard;

import com.example.makeboard.Domain.Answer.answer;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<answer, Integer> {

}
