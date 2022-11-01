package com.example.makeboard.Service;

import com.example.makeboard.Domain.Answer.answer;
import com.example.makeboard.Domain.Question.question;
import com.example.makeboard.Domain.Site_User.site_user;
import com.example.makeboard.Repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;




@Service
public class AnswerService {


    @Autowired
    private AnswerRepository answerRepository;

    //답변 작성
    public void answrite(question question, String content, site_user author) {
        answer answer = new answer();//새로운 생성자 만들어서 폼에 입력되어있는 값들 대입하여 set
        answer.setContent(content);
        answer.setCreate_date(LocalDateTime.now());
        answer.setQuestion(question);
        answer.setAuthor(author);
        this.answerRepository.save(answer);//persist
    }


    //답변 삭제
    public void ansDelete(Integer id) {

        answerRepository.deleteById(id);

    }


    //answer의 id로 answer 불러오기
    public answer getAnswer(Integer id) {
        Optional<answer> answer = this.answerRepository.findById(id);
        return answer.get();

    }


    //해당 answer에 입력된 content와 현재 localtime으로 수정
    public void ansModify(answer answer, String content) {
        answer.setContent(content);
        answer.setModify_date(LocalDateTime.now());
        this.answerRepository.save(answer);
    }


    //답변 추천(voter는 Set이라서 유저 중복 안됨)
    public void vote(answer answer, site_user site_user) {
        answer.getVoter().add(site_user);
        this.answerRepository.save(answer);
    }


}
