package com.example.makeboard.Controller;

import com.example.makeboard.Form.Site_userCreateForm;
import com.example.makeboard.Service.Site_userService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class Site_userController {

    private final Site_userService site_userService;

    @GetMapping("/signup")
    public String signup(Site_userCreateForm site_userCreateForm) {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid Site_userCreateForm site_userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }

        if (!site_userCreateForm.getPassword1().equals(site_userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "signup";
        }

        site_userService.create(site_userCreateForm.getUsername(),
                site_userCreateForm.getEmail(), site_userCreateForm.getPassword1());

        return "redirect:/";
    }
}
