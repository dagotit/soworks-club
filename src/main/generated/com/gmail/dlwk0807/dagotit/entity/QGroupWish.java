package com.gmail.dlwk0807.dagotit.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QGroupWish is a Querydsl query type for GroupWish
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGroupWish extends EntityPathBase<GroupWish> {

    private static final long serialVersionUID = -812687659L;

    public static final QGroupWish groupWish = new QGroupWish("groupWish");

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

    public QGroupWish(String variable) {
        super(GroupWish.class, forVariable(variable));
    }

    public QGroupWish(Path<? extends GroupWish> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGroupWish(PathMetadata metadata) {
        super(GroupWish.class, metadata);
    }

}

