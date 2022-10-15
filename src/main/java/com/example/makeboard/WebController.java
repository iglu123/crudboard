package com.example.makeboard;

import com.example.makeboard.Domain.Answer.answer;
import com.example.makeboard.Domain.Question.question;
import com.example.makeboard.Service.AnswerService;
import com.example.makeboard.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/")
@Controller
public class WebController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;

    @Autowired
    private QuestionRepository questionRepository;


    @GetMapping ("/board/write") //localhost:8100/board/write
    public String index() {
        return "boardwrite";
    }


    @PostMapping("/board/writepro")
    public String questionWriteProcess(question quest) {
        questionService.questwrite(quest);

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
    public String boardList(Model model, @RequestParam(value="page", defaultValue="0") int page) {

//        model.addAttribute("boardList", questionService.boardList());
        Page<question> paging = this.questionService.getList(page);
        model.addAttribute("paging", paging);
        return "boardlist";
    }

    @GetMapping("/board/view")
    public String boardView(Model model, Integer id) {

        model.addAttribute("question", questionService.boardView(id));

//        model.addAttribute("answer", answerService.ansboardList(id));
        return "boardview";
    }



    @PostMapping("/reply/{id}")
    public String answerWriteProcess(Model model, @PathVariable("id") Integer id, @RequestParam String content) {
//        answerService.answrite(ans);
        question question = this.questionService.boardView(id);
        this.answerService.answrite(question, content);
        return String.format("redirect:/board/view/%s","?id="+id);
    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id) {
        questionService.boardDelete(id);

        return "redirect:/board/list";
    }

    @GetMapping("/board/ansdelete")
    public String ansDelete(Integer id) {
        answerService.ansDelete(id);




        return String.format("redirect:/board/view/%s","?id=26");

    }

    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id, Model model ) {
        model.addAttribute("question", questionService.boardView(id));
        return "boardmodify";
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, question question) {
        question questiontmp = questionService.boardView(id);
        questiontmp.setSubject(question.getSubject());
        questiontmp.setContent(question.getContent());
        questiontmp.setModify_date(LocalDateTime.now());

        questionService.questwrite(questiontmp);

        return String.format("redirect:/board/view/%s","?id="+id);
    }


    @GetMapping("/signup")
    public String signUp() {
        return "signup";
    }
}
//다시 추가
//회사 컴에서 추가22222