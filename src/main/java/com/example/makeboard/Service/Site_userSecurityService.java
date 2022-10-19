package com.example.makeboard.Service;

import com.example.makeboard.Domain.Site_User.site_user;
import com.example.makeboard.Repository.Site_userRepository;
import com.example.makeboard.Role.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class Site_userSecurityService implements UserDetailsService {

    private final Site_userRepository site_userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<site_user> _site_user = this.site_userRepository.findByUsername(username);

        if (_site_user==null) {
            throw new UsernameNotFoundException("User not registered");
        }

        site_user site_user = _site_user.get();
        List<GrantedAuthority> authorities = new ArrayList<>();

        if ("admin".equals(username)) {
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(Role.USER.getValue()));
        }
        System.out.println(_site_user);
        return new User(site_user.getUsername(), site_user.getPassword(), authorities);


    }


}
