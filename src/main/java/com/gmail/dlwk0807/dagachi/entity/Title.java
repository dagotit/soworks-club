package com.gmail.dlwk0807.dagachi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Table(name = "TITLE")
@Entity
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Title extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titleNo;
    private String titleName;


}
