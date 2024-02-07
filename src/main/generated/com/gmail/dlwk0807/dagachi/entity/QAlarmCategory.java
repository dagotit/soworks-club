package com.gmail.dlwk0807.dagachi.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAlarmCategory is a Querydsl query type for AlarmCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlarmCategory extends EntityPathBase<AlarmCategory> {

    private static final long serialVersionUID = 1497687307L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAlarmCategory alarmCategory = new QAlarmCategory("alarmCategory");

    public final QAlarm alarm;

    public final QCompany company;

    public final QAlarmCompanyId id;

    public QAlarmCategory(String variable) {
        this(AlarmCategory.class, forVariable(variable), INITS);
    }

    public QAlarmCategory(Path<? extends AlarmCategory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAlarmCategory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAlarmCategory(PathMetadata metadata, PathInits inits) {
        this(AlarmCategory.class, metadata, inits);
    }

    public QAlarmCategory(Class<? extends AlarmCategory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.alarm = inits.isInitialized("alarm") ? new QAlarm(forProperty("alarm")) : null;
        this.company = inits.isInitialized("company") ? new QCompany(forProperty("company")) : null;
        this.id = inits.isInitialized("id") ? new QAlarmCompanyId(forProperty("id")) : null;
    }

}

