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

    public final NumberPath<Long> id = createNumber("id", Long.class);

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

