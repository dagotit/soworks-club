package com.gmail.dlwk0807.dagotit.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
@NoArgsConstructor
@Table(name = "GROUP_ATTEND")
@Entity
public class GroupAttend extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ColumnDefault("'N'")
    @Column(length = 5)
    private String attendYn;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "GROUP_ID")
    private Group group;

    @Builder
    public GroupAttend(String attendYn, Member member, Group group) {
        this.attendYn = attendYn;
        this.member = member;
        this.group = group;
    }
}
