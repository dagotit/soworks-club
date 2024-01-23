package com.gmail.dlwk0807.dagachi.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QGroupAttach is a Querydsl query type for GroupAttach
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGroupAttach extends EntityPathBase<GroupAttach> {

    private static final long serialVersionUID = 102684480L;

    public static final QGroupAttach groupAttach = new QGroupAttach("groupAttach");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath sysRegDbId = _super.sysRegDbId;

    //inherited
    public final StringPath sysUpdDbId = _super.sysUpdDbId;

    //inherited
    public final StringPath sysUpdReason = _super.sysUpdReason;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QGroupAttach(String variable) {
        super(GroupAttach.class, forVariable(variable));
    }

    public QGroupAttach(Path<? extends GroupAttach> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGroupAttach(PathMetadata metadata) {
        super(GroupAttach.class, metadata);
    }

}

