package com.example.makeboard.Service;

import com.example.makeboard.Domain.Question.question;
import com.example.makeboard.Domain.Site_User.site_user;
import com.example.makeboard.Repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class QuestionService {


    @Autowired
    private QuestionRepository questionRepository;


    //질문 작성
    public void questwrite(question quest, site_user username) {
        if (quest.getCreate_date() == null) {   //생성 일시가 없다면 현재 시간으로 바로 설정
            quest.setCreate_date(LocalDateTime.now());
        }
        question qquestion = new question();    //새로운 생성자 만들어서 폼에 입력되어있는 값들 대입하여 set
        qquestion.setContent(quest.getContent());
        qquestion.setSubject(quest.getSubject());
        qquestion.setCreate_date(quest.getCreate_date());
        qquestion.setAuthor(username);
        questionRepository.save(qquestion);     //persist
    }


    //질문 수정
    public void questmodify(question quest,String subject, String content) {
        quest.setSubject(subject);
        quest.setContent(content);
        quest.setModify_date(LocalDateTime.now());
        this.questionRepository.save(quest);    //merge
    }


    //author_id의 값으로 question 불러오기
    //관리자(author_id=1)의 질문들을 불러오기 위해서
    public List<question> adminList(Long author) {
        return questionRepository.findAllByAuthor_Id(author);
        }


    //질문 목록
    public Page<question> boardList(Pageable pageable) {

        return questionRepository.findAll(pageable);//페이지 별로 나뉘어 한 페이지의 질문 목록 보여주기
    }


    //질문 답변 상세 불러오기
    public question boardView(Integer id) {
        Optional<question> question = this.questionRepository.findById(id); //question의 id로 question 불러오기
        return question.get();
    }


    //게시글 삭제
    public void boardDelete(Integer id) {
        questionRepository.deleteById(id);
    }


    //제목에 포함되어 있는 키워드로 검색된 질문 불러오기
    public Page<question> searchList(String keyword, Pageable pageable) {

        return questionRepository.findBySubjectContaining(keyword, pageable);
    }

    //질문 추천(voter는 Set이라서 유저 중복 안됨)
    public void vote(question question, site_user site_user) {
        question.getVoter().add(site_user);
        this.questionRepository.save(question);
    }


}
