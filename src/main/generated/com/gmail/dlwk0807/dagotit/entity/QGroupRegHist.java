package com.gmail.dlwk0807.dagotit.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QGroupRegHist is a Querydsl query type for GroupRegHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGroupRegHist extends EntityPathBase<GroupRegHist> {

    private static final long serialVersionUID = -316400408L;

    public static final QGroupRegHist groupRegHist = new QGroupRegHist("groupRegHist");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QGroupRegHist(String variable) {
        super(GroupRegHist.class, forVariable(variable));
    }

    public QGroupRegHist(Path<? extends GroupRegHist> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGroupRegHist(PathMetadata metadata) {
        super(GroupRegHist.class, metadata);
    }

}

