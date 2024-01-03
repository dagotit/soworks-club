package com.gmail.dlwk0807.dagotit.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QGroupTag is a Querydsl query type for GroupTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGroupTag extends EntityPathBase<GroupTag> {

    private static final long serialVersionUID = 1359254444L;

    public static final QGroupTag groupTag = new QGroupTag("groupTag");

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

    public QGroupTag(String variable) {
        super(GroupTag.class, forVariable(variable));
    }

    public QGroupTag(Path<? extends GroupTag> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGroupTag(PathMetadata metadata) {
        super(GroupTag.class, metadata);
    }

}

