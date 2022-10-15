package com.example.makeboard.Domain.Answer;

import com.example.makeboard.Domain.Question.question;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Text;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
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
    private Long author_id;

    @ManyToOne
    private question question;


}
