package com.example.makeboard.Service;

import com.example.makeboard.Repository.AnswerRepository;
import com.example.makeboard.Domain.Answer.answer;
import com.example.makeboard.Domain.Question.question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class AnswerService {


    @Autowired
    private AnswerRepository answerRepository;

    //답변 작성
    public void answrite(question question, String content) {
        answer answer = new answer();
        answer.setContent(content);
        answer.setCreate_date(LocalDateTime.now());
        answer.setQuestion(question);
        this.answerRepository.save(answer);
    }

    public void ansDelete(Integer id) {


        answerRepository.deleteById(id);

    }

    public answer getAnswer(Integer id) {
        Optional<answer> answer = this.answerRepository.findById(id);
        return answer.get();

    }

    public void ansModify(answer answer, String content) {
        answer.setContent(content);
        answer.setModify_date(LocalDateTime.now());
        this.answerRepository.save(answer);
    }

//    //게시물 목록
//    public answer ansboardList(Integer id){
//
//
//        return answerRepository.findByQuestion_Id(id).get();
//    }


//    //답변 게시글 불러오기
//    public answer ansboardView(Integer id) {
//        return answerRepository.findById(id).get();
//    }


}
