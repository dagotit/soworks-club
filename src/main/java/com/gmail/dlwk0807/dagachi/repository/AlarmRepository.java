package com.gmail.dlwk0807.dagachi.repository;

import com.gmail.dlwk0807.dagachi.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    List<Alarm> findAllByReceiveId(Long receiveId);
}
