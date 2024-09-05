package com.yourtechnologies.yourtechnologies.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "forms")
@Builder
@Getter @Setter
public class Form {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String slug;
    private String description;
    private Integer limitOneResponse;
    private Long creatorId;
}
