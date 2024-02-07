package com.gmail.dlwk0807.dagachi.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGroup is a Querydsl query type for Group
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGroup extends EntityPathBase<Group> {

    private static final long serialVersionUID = 1818524955L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGroup group = new QGroup("group1");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath allDay = createString("allDay");

    public final ListPath<Category, QCategory> categories = this.<Category, QCategory>createList("categories", Category.class, QCategory.class, PathInits.DIRECT2);

    public final QCompany company;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    public final DateTimePath<java.time.LocalDateTime> endDateTime = createDateTime("endDateTime", java.time.LocalDateTime.class);

    public final ListPath<GroupAttend, QGroupAttend> groupAttendList = this.<GroupAttend, QGroupAttend>createList("groupAttendList", GroupAttend.class, QGroupAttend.class, PathInits.DIRECT2);

    public final ListPath<GroupFile, QGroupFile> groupFileList = this.<GroupFile, QGroupFile>createList("groupFileList", GroupFile.class, QGroupFile.class, PathInits.DIRECT2);

    public final StringPath groupImage = createString("groupImage");

    public final NumberPath<Long> groupMaxNum = createNumber("groupMaxNum", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final StringPath name = createString("name");

    public final DateTimePath<java.time.LocalDateTime> startDateTime = createDateTime("startDateTime", java.time.LocalDateTime.class);

    public final EnumPath<GroupStatus> status = createEnum("status", GroupStatus.class);

    //inherited
    public final StringPath sysRegDbId = _super.sysRegDbId;

    //inherited
    public final StringPath sysUpdDbId = _super.sysUpdDbId;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QGroup(String variable) {
        this(Group.class, forVariable(variable), INITS);
    }

    public QGroup(Path<? extends Group> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGroup(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGroup(PathMetadata metadata, PathInits inits) {
        this(Group.class, metadata, inits);
    }

    public QGroup(Class<? extends Group> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new QCompany(forProperty("company")) : null;
    }

}

