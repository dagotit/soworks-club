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

    public final NumberPath<Long> id = createNumber("id", Long.class);

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

