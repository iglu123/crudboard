package com.example.makeboard.Domain.Question;

import com.example.makeboard.Domain.Answer.answer;
import com.example.makeboard.Domain.Site_User.site_user;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;
import javax.persistence.OneToMany;

@Data
@Entity
public class question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column
    private LocalDateTime create_date;

    @Column
    private LocalDateTime modify_date;

    @Column(length = 200)
    private String subject;

    @ManyToOne
    private site_user author;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<answer> answerList;

    @ManyToMany
    Set<site_user> voter;

}
