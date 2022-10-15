package com.example.makeboard.Service;

import com.example.makeboard.Domain.Question.question;
import com.example.makeboard.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.ArrayList;
import org.springframework.data.domain.Sort;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;

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
    public List<question> boardList() {

        return questionRepository.findAll();
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




    public Page<question> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.questionRepository.findAllBy(pageable);
    }


}
