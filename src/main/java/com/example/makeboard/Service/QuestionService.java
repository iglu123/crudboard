package com.example.makeboard.Service;

import com.example.makeboard.Domain.Question.question;
import com.example.makeboard.Domain.Site_User.site_user;
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
import com.example.makeboard.Domain.Answer.answer;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    private Specification<question> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거
                Join<question, site_user> u1 = q.join("author", JoinType.LEFT);
                Join<question, answer> a = q.join("answerList", JoinType.LEFT);
                Join<answer, site_user> u2 = a.join("author", JoinType.LEFT);
                return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목
                        cb.like(q.get("content"), "%" + kw + "%"),      // 내용
                        cb.like(u1.get("username"), "%" + kw + "%"),    // 질문 작성자
                        cb.like(a.get("content"), "%" + kw + "%"),      // 답변 내용
                        cb.like(u2.get("username"), "%" + kw + "%"));   // 답변 작성자
            }
        };
    }



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




    public Page<question> getList(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Specification<question> spec = search(kw);
        return this.questionRepository.findAll(pageable);
    }


}
