package com.gmail.dlwk0807.dagachi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Table(name = "CATEGORY")
@Entity
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Category extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

}
