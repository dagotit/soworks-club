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
    private String bizNo;
    private String name;
    private String companyName;
    private String companyDate;

    @Enumerated(EnumType.STRING)
    private Authority authority;


    @Builder
    public Member(String email, String password, String address, String bizNo, String name, String companyName, String companyDate, Authority authority) {
        this.email = email;
        this.password = password;
        this.address = address;
        this.bizNo = bizNo;
        this.name = name;
        this.companyName = companyName;
        this.companyDate = companyDate;
        this.authority = authority;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
