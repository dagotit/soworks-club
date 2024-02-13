package com.gmail.dlwk0807.dagachi.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Deprecated(since = "복합키 테스트를위한 entity")
public class AlarmCategory {

    @EmbeddedId
    AlarmCompanyId id;

    @MapsId("alarmId")
    @ManyToOne
    @JoinColumn(name = "ALARM_ID")
    private Alarm alarm;

    @MapsId("companyId")
    @ManyToOne
    @JoinColumn(name = "COMPANY_ID")
    private Company company;

}