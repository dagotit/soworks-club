package com.gmail.dlwk0807.dagotit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "GROUP_MST")
@Entity
public class Group extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;
    private String name;
    private String memberId;
    private String picture;
    private String description;
    private String status;
    private String attachId;

    @Builder
    public Group(String category, String name, String memberId, String picture, String description, String status, String attachId) {
        this.category = category;
        this.name = name;
        this.memberId = memberId;
        this.picture = picture;
        this.description = description;
        this.status = status;
        this.attachId = attachId;
    }
}
