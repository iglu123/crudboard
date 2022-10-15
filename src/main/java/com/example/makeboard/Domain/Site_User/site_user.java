package com.example.makeboard.Domain.Site_User;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;

@Getter
@Setter
@Entity
public class site_user {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false )
    private Long id;
    private String email;
    private String password;
    private String username;
}
