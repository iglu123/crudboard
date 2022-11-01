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


    //회원 가입 폼
    @GetMapping("/signup")
    public String get_signup(Site_userCreateForm site_userCreateForm) {


        return "signup";
    }


    //입력된 회원 가입 post
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


    //로그인 누르면 로그인 창으로 이동
    @GetMapping("/login")
    public String login() {
        return "login";
    }


    //회원 탈퇴(DB에서 삭제)
    @Transactional
    @GetMapping("/resign")
    public String resign(site_user site_user, Principal principal) {
        site_userService.deleteUser(principal.getName());

        return "redirect:/user/logout"; //자동으로 로그아웃시키기
    }


    //회원 정보(비밀번호) 수정
    @GetMapping("/edit")
    public String seeInfo(Site_userEditForm site_userEditForm, Principal principal, Model model) {
        model.addAttribute("memberinfo", site_userService.getUser(principal.getName()));

        return "editinfo";
    }


    //회원 정보 수정(입력된 비밀번호로 정보 변경 후 post)
    @PostMapping("/editpw")
    public String editInfo(@Valid Site_userEditForm site_userEditForm, Principal principal, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/user/edit";
        }

        if (!site_userEditForm.getPassword1().equals(site_userEditForm.getPassword2())) {   //새로운 비밀번호, 비밀번호 확인 일치하지 않는다면
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "redirect:/user/edit";
        }
        try {   //새로운 비밀번호로 비밀번호 업데이트
            site_user site_usertmp = site_userService.getUser(principal.getName());
            site_userService.updateUser(site_usertmp, site_userEditForm.getPassword1());
        } catch (Exception e) {

            bindingResult.reject("changeFailed", e.getMessage());
            return "redirect:/user/edit";
        }

        return "/login";
    }
}
