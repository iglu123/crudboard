package com.example.makeboard;

import com.example.makeboard.Domain.Question_Voter.question_voter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Question_voterRepository extends JpaRepository <question_voter,Integer> {

}
