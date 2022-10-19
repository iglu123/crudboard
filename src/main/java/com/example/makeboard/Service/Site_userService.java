package com.example.makeboard.Service;


import com.example.makeboard.DataNotFoundException;
import com.example.makeboard.Domain.Site_User.site_user;
import com.example.makeboard.Repository.Site_userRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public site_user getUser(String username) {
        Optional<site_user> siteUser = this.site_userRepository.findByUsername(username);
        if ( siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("user not found");
        }
    }

}
