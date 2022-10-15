package com.example.makeboard;

import com.example.makeboard.Domain.Site_User.site_user;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Site_userRepository extends JpaRepository <site_user , Integer> {
}
