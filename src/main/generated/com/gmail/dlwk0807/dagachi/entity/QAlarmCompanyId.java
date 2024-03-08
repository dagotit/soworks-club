package com.gmail.dlwk0807.dagachi.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAlarmCompanyId is a Querydsl query type for AlarmCompanyId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QAlarmCompanyId extends BeanPath<AlarmCompanyId> {

    private static final long serialVersionUID = 499971083L;

    public static final QAlarmCompanyId alarmCompanyId = new QAlarmCompanyId("alarmCompanyId");

    public final NumberPath<Long> alarmId = createNumber("alarmId", Long.class);

    public final NumberPath<Long> companyId = createNumber("companyId", Long.class);

    public QAlarmCompanyId(String variable) {
        super(AlarmCompanyId.class, forVariable(variable));
    }

    public QAlarmCompanyId(Path<? extends AlarmCompanyId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAlarmCompanyId(PathMetadata metadata) {
        super(AlarmCompanyId.class, metadata);
    }

}

