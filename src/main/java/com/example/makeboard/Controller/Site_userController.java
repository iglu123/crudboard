package com.example.makeboard.Controller;

import com.example.makeboard.Domain.Site_User.site_user;
import com.example.makeboard.Form.Site_userCreateForm;
import com.example.makeboard.Form.Site_userEditForm;
import com.example.makeboard.Service.Site_userService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class Site_userController {

    private final Site_userService site_userService;

    @GetMapping("/signup")
    public String get_signup(Site_userCreateForm site_userCreateForm) {


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

        try {
            site_userService.create(site_userCreateForm.getUsername(),
                    site_userCreateForm.getEmail(), site_userCreateForm.getPassword1());
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @Transactional
    @GetMapping("/resign")
    public String resign(site_user site_user, Principal principal) {
        site_userService.deleteUser(principal.getName());

        return "redirect:/user/logout";
    }

    @GetMapping("/edit")
    public String seeInfo(Site_userEditForm site_userEditForm, Principal principal, Model model) {
        model.addAttribute("memberinfo", site_userService.getUser(principal.getName()));

        return "editinfo";
    }

    @PostMapping("/editpw")
    public String editInfo(@Valid Site_userEditForm site_userEditForm, Principal principal, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/user/edit";
        }

        if (!site_userEditForm.getPassword1().equals(site_userEditForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "redirect:/user/edit";
        }
        try {
            site_user site_usertmp = site_userService.getUser(principal.getName());
            site_userService.updateUser(site_usertmp, site_userEditForm.getPassword1());
        } catch (Exception e) {

            bindingResult.reject("changeFailed", e.getMessage());
            return "redirect:/user/edit";
        }

        return "/login";
    }
}
