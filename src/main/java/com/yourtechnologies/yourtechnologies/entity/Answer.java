package com.yourtechnologies.yourtechnologies.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "answers")
@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String value;

    @ManyToOne
    @JoinColumn(
            name = "response_id",
            referencedColumnName = "id"
    )
    private Response response;

    @ManyToOne
    @JoinColumn(
            name = "question_id",
            referencedColumnName = "id"
    )
    private Question question;
}
