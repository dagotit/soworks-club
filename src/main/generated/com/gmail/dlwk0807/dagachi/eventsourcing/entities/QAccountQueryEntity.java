package com.gmail.dlwk0807.dagachi.eventsourcing.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAccountQueryEntity is a Querydsl query type for AccountQueryEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAccountQueryEntity extends EntityPathBase<AccountQueryEntity> {

    private static final long serialVersionUID = 778461544L;

    public static final QAccountQueryEntity accountQueryEntity = new QAccountQueryEntity("accountQueryEntity");

    public final NumberPath<Double> accountBalance = createNumber("accountBalance", Double.class);

    public final StringPath currency = createString("currency");

    public final StringPath id = createString("id");

    public final StringPath status = createString("status");

    public QAccountQueryEntity(String variable) {
        super(AccountQueryEntity.class, forVariable(variable));
    }

    public QAccountQueryEntity(Path<? extends AccountQueryEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAccountQueryEntity(PathMetadata metadata) {
        super(AccountQueryEntity.class, metadata);
    }

}

