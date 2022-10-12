package com.example.makeboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@RequestMapping("/")
@Controller
public class WebController {


    @RequestMapping("/index")
    public String index() {
        return "index";
    }


@RequestMapping("/test")
    public String test(Model model) {
        model.addAttribute("test","모델로 가져옴");
        return "test";
    }
}