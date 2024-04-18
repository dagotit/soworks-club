package com.gmail.dlwk0807.dagachi.entity;

import jakarta.persistence.*;
import lombok.*;


@Table(name = "COMPANY")
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Company extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String address;
    private String addressDtl;
    @Column(unique = true)
    private String bizNo;
    private String companyName;
    private String companyDate;

}
