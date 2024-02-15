package com.gmail.dlwk0807.dagachi.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTitle is a Querydsl query type for Title
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTitle extends EntityPathBase<Title> {

    private static final long serialVersionUID = 1830267124L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTitle title = new QTitle("title");

    public final QCategory category;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> requiredCount = createNumber("requiredCount", Long.class);

    public final StringPath titleName = createString("titleName");

    public final ListPath<MemberTitle, QMemberTitle> titles = this.<MemberTitle, QMemberTitle>createList("titles", MemberTitle.class, QMemberTitle.class, PathInits.DIRECT2);

    public QTitle(String variable) {
        this(Title.class, forVariable(variable), INITS);
    }

    public QTitle(Path<? extends Title> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTitle(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTitle(PathMetadata metadata, PathInits inits) {
        this(Title.class, metadata, inits);
    }

    public QTitle(Class<? extends Title> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new QCategory(forProperty("category")) : null;
    }

}

