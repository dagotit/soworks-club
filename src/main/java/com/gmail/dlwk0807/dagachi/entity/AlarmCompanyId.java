package com.gmail.dlwk0807.dagachi.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Deprecated(since = "복합키 테스트를위한 embeddable 객체")
public class AlarmCompanyId implements Serializable {

    private Long alarmId;
    private Long companyId;

}