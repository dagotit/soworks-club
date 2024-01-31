package com.gmail.dlwk0807.dagachi.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGroupFile is a Querydsl query type for GroupFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGroupFile extends EntityPathBase<GroupFile> {

    private static final long serialVersionUID = 2105270583L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGroupFile groupFile = new QGroupFile("groupFile");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath delete_yn = createString("delete_yn");

    public final QGroup group;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath originalName = createString("originalName");

    public final StringPath saveName = createString("saveName");

    public final NumberPath<Long> size = createNumber("size", Long.class);

    //inherited
    public final StringPath sysRegDbId = _super.sysRegDbId;

    //inherited
    public final StringPath sysUpdDbId = _super.sysUpdDbId;

    //inherited
    public final StringPath sysUpdReason = _super.sysUpdReason;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QGroupFile(String variable) {
        this(GroupFile.class, forVariable(variable), INITS);
    }

    public QGroupFile(Path<? extends GroupFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGroupFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGroupFile(PathMetadata metadata, PathInits inits) {
        this(GroupFile.class, metadata, inits);
    }

    public QGroupFile(Class<? extends GroupFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.group = inits.isInitialized("group") ? new QGroup(forProperty("group")) : null;
    }

}

