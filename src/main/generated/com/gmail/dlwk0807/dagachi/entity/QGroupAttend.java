package com.gmail.dlwk0807.dagachi.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGroupAttend is a Querydsl query type for GroupAttend
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGroupAttend extends EntityPathBase<GroupAttend> {

    private static final long serialVersionUID = 102688661L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGroupAttend groupAttend = new QGroupAttend("groupAttend");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath attendYn = createString("attendYn");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QGroup group;

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

    public QGroupAttend(String variable) {
        this(GroupAttend.class, forVariable(variable), INITS);
    }

    public QGroupAttend(Path<? extends GroupAttend> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGroupAttend(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGroupAttend(PathMetadata metadata, PathInits inits) {
        this(GroupAttend.class, metadata, inits);
    }

    public QGroupAttend(Class<? extends GroupAttend> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.group = inits.isInitialized("group") ? new QGroup(forProperty("group"), inits.get("group")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

