package com.gmail.dlwk0807.dagachi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Table(name = "CATEGORY")
@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Category extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "upCategoryId")
    private Category upCategory;

    @Column(name = "depth")
    private Long depth;

    @OneToMany(mappedBy = "upCategory")
    private List<Category> children = new ArrayList<>();

}
