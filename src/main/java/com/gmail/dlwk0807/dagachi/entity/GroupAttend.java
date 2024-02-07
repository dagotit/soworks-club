package com.gmail.dlwk0807.dagachi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
@NoArgsConstructor
@Table(name = "GROUP_ATTEND")
@Entity
@Builder
@AllArgsConstructor
public class GroupAttend extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ColumnDefault("'N'")
    @Column(length = 2)
    private String attendYn = "N";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID")
    private Group group;

    public void updateAttendYn(String attendYn) {
        this.attendYn = attendYn;
    }
}
