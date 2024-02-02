package com.gmail.dlwk0807.dagachi.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAttendance is a Querydsl query type for Attendance
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAttendance extends EntityPathBase<Attendance> {

    private static final long serialVersionUID = 1477189069L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAttendance attendance = new QAttendance("attendance");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final DatePath<java.time.LocalDate> attendDate = createDate("attendDate", java.time.LocalDate.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    //inherited
    public final StringPath sysRegDbId = _super.sysRegDbId;

    //inherited
    public final StringPath sysUpdDbId = _super.sysUpdDbId;

    //inherited
    public final StringPath sysUpdReason = _super.sysUpdReason;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QAttendance(String variable) {
        this(Attendance.class, forVariable(variable), INITS);
    }

    public QAttendance(Path<? extends Attendance> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAttendance(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAttendance(PathMetadata metadata, PathInits inits) {
        this(Attendance.class, metadata, inits);
    }

    public QAttendance(Class<? extends Attendance> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

