package com.example.makeboard;

import com.example.makeboard.Domain.Answer_Voter.answer_voter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Answer_voterRepository extends JpaRepository <answer_voter, Integer> {
}
