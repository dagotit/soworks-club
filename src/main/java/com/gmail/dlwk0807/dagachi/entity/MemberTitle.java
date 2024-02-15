package com.gmail.dlwk0807.dagachi.entity;

import jakarta.persistence.*;

@Entity
public class MemberTitle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "title_id")
    private Title title;

}
