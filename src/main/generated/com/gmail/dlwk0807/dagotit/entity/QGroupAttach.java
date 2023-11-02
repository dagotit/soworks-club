package com.gmail.dlwk0807.dagotit.entity;

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

    private static final long serialVersionUID = 71551219L;

    public static final QGroupAttach groupAttach = new QGroupAttach("groupAttach");

    public final NumberPath<Long> id = createNumber("id", Long.class);

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

