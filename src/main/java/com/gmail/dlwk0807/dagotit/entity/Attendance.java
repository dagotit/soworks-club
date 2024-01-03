package com.gmail.dlwk0807.dagotit.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private boolean attendance;

    @Builder
    public Attendance(Member member, LocalDate attendDate, boolean attendance) {
        this.member = member;
        this.attendDate = attendDate;
        this.attendance = attendance;
    }
}
