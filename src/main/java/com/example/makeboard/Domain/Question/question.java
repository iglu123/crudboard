package com.example.makeboard.Domain.Question;

import com.example.makeboard.Domain.Answer.answer;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.OneToMany;

@Data
@Entity
public class question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name="question_id")
    private Integer id;
    @Column(columnDefinition = "TEXT")
    private String content;
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date create_date;
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date modify_date;
    @Column
    private LocalDateTime create_date;
    @Column
    private LocalDateTime modify_date;
    @Column(length = 200)
    private String subject;
    private Long author_id;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<answer> answerList;

}
