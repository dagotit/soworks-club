package com.gmail.dlwk0807.dagachi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ALARM")
@Entity
public class Alarm extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long sendId;
    private Long receiveId;
    private String title;
    private String content;
    @ColumnDefault("'N'")
    private String readYn;
    private LocalDateTime regDateTime;

    public void updateReadYn() {
        this.readYn = "Y";
    }
}
