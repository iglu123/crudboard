package com.example.makeboard.Service;


import com.example.makeboard.Domain.Site_User.site_user;
import com.example.makeboard.Repository.Site_userRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@Service
public class Site_userService {

    @Autowired
    private Site_userRepository site_userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public site_user create(String username, String email, String password) {
        site_user user = new site_user();
        user.setUsername(username);
        user.setEmail(email);

        user.setPassword(passwordEncoder.encode(password));
        this.site_userRepository.save(user);
        return user;
    }


}
