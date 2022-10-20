package com.example.makeboard.Domain.Answer;

import com.example.makeboard.Domain.Question.question;
import com.example.makeboard.Domain.Site_User.site_user;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.w3c.dom.Text;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;
import javax.persistence.ManyToOne;

@Data
@Entity
public class answer {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "TEXT")
    private String content;
    @Column
    private LocalDateTime create_date;
    @Column
    private LocalDateTime modify_date;

    @ManyToOne
    private site_user author;

    @ManyToOne(fetch=FetchType.LAZY)
    private question question;

    @ManyToMany
    Set<site_user> voter;

}
