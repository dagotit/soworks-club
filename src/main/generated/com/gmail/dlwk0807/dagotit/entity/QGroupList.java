package com.gmail.dlwk0807.dagotit.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QGroupList is a Querydsl query type for GroupList
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGroupList extends EntityPathBase<GroupList> {

    private static final long serialVersionUID = -813015348L;

    public static final QGroupList groupList = new QGroupList("groupList");

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

    public QGroupList(String variable) {
        super(GroupList.class, forVariable(variable));
    }

    public QGroupList(Path<? extends GroupList> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGroupList(PathMetadata metadata) {
        super(GroupList.class, metadata);
    }

}

