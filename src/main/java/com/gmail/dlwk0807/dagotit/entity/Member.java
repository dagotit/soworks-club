package com.gmail.dlwk0807.dagotit.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@Table(name = "MEMBER")
@Entity
public class Member {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;
    private String address;
    private String bizno;

    @Enumerated(EnumType.STRING)
    private Authority authority;


    @Builder
    public Member(String email, String password, String address, String bizno, Authority authority) {
        this.email = email;
        this.password = password;
        this.address = address;
        this.bizno = bizno;
        this.authority = authority;
    }
}
