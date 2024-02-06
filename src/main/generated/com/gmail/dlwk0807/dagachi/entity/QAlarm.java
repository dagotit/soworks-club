package com.gmail.dlwk0807.dagachi.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAlarm is a Querydsl query type for Alarm
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlarm extends EntityPathBase<Alarm> {

    private static final long serialVersionUID = 1812791533L;

    public static final QAlarm alarm = new QAlarm("alarm");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath readYn = createString("readYn");

    public final NumberPath<Long> receiveId = createNumber("receiveId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> regDateTime = createDateTime("regDateTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> sendId = createNumber("sendId", Long.class);

    //inherited
    public final StringPath sysRegDbId = _super.sysRegDbId;

    //inherited
    public final StringPath sysUpdDbId = _super.sysUpdDbId;

    //inherited
    public final StringPath sysUpdReason = _super.sysUpdReason;

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QAlarm(String variable) {
        super(Alarm.class, forVariable(variable));
    }

    public QAlarm(Path<? extends Alarm> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAlarm(PathMetadata metadata) {
        super(Alarm.class, metadata);
    }

}

