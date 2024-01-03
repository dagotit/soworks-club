package com.gmail.dlwk0807.dagotit.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLeaderBoard is a Querydsl query type for LeaderBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLeaderBoard extends EntityPathBase<LeaderBoard> {

    private static final long serialVersionUID = -1908110676L;

    public static final QLeaderBoard leaderBoard = new QLeaderBoard("leaderBoard");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath sysRegDbId = _super.sysRegDbId;

    //inherited
    public final StringPath sysUpdDbId = _super.sysUpdDbId;

    //inherited
    public final StringPath sysUpdReason = _super.sysUpdReason;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QLeaderBoard(String variable) {
        super(LeaderBoard.class, forVariable(variable));
    }

    public QLeaderBoard(Path<? extends LeaderBoard> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLeaderBoard(PathMetadata metadata) {
        super(LeaderBoard.class, metadata);
    }

}

