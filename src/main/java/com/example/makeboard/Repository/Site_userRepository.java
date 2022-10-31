package com.example.makeboard.Repository;

import com.example.makeboard.Domain.Site_User.site_user;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface Site_userRepository extends JpaRepository <site_user , Long> {
    Optional<site_user> findByUsername(String username);
    @Transactional
    Optional<site_user> deleteByUsername(String username);



}
