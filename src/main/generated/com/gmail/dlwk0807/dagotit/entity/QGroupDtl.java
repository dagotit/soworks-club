package com.gmail.dlwk0807.dagotit.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QGroupDtl is a Querydsl query type for GroupDtl
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGroupDtl extends EntityPathBase<GroupDtl> {

    private static final long serialVersionUID = 1359239662L;

    public static final QGroupDtl groupDtl = new QGroupDtl("groupDtl");

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

    public QGroupDtl(String variable) {
        super(GroupDtl.class, forVariable(variable));
    }

    public QGroupDtl(Path<? extends GroupDtl> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGroupDtl(PathMetadata metadata) {
        super(GroupDtl.class, metadata);
    }

}

