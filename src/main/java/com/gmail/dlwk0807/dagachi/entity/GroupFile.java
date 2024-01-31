package com.gmail.dlwk0807.dagachi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "GROUP_FILE")
@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class GroupFile extends BaseEntity{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String originalName;
    private String saveName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID")
    @JsonIgnore
    private Group group;
    private Long size;
}
