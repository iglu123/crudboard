package com.example.makeboard.Service;

import com.example.makeboard.Domain.Question.question;
import com.example.makeboard.Repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;


    //질문 작성
    public void questwrite(question quest) {
        if (quest.getCreate_date() == null) {
            quest.setCreate_date(LocalDateTime.now());
        }

        questionRepository.save(quest);
    }


    //게시물 목록
    public Page<question> boardList(Pageable pageable) {

        return questionRepository.findAll(pageable);
    }

    //질문 게시글 불러오기
    public question boardView(Integer id) {
        Optional<question> question = this.questionRepository.findById(id);
        return question.get();
    }


    //게시글 삭제
    public void boardDelete(Integer id) {
        questionRepository.deleteById(id);
    }







    public Page<question> searchList(String keyword, Pageable pageable) {

        return questionRepository.findBySubjectContaining(keyword, pageable);
    }

}
