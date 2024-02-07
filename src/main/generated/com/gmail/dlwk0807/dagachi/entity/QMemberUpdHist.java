package com.gmail.dlwk0807.dagachi.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMemberUpdHist is a Querydsl query type for MemberUpdHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberUpdHist extends EntityPathBase<MemberUpdHist> {

    private static final long serialVersionUID = -165129587L;

    public static final QMemberUpdHist memberUpdHist = new QMemberUpdHist("memberUpdHist");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath sysRegDbId = _super.sysRegDbId;

    //inherited
    public final StringPath sysUpdDbId = _super.sysUpdDbId;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QMemberUpdHist(String variable) {
        super(MemberUpdHist.class, forVariable(variable));
    }

    public QMemberUpdHist(Path<? extends MemberUpdHist> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMemberUpdHist(PathMetadata metadata) {
        super(MemberUpdHist.class, metadata);
    }

}

