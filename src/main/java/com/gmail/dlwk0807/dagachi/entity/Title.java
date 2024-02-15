package com.gmail.dlwk0807.dagachi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Table(name = "TITLE")
@Entity
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Title {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titleName;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Builder.Default
    @OneToMany(mappedBy = "title")
    private List<MemberTitle> titles = new ArrayList<>();

    private Long requiredCount;

}
