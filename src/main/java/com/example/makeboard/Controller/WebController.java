package com.example.makeboard.Controller;

import com.example.makeboard.Domain.Answer.answer;
import com.example.makeboard.Domain.Question.question;
import com.example.makeboard.Domain.Site_User.site_user;
import com.example.makeboard.Repository.QuestionRepository;
import com.example.makeboard.Service.AnswerService;
import com.example.makeboard.Service.QuestionService;
import com.example.makeboard.Service.Site_userService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

import java.security.Principal;


@RequiredArgsConstructor
@RequestMapping("/")
@Controller
public class WebController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;

    private final Site_userService site_userService;

    @Autowired
    private QuestionRepository questionRepository;




    @GetMapping ("/board/write") //localhost:8100/board/write
    public String index() {
        return "boardwrite";
    }


    @PostMapping("/board/writepro")
    public String questionWriteProcess(question quest, Principal principal) {
        site_user site_username = this.site_userService.getUser(principal.getName());
        questionService.questwrite(quest,site_username);

        return "redirect:/board/list";
    }

    @RequestMapping("/test")
    public String test(Model model) {
        model.addAttribute("test","모델로 가져옴");
        return "test";
    }




//    public String boardList(Model model) {
//
//        model.addAttribute("list", questionService.boardList());
//        return "boardlist";
//    }

    @GetMapping("/board/list")
    public String boardList(Model model,
                            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                            String keyword) {

        Page<question> list = null;
        if(keyword == null) {
            list = questionService.boardList(pageable);
        } else {
            list = questionService.searchList(keyword, pageable);
        }

//        model.addAttribute("boardList", questionService.boardList());
//        Page<question> list = questionService.boardList(pageable);

        int nowPage = pageable.getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4 , 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());
        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "boardlist";
    }

    @GetMapping("/board/view")
    public String boardView(Model model, Integer id) {

        model.addAttribute("question", questionService.boardView(id));

//        model.addAttribute("answer", answerService.ansboardList(id));
        return "boardview";
    }



    @PostMapping("/reply/{id}")
    public String answerWriteProcess(Model model, @PathVariable("id") Integer id, @RequestParam String content, Principal principal) {
//        answerService.answrite(ans);
        question question = this.questionService.boardView(id);
        site_user site_username = this.site_userService.getUser(principal.getName());
        this.answerService.answrite(question, content, site_username);
        return String.format("redirect:/board/view/%s","?id="+id);
    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id) {
        questionService.boardDelete(id);

        return "redirect:/board/list";
    }

    @GetMapping("/board/ansdelete")
    public String ansDelete(Integer id) {

        answer answer = this.answerService.getAnswer(id);
        answerService.ansDelete(id);


        //return String.format("redirect:/board/view/%s","?id=26");
        return String.format("redirect:/board/view/?id=%s", answer.getQuestion().getId());

    }

    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id, Model model ) {
        model.addAttribute("question", questionService.boardView(id));
        return "boardmodify";
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, question question,Principal principal) {
        question questiontmp = this.questionService.boardView(id);
        questiontmp.setSubject(question.getSubject());
        questiontmp.setContent(question.getContent());
        questiontmp.setModify_date(LocalDateTime.now());




        this.questionService.questmodify(questiontmp,questiontmp.getSubject(),questiontmp.getContent());

        return String.format("redirect:/board/view/%s","?id="+id);
    }

    @GetMapping("/board/ansmodify/{id}")
    public String ansboardModify(@PathVariable("id") Integer id, Model model ) {
        model.addAttribute("answer", answerService.getAnswer(id));


        return "answerform";

    }

    @PostMapping("/board/ansupdate/{id}")
    public String ansboardUpdate(@PathVariable("id") Integer id, answer answer) {

        answer answertmp = answerService.getAnswer(id);
        answertmp.setContent(answer.getContent());
        answertmp.setModify_date(LocalDateTime.now());

        answerService.ansModify(answertmp, answertmp.getContent());

        return String.format("redirect:/board/view/?id=%s", answertmp.getQuestion().getId());
    }

    //question vote
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/questvote/{id}")
    public String questionVote(Principal principal, @PathVariable("id") Integer id) {
        question question = this.questionService.boardView(id);
        site_user siteUser = this.site_userService.getUser(principal.getName());
        this.questionService.vote(question, siteUser);
        return String.format("redirect:/board/view/?id=%s", id);
    }


    //answer vote
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/ansvote/{id}")
    public String answerVote(Principal principal, @PathVariable("id") Integer id) {
        answer answer = this.answerService.getAnswer(id);
        site_user siteUser = this.site_userService.getUser(principal.getName());
        this.answerService.vote(answer, siteUser);
        return String.format("redirect:/board/view/?id=%s", answer.getQuestion().getId());
    }

}



