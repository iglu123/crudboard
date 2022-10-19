package com.example.makeboard.Domain.Site_User;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class site_user {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true )
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique=true)
    private String username;
}
