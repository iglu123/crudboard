package com.example.makeboard;

import com.example.makeboard.Domain.Question.question;
import com.example.makeboard.Repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MakeboardApplicationTests {

	@Autowired
	AnswerRepository answerRepository;
	@Autowired
	QuestionRepository questionRepository;


	@Autowired
	Site_userRepository site_userRepository;

	@Test
	void contextLoads() {
//		answer a = new answer();
//		question q1 = new question();
//		answer_voter av = new answer_voter();
//		question_voter qv = new question_voter();
//		site_user s = new site_user();
//
//		q1.setId(1);
//		av.setAnswer_id(1);
//		av.setVoter_id(100l);
//		qv.setQuestion_id(1);
//		qv.setVoter_id(12l);
//		s.setEmail("a@a.com");
//
//		this.answerRepository.save(a);
//		this.questionRepository.save(q);
//		this.answer_voterRepository.save(av);
//		this.question_voterRepository.save(qv);
//		this.site_userRepository.save(s);
		List<question> all = this.questionRepository.findAll();


		question q = all.get(0);

	}

}
