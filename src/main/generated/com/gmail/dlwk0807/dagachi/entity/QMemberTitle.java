package com.gmail.dlwk0807.dagachi.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberTitle is a Querydsl query type for MemberTitle
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberTitle extends EntityPathBase<MemberTitle> {

    private static final long serialVersionUID = 1232230778L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberTitle memberTitle = new QMemberTitle("memberTitle");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public final QTitle title;

    public QMemberTitle(String variable) {
        this(MemberTitle.class, forVariable(variable), INITS);
    }

    public QMemberTitle(Path<? extends MemberTitle> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberTitle(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberTitle(PathMetadata metadata, PathInits inits) {
        this(MemberTitle.class, metadata, inits);
    }

    public QMemberTitle(Class<? extends MemberTitle> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
        this.title = inits.isInitialized("title") ? new QTitle(forProperty("title"), inits.get("title")) : null;
    }

}

