package com.example.makeboard.Domain.Site_User;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class site_user {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String username;

    @Column
    private String password;

    @Column(unique = true)
    private String email;


}
