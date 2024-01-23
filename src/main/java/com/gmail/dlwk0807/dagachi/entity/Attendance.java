package com.gmail.dlwk0807.dagachi.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Table(name = "ATTENDANCE")
@Entity
public class Attendance extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="MEMBER_ID")
    private Member member;
    private LocalDate attendDate;

    @Builder
    public Attendance(Member member, LocalDate attendDate) {
        this.member = member;
        this.attendDate = attendDate;
    }
}
