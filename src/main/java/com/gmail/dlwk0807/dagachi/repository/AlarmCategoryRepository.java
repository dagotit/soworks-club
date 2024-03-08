package com.gmail.dlwk0807.dagachi.repository;

import com.gmail.dlwk0807.dagachi.entity.AlarmCategory;
import com.gmail.dlwk0807.dagachi.entity.AlarmCompanyId;
import org.springframework.data.jpa.repository.JpaRepository;

@Deprecated(since = "복합키 테스트를위한 repository")
public interface AlarmCategoryRepository extends JpaRepository<AlarmCategory, AlarmCompanyId> {
}
